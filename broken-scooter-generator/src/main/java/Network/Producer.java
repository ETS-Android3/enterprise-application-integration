package Network;

import org.apache.activemq.artemis.jms.client.ActiveMQTopicConnectionFactory;

import javax.jms.*;

public class Producer {

    private MessageProducer producer;
    private Session session;
    private Connection connection;

    public Producer(){
        try {
            ActiveMQTopicConnectionFactory connectionFactory = new ActiveMQTopicConnectionFactory("tcp://artemis:61616");

            // Create a Connection
            this.connection = connectionFactory.createConnection("default", "default");
            this.connection.setClientID("scooterGenerator");
            this.connection.start();

            // Create a Session
            this.session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Topic destination = session.createTopic("scooters");

            // Create a MessageProducer from the Session to the Topic or Queue
            this.producer = session.createProducer(destination);
            this.producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        } catch (NullPointerException e) {
            //If null pointer exception try restarting the container by making it crash (maybe artemis not ready yet)
            throw e;
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public void sendBrokenNotification(String json)
    {
        try {
            TextMessage message = session.createTextMessage(json);

            // Tell the producer to send the message
            System.out.println("Sent message: "+ message.hashCode());
            producer.send(message);

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public void cleanup() {
        try {
            this.session.close();
            this.connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
