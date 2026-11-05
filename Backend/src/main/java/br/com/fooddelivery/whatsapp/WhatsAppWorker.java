package br.com.fooddelivery.whatsapp;
import br.com.fooddelivery.settings.PrivateCipher;
import br.com.fooddelivery.config.DemoEnvironment;
import br.com.fooddelivery.payments.MercadoPagoCheckoutService;
import java.util.*;
import java.time.*;
import java.net.http.HttpClient;
import org.springframework.stereotype.Service;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.client.RestClient;
import org.springframework.http.client.JdkClientHttpRequestFactory;
@Service
public class WhatsAppWorker {
 private final WhatsAppCommerce commerce;private final WhatsAppEventRepository events;private final WhatsAppReplyRepository replies;private final PrivateCipher cipher;private final DemoEnvironment demo;private final MercadoPagoCheckoutService checkout;private final TransactionTemplate tx;private final RestClient http;
 public WhatsAppWorker(WhatsAppCommerce commerce,WhatsAppEventRepository events,WhatsAppReplyRepository replies,PrivateCipher cipher,DemoEnvironment demo,MercadoPagoCheckoutService checkout,PlatformTransactionManager manager){this.commerce=commerce;this.events=events;this.replies=replies;this.cipher=cipher;this.demo=demo;this.checkout=checkout;tx=new TransactionTemplate(manager);var factory=new JdkClientHttpRequestFactory(HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).build());factory.setReadTimeout(Duration.ofSeconds(10));http=RestClient.builder().requestFactory(factory).build();}
 @Scheduled(fixedDelayString="${app.whatsapp.poll-ms:3000}") public void tick(){if(!commerce.enabled())return;
  for(var event:events.findByStatusOrderByCreatedAt("PENDING",PageRequest.of(0,20)))try{commerce.process(event.messageId);}catch(RuntimeException error){tx.executeWithoutResult(status->{var row=events.lock(event.messageId).orElseThrow();if(row.status.equals("PENDING")&&++row.attempts>=5)row.status="FAILED";});}
  for(var reply:replies.due(Instant.now(),PageRequest.of(0,20)))send(reply.id);
 }
 record Outgoing(UUID id,UUID lease,String messageId,String phone,String text,UUID payment,UUID customer){}
 private void send(UUID id){Outgoing outgoing=tx.execute(status->{var r=replies.lock(id).orElseThrow();if(!Set.of("PENDING","SENDING").contains(r.status)||r.nextAttemptAt.isAfter(Instant.now()))return null;r.status="SENDING";r.attempts++;r.leaseId=UUID.randomUUID();r.nextAttemptAt=Instant.now().plusSeconds(120);return new Outgoing(r.id,r.leaseId,r.messageId,cipher.decrypt("WA_REPLY_PHONE:"+r.messageId,r.encryptedPhone),cipher.decrypt("WA_REPLY_TEXT:"+r.messageId,r.encryptedText),r.paymentId,r.customerId);});if(outgoing==null)return;
  try{String text=outgoing.text();if(outgoing.payment()!=null)text+=demo.enabled()?"\nPagamento fictício: abra Meus pedidos no ambiente de demonstração.":"\nCheckout seguro: "+checkout.create(outgoing.payment(),outgoing.customer()).checkoutUrl();
   if(demo.enabled())demo.capture("WHATSAPP_BOT",outgoing.phone(),text);
   else{String token=commerce.setting("WHATSAPP_ACCESS_TOKEN",""),phoneId=commerce.setting("WHATSAPP_PHONE_NUMBER_ID","");if(token.isBlank()||!phoneId.matches("[0-9]{1,40}"))throw new IllegalStateException("Canal não configurado.");String version=commerce.setting("WHATSAPP_GRAPH_VERSION","v23.0");if(!version.matches("v[0-9]{1,3}\\.[0-9]{1,2}"))throw new IllegalStateException("Versão do canal inválida.");http.post().uri("https://graph.facebook.com/{version}/{phoneId}/messages",version,phoneId).headers(h->h.setBearerAuth(token)).contentType(org.springframework.http.MediaType.APPLICATION_JSON).body(Map.of("messaging_product","whatsapp","to",outgoing.phone(),"type","text","text",Map.of("body",text,"preview_url",false))).retrieve().toBodilessEntity();}
   finish(outgoing,true);
  }catch(RuntimeException error){finish(outgoing,false);}
 }
 private void finish(Outgoing outgoing,boolean success){tx.executeWithoutResult(status->{var r=replies.lock(outgoing.id()).orElseThrow();if(!outgoing.lease().equals(r.leaseId))return;r.status=success?"SENT":r.attempts>=8?"REVIEW":"PENDING";r.nextAttemptAt=Instant.now().plusSeconds(Math.min(900,5L<<Math.min(7,r.attempts)));});}
}
