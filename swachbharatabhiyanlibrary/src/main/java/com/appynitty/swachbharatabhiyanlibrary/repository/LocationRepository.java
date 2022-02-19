package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.UserLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.ArrayList;
import java.util.List;

public class LocationRepository {

    private final Context mContext;

    public LocationRepository(Context context) {
        mContext = context;
    }

    public void insertUserLocationEntity(String pojo) {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);

        ContentValues values = new ContentValues();
        values.put(UserLocationEntity.COLUMN_DATA, pojo);

        database.insert(AUtils.LOCATION_TABLE_NAME, null, values);
        database.close();
    }

    public List<UserLocationEntity> getAllUserLocationEntity() {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);

        List<UserLocationEntity> list = new ArrayList<>();

        String sql = "SELECT * FROM " + AUtils.LOCATION_TABLE_NAME + " ORDER BY " + UserLocationEntity.COLUMN_ID + " DESC";

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                UserLocationEntity userLocationEntity = new UserLocationEntity();
                userLocationEntity.setIndex_id(cursor.getInt(cursor.getColumnIndex(UserLocationEntity.COLUMN_ID)));
                userLocationEntity.setPojo(cursor.getString(cursor.getColumnIndex(UserLocationEntity.COLUMN_DATA)));

                list.add(userLocationEntity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }

    public void deleteUserLocationEntity(int id) {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);

        String whereClause = UserLocationEntity.COLUMN_ID + "= ?";
        String[] args = new String[]{String.valueOf(id)};

        database.delete(AUtils.LOCATION_TABLE_NAME, whereClause, args);
        database.close();
    }

    public void deleteAllUserLocationEntity() {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);

        database.delete(AUtils.LOCATION_TABLE_NAME, null, null);
        database.close();
    }
}
