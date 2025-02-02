package it.pliot.equipment.service;

public class ServiceExceptions extends RuntimeException{

    public ServiceExceptions(String code) {
        super( code );
    }
}
