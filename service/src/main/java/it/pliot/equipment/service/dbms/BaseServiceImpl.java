package it.pliot.equipment.service.dbms;

import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

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
    public void delete( T d ) { getRepo().delete( d );}
    public List<T> findAll(){ return getRepo().findAll(); }
    public T findByKey( K key) { return ( T ) getRepo().findById( key ); }

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
