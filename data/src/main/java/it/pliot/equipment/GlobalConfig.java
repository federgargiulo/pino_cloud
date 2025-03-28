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

    private Mode m = null;

    public Mode getMode(){
        if ( m == null ) {
            try {
                m = Mode.valueOf(mode);
            }catch ( Exception exc ){
                log.warn( " pliot.mode conf {} is not a correct value " , mode );

            }
            if ( m == null )
                m = Mode.SERVER;
        }
        return m;
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
