package it.pliot.equipment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class EquipmentApplication {

    public static void main(String... args) {
        SpringApplication.run(EquipmentApplication.class, args);
    }
}
