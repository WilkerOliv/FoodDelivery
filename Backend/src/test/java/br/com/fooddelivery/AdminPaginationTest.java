package br.com.fooddelivery;

import br.com.fooddelivery.admin.*;
import br.com.fooddelivery.catalog.*;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.support.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties={
 "app.security.jwt-secret=dGVzdC1vbmx5LWtleS1uZXZlci11c2UtaW4tcHJvZHVjdGlvbg==",
 "spring.datasource.url=jdbc:h2:mem:admin-pagination;MODE=PostgreSQL;DB_CLOSE_DELAY=-1",
 "spring.datasource.username=sa", "spring.datasource.password="
})
@ActiveProfiles("dev-h2")
@Transactional
class AdminPaginationTest {
 @BeforeEach void authenticate(){asRole("ADMIN");}
 @AfterEach void clear(){SecurityContextHolder.clearContext();}
 private void asRole(String role){SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("test", "unused", AuthorityUtils.createAuthorityList("ROLE_"+role)));}
 @Autowired AccountRepository accounts;
 @Autowired StoreRepository stores;
 @Autowired SupportTicketRepository tickets;
 @Autowired AdminController admin;
 @Autowired SupportTicketController support;
 @Autowired AdminListSummaryController summary;

 @Test void pagesAreBoundedOrderedAndGlobalCountsRemainAccurate(){
   long initial=accounts.count();
   for(int i=0;i<53;i++)accounts.save(new Account("page-"+i+"@example.test",String.format("Page %03d",i),"hash"));
   accounts.flush();
   var first=admin.accountPage(0,25);var second=admin.accountPage(1,25);
   assertEquals(25,first.content().size());assertEquals(25,second.content().size());
   assertEquals(initial+53,first.totalElements());
   var ids=first.content().stream().map(AdminController.AdminAccountView::id).toList();
   assertTrue(second.content().stream().noneMatch(a->ids.contains(a.id())));
   assertEquals(Math.min(50,initial+53),admin.allAccounts().size());
   assertEquals(initial+53,summary.summary().get("accounts"));
 }
 @Test void operatorsIncludeSecondaryAndSuspendedProfessionalRoles(){
   long before=accounts.countOperators();
   var owner=new Account("multi@example.test","Multi","hash");owner.setRole(AccountRole.MERCHANT);owner.setRole(AccountRole.CUSTOMER);owner.setRoleActive(AccountRole.MERCHANT,false);accounts.saveAndFlush(owner);
   assertEquals(before+1,accounts.countOperators());
   assertTrue(admin.operatorPage(0,50).content().stream().anyMatch(a->a.id().equals(owner.getId())));
 }
 @Test void storeFiltersAreAppliedBeforePagination(){
   var owner=accounts.save(new Account("owner-page@example.test","Owner","hash"));
   long before=stores.countByActiveAndApproved(true,false);
   for(int i=0;i<28;i++){var store=new Store(owner,"Paged "+i,null,"Rua A","Mercados");store.setActive(true);store.setApproved(false);stores.save(store);}
   stores.flush();
   var result=admin.storePage(1,25,"PENDING_REVIEW");
   assertEquals(before+28,result.totalElements());assertTrue(result.content().size()>=3);
   assertTrue(result.content().stream().allMatch(s->s.active()&&!s.approved()));
   assertEquals(before+28,summary.summary().get("pendingStores"));
 }
 @Test void ticketsPageAndBoundsAreEnforced(){
   var owner=accounts.save(new Account("ticket-page@example.test","Owner","hash"));
   for(int i=0;i<27;i++)tickets.save(new SupportTicket(owner,"Ticket "+i,"GENERAL"));
   tickets.flush();
   assertEquals(25,support.page(0,25).content().size());
   assertTrue(support.page(1,25).totalElements()>=27);
   assertThrows(IllegalArgumentException.class,()->admin.accountPage(-1,25));
   assertThrows(IllegalArgumentException.class,()->admin.operatorPage(0,51));
   assertThrows(IllegalArgumentException.class,()->support.page(0,0));
   assertThrows(IllegalArgumentException.class,()->admin.storePage(0,25,"INVALID"));
 }
 @Test void customerCannotReadAdministrativePagesOrSummary(){
   asRole("CUSTOMER");
   assertThrows(org.springframework.security.access.AccessDeniedException.class,()->admin.accountPage(0,25));
   assertThrows(org.springframework.security.access.AccessDeniedException.class,()->admin.storePage(0,25,"ALL"));
   assertThrows(org.springframework.security.access.AccessDeniedException.class,()->admin.operatorPage(0,25));
   assertThrows(org.springframework.security.access.AccessDeniedException.class,()->support.page(0,25));
   assertThrows(org.springframework.security.access.AccessDeniedException.class,()->summary.summary());
 }
}
