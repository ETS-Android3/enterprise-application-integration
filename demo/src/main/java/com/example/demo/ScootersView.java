package com.example.demo;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.springframework.stereotype.Service;
import org.vaadin.crudui.crud.impl.GridCrud;

@Route("")
public class ScootersView extends VerticalLayout {

    public ScootersView(ScooterService service){
        GridCrud<Scooter> crud = new GridCrud<>(Scooter.class, service);


        add(crud);
        setSizeFull();
    }
}
