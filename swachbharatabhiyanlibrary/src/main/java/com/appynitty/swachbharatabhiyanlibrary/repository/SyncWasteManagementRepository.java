package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementHistoryPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayan Dey on 31/1/20.
 */
public class SyncWasteManagementRepository {

    private static final String TABLE_NAME = "tableWasteManagement";
    private static final String COLUMN_ID = "_wasteManagementId";
    private static final String COLUMN_POJO = "wasteManagementPojo";
    private static final String COLUMN_SUBCATEGORY_ID = "wasteManagementSubCategory";
    private static final String COLUMN_WEIGHT = "wasteManagementWeight";
    private static final String COLUMN_UNIT = "wasteManagementUnit";
    private static final String COLUMN_DATE_TIME = "wasteManagementDateTime";

    private static final int DATA_LIMIT = 25;
    
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" ( " +
            COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_POJO +" TEXT DEFAULT NULL, " +
            COLUMN_SUBCATEGORY_ID +" INTEGER DEFAULT NULL, " +
            COLUMN_WEIGHT +" TEXT DEFAULT 0, " +
            COLUMN_UNIT +" INTEGER DEFAULT 1, " +
            COLUMN_DATE_TIME + " DATETIME DEFAULT (strftime('%Y-%m-%d %H:%M:%f','now', 'localtime'))" +
            ")";

    public static final String RESTORE_TABLE =
            "INSERT INTO " + TABLE_NAME + " SELECT * FROM TEMP_" + TABLE_NAME;

    public static final String DROP_TEMP_TABLE =
            "DROP TABLE IF EXISTS TEMP_" + TABLE_NAME;

    public static final String CREATE_TEMP_TABLE =
            "ALTER TABLE " + TABLE_NAME + " RENAME TO TEMP_" + TABLE_NAME;

    private final Context context;

    public SyncWasteManagementRepository(Context context) {
        this.context = context;
    }

    @Nullable
    public Long saveWasteManagementData(List<WasteManagementPojo> list) {
        Long insert = null;
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(context);
        for (WasteManagementPojo pojo : list) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_POJO, new Gson().toJson(pojo));
            contentValues.put(COLUMN_SUBCATEGORY_ID, pojo.getSubCategoryID());
            contentValues.put(COLUMN_WEIGHT, pojo.getWeight());
            contentValues.put(COLUMN_UNIT, pojo.getUnitID());
            insert = insertWasteManagement(sqLiteDatabase, contentValues);
        }
        sqLiteDatabase.close();
        return insert;
    }

    public List<WasteManagementPojo> getWasteManagementData(String offset) {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(context);
        List<WasteManagementPojo> list = fetchWasteManagement(sqLiteDatabase, offset);
        sqLiteDatabase.close();
        return list;
    }

    public Integer removeWasteManagementData(String id) {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(context);
        Integer i = deleteWasteManagement(sqLiteDatabase, id);
        sqLiteDatabase.close();
        return i;
    }

    public List<WasteManagementHistoryPojo> getOfflineWasteManagementHistory() {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(context);
        List<WasteManagementHistoryPojo> list = fetchWasteManagementHistory(sqLiteDatabase);
        sqLiteDatabase.close();
        return list;
    }

    private static long insertWasteManagement(SQLiteDatabase sqLiteDatabase, ContentValues contentValues) {
        return sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
    }

    private static List<WasteManagementPojo> fetchWasteManagement(SQLiteDatabase sqLiteDatabase, String offset) {
        List<WasteManagementPojo> list = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME +
                " limit " + DATA_LIMIT + " offset " + offset, null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            do {
                WasteManagementPojo pojo;
                String s = cursor.getString(cursor.getColumnIndex(COLUMN_POJO));
                if (s == null)
                    pojo = new WasteManagementPojo();
                else
                    pojo = new Gson().fromJson(s, WasteManagementPojo.class);

                if (pojo != null) {
                    pojo.setID(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
                    pojo.setSubCategoryID(cursor.getString(cursor.getColumnIndex(COLUMN_SUBCATEGORY_ID)));
                    pojo.setWeight(cursor.getString(cursor.getColumnIndex(COLUMN_WEIGHT)));
                    pojo.setUnitID(cursor.getInt(cursor.getColumnIndex(COLUMN_UNIT)));
                    pojo.setGdDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_TIME)));
                    list.add(pojo);
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private static List<WasteManagementHistoryPojo> fetchWasteManagementHistory(SQLiteDatabase sqLiteDatabase) {
        List<WasteManagementHistoryPojo> list = new ArrayList<>();

        String sql = "SELECT COUNT(*) AS GarbageCount, strftime('%m-%d-%Y', "+ COLUMN_DATE_TIME +") AS Date FROM "
                + TABLE_NAME + " GROUP BY DATE(" + COLUMN_DATE_TIME + ")";

        Cursor cursor = sqLiteDatabase.rawQuery(sql , null);
        if (cursor.moveToFirst() && cursor.getCount() > 0) {
            do {
                WasteManagementHistoryPojo pojo = new WasteManagementHistoryPojo();
                pojo.setGarbageCount(cursor.getInt(cursor.getColumnIndex("GarbageCount")));
                pojo.setDate(cursor.getString(cursor.getColumnIndex("Date")));
                list.add(pojo);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private static int deleteWasteManagement(SQLiteDatabase sqLiteDatabase, String id) {
        return sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{id});
    }
}
