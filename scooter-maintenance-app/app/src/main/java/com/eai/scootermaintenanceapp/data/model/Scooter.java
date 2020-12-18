package com.eai.scootermaintenanceapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Scooter implements Parcelable {

    private Double latitude;
    private Double longitude;
    private String name;
    private ScooterStatus status;
    private String failureReason;

    public Scooter(Double latitude, Double longitude, String name, ScooterStatus status, String failureReason) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.status = status;
        this.failureReason = failureReason;
    }

    public Scooter(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
        status = ScooterStatus.valueOf(in.readString());
        failureReason = in.readString();
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public String getName() {
        return name;
    }

    public ScooterStatus getStatus() {
        return status;
    }

    public String getFailureReason() {
        return failureReason;
    }

    public void setStatus(ScooterStatus status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(name);
        dest.writeString(status.name());
        dest.writeString(failureReason);
    }

    public static final Parcelable.Creator<Scooter> CREATOR = new Parcelable.Creator<Scooter>() {
        @Override
        public Scooter createFromParcel(Parcel in) {
            return new Scooter(in);
        }

        @Override
        public Scooter[] newArray(int size) {
            return new Scooter[size];
        }
    };
}