package it.pliot.equipment.casupport;

import it.pliot.equipment.repository.ServerRepository;
import it.pliot.equipment.repository.SystemConfigurationRepository;
import it.pliot.equipment.service.business.CaServices;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Transactional
public class CaServicesImpl implements CaServices  {


    @Autowired
    private SystemConfigurationRepository repository;

    @Autowired
    private ServerRepository serverRepository;

    private static final String CA_CN = "CN=PinoCA";
    private static final String CA_PASSWORD = "capassword";

    private CaServer caServer = new CaServer(CA_CN , CA_PASSWORD );


    @Override
    public CertAndKey generateCA() throws Exception {
        return caServer.generateCA();
    }

    public String certAsBase64String( String alias , String password,
                                                      CertAndKey cerAndKey ){
        try {
            return CaUtils.exportKeyStoreTobase64Buffer( alias , password, cerAndKey );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
