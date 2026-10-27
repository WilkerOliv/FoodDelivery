package br.com.fooddelivery.orders;
import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface OrderRepository extends JpaRepository<Order,UUID>{List<Order> findByCustomerIdOrderByCreatedAtDesc(UUID id);List<Order> findByStoreOwnerIdOrderByCreatedAtDesc(UUID id);List<Order> findByStatusAndCreatedAtBefore(OrderStatus status,java.time.Instant before);}
