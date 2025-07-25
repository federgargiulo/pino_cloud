package it.pliot.equipment.service.dbms;

import it.pliot.equipment.casupport.CertAndKey;
import it.pliot.equipment.io.ServerTO;
import it.pliot.equipment.model.Server;
import it.pliot.equipment.repository.ServerRepository;
import it.pliot.equipment.service.business.CaServices;
import it.pliot.equipment.service.business.SystemServices;
import it.pliot.equipment.service.dbms.util.ServerUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Transactional
public class SystemServicesImpl implements SystemServices {
    private static final String SERVER_KEY = "server";
    private static final String SERVER_KEYSTORE_PASSWORD  = "serverPassword";
    @Autowired
    ServerRepository repository;
    @Autowired
    CaServices caService ;

    @Override
    public boolean isInizialized() {
        Optional<Server> op =  repository.findById( SERVER_KEY );
        return op.isPresent();
    }
    public ServerTO inizialize(){
        Optional<Server> op =  repository.findById( SERVER_KEY );
        if ( op.isPresent() )
            return ServerUtils.instance().data2io( op.get() );

        try {
             CertAndKey certAndKey = caService.generateCA();
             String base64keystore = caService.certAsBase64String( SERVER_KEY ,SERVER_KEYSTORE_PASSWORD ,  certAndKey );
             Server s = new Server();
             s.setId( SERVER_KEY );
             s.setBase64keystore( base64keystore );
             s = repository.save( s );
             return ServerUtils.instance().data2io( s );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}
