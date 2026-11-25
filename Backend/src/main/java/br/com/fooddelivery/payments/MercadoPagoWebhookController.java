package br.com.fooddelivery.payments;

import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.reservations.Reservation;
import java.time.Instant;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Map;
import java.util.UUID;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.beans.factory.annotation.Qualifier;

@RestController
@RequestMapping("/api/v1/webhooks/mercadopago")
public class MercadoPagoWebhookController {
    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(MercadoPagoWebhookController.class);
    private static ResponseStatusException rejected(HttpStatus status, String message) {
        log.warn("Webhook Mercado Pago recusado: status={}", status.value());
        return new ResponseStatusException(status, message);
    }
    private final PaymentRepository payments;
    private final PaymentService paymentService;
    private final MercadoPagoRefundService refundService;
    private final MarketplaceAccountService marketplaceAccounts;
    private final MercadoPagoProperties properties;
    private final RestClient http;

    public MercadoPagoWebhookController(PaymentRepository payments,PaymentService paymentService,MercadoPagoRefundService refundService,MarketplaceAccountService marketplaceAccounts,MercadoPagoProperties properties,@Qualifier("mercadoPagoRestClient") RestClient http){this.payments=payments;this.paymentService=paymentService;this.refundService=refundService;this.marketplaceAccounts=marketplaceAccounts;this.properties=properties;this.http=http;}

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    public Map<String,String> receive(@RequestParam(name="data.id",required=false) String dataId,
            @RequestParam(required=false) String type,@RequestParam(name="payment_id") UUID internalPaymentId,
            @RequestHeader(name="x-signature",required=false) String signature,
            @RequestHeader(name="x-request-id",required=false) String requestId){
        if(!"payment".equals(type)||dataId==null||dataId.isBlank())return Map.of("status","ignored");
        if(!validSignature(dataId,requestId,signature))throw rejected(HttpStatus.UNAUTHORIZED,"Assinatura de webhook inválida.");
        Payment payment=payments.findWithOperationById(internalPaymentId).orElseThrow(()->rejected(HttpStatus.NOT_FOUND, "Pagamento não encontrado."));
        Order order=payment.getOrder();Reservation reservation=payment.getReservation();
        var store=order!=null?order.getStore():reservation.getStore();
        String accessToken=marketplaceAccounts.accessTokenForStore(store.getId());
        Map<String,Object> mpPayment;
        try{mpPayment=http.get().uri("https://api.mercadopago.com/v1/payments/{id}",dataId).header("Authorization","Bearer "+accessToken).retrieve().body(new ParameterizedTypeReference<>(){});}
        catch(RuntimeException error){throw rejected(HttpStatus.BAD_GATEWAY,"Não foi possível validar a notificação com o Mercado Pago.");}
        String mismatch = PaymentReconciliationService.validate(payment, mpPayment, dataId);
        if (mismatch != null) throw rejected(HttpStatus.CONFLICT, mismatch);
        String status=String.valueOf(mpPayment.get("status"));
        if("approved".equals(status))paymentService.confirmProvider(internalPaymentId,dataId);
        else if("refunded".equals(status))refundService.providerConfirmedRefund(dataId);
        else if("rejected".equals(status)||"cancelled".equals(status))paymentService.failProvider(internalPaymentId,dataId);
        return Map.of("status","processed");
    }

    // Accept the seconds and milliseconds representations used in provider documentation.
    static boolean freshTimestamp(String timestamp, Instant now, int toleranceSeconds) {
        if (timestamp == null || !timestamp.matches("[0-9]{10}|[0-9]{13}")) return false;
        try {
            long value = Long.parseLong(timestamp);
            Instant signedAt = timestamp.length() == 13 ? Instant.ofEpochMilli(value) : Instant.ofEpochSecond(value);
            return !signedAt.isBefore(now.minusSeconds(toleranceSeconds)) && !signedAt.isAfter(now.plusSeconds(toleranceSeconds));
        } catch (RuntimeException invalid) {
            return false;
        }
    }

    private boolean validSignature(String dataId,String requestId,String signature){
        if(properties.testWebhookSignature()&&properties.sandbox())return true;
        if(!properties.webhookConfigured()||signature==null||requestId==null)return false;
        String timestamp=null,provided=null;
        for(String part:signature.split(",")){String[] pair=part.trim().split("=",2);if(pair.length!=2)continue;if(pair[0].equals("ts"))timestamp=pair[1];if(pair[0].equals("v1"))provided=pair[1];}
        if(timestamp==null||provided==null||!provided.matches("[a-fA-F0-9]{64}")||!freshTimestamp(timestamp, Instant.now(), properties.getWebhookToleranceSeconds()))return false;
        String manifest="id:"+dataId.toLowerCase(java.util.Locale.ROOT)+";request-id:"+requestId+";ts:"+timestamp+";";
        try{Mac mac=Mac.getInstance("HmacSHA256");mac.init(new SecretKeySpec(properties.webhookSecret().getBytes(StandardCharsets.UTF_8),"HmacSHA256"));String expected=java.util.HexFormat.of().formatHex(mac.doFinal(manifest.getBytes(StandardCharsets.UTF_8)));return MessageDigest.isEqual(expected.getBytes(StandardCharsets.US_ASCII),provided.toLowerCase(java.util.Locale.ROOT).getBytes(StandardCharsets.US_ASCII));}
        catch(Exception e){return false;}
    }
}
