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
public class SyncWasteSubCategoriesRepository {

    private static final String TABLE_NAME = "tableWasteSubCategory";
    private static final String COLUMN_SUBCATEGORY_ID = "_wasteSubCategoryId";
    private static final String COLUMN_SUBCATEGORY_NAME = "wasteSubCategoryName";
    private static final String COLUMN__CATEGORY_ID = "wasteCategoryId";
    
    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS "+ TABLE_NAME +" ( " +
            COLUMN_SUBCATEGORY_ID +" INTEGER DEFAULT NULL, " +
            COLUMN_SUBCATEGORY_NAME +" TEXT DEFAULT NULL, " +
            COLUMN__CATEGORY_ID +" INTEGER DEFAULT NULL " +
            ")";

    public static final String RESTORE_TABLE =
            "INSERT INTO " + TABLE_NAME + " SELECT * FROM TEMP_" + TABLE_NAME;

    public static final String DROP_TEMP_TABLE =
            "DROP TABLE IF EXISTS TEMP_" + TABLE_NAME;

    public static final String CREATE_TEMP_TABLE =
            "ALTER TABLE " + TABLE_NAME + " RENAME TO TEMP_" + TABLE_NAME;

    private final Context context;

    public SyncWasteSubCategoriesRepository(Context context) {
        this.context = context;
    }

    public void insertWasteSubCategory(List<WasteManagementPojo.GarbageSubCategoryPojo> subCategories) {
        SQLiteDatabase database = AUtils.sqlDBInstance(context);
        for (WasteManagementPojo.GarbageSubCategoryPojo subCategory : subCategories) {
            ContentValues contentValues = new ContentValues();
            contentValues.put(COLUMN_SUBCATEGORY_ID, subCategory.getSubCategoryID());
            contentValues.put(COLUMN_SUBCATEGORY_NAME, subCategory.getGarbageSubCategory());
            contentValues.put(COLUMN__CATEGORY_ID, subCategory.getCategoryID());
            insertSubCategories(database, contentValues);
        }
        database.close();
    }

    public void truncateWasteSubCategories() {
        SQLiteDatabase database = AUtils.sqlDBInstance(context);
        truncateSubCategories(database);
        database.close();
    }

    public List<WasteManagementPojo.GarbageSubCategoryPojo> fetchWasteSubCategories(String categoryId) {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(context);
        List<WasteManagementPojo.GarbageSubCategoryPojo> list = fetchGarbageSubCategories(sqLiteDatabase, categoryId);
        sqLiteDatabase.close();
        return list;
    }

    private static void insertSubCategories(SQLiteDatabase database, ContentValues contentValues) {
        database.insert(TABLE_NAME, null, contentValues);
    }

    private static void truncateSubCategories(SQLiteDatabase database) {
        database.delete(TABLE_NAME, null, null);
    }

    private static List<WasteManagementPojo.GarbageSubCategoryPojo> fetchGarbageSubCategories(SQLiteDatabase database, String categoryId) {
        Cursor cursor = database.rawQuery("SELECT * FROM "+ TABLE_NAME + " WHERE "+ COLUMN__CATEGORY_ID +" = ?", new String[]{categoryId});
        List<WasteManagementPojo.GarbageSubCategoryPojo> subCategories = new ArrayList<>();

        if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
            do {
                WasteManagementPojo.GarbageSubCategoryPojo subCategory = new WasteManagementPojo().new GarbageSubCategoryPojo(
                        cursor.getString(cursor.getColumnIndex(COLUMN_SUBCATEGORY_ID)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SUBCATEGORY_NAME)),
                        cursor.getString(cursor.getColumnIndex(COLUMN_SUBCATEGORY_ID))
                );
                subCategories.add(subCategory);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return subCategories;
    }

}
