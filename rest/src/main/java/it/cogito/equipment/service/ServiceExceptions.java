package it.cogito.equipment.service;

public class ServiceExceptions extends RuntimeException{

    public ServiceExceptions(String code) {
        super( code );
    }
}
