package com.vstudio.locationtracker.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.vstudio.locationtracker.LocationTrackerPreferences;
import com.vstudio.locationtracker.fragment.BaseFragment;

public class LocationTrackingService extends Service {

    private Intent intentInitiatedWith;

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

        intentInitiatedWith = intent;

        Log.i(BaseFragment.LOG_TAG, "LOCATION TRACKING SERVICE STARTED");
		runLocationTracking();

		return START_REDELIVER_INTENT;
	}

    @Override
	public void onDestroy() {
		Log.i(BaseFragment.LOG_TAG, "LOCATION TRACKING SERVICE DESTROYED");
		super.onDestroy();
        if(!LocationTrackerPreferences.getLocationServiceEndMode(LocationTrackingService.this)) {
            startService(intentInitiatedWith);
        }
	}

    private void runLocationTracking() {

    }
		
}
