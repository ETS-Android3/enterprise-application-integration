import Network.Network;
import Network.TestServer;
import RandomGenerator.RandomScooterErrorGenerator;
import Scooter.ScooterError;
import Serializer.Serializer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class main {

    public static void main(String[] args){
        TestServer server = new TestServer(8000);
        server.startServer();

        //generating 10 scooters and sending them to the server
        //also waits 10 seconds
        for(int i = 0; i < 10; ++i){
            ScooterError tempScooterError = RandomScooterErrorGenerator.generateScooterError();
            String jsonString = Serializer.serializeScooterError(tempScooterError);
            try {
                Network.sendToPort(jsonString, "127.0.0.1", 8000);
                Network.sendToPort("test message\n", "127.0.0.1", 8000);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
