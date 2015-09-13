package com.vstudio.locationtracker;

import android.app.Application;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by vaibhav on 13/9/15.
 */
public class LocationTracker extends Application {

    public LocationTracker() {
        super();
    }

    @Override
    public void onCreate () {
        super.onCreate();
        /***********ADD CUSTOM FONTS TO APP**************/
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath(getResources().getString(R.string.custom_font_path))
                .setFontAttrId(R.attr.fontPath)
                .build());
        /***********************************************/
    }

}
