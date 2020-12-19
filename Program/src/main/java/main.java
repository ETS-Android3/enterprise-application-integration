import RandomGenerator.RandomScooterErrorGenerator;
import Scooter.ScooterError;
import Serializer.Serializer;
import Network.Producer;

public class main {

    public static void main(String[] args){

        Producer producer = new Producer();
        for(int i = 0; i < 10; ++i) {
            ScooterError tempScooterError = RandomScooterErrorGenerator.generateScooterError();
            String jsonString = Serializer.serializeScooterError(tempScooterError);
            producer.sendBrokenNotification(jsonString);
        }

        producer.cleanup();
    }
}
