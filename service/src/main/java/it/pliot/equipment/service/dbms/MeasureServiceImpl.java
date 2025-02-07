package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.repository.MeasureRepository;
import it.pliot.equipment.service.business.MeasureServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.MeasureUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class MeasureServiceImpl extends BaseServiceImpl<MeasureTO, String> implements MeasureServices {

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
