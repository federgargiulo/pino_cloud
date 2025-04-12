package it.pliot.equipment.service.business;

import it.pliot.equipment.io.SyncCheckpointsTO;

import java.util.Date;

public interface SyncCheckpointsServices extends BaseServiceInterface<SyncCheckpointsTO,String> {

    public boolean updateSynckpointsService(String s ,   Date checkPoint  );

}
