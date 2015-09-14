package com.vbstudio.locationtracker.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.vbstudio.locationtracker.LocationTrackerPreferences;
import com.vbstudio.locationtracker.fragment.BaseFragment;
import com.vbstudio.locationtracker.utils.RiderLocationListener;

public class LocationTrackingService extends Service {

    private final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 0;
    //private final long MIN_TIME_BW_LOCATION_UPDATES = 5 * 3600 * 1000; //5mins
    private final long MIN_TIME_BW_LOCATION_UPDATES = 5 * 1000; //5secs

    private Intent intentInitiatedWith;
    private RiderLocationListener riderLocationListener;
    private LocationManager locationManager;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(BaseFragment.LOG_TAG, "LocationTrackerPreferences.onCreate()");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        this.intentInitiatedWith = intent;
        this.riderLocationListener = new RiderLocationListener(LocationTrackingService.this);
        this.locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        Log.i(BaseFragment.LOG_TAG, "LOCATION TRACKING SERVICE STARTED");

        runLocationTracking();

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.i(BaseFragment.LOG_TAG, "LOCATION TRACKING SERVICE DESTROYED");

        if (!LocationTrackerPreferences.getLocationServiceEndMode(LocationTrackingService.this)) {
            showToast("Restarting Service");
            startService(new Intent(this, LocationTrackingService.class));
        } else {
            stopLocationTracking();
        }
    }

    private void runLocationTracking() {
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_LOCATION_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, riderLocationListener);
        showToast("Location Tracking Started");
    }

    private void stopLocationTracking() {
        locationManager.removeUpdates(riderLocationListener);
        showToast("Location Tracking Destroyed");
    }

    private void showToast(final String message) {

        new Handler().post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LocationTrackingService.this, message, Toast.LENGTH_LONG).show();
            }
        });
    }

}
