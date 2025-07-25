package it.pliot.equipment.service.business;

import it.pliot.equipment.io.ServerTO;
import org.springframework.stereotype.Service;

@Service
public interface SystemServices  {

    public boolean isInizialized();
    public ServerTO inizialize();
}
