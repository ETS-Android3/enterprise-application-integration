import Data.ScooterError;
import Database.DBConnector;
import Network.Consumer;
import Network.Producer;
import Network.Router;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Start {
    public static void main(String[] args){

        new Producer();

        Consumer consumer = new Consumer();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        DBConnector dbConnector = new DBConnector("127.0.0.1", 27017, "testDB", "user");

        //infinite loop
        int i = 0;
        while(i < 1){
            String message = consumer.consume();
            if(message.equals("ERROR")){
                System.out.println("Invalid message consumed");
            } else {
                ScooterError scooterError = null;
                try {
                    scooterError = objectMapper.readValue(message, ScooterError.class);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
                dbConnector.storeScooterError(scooterError);

                if(scooterError.getStatus().equals("BROKEN")) {
                    Producer.send(Router.getRegion(scooterError), message);
                } //else scooter has been repaired
            }
        }

        consumer.close();
        dbConnector.close();

    }

}
