package com.happyhours.happyhours;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.nio.Buffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jesse on 2015-03-01.
 */
public class HoursDatabaseAdapter {
    HoursDatabase helper;

    public HoursDatabaseAdapter(Context context){
        helper = new HoursDatabase(context);
    }
    public void insertTables(String time, String date, String activity, float happiness){
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues timeValues = new ContentValues();
        ContentValues actValues = new ContentValues();

        timeValues.put(HoursDatabase.DATE, date);
        timeValues.put(HoursDatabase.TIME, time);
        actValues.put(HoursDatabase.ACTIVITY, activity);
        actValues.put(HoursDatabase.HAPPINESS, happiness);

        db.insert(HoursDatabase.TABLE_TIME, null, timeValues);
        db.insert(HoursDatabase.TABLE_ACTIVITY, null, actValues);
    }

    /* Modify for app. Currently used for testing */
    public List<Float> getTable(){
        List<Float> info = new ArrayList<Float>();
        SQLiteDatabase db = helper.getWritableDatabase();
        Cursor cAct = db.rawQuery("SELECT * FROM " + HoursDatabase.TABLE_ACTIVITY, null);
        Cursor cTime = db.rawQuery("SELECT time FROM " + HoursDatabase.TABLE_TIME, null);
        while (cAct.moveToNext() && cTime.moveToNext()){
            int index1 = cAct.getColumnIndex(HoursDatabase.ACTIVITY);
            int index2 = cAct.getColumnIndex(HoursDatabase.HAPPINESS);
            int index3 = cTime.getColumnIndex(HoursDatabase.TIME);
            String act = cAct.getString(index1);
            Float rating = cAct.getFloat(index2);
            String time = cTime.getString(index3);
            String[] parts = time.split(":");
            info.add(Float.parseFloat(parts[0]));
        }
        return info;
    }
    /* If activity time added to current day is over 24 hours. Split into two entries. */
    public void checkExtendedHours(){
        /* HAVE TO IMPLEMENT */
    }

    static class HoursDatabase extends SQLiteOpenHelper {
        private Context context;
        private static final int DATABASE_VERSION = 2;
        private static final String DATABASE_NAME = "happyInfo";

        /* Tables */
        private static final String TABLE_TIME = "TIME";
        private static final String TABLE_ACTIVITY = "ACTIVITY";

        /* Common Columns */
        private static final String KEY_ID = "_id";

        /* TABLE_TIME - Columns */
        private static final String TIME = "time";
        private static final String DATE = "date";

        /* TABLE_ACTIVITY - Columns */
        private static final String ACTIVITY = "activity";
        private static final String HAPPINESS = "happiness";

        /* TABLE CREATE STATEMENTS */
        private static final String CREATE_TABLE_TIME = "CREATE TABLE " + TABLE_TIME + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + DATE + " TEXT, " + TIME + " STRING);";
        private static final String CREATE_TABLE_ACTIVITY = "CREATE TABLE " + TABLE_ACTIVITY + "("
                + KEY_ID + " INTEGER PRIMARY KEY, " + ACTIVITY + " TEXT, " + HAPPINESS +
                " REAL);";

        /* DELETE STATEMENTS */
        private static final String DELETE_TABLE_TIME = "DROP TABLE IF EXISTS " + TABLE_TIME;
        private static final String DELETE_TABLE_ACTIVITY = "DROP TABLE IF EXISTS "
                + TABLE_ACTIVITY;

        public HoursDatabase(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
            this.context = context;
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL(CREATE_TABLE_TIME);
                db.execSQL(CREATE_TABLE_ACTIVITY);
            } catch (android.database.SQLException e) {
                e.getMessage();
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                db.execSQL(DELETE_TABLE_TIME);
                db.execSQL(DELETE_TABLE_ACTIVITY);
                onCreate(db);
            } catch (SQLException e) {
                e.getMessage();
            }
        }
    }
}
