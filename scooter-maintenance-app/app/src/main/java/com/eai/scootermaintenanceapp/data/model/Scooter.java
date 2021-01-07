package com.eai.scootermaintenanceapp.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Scooter implements Parcelable {

    private String id;
    private Date errorDate;
    private Double latitude;
    private Double longitude;
    private ScooterStatus status;
    private Integer errorCode;
    private String failureReason;

    public Scooter(String id, Date errorDate, Double latitude, Double longitude, ScooterStatus status,
                   Integer errorCode, String failureReason) {
        this.id = id;
        this.errorDate = errorDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.status = status;
        this.errorCode = errorCode;
        this.failureReason = failureReason;
    }

    public Scooter(Parcel in) {
        id = in.readString();
        errorDate = new Date(in.readLong());
        latitude = in.readDouble();
        longitude = in.readDouble();
        status = ScooterStatus.valueOf(in.readString());
        errorCode = in.readInt();
        failureReason = in.readString();
    }

    public String getId() {
        return id;
    }

    public Date getErrorDate() {
        return errorDate;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public ScooterStatus getStatus() {
        return status;
    }

    public Integer getErrorCode() {
        return errorCode;
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
        dest.writeString(id);
        dest.writeLong(errorDate.getTime());
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(status.name());
        dest.writeInt(errorCode);
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

    @Override
    public String toString() {
        return "Scooter{" +
                "id='" + id + '\'' +
                ", errorDate=" + errorDate +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", status=" + status +
                ", errorCode=" + errorCode +
                ", failureReason='" + failureReason + '\'' +
                '}';
    }
}