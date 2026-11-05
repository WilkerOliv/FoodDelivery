package br.com.fooddelivery.payments;
import br.com.fooddelivery.orders.*;
import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.identity.Account;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
class PaymentReconciliationTest {
 final Payment payment=new Payment(new Order(new Account("qa@example.test","QA","encoded"),new Store(new Account("owner@example.test","QA","encoded"),"Loja",null,"Rua","Mercados"),FulfillmentType.PICKUP,null,1000,0,null));
 PaymentReconciliationTest(){ReflectionTestUtils.setField(payment,"id",UUID.randomUUID());}
 Map<String,Object> response(){return new HashMap<>(Map.of("id",123,"external_reference",payment.getId().toString(),"currency_id","BRL","transaction_amount","10.00","status","approved"));}
 @Test void matchesOnlyExactReferenceAmountCurrencyAndProviderId(){assertNull(PaymentReconciliationService.validate(payment,response(),"123"));for(String field:List.of("id","external_reference","currency_id","transaction_amount")){var body=response();body.put(field,"wrong");assertNotNull(PaymentReconciliationService.validate(payment,body,"123"));}}
 @Test void partialRefundCannotBeConsideredFullRefund(){var body=response();body.put("status","refunded");body.put("transaction_amount_refunded","5.00");assertNotNull(PaymentReconciliationService.validate(payment,body,"123"));body.put("transaction_amount_refunded","10.00");assertNull(PaymentReconciliationService.validate(payment,body,"123"));}
 @Test void differentExistingProviderAssociationIsNotOverwritten(){payment.confirm("456");assertNotNull(PaymentReconciliationService.validate(payment,response(),"123"));}
}
