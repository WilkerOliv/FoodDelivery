package br.com.fooddelivery.identity;

import org.junit.jupiter.api.Test;
import java.util.*;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import static org.mockito.Mockito.*;

class ProfilePhoneSecurityTest {
    @Test void changingPhoneInvalidatesOldVerificationAndRecoveryCodes(){
        UUID id=UUID.randomUUID();var accounts=mock(AccountRepository.class);var recovery=mock(PasswordRecoveryService.class);
        Account account=new Account("test@example.test","Teste","hash");account.updateProfile("Teste","11900000000",null,null,null,null,null,null);
        when(accounts.findByIdForUpdate(id)).thenReturn(Optional.of(account));when(accounts.save(account)).thenReturn(account);
        var controller=new ProfileController(accounts,mock(org.springframework.security.crypto.password.PasswordEncoder.class),mock(AuthService.class),mock(AuthSessionCookie.class),recovery);
        var auth=new JwtAuthenticationToken(Jwt.withTokenValue("test").header("alg","HS256").subject(id.toString()).build());
        controller.update(new ProfileController.ProfileUpdate("Teste","11911111111",null,null,null,null,null,null,null),auth);
        verify(recovery).invalidatePhoneCodes(account);verify(accounts).findByIdForUpdate(id);
    }
}
