package it.pliot.equipment.service.business;

import org.springframework.data.domain.Example;

import java.util.List;

public interface BaseServiceInterface<T,K> {


    public T create(T io );

    public T update( T io );

    public T save( T io );

    public T findById( K id );

    public List<T> findAll();

    public void delete(K d );

    public List<T> saveAll(List<T> iterable );


}
