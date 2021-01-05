package com.example.demo;

import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.CrudListener;

import java.util.Collection;
import java.util.List;

@Service
public class ScooterService implements CrudListener<Scooter> {

    private final ScooterRepository repository;


    public ScooterService(ScooterRepository repository) {
        this.repository = repository;
    }
    @Override
    public Collection<Scooter> findAll() {
            return repository.findAll();
    }
    @Override
    public Scooter add(Scooter scooter){
        return repository.save(scooter);
    }
    @Override
    public Scooter update(Scooter scooter){
        return repository.save(scooter);
    }
    @Override
    public void delete(Scooter scooter){
        repository.delete(scooter);
    }


}
