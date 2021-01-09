package Database;

import Data.ScooterError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class DBConnector {
    private MongoClient mongoClient = null;
    private MongoDatabase database = null;
    private MongoCollection<Document> scooterCollection;

    private ObjectMapper objectMapper;

    public DBConnector(String ip, int port, String databaseToUse, String scooterCollection){
        String address = "mongodb://" + ip + ":" + port;
        this.mongoClient = MongoClients.create(address);
        this.database = mongoClient.getDatabase(databaseToUse);
        this.scooterCollection = database.getCollection(scooterCollection);

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    public void storeScooterError(ScooterError se){
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(se);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        Document doc = Document.parse(json);
        scooterCollection.insertOne(doc);
        System.out.println("Stored scooter: " + json);
    }

     public void updateScooterError(ScooterError se) {
         String json = null;
         try {
             json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(se);
         } catch (JsonProcessingException e) {
             e.printStackTrace();
         }

         Document doc = Document.parse(json);
         scooterCollection.updateOne(eq("id", se.getId()), doc);
         System.out.println("Updated scooter: " + json);
     }

    public void close(){
        mongoClient.close();
    }

}
