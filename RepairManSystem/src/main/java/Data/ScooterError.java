package Data;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import java.time.LocalDate;

public class ScooterError {
    private String id;
    private String errorMessage;
    private LocalDate timeOfError;
    private String status;
    private double xLoc;
    private double yLoc;

    public ScooterError(){}

    public ScooterError(String id, String errorMessage, LocalDate timeOfError, double xLoc, double yLoc){
        this.id = id;
        this.errorMessage = errorMessage;
        this.timeOfError = timeOfError;
        this.xLoc = xLoc;
        this.yLoc = yLoc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public LocalDate getTimeOfError() {
        return timeOfError;
    }

    public void setTimeOfError(LocalDate timeOfError) {
        this.timeOfError = timeOfError;
    }

    public double getxLoc() {
        return xLoc;
    }

    public void setxLoc(double xLoc) {
        this.xLoc = xLoc;
    }

    public double getyLoc() {
        return yLoc;
    }

    public void setyLoc(double yLoc) {
        this.yLoc = yLoc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
