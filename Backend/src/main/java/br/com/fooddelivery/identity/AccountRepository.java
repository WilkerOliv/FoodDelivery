package br.com.fooddelivery.identity;

import java.util.Optional;
import java.util.UUID;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByEmailIgnoreCase(String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByCpf(String cpf);
    List<Account> findByRoleIn(Collection<AccountRole> roles);
    List<Account> findByOperatorApplicationStatus(String status);
}
