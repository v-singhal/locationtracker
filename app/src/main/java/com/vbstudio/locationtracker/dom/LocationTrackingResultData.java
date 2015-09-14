package com.vbstudio.locationtracker.dom;

/**
 * Created by vaibhav on 13/9/15.
 */
public class LocationTrackingResultData {

    private String trackingSessionId;
    private String tripStartTime;
    private String tripEndTime;
    private Double distanceTraveled;

    public String getTrackingSessionId() {
        return trackingSessionId;
    }

    public void setTrackingSessionId(String trackingSessionId) {
        this.trackingSessionId = trackingSessionId;
    }

    public String getTripStartTime() {
        return tripStartTime;
    }

    public void setTripStartTime(String tripStartTime) {
        this.tripStartTime = tripStartTime;
    }

    public void setTripStartTime(Long tripStartTime) {
        this.tripStartTime = tripStartTime.toString();
    }

    public String getTripEndTime() {
        return tripEndTime;
    }

    public void setTripEndTime(String tripEndTime) {
        this.tripEndTime = tripEndTime;
    }

    public void setTripEndTime(Long tripEndTime) {
        this.tripEndTime = tripEndTime.toString();
    }

    public Double getDistanceTraveled() {
        return distanceTraveled;
    }

    public void setDistanceTraveled(Double distanceTraveled) {
        this.distanceTraveled = distanceTraveled;
    }

    public void setDistanceTraveled(String distanceTraveled) {
        this.distanceTraveled = Double.valueOf(distanceTraveled);
    }
}
