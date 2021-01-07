package RandomGenerator;

import Scooter.ScooterError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class RandomScooterErrorGenerator {
    private static final String characters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final HashMap<Integer, String> errorMessages;

    static {
        errorMessages = new HashMap<>();

        errorMessages.put(1222, "Punctured tire");
        errorMessages.put(1223, "Out of gas");
        errorMessages.put(1589, "Blocked exhaust system");
        errorMessages.put(2211, "Leaking oil");
        errorMessages.put(2302, "Broken steering axle");
        errorMessages.put(3004, "Malfunctioning starter motor");
    }

    public static ScooterError generateScooterError(){
        double xRange = 0.121616;
        double yRange = 0.054169;
        double xMin = 6.510863;
        double yMin = 53.198403;
        Random r = new Random();

        double randomX = xMin + xRange * r.nextDouble();
        double randomY = yMin + yRange * r.nextDouble();

        List<Integer> errorCodes = new ArrayList<>(errorMessages.keySet());
        int errorCode = errorCodes.get(r.nextInt(errorCodes.size()));
        String errorMessage = errorMessages.get(errorCode);

        String id = randomString(10);
        LocalDateTime timeOfError = LocalDateTime.now();

        return new ScooterError(id, errorCode, errorMessage, "BROKEN", timeOfError, randomX, randomY);
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
