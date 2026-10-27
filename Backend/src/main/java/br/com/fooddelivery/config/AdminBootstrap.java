package br.com.fooddelivery.config;

import br.com.fooddelivery.identity.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap implements ApplicationRunner {
    private final AccountRepository accounts; private final PasswordEncoder encoder;
    @Value("${app.admin.email:}") private String email;
    @Value("${app.admin.password:}") private String password;
    public AdminBootstrap(AccountRepository accounts,PasswordEncoder encoder){this.accounts=accounts;this.encoder=encoder;}
    @Override public void run(ApplicationArguments args){
        if(email.isBlank()&&password.isBlank()) return;
        if(email.isBlank()||password.length()<16) throw new IllegalStateException("Configure APP_ADMIN_EMAIL e APP_ADMIN_PASSWORD (mínimo 16 caracteres) juntos.");
        if(!accounts.existsByEmailIgnoreCase(email)){Account admin=new Account(email.trim().toLowerCase(),"Administrador",encoder.encode(password));admin.setRole(AccountRole.ADMIN);accounts.save(admin);}
    }
}
