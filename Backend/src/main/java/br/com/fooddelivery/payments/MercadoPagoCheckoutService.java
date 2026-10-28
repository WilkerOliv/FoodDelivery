package br.com.fooddelivery.payments;

import br.com.fooddelivery.orders.Order;
import br.com.fooddelivery.orders.OrderRepository;
import br.com.fooddelivery.reservations.Reservation;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.time.format.DateTimeFormatter;
import java.time.ZoneOffset;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;
import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class MercadoPagoCheckoutService {
    private final PaymentRepository payments;
    private final MarketplaceAccountService marketplaceAccounts;
    private final MercadoPagoProperties properties;
    private final Environment environment;
    private final PaymentService paymentService;
    private final RestClient http;

    public MercadoPagoCheckoutService(PaymentRepository payments,MarketplaceAccountService marketplaceAccounts,MercadoPagoProperties properties,Environment environment,PaymentService paymentService,@Qualifier("mercadoPagoRestClient") RestClient http){this.payments=payments;this.marketplaceAccounts=marketplaceAccounts;this.properties=properties;this.environment=environment;this.paymentService=paymentService;this.http=http;}

    @Transactional
    public CheckoutView create(UUID paymentId,UUID customerId){
        if(!properties.checkoutConfigured())throw new IllegalStateException("O Mercado Pago ainda não foi configurado. Fale com a equipe da plataforma.");
        if(Arrays.asList(environment.getActiveProfiles()).contains("prod")&&(!properties.hasNotificationUrl()||!properties.webhookConfigured()||!properties.notificationBaseUrl().startsWith("https://")))throw new IllegalStateException("Pagamentos em produção exigem um endereço HTTPS público e a assinatura de webhook do Mercado Pago.");
        Payment payment=payments.findById(paymentId).orElseThrow(()->new NoSuchElementException("Pagamento não encontrado."));
        Order order=payment.getOrder();Reservation reservation=payment.getReservation();
        if(order==null&&reservation==null)throw new IllegalStateException("Não há pedido ou reserva associado ao pagamento.");
        var customer=order!=null?order.getCustomer():reservation.getCustomer();
        var store=order!=null?order.getStore():reservation.getStore();
        if(!customer.getId().equals(customerId))throw new org.springframework.security.access.AccessDeniedException("Este pagamento pertence a outra conta.");
        if(payment.getStatus()!=PaymentStatus.PENDING&&payment.getStatus()!=PaymentStatus.FAILED)throw new IllegalStateException("Este pagamento não está disponível para um novo checkout.");
        if(!store.isAvailable())throw new IllegalStateException("A loja não está disponível para receber pagamentos.");
        if(order!=null&&order.getStatus()!=br.com.fooddelivery.orders.OrderStatus.AWAITING_PAYMENT)throw new IllegalStateException("O pedido não está mais aguardando pagamento.");
        if(reservation!=null&&reservation.getStatus()!=br.com.fooddelivery.reservations.ReservationStatus.PENDING_PAYMENT)throw new IllegalStateException("A reserva não está mais aguardando pagamento.");
        Instant operationCreatedAt=order!=null?order.getCreatedAt():reservation.getCreatedAt();
        Instant expiresAt=operationCreatedAt.plus(15,ChronoUnit.MINUTES);
        if(!expiresAt.isAfter(Instant.now()))throw new IllegalStateException("O prazo de pagamento terminou. Crie um novo pedido ou escolha outro horário.");

        List<Map<String,Object>> items=new ArrayList<>();
        if(order!=null){for(Order.OrderLine line:order.getLines())items.add(item(line.getItemId().toString(),line.getName(),line.getQuantity(),line.getUnitPriceCents()));if(order.getDeliveryFeeCents()>0)items.add(item("delivery-"+order.getId(),"Taxa de entrega",1,order.getDeliveryFeeCents()));}
        else items.add(item(reservation.getCatalogItemId().toString(),reservation.getServiceName(),1,reservation.getAmountCents()));
        Map<String,Object> body=new LinkedHashMap<>();body.put("items",items);body.put("currency_id",properties.currency());
        body.put("external_reference",payment.getId().toString());
        body.put("marketplace_fee",BigDecimal.valueOf(payment.getAmountCents()).multiply(BigDecimal.valueOf(store.getMarketplaceFeeBps()))
                .divide(BigDecimal.valueOf(10000),2,RoundingMode.HALF_UP).movePointLeft(2));
        body.put("payer",Map.of("email",customer.getEmail(),"name",customer.getName()));
        String returnPage=reservation!=null?"reservations":"orders";
        body.put("back_urls",Map.of("success",returnUrl("success",payment,returnPage),"pending",returnUrl("pending",payment,returnPage),"failure",returnUrl("failure",payment,returnPage)));
        body.put("auto_return","approved");
        body.put("expires",true);
        body.put("expiration_date_to",DateTimeFormatter.ISO_OFFSET_DATE_TIME.format(expiresAt.atOffset(ZoneOffset.UTC)));
        body.put("metadata",Map.of("payment_id",payment.getId().toString()));
        if(properties.hasNotificationUrl())body.put("notification_url",UriComponentsBuilder.fromUriString(properties.notificationBaseUrl())
                .path("/api/v1/webhooks/mercadopago").queryParam("payment_id",payment.getId()).build().toUriString());
        String accessToken=marketplaceAccounts.accessTokenForStore(store.getId());
        try {
            Map<String,Object> response=http.post().uri("https://api.mercadopago.com/checkout/preferences")
                    .header("Authorization","Bearer "+accessToken).header("X-Idempotency-Key",payment.getId().toString())
                    .contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(body).retrieve()
                    .body(new ParameterizedTypeReference<>(){});
            if(response==null)throw new IllegalStateException();
            String preferenceId=Objects.toString(response.get("id"),"");
            String checkoutUrl=Objects.toString(properties.sandbox()?response.getOrDefault("sandbox_init_point",response.get("init_point")):response.get("init_point"),"");
            if(preferenceId.isBlank()||checkoutUrl.isBlank())throw new IllegalStateException();
            payment.checkoutCreated(preferenceId,checkoutUrl);payments.save(payment);
            return new CheckoutView(paymentId,checkoutUrl,payment.getStatus());
        } catch(RestClientException|IllegalStateException error) {
            if(error instanceof IllegalStateException stateError&&stateError.getMessage()!=null&&stateError.getMessage().startsWith("A loja"))throw stateError;
            throw new IllegalStateException("Não conseguimos abrir o pagamento agora. Confira a conexão e tente novamente.");
        }
    }

    private String returnUrl(String result,Payment payment,String page){return UriComponentsBuilder.fromUriString(properties.frontendUrl())
            .queryParam("mp",result).queryParam("app_payment_id",payment.getId()).fragment(page).build().encode().toUriString();}

    @Transactional
    public PaymentController.PaymentView syncFromProvider(UUID paymentId,UUID customerId,String providerPaymentId){
        if(providerPaymentId==null||!providerPaymentId.matches("[0-9]{1,30}"))throw new IllegalArgumentException("Não recebemos um identificador válido do Mercado Pago.");
        Payment payment=payments.findById(paymentId).orElseThrow(()->new NoSuchElementException("Pagamento não encontrado."));
        Order order=payment.getOrder();Reservation reservation=payment.getReservation();var customer=order!=null?order.getCustomer():reservation.getCustomer();
        if(!customer.getId().equals(customerId))throw new org.springframework.security.access.AccessDeniedException("Este pagamento pertence a outra conta.");
        if(payment.getStatus()!=PaymentStatus.PAID&&payment.getProviderPreferenceId()!=null){
            var store=order!=null?order.getStore():reservation.getStore();String accessToken=marketplaceAccounts.accessTokenForStore(store.getId());
            Map<String,Object> response;
            try{response=http.get().uri("https://api.mercadopago.com/v1/payments/{id}",providerPaymentId).header("Authorization","Bearer "+accessToken).retrieve().body(new ParameterizedTypeReference<>(){});}
            catch(RestClientException error){throw new IllegalStateException("Não foi possível consultar o Mercado Pago. Tente novamente em instantes.");}
            if(response==null||!paymentId.toString().equals(Objects.toString(response.get("external_reference"),"")))throw new org.springframework.security.access.AccessDeniedException("A transação não corresponde a este pagamento.");
            BigDecimal amount=new BigDecimal(Objects.toString(response.get("transaction_amount"),"0"));
            if(amount.movePointRight(2).longValueExact()!=payment.getAmountCents()||!properties.currency().equals(response.get("currency_id")))throw new IllegalStateException("O valor da transação não corresponde ao total esperado.");
            String status=Objects.toString(response.get("status"),"");
            if("approved".equals(status))paymentService.confirmProvider(paymentId,providerPaymentId);
            else if("rejected".equals(status)||"cancelled".equals(status))paymentService.failProvider(paymentId,providerPaymentId);
        }
        Payment refreshed=payments.findById(paymentId).orElseThrow();
        return new PaymentController.PaymentView(refreshed.getId(),refreshed.getAmountCents(),refreshed.getStatus(),refreshed.getOrder()==null?null:refreshed.getOrder().getId(),refreshed.getReservation()==null?null:refreshed.getReservation().getId());
    }
    private Map<String,Object> item(String id,String title,int quantity,long unitPriceCents){return Map.of("id",id,"title",title,"quantity",quantity,"currency_id",properties.currency(),"unit_price",BigDecimal.valueOf(unitPriceCents,2));}
    public record CheckoutView(UUID paymentId,String checkoutUrl,PaymentStatus status){}
}
