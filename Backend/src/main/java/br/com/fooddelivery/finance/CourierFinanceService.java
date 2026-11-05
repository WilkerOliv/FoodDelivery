package br.com.fooddelivery.finance;

import br.com.fooddelivery.admin.AdminAuditService;
import br.com.fooddelivery.delivery.*;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.notifications.*;
import java.util.*;
import java.time.Instant;
import org.springframework.data.domain.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CourierFinanceService {
 private final CourierCompensationRepository policies;private final CourierEarningRepository earnings;private final CourierPayoutRepository payouts;private final AccountRepository accounts;private final PasswordEncoder encoder;private final PasswordRecoveryService verification;private final NotificationService notifications;private final AdminAuditService audit;
 public CourierFinanceService(CourierCompensationRepository policies,CourierEarningRepository earnings,CourierPayoutRepository payouts,AccountRepository accounts,PasswordEncoder encoder,PasswordRecoveryService verification,NotificationService notifications,AdminAuditService audit){this.policies=policies;this.earnings=earnings;this.payouts=payouts;this.accounts=accounts;this.encoder=encoder;this.verification=verification;this.notifications=notifications;this.audit=audit;}
 public record Policy(boolean configured,long amountCents,Instant updatedAt){}
 public record Summary(long earnedCents,long reservedCents,long paidCents,long availableCents){}
 public record PayoutView(UUID id,UUID courierId,String courierName,long amountCents,String destinationMasked,CourierPayout.Status status,Instant createdAt,Instant paidAt,String externalReference,String note){}
 public record Approved(PayoutView payout,String pixKeyType,String pixKey,String instruction){}
 @Transactional(readOnly=true) public Policy policy(){var p=policies.findById(1).orElseThrow();return new Policy(p.isConfigured(),p.getAmountCents(),p.getUpdatedAt());}
 @Transactional(readOnly=true) public long requiredFee(){Policy p=policy();if(!p.configured())throw new IllegalStateException("A administração precisa configurar a remuneração dos entregadores antes de ofertar novas entregas.");return p.amountCents();}
 private Account account(UUID id){return accounts.findByIdForUpdate(id).filter(Account::isActive).orElseThrow();}
 private Account reauthenticate(UUID id,String password){Account a=account(id);if(password==null||!encoder.matches(password,a.getPasswordHash()))throw new AccessDeniedException("Confirmação de identidade inválida.");return a;}
 @Transactional public Policy configure(UUID admin,long amount,String password){Account a=reauthenticate(admin,password);requireAdmin(a);var p=policies.lockPolicy().orElseThrow();p.configure(amount);audit.record(admin,"COURIER_COMPENSATION_CHANGED","POLICY",1,"Valor fixo das próximas entregas atualizado.");return new Policy(true,p.getAmountCents(),p.getUpdatedAt());}
 private void requireAdmin(Account account){if(!account.getGrantedRoles().contains(AccountRole.ADMIN))throw new AccessDeniedException("Administração necessária.");}
 @Transactional public void creditDelivered(DeliveryTask task){
  if(task.getStatus()!=DeliveryStatus.DELIVERED||task.getCourier()==null)throw new IllegalStateException("A entrega precisa estar concluída e atribuída.");
  account(task.getCourier().getId());
  if(earnings.existsByDeliveryId(task.getId()))return;
  Long amount=task.getCourierFeeCents();if(amount==null||amount<1)return; // Legacy deliveries never gain invented retroactive amounts.
  earnings.save(new CourierEarning(task.getId(),task.getCourier().getId(),amount));
  notifications.sendLinked(task.getCourier(),"Ganho de entrega registrado","A remuneração desta entrega foi adicionada ao seu financeiro. Você pode solicitar o repasse do saldo disponível.","success",NotificationTarget.FINANCE,null);
 }
 @Transactional(readOnly=true) public Summary summary(UUID courier){long earned=earnings.total(courier),reserved=payouts.total(courier,List.of(CourierPayout.Status.REQUESTED,CourierPayout.Status.APPROVED)),paid=payouts.total(courier,List.of(CourierPayout.Status.PAID));long available=Math.subtractExact(Math.subtractExact(earned,reserved),paid);if(available<0)throw new IllegalStateException("Saldo inconsistente. Procure a administração antes de movimentar valores.");return new Summary(earned,reserved,paid,available);}
 @Transactional public void requestCode(UUID courier,String password){Account a=reauthenticate(courier,password);if(!Cpf.isValid(a.getCpf()))throw new IllegalStateException("Cadastre um CPF válido antes de solicitar repasse.");verification.requestPayoutVerification(a);}
 @Transactional public PayoutView request(UUID courier,long amount,UUID key,String password,String code){
  Account a=reauthenticate(courier,password);
  var prior=payouts.findByCourierIdAndRequestKey(courier,key);
  if(prior.isPresent()){if(prior.get().getAmountCents()!=amount)throw new IllegalStateException("Esta solicitação já foi usada com outro valor.");return view(prior.get());}
  if(!Cpf.isValid(a.getCpf()))throw new IllegalStateException("Cadastre um CPF válido antes de solicitar repasse.");
  if(amount<1||amount>summary(courier).availableCents())throw new IllegalStateException("O valor excede seu saldo disponível.");
  if(!verification.verifyPayout(a,code))return null;
  var saved=payouts.save(new CourierPayout(courier,amount,a.getCpf(),key));audit.record(courier,"COURIER_PAYOUT_REQUESTED","PAYOUT",saved.getId(),"Solicitação de repasse criada; saldo reservado.");
  return view(saved);
 }
 @Transactional public PayoutView cancel(UUID courier,UUID id){account(courier);var p=payouts.findById(id).orElseThrow();if(!p.getCourierId().equals(courier))throw new AccessDeniedException("Repasse de outra conta.");p.cancel();audit.record(courier,"COURIER_PAYOUT_CANCELLED","PAYOUT",id,"Solicitação retirada; saldo liberado.");return view(p);}
 @Transactional public Approved decide(UUID admin,UUID id,boolean approved,String note,String password){Account a=reauthenticate(admin,password);requireAdmin(a);var p=lockedPayout(id);p.decide(approved,admin,note);audit.record(admin,approved?"COURIER_PAYOUT_APPROVED":"COURIER_PAYOUT_REJECTED","PAYOUT",id,"Decisão administrativa registrada, sem transferência automática.");notifications.sendLinked(accounts.findById(p.getCourierId()).orElseThrow(),approved?"Repasse aprovado":"Repasse recusado",approved?"A administração aprovou sua solicitação. O pagamento ainda precisa ser realizado e confirmado.":"Confira o motivo no financeiro. O valor foi liberado para nova solicitação.",approved?"info":"warn",NotificationTarget.FINANCE,null);return new Approved(view(p),approved?"CPF":null,approved?p.getDestinationCpf():null,"Pagamento manual externo. Confira titular e comprovante antes de registrar a conclusão.");}
 @Transactional public PayoutView paid(UUID admin,UUID id,String reference,String password){Account a=reauthenticate(admin,password);requireAdmin(a);var p=lockedPayout(id);boolean already=p.getStatus()==CourierPayout.Status.PAID;p.recordPaid(admin,reference);if(!already){audit.record(admin,"COURIER_PAYOUT_PAID","PAYOUT",id,"Comprovante de pagamento externo registrado pela administração.");notifications.sendLinked(accounts.findById(p.getCourierId()).orElseThrow(),"Repasse registrado como pago","A administração registrou o pagamento externo. Confira o crédito na sua conta; em caso de divergência, abra um chamado.","success",NotificationTarget.FINANCE,null);}return view(p);}
 private CourierPayout lockedPayout(UUID id){var p=payouts.findById(id).orElseThrow();account(p.getCourierId());return payouts.findById(id).orElseThrow();}
 @Transactional(readOnly=true) public Page<PayoutView> mine(UUID courier,int page){return payouts.findByCourierIdOrderByCreatedAtDesc(courier,PageRequest.of(Math.max(0,page),30)).map(this::view);}
 @Transactional(readOnly=true) public Page<PayoutView> all(int page){return payouts.findAllByOrderByCreatedAtDesc(PageRequest.of(Math.max(0,page),30)).map(this::view);}
 @Transactional(readOnly=true) public Page<CourierEarning> earnings(UUID courier,int page){return earnings.findByCourierIdOrderByCreatedAtDesc(courier,PageRequest.of(Math.max(0,page),30));}
 private PayoutView view(CourierPayout p){return new PayoutView(p.getId(),p.getCourierId(),accounts.findById(p.getCourierId()).orElseThrow().getName(),p.getAmountCents(),"***.***.***-"+p.getDestinationCpf().substring(9),p.getStatus(),p.getCreatedAt(),p.getPaidAt(),p.getExternalReference(),p.getDecisionNote());}
}
