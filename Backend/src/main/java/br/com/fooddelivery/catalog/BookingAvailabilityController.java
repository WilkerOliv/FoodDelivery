package br.com.fooddelivery.catalog;
import br.com.fooddelivery.reservations.*;
import br.com.fooddelivery.admin.AdminAuditService;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.access.AccessDeniedException;
@RestController @RequestMapping("/api/v1")
public class BookingAvailabilityController {
 private final StoreRepository stores;private final StoreProfessionalRepository professionals;private final StoreOperatingHours hours;private final StoreHoursChangeService changes;private final CatalogItemRepository items;private final ReservationSlotRepository slots;private final AdminAuditService audit;
 public BookingAvailabilityController(StoreRepository stores,StoreProfessionalRepository professionals,StoreOperatingHours hours,StoreHoursChangeService changes,CatalogItemRepository items,ReservationSlotRepository slots,AdminAuditService audit){this.stores=stores;this.professionals=professionals;this.hours=hours;this.changes=changes;this.items=items;this.slots=slots;this.audit=audit;}
 private UUID actor(Authentication a){return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());}
 private Store owned(UUID id,Authentication a,boolean admin){Store s=stores.findByIdForUpdate(id).orElseThrow();if(!admin&&!s.getOwner().getId().equals(actor(a)))throw new AccessDeniedException("Loja de outra conta.");return s;}
 private StoreProfessional professional(Store s,UUID id){var p=professionals.findById(id).orElseThrow();if(!p.getStore().getId().equals(s.getId()))throw new AccessDeniedException("Profissional de outra loja.");return p;}
 public record Policy(@Min(0)@Max(10080)int noticeMinutes,@NotNull Integer stepMinutes,@Min(-1)@Max(10080)int automaticCancellationNoticeMinutes){}
 private Policy policy(Store s){return new Policy(s.getBookingNoticeMinutes(),s.getBookingStepMinutes(),s.getAutomaticCancellationNoticeMinutes());}
 @GetMapping("/stores/{id}/booking-policy") @Transactional(readOnly=true) public Policy publicPolicy(@PathVariable UUID id){return policy(stores.findById(id).filter(Store::isAvailable).orElseThrow());}
 @GetMapping("/merchant/stores/{id}/booking-policy") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Policy merchantPolicy(@PathVariable UUID id,Authentication a){return policy(owned(id,a,false));}
 @PutMapping("/merchant/stores/{id}/booking-policy") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Policy merchantPolicySave(@PathVariable UUID id,Authentication a,@Valid @RequestBody Policy r){return save(owned(id,a,false),a,r);}
 @GetMapping("/admin/stores/{id}/booking-policy") @PreAuthorize("hasRole('ADMIN')") @Transactional public Policy adminPolicy(@PathVariable UUID id,Authentication a){return policy(owned(id,a,true));}
 @PutMapping("/admin/stores/{id}/booking-policy") @PreAuthorize("hasRole('ADMIN')") @Transactional public Policy adminPolicySave(@PathVariable UUID id,Authentication a,@Valid @RequestBody Policy r){return save(owned(id,a,true),a,r);}
 private Policy save(Store s,Authentication a,Policy r){s.configureBookingPolicy(r.noticeMinutes(),r.stepMinutes(),r.automaticCancellationNoticeMinutes());audit.record(actor(a),"BOOKING_POLICY_CHANGED","STORE",s.getId(),"Antecedência, grade e política de cancelamento atualizadas.");return policy(s);}
 @GetMapping("/merchant/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('MERCHANT')") @Transactional public Object merchantHours(@PathVariable UUID id,@PathVariable UUID pid,Authentication a){return hours.read(professional(owned(id,a,false),pid));}
 @PostMapping("/merchant/stores/{id}/professionals/{pid}/availability/preview") @PreAuthorize("hasRole('MERCHANT')") public Object merchantPreview(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r){return changes.previewProfessional(id,pid,actor(a),r,false);}
 @PutMapping("/merchant/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('MERCHANT')") public Object merchantSave(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r){return changes.saveProfessional(id,pid,actor(a),r,false);}
 @GetMapping("/admin/stores/{id}/professionals") @PreAuthorize("hasRole('ADMIN')") @Transactional public Object adminProfessionals(@PathVariable UUID id,Authentication a){owned(id,a,true);return professionals.findByStoreIdOrderByNameAsc(id).stream().map(p->Map.of("id",p.getId(),"name",p.getName(),"active",p.isActive())).toList();}
 @GetMapping("/admin/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('ADMIN')") @Transactional public Object adminHours(@PathVariable UUID id,@PathVariable UUID pid,Authentication a){return hours.read(professional(owned(id,a,true),pid));}
 @PostMapping("/admin/stores/{id}/professionals/{pid}/availability/preview") @PreAuthorize("hasRole('ADMIN')") public Object adminPreview(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r){return changes.previewProfessional(id,pid,actor(a),r,true);}
 @PutMapping("/admin/stores/{id}/professionals/{pid}/availability") @PreAuthorize("hasRole('ADMIN')") public Object adminSave(@PathVariable UUID id,@PathVariable UUID pid,Authentication a,@RequestBody StoreHoursChangeService.ChangeRequest r){return changes.saveProfessional(id,pid,actor(a),r,true);}
 public record FreeSlot(String localStartsAt,Instant startsAt,Instant endsAt,String label,List<UUID> professionalIds){}
 @GetMapping("/stores/{id}/availability") @Transactional(readOnly=true) public Object available(@PathVariable UUID id,@RequestParam UUID serviceItemId,@RequestParam LocalDate date,@RequestParam(required=false)UUID professionalId){
  Store store=stores.findById(id).filter(Store::isAvailable).orElseThrow();store.requireInPerson();var item=items.findById(serviceItemId).filter(i->i.isActive()&&i.isServiceItem()&&i.getStore().getId().equals(id)).orElseThrow();ZoneId zone=ZoneId.of(store.getTimezone());LocalDate today=LocalDate.now(zone);if(date.isBefore(today)||date.isAfter(today.plusDays(90)))throw new IllegalArgumentException("Escolha uma data entre hoje e os próximos 90 dias.");
  var active=professionals.findByStoreIdAndActiveTrueOrderByNameAsc(id);if(professionalId!=null&&active.stream().noneMatch(p->p.getId().equals(professionalId)))throw new IllegalArgumentException("Profissional indisponível.");
  Instant minimum=Instant.now().plusSeconds(store.getBookingNoticeMinutes()*60L);var occupied=slots.findAcrossInterval(id,date.atStartOfDay(zone).toInstant(),date.plusDays(2).atStartOfDay(zone).toInstant());List<FreeSlot> result=new ArrayList<>();var config=hours.read(store);Map<UUID,StoreOperatingHours.Configuration> professionalHours=new HashMap<>();active.forEach(p->professionalHours.put(p.getId(),hours.read(p)));
  for(int minute=0;minute<1440;minute+=store.getBookingStepMinutes()){var local=date.atTime(minute/60,minute%60);if(zone.getRules().getValidOffsets(local).size()!=1)continue;Instant start=local.atZone(zone).toInstant(),end=start.plusSeconds(item.getAppointmentDurationMinutes()*60L);if(start.isBefore(minimum)||!hours.fits(config,zone,start,end))continue;List<UUID> busy=occupied.stream().filter(s->s.overlaps(start,end)).map(ReservationSlot::getSlotKey).toList();List<UUID> free=active.stream().filter(p->professionalId==null||p.getId().equals(professionalId)).filter(p->!busy.contains(ReservationSlot.STORE_RESOURCE_KEY)&&!busy.contains(p.getId())&&hours.fits(professionalHours.get(p.getId()),zone,start,end)).map(StoreProfessional::getId).toList();if(active.isEmpty()?!busy.isEmpty():free.isEmpty())continue;result.add(new FreeSlot(local.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm")),start,end,local.format(DateTimeFormatter.ofPattern("HH:mm")),free));}
  return Map.of("date",date,"timezone",zone.getId(),"durationMinutes",item.getAppointmentDurationMinutes(),"policy",policy(store),"slots",result);
 }
}
