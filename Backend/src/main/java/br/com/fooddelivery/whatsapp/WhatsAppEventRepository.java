package br.com.fooddelivery.whatsapp;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.util.*;
import jakarta.persistence.LockModeType;
public interface WhatsAppEventRepository extends JpaRepository<WhatsAppEvent,String>{
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select e from WhatsAppEvent e where e.messageId=:id") Optional<WhatsAppEvent> lock(@Param("id")String id);
 List<WhatsAppEvent> findByStatusOrderByCreatedAt(String status,Pageable page);
 long countByPhoneHashAndCreatedAtAfter(String hash,java.time.Instant after);
 long countByStatus(String status);
}
