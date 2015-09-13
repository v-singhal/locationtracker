package com.vstudio.locationtracker.dom;

/**
 * Created by vaibhav on 13/9/15.
 */
public class PingedLocationDetailsData {

    private String trackingSessionId;
    private Double pingedLatitude;
    private Double pingedLongitude;
    private String pingedTime;

    public String getTrackingSessionId() {
        return trackingSessionId;
    }

    public void setTrackingSessionId(String trackingSessionId) {
        this.trackingSessionId = trackingSessionId;
    }

    public Double getPingedLatitude() {
        return pingedLatitude;
    }

    public void setPingedLatitude(Double pingedLatitude) {
        this.pingedLatitude = pingedLatitude;
    }

    public void setPingedLatitude(String pingedLatitude) {
        this.pingedLatitude = Double.valueOf(pingedLatitude);
    }

    public Double getPingedLongitude() {
        return pingedLongitude;
    }

    public void setPingedLongitude(Double pingedLongitude) {
        this.pingedLongitude = pingedLongitude;
    }

    public void setPingedLongitude(String pingedLongitude) {
        this.pingedLongitude = Double.valueOf(pingedLongitude);
    }

    public String getPingedTime() {
        return pingedTime;
    }

    public void setPingedTime(String pingedTime) {
        this.pingedTime = pingedTime;
    }
}
