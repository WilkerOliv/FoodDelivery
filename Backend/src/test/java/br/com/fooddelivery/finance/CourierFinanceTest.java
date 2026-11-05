package br.com.fooddelivery.finance;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.admin.AdminAuditService;
import br.com.fooddelivery.notifications.NotificationService;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.access.AccessDeniedException;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.*;
class CourierFinanceTest {
 final UUID id=UUID.randomUUID();final Account courier=new Account("courier@example.test","Entregador","hash");
 final CourierCompensationRepository policies=mock(CourierCompensationRepository.class);final CourierEarningRepository earnings=mock(CourierEarningRepository.class);final CourierPayoutRepository payouts=mock(CourierPayoutRepository.class);final AccountRepository accounts=mock(AccountRepository.class);final PasswordEncoder encoder=mock(PasswordEncoder.class);final PasswordRecoveryService codes=mock(PasswordRecoveryService.class);
 final CourierFinanceService service=new CourierFinanceService(policies,earnings,payouts,accounts,encoder,codes,mock(NotificationService.class),mock(AdminAuditService.class));
 CourierFinanceTest(){ReflectionTestUtils.setField(courier,"id",id);courier.setRole(AccountRole.COURIER);courier.setCpfOnce("12345678909");when(accounts.findByIdForUpdate(id)).thenReturn(Optional.of(courier));when(accounts.findById(id)).thenReturn(Optional.of(courier));when(encoder.matches("password","hash")).thenReturn(true);when(payouts.save(any())).thenAnswer(i->{CourierPayout p=i.getArgument(0);ReflectionTestUtils.setField(p,"id",UUID.randomUUID());return p;});}
 @Test void reservedAndPaidFundsCannotBeRequestedAgain(){when(earnings.total(id)).thenReturn(1000L);when(payouts.total(id,List.of(CourierPayout.Status.REQUESTED,CourierPayout.Status.APPROVED))).thenReturn(300L);when(payouts.total(id,List.of(CourierPayout.Status.PAID))).thenReturn(200L);assertEquals(500,service.summary(id).availableCents());assertThrows(IllegalStateException.class,()->service.request(id,501,UUID.randomUUID(),"password","123456"));verifyNoInteractions(codes);}
 @Test void payoutRequiresCodeAndReturnsMaskedDestination(){when(earnings.total(id)).thenReturn(1000L);when(codes.verifyPayout(courier,"123456")).thenReturn(true);var view=service.request(id,500,UUID.randomUUID(),"password","123456");assertEquals(500,view.amountCents());assertEquals("***.***.***-09",view.destinationMasked());assertEquals(CourierPayout.Status.REQUESTED,view.status());}
 @Test void wrongCodeCommitsItsAttemptWithoutCreatingPayout(){when(earnings.total(id)).thenReturn(1000L);assertNull(service.request(id,500,UUID.randomUUID(),"password","000000"));verify(codes).verifyPayout(courier,"000000");verify(payouts,never()).save(any());}
 @Test void retryWithSameKeyIsIdempotentButAnotherAmountIsRejected(){UUID key=UUID.randomUUID();CourierPayout p=new CourierPayout(id,500,courier.getCpf(),key);when(payouts.findByCourierIdAndRequestKey(id,key)).thenReturn(Optional.of(p));assertEquals(500,service.request(id,500,key,"password","123456").amountCents());assertThrows(IllegalStateException.class,()->service.request(id,600,key,"password","123456"));verifyNoInteractions(codes);}
 @Test void incorrectPasswordCannotRequestCodeOrPayout(){assertThrows(AccessDeniedException.class,()->service.requestCode(id,"wrong"));assertThrows(AccessDeniedException.class,()->service.request(id,1,UUID.randomUUID(),"wrong","123456"));verifyNoInteractions(codes);}
 @Test void decisionsCannotSkipApprovalOrInventASecondPayment(){CourierPayout p=new CourierPayout(id,500,courier.getCpf(),UUID.randomUUID());assertThrows(IllegalStateException.class,()->p.recordPaid(id,"reference-123"));p.decide(true,id,null);p.recordPaid(id,"reference-123");assertEquals(CourierPayout.Status.PAID,p.getStatus());assertDoesNotThrow(()->p.recordPaid(id,"reference-123"));assertThrows(IllegalStateException.class,()->p.recordPaid(id,"different-123"));assertThrows(IllegalStateException.class,p::cancel);}
 @Test void refusingRequiresReasonAndApprovingDoesNotMeanPaid(){CourierPayout p=new CourierPayout(id,500,courier.getCpf(),UUID.randomUUID());assertThrows(IllegalArgumentException.class,()->p.decide(false,id,""));p.decide(true,id,null);assertEquals(CourierPayout.Status.APPROVED,p.getStatus());assertNull(p.getPaidAt());}
 @Test void unsetCompensationCannotCreateNewDeliveryOffers(){when(policies.findById(1)).thenReturn(Optional.of(new CourierCompensation()));assertThrows(IllegalStateException.class,service::requiredFee);}
 @Test void inconsistentBalanceFailsClosed(){when(payouts.total(id,List.of(CourierPayout.Status.PAID))).thenReturn(100L);assertThrows(IllegalStateException.class,()->service.summary(id));}
}
