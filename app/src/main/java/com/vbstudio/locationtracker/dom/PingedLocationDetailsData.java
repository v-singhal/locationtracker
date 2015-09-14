package com.vbstudio.locationtracker.dom;

import com.vbstudio.locationtracker.utils.StringUtils;

import java.text.SimpleDateFormat;

/**
 * Created by vaibhav on 13/9/15.
 */
public class PingedLocationDetailsData {

    private String trackingSessionId;
    private Double pingedLatitude;
    private Double pingedLongitude;
    private String pingedTime;

    public String getFormattedPingedTime() {
        return new SimpleDateFormat(StringUtils.APP_DATE_TIME_SEC_FORMAT).format(Long.valueOf(pingedTime));
    }

    //***********************/

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

    public void setPingedTime(Long pingedTime) {
        this.pingedTime = pingedTime.toString();
    }
}
