package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EdgeTO;
import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.Edge;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.repository.EdgeRepository;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.security.JwtUser;
import it.pliot.equipment.security.UserContext;
import it.pliot.equipment.service.business.EdgeServices;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EdgeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;


@Component
@Transactional
public class EdgeServicesImpl extends BaseServiceImpl<EdgeTO, Edge,String>
        implements EdgeServices {
    @Autowired
    EdgeRepository edgeRepository;
    @Override
    public EdgeTO registerEdge(EdgeTO edge) {
        String id = UUID.randomUUID().toString();
        JwtUser user = UserContext.currentUser();
        edge.setId( id );
        edge.setRegistrationDttm( new Date( ) );
        edge.setTenant( user.getTenantId() );

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
