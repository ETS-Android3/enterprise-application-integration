package Start;

import Data.ScooterError;
import Database.DBConnector;
import Network.Consumer;
import Network.Producer;
import Network.Router;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Start {
    public static void main(String[] args){

        new Producer();

        Consumer consumer = new Consumer();

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

        DBConnector dbConnector = new DBConnector("mongodb_container", 27017, "testDB", "user");

        //infinite loop
        int i = 0;
        while(i < 1){
            String message = consumer.consume();
            if(message.equals("ERROR")){
                System.out.println("Invalid message consumed");
            } else {
                try {
                    JsonNode node = objectMapper.readTree(message);
                    String status = node.path("status").asText();

                    if (status.equals("BROKEN")) {
                        ScooterError scooterError = objectMapper.readValue(message, ScooterError.class);
                        Producer.send(Router.getRegion(scooterError), message);
                    } else {
                        String id = node.get("id").asText();
                        int errorCode = node.get("errorCode").asInt();
                        String errorMessage = node.get("failureReason").asText();

                        String timeOfErrorString = node.get("errorDate").asText();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        LocalDateTime timeOfError = LocalDateTime.parse(timeOfErrorString, formatter);

                        double xLoc = node.get("longitude").asDouble();
                        double yLoc = node.get("latitude").asDouble();

                        ScooterError scooterError = new ScooterError(id, errorCode, errorMessage, status, timeOfError, xLoc, yLoc);
                        dbConnector.storeScooterError(scooterError);
                    }
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }
            }
        }

        consumer.close();
        dbConnector.close();

    }

}
