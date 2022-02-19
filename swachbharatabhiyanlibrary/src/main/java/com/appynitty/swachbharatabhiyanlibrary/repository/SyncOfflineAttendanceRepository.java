package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.appynitty.swachbharatabhiyanlibrary.entity.UserDailyAttendanceEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.AttendancePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayan Dey on 17/9/19.
 * It contains CRUD operation methods for offline sync of data from Ghanta Gadi Employee
 */
public class SyncOfflineAttendanceRepository {

    private static final String SYNC_OFFLINE_ATTENDANCE_TABLE = "tableSyncOfflineAttendance";

    public static final int InAttendanceId = 1;
    public static final int OutAttendanceId = 2;
    private final static String COLUMN_DATE_IN = "offlineAttendanceInDate";
    private final static String COLUMN_DATE_OUT = "offlineAttendanceOutDate";
    private static final int DATA_LIMIT = 25;
    private static final int IS_SYNC = 1;
    private static final int NOT_SYNC = 0;
    private final static String COLUMN_ID = "_offlineAttendanceSyncId";
    private final static String COLUMN_POJO = "offlineAttendanceSyncPojo";
    private final static String COLUMN_IS_ATTENDANCE_IN = "offlineSyncIsAttendanceIn";
    private final static String COLUMN_IS_IN_SYNC = "IsInSync";
    private final static String COLUMN_IS_OUT_SYNC = "IsOutSync";


    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " +
            SYNC_OFFLINE_ATTENDANCE_TABLE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_POJO + " TEXT DEFAULT NULL, " +
            COLUMN_IS_ATTENDANCE_IN + " INTEGER DEFAULT 0, " +
            COLUMN_DATE_IN + " DATETIME, " +
            COLUMN_DATE_OUT + " DATETIME, " +
            COLUMN_IS_IN_SYNC + " INTEGER DEFAULT 0, " +
            COLUMN_IS_OUT_SYNC + " INTEGER DEFAULT 0 " +
            ")";

    public static final String RESTORE_TABLE =
            "INSERT INTO " + SYNC_OFFLINE_ATTENDANCE_TABLE + " SELECT * FROM TEMP_" + SYNC_OFFLINE_ATTENDANCE_TABLE;

    public static final String DROP_TEMP_TABLE =
            "DROP TABLE IF EXISTS TEMP_" + SYNC_OFFLINE_ATTENDANCE_TABLE;

    public static final String CREATE_TEMP_TABLE =
            "ALTER TABLE " + SYNC_OFFLINE_ATTENDANCE_TABLE + " RENAME TO TEMP_" + SYNC_OFFLINE_ATTENDANCE_TABLE;

    private final Context mContext;

    public SyncOfflineAttendanceRepository(Context context) {
        this.mContext = context;
    }

    private static void insertAttendanceSyncTableData(SQLiteDatabase sqLiteDatabase, ContentValues contentValues) {
        sqLiteDatabase.insert(SYNC_OFFLINE_ATTENDANCE_TABLE, null, contentValues);
    }

    private static void updateAttendanceSyncTableData(SQLiteDatabase sqLiteDatabase, ContentValues contentValues,
                                                      String OfflineId) {
        String whereClause = COLUMN_ID + " = ?";
        sqLiteDatabase.update(SYNC_OFFLINE_ATTENDANCE_TABLE, contentValues, whereClause, new String[]{OfflineId});
    }

    public static void dropAttendanceSyncTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SYNC_OFFLINE_ATTENDANCE_TABLE);
    }

    private static int deleteAttendanceSyncTableData(SQLiteDatabase sqLiteDatabase, String id) {
        return sqLiteDatabase.delete(SYNC_OFFLINE_ATTENDANCE_TABLE, COLUMN_ID + " = ?", new String[]{id});
    }

    private static void deleteCompleteAttendanceSyncTableData(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.delete(SYNC_OFFLINE_ATTENDANCE_TABLE, null, null);
        sqLiteDatabase.close();
    }

    private static void performCollectionInsert(SQLiteDatabase sqLiteDatabase, AttendancePojo pojo, int AttendanceType) {

        ContentValues contentValues = new ContentValues();

        String inOutDateTime = AUtils.getServerDateTimeLocal();

        pojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
        pojo.setVehicleNumber(Prefs.getString(AUtils.VEHICLE_NO, ""));
        pojo.setVtId(String.valueOf(Prefs.getString(AUtils.VEHICLE_ID, "0")));
        pojo.setBatteryStatus(String.valueOf(AUtils.getBatteryStatus()));

        if (AttendanceType == InAttendanceId) {
            pojo.setDaDate(AUtils.getServerDate());
            pojo.setStartTime(AUtils.getServerTime());

            pojo.setStartLat(Prefs.getString(AUtils.LAT, ""));
            pojo.setStartLong(Prefs.getString(AUtils.LONG, ""));

            contentValues.put(COLUMN_DATE_IN, inOutDateTime);
        } else {
            pojo.setDaEndDate(AUtils.getServerDate());
            pojo.setEndTime(AUtils.getServerTime());

            pojo.setEndLat(Prefs.getString(AUtils.LAT, ""));
            pojo.setEndLong(Prefs.getString(AUtils.LONG, ""));

            contentValues.put(COLUMN_DATE_OUT, inOutDateTime);
        }

        Type typeNew = new TypeToken<AttendancePojo>() {
        }.getType();
        String mData = new Gson().toJson(pojo, typeNew);
        contentValues.put(COLUMN_POJO, mData);

        contentValues.put(COLUMN_IS_ATTENDANCE_IN, AttendanceType);

        String offlineId = null;
        if (AttendanceType == OutAttendanceId) {
            offlineId = checkAvailableInData(sqLiteDatabase, contentValues);


            updateAttendanceSyncTableData(sqLiteDatabase, contentValues, offlineId);
        } else
            insertAttendanceSyncTableData(sqLiteDatabase, contentValues);
    }

    private static String checkAvailableInData(SQLiteDatabase sqLiteDatabase, ContentValues contentValues) {

        String sql = "SELECT * FROM " + SYNC_OFFLINE_ATTENDANCE_TABLE + " WHERE " +
                COLUMN_IS_ATTENDANCE_IN + " = ? ORDER BY " + COLUMN_DATE_IN + " DESC";

        String[] param = new String[]{String.valueOf(InAttendanceId)};

        Cursor cursor = sqLiteDatabase.rawQuery(sql, param);
        if (cursor.moveToFirst()) {
            if (cursor.getColumnCount() > 0) {
                return String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            }
        }

        cursor.close();

        return null;
    }

    public void dropAttendanceSyncTable() {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        dropAttendanceSyncTable(sqLiteDatabase);
        sqLiteDatabase.close();
    }

    public List<UserDailyAttendanceEntity> fetchOfflineAttendanceData(String offset) {
        if (AUtils.isNull(offset))
            offset = "0";
        List<UserDailyAttendanceEntity> mList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        String sql = "select * from " + SYNC_OFFLINE_ATTENDANCE_TABLE + " order by " +
                COLUMN_DATE_IN + " limit " + DATA_LIMIT + " offset " + offset;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
            mList.clear();
            do {
                UserDailyAttendanceEntity entity = new UserDailyAttendanceEntity();
                entity.setAttendanceId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                entity.setAttendanceData(cursor.getString(cursor.getColumnIndex(COLUMN_POJO)));
                entity.setIsAttendanceIn(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_ATTENDANCE_IN))));
                entity.setAttendanceInDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_IN)));
                entity.setAttendanceOutDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OUT)));
                entity.setAttendanceInSync(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_IN_SYNC))));
                entity.setAttendanceOutSync(String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_IS_OUT_SYNC))));

                mList.add(entity);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mList;
    }

    public List<UserDailyAttendanceEntity> fetchOfflineAttendance(String offset) {
        if (AUtils.isNull(offset))
            offset = "0";
        List<UserDailyAttendanceEntity> mList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        String sql = "select * from " + SYNC_OFFLINE_ATTENDANCE_TABLE +
                " WHERE " + COLUMN_IS_ATTENDANCE_IN + " = ?" +
                " AND date(" + COLUMN_DATE_IN + ") = ? order by " + COLUMN_DATE_IN +
                " limit " + DATA_LIMIT + " offset " + offset;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{"true", AUtils.getLocalDate()});

        if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
            mList.clear();
            do {
                UserDailyAttendanceEntity entity = new UserDailyAttendanceEntity();
                entity.setAttendanceId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                entity.setAttendanceData(cursor.getString(cursor.getColumnIndex(COLUMN_POJO)));
                entity.setIsAttendanceIn(cursor.getString(cursor.getColumnIndex(COLUMN_IS_ATTENDANCE_IN)));
                entity.setAttendanceInDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_IN)));
                entity.setAttendanceOutDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE_OUT)));
                entity.setAttendanceInSync(cursor.getString(cursor.getColumnIndex(COLUMN_IS_IN_SYNC)));
                entity.setAttendanceOutSync(cursor.getString(cursor.getColumnIndex(COLUMN_IS_OUT_SYNC)));
                mList.add(entity);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mList;
    }

    public int deleteAttendanceSyncTableData(String id) {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        int i = deleteAttendanceSyncTableData(sqLiteDatabase, id);
        sqLiteDatabase.close();
        return i;
    }

    public void deleteCompleteAttendanceSyncTableData() {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        deleteCompleteAttendanceSyncTableData(sqLiteDatabase);
        sqLiteDatabase.close();
    }

    public void insertCollection(AttendancePojo pojo, int attendanceType) {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        performCollectionInsert(sqLiteDatabase, pojo, attendanceType);
        sqLiteDatabase.close();
    }

    public AttendancePojo checkAttendance() {

        AttendancePojo pojo;

        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);

        String sql = "SELECT * FROM " + SYNC_OFFLINE_ATTENDANCE_TABLE + " WHERE " +
                COLUMN_IS_ATTENDANCE_IN + " = ? ORDER BY " + COLUMN_DATE_IN + " DESC";

        String[] param = new String[]{String.valueOf(InAttendanceId)};

        Cursor cursor = sqLiteDatabase.rawQuery(sql, param);
        if (cursor.moveToFirst()) {
            if (cursor.getColumnCount() > 0) {
                Type type = new TypeToken<AttendancePojo>() {
                }.getType();

                pojo = new Gson().fromJson(
                        cursor.getString(cursor.getColumnIndex(COLUMN_POJO)), type);
                return pojo;
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return null;
    }

    public boolean checkIsAttendanceIn() {

        boolean isAttendanceIn = false;

        AttendancePojo pojo;

        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);

        String sql = "SELECT * FROM " + SYNC_OFFLINE_ATTENDANCE_TABLE + " WHERE " +
                COLUMN_IS_ATTENDANCE_IN + " = ? ORDER BY " + COLUMN_DATE_IN + " DESC";

        String[] param = new String[]{String.valueOf(InAttendanceId)};

        Cursor cursor = sqLiteDatabase.rawQuery(sql, param);
        if (cursor.moveToFirst()) {
            if (cursor.getColumnCount() > 0) {
                Type type = new TypeToken<AttendancePojo>() {
                }.getType();

                pojo = new Gson().fromJson(
                        cursor.getString(cursor.getColumnIndex(COLUMN_POJO)), type);

                if (!AUtils.isNull(pojo)) {
                    isAttendanceIn = true;
                }
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return isAttendanceIn;
    }

    public void updateIsInSync(String id) {

        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);

        String sql = "UPDATE " + SYNC_OFFLINE_ATTENDANCE_TABLE + " SET " + COLUMN_IS_IN_SYNC + " = " + IS_SYNC
                + " WHERE " + COLUMN_ID + " = " + id;

        sqLiteDatabase.execSQL(sql);
        sqLiteDatabase.close();
    }

    public boolean checkIsInAttendanceSync() {

        Boolean b = false;

        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);

        String sql = "SELECT * FROM " + SYNC_OFFLINE_ATTENDANCE_TABLE + " WHERE " +
                COLUMN_IS_ATTENDANCE_IN + " = ? ORDER BY " + COLUMN_DATE_IN + " DESC";

        String[] param = new String[]{String.valueOf(InAttendanceId)};

        Cursor cursor = sqLiteDatabase.rawQuery(sql, param);
        if (cursor.moveToFirst()) {
            if (cursor.getColumnCount() > 0) {

                int isSync = cursor.getInt(cursor.getColumnIndex(COLUMN_IS_IN_SYNC));

                if (isSync == IS_SYNC) {
                    b = true;
                }
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return b;
    }

    public boolean checkAnyInAttendanceSyncAvailable() {

        Boolean b = false;

        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);

        String sql = "SELECT * FROM " + SYNC_OFFLINE_ATTENDANCE_TABLE + " WHERE " +
                COLUMN_IS_IN_SYNC + " = ? OR "+ COLUMN_IS_OUT_SYNC +" = ? ORDER BY " + COLUMN_DATE_IN + " DESC";

        String[] param = new String[]{String.valueOf(NOT_SYNC), String.valueOf(NOT_SYNC)};

        Cursor cursor = sqLiteDatabase.rawQuery(sql, param);
        if (cursor.moveToFirst()) {
            if (cursor.getColumnCount() > 0) {
                b = true;
            }
        }

        cursor.close();
        sqLiteDatabase.close();

        return b;
    }

    public void performCollectionInsert(Context mContext, AttendancePojo pojo, String date) {

        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);

        ContentValues contentValues = new ContentValues();

        String outDate = date;
if(TextUtils.isEmpty(outDate))
        pojo.setDaEndDate(AUtils.serverDateFromLocal(outDate));
        pojo.setEndTime(AUtils.serverTimeFromLocal(outDate));

        pojo.setEndLat(Prefs.getString(AUtils.LAT, ""));
        pojo.setEndLong(Prefs.getString(AUtils.LONG, ""));

        pojo.setBatteryStatus(String.valueOf(AUtils.getBatteryStatus()));

        contentValues.put(COLUMN_DATE_OUT, outDate);

        Type typeNew = new TypeToken<AttendancePojo>() {
        }.getType();
        String mData = new Gson().toJson(pojo, typeNew);
        contentValues.put(COLUMN_POJO, mData);

        contentValues.put(COLUMN_IS_ATTENDANCE_IN, OutAttendanceId);

        String offlineId = checkAvailableInData(sqLiteDatabase, contentValues);


        updateAttendanceSyncTableData(sqLiteDatabase, contentValues, offlineId);

        sqLiteDatabase.close();
    }
}
