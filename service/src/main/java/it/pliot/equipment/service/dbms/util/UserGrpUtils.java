package it.pliot.equipment.service.dbms.util;

import it.pliot.equipment.io.UserGrpTO;
import it.pliot.equipment.model.UserGrp;

public class UserGrpUtils extends BaseConvertUtil<UserGrp, UserGrpTO>{

    private static UserGrpUtils INSTANCE = new UserGrpUtils();

    public static UserGrpUtils instance(){
        return INSTANCE;
    }
    private UserGrpUtils(){}

    public UserGrpTO data2io(UserGrp data ){
        if ( data == null ) return null;
        UserGrpTO rio  = new UserGrpTO();
        rio.setGrpName( data.getGrpName() );

        rio.setDescription( data.getDescription() );
        return rio;
    }

    public UserGrp io2data(UserGrpTO rio ){
        if ( rio == null ) return null;
        UserGrp data  = new UserGrp();
        data.setGrpName( rio.getGrpName() );
        data.setDescription( rio.getDescription() );
        return data;
    }



}
