package it.pliot.equipment.service.dbms;

import it.pliot.equipment.io.PagedResultTO;
import it.pliot.equipment.model.AuditObject;
import it.pliot.equipment.repository.PliotJpaRepository;
import it.pliot.equipment.service.business.BaseServiceInterface;
import it.pliot.equipment.service.business.errors.ServiceExceptions;
import it.pliot.equipment.service.dbms.util.BaseConvertUtil;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;
@Transactional
public abstract class BaseServiceImpl<T,E,K> implements BaseServiceInterface<T,K> {

    enum DBOPERATION { CREATE , UPDATE }


    public abstract PliotJpaRepository getRepo();

    public abstract BaseConvertUtil getConverter();

    public T create(T io ){

        return saveorupdate( io , DBOPERATION.CREATE );
    }
    public T save(T io ){
        return saveorupdate( io , DBOPERATION.CREATE );
    }
    public T update( T io ){
        return saveorupdate( io , DBOPERATION.UPDATE );
    }
    public void delete( K d ) { getRepo().deleteById( d );}
    public List<T> findAll(){ return getRepo().findAll(); }
    public T findByKey( K key) { return ( T ) getRepo().findById( key ); }

    private T saveorupdate(T io , DBOPERATION op  ){
        if ( io == null )
            throw new ServiceExceptions(" create exception Object must not be null ");

        Object o = getConverter().io2data( io );
        if ( o instanceof AuditObject ) {
            Date now  = new Date();
            if (DBOPERATION.CREATE == op) {
                ((AuditObject) o).setCreatedDttm( now );
            }
            ((AuditObject) o).setUpdateDttm( now );
        }
        Object savedObject =  getRepo().save( o );
        return ( T ) getConverter().data2io( savedObject );
    }


    public T findById( K id ){

       Optional o = getRepo().findById( id );
       if(o.isEmpty()) return null;
       return ( T ) getConverter().data2io( o.get() );

    }

    public List<T> saveAll(List<T> iterable ){
        List<Object> data = getConverter().converListIO2data( iterable );
        data = getRepo().saveAllAndFlush( data ) ;

        return  getConverter().converListData2IO( data );
    }

    protected PagedResultTO findPaged( Example<E> example , Pageable paged){
       Page<Object> p =  getRepo().findAll( example , paged );
       return getConverter().convertPage( p );

    }

    protected PagedResultTO findPaged(Specification<E> spec , Pageable paged){
        Page<Object> p =  getRepo().findAll( spec  , paged );
        return getConverter().convertPage( p );

    }

}
