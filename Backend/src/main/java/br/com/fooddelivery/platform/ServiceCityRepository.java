package br.com.fooddelivery.platform;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.*;
public interface ServiceCityRepository extends JpaRepository<ServiceCity,UUID>{ Optional<ServiceCity> findByCityIgnoreCaseAndStateIgnoreCase(String city,String state); List<ServiceCity> findByEnabledTrue(); List<ServiceCity> findAllByOrderByCityAscStateAsc(); }
