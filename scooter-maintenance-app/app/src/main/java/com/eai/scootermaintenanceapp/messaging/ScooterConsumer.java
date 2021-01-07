package com.eai.scootermaintenanceapp.messaging;

import android.util.Log;

import com.eai.scootermaintenanceapp.data.model.Region;
import com.swiftmq.amqp.AMQPContext;
import com.swiftmq.amqp.v100.client.AMQPException;
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

    private final Region region;
    private final String hostName;
    private final Integer port;

    private boolean isConsuming = false;

    private Connection connection;
    private Session session;
    private Consumer consumer;

    ScooterConsumer(Region region, String hostName, Integer port) {
        this.region = region;
        this.hostName = hostName;
        this.port = port;
    }

    public void startConsuming() {
        isConsuming = true;

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    AMQPContext ctx = new AMQPContext(AMQPContext.CLIENT);

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
                                String str = ((AMQPString) payload).getValue();
                                Log.d(LOG_TAG, str);
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
        try {
            consumer.close();
            session.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
