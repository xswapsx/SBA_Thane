package com.appynitty.swachbharatabhiyanlibrary.entity;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.SbaDatabase;

import java.util.ArrayList;
import java.util.List;

public class EmpSyncServerEntity {

    public static final String COLUMN_ID = "_qrid";
    public static final String COLUMN_DATA = "qr_pojo";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + AUtils.QR_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DATA + " TEXT DEFAULT NULL)";

    public static final String RESTORE_TABLE =
            "INSERT INTO " + AUtils.QR_TABLE_NAME + " SELECT * FROM TEMP_" + AUtils.QR_TABLE_NAME;

    public static final String DROP_TEMP_TABLE =
            "DROP TABLE IF EXISTS TEMP_" + AUtils.QR_TABLE_NAME;

    public static final String CREATE_TEMP_TABLE =
            "ALTER TABLE " + AUtils.QR_TABLE_NAME + " RENAME TO TEMP_" + AUtils.QR_TABLE_NAME;

    private int index_id;

    private String pojo;

    public int getIndex_id() {
        return index_id;
    }

    public void setIndex_id(int index_id) {
        this.index_id = index_id;
    }

    public String getPojo() {
        return pojo;
    }

    public void setPojo(String pojo) {
        this.pojo = pojo;
    }

    @Override
    public String toString() {
        return "EmpSyncServerEntity{"
                + "index_id=" + index_id
                + ", pojo='" + pojo + '\'' + '}';
    }
}
