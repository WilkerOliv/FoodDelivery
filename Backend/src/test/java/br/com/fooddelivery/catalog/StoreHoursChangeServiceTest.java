package br.com.fooddelivery.catalog;

import br.com.fooddelivery.admin.AdminAuditService;
import br.com.fooddelivery.identity.Account;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.payments.*;
import br.com.fooddelivery.reservations.*;
import tools.jackson.databind.ObjectMapper;
import java.time.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreHoursChangeServiceTest {
    final UUID ownerId=UUID.randomUUID(),storeId=UUID.randomUUID();
    final Account owner=new Account("owner@example.test","Vendedor","hash"),customer=new Account("customer@example.test","Cliente","hash");
    final Store store=new Store(owner,"Loja",null,"Rua","Barbearias");
    final StoreRepository stores=mock(StoreRepository.class);final ReservationRepository reservations=mock(ReservationRepository.class);
    final ReservationSlotRepository slots=mock(ReservationSlotRepository.class);final PaymentRepository payments=mock(PaymentRepository.class);
    final NotificationService notifications=mock(NotificationService.class);final StoreOperatingHours hours=new StoreOperatingHours(tools.jackson.databind.json.JsonMapper.builder().findAndAddModules().build());
    final StoreHoursChangeService service=new StoreHoursChangeService(stores,hours,reservations,slots,payments,notifications,mock(AdminAuditService.class));
    StoreHoursChangeServiceTest(){ReflectionTestUtils.setField(owner,"id",ownerId);ReflectionTestUtils.setField(store,"id",storeId);store.setTimezone("UTC");when(stores.findByIdForUpdate(storeId)).thenReturn(Optional.of(store));}
    Reservation reservation(boolean payAfter){var start=Instant.parse("2030-01-02T10:00:00Z");var r=new Reservation(store,customer,start,UUID.randomUUID(),"Corte",2500,null,payAfter);ReflectionTestUtils.setField(r,"id",UUID.randomUUID());when(reservations.lockFutureForHours(eq(storeId),any(),any())).thenReturn(List.of(r));return r;}
    StoreHoursChangeService.ChangeRequest request(String action,List<UUID> ids,String revision){return new StoreHoursChangeService.ChangeRequest(false,Map.of(),List.of(new StoreOperatingHours.ExceptionHours(LocalDate.parse("2030-01-02"),true,null,null,"Observação privada")),null,action,ids,revision);}
    StoreHoursChangeService.Preview preview(){return service.preview(storeId,ownerId,request(null,null,null));}
    @Test void requiresExplicitDecisionAndDoesNotChangeSchedule(){reservation(true);assertThrows(IllegalStateException.class,()->service.save(storeId,ownerId,request(null,null,null)));assertEquals("[]",store.getHoursExceptionsJson());verifyNoInteractions(notifications);}
    @Test void keepPreservesReservationAndPendingPayment(){var r=reservation(false);var p=new Payment(r);when(payments.findByReservationId(r.getId())).thenReturn(Optional.of(p));var preview=preview();var result=service.save(storeId,ownerId,request("KEEP",List.of(r.getId()),preview.revision()));assertEquals(1,result.changeSummary().kept());assertTrue(r.isOperatingHoursOverride());assertEquals(ReservationStatus.PENDING_PAYMENT,r.getStatus());assertEquals(PaymentStatus.PENDING,p.getStatus());verify(slots,never()).deleteByReservationId(any());assertFalse(r.getOperatingHoursNotice().contains("Observação privada"));}
    @Test void cancellingPaidReservationRequestsRefundAndReleasesSlot(){var r=reservation(true);var p=new Payment(r);p.confirm("test");when(payments.findByReservationId(r.getId())).thenReturn(Optional.of(p));var preview=preview();var result=service.save(storeId,ownerId,request("CANCEL",List.of(r.getId()),preview.revision()));assertEquals(1,result.changeSummary().cancelled());assertEquals(ReservationStatus.CANCELLED,r.getStatus());assertEquals(PaymentStatus.REFUND_PENDING,p.getStatus());assertNotNull(p.getRefundIdempotencyKey());verify(slots).deleteByReservationId(r.getId());}
    @Test void cancellingUnpaidReservationClosesCheckout(){var r=reservation(false);var p=new Payment(r);when(payments.findByReservationId(r.getId())).thenReturn(Optional.of(p));var preview=preview();service.save(storeId,ownerId,request("CANCEL",List.of(r.getId()),preview.revision()));assertEquals(ReservationStatus.CANCELLED,r.getStatus());assertEquals(PaymentStatus.FAILED,p.getStatus());}
    @Test void inProgressServiceIsKeptEvenWhenCancelWasChosen(){var r=reservation(true);r.change(ReservationStatus.IN_SERVICE);var preview=preview();var result=service.save(storeId,ownerId,request("CANCEL",List.of(r.getId()),preview.revision()));assertEquals(0,result.changeSummary().cancelled());assertEquals(1,result.changeSummary().kept());assertEquals(ReservationStatus.IN_SERVICE,r.getStatus());assertTrue(r.isOperatingHoursOverride());verifyNoInteractions(slots);}
    @Test void staleReservationListAndStaleRevisionAreRejected(){var r=reservation(true);var preview=preview();assertThrows(IllegalStateException.class,()->service.save(storeId,ownerId,request("CANCEL",List.of(UUID.randomUUID()),preview.revision())));assertThrows(IllegalStateException.class,()->service.save(storeId,ownerId,request("CANCEL",List.of(r.getId()),"old")));assertEquals(ReservationStatus.CONFIRMED,r.getStatus());assertEquals("[]",store.getHoursExceptionsJson());}
    @Test void anotherMerchantCannotPreviewOrChangeHours(){assertThrows(AccessDeniedException.class,()->service.preview(storeId,UUID.randomUUID(),request(null,null,null)));assertThrows(AccessDeniedException.class,()->service.save(storeId,UUID.randomUUID(),request(null,null,null)));verifyNoInteractions(reservations);}
    @Test void preservedBookingFitsAgainAndLosesOverride(){var r=reservation(true);r.keepAfterHoursChange();var open=new StoreHoursChangeService.ChangeRequest(false,Map.of(),List.of(),null,null,List.of(),null);service.save(storeId,ownerId,open);assertFalse(r.isOperatingHoursOverride());verifyNoInteractions(notifications);}
    @Test void administratorCanManageAnotherOwnersHours(){var r=reservation(true);UUID adminId=UUID.randomUUID();var preview=service.preview(storeId,adminId,request(null,null,null),true);assertEquals(1,preview.affected().size());var result=service.save(storeId,adminId,request("KEEP",List.of(r.getId()),preview.revision()),true);assertEquals(1,result.changeSummary().kept());}
    @Test void timezoneChangeCannotSilentlyMoveExistingAppointments(){reservation(true);assertThrows(IllegalStateException.class,()->service.requireSafeTimezoneChange(store,"America/Manaus"));assertDoesNotThrow(()->service.requireSafeTimezoneChange(store,"UTC"));}
    @Test void timezoneChangeWithoutAppointmentsRemainsAvailable(){when(reservations.lockFutureForHours(eq(storeId),any(),any())).thenReturn(List.of());assertDoesNotThrow(()->service.requireSafeTimezoneChange(store,"America/Manaus"));assertThrows(IllegalArgumentException.class,()->service.requireSafeTimezoneChange(store,"Invalid/Zone"));}
}
