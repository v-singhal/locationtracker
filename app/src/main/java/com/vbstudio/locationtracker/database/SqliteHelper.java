package com.vbstudio.locationtracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.vbstudio.locationtracker.dom.LocationTrackingResultData;
import com.vbstudio.locationtracker.dom.PingedLocationDetailsData;

import java.util.ArrayList;
import java.util.List;

public class SqliteHelper extends SQLiteOpenHelper {


    public static final String DATABASE_NAME = "LocationTracker.db";
    public static final int DATABASE_VERSION = 1;

    //--------------Columns------------

    //Rider's Location
    public static final String TRACKING_SESSION_ID = "trackingSessionId";
    public static final String PINGED_LATITUDE = "pingedLatitude";
    public static final String PINGED_LONGITUDE = "pingedLongitude";
    public static final String PINGED_TIME = "pingedTime";

    //Rider's Trip Info
    public static final String TRIP_TRACKING_SESSION_ID = "tripTrackingSessionId";
    public static final String PINGED_START_TIME = "pingedStartTime";
    public static final String PINGED_END_TIME = "pingedEndTime";
    public static final String TRIP_DISTANCE = "tripDistance";

    //***************************************************/

    public static final String TABLE_LOCATION_TRACKER = "LOCATIONTRACKER";
    public static final String TABLE_LOCATION_TRACKER_RESULT = "LOCATIONTRACKERRESULT";

    //*****************************************************************/

    public static final String CREATE_TABLE_LOCATION_TRACKER = "CREATE TABLE "
            + "IF NOT EXISTS "
            + TABLE_LOCATION_TRACKER
            + "("
            + TRACKING_SESSION_ID + " TEXT,"
            + PINGED_LATITUDE + " TEXT,"
            + PINGED_LONGITUDE + " TEXT,"
            + PINGED_TIME + " TEXT"
            + ")";

    public static final String CREATE_TABLE_LOCATION_TRACKER_RESULT = "CREATE TABLE "
            + "IF NOT EXISTS "
            + TABLE_LOCATION_TRACKER_RESULT
            + "("
            + TRIP_TRACKING_SESSION_ID + " TEXT,"
            + PINGED_START_TIME + " TEXT,"
            + PINGED_END_TIME + " TEXT,"
            + TRIP_DISTANCE + " TEXT"
            + ")";


    //********************************************************************/

    public SqliteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_TABLE_LOCATION_TRACKER);
        database.execSQL(CREATE_TABLE_LOCATION_TRACKER_RESULT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_TRACKER);
        //db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOCATION_TRACKER_RESULT);

        onCreate(db);
    }

    private Boolean isColumnPresentInTable(SQLiteDatabase db, String columnName, String tableName) {
        try {
            String rawQuery = "SELECT /*FROM " + tableName + " LIMIT 0";
            Cursor cursor = db.rawQuery(rawQuery, null);

            if (cursor.getColumnIndex(columnName) != -1)
                return true;
            else
                return false;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void deleteDatabase(Context context) {
        context.deleteDatabase(DATABASE_NAME);
    }

    //Common Delete
    public void deleteData(String TableName, String ColumnName, String ColumnValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, ColumnName + " = ?",
                new String[]{String.valueOf(ColumnValue)});
        db.close();
    }

    public void deleteData(String TableName, String whereClause) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, whereClause, null);
        db.close();
    }

    //Called when further processing is required on DB
    public void deleteDataWithoutClosingDb(String TableName, String whereClause) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TableName, whereClause, null);
    }

    public Cursor getData(String Query) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(Query, null);
        return cursor;
    }


    //************************************************************/
    // RIDER'S LOCATION DATA CRUD
    //**********************************************************/
    // CREATE
    public void addLocationPingData(PingedLocationDetailsData pingedLocationData) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(TRACKING_SESSION_ID, pingedLocationData.getTrackingSessionId());
        values.put(PINGED_LATITUDE, pingedLocationData.getPingedLatitude());
        values.put(PINGED_LONGITUDE, pingedLocationData.getPingedLongitude());
        values.put(PINGED_TIME, pingedLocationData.getPingedTime());

        db.insert(TABLE_LOCATION_TRACKER, null, values);
        db.close(); // Closing database connection
    }

    // READ
    public List<PingedLocationDetailsData> getLocationPingDataList() {
        SQLiteDatabase db = this.getReadableDatabase();

        String rawQuery = "SELECT * FROM " + TABLE_LOCATION_TRACKER;

        List<PingedLocationDetailsData> pingedLocationDataSetFromDb = new ArrayList<>();
        PingedLocationDetailsData pingedLocationData = new PingedLocationDetailsData();
        Cursor cursor = db.rawQuery(rawQuery, null);

        if (cursor.moveToFirst()) {
            do {
                pingedLocationData = new PingedLocationDetailsData();

                pingedLocationData.setTrackingSessionId(cursor.getString(cursor.getColumnIndex(TRACKING_SESSION_ID)));
                pingedLocationData.setPingedLatitude(cursor.getString(cursor.getColumnIndex(PINGED_LATITUDE)));
                pingedLocationData.setPingedLongitude(cursor.getString(cursor.getColumnIndex(PINGED_LONGITUDE)));
                pingedLocationData.setPingedTime(cursor.getString(cursor.getColumnIndex(PINGED_TIME)));

                pingedLocationDataSetFromDb.add(pingedLocationData);
            } while (cursor.moveToNext());
        }
        db.close();

        return pingedLocationDataSetFromDb;
    }

    public List<PingedLocationDetailsData> getLocationPingDataById(String trackingSessionId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String rawQuery = "SELECT * FROM " + TABLE_LOCATION_TRACKER + " WHERE "
                + TRACKING_SESSION_ID + " = '" + trackingSessionId + "'";

        List<PingedLocationDetailsData> pingedLocationDataSetFromDb = new ArrayList<>();
        PingedLocationDetailsData pingedLocationData = new PingedLocationDetailsData();
        Cursor cursor = db.rawQuery(rawQuery, null);

        if (cursor.moveToFirst()) {
            do {
                pingedLocationData = new PingedLocationDetailsData();

                pingedLocationData.setTrackingSessionId(cursor.getString(cursor.getColumnIndex(TRACKING_SESSION_ID)));
                pingedLocationData.setPingedLatitude(cursor.getString(cursor.getColumnIndex(PINGED_LATITUDE)));
                pingedLocationData.setPingedLongitude(cursor.getString(cursor.getColumnIndex(PINGED_LONGITUDE)));
                pingedLocationData.setPingedTime(cursor.getString(cursor.getColumnIndex(PINGED_TIME)));

                pingedLocationDataSetFromDb.add(pingedLocationData);
            } while (cursor.moveToNext());
        }
        db.close();

        return pingedLocationDataSetFromDb;
    }

    // UPDATE
    public void updateLocationPingData() {

    }

    // DELETE
    public void deleteLocationPingData() {
        String where = "";

        deleteData(TABLE_LOCATION_TRACKER, where);
    }

    //************************************************************/
    // LOCATION TRACKER RESULT DATA CRUD
    //**********************************************************/
    // CREATE
    public void addLocationTrackerResult(LocationTrackingResultData locationTrackingResultData) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        if(!isLocationTrackingPresentInTable(locationTrackingResultData.getTrackingSessionId())) {
            values.put(TRIP_TRACKING_SESSION_ID, locationTrackingResultData.getTrackingSessionId());
            values.put(PINGED_START_TIME, locationTrackingResultData.getTripStartTime());
            values.put(PINGED_END_TIME, locationTrackingResultData.getTripEndTime());
            values.put(TRIP_DISTANCE, locationTrackingResultData.getDistanceTraveled());

            db.insert(TABLE_LOCATION_TRACKER_RESULT, null, values);
        } else {
            updateLocationTrackerAtEnd(locationTrackingResultData);
        }
        db.close(); // Closing database connection
    }

    // READ
    public List<LocationTrackingResultData> getLocationTrackerResultList() {
        SQLiteDatabase db = this.getReadableDatabase();

        String rawQuery = "SELECT * FROM " + TABLE_LOCATION_TRACKER_RESULT;

        List<LocationTrackingResultData> locationTrackingResultDataSetFromDb = new ArrayList<>();
        LocationTrackingResultData locationTrackingResultData = new LocationTrackingResultData();
        Cursor cursor = db.rawQuery(rawQuery, null);

        if (cursor.moveToFirst()) {
            do {
                locationTrackingResultData = new LocationTrackingResultData();

                locationTrackingResultData.setTrackingSessionId(cursor.getString(cursor.getColumnIndex(TRIP_TRACKING_SESSION_ID)));
                locationTrackingResultData.setTripStartTime(cursor.getString(cursor.getColumnIndex(PINGED_START_TIME)));
                locationTrackingResultData.setTripEndTime(cursor.getString(cursor.getColumnIndex(PINGED_END_TIME)));
                locationTrackingResultData.setDistanceTraveled(cursor.getString(cursor.getColumnIndex(TRIP_DISTANCE)));

                locationTrackingResultDataSetFromDb.add(locationTrackingResultData);
            } while (cursor.moveToNext());
        }
        db.close();

        return locationTrackingResultDataSetFromDb;
    }

    public LocationTrackingResultData getLocationTrackerResultById(String tripId) {
        SQLiteDatabase db = this.getReadableDatabase();

        String rawQuery = "SELECT * FROM " + TABLE_LOCATION_TRACKER_RESULT + " WHERE "
                + TRIP_TRACKING_SESSION_ID + " = '" + tripId + "'";

        LocationTrackingResultData locationTrackingResultData = new LocationTrackingResultData();
        Cursor cursor = db.rawQuery(rawQuery, null);

        if (cursor.moveToFirst()) {
            do {
                locationTrackingResultData = new LocationTrackingResultData();

                locationTrackingResultData.setTrackingSessionId(cursor.getString(cursor.getColumnIndex(TRIP_TRACKING_SESSION_ID)));
                locationTrackingResultData.setTripStartTime(cursor.getString(cursor.getColumnIndex(PINGED_START_TIME)));
                locationTrackingResultData.setTripEndTime(cursor.getString(cursor.getColumnIndex(PINGED_END_TIME)));
                locationTrackingResultData.setDistanceTraveled(cursor.getString(cursor.getColumnIndex(TRIP_DISTANCE)));

            } while (cursor.moveToNext());
        }
        db.close();

        return locationTrackingResultData;
    }

    //Check IF A TRIP Exists
    public Boolean isLocationTrackingPresentInTable(String tripTrackingSessionId) {
        Boolean isLocationTrackingPresent = false;
        SQLiteDatabase db = this.getReadableDatabase();

        String rawQuery = "SELECT * FROM " + TABLE_LOCATION_TRACKER_RESULT + " WHERE "
                + TRIP_TRACKING_SESSION_ID + " = '" + tripTrackingSessionId + "'";
        Cursor cursor = db.rawQuery(rawQuery, null);

        if (cursor.moveToFirst()) {
            isLocationTrackingPresent = true;
        }
        //db.close();

        return isLocationTrackingPresent;
    }

    // UPDATE
    public void updateLocationTrackerResult() {

    }

    public void updateLocationTrackerAtEnd(LocationTrackingResultData locationTrackingResultData) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        String where = TRIP_TRACKING_SESSION_ID + " = '" + locationTrackingResultData.getTrackingSessionId() + "'";
        values.put(PINGED_END_TIME, locationTrackingResultData.getTripEndTime());
        values.put(TRIP_DISTANCE, locationTrackingResultData.getDistanceTraveled().toString());

        db.update(TABLE_LOCATION_TRACKER_RESULT, values, where, null);

        db.close(); // Closing database connection
    }

    // DELETE
    public void deleteLocationTrackerResult() {
        String where = "";

        deleteData(TABLE_LOCATION_TRACKER_RESULT, where);
    }
}
