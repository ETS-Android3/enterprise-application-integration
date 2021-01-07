package com.eai.scootermaintenanceapp.messaging;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.eai.scootermaintenanceapp.data.model.Region;

import org.eclipse.paho.android.service.MqttAndroidClient;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class MessagingGateway {
    private static final String LOG_TAG = MessagingGateway.class.getSimpleName();
    private static final String CLIENT_ID = "mobile_maintenance_app";
    private static final String BROKER_URL = "tcp://192.168.178.22:61616";

    private MqttAndroidClient client;

    private final Region region;
    private final MessagingMapper mapper;

    public MessagingGateway(Context context, Region region) {
        this.region = region;
        this.mapper = new MessagingMapper();

        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setAutomaticReconnect(true);
        connectOptions.setCleanSession(true);

        client = new MqttAndroidClient(context, BROKER_URL, MqttClient.generateClientId());
        try {
            client.connect(connectOptions, new IMqttActionListener() {
                @Override
                public void onSuccess(IMqttToken asyncActionToken) {
                    Log.d(LOG_TAG, "CONNECTED");

                    try {
                        client.subscribe(region.getId(), 1, new IMqttMessageListener() {
                            @Override
                            public void messageArrived(final String topic, final MqttMessage message) throws Exception {
                                Toast.makeText(context, message.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(IMqttToken asyncActionToken, Throwable e) {
                    e.printStackTrace();
                }
            });
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public Region getRegion() {
        return region;
    }
}
