package it.pliot.equipment.conf;

import it.pliot.equipment.io.EquipmentIO;
import it.pliot.equipment.service.business.api.CreateEquipmentService;
import it.pliot.equipment.service.business.api.EquipmentService;
import it.pliot.equipment.service.business.impl.EquipmentServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitDb {

    private static final Logger log = LoggerFactory.getLogger(InitDb.class);

    @Bean
    CommandLineRunner initDatabase(CreateEquipmentService service) {

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
