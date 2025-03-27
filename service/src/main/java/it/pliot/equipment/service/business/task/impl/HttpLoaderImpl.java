package it.pliot.equipment.service.business.task.impl;

import it.pliot.equipment.io.EquipmentPullerTO;
import it.pliot.equipment.io.MeasureTO;
import it.pliot.equipment.io.SignalTO;
import it.pliot.equipment.service.business.EquipmentPullerServices;
import it.pliot.equipment.service.business.MeasureServices;
import it.pliot.equipment.service.business.SignalServices;
import it.pliot.equipment.service.business.dto.KeyValueDTO;
import it.pliot.equipment.service.business.dto.ResponseDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.Date;
import java.util.List;

public class HttpLoaderImpl {



    private static final Logger LOGGER = LoggerFactory.getLogger(HttpLoaderImpl.class);



    public void executeRetrieve( EquipmentPullerTO eqPuller ,
                          EquipmentPullerServices pullerService ,
                          ApplicationContext context ){

        ResponseDTO response = null;
        StringBuffer buff = new StringBuffer();
        try {
            // manage the lock here
            pullerService.startPull( eqPuller.getPullerId() );

            response = callApi(eqPuller, context);
            SignalServices signalService =
                    (SignalServices) context.getBean( SignalServices.class );
            MeasureServices measureSignals =
                    ( MeasureServices ) context.getBean( MeasureServices.class );

            List<SignalTO> signals  = signalService.getSignalsByEquipmentId( eqPuller.getEquipmentId() ) ;
            List<KeyValueDTO> values  = response.getValues();
            buff.append( "received signals " );
            buff.append( values.size() );

            values.forEach( x -> {
                SignalTO signal = findOrRegister( eqPuller, signals , x  , signalService );
                MeasureTO m = new MeasureTO();
                m.setSignalId( signal.getSignalId() );
                m.setEquipmentId( eqPuller.getEquipmentId() );
                m.setTenantId( eqPuller.getTenant() );
                m.setMeasureDttm( new Date());
                m.setVal( x.getValue() );
                measureSignals.create( m );
            });


        } catch (Exception e) {
            buff.append( e.getMessage() );
        }finally {
            pullerService.endPull(eqPuller.getPullerId(),  buff.toString() );
        }

    }

    private SignalTO findOrRegister(EquipmentPullerTO eqPuller , List<SignalTO> signals, KeyValueDTO x , SignalServices signlaServices ) {
        SignalTO s = null;
        if ( signals == null || signals.isEmpty() )
            s = createSignal( eqPuller, x , signlaServices );
        else{
            for ( int i = 0 ; i < signals.size() ; i ++ ){
                SignalTO st =  signals.get( i );
                if ( st.getName().equals( x.getName() ) )
                    return st;
            }
        }
        return createSignal( eqPuller , x , signlaServices );


    }

    private SignalTO createSignal(EquipmentPullerTO eqPuller , KeyValueDTO x, SignalServices signalServices) {
        SignalTO s = SignalTO.newEmptyInstance( eqPuller.getEquipmentId() , x.getName(), eqPuller.getTenant() );
        s = signalServices.create( s );
        return s;
    }

    private static ResponseDTO callApi(EquipmentPullerTO eqPuller, ApplicationContext context) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-type", "application/json;charset=UTF-8");
        headers.set( "X-API-KEY" , eqPuller.getApiKey() );

        RestTemplateBuilder restTemplateBuilder = ( RestTemplateBuilder ) context.getBean(RestTemplateBuilder.class);
        RestTemplate restTemplate = restTemplateBuilder.connectTimeout( Duration.ofSeconds( 10 ) )
                .build();
        HttpEntity<String> entity = new HttpEntity<>(headers);

            // Effettua la chiamata GET con le intestazioni
        ResponseEntity<ResponseDTO> response = restTemplate.exchange(
                    eqPuller.getUrl() + "/" + eqPuller.getEquipmentId() , HttpMethod.GET, entity, ResponseDTO.class
            );

        ResponseDTO o = response.getBody();
        LOGGER.info( " received response " + o );
        return o;

    }


}


