package com.vstudio.locationtracker.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vstudio.locationtracker.R;

/**
 * Created by vaibhav on 23/8/15.
 */
public class LocationTrackingFragment extends BaseFragment {
    
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

    }
}