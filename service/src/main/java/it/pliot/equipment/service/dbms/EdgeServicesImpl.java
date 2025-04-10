package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.EdgeTO;

import it.pliot.equipment.model.Edge;

import it.pliot.equipment.repository.EdgeRepository;
import it.pliot.equipment.repository.PliotJpaRepository;

import it.pliot.equipment.service.business.EdgeServices;

import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.EdgeUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;




@Component
@Transactional
public class EdgeServicesImpl extends BaseServiceImpl<EdgeTO, Edge,String>
        implements EdgeServices {
    @Autowired
    EdgeRepository edgeRepository;


    @Override
    public PliotJpaRepository getRepo() {
        return edgeRepository;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return EdgeUtils.instance();
    }
}
