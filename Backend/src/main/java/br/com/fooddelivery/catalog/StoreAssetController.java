package br.com.fooddelivery.catalog;

import br.com.fooddelivery.admin.AdminAuditService;
import br.com.fooddelivery.notifications.NotificationService;
import br.com.fooddelivery.notifications.NotificationTarget;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.io.*;
import java.time.Instant;
import java.util.*;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import br.com.fooddelivery.media.StoreImageStorage;
import org.springframework.http.*;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.*;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1")
public class StoreAssetController {
    public static final int WIDTH=1200,HEIGHT=600,MAX_IMAGE_BYTES=5*1024*1024;
    private final StoreRepository stores;
    private final StoreCoverSubmissionRepository submissions;
    private final AdminAuditService audit;
    private final NotificationService notifications;
    private final StoreImageStorage storage;
    public StoreAssetController(StoreRepository stores,StoreCoverSubmissionRepository submissions,
            AdminAuditService audit,NotificationService notifications,
            StoreImageStorage storage){
        this.stores=stores;this.submissions=submissions;this.audit=audit;this.notifications=notifications;
        this.storage=storage;
    }

    @PostMapping("/merchant/stores/{id}/cover") @PreAuthorize("hasRole('MERCHANT')")
    @ResponseStatus(HttpStatus.CREATED) @Transactional
    public CoverView submit(@PathVariable UUID id,@Valid @RequestBody CoverRequest request,Authentication auth){
        Store store=stores.findByIdForUpdate(id).orElseThrow();requireOwner(store,auth);
        if(submissions.countByStoreIdAndCreatedAtAfter(id,Instant.now().minusSeconds(3600))>=10)
            throw new org.springframework.web.server.ResponseStatusException(HttpStatus.TOO_MANY_REQUESTS,"Limite de 10 envios por hora para esta loja. Tente mais tarde.");
        byte[] clean=sanitize(request.dataUrl());
        String name="cover-"+UUID.randomUUID()+".png";
        try{storage.put(name,clean);}
        catch(IOException ex){throw new IllegalStateException("Não foi possível salvar a imagem. Tente novamente.");}
        if(TransactionSynchronizationManager.isSynchronizationActive())TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization(){
            @Override public void afterCompletion(int status){if(status!=STATUS_COMMITTED)try{storage.delete(name);}catch(IOException ignored){}}
        });
        submissions.findByStoreIdAndStatus(id,"PENDING").forEach(StoreCoverSubmission::supersede);
        return view(submissions.save(new StoreCoverSubmission(store,name)));
    }

    @GetMapping("/merchant/stores/{id}/cover-submission") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true)
    public CoverState mine(@PathVariable UUID id,Authentication auth){
        Store store=stores.findById(id).orElseThrow();requireOwner(store,auth);
        return new CoverState(store.getCoverImageUrl()==null?null:"/api/v1/merchant/stores/"+id+"/published-cover",submissions.findFirstByStoreIdOrderByCreatedAtDesc(id).map(this::view).orElse(null));
    }

    @GetMapping("/merchant/stores/{id}/published-cover") @PreAuthorize("hasRole('MERCHANT')") @Transactional(readOnly=true)
    public ResponseEntity<byte[]> ownPublishedImage(@PathVariable UUID id,Authentication auth){
        Store store=stores.findById(id).orElseThrow();requireOwner(store,auth);
        String url=store.getCoverImageUrl(),prefix="/api/v1/store-assets/";
        return url!=null&&url.startsWith(prefix)?file(url.substring(prefix.length()),true):ResponseEntity.notFound().build();
    }

    @GetMapping("/admin/store-covers") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true)
    public List<CoverView> pending(){return submissions.findByStatusOrderByCreatedAtAsc("PENDING").stream().map(this::view).toList();}

    @PatchMapping("/admin/store-covers/{id}/review") @PreAuthorize("hasRole('ADMIN')") @Transactional
    public CoverView review(@PathVariable UUID id,@Valid @RequestBody ReviewRequest request,Authentication auth){
        UUID storeId=submissions.findStoreId(id).orElseThrow();
        Store store=stores.findByIdForUpdate(storeId).orElseThrow();
        StoreCoverSubmission submission=submissions.findByIdForUpdate(id).orElseThrow();
        try{if(storage.read(submission.getFileName()).isEmpty())throw new IllegalStateException("Arquivo indisponível. Solicite um novo envio ao vendedor.");}
        catch(IOException error){throw new IllegalStateException("Não foi possível consultar a imagem. Tente novamente.");}
        submission.review(request.approved(),request.note(),subject(auth));
        if(request.approved())store.setCoverImageUrl("/api/v1/store-assets/"+submission.getFileName());
        audit.record(subject(auth),request.approved()?"STORE_COVER_APPROVED":"STORE_COVER_REJECTED","STORE",storeId,"Imagem de capa analisada pela administração.");
        notifications.sendLinked(store.getOwner(),request.approved()?"Capa aprovada":"Revise a capa da sua loja",
            request.approved()?"A nova capa de "+store.getName()+" foi aprovada. Ela aparece aos clientes quando a loja está publicada.":"A capa de "+store.getName()+" não foi aprovada. Motivo: "+submission.getReviewNote(),
            request.approved()?"success":"info",NotificationTarget.STORE,storeId);
        return view(submission);
    }

    @GetMapping("/store-cover-submissions/{id}/image") @Transactional(readOnly=true)
    public ResponseEntity<byte[]> privateImage(@PathVariable UUID id,Authentication auth){
        StoreCoverSubmission submission=submissions.findById(id).orElseThrow();
        if(!auth.getAuthorities().stream().anyMatch(a->a.getAuthority().equals("ROLE_ADMIN")))requireOwner(submission.getStore(),auth);
        return file(submission.getFileName(),true);
    }

    @GetMapping("/store-assets/{fileName}") @Transactional(readOnly=true)
    public ResponseEntity<byte[]> readCover(@PathVariable String fileName){
        if(!validName(fileName)||stores.findByCoverImageUrl("/api/v1/store-assets/"+fileName).filter(Store::isAvailable).isEmpty())return ResponseEntity.notFound().build();
        return file(fileName,false);
    }

    private ResponseEntity<byte[]> file(String name,boolean confidential){
        if(!validName(name))return ResponseEntity.notFound().build();
        try {
            var image=storage.read(name);
            if(image.isEmpty())return ResponseEntity.notFound().build();
            return ResponseEntity.ok().cacheControl(confidential?CacheControl.noStore():CacheControl.noCache())
                .header("X-Content-Type-Options","nosniff").contentType(MediaType.IMAGE_PNG).body(image.get());
        } catch(IOException error) { throw new IllegalStateException("Imagem temporariamente indisponível. Tente novamente."); }
    }
    private static boolean validName(String name){return name.matches("cover-[0-9a-fA-F-]{36}\\.png");}
    private static UUID subject(Authentication auth){return UUID.fromString(((Jwt)auth.getPrincipal()).getSubject());}
    private static void requireOwner(Store store,Authentication auth){if(!store.getOwner().getId().equals(subject(auth)))throw new AccessDeniedException("Loja não pertence à sua conta.");}
    private CoverView view(StoreCoverSubmission submission){Store s=submission.getStore();return new CoverView(submission.getId(),s.getId(),s.getName(),s.getOwner().getName(),submission.getStatus(),submission.getReviewNote(),submission.getCreatedAt(),submission.getReviewedAt(),"/api/v1/store-cover-submissions/"+submission.getId()+"/image");}

    static byte[] sanitize(String dataUrl){
        String prefix="data:image/png;base64,";
        if(dataUrl==null||!dataUrl.startsWith(prefix)||dataUrl.length()>((MAX_IMAGE_BYTES+2L)/3)*4+prefix.length())throw new IllegalArgumentException("Envie uma imagem PNG de até 5 MB.");
        byte[] bytes;
        try{bytes=Base64.getDecoder().decode(dataUrl.substring(prefix.length()));}catch(IllegalArgumentException ex){throw new IllegalArgumentException("Arquivo de imagem inválido.");}
        if(bytes.length<8||bytes.length>MAX_IMAGE_BYTES||(bytes[0]&255)!=137||bytes[1]!=80||bytes[2]!=78||bytes[3]!=71)throw new IllegalArgumentException("Arquivo PNG inválido.");
        try(ImageInputStream input=ImageIO.createImageInputStream(new ByteArrayInputStream(bytes))){
            var readers=ImageIO.getImageReaders(input);if(!readers.hasNext())throw new IllegalArgumentException("A imagem não pôde ser lida.");
            var reader=readers.next();
            try{reader.setInput(input,true,true);if(reader.getWidth(0)!=WIDTH||reader.getHeight(0)!=HEIGHT)throw new IllegalArgumentException("A capa precisa ter 1.200 × 600 pixels, proporção 2:1. Ajuste a prévia antes de enviar.");
                var decoded=reader.read(0);var clean=new ByteArrayOutputStream();
                if(!ImageIO.write(decoded,"png",clean)||clean.size()>MAX_IMAGE_BYTES)throw new IllegalArgumentException("A capa ultrapassa 5 MB após processamento.");
                return clean.toByteArray();
            }finally{reader.dispose();}
        }catch(IOException ex){throw new IllegalArgumentException("Arquivo de imagem inválido.");}
    }
    public record CoverRequest(@NotBlank @Size(max=7_000_600) String dataUrl){}
    public record ReviewRequest(@NotNull Boolean approved,@Size(max=500) String note){}
    public record CoverState(String publishedUrl,CoverView submission){}
    public record CoverView(UUID id,UUID storeId,String storeName,String ownerName,String status,String reviewNote,Instant createdAt,Instant reviewedAt,String imageUrl){}
}
