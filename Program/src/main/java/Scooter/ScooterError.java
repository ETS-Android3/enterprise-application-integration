package Scooter;

import java.time.LocalDate;

public class ScooterError {
    private String ID;
    private String errorMessage;
    private LocalDate timeOfError;
    private double xLoc;
    private double yLoc;

    public ScooterError(String ID, String errorMessage, LocalDate timeOfError, double xLoc, double yLoc){
        this.ID = ID;
        this.errorMessage = errorMessage;
        this.timeOfError = timeOfError;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public String getID() {
        return ID;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDate getTimeOfError() {
        return timeOfError;
    }
}
