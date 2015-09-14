package com.vbstudio.locationtracker.fragment;


import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.vbstudio.locationtracker.LocationTrackerPreferences;
import com.vbstudio.locationtracker.R;
import com.vbstudio.locationtracker.dom.LocationTrackingResultData;
import com.vbstudio.locationtracker.dom.PingedLocationDetailsData;
import com.vbstudio.locationtracker.service.LocationTrackingService;
import com.vbstudio.locationtracker.utils.RiderLocationListener;
import com.vbstudio.locationtracker.utils.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static com.vbstudio.locationtracker.utils.StringUtils.isValidString;

/**
 * Created by vaibhav on 13/9/15.
 */
public class LocationTrackingFragment extends BaseFragment {

    private RiderLocationListener riderLocationListener;
    private LocationManager locationManager;

    public static LocationTrackingFragment newInstance() {
        LocationTrackingFragment fragment = new LocationTrackingFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Location tracking");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return View.inflate(getActivity(), R.layout.fragment_location_tracker, null);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupView(view);

    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        final int id = view.getId();

        if(id == R.id.btnStartLocationService) {
            startLocationTrackingService();
        } else if (id == R.id.btnStopLocationService) {
            stopLocationTrackingService();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void setupView(View view) {

        this.riderLocationListener = new RiderLocationListener(getActivity());
        this.locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        setupServiceStatusText(view);
        view.findViewById(R.id.btnStartLocationService).setOnClickListener(this);
        view.findViewById(R.id.btnStopLocationService).setOnClickListener(this);
    }

    private void setupServiceStatusText(View view) {
        TextView txtTrackerId = (TextView) view.findViewById(R.id.txtTrackerId);
        TextView txtTripStartTime = (TextView) view.findViewById(R.id.txtTripStartTime);
        TextView txtTripEndTime = (TextView) view.findViewById(R.id.txtTripEndTime);
        TextView txtDistanceTravelled = (TextView) view.findViewById(R.id.txtDistanceTravelled);
        String lastTrackerId = LocationTrackerPreferences.getLastLocationTrackerId(getActivity()).toString();

        LocationTrackingResultData locationTrackingResultData = getSqliteHelper().getLocationTrackerResultById(lastTrackerId);
        if(locationTrackingResultData != null && isValidString(locationTrackingResultData.getTripEndTime())) {

            String startTime = new SimpleDateFormat(StringUtils.APP_DATE_TIME_SEC_FORMAT).format(Long.valueOf(locationTrackingResultData.getTripStartTime()));
            String endTime = new SimpleDateFormat(StringUtils.APP_DATE_TIME_SEC_FORMAT).format(Long.valueOf(locationTrackingResultData.getTripEndTime()));

            txtTrackerId.setText(locationTrackingResultData.getTrackingSessionId());
            txtTripStartTime.setText(startTime);
            txtTripEndTime.setText(endTime);
            txtDistanceTravelled.setText(locationTrackingResultData.getDistanceTraveled() + "m");
        }
    }

    private void startLocationTrackingService() {
        getView().findViewById(R.id.btnStartLocationService).setOnClickListener(null);
        getView().findViewById(R.id.btnStopLocationService).setOnClickListener(this);

        LocationTrackerPreferences.setLocationServiceEndMode(getActivity(), false);

        Intent startLocationTrackingServiceIntent = new Intent(getActivity(), LocationTrackingService.class);
        getActivity().startService(startLocationTrackingServiceIntent);

        initiateTripInDb();
    }

    private void stopLocationTrackingService() {
        getView().findViewById(R.id.btnStartLocationService).setOnClickListener(this);
        getView().findViewById(R.id.btnStopLocationService).setOnClickListener(null);

        showLoadingIndicator();
        LocationTrackerPreferences.setLocationServiceEndMode(getActivity(), true);

        Intent stopLocationTrackingServiceIntent = new Intent(getActivity(), LocationTrackingService.class);
        getActivity().stopService(stopLocationTrackingServiceIntent);

        terminateTipInDb();
        openTripSummary();
        hideLoadingIndicator();
        LocationTrackerPreferences.resetCurrentLocationTrackerId(getActivity());
    }

    private void initiateTripInDb() {
        LocationTrackingResultData locationTrackingResultData = new LocationTrackingResultData();

        locationTrackingResultData.setTrackingSessionId(LocationTrackerPreferences.getCurrentLocationTrackerId(getActivity()));
        locationTrackingResultData.setTripStartTime(Calendar.getInstance().getTimeInMillis());
        locationTrackingResultData.setTripEndTime(Calendar.getInstance().getTimeInMillis());
        locationTrackingResultData.setDistanceTraveled("0");

        getSqliteHelper().addLocationTrackerResult(locationTrackingResultData);

        getCurrentLocation();
    }

    private void terminateTipInDb() {
        getCurrentLocation();

        LocationTrackingResultData locationTrackingResultData = new LocationTrackingResultData();

        locationTrackingResultData.setTrackingSessionId(LocationTrackerPreferences.getCurrentLocationTrackerId(getActivity()));
        locationTrackingResultData.setTripEndTime(Calendar.getInstance().getTimeInMillis());
        locationTrackingResultData.setDistanceTraveled(computeDistanceTravelledInTrip(locationTrackingResultData.getTrackingSessionId()));

        getSqliteHelper().updateLocationTrackerAtEnd(locationTrackingResultData);
        setupServiceStatusText(getView());
    }

    private String computeDistanceTravelledInTrip(String trackingSessionId) {
        Float distanceTravelled = 0f;
        List<PingedLocationDetailsData> pingedLocationDetailsDataList = getSqliteHelper().getLocationPingDataById(trackingSessionId);

        for(int counter = 0; counter < pingedLocationDetailsDataList.size()-1; counter++) {
            PingedLocationDetailsData pingedLocationDetailsData1 = new PingedLocationDetailsData();
            PingedLocationDetailsData pingedLocationDetailsData2 = new PingedLocationDetailsData();
            Location location1 = new Location("");
            Location location2 = new Location("");

            pingedLocationDetailsData1 = pingedLocationDetailsDataList.get(counter);
            pingedLocationDetailsData2 = pingedLocationDetailsDataList.get(counter+1);

            location1.setLatitude(pingedLocationDetailsData1.getPingedLatitude());
            location1.setLongitude(pingedLocationDetailsData1.getPingedLongitude());
            location2.setLatitude(pingedLocationDetailsData2.getPingedLatitude());
            location2.setLongitude(pingedLocationDetailsData2.getPingedLongitude());

            distanceTravelled += location1.distanceTo(location2);
        }

        Log.e(BaseFragment.LOG_TAG, "TOTAL DISTANCE TRAVELLED IN TRIP #" + trackingSessionId + " is " + distanceTravelled.toString() + "m");
        Log.i(BaseFragment.LOG_TAG, "-----------------------------------");

        return distanceTravelled.toString();
    }

    private void getCurrentLocation() {
        Criteria criteria = new Criteria();

        criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        riderLocationListener.addLocationUpdateToDb(locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, true)));
    }

    private void openTripSummary() {

    }
}