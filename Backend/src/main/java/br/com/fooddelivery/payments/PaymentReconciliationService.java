package br.com.fooddelivery.payments;
import br.com.fooddelivery.config.DemoEnvironment;
import br.com.fooddelivery.admin.AdminAuditService;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.RestClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.PlatformTransactionManager;
@Service
public class PaymentReconciliationService {
 private final PaymentRepository payments;private final MarketplaceAccountService accounts;private final PaymentService service;private final MercadoPagoRefundService refunds;private final RestClient http;private final PaymentReconciliationRepository reports;private final TransactionTemplate transaction;private final DemoEnvironment demo;private final AdminAuditService audit;
 public PaymentReconciliationService(PaymentRepository payments,MarketplaceAccountService accounts,PaymentService service,MercadoPagoRefundService refunds,@Qualifier("mercadoPagoRestClient") RestClient http,PaymentReconciliationRepository reports,PlatformTransactionManager manager,DemoEnvironment demo,AdminAuditService audit){this.payments=payments;this.accounts=accounts;this.service=service;this.refunds=refunds;this.http=http;this.reports=reports;transaction=new TransactionTemplate(manager);this.demo=demo;this.audit=audit;}
 public PaymentReconciliation reconcile(UUID id,UUID actor,String suppliedProviderId){
  Payment payment=payments.findWithOperationById(id).orElseThrow();
  if(demo.enabled())return record(id,actor,"SIMULATED",null,"Ambiente simulado: não houve consulta nem validação financeira externa.");
  String providerId=suppliedProviderId==null||suppliedProviderId.isBlank()?payment.getProviderReference():suppliedProviderId.trim();
  if(providerId==null||!providerId.matches("[0-9]{1,30}"))throw new IllegalArgumentException("Informe o identificador numérico do pagamento no Mercado Pago.");
  var store=payment.getOrder()!=null?payment.getOrder().getStore():payment.getReservation().getStore();
  Map<String,Object> provider;
  try{String token=accounts.accessTokenForStore(store.getId());provider=http.get().uri("https://api.mercadopago.com/v1/payments/{id}",providerId).headers(h->h.setBearerAuth(token)).retrieve().body(new ParameterizedTypeReference<>(){});}
  catch(RuntimeException error){return record(id,actor,"ERROR",null,"Não foi possível consultar o provedor. Nenhum valor ou estado foi alterado.");}
  String mismatch=validate(payment,provider,providerId);
  if(mismatch!=null)return record(id,actor,"MISMATCH",safeStatus(provider),mismatch);
  String status=safeStatus(provider);
  return transaction.execute(tx->{Payment current=payments.findById(id).orElseThrow();String before=current.getStatus().name();
   if("approved".equals(status))service.confirmProvider(id,providerId);
   else if("refunded".equals(status)){if(current.getStatus()==PaymentStatus.PENDING||current.getStatus()==PaymentStatus.FAILED)return record(id,actor,"REVIEW",status,"O provedor informa reembolso de operação não confirmada localmente. Revise sem reativar o pedido.");if(current.getStatus()==PaymentStatus.PAID)current.requestFullRefund();refunds.providerConfirmedRefund(providerId);}
   else if("rejected".equals(status)||"cancelled".equals(status)){if(current.getStatus()!=PaymentStatus.PENDING&&current.getStatus()!=PaymentStatus.FAILED)return record(id,actor,"REVIEW",status,"O provedor diverge de um estado financeiro concluído. Exige revisão humana.");service.failProvider(id,providerId);}
   else if("charged_back".equals(status))return record(id,actor,"REVIEW",status,"Contestação identificada. Exige revisão humana; o pedido e o estoque não foram revertidos automaticamente.");
   else return record(id,actor,"REVIEW",status,"Estado do provedor exige acompanhamento. Nenhuma conclusão financeira foi presumida.");
   return record(id,actor,before.equals(current.getStatus().name())?"MATCHED":"UPDATED",status,"Consulta validada por identificador, referência externa, valor e moeda. Estado local conferido.");});
 }
 public static String validate(Payment local,Map<String,Object> remote,String id){try{
  if(remote==null||!id.equals(String.valueOf(remote.get("id")))||!local.getId().toString().equals(String.valueOf(remote.get("external_reference"))))return "Identificador ou referência externa divergente. Nenhum estado foi alterado.";
  if(!"BRL".equals(String.valueOf(remote.get("currency_id")))||new BigDecimal(String.valueOf(remote.get("transaction_amount"))).movePointRight(2).longValueExact()!=local.getAmountCents())return "Valor ou moeda divergente. Nenhum estado foi alterado.";
  Object reportedRefund = remote.get("transaction_amount_refunded");
  if ("refunded".equals(remote.get("status"))) {
   if (reportedRefund == null || cents(reportedRefund) != local.getAmountCents())
    return "Reembolso integral não comprovado. Encaminhe para revisão.";
  } else if (reportedRefund != null && cents(reportedRefund) != 0) {
   return "Valor devolvido incompatível com o estado informado. Encaminhe para revisão.";
  }
  if(local.getProviderReference()!=null&&local.getProviderReference().matches("[0-9]{1,30}")&&!id.equals(local.getProviderReference()))return "O registro já está associado a outro pagamento do provedor. Revise antes de alterar.";
  return null;
 }catch(RuntimeException invalid){return "Resposta financeira incompleta ou inválida. Nenhum estado foi alterado.";}}
 private static long cents(Object amount) { return new BigDecimal(String.valueOf(amount)).movePointRight(2).longValueExact(); }
 private static String safeStatus(Map<String,Object> value){String status=value==null?null:String.valueOf(value.get("status"));return status!=null&&status.matches("[a-z_]{1,40}")?status:null;}
 private PaymentReconciliation record(UUID id,UUID actor,String result,String status,String summary){return transaction.execute(tx->{audit.record(actor,"PAYMENT_RECONCILED","PAYMENT",id,"Conciliação: "+result);return reports.save(new PaymentReconciliation(id,actor,result,status,summary));});}
}
