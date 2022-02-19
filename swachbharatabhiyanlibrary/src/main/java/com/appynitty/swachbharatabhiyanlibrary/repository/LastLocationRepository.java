package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appynitty.swachbharatabhiyanlibrary.entity.LastLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.HashMap;

/**
 * Created by Ayan Dey on 7/10/19.
 */
public class LastLocationRepository {

    public static final String LAST_LOCATION_TABLE = "tableLastLocation";
    private final static String COLUMN_ID = "_lastLocationId";
    private final static String COLUMN_LAT = "lastLocationLat";
    private final static String COLUMN_LNG = "lastLocationLng";
    private final static String COLUMN_DATE = "lastLocationDate";

    public static String LatitudeKey = "LatitudeKey";
    public static String LongitudeKey = "LongitudeKey";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + LastLocationRepository.LAST_LOCATION_TABLE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_LAT + " TEXT DEFAULT NULL, " +
            COLUMN_LNG + " TEXT DEFAULT NULL, " +
            COLUMN_DATE + " DATETIME DEFAULT (strftime('%Y-%m-%d %H:%M:%S','now', 'localtime')))";

    public static final String RESTORE_TABLE =
            "INSERT INTO " + LAST_LOCATION_TABLE + " SELECT * FROM TEMP_" + LAST_LOCATION_TABLE;

    public static final String DROP_TEMP_TABLE =
            "DROP TABLE IF EXISTS TEMP_" + LAST_LOCATION_TABLE;

    public static final String CREATE_TEMP_TABLE =
            "ALTER TABLE " + LAST_LOCATION_TABLE + " RENAME TO TEMP_" + LAST_LOCATION_TABLE;

    private final Context mContext;

    public LastLocationRepository(Context context) {
        this.mContext = context;
    }

    public void insertUpdateLastLocation(LastLocationEntity entity){
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_LAT, entity.getColumnLattitude());
        contentValues.put(COLUMN_LNG, entity.getColumnLongitude());
        contentValues.put(COLUMN_DATE, entity.getColumnDate());
        insertUpdateLastLocation(sqLiteDatabase, contentValues);
        sqLiteDatabase.close();
    }

    public static void insertUpdateLastLocation(SQLiteDatabase sqLiteDatabase, ContentValues contentValues){
        String sql = "SELECT * FROM " + LAST_LOCATION_TABLE + " WHERE date("+ COLUMN_DATE +") = date(?)";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{String.valueOf(contentValues.get(COLUMN_DATE))});
        if(cursor.getCount() == 0)
            sqLiteDatabase.insert(LAST_LOCATION_TABLE, null, contentValues);
        else {
            String whereClause = "date(" + COLUMN_DATE + ") = date(?)";
            sqLiteDatabase.update(LastLocationRepository.LAST_LOCATION_TABLE, contentValues,
                    whereClause, new String[]{String.valueOf(contentValues.get(COLUMN_DATE))});
        }

        cursor.close();
    }

    public HashMap<String, Double> getLastLocationCoordinates(String date) {

        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);

        HashMap<String, Double> lastCoordinates = new HashMap<>();
        String sql = "SELECT * FROM " + LAST_LOCATION_TABLE +
                " WHERE DATE(" + COLUMN_DATE + ") = ? ORDER BY " + COLUMN_DATE + " DESC";
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{date});

        if(cursor.moveToFirst()){
            do {
                Double lat = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_LAT)));
                lastCoordinates.put(LatitudeKey, lat);
                Double lng = Double.parseDouble(cursor.getString(cursor.getColumnIndex(COLUMN_LNG)));
                lastCoordinates.put(LongitudeKey, lng);
            } while (cursor.moveToNext());
        }
        cursor.close();
        sqLiteDatabase.close();
        return lastCoordinates;
    }

    public void clearUnwantedRows(){
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        String sql = "DELETE FROM " + LastLocationRepository.LAST_LOCATION_TABLE +
                " WHERE date("+ LastLocationRepository.COLUMN_DATE +")" +
                " NOT IN (" +
                " SELECT date("+ SyncOfflineRepository.COLUMN_DATE +")" +
                " FROM "+ SyncOfflineRepository.SYNC_OFFLINE_TABLE +" GROUP BY date("+ SyncOfflineRepository.COLUMN_DATE +"))";
        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public void clearTableRows(){
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        sqLiteDatabase.delete(LastLocationRepository.LAST_LOCATION_TABLE, null, null);
        sqLiteDatabase.close();
    }

}
