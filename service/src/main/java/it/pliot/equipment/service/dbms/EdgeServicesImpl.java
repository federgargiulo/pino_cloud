package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.Edge;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.repository.EdgeRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EdgeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;


@Component
@Transactional
@Profile("!edge")
public class EdgeServicesImpl extends BaseServiceImpl<EdgeTO, Edge,String>
        implements EdgeServices {
    @Autowired
    EdgeRepository edgeRepository;
    @Override
    public EdgeTO registerEdge(EdgeTO edge) {
        // set id and tenant;

        return save( edge );
    }

    @Override
    public PliotJpaRepository getRepo() {
        return edgeRepository;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return EdgeUtils.instance();
    }
}
