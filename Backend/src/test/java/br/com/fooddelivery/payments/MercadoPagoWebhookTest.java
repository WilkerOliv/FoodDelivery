package br.com.fooddelivery.payments;

import java.time.Instant;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.*;
import static org.springframework.test.web.client.response.MockRestResponseCreators.*;

class MercadoPagoWebhookTest {
    @Test void validatesTimestampWindowAndUnits() {
        Instant now = Instant.parse("2026-11-23T12:00:00Z");
        for (int seconds : new int[]{-300, 0, 300}) {
            Instant signed = now.plusSeconds(seconds);
            assertTrue(MercadoPagoWebhookController.freshTimestamp("" + signed.getEpochSecond(), now, 300));
            assertTrue(MercadoPagoWebhookController.freshTimestamp("" + signed.toEpochMilli(), now, 300));
        }
        for (String value : Arrays.asList(null, "", "invalid", "99999999999999999999",
                "" + now.minusSeconds(301).getEpochSecond(), "" + now.plusSeconds(301).toEpochMilli())) {
            assertFalse(MercadoPagoWebhookController.freshTimestamp(value, now, 300));
        }
    }

    @Test void signedButExpiredNotificationsAreRejectedBeforeRepositoryAccess() throws Exception {
        var repository = mock(PaymentRepository.class);
        var properties = new MercadoPagoProperties();
        properties.setWebhookSecret("test-secret");
        var controller = new MercadoPagoWebhookController(repository, mock(PaymentService.class),
            mock(MercadoPagoRefundService.class), mock(MarketplaceAccountService.class), properties, RestClient.create());
        String ts = "" + Instant.now().minusSeconds(600).getEpochSecond();
        var mac = javax.crypto.Mac.getInstance("HmacSHA256");
        mac.init(new javax.crypto.spec.SecretKeySpec("test-secret".getBytes(java.nio.charset.StandardCharsets.UTF_8), "HmacSHA256"));
        String hash = java.util.HexFormat.of().formatHex(mac.doFinal(
            ("id:123;request-id:request;ts:" + ts + ";").getBytes(java.nio.charset.StandardCharsets.UTF_8)));
        var error = assertThrows(ResponseStatusException.class, () -> controller.receive("123", "payment",
            UUID.randomUUID(), "ts=" + ts + ",v1=" + hash, "request"));
        assertEquals(401, error.getStatusCode().value());
        verifyNoInteractions(repository);
    }

    @Test void malformedOrMismatchedProviderDataNeverChangesFinancialState() {
        for (String amount : List.of("null", "\"invalid\"", "10.001", "1e30", "9.00")) {
            exercise(amount, "123", "approved", "0", false);
        }
        exercise("10.00", "456", "approved", "0", false);
        exercise("10.00", "123", "refunded", "5.00", false);
        exercise("10.00", "123", "approved", "5.00", false);
        exercise("10.00", "123", "approved", "10.00", false);
        exercise("10.00", "123", "approved", "0", true);
        exercise("10.00", "123", "refunded", "10.00", true);
    }

    @Test void duplicateAndOutOfOrderEventsKeepFinancialState() {
        Payment payment = new Payment(mock(br.com.fooddelivery.orders.Order.class));
        payment.confirm("123");
        payment.confirm("123");
        payment.fail("123");
        assertEquals(PaymentStatus.PAID, payment.getStatus());
        payment.requestFullRefund();
        UUID key = payment.getRefundIdempotencyKey();
        payment.requestFullRefund();
        assertEquals(key, payment.getRefundIdempotencyKey());
        payment.refundSucceeded("456");
        payment.confirm("123");
        payment.fail("123");
        assertEquals(PaymentStatus.REFUNDED, payment.getStatus());
    }

    @Test void unavailableProviderAndWrongCurrencyDoNotChangeState() {
        exercise("10.00","123","approved","0", false, "USD", false);
        exercise("10.00","123","approved","0", false, "BRL", true);
    }

    private void exercise(String amount, String id, String status, String refunded, boolean accepted) {
        exercise(amount, id, status, refunded, accepted, "BRL", false);
    }

    private void exercise(String amount, String id, String status, String refunded, boolean accepted, String currency, boolean unavailable) {
        var owner = new br.com.fooddelivery.identity.Account("owner@example.test", "QA", "encoded");
        var store = new br.com.fooddelivery.catalog.Store(owner, "Loja QA", null, "Rua QA", "Mercados");
        var order = new br.com.fooddelivery.orders.Order(owner, store,
            br.com.fooddelivery.orders.FulfillmentType.PICKUP, null, 1000, 0, null);
        Payment payment = new Payment(order);
        org.springframework.test.util.ReflectionTestUtils.setField(payment, "id", UUID.randomUUID());
        var repository = mock(PaymentRepository.class);
        var service = mock(PaymentService.class);
        var refunds = mock(MercadoPagoRefundService.class);
        var accounts = mock(MarketplaceAccountService.class);
        when(repository.findWithOperationById(payment.getId())).thenReturn(Optional.of(payment));
        when(accounts.accessTokenForStore(any())).thenReturn("test-token");
        var builder = RestClient.builder();
        var server = MockRestServiceServer.bindTo(builder).build();
        server.expect(requestTo("https://api.mercadopago.com/v1/payments/123"))
            .andRespond(unavailable ? withServerError() : withSuccess("{\"id\":\"" + id + "\",\"external_reference\":\"" + payment.getId()
                + "\",\"currency_id\":\"" + currency + "\",\"transaction_amount\":" + amount
                + ",\"status\":\"" + status + "\",\"transaction_amount_refunded\":" + refunded + "}", MediaType.APPLICATION_JSON));
        var properties = new MercadoPagoProperties();
        properties.setSandbox(true);
        properties.setTestWebhookSignature(true);
        var controller = new MercadoPagoWebhookController(repository, service, refunds, accounts, properties, builder.build());
        if (accepted) {
            assertEquals("processed", controller.receive("123", "payment", payment.getId(), null, null).get("status"));
            if ("refunded".equals(status)) verify(refunds).providerConfirmedRefund("123");
            else verify(service).confirmProvider(payment.getId(), "123");
        } else {
            var error = assertThrows(ResponseStatusException.class,
                () -> controller.receive("123", "payment", payment.getId(), null, null));
            assertEquals(unavailable ? 502 : 409, error.getStatusCode().value());
            verifyNoInteractions(service, refunds);
        }
        server.verify();
    }
}
