package br.com.fooddelivery.config;

import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.catalog.*;
import br.com.fooddelivery.platform.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component @Profile("homologacao")
public class DemoBootstrap implements ApplicationRunner {
    private final AccountRepository accounts;
    private final StoreRepository stores;
    private final CatalogItemRepository items;
    private final ServiceCityRepository cities;
    private final StoreProfessionalRepository professionals;
    private final PasswordEncoder encoder;
    public DemoBootstrap(AccountRepository accounts, StoreRepository stores, CatalogItemRepository items,
            ServiceCityRepository cities, StoreProfessionalRepository professionals, PasswordEncoder encoder) {
        this.accounts=accounts;this.stores=stores;this.items=items;this.cities=cities;this.professionals=professionals;this.encoder=encoder;
    }
    @Override @Transactional public void run(ApplicationArguments args) {
        if(accounts.count()>0)return;
        create("admin",AccountRole.ADMIN,null);
        create("cliente",AccountRole.CUSTOMER,"11144477735");
        Account merchant=create("vendedor",AccountRole.MERCHANT,"52998224725");
        create("entregador",AccountRole.COURIER,"12345678909");
        cities.save(new ServiceCity("Nantes","SP","19645000"));
        cities.save(new ServiceCity("Iepê","SP","19640000"));
        Store market=new Store(merchant,"Mercado Demonstração","Dados fictícios para homologação","Rua de Demonstração, 100","Mercados");
        market.setLocation("Nantes","SP");market.setApproved(true);stores.save(market);
        items.save(new CatalogItem(market,"Produto de teste","Produto fictício",1500,50,false));
        Store barber=new Store(merchant,"Barbearia Demonstração","Agenda de teste","Rua de Demonstração, 200","Barbearias");
        barber.setLocation("Nantes","SP");barber.setApproved(true);stores.save(barber);
        items.save(new CatalogItem(barber,"Corte de teste","Serviço fictício",2500,0,true,false,30));
        items.save(new CatalogItem(barber,"Serviço com pagamento posterior","Teste de regra por cidade",3000,0,true,true,30));
        professionals.save(new StoreProfessional(barber,"Profissional A"));
        professionals.save(new StoreProfessional(barber,"Profissional B"));
    }
    private Account create(String name, AccountRole role, String cpf) {
        Account account=new Account(name+"@example.test",name+" demonstração",encoder.encode("DemoLocal!2026#Teste"));
        account.setRole(role);if(cpf!=null)account.setCpfOnce(cpf);
        account.updateProfile(account.getName(),"11900000000","Rua de Demonstração, 1",null,"Centro","Nantes","SP","19645000");
        return accounts.save(account);
    }
}
