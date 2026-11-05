package br.com.fooddelivery.catalog;

import br.com.fooddelivery.admin.AdminAuditService;
import br.com.fooddelivery.notifications.*;
import br.com.fooddelivery.payments.*;
import br.com.fooddelivery.reservations.*;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Preview and commit use the store lock also held during reservation creation. */
@Service
public class StoreHoursChangeService {
    @org.springframework.beans.factory.annotation.Autowired private StoreProfessionalRepository professionals;
    private final StoreRepository stores;private final StoreOperatingHours hours;private final ReservationRepository reservations;
    private final ReservationSlotRepository slots;private final PaymentRepository payments;private final NotificationService notifications;private final AdminAuditService audit;
    public StoreHoursChangeService(StoreRepository stores,StoreOperatingHours hours,ReservationRepository reservations,ReservationSlotRepository slots,PaymentRepository payments,NotificationService notifications,AdminAuditService audit){this.stores=stores;this.hours=hours;this.reservations=reservations;this.slots=slots;this.payments=payments;this.notifications=notifications;this.audit=audit;}
    public record ChangeRequest(boolean enabled,Map<DayOfWeek,StoreOperatingHours.Interval> weekly,List<StoreOperatingHours.ExceptionHours> exceptions,Map<DayOfWeek,List<StoreOperatingHours.Interval>> weeklyShifts,String conflictAction,List<UUID> expectedAffectedIds,String revision){
        public StoreOperatingHours.Configuration configuration(){return new StoreOperatingHours.Configuration(enabled,weekly,exceptions,weeklyShifts);}
    }
    public record Affected(UUID id,String serviceName,String customerName,Instant startsAt,Instant endsAt,ReservationStatus status,boolean payAfterService){}
    public record Preview(StoreOperatingHours.Configuration configuration,String revision,List<Affected> affected){}
    public record Summary(int cancelled,int kept,int notified){}
    public record Saved(@JsonUnwrapped StoreOperatingHours.Configuration configuration,Summary changeSummary){}
    private Store owned(UUID storeId,UUID ownerId,boolean administrator){Store s=stores.findByIdForUpdate(storeId).orElseThrow();if(!administrator&&!s.getOwner().getId().equals(ownerId))throw new AccessDeniedException("Loja não pertence à sua conta.");return s;}
    private List<Reservation> future(Store store){return reservations.lockFutureForHours(store.getId(),List.of(ReservationStatus.PENDING_PAYMENT,ReservationStatus.CONFIRMED,ReservationStatus.IN_SERVICE),Instant.now());}
    /** Caller holds the store lock. Existing appointments keep their fixed instants. */
    public void requireSafeTimezoneChange(Store store,String requested){
        if(requested==null||requested.isBlank())return;
        final ZoneId zone;try{zone=ZoneId.of(requested.trim());}catch(DateTimeException invalid){throw new IllegalArgumentException("Escolha um fuso horário válido.");}
        if(!zone.equals(ZoneId.of(store.getTimezone()))&&!future(store).isEmpty())throw new IllegalStateException("Há reservas futuras ou em atendimento. Resolva esses compromissos antes de mudar o fuso da loja; os horários combinados não podem ser alterados silenciosamente.");
    }
    private static String shortName(String value){return value.codePointCount(0,value.length())<=30?value:value.substring(0,value.offsetByCodePoints(0,29))+"…";}
    private static String revision(Store store){
        String raw=store.isOperatingHoursEnabled()+"|"+store.getTimezone()+"|"+store.getWeeklyHoursJson()+"|"+store.getHoursExceptionsJson();
        try{return java.util.HexFormat.of().formatHex(java.security.MessageDigest.getInstance("SHA-256").digest(raw.getBytes(java.nio.charset.StandardCharsets.UTF_8)));}catch(java.security.NoSuchAlgorithmException impossible){throw new IllegalStateException(impossible);}
    }
    private boolean fits(Store s,StoreProfessional scoped,StoreOperatingHours.Configuration config,Reservation r){ZoneId zone=ZoneId.of(s.getTimezone());boolean storeFits=scoped==null?hours.fits(config,zone,r.getStartsAt(),r.getEndsAt()):hours.fits(s,r.getStartsAt(),r.getEndsAt());var professional=r.getProfessional();boolean professionalFits=professional==null||hours.fits(scoped==null?hours.read(professional):config,zone,r.getStartsAt(),r.getEndsAt());return storeFits&&professionalFits;}
    private List<Reservation> affected(Store s,StoreProfessional scoped,StoreOperatingHours.Configuration config,List<Reservation> future){return future.stream().filter(r->!r.isOperatingHoursOverride()&&!fits(s,scoped,config,r)).toList();}
    private StoreProfessional scopedProfessional(Store s,UUID id){var p=professionals.findById(id).orElseThrow();if(!p.getStore().getId().equals(s.getId()))throw new AccessDeniedException("Profissional de outra loja.");return p;}
    private String scopedRevision(Store s,StoreProfessional p){return p==null?revision(s):revision(s)+":"+p.isOperatingHoursEnabled()+":"+Integer.toHexString(Objects.hash(p.getWeeklyHoursJson(),p.getHoursExceptionsJson()));}
    private List<Reservation> scopedFuture(Store s,StoreProfessional p){var all=future(s);return p==null?all:all.stream().filter(r->r.getProfessional()!=null&&r.getProfessional().getId().equals(p.getId())).toList();}
    @Transactional public Preview previewProfessional(UUID storeId,UUID professionalId,UUID actor,ChangeRequest request,boolean admin){Store s=owned(storeId,actor,admin);return previewScope(s,scopedProfessional(s,professionalId),request);}
    @Transactional public Saved saveProfessional(UUID storeId,UUID professionalId,UUID actor,ChangeRequest request,boolean admin){Store s=owned(storeId,actor,admin);return saveScope(s,scopedProfessional(s,professionalId),actor,request);}
    private Preview previewScope(Store s,StoreProfessional p,ChangeRequest request){var config=hours.validate(request.configuration());return new Preview(config,scopedRevision(s,p),affected(s,p,config,scopedFuture(s,p)).stream().map(r->new Affected(r.getId(),r.getServiceName(),r.getCustomer().getName(),r.getStartsAt(),r.getEndsAt(),r.getStatus(),r.isPayAfterService())).toList());}
    @Transactional public Preview preview(UUID storeId,UUID ownerId,ChangeRequest request){return preview(storeId,ownerId,request,false);}
    @Transactional public Preview preview(UUID storeId,UUID ownerId,ChangeRequest request,boolean administrator){Store s=owned(storeId,ownerId,administrator);return previewScope(s,null,request);}
    @Transactional public Saved save(UUID storeId,UUID ownerId,ChangeRequest request){return save(storeId,ownerId,request,false);}
    @Transactional public Saved save(UUID storeId,UUID ownerId,ChangeRequest request,boolean administrator){
        Store s=owned(storeId,ownerId,administrator);return saveScope(s,null,ownerId,request);
    }
    private Saved saveScope(Store s,StoreProfessional scoped,UUID ownerId,ChangeRequest request){
        var config=hours.validate(request.configuration());List<Reservation> future=scopedFuture(s,scoped),affected=affected(s,scoped,config,future);
        if(request.revision()!=null&&!request.revision().equals(scopedRevision(s,scoped)))throw new IllegalStateException("O expediente mudou em outra sessão. Atualize a prévia antes de salvar.");
        if(!affected.isEmpty()){
            if(!List.of("KEEP","CANCEL").contains(request.conflictAction()==null?"":request.conflictAction())||request.expectedAffectedIds()==null||request.revision()==null)throw new IllegalStateException("Há reservas afetadas. Confira a prévia e escolha como tratar os clientes antes de salvar.");
            Set<UUID> expected=new HashSet<>(request.expectedAffectedIds()),actual=new HashSet<>(affected.stream().map(Reservation::getId).toList());
            if(!expected.equals(actual))throw new IllegalStateException("A agenda mudou desde a prévia. Confira novamente as reservas antes de confirmar.");
        }else if(request.expectedAffectedIds()!=null&&!request.expectedAffectedIds().isEmpty())throw new IllegalStateException("A agenda mudou desde a prévia. Atualize antes de confirmar.");
        int cancelled=0,kept=0;
        for(Reservation r:affected){
            boolean cancel="CANCEL".equals(request.conflictAction())&&r.getStatus()!=ReservationStatus.IN_SERVICE;
            Payment payment=payments.findByReservationId(r.getId()).orElse(null);
            if(cancel){r.cancelForHoursChange();slots.deleteByReservationId(r.getId());if(payment!=null){if(payment.getStatus()==PaymentStatus.PAID)payment.requestFullRefund();else if(payment.getStatus()==PaymentStatus.PENDING||payment.getStatus()==PaymentStatus.FAILED)payment.cancelPendingCheckout();}cancelled++;}
            else{r.keepAfterHoursChange();kept++;}
            String when=DateTimeFormatter.ofPattern("dd/MM/yyyy 'às' HH:mm").withZone(ZoneId.of(s.getTimezone())).format(r.getStartsAt());
            notifications.sendLinked(r.getCustomer(),cancel?"Reserva cancelada por mudança no expediente":"Seu horário foi mantido",shortName(s.getName())+" · "+shortName(r.getServiceName())+" · "+when+". "+r.getOperatingHoursNotice(),cancel?"warn":"info",NotificationTarget.RESERVATION,r.getId());
        }
        // A previously preserved reservation no longer needs an exception when it fits again.
        for(Reservation r:future)if(r.isOperatingHoursOverride()&&fits(s,scoped,config,r))r.clearHoursOverride();
        if(scoped==null)hours.save(s,config);else hours.save(scoped,config);
        if(!affected.isEmpty())notifications.sendLinked(s.getOwner(),"Expediente atualizado",cancelled+" reserva(s) cancelada(s), "+kept+" mantida(s). Os clientes afetados receberam aviso na plataforma.","info",NotificationTarget.STORE,s.getId());
        audit.record(ownerId,scoped==null?"STORE_HOURS_CHANGED":"PROFESSIONAL_HOURS_CHANGED",scoped==null?"STORE":"PROFESSIONAL",scoped==null?s.getId():scoped.getId(),"Expediente salvo; reservas canceladas: "+cancelled+"; mantidas: "+kept+".");
        return new Saved(config,new Summary(cancelled,kept,affected.size()));
    }
}
