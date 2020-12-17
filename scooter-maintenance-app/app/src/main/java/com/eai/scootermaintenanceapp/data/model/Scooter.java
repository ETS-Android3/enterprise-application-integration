package com.eai.scootermaintenanceapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Scooter implements Parcelable {

    private final Double latitude;
    private final Double longitude;
    private final String name;
    private final String failureReason;

    public Scooter(Double latitude, Double longitude, String name, String failureReason) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.name = name;
        this.failureReason = failureReason;
    }

    public Scooter(Parcel in) {
        latitude = in.readDouble();
        longitude = in.readDouble();
        name = in.readString();
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

    public String getFailureReason() {
        return failureReason;
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