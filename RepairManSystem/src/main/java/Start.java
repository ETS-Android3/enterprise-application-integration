import Network.Consumer;
import Network.Producer;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Start {
    public static void main(String[] args){

        new Producer();
        Producer.send("ng", "message");
        Producer.send("ng", "message");

        Consumer consumer = new Consumer();
        String message = consumer.consume();
        consumer.close();

        if(message.equals("ERROR")){
            System.out.println("Invalid message consumed");
        } else {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            ScooterError scooterError = null;
            try {
                scooterError = objectMapper.readValue(message, ScooterError.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            DBConnector dbConnector = new DBConnector("127.0.0.1", 27017, "testDB", "user");
            dbConnector.storeScooterError(scooterError);
            dbConnector.close();

            Producer.send(Logic.getRegion(scooterError), message);
        }
    }

}
