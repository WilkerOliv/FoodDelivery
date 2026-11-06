package br.com.fooddelivery;

import br.com.fooddelivery.catalog.*;
import br.com.fooddelivery.delivery.*;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.orders.*;
import br.com.fooddelivery.payments.*;
import br.com.fooddelivery.reservations.*;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import static org.junit.jupiter.api.Assertions.*;

/** Opt-in only: the launcher provides a disposable PostgreSQL instance, never the application database. */
@SpringBootTest(properties={"spring.datasource.url=${QA_DATABASE_URL}","spring.datasource.username=${QA_DATABASE_USERNAME}","spring.datasource.password=${QA_DATABASE_PASSWORD}","app.security.jwt-secret=dGVzdC1vbmx5LWtleS1uZXZlci11c2UtaW4tcHJvZHVjdGlvbg=="})
@ActiveProfiles("postgres")
class PostgresConcurrencyIT {
 @Autowired org.springframework.context.ApplicationContext context;
 @Test void normalDatabaseProfileDoesNotExposePaymentSimulators(){
  assertFalse(context.getBean(br.com.fooddelivery.config.DemoEnvironment.class).enabled());
  assertTrue(context.getBeansOfType(br.com.fooddelivery.config.DemoController.class).isEmpty());
  assertTrue(context.getBeansOfType(DevelopmentPaymentController.class).isEmpty());
  assertTrue(context.getBeansOfType(DevelopmentReservationPaymentController.class).isEmpty());
 }
 @Autowired AccountRepository accounts;@Autowired StoreRepository stores;@Autowired CatalogItemRepository items;@Autowired OrderRepository orders;@Autowired PaymentRepository payments;@Autowired ReservationSlotRepository slots;@Autowired OrderService orderService;@Autowired ReservationController bookings;@Autowired PlatformTransactionManager manager;
 record Fixture(UUID customer,UUID store,UUID item){}
 Fixture fixture(boolean service){return new TransactionTemplate(manager).execute(status->{String tag=UUID.randomUUID().toString();Account owner=new Account("owner-"+tag+"@example.test","QA Owner","encoded");owner.setRole(AccountRole.MERCHANT);accounts.save(owner);Account customer=new Account("customer-"+tag+"@example.test","QA Customer","encoded");customer.setCpfOnce("52998224725");customer.updateProfile("QA Customer","11900000000","Rua QA",null,"Centro","Nantes","SP","19645000");
 // Document is unique across test fixtures; use the same existing customer after the first fixture.
 var existing=accounts.findByCpf("52998224725");customer=existing.orElseGet(()->accounts.save(newCustomer(tag)));
 Store store=new Store(owner,"QA "+tag,null,"Rua fictícia","Mercados");store.setApproved(true);store.setLocation("Nantes","SP");store.setTimezone("UTC");stores.save(store);CatalogItem item=items.save(new CatalogItem(store,service?"Serviço":"Última unidade",null,1000,service?0:1,service));return new Fixture(customer.getId(),store.getId(),item.getId());});}
 Account newCustomer(String tag){Account c=new Account("customer-"+tag+"@example.test","QA Customer","encoded");c.setCpfOnce("52998224725");c.updateProfile("QA Customer","11900000000","Rua QA",null,"Centro","Nantes","SP","19645000");return c;}
 static JwtAuthenticationToken auth(UUID id){return new JwtAuthenticationToken(Jwt.withTokenValue("qa").header("alg","HS256").subject(id.toString()).build(),List.of(new org.springframework.security.core.authority.SimpleGrantedAuthority("ROLE_CUSTOMER")));}
 int race(Runnable action)throws Exception{var start=new CountDownLatch(1);var success=new AtomicInteger();try(var pool=Executors.newFixedThreadPool(2)){List<Future<?>> tasks=new ArrayList<>();for(int i=0;i<2;i++)tasks.add(pool.submit(()->{try{start.await();action.run();success.incrementAndGet();}catch(IllegalStateException|org.springframework.dao.DataAccessException expected){}catch(InterruptedException interrupted){Thread.currentThread().interrupt();throw new RuntimeException(interrupted);}}));start.countDown();for(var task:tasks)task.get(20,TimeUnit.SECONDS);}return success.get();}
 @Test void twoPurchasesCannotBothReserveTheLastItem()throws Exception{Fixture f=fixture(false);assertEquals(1,race(()->orderService.create(f.customer(),f.store(),FulfillmentType.PICKUP,null,null,List.of(new OrderService.RequestedLine(f.item(),1)))));assertEquals(0,items.findById(f.item()).orElseThrow().getStock());}
 @Test void overlappingReservationsHaveOneWinner()throws Exception{Fixture f=fixture(true);Instant start=Instant.now().plusSeconds(86400*3).truncatedTo(java.time.temporal.ChronoUnit.MINUTES);assertEquals(1,race(()->{var authentication=auth(f.customer());org.springframework.security.core.context.SecurityContextHolder.getContext().setAuthentication(authentication);try{bookings.create(new ReservationController.Request(f.store(),f.item(),start,null,null),authentication);}finally{org.springframework.security.core.context.SecurityContextHolder.clearContext();}}));assertEquals(1,slots.occupiedResources(f.store(),start,start.plusSeconds(1800)).size());}
}
