package it.pliot.equipment.service.business;

import it.pliot.equipment.io.UserTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserServices extends BaseServiceInterface<UserTO, String> {


    public List<UserTO> findUsersByTenant(String tenant  );

}
