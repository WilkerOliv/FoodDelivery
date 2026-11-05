package br.com.fooddelivery.identity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AdminMfaRepository extends JpaRepository<AdminMfa,UUID>{}
