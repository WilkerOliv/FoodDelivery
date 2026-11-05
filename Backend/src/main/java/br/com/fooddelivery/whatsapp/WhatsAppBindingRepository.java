package br.com.fooddelivery.whatsapp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface WhatsAppBindingRepository extends JpaRepository<WhatsAppBinding,String>{Optional<WhatsAppBinding> findByAccountId(UUID id);}
