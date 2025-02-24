package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EquipmentTO;
import it.pliot.equipment.model.Equipment;
import it.pliot.equipment.model.Signal;
import it.pliot.equipment.repository.EquipmentRepository;
import it.pliot.equipment.service.business.EquipmentServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EquipmentUtls;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Transactional
public class EquipmentServiceImpl extends BaseServiceImpl<EquipmentTO,String> implements EquipmentServices {

    @Autowired
    private EquipmentRepository repo;

    @Override
    public JpaRepository getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return EquipmentUtls.instance();
    }
    public List<EquipmentTO> findByTenant(String tenantId ){
        Equipment probe = new Equipment();
        probe.setTenant( tenantId );
        Example<Equipment> example = Example.of(probe);
        List<Equipment> equipments = getRepo().findAll(example);
        return getConverter().converListData2IO( equipments );
    }
}
