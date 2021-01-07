package randomclasses;

import com.datastax.driver.core.ResultSet;
import org.json.JSONObject;

import javax.jms.Message;
import java.util.concurrent.TimeUnit;

public class MessageQueueAdapter implements Runnable{

    @Override
    public void run() {
        Consumer consumer = new Consumer();
        CassandraConnector connector = new CassandraConnector();
        connector.connect("127.0.0.1", null);

        //Run this if you havent inited
        connector.init();

        boolean no_errors = true;
        while(no_errors) {
            try {
                Message message = consumer.consume();
                String text = message.getBody(String.class);
                JSONObject json = new JSONObject(text);
                ResultSet result = connector.insert(
                        json.getString("timestamp"),
                        json.getInt("scooter_id"),
                        json.getString("status"),
                        json.getInt("error_code"),
                        json.getString("error_message"),
                        json.getDouble("lan"),
                        json.getDouble("lon")
                );
                System.out.println(result.toString());
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception something_went_wrong){
                System.out.println(something_went_wrong.getMessage());
                no_errors = false;
            }
        }
    }
}
