package br.com.fooddelivery.support;

import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.notifications.NotificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class SupportTicketController {
    private final SupportTicketRepository tickets;
    private final SupportTicketMessageRepository messages;
    private final AccountRepository accounts;
    private final NotificationService notifications;
    public SupportTicketController(SupportTicketRepository tickets, SupportTicketMessageRepository messages,
            AccountRepository accounts, NotificationService notifications) {
        this.tickets=tickets; this.messages=messages; this.accounts=accounts; this.notifications=notifications;
    }

    @GetMapping("/support/tickets/mine")
    @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER','ADMIN')")
    @Transactional(readOnly=true)
    public List<TicketView> mine(Authentication auth) {
        UUID owner=subject(auth);
        return tickets.findByAccountIdOrderByUpdatedAtDesc(owner).stream().map(this::view).toList();
    }

    @PostMapping("/support/tickets")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')")
    @Transactional
    public TicketDetail create(@Valid @RequestBody CreateRequest request, Authentication auth) {
        String category=request.category().trim().toUpperCase(Locale.ROOT);
        if (!Set.of("GENERAL","ORDER","RESERVATION","PAYMENT","ACCOUNT").contains(category)) throw new IllegalArgumentException("Escolha um assunto válido para o chamado.");
        Account owner=account(subject(auth));
        SupportTicket ticket=tickets.save(new SupportTicket(owner,request.subject().trim(),category));
        messages.save(new SupportTicketMessage(ticket,owner,request.body().trim()));
        accounts.findByRoleIn(List.of(AccountRole.ADMIN)).forEach(admin -> notifications.sendUnreadOnce(admin,
                "Novo chamado de atendimento", "Há um novo chamado aguardando análise.", "info"));
        return detail(ticket);
    }

    @GetMapping("/support/tickets/{id}")
    @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')")
    @Transactional(readOnly=true)
    public TicketDetail mineDetail(@PathVariable UUID id, Authentication auth) { return detail(owned(id,subject(auth))); }

    @PostMapping("/support/tickets/{id}/messages")
    @PreAuthorize("hasAnyRole('CUSTOMER','MERCHANT','COURIER')")
    @Transactional
    public TicketDetail reply(@PathVariable UUID id,@Valid @RequestBody MessageRequest request,Authentication auth) {
        SupportTicket ticket=owned(id,subject(auth));
        if(ticket.getStatus()==SupportTicketStatus.CLOSED) ticket.setStatus(SupportTicketStatus.OPEN); else ticket.touch();
        messages.save(new SupportTicketMessage(ticket,account(subject(auth)),request.body().trim()));
        notifyAdmins(ticket);
        return detail(ticket);
    }

    @GetMapping("/admin/support-tickets")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly=true)
    public List<TicketView> all() { return tickets.findAllByOrderByUpdatedAtDesc().stream().map(this::view).toList(); }

    @GetMapping("/admin/support-tickets/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional(readOnly=true)
    public TicketDetail adminDetail(@PathVariable UUID id) { return detail(ticket(id)); }

    @PostMapping("/admin/support-tickets/{id}/messages")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TicketDetail adminReply(@PathVariable UUID id,@Valid @RequestBody MessageRequest request,Authentication auth) {
        SupportTicket ticket=ticket(id);
        if(ticket.getStatus()==SupportTicketStatus.CLOSED) throw new IllegalStateException("Reabra o chamado antes de responder.");
        ticket.touch(); messages.save(new SupportTicketMessage(ticket,account(subject(auth)),request.body().trim()));
        notifications.sendUnreadOnce(ticket.getAccount(),"Resposta do atendimento", "A equipe respondeu ao seu chamado: "+ticket.getSubject(),"info");
        return detail(ticket);
    }

    @PatchMapping("/admin/support-tickets/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public TicketView updateStatus(@PathVariable UUID id,@Valid @RequestBody StatusRequest request) {
        SupportTicket ticket=ticket(id); ticket.setStatus(request.status());
        return view(ticket);
    }

    private SupportTicket owned(UUID id,UUID owner){SupportTicket ticket=ticket(id);if(!ticket.getAccount().getId().equals(owner))throw new AccessDeniedException("Este chamado pertence a outra conta.");return ticket;}
    private SupportTicket ticket(UUID id){return tickets.findById(id).orElseThrow(()->new NoSuchElementException("Chamado não encontrado."));}
    private Account account(UUID id){return accounts.findById(id).orElseThrow();}
    private UUID subject(Authentication auth){return UUID.fromString(((Jwt)auth.getPrincipal()).getSubject());}
    private void notifyAdmins(SupportTicket ticket){accounts.findByRoleIn(List.of(AccountRole.ADMIN)).forEach(admin->notifications.sendUnreadOnce(admin,"Atualização em chamado","Uma nova mensagem foi adicionada a um chamado.","info"));}
    private TicketView view(SupportTicket ticket){
        SupportTicketMessage last=messages.findTopByTicketIdOrderByCreatedAtDesc(ticket.getId());
        return new TicketView(ticket.getId(),ticket.getSubject(),ticket.getCategory(),ticket.getStatus(),ticket.getAccount().getId(),ticket.getAccount().getName(),ticket.getAccount().getEmail(),ticket.getCreatedAt(),ticket.getUpdatedAt(),last==null?"":last.getBody(),last==null?ticket.getCreatedAt():last.getCreatedAt());
    }
    private TicketDetail detail(SupportTicket ticket){return new TicketDetail(view(ticket),messages.findByTicketIdOrderByCreatedAtAsc(ticket.getId()).stream().map(m->new MessageView(m.getId(),m.getSender().getId(),m.getSender().getName(),m.getSender().getRole().name(),m.getBody(),m.getCreatedAt())).toList());}
    public record CreateRequest(@NotBlank @Size(max=140) String subject,@NotBlank @Size(max=30) String category,@NotBlank @Size(max=2000) String body){}
    public record MessageRequest(@NotBlank @Size(max=2000) String body){}
    public record StatusRequest(@NotNull SupportTicketStatus status){}
    public record TicketView(UUID id,String subject,String category,SupportTicketStatus status,UUID accountId,String accountName,String accountEmail,java.time.Instant createdAt,java.time.Instant updatedAt,String lastMessage,java.time.Instant lastMessageAt){}
    public record MessageView(UUID id,UUID senderId,String senderName,String senderRole,String body,java.time.Instant createdAt){}
    public record TicketDetail(TicketView ticket,List<MessageView> messages){}
}
