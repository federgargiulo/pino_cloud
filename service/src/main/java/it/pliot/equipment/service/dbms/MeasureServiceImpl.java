package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.MeasureIO;
import it.pliot.equipment.io.RoleIO;
import it.pliot.equipment.repository.MeasureRepository;
import it.pliot.equipment.repository.RoleRepository;
import it.pliot.equipment.service.business.MeasureServices;
import it.pliot.equipment.service.business.RoleServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.MeasureUtils;
import it.pliot.equipment.service.dbms.util.RoleUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class MeasureServiceImpl extends BaseServiceImpl<MeasureIO, String> implements MeasureServices {

    @Autowired
    private MeasureRepository repo;

    @Override
    public JpaRepository getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return MeasureUtils.instance();
    }
}
