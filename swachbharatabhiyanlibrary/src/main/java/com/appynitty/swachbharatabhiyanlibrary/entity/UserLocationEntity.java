package com.appynitty.swachbharatabhiyanlibrary.entity;


import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

public class UserLocationEntity {

    public static final String COLUMN_ID = "_locid";
    public static final String COLUMN_DATA = "loc_pojo";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + AUtils.LOCATION_TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    COLUMN_DATA + " TEXT DEFAULT NULL)";

    public static final String RESTORE_TABLE =
            "INSERT INTO " + AUtils.LOCATION_TABLE_NAME + " SELECT * FROM TEMP_" + AUtils.LOCATION_TABLE_NAME;

    public static final String DROP_TEMP_TABLE =
            "DROP TABLE IF EXISTS TEMP_" + AUtils.LOCATION_TABLE_NAME;

    public static final String CREATE_TEMP_TABLE =
            "ALTER TABLE " + AUtils.LOCATION_TABLE_NAME + " RENAME TO TEMP_" + AUtils.LOCATION_TABLE_NAME;

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
        return "UserLocationEntity{"
                + "index_id=" + index_id
                + ", pojo='" + pojo + '\'' + '}';
    }
}
