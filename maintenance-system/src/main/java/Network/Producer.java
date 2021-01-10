package Network;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.util.ArrayList;

public class Producer {

    private static ActiveMQConnectionFactory connectionFactory = null;
    private static Connection connection = null;
    private static Session session = null;
    private static ArrayList<Destination> destinations = null;
    private static ArrayList<MessageProducer> producers = null;

    public Producer(){
        destinations = new ArrayList<>(2);
        producers = new ArrayList<>(2);
        connectionFactory = new ActiveMQConnectionFactory("tcp://artemis:61616");
        try {
            connection = connectionFactory.createConnection("default", "default");
            connection.setClientID("maintenanceSystemProducer");

            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destinations.add(session.createQueue("north-groningen"));
            destinations.add(session.createQueue("south-groningen"));

            producers.add(session.createProducer(destinations.get(0)));
            producers.add(session.createProducer(destinations.get(1)));

            connection.start();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void send(String region, String msg){
        TextMessage message = null;

        System.out.println("Routing message to region: " + region);

        if(region.equals("north-groningen")){
            try {
                message = session.createTextMessage(msg);
                producers.get(0).send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else if(region.equals("south-groningen")){
            try {
                message = session.createTextMessage(msg);
                producers.get(1).send(message);
            } catch (JMSException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("INVALID REGION");
        }
    }
}
