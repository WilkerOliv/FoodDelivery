package br.com.fooddelivery.payments;

import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.reservations.Reservation;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.time.Duration;
import java.net.http.HttpClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientResponseException;

/** Retries full refunds with the same provider idempotency key after ambiguous failures. */
@Service
public class MercadoPagoRefundService {
    private static final int MAX_AMBIGUOUS_ATTEMPTS = 8;
    private final PaymentRepository payments;
    private final MarketplaceAccountService marketplaceAccounts;
    private final NotificationService notifications;
    private final RestClient http = createHttpClient();

    public MercadoPagoRefundService(PaymentRepository payments, MarketplaceAccountService marketplaceAccounts,
            NotificationService notifications) {
        this.payments = payments;
        this.marketplaceAccounts = marketplaceAccounts;
        this.notifications = notifications;
    }

    private static RestClient createHttpClient() {
        var client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build();
        var factory = new JdkClientHttpRequestFactory(client);
        factory.setReadTimeout(Duration.ofSeconds(15));
        return RestClient.builder().requestFactory(factory).build();
    }

    @Transactional
    public void attempt(UUID paymentId) {
        Payment payment = payments.findById(paymentId).orElse(null);
        if (payment == null || !payment.beginRefundAttempt()) return;
        if (payment.getProviderReference() == null || !payment.getProviderReference().matches("[0-9]{1,30}")) {
            payment.refundNeedsReview("O Mercado Pago não retornou o identificador do pagamento. Verifique a transação no painel do provedor.");
            return;
        }

        Order order = payment.getOrder();
        Reservation reservation = payment.getReservation();
        if (order == null && reservation == null) {
            payment.refundNeedsReview("Pagamento sem pedido ou reserva associados.");
            return;
        }
        var store = order != null ? order.getStore() : reservation.getStore();
        try {
            String accessToken = marketplaceAccounts.accessTokenForStore(store.getId());
            Map<String, Object> refund = http.post()
                    .uri("https://api.mercadopago.com/v1/payments/{id}/refunds", payment.getProviderReference())
                    .header("Authorization", "Bearer " + accessToken)
                    .header("X-Idempotency-Key", payment.getRefundIdempotencyKey().toString())
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Map.of())
                    .retrieve()
                    .body(new ParameterizedTypeReference<>() {});

            String providerRefundId = refund == null ? "" : Objects.toString(refund.get("id"), "");
            String status = refund == null ? "" : Objects.toString(refund.get("status"), "");
            if (providerRefundId.isBlank()) {
                payment.refundAttemptFailed("O Mercado Pago não confirmou o identificador do reembolso; a solicitação será reconciliada com a mesma chave idempotente.");
            } else if ("approved".equals(status)) {
                payment.refundSucceeded(providerRefundId);
                notifyRefundComplete(payment, store.getName());
            } else if ("pending".equals(status) || "in_process".equals(status)) {
                payment.refundAccepted(providerRefundId);
                notifications.send(paymentCustomer(payment), "Estorno em processamento",
                        "O Mercado Pago recebeu a solicitação de estorno de " + store.getName() + ". Avisaremos quando houver atualização.", "info");
            } else {
                payment.refundNeedsReview("O Mercado Pago não aprovou automaticamente o reembolso. Verifique saldo, permissões e estado da transação no painel do provedor.");
            }
        } catch (RestClientResponseException error) {
            if (error.getStatusCode().is4xxClientError()) {
                payment.refundNeedsReview("O Mercado Pago recusou o reembolso. Verifique saldo do vendedor, permissões e estado da transação no painel do provedor.");
            } else {
                ambiguousFailure(payment);
            }
        } catch (RuntimeException error) {
            ambiguousFailure(payment);
        }
    }

    @Transactional
    public void providerConfirmedRefund(String providerPaymentId) {
        Payment payment = payments.findByProviderReference(providerPaymentId).orElse(null);
        if (payment == null || payment.getStatus() == PaymentStatus.REFUNDED) return;
        if (payment.getStatus() != PaymentStatus.REFUND_PENDING && payment.getStatus() != PaymentStatus.REFUND_REVIEW) return;
        payment.refundSucceeded(payment.getRefundProviderReference());
        var store = payment.getOrder() != null ? payment.getOrder().getStore() : payment.getReservation().getStore();
        notifyRefundComplete(payment, store.getName());
    }

    private void ambiguousFailure(Payment payment) {
        if (payment.getRefundAttempts() >= MAX_AMBIGUOUS_ATTEMPTS) {
            payment.refundNeedsReview("Não foi possível confirmar o resultado após várias tentativas. Confira o pagamento e o estorno no painel do Mercado Pago.");
        } else {
            payment.refundAttemptFailed("Não foi possível confirmar o resultado agora. O sistema tentará novamente sem duplicar a solicitação.");
        }
    }

    private void notifyRefundComplete(Payment payment, String storeName) {
        notifications.send(paymentCustomer(payment), "Estorno confirmado",
                "O Mercado Pago confirmou o estorno referente a " + storeName + ". O prazo para o valor aparecer depende do meio de pagamento.", "success");
    }

    private br.com.fooddelivery.identity.Account paymentCustomer(Payment payment) {
        return payment.getOrder() != null ? payment.getOrder().getCustomer() : payment.getReservation().getCustomer();
    }
}
