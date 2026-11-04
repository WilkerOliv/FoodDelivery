package br.com.fooddelivery.catalog;

import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.platform.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.test.util.ReflectionTestUtils;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CatalogOwnershipTest {
    @Test void anotherAuthenticatedMerchantCannotEditOrRemoveAnItem() {
        var owner=new Account("owner@example.test","Dono","hash");
        ReflectionTestUtils.setField(owner,"id",UUID.randomUUID());
        var store=new Store(owner,"Loja",null,"Rua A","Mercados");
        var item=new CatalogItem(store,"Produto",null,1500,10,false);
        UUID itemId=UUID.randomUUID();
        var items=mock(CatalogItemRepository.class);
        when(items.findById(itemId)).thenReturn(Optional.of(item));
        var controller=new CatalogController(mock(StoreRepository.class),items,mock(AccountRepository.class),mock(PlatformRequestRepository.class),mock(ServiceCityRepository.class),mock(StoreCategoryRepository.class),mock(StoreProfessionalRepository.class),mock(StoreOperatingHours.class));
        var auth=new JwtAuthenticationToken(Jwt.withTokenValue("test").header("alg","HS256").subject(UUID.randomUUID().toString()).build(),List.of(new SimpleGrantedAuthority("ROLE_MERCHANT")));
        var body=new CatalogController.ItemRequest("Alteração indevida",null,1,0,false,true,false,30);
        assertThrows(AccessDeniedException.class,()->controller.updateItem(itemId,body,auth));
        assertThrows(AccessDeniedException.class,()->controller.deactivateItem(itemId,auth));
        assertEquals("Produto",item.getName());
        verify(items,never()).save(any());
        verify(items,never()).deactivateOwned(any(),any());
    }
}
