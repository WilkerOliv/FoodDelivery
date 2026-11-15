package br.com.fooddelivery.catalog;

import br.com.fooddelivery.admin.AdminAuditService;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.notifications.NotificationService;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.nio.file.*;
import java.util.*;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StoreCoverTest {
    @TempDir Path directory;
    final UUID ownerId=UUID.randomUUID(),storeId=UUID.randomUUID(),submissionId=UUID.randomUUID();
    final StoreRepository stores=mock(StoreRepository.class);
    final StoreCoverSubmissionRepository submissions=mock(StoreCoverSubmissionRepository.class);
    StoreAssetController controller(){return new StoreAssetController(stores,submissions,mock(AdminAuditService.class),mock(NotificationService.class),new br.com.fooddelivery.media.LocalStoreImageStorage(directory.toString()));}
    JwtAuthenticationToken auth(UUID id){return new JwtAuthenticationToken(Jwt.withTokenValue("test").header("alg","HS256").subject(id.toString()).build());}
    Store store(){var owner=new Account("owner@example.test","Vendedor","hash");owner.setRole(AccountRole.MERCHANT);ReflectionTestUtils.setField(owner,"id",ownerId);var store=new Store(owner,"Loja",null,"Rua A","Mercados");ReflectionTestUtils.setField(store,"id",storeId);store.setApproved(true);store.setCoverImageUrl("/api/v1/store-assets/cover-old.png");return store;}
    static String png(int width,int height)throws Exception{var bytes=new ByteArrayOutputStream();ImageIO.write(new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB),"png",bytes);return "data:image/png;base64,"+Base64.getEncoder().encodeToString(bytes.toByteArray());}
    @Test void onlyExactRatioAndValidPngAreAccepted()throws Exception{
        assertTrue(StoreAssetController.sanitize(png(1200,600)).length>0);
        assertThrows(IllegalArgumentException.class,()->StoreAssetController.sanitize(png(600,600)));
        assertThrows(IllegalArgumentException.class,()->StoreAssetController.sanitize("data:image/svg+xml;base64,AAAA"));
        assertThrows(IllegalArgumentException.class,()->StoreAssetController.sanitize("data:image/png;base64,not-base64"));
    }
    @Test void submissionPreservesPublishedCoverAndSupersedesPendingImage()throws Exception{
        Store store=store();var old=new StoreCoverSubmission(store,"cover-old-pending.png");
        when(stores.findByIdForUpdate(storeId)).thenReturn(Optional.of(store));
        when(submissions.findByStoreIdAndStatus(storeId,"PENDING")).thenReturn(List.of(old));
        when(submissions.save(any())).thenAnswer(i->{var s=i.getArgument(0,StoreCoverSubmission.class);ReflectionTestUtils.setField(s,"id",submissionId);return s;});
        var result=controller().submit(storeId,new StoreAssetController.CoverRequest(png(1200,600)),auth(ownerId));
        assertEquals("PENDING",result.status());assertEquals("SUPERSEDED",old.getStatus());
        assertEquals("/api/v1/store-assets/cover-old.png",store.getCoverImageUrl());
        try(var paths=Files.list(directory)){assertEquals(1,paths.count());}
    }
    @Test void otherMerchantCannotSubmitForThisStore()throws Exception{
        when(stores.findByIdForUpdate(storeId)).thenReturn(Optional.of(store()));
        assertThrows(AccessDeniedException.class,()->controller().submit(storeId,new StoreAssetController.CoverRequest(png(1200,600)),auth(UUID.randomUUID())));
        verifyNoInteractions(submissions);
    }
    @Test void publicPathCannotExposeAnUnapprovedFile()throws Exception{
        String name="cover-"+UUID.randomUUID()+".png";Files.writeString(directory.resolve(name),"not public");
        assertEquals(404,controller().readCover(name).getStatusCode().value());
    }
    @Test void uploadQuotaRejectsBeforeWritingAnyFile()throws Exception{
        when(stores.findByIdForUpdate(storeId)).thenReturn(Optional.of(store()));
        when(submissions.countByStoreIdAndCreatedAtAfter(eq(storeId),any())).thenReturn(10L);
        var error=assertThrows(org.springframework.web.server.ResponseStatusException.class,()->controller().submit(storeId,new StoreAssetController.CoverRequest(png(1200,600)),auth(ownerId)));
        assertEquals(429,error.getStatusCode().value());
        try(var paths=Files.list(directory)){assertEquals(0,paths.count());}
    }
    @Test void unpublishedStoreOwnerCanPreviewItsApprovedCoverPrivately()throws Exception{
        var store=store();store.setApproved(false);String name="cover-"+UUID.randomUUID()+".png";
        store.setCoverImageUrl("/api/v1/store-assets/"+name);Files.write(directory.resolve(name),new byte[]{1});
        when(stores.findById(storeId)).thenReturn(Optional.of(store));
        assertEquals("no-store",controller().ownPublishedImage(storeId,auth(ownerId)).getHeaders().getCacheControl());
        assertThrows(AccessDeniedException.class,()->controller().ownPublishedImage(storeId,auth(UUID.randomUUID())));
    }
    @Test void approvalPublishesExactlyReviewedImageAndCannotBeRepeated()throws Exception{
        Store store=store();String name="cover-"+UUID.randomUUID()+".png";Files.write(directory.resolve(name),Base64.getDecoder().decode(png(1200,600).split(",")[1]));
        var submission=new StoreCoverSubmission(store,name);ReflectionTestUtils.setField(submission,"id",submissionId);
        when(submissions.findStoreId(submissionId)).thenReturn(Optional.of(storeId));when(stores.findByIdForUpdate(storeId)).thenReturn(Optional.of(store));when(submissions.findByIdForUpdate(submissionId)).thenReturn(Optional.of(submission));
        var controller=controller();assertEquals("APPROVED",controller.review(submissionId,new StoreAssetController.ReviewRequest(true,null),auth(UUID.randomUUID())).status());
        assertEquals("/api/v1/store-assets/"+name,store.getCoverImageUrl());
        assertThrows(IllegalStateException.class,()->controller.review(submissionId,new StoreAssetController.ReviewRequest(false,"Recusa tardia"),auth(UUID.randomUUID())));
    }
    @Test void rejectionRequiresReasonAndPreservesCurrentCover(){
        var store=store();var submission=new StoreCoverSubmission(store,"cover-test.png");
        assertThrows(IllegalArgumentException.class,()->submission.review(false," ",UUID.randomUUID()));
        assertEquals("PENDING",submission.getStatus());submission.review(false,"Imagem ilegível",UUID.randomUUID());
        assertEquals("REJECTED",submission.getStatus());assertEquals("Imagem ilegível",submission.getReviewNote());assertEquals("/api/v1/store-assets/cover-old.png",store.getCoverImageUrl());
    }
    @Test void supersededSubmissionCannotBeApproved(){var submission=new StoreCoverSubmission(store(),"cover-test.png");submission.supersede();assertThrows(IllegalStateException.class,()->submission.review(true,null,UUID.randomUUID()));}
}
