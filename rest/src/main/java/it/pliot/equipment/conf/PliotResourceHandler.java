package it.pliot.equipment.conf;

import it.pliot.equipment.GlobalConfig;
import jakarta.servlet.ServletException;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;


public class PliotResourceHandler  extends ResourceHttpRequestHandler {

    GlobalConfig config;



    public PliotResourceHandler( GlobalConfig config ){
        this.config = config;
        String idpUrl = config.getConfValue( "pliot.keycloak.url" , "http://localhost:8180");
        String realm = config.getConfValue( "pliot.keycloak.realmManaged" , "pliot_default");
        String webClientId = config.getConfValue( "pliot.keycloak.webClientId" , "webClientId_default");
        StringBuffer b = new StringBuffer();


        b.append( "     function getConfiguration() {\n ");
        b.append( "         return `{\n");
        b.append( "                     \"idpUrl\": \"").append( idpUrl ).append( "\",\n");
        b.append( "                     \"realm\":").append("\"").append( realm ).append( "\",\n");
        b.append( "                     \"clientId\":").append("\"").append( webClientId ).append( "\"\n");
        b.append( "                 }`;\n");
        b.append( "     }");
        resAsByte = b.toString().getBytes( StandardCharsets.UTF_8);

    }





    private byte[] resAsByte;

    public byte[] getBytes(){
        return  resAsByte;
    }
    private String locatePath = "/websupport/location.js";

    @Override
    public void handleRequest(HttpServletRequest request , HttpServletResponse response ) throws IOException {
        String requestUri = request.getRequestURI();
        if ( locatePath.equals( requestUri ) ){

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