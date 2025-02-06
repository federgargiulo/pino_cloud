package it.pliot.equipment.service.business;

import it.pliot.equipment.io.RoleIO;
import it.pliot.equipment.io.TenantIO;
import it.pliot.equipment.repository.RoleRepository;
import it.pliot.equipment.repository.TenantRepository;
import it.pliot.equipment.service.business.impl.BaseServiceImpl;
import it.pliot.equipment.util.BaseConvertUtil;
import it.pliot.equipment.util.RoleUtils;
import it.pliot.equipment.util.TenantUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public interface RoleServices extends BaseServiceInterface<RoleIO, String>{

}
