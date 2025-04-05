package it.pliot.equipment.conf;

import it.pliot.equipment.GlobalConfig;
import it.pliot.equipment.Mode;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class PliotResourceHandler  extends ResourceHttpRequestHandler {

    GlobalConfig config;

    private static final String IDP_URL_CONF_KEY = "idpUrl";
    private static final String IDP_REALM_CONF_KEY = "realm";
    private static final String IDP_CLIENT_ID_CONF_KEY = "clientId";
    private static final String UI_MODE_CONF_KEY = "mode";




    public PliotResourceHandler( GlobalConfig config ){
        this.config = config;
        String idpUrl = config.getConfValue( "pliot.keycloak.url" , "http://localhost:8180");
        String realm = config.getConfValue( "pliot.keycloak.realmManaged" , "pliot_default");
        String webClientId = config.getConfValue( "pliot.keycloak.webClientId" , "webClientId_default");
        String mode = config.getConfValue( "pliot.mode" , Mode.SERVER.toString() );
        StringBuffer b = new StringBuffer();

        b.append( "     function getConfiguration() {\n ");
        b.append( "         return `{\n");
        append( b , IDP_URL_CONF_KEY, idpUrl , false );
        append( b , IDP_REALM_CONF_KEY, realm , false  );
        append( b , IDP_CLIENT_ID_CONF_KEY, webClientId , false );
        append( b , UI_MODE_CONF_KEY , mode , true );
        b.append( "                 }`;\n");
        b.append( "     }");
        resAsByte = b.toString().getBytes( StandardCharsets.UTF_8);

    }


    private void append( StringBuffer b , String key , String value, boolean islast ){
        b.append( "                     \"").append(key).append("\": \"").append( value );
        if ( islast )
           b.append( "\"\n");
        else
          b.append( "\",\n");

    }


    private final byte[] resAsByte;

    public byte[] getBytes(){
        return  resAsByte;
    }
    private static final String URI_PATH = "/websupport/location.js";

    @Override
    public void handleRequest(HttpServletRequest request , HttpServletResponse response ) throws IOException {
        String requestUri = request.getRequestURI();
        if ( URI_PATH.equals( requestUri ) ){
            byte[] b = getBytes();
            response.setContentType("text/plain; charset=UTF-8");
            response.setHeader(HttpHeaders.CONTENT_LENGTH, String.valueOf( b.length));
            response.getOutputStream().write( b );
            response.flushBuffer();
            return;

        }
        super.getResource(request);
    }
}