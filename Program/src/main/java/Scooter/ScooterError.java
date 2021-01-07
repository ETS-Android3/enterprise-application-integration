package Scooter;

import java.time.LocalDateTime;

public class ScooterError {
    private String id;
    private int errorCode;
    private String errorMessage;
    private LocalDateTime timeOfError;
    private String status;
    private double xLoc;
    private double yLoc;

    public ScooterError(String id, int errorCode, String errorMessage, String status, LocalDateTime timeOfError, double xLoc, double yLoc){
        this.id = id;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
        this.timeOfError = timeOfError;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public String getId() {
        return id;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public LocalDateTime getTimeOfError() {
        return timeOfError;
    }

    public String getStatus() {
        return status;
    }

    public double getxLoc() {
        return xLoc;
    }

    public double getyLoc() {
        return yLoc;
    }
}
