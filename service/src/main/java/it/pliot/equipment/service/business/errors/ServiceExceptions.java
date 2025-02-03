package it.pliot.equipment.service.business.errors;

public class ServiceExceptions extends RuntimeException{

    public ServiceExceptions(String code) {
        super( code );
    }
}
