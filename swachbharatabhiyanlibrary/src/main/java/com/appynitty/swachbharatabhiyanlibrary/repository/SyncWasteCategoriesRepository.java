package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayan Dey on 31/1/20.
 */
public class SyncWasteCategoriesRepository {

    private static final String TABLE_NAME = "tableWasteCategory";
    private static final String COLUMN_CATEGORY_ID = "_wasteCategoryId";
    private static final String COLUMN_CATEGORY_NAME = "wasteCategoryName";
    
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" ( " +
            COLUMN_CATEGORY_ID +" INTEGER DEFAULT NULL, " +
            COLUMN_CATEGORY_NAME +" TEXT DEFAULT NULL " +
            ")";

    public static final String RESTORE_TABLE =
            "INSERT INTO " + TABLE_NAME + " SELECT * FROM TEMP_" + TABLE_NAME;

    public static final String DROP_TEMP_TABLE =
            "DROP TABLE IF EXISTS TEMP_" + TABLE_NAME;

    public static final String CREATE_TEMP_TABLE =
            "ALTER TABLE " + TABLE_NAME + " RENAME TO TEMP_" + TABLE_NAME;

    private final Context context;

    public SyncWasteCategoriesRepository(Context context) {
        this.context = context;
    }

    public void insertWasteCategory(List<WasteManagementPojo.GarbageCategoryPojo> categories) {
        SQLiteDatabase database = AUtils.sqlDBInstance(context);
        for (WasteManagementPojo.GarbageCategoryPojo category : categories) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_CATEGORY_ID, category.getCategoryID());
            contentValues.put(COLUMN_CATEGORY_NAME, category.getGarbageCategory());
            insertCategories(database, contentValues);
        }
        database.close();
    }

    public void truncateWasteCategories() {
        SQLiteDatabase database = AUtils.sqlDBInstance(context);
        truncateCategories(database);
        database.close();
    }

    public List<WasteManagementPojo.GarbageCategoryPojo> fetchWasteCategories() {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(context);
        List<WasteManagementPojo.GarbageCategoryPojo> list = fetchGarbageCategories(sqLiteDatabase);
        sqLiteDatabase.close();
        return list;
    }

    private static void insertCategories(SQLiteDatabase database, ContentValues contentValues) {
        database.insert(TABLE_NAME, null, contentValues);
    }

    private static void truncateCategories(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
    }

    private static List<WasteManagementPojo.GarbageCategoryPojo> fetchGarbageCategories(SQLiteDatabase database) {
        Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_NAME, null);
        List<WasteManagementPojo.GarbageCategoryPojo> categories = new ArrayList<>();

        if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
            do {
                WasteManagementPojo.GarbageCategoryPojo category = new WasteManagementPojo().new GarbageCategoryPojo(
                        cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_CATEGORY_NAME))
                );
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }
}
