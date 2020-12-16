package RandomGenerator;

import Scooter.ScooterError;

import java.time.LocalDate;
import java.util.Random;

public class RandomScooterErrorGenerator {
    static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static ScooterError generateScooterError(){
        double xRange = 0.121616;
        double yRange = 0.054169;
        double xMin = 6.510863;
        double yMin = 53.198403;
        Random r = new Random();

        double randomX = xMin + xRange * r.nextDouble();
        double randomY = yMin + yRange * r.nextDouble();
        String errorMessage = "Punctured tire.";
        String id = randomString(10);
        LocalDate timeOfError = LocalDate.now();

        return new ScooterError(id, errorMessage, timeOfError, randomX, randomY);
    }

    private static String randomString(int len){
        Random r = new Random();
        StringBuilder sb = new StringBuilder(len);
        for(int i = 0; i < len; i++) {
            sb.append(characters.charAt(r.nextInt(characters.length())));
        }
        return sb.toString();
    }
}
