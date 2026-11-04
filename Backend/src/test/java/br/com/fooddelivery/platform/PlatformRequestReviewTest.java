package br.com.fooddelivery.platform;

import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.notifications.NotificationService;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlatformRequestReviewTest {
    @Test void reviewLocksOwnerBeforeRequestAndRefusesRepeatedDecision() {
        UUID ownerId=UUID.randomUUID(),requestId=UUID.randomUUID(),adminId=UUID.randomUUID();
        Account owner=new Account("owner@example.test","Teste","hash");
        ReflectionTestUtils.setField(owner,"id",ownerId);
        PlatformRequest request=PlatformRequest.role(owner,AccountRole.MERCHANT);
        ReflectionTestUtils.setField(request,"id",requestId);
        var requests=mock(PlatformRequestRepository.class);
        var accounts=mock(AccountRepository.class);
        var stores=mock(StoreRepository.class);
        when(requests.findAccountId(requestId)).thenReturn(Optional.of(ownerId));
        when(accounts.findByIdForUpdate(ownerId)).thenReturn(Optional.of(owner));
        when(requests.findByIdForUpdate(requestId)).thenReturn(Optional.of(request));
        when(accounts.findById(adminId)).thenReturn(Optional.of(new Account("admin@example.test","Admin","hash")));
        var controller=new PlatformRequestController(requests,accounts,stores,mock(ServiceCityRepository.class),mock(NotificationService.class));
        var auth=new JwtAuthenticationToken(Jwt.withTokenValue("test").header("alg","HS256").subject(adminId.toString()).build());
        assertEquals("APPROVED",controller.review(requestId,new PlatformRequestController.Review(true,null),auth).status());
        assertTrue(owner.getGrantedRoles().contains(AccountRole.MERCHANT));
        var sequence=inOrder(accounts,requests);
        sequence.verify(requests).findAccountId(requestId);
        sequence.verify(accounts).findByIdForUpdate(ownerId);
        sequence.verify(requests).findByIdForUpdate(requestId);
        assertThrows(IllegalArgumentException.class,()->controller.review(requestId,new PlatformRequestController.Review(false,null),auth));
        assertEquals("APPROVED",request.getStatus());
        verifyNoInteractions(stores);
    }
}
