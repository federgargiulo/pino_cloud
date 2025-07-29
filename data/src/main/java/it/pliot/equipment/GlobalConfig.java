package it.pliot.equipment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Component
public class GlobalConfig {



    private static final Logger log = LoggerFactory.getLogger(GlobalConfig.class);

    @Value("${pliot.mode:EDGE} ")
    private String mode;


    @Autowired
    private Environment environment;

    private Mode ACTIVE_MODE = null;


    @Value("${pliot.edge.client-id}")
    private String clientId;

    @Value("${pliot.server.url}" )
    private String serverUrl;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getServerUrl() {
        return serverUrl;
    }

    public void setServerUrl(String serverUrl) {
        this.serverUrl = serverUrl;
    }

    public Mode getMode(){
        if ( ACTIVE_MODE == null ) {
            for (String profileName : environment.getActiveProfiles()) {
                if( Mode.EDGE.name().equalsIgnoreCase( profileName ) )
                        ACTIVE_MODE=Mode.EDGE;
            }
            if ( ACTIVE_MODE == null )
                ACTIVE_MODE = Mode.SERVER;
        }
        return ACTIVE_MODE;
    }

    private Boolean loadEnabled = null;

    public boolean isLoadEnabled(){
        if ( loadEnabled != null ){
            loadEnabled = Boolean.TRUE;
            return loadEnabled;
        }
        String x = env.getProperty( "pliot.load_data" );
        if ( x == null ) {
            loadEnabled = Boolean.FALSE;
            return loadEnabled;
        }
        try {
            loadEnabled = Boolean.valueOf( x );
        }catch ( Exception e ){
            loadEnabled = Boolean.FALSE;
        }
        return  loadEnabled;
    }

    @Autowired
    private Environment env;

    public String getConfValue( String x , String def ){
        return env.getProperty( x );
    }

    public static void main( String args []){
        System.out.println( "main -> " + Mode.valueOf( "EDGE"));
    }
}
