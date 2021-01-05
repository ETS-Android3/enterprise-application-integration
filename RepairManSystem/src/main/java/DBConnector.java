import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class DBConnector {
    private MongoClient mongoClient = null;
    private MongoDatabase database = null;
    private MongoCollection<Document> scooterCollection;

    public DBConnector(String ip, int port, String databaseToUse, String scooterCollection){
        String address = "mongodb://" + ip + ":" + port;
        this.mongoClient = MongoClients.create(address);
        this.database = mongoClient.getDatabase(databaseToUse);
        this.scooterCollection = database.getCollection(scooterCollection);
    }

    public void storeScooterError(ScooterError se){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        String json = null;
        try {
            json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(se);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Document doc = Document.parse(json);
        scooterCollection.insertOne(doc);
        System.out.println("stored a scooter");
    }

    public void close(){
        mongoClient.close();
    }

}
