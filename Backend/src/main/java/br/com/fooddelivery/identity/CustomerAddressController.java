package br.com.fooddelivery.identity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/account/addresses")
@PreAuthorize("hasRole('CUSTOMER')")
public class CustomerAddressController {
    private final CustomerAddressRepository addresses;
    private final AccountRepository accounts;
    public CustomerAddressController(CustomerAddressRepository addresses, AccountRepository accounts) {
        this.addresses = addresses; this.accounts = accounts;
    }

    @GetMapping @Transactional(readOnly = true)
    public List<AddressView> list(Authentication auth) {
        UUID owner = subject(auth);
        return addresses.findByAccountIdOrderByDefaultAddressDescIdAsc(owner).stream().map(AddressView::from).toList();
    }

    @PostMapping @ResponseStatus(HttpStatus.CREATED) @Transactional
    public AddressView create(@Valid @RequestBody AddressRequest request, Authentication auth) {
        UUID owner = subject(auth);
        Account account = accounts.findById(owner).orElseThrow();
        boolean makeDefault = request.defaultAddress() || !addresses.existsByAccountId(owner);
        if (makeDefault) addresses.findByAccountIdOrderByDefaultAddressDescIdAsc(owner).forEach(a -> a.setDefaultAddress(false));
        CustomerAddress saved = addresses.save(new CustomerAddress(account, label(request.label()), request.addressLine().trim(),
                clean(request.addressComplement()), request.neighborhood().trim(), request.city().trim(),
                request.state().trim().toUpperCase(), normalizeCep(request.postalCode()), makeDefault));
        if (makeDefault) updateProfileAddress(account, saved);
        return AddressView.from(saved);
    }

    @PutMapping("/{id}") @Transactional
    public AddressView update(@PathVariable UUID id, @Valid @RequestBody AddressRequest request, Authentication auth) {
        UUID owner = subject(auth);
        CustomerAddress address = addresses.findByIdAndAccountId(id, owner).orElseThrow();
        address.update(label(request.label()), request.addressLine().trim(), clean(request.addressComplement()),
                request.neighborhood().trim(), request.city().trim(), request.state().trim().toUpperCase(), normalizeCep(request.postalCode()));
        if (request.defaultAddress()) setDefault(owner, address);
        if (address.isDefaultAddress()) updateProfileAddress(address.getAccount(), address);
        return AddressView.from(address);
    }

    @PatchMapping("/{id}/default") @Transactional
    public AddressView makeDefault(@PathVariable UUID id, Authentication auth) {
        UUID owner = subject(auth);
        CustomerAddress address = addresses.findByIdAndAccountId(id, owner).orElseThrow();
        setDefault(owner, address); updateProfileAddress(address.getAccount(), address);
        return AddressView.from(address);
    }

    @DeleteMapping("/{id}") @ResponseStatus(HttpStatus.NO_CONTENT) @Transactional
    public void delete(@PathVariable UUID id, Authentication auth) {
        UUID owner = subject(auth);
        CustomerAddress address = addresses.findByIdAndAccountId(id, owner).orElseThrow();
        boolean wasDefault = address.isDefaultAddress();
        addresses.delete(address);
        if (wasDefault) {
            CustomerAddress next = addresses.findByAccountIdOrderByDefaultAddressDescIdAsc(owner).stream().findFirst().orElse(null);
            if (next != null) { next.setDefaultAddress(true); updateProfileAddress(address.getAccount(), next); }
            else { address.getAccount().updateAddress(null, null, null, null, null, null); accounts.save(address.getAccount()); }
        }
    }

    private void setDefault(UUID owner, CustomerAddress selected) {
        addresses.findByAccountIdOrderByDefaultAddressDescIdAsc(owner).forEach(a -> a.setDefaultAddress(a.getId().equals(selected.getId())));
    }
    private void updateProfileAddress(Account account, CustomerAddress address) {
        account.updateAddress(address.getAddressLine(), address.getAddressComplement(), address.getNeighborhood(),
                address.getCity(), address.getState(), address.getPostalCode());
        accounts.save(account);
    }
    private static String clean(String value) { return value == null || value.isBlank() ? null : value.trim(); }
    private static String label(String value) { String clean = clean(value); return clean == null ? "Meu endereço" : clean; }
    private static String normalizeCep(String value) { return value.replaceAll("\\D", ""); }
    private static UUID subject(Authentication auth) { return UUID.fromString(((Jwt) auth.getPrincipal()).getSubject()); }

    public record AddressRequest(@Size(max=40) String label, @NotBlank @Size(max=250) String addressLine,
            @Size(max=120) String addressComplement, @NotBlank @Size(max=120) String neighborhood,
            @NotBlank @Size(max=100) String city, @NotBlank @Size(min=2,max=2) String state,
            @NotBlank @Pattern(regexp="\\d{5}-?\\d{3}") String postalCode, boolean defaultAddress) {}
    public record AddressView(UUID id, String label, String addressLine, String addressComplement,
            String neighborhood, String city, String state, String postalCode, boolean defaultAddress) {
        static AddressView from(CustomerAddress a) { return new AddressView(a.getId(), a.getLabel(), a.getAddressLine(),
                a.getAddressComplement(), a.getNeighborhood(), a.getCity(), a.getState(), a.getPostalCode(), a.isDefaultAddress()); }
    }
}
