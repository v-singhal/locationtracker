package com.vbstudio.locationtracker.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import com.vbstudio.locationtracker.LocationTrackerPreferences;
import com.vbstudio.locationtracker.database.SqliteHelper;
import com.vbstudio.locationtracker.dom.PingedLocationDetailsData;
import com.vbstudio.locationtracker.fragment.BaseFragment;

import java.util.Calendar;

/**
 * Created by vaibhav on 14/9/15.
 */
public class RiderLocationListener implements LocationListener {

    private Context context;
    private SqliteHelper sqliteHelper;

    public RiderLocationListener(Context context) {
        this.context = context;
        this.sqliteHelper = new SqliteHelper(context);
    }

    @Override
    public void onLocationChanged(Location location) {
        addLocationUpdateToDb(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {

    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    public void addLocationUpdateToDb(Location location) {
        PingedLocationDetailsData pingedLocationDetailsData = new PingedLocationDetailsData();

        pingedLocationDetailsData.setTrackingSessionId(LocationTrackerPreferences.getCurrentLocationTrackerId(context));
        pingedLocationDetailsData.setPingedLatitude(location.getLatitude());
        pingedLocationDetailsData.setPingedLongitude(location.getLongitude());
        pingedLocationDetailsData.setPingedTime(Calendar.getInstance().getTimeInMillis());

        Log.i(BaseFragment.LOG_TAG, "LATITUDE TO BE ADDED: " + pingedLocationDetailsData.getPingedLatitude());
        Log.i(BaseFragment.LOG_TAG, "LONGITUDE TO BE ADDED: " + pingedLocationDetailsData.getPingedLongitude());
        Log.i(BaseFragment.LOG_TAG, "TIME TO BE ADDED: " + pingedLocationDetailsData.getFormattedPingedTime());
        Log.i(BaseFragment.LOG_TAG, "-----------------------------------");

        sqliteHelper.addLocationPingData(pingedLocationDetailsData);
    }
}
