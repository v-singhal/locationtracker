package com.vbstudio.locationtracker;

import android.content.Context;
import android.content.SharedPreferences;

import static com.vbstudio.locationtracker.utils.StringUtils.isValidString;

public class LocationTrackerPreferences {

    public static final String LAST_LOCATION_TRACKER_ID = "lastLocationTrackerId";
    public static final String CURRENT_LOCATION_TRACKER_ID = "currentLocationTrackerId";
    public static final String LOCATION_SERVICE_END_MODE = "locationServiceEndMode";

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("locationTrackerPreferences", Context.MODE_PRIVATE);
    }

    public static Integer getLastLocationTrackerId(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getInt(LAST_LOCATION_TRACKER_ID, -1);
    }

    public static void setLastLocationTrackerId(Context context, int lastLocationTrackerId) {
        SharedPreferences sharedPreferences = getPreferences(context);
        sharedPreferences.edit().putInt(LAST_LOCATION_TRACKER_ID, lastLocationTrackerId).commit();
    }

    public static String getCurrentLocationTrackerId(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);

        int lastLocationTrackerId = getLastLocationTrackerId(context);
        String trackerId = sharedPreferences.getString(CURRENT_LOCATION_TRACKER_ID, "");

        if (!isValidString(trackerId)) {
            lastLocationTrackerId++;
            trackerId = String.valueOf(lastLocationTrackerId);
            sharedPreferences.edit().putString(CURRENT_LOCATION_TRACKER_ID, trackerId).commit();
            setLastLocationTrackerId(context, lastLocationTrackerId);
        }

        return trackerId;
    }

    public static void resetCurrentLocationTrackerId(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        sharedPreferences.edit().putString(CURRENT_LOCATION_TRACKER_ID, "").commit();
    }

    public static Boolean getLocationServiceEndMode(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getBoolean(LOCATION_SERVICE_END_MODE, false);
    }

    public static void setLocationServiceEndMode(Context context, Boolean endMode) {
        SharedPreferences sharedPreferences = getPreferences(context);
        sharedPreferences.edit().putBoolean(LOCATION_SERVICE_END_MODE, endMode).commit();
    }


}
