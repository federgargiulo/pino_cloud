package it.pliot.equipment.service.business;

import it.pliot.equipment.io.UserIO;
import it.pliot.equipment.service.business.BaseServiceInterface;
import org.springframework.stereotype.Service;

@Service
public interface UserServices extends BaseServiceInterface<UserIO, String> {


}
