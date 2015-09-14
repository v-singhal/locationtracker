package com.vbstudio.locationtracker.utils;

/**
 * Created by vaibhav on 13/9/15.
 */
public class BooleanUtils {

    public static boolean isValidBoolean(Boolean testBoolean) {
        if(testBoolean != null){
            return true;
        }
        else {
            return false;
        }
    }
}
