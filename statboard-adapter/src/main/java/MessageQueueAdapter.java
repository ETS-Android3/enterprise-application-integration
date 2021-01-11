import com.datastax.driver.core.ResultSet;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.json.JSONObject;

import javax.jms.Message;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.TimeUnit;

public class MessageQueueAdapter {

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        CassandraConnector connector = new CassandraConnector();
        connector.connect("cassandra", null);

        //Run this if you havent inited
        try {
            connector.init();
        } catch (Exception e){
            System.out.println("problem with init: " + e);
        }

        boolean no_errors = true;
        while(no_errors) {
            try {
                Message message = consumer.consume();
                if (message == null) {
                    TimeUnit.SECONDS.sleep(2);
                } else {
                    String text = message.getBody(String.class);
                    JSONObject json = new JSONObject(text);

                    String id = json.getString("id");
                    String status  = json.getString("status");
                    int errorCode = json.getInt("errorCode");
                    if (status.equals("BROKEN")) {
                        ResultSet result = connector.insert(
                                json.getString("timeOfError"),
                                id,
                                status,
                                errorCode,
                                json.getString("errorMessage"),
                                json.getDouble("yLoc"),
                                json.getDouble("xLoc")
                        );
                        System.out.println(result.toString());
                    } else {
                        ResultSet result = connector.insert(
                                json.getString("errorDate"),
                                id,
                                status,
                                errorCode,
                                json.getString("failureReason"),
                                json.getDouble("latitude"),
                                json.getDouble("longitude")
                        );
                        System.out.println(result.toString());
                        TimeUnit.SECONDS.sleep(1);
                    }
                }
            } catch (Exception something_went_wrong){
                System.out.println(something_went_wrong.getMessage());
                no_errors = false;
                System.out.println("stopping");
            }
        }
    }
}
