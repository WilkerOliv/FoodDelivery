package br.com.fooddelivery.whatsapp;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import java.time.Instant;
import java.util.*;
import jakarta.persistence.LockModeType;
public interface WhatsAppReplyRepository extends JpaRepository<WhatsAppReply,UUID>{
 @Lock(LockModeType.PESSIMISTIC_WRITE) @Query("select r from WhatsAppReply r where r.id=:id") Optional<WhatsAppReply> lock(@Param("id")UUID id);
 @Query("select r from WhatsAppReply r where r.status in ('PENDING','SENDING') and r.nextAttemptAt < :now order by r.createdAt") List<WhatsAppReply> due(@Param("now")Instant now,Pageable page);
 long countByStatus(String status);
}
