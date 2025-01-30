package it.cogito.equipment.conf;

import it.cogito.equipment.io.EquipmentIO;
import it.cogito.equipment.service.EquipmentService;
import it.cogito.equipment.service.EquipmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDb {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Bean
    CommandLineRunner initDatabase(EquipmentServiceImpl service) {

        return args -> {
            log.info("Preloading " + service.create( new EquipmentIO( "Pump" )));
            log.info("Preloading " + service.create( new EquipmentIO("Inverter" )));
        };
    }

    @Bean
    public EquipmentService transferService() {
        return new EquipmentServiceImpl();
    }
}
