package Network;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class Consumer {
    private MessageConsumer consumer = null;
    private Session session = null;
    private Connection connection = null;

    public Consumer(){
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://artemis:61616");

        try {
            connection = connectionFactory.createConnection("default", "default");
            connection.setClientID("maintenanceSystemConsumer");
            connection.start();

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Topic destination = session.createTopic("scooters");

            consumer = session.createDurableSubscriber(destination, "maintenanceSystemConsumer");
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public String consume(){
        Message message = null;
        try {
            message = consumer.receive(100000);
        } catch (JMSException e) {
            e.printStackTrace();
        }

        if(message instanceof TextMessage){
            TextMessage textMessage = (TextMessage) message;
            String text = null;
            try {
                text = textMessage.getText();
            } catch (JMSException e) {
                e.printStackTrace();
            }
            System.out.println("Received message: " + text);
            return text;
        } else {
            System.out.println("NOT AN INSTANCE OF TextMessage: " + message);
            return "ERROR";
        }
    }

    public void close(){
        try {
            this.consumer.close();
            this.session.close();
            this.connection.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
