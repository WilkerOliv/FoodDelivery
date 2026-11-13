package br.com.fooddelivery.admin;

import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.identity.AccountRepository;
import br.com.fooddelivery.support.SupportTicketRepository;
import br.com.fooddelivery.support.SupportTicketStatus;
import java.util.Map;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/list-summary")
@PreAuthorize("hasRole('ADMIN')")
public class AdminListSummaryController {
    private final StoreRepository stores;
    private final AccountRepository accounts;
    private final SupportTicketRepository tickets;
    public AdminListSummaryController(StoreRepository stores, AccountRepository accounts, SupportTicketRepository tickets) {
        this.stores=stores; this.accounts=accounts; this.tickets=tickets;
    }
    @GetMapping
    @Transactional(readOnly=true)
    public Map<String, Long> summary() {
        return Map.of("stores", stores.count(), "pendingStores", stores.countByActiveAndApproved(true,false),
            "approvedStores", stores.countByActiveAndApproved(true,true), "suspendedStores", stores.countByActiveFalse(),
            "accounts", accounts.count(), "operators", accounts.countOperators(), "openTickets", tickets.countByStatusNot(SupportTicketStatus.CLOSED));
    }
}
