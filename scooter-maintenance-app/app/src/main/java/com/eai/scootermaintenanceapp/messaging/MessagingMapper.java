package com.eai.scootermaintenanceapp.messaging;

import com.eai.scootermaintenanceapp.data.model.Scooter;
import com.eai.scootermaintenanceapp.data.model.ScooterStatus;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MessagingMapper {
    public static Scooter jsonToScooter(String jsonString) {
        JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);

        String id = jsonObject.get("id").getAsString();

        String errorDateString = jsonObject.get("timeOfError").getAsString();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS", Locale.getDefault());
        Date errorDate = null;
        try {
            errorDate = format.parse(errorDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Double latitude = jsonObject.get("yLoc").getAsDouble();
        Double longitude = jsonObject.get("xLoc").getAsDouble();
        ScooterStatus status = ScooterStatus.valueOf(jsonObject.get("status").getAsString());
        Integer errorCode = jsonObject.get("errorCode").getAsInt();
        String failureReason = jsonObject.get("errorMessage").getAsString();

        return new Scooter(id, errorDate, latitude, longitude, status, errorCode, failureReason);
    }

    public static String scooterToJson(Scooter scooter) {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS").create();
        return gson.toJson(scooter);
    }
}
