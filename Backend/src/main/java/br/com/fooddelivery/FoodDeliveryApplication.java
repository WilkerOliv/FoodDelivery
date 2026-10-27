package br.com.fooddelivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@org.springframework.scheduling.annotation.EnableScheduling
public class FoodDeliveryApplication {
    public static void main(String[] args) {
        SpringApplication.run(FoodDeliveryApplication.class, args);
    }
}
