package br.com.fooddelivery.admin;

import br.com.fooddelivery.platform.ServiceCity;
import br.com.fooddelivery.platform.ServiceCityRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCoverageController {
    private final ServiceCityRepository serviceCities;
    private final AdminAuditService auditService;
    public AdminCoverageController(ServiceCityRepository serviceCities, AdminAuditService auditService) {
        this.serviceCities=serviceCities;
        this.auditService=auditService;
    }
    @GetMapping("/service-cities")
    public List<ServiceCityView> serviceCities() {
        return serviceCities.findAllByOrderByCityAscStateAsc().stream().map(ServiceCityView::of).toList();
    }

    @PostMapping("/service-cities")
    @ResponseStatus(HttpStatus.CREATED)
    @org.springframework.transaction.annotation.Transactional
    public ServiceCityView createServiceCity(@Valid @RequestBody ServiceCityRequest request) {
        String city = request.city().trim();
        String state = request.state().trim().toUpperCase(Locale.ROOT);
        if (serviceCities.findByCityIgnoreCaseAndStateIgnoreCase(city, state).isPresent())
            throw new IllegalArgumentException("Esta cidade já está cadastrada. Reative ou edite o cadastro existente.");
        ServiceCity saved = serviceCities.save(new ServiceCity(city, state, request.postalCode()));
        auditService.recordCurrent("CITY_CREATED", "SERVICE_CITY", saved.getId(), "Cidade adicionada à cobertura.");
        return ServiceCityView.of(saved);
    }

    @PutMapping("/service-cities/{id}")
    @org.springframework.transaction.annotation.Transactional
    public ServiceCityView updateServiceCity(@PathVariable UUID id, @Valid @RequestBody ServiceCityRequest request) {
        ServiceCity city = serviceCities.findById(id).orElseThrow();
        String name = request.city().trim();
        String state = request.state().trim().toUpperCase(Locale.ROOT);
        serviceCities.findByCityIgnoreCaseAndStateIgnoreCase(name, state)
                .filter(existing -> !existing.getId().equals(id))
                .ifPresent(existing -> { throw new IllegalArgumentException("Já existe um cadastro para esta cidade e estado."); });
        city.update(name, state, request.postalCode(), request.enabled());
        ServiceCity saved = serviceCities.save(city);
        auditService.recordCurrent("CITY_UPDATED", "SERVICE_CITY", id, "Cidade ou estado de cobertura atualizado.");
        return ServiceCityView.of(saved);
    }

    public record ServiceCityRequest(@NotBlank @Size(max=100) String city,
                                     @NotBlank @jakarta.validation.constraints.Pattern(regexp="[A-Za-z]{2}") String state,
                                     @jakarta.validation.constraints.Pattern(regexp="^$|\\d{5}-?\\d{3}") String postalCode,
                                     boolean enabled) {}

    public record ServiceCityView(UUID id, String city, String state, String postalCode, boolean enabled) {
        static ServiceCityView of(ServiceCity city) { return new ServiceCityView(city.getId(), city.getCity(), city.getState(), city.getPostalCode(), city.isEnabled()); }
    }
}
