package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.UserLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.ArrayList;
import java.util.List;

public class SyncServerRepository {

    private final Context mContext;

    public SyncServerRepository(Context context) {
        mContext = context;
    }

    public void insertSyncServerEntity(String pojo) {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);

        ContentValues values = new ContentValues();
        values.put(SyncServerEntity.COLUMN_DATA, pojo);

        database.insert(AUtils.COLLECTION_TABLE_NAME, null, values);
        database.close();
    }

    public List<SyncServerEntity> getAllSyncServerEntity() {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);

        List<SyncServerEntity> list = new ArrayList<>();

        String sql = "SELECT * FROM " + AUtils.COLLECTION_TABLE_NAME + " ORDER BY " + SyncServerEntity.COLUMN_ID + " DESC";

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                SyncServerEntity syncServerEntity = new SyncServerEntity();
                syncServerEntity.setIndex_id(cursor.getInt(cursor.getColumnIndex(SyncServerEntity.COLUMN_ID)));
                syncServerEntity.setPojo(cursor.getString(cursor.getColumnIndex(SyncServerEntity.COLUMN_DATA)));

                list.add(syncServerEntity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }

    public void deleteSyncServerEntity(int id) {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);

        String whereClause = SyncServerEntity.COLUMN_ID + "= ?";
        String[] args = new String[]{String.valueOf(id)};

        database.delete(AUtils.COLLECTION_TABLE_NAME, whereClause, args);
        database.close();
    }

    public void deleteAllSyncServerEntity() {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);

        database.delete(AUtils.COLLECTION_TABLE_NAME, null, null);
        database.close();
    }
}
