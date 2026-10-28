package br.com.fooddelivery.platform;

import br.com.fooddelivery.catalog.Store;
import br.com.fooddelivery.catalog.StoreRepository;
import br.com.fooddelivery.identity.*;
import br.com.fooddelivery.notifications.NotificationService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.time.Instant;
import java.util.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/v1")
public class PlatformRequestController {
    private final PlatformRequestRepository requests; private final AccountRepository accounts; private final StoreRepository stores;
    private final ServiceCityRepository cities; private final NotificationService notifications;
    public PlatformRequestController(PlatformRequestRepository requests,AccountRepository accounts,StoreRepository stores,ServiceCityRepository cities,NotificationService notifications){this.requests=requests;this.accounts=accounts;this.stores=stores;this.cities=cities;this.notifications=notifications;}

    @GetMapping("/account/platform-requests") @Transactional(readOnly=true) public List<RequestView> mine(Authentication auth){return requests.findByAccountIdOrderByCreatedAtDesc(subject(auth)).stream().map(RequestView::of).toList();}

    @PostMapping("/account/platform-requests/roles") @ResponseStatus(HttpStatus.CREATED) @Transactional
    public RequestView requestRole(@Valid @RequestBody RoleRequest body,Authentication auth){
        Account account=accounts.findByIdForUpdate(subject(auth)).orElseThrow(); if(!account.isActive())throw new IllegalArgumentException("Uma conta suspensa não pode enviar solicitações.");
        if(account.getCpf()==null)throw new IllegalArgumentException("Cadastre um CPF válido no perfil antes de solicitar outro acesso.");
        AccountRole requested=body.role(); if(requested!=AccountRole.MERCHANT&&requested!=AccountRole.COURIER)throw new IllegalArgumentException("Escolha vendedor ou entregador.");
        if(account.getRegisteredRoles().contains(requested))throw new IllegalArgumentException("Este perfil já está vinculado à sua conta. Se estiver suspenso, fale com o administrador.");
        if(requests.existsByAccountIdAndRequestTypeAndRequestedRoleAndStatus(account.getId(),"ROLE",requested,"PENDING"))throw new IllegalArgumentException("Já existe uma solicitação deste perfil em análise.");
        return RequestView.of(requests.save(PlatformRequest.role(account,requested)));
    }

    @PostMapping("/account/platform-requests/cities") @ResponseStatus(HttpStatus.CREATED) @Transactional
    public RequestView requestCity(@Valid @RequestBody CityRequest body,Authentication auth){
        Account account=accounts.findByIdForUpdate(subject(auth)).orElseThrow();if(!account.isActive())throw new IllegalArgumentException("Uma conta suspensa não pode enviar solicitações.");
        String city=body.city().trim(),state=body.state().trim().toUpperCase(Locale.ROOT),cep=body.postalCode()==null?null:body.postalCode().replaceAll("\\D","");
        if(cities.findByCityIgnoreCaseAndStateIgnoreCase(city,state).filter(ServiceCity::isEnabled).isPresent())throw new IllegalArgumentException("Esta cidade já está disponível no FoodDelivery.");
        if(requests.existsByAccountIdAndRequestTypeAndCityIgnoreCaseAndStateIgnoreCaseAndStatus(account.getId(),"CITY",city,state,"PENDING"))throw new IllegalArgumentException("Sua solicitação para esta cidade já está em análise.");
        return RequestView.of(requests.save(PlatformRequest.city(account,city,state,cep)));
    }

    @GetMapping("/admin/platform-requests") @PreAuthorize("hasRole('ADMIN')") @Transactional(readOnly=true)
    public List<RequestView> all(){return requests.findAllByOrderByCreatedAtDesc().stream().map(RequestView::of).toList();}

    @PatchMapping("/admin/platform-requests/{id}/review") @PreAuthorize("hasRole('ADMIN')") @Transactional
    public RequestView review(@PathVariable UUID id,@Valid @RequestBody Review body,Authentication auth){
        PlatformRequest request=requests.findById(id).orElseThrow(); Account reviewer=account(subject(auth));Account owner=request.getAccount();boolean approve=body.approved();
        switch(request.getRequestType()){
            case "ROLE" -> {if(approve){if(!owner.isActive())throw new IllegalArgumentException("Reative a conta antes de aprovar um novo perfil.");owner.setRole(request.getRequestedRole());accounts.save(owner);}}
            case "CITY" -> {if(approve){ServiceCity city=cities.findByCityIgnoreCaseAndStateIgnoreCase(request.getCity(),request.getState()).orElseGet(()->new ServiceCity(request.getCity(),request.getState(),request.getPostalCode()));city.enable(request.getPostalCode());cities.save(city);}}
            case "STORE" -> {Store store=request.getStore();if(store==null)throw new IllegalArgumentException("A loja desta solicitação não foi encontrada.");if(approve&&(!owner.isActive()||!owner.getGrantedRoles().contains(AccountRole.MERCHANT)))throw new IllegalArgumentException("A conta do vendedor está suspensa ou sem acesso ativo. Reative o perfil antes de aprovar a loja.");store.setApproved(approve);store.setActive(approve);stores.save(store);}
            default -> throw new IllegalArgumentException("Tipo de solicitação desconhecido.");
        }
        request.review(approve,reviewer,body.note()==null?null:body.note().trim());requests.save(request);
        notifications.send(owner,approve?"Solicitação aprovada":"Solicitação não aprovada",approvedMessage(request,approve),approve?"success":"info");
        return RequestView.of(request);
    }
    private String approvedMessage(PlatformRequest r,boolean approved){if(!approved)return "A administração analisou sua solicitação. Consulte os detalhes no painel da sua conta.";return switch(r.getRequestType()){case "ROLE"->"Seu perfil de "+(r.getRequestedRole()==AccountRole.MERCHANT?"vendedor":"entregador")+" foi liberado nesta mesma conta.";case "CITY"->"A cidade "+r.getCity()+"/"+r.getState()+" foi liberada na plataforma.";default->"A loja "+(r.getStore()==null?"solicitada":r.getStore().getName())+" foi aprovada e já pode ser configurada.";};}
    private Account account(UUID id){return accounts.findById(id).orElseThrow();} private UUID subject(Authentication a){return UUID.fromString(((Jwt)a.getPrincipal()).getSubject());}
    public record RoleRequest(@NotNull AccountRole role){} public record CityRequest(@NotBlank @Size(max=100) String city,@NotBlank @Size(min=2,max=2) String state,@Pattern(regexp="^$|\\d{5}-?\\d{3}") String postalCode){}
    public record Review(boolean approved,@Size(max=500) String note){}
    public record RequestView(UUID id,String requestType,String status,AccountRole requestedRole,String city,String state,String postalCode,UUID storeId,String storeName,String storeCnpj,UUID accountId,String accountName,String accountEmail,Instant createdAt,Instant reviewedAt,String reviewNote){static RequestView of(PlatformRequest r){Store s=r.getStore();Account a=r.getAccount();return new RequestView(r.getId(),r.getRequestType(),r.getStatus(),r.getRequestedRole(),r.getCity(),r.getState(),r.getPostalCode(),s==null?null:s.getId(),s==null?null:s.getName(),s==null?null:s.getCnpj(),a.getId(),a.getName(),a.getEmail(),r.getCreatedAt(),r.getReviewedAt(),r.getReviewNote());}}
}
