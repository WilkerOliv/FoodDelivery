package br.com.fooddelivery.identity;

import java.util.Optional;
import java.util.UUID;
import java.util.Collection;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, UUID> {
    Optional<Account> findByEmailIgnoreCase(String email);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a where lower(a.email) = lower(:email)")
    Optional<Account> findByEmailForUpdate(@Param("email") String email);
    boolean existsByEmailIgnoreCase(String email);
    boolean existsByCpf(String cpf);
    Optional<Account> findByCpf(String cpf);
    List<Account> findByRoleIn(Collection<AccountRole> roles);
    List<Account> findByOperatorApplicationStatus(String status);
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select a from Account a where a.id = :id")
    Optional<Account> findByIdForUpdate(@Param("id") UUID id);
}
