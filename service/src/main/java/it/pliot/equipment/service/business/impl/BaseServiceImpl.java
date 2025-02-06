package it.pliot.equipment.service.business.impl;

import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.util.BaseConvertUtil;
import org.springframework.data.jpa.repository.JpaRepository;

public abstract class BaseServiceImpl<T,K> {

    public abstract JpaRepository getRepo();

    public abstract BaseConvertUtil getConverter();

    public T create(T io ){
        return saveorupdate( io );
    }
    public T save(T io ){
        return saveorupdate( io );
    }
    public T update( T io ){
        return saveorupdate( io );
    }

    private T saveorupdate(T io ){
        if ( io == null )
            throw new ServiceExceptions(" create exception Object must not be null ");
        Object o = getConverter().io2data( io );
        Object savedObject =  getRepo().save( o );
        return ( T ) getConverter().data2io( savedObject );
    }


    public T findById( K id ){
       Object o = getRepo().findById( id );
       return ( T ) getConverter().io2data( o );
    }
}
