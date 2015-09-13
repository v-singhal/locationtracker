package com.vstudio.locationtracker;

import android.content.Context;
import android.content.SharedPreferences;

import static com.vstudio.locationtracker.utils.StringUtils.isValidString;

public class LocationTrackerPreferences {

    private static int lastLocationTrackerId = -1;

    public static final String LAST_LOCATION_TRACKER_ID = "lastLocationTrackerId";
    public static final String LOCATION_SERVICE_END_MODE = "locationServiceEndMode";

    public static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences("locationTrackerPreferences", Context.MODE_PRIVATE);
    }

    public static String getLastLocationTrackerId(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        String trackerId = sharedPreferences.getString(LAST_LOCATION_TRACKER_ID, "");
        if(!isValidString(trackerId)) {
            lastLocationTrackerId++;
            trackerId = String.valueOf(trackerId);
        }

        return trackerId;
    }

    public static void resetLastLocationTrackerId(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        sharedPreferences.edit().putString(LAST_LOCATION_TRACKER_ID, "").commit();
    }

    public static Boolean getLocationServiceEndMode(Context context) {
        SharedPreferences sharedPreferences = getPreferences(context);
        return sharedPreferences.getBoolean(LOCATION_SERVICE_END_MODE, false);
    }

    public static void setLocationServiceEndMode(Context context, Boolean endMode) {
        SharedPreferences sharedPreferences = getPreferences(context);
        sharedPreferences.edit().putBoolean(LAST_LOCATION_TRACKER_ID, endMode).commit();
    }


}
