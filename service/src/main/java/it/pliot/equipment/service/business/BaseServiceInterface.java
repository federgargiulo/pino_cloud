package it.pliot.equipment.service.business;

import it.pliot.equipment.io.UserIO;

import java.util.List;

public interface BaseServiceInterface<T,K> {


    public T create(T io );

    public T update( T io );

    public T save( T io );

    public T findById( K id );

}
