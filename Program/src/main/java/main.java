import RandomGenerator.RandomScooterErrorGenerator;
import Scooter.ScooterError;
import Serializer.Serializer;
import Network.Producer;

public class main {

    public static void main(String[] args){

        Producer producer = new Producer();
        for (int i = 0; i < 20; ++i) {
            ScooterError tempScooterError = RandomScooterErrorGenerator.generateScooterError();
            String jsonString = Serializer.serializeScooterError(tempScooterError);
            producer.sendBrokenNotification(jsonString);
            System.out.println(jsonString);

            try {
                Thread.sleep(20000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        producer.cleanup();
    }
}
