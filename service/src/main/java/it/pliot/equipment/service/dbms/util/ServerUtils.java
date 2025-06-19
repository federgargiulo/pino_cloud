package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.ServerTO;
import it.pliot.equipment.model.Server;

public class ServerUtils extends BaseConvertUtil<Server, ServerTO>{

    private static ServerUtils INSTANCE = new ServerUtils();

    public static ServerUtils instance(){
        return INSTANCE;
    }

    @Override
    public Server io2data(ServerTO in) {
        Server out = new Server();
        out.setId( in.getId() );
        out.setBase64certificate( in.getBase64certificate() );
        out.setBase64certificate( in.getBase64keystore() );
        return out;
    }

    @Override
    public ServerTO data2io(Server in) {
        ServerTO out = new ServerTO();
        out.setId( in.getId() );
        out.setBase64certificate( in.getBase64certificate() );
        out.setBase64certificate( in.getBase64keystore() );
        return out;
    }
}
