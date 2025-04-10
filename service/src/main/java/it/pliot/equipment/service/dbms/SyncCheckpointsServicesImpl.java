package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.io.SyncCheckpointsTO;
import it.pliot.equipment.model.Signal;
import it.pliot.equipment.model.SyncCheckpoints;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.repository.SyncCheckpointsRepository;
import it.pliot.equipment.service.business.SignalServices;
import it.pliot.equipment.service.business.SyncCheckpointsServices;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import it.pliot.equipment.service.dbms.util.SyncCheckpointsUtils;
import org.springframework.beans.factory.annotation.Autowired;

public class SyncCheckpointsServicesImpl extends BaseServiceImpl<SyncCheckpointsTO, SyncCheckpoints, String> implements SyncCheckpointsServices {

    @Autowired
    SyncCheckpointsRepository repo;

    @Override
    public PliotJpaRepository getRepo() {
        return repo;
    }

    @Override
    public BaseConvertUtil getConverter() {
        return SyncCheckpointsUtils.instance();
    }
}
