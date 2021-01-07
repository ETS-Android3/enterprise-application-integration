package com.eai.scootermaintenanceapp.messaging;

import android.util.Log;

import androidx.lifecycle.ViewModelProvider;

import com.eai.scootermaintenanceapp.data.model.Region;
import com.eai.scootermaintenanceapp.ui.maintenance.MaintenanceActivity;
import com.eai.scootermaintenanceapp.ui.maintenance.ScooterViewModel;

public class MessagingGateway {
    private static final String HOST_NAME = "192.168.178.22";
    private static final Integer PORT = 61616;

    private final Region region;

    private ScooterConsumer scooterConsumer;
    private ScooterProducer scooterProducer;

    public MessagingGateway(MaintenanceActivity activity, Region region) {
        this.region = region;

        ScooterViewModel scooterViewModel = new ViewModelProvider(activity).get(ScooterViewModel.class);

        scooterConsumer = new ScooterConsumer(region, HOST_NAME, PORT, scooterViewModel);
        scooterConsumer.startConsuming();

//        scooterProducer = new ScooterProducer(region, HOST_NAME, PORT);
//        scooterProducer.startProducing();
    }

    public Region getRegion() {
        return region;
    }

    public void dispose() {
        scooterConsumer.close();
//        scooterProducer.close();
    }
}
