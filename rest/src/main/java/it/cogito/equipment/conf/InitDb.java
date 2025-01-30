package it.cogito.equipment.conf;

import it.cogito.equipment.model.Equipment;
import it.cogito.equipment.repository.EquipmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDb {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Bean
    CommandLineRunner initDatabase(EquipmentRepository repository) {

        return args -> {
            log.info("Preloading " + repository.save(new Equipment("Pump" )));
            log.info("Preloading " + repository.save(new Equipment("Inverter" )));
        };
    }
}
