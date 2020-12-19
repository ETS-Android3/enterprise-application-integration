package Network;

import org.apache.activemq.artemis.jms.client.ActiveMQTopicConnectionFactory;

import javax.jms.*;

public class Consumer {

    public static void main(String[] args) {
        (new Consumer()).consume();
    }

    private MessageConsumer consumer;
    private Session session;
    private Connection connection;

    public Consumer()
    {
        try {

            // Create a ConnectionFactory
            ActiveMQTopicConnectionFactory connectionFactory = new ActiveMQTopicConnectionFactory("tcp://localhost:61616");

            // Create a Connection
            connection = connectionFactory.createConnection("default", "default");
            connection.setClientID("employee management system");
            connection.start();

            // Create a Session
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Topic destination = session.createTopic("TEST.FOO");

            // Create a MessageConsumer from the Session to the Topic or Queue
            consumer = session.createDurableSubscriber(destination, "employee management system");
        }  catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }

    }

    public void consume(){
         try {
            // Wait for a message
            Message message = consumer.receive(10000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;
                String text = textMessage.getText();
                System.out.println("Received: " + text);
            } else {
                System.out.println("Received: " + message);
            }
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }

    public void cleanup() {
        try {
            this.consumer.close();
            this.session.close();
            this.connection.close();
        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
