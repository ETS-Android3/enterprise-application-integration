package com.eai.scootermaintenanceapp.messaging;

import android.util.Log;

import com.eai.scootermaintenanceapp.data.model.Region;
import com.eai.scootermaintenanceapp.data.model.Scooter;
import com.eai.scootermaintenanceapp.ui.maintenance.ScooterViewModel;
import com.swiftmq.amqp.AMQPContext;
import com.swiftmq.amqp.v100.client.Connection;
import com.swiftmq.amqp.v100.client.Consumer;
import com.swiftmq.amqp.v100.client.ExceptionListener;
import com.swiftmq.amqp.v100.client.QoS;
import com.swiftmq.amqp.v100.client.Session;
import com.swiftmq.amqp.v100.messaging.AMQPMessage;
import com.swiftmq.amqp.v100.types.AMQPString;
import com.swiftmq.amqp.v100.types.AMQPType;

public class ScooterConsumer {
    private static final String LOG_TAG = ScooterConsumer.class.getSimpleName();

    private Thread thread;

    private final Region region;
    private final String hostName;
    private final Integer port;
    private final ScooterViewModel scooterViewModel;

    private boolean isConsuming = false;

    private Connection connection;
    private Session session;
    private Consumer consumer;

    ScooterConsumer(Region region, String hostName, Integer port, ScooterViewModel scooterViewModel) {
        this.region = region;
        this.hostName = hostName;
        this.port = port;

        this.scooterViewModel = scooterViewModel;
    }

    public void startConsuming() {
        isConsuming = true;

        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AMQPContext ctx = new AMQPContext(AMQPContext.CLIENT);

                    // Authentication is disabled because required imports for the constructor below
                    //     new Connection(ctx, hostName, port, "default", "default");
                    // gave java.lang.ClassNotFoundException errors due to differences between
                    // the Java Virtual Machine (JVM) and the Dalvik Virtual Machine (DVM) that
                    // Android uses (javax is not included).
                    connection = new Connection(ctx, hostName, port, false);
                    connection.setContainerId("maintenanceAppConsumer");
                    connection.setExceptionListener(new ExceptionListener() {
                        public void onException(Exception e) {
                            e.printStackTrace();
                        }
                    });

                    connection.connect();

                    session = connection.createSession(50, 50);
                    consumer = session.createConsumer(region.getId(), 100, QoS.AT_LEAST_ONCE, false, null);

                    while (isConsuming) {
                        AMQPMessage message = consumer.receiveNoWait();
                        if (message != null) {
                            AMQPType payload = message.getAmqpValue().getValue();

                            if (payload instanceof AMQPString) {
                                String jsonString = ((AMQPString) payload).getValue();
                                Scooter scooter = MessagingMapper.jsonToScooter(jsonString);

                                Log.d(LOG_TAG, scooter.toString());

                                scooterViewModel.addScooter(scooter);
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    public void stopConsuming() {
        isConsuming = false;
    }

    public void close() {
        stopConsuming();

        try {
            if (thread != null) {
                thread.join();
            }

            if (consumer != null) {
                consumer.close();
            }

            if (session != null) {
                session.close();
            }

            if (connection != null) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
