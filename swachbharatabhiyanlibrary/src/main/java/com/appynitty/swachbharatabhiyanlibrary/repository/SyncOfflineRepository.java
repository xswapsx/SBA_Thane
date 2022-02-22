package com.appynitty.swachbharatabhiyanlibrary.repository;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncOfflineEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.UserLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGarbageColectionPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SyncOfflinePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Ayan Dey on 17/9/19.
 * It contains CRUD operation methods for offline sync of data from Ghanta Gadi Employee
 */
public class SyncOfflineRepository {

    public static final String SYNC_OFFLINE_TABLE = "tableSyncOffline";
    private static final int isCollectionId = 0;
    private static final int isLocationId = 1;
    private static final int houseCollectionId = 1;
    private static final int pointCollectionId = 2;
    private static final int dumpCollectionId = 3;
    private static final int liquidCollectionId = 4;// added by swapnil
    private static final int streetCollectionId = 5;// added by swapnil
    private static final int candDCollectionId = 6;//added by rahul
    private static final int hortCollectionId = 7;//added by rahul
    private static final int commercialCollectionId = 9;// added by swapnil

    private static final String C_TYPE_RNC = "R";
    private static final String C_TYPE_RBC = "RBW";
    private static final String C_TYPE_RSC = "RSW";
    private static final String C_TYPE_CC = "CW";


    private static final int DATA_LIMIT = 25;
    private final static String COLUMN_ID = "_offlineSyncId";
    private final static String COLUMN_POJO = "offlineSyncPojo";
    public final static String COLUMN_DATE = "offlineSyncDate";
    private final static String COLUMN_REFERENCE_ID = "offlineSyncRefID";
    private final static String COLUMN_GC_TYPE = "offlineSyncGcType";
    private final static String COLUMN_C_TYPE = "offlineSyncCType";
    private final static String COLUMN_IS_LOCATION = "offlineSyncIsLocation";

    public static final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS " + SyncOfflineRepository.SYNC_OFFLINE_TABLE + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_POJO + " TEXT DEFAULT NULL, " +
            COLUMN_REFERENCE_ID + " TEXT DEFAULT NULL, " +
            COLUMN_GC_TYPE + " INTEGER DEFAULT 0, " +
            COLUMN_C_TYPE + " TEXT DEFAULT NULL, " +
            COLUMN_IS_LOCATION + " TEXT, " +
            COLUMN_DATE + " DATETIME DEFAULT (strftime('%Y-%m-%d %H:%M:%f','now', 'localtime')))";

    public static final String RESTORE_TABLE =
            "INSERT INTO " + SYNC_OFFLINE_TABLE + " SELECT * FROM TEMP_" + SYNC_OFFLINE_TABLE;

    public static final String DROP_TEMP_TABLE =
            "DROP TABLE IF EXISTS TEMP_" + SYNC_OFFLINE_TABLE;

    public static final String CREATE_TEMP_TABLE =
            "ALTER TABLE " + SYNC_OFFLINE_TABLE + " RENAME TO TEMP_" + SYNC_OFFLINE_TABLE;

    private final Context mContext;

    public SyncOfflineRepository(Context context) {
        this.mContext = context;
    }

    private static void insertSyncTableData(SQLiteDatabase sqLiteDatabase, ContentValues contentValues) {
        sqLiteDatabase.insert(SYNC_OFFLINE_TABLE, null, contentValues);
    }

    private static void updateSyncTableData(SQLiteDatabase sqLiteDatabase, ContentValues contentValues, String OfflineId) {
        String whereClause = COLUMN_ID + " = ?";
        sqLiteDatabase.update(SYNC_OFFLINE_TABLE, contentValues, whereClause, new String[]{OfflineId});
    }

    //added by Rahul
    public List<SyncServerEntity> getAllSyncServerEntity() {

        SQLiteDatabase database = AUtils.sqlDBInstance(mContext);
        List<SyncServerEntity> list = new ArrayList<>();

        String sql = "SELECT * FROM " + AUtils.QR_TABLE_NAME + " ORDER BY " + SyncServerEntity.COLUMN_ID + " DESC";

        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                SyncServerEntity syncServerEntity = new SyncServerEntity();
                syncServerEntity.setIndex_id(cursor.getInt(cursor.getColumnIndex(EmpSyncServerEntity.COLUMN_ID)));
                syncServerEntity.setPojo(cursor.getString(cursor.getColumnIndex(EmpSyncServerEntity.COLUMN_DATA)));

                list.add(syncServerEntity);
            } while (cursor.moveToNext());
        }

        cursor.close();
        database.close();
        return list;
    }


    public static void restoreData_1_2(SQLiteDatabase sqLiteDatabase) {

        String sql = "SELECT * FROM TEMP_" + AUtils.COLLECTION_TABLE_NAME;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
            do {

                String data = cursor.getString(cursor.getColumnIndex(SyncServerEntity.COLUMN_DATA));
                Type type = new TypeToken<OfflineGarbageColectionPojo>() {
                }.getType();
                OfflineGarbageColectionPojo pojo = new Gson().fromJson(data, type);
                pojo.setGcDate(AUtils.formatDate(pojo.getGcDate(), AUtils.SERVER_DATE_TIME_FORMATE, AUtils.SERVER_DATE_TIME_FORMATE_LOCAL));
                pojo.setDistance("0");

                performCollectionInsert(sqLiteDatabase, pojo);

            } while (cursor.moveToNext());
        }

        sql = "SELECT * FROM TEMP_" + AUtils.LOCATION_TABLE_NAME;
        cursor = sqLiteDatabase.rawQuery(sql, null);
        if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
            do {

                String data = cursor.getString(cursor.getColumnIndex(UserLocationEntity.COLUMN_DATA));
                Type type = new TypeToken<UserLocationPojo>() {
                }.getType();
                UserLocationPojo pojo = new Gson().fromJson(data, type);
                pojo.setDatetime(AUtils.formatDate(pojo.getDatetime(), AUtils.SERVER_DATE_TIME_FORMATE,
                        AUtils.SERVER_DATE_TIME_FORMATE_LOCAL));
                pojo.setDistance("0");

                performLocationInsert(sqLiteDatabase, pojo);

            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    private static String checkDumpPointAvailableData(SQLiteDatabase sqLiteDatabase, ContentValues contentValues) {

        String collectionDate = AUtils.getServerDateLocal((String) contentValues.get(COLUMN_DATE));
        String sql = "SELECT * FROM " + SYNC_OFFLINE_TABLE + " WHERE " + COLUMN_REFERENCE_ID + " = ? " +
                "AND " + COLUMN_DATE + " BETWEEN ? AND ? order by " + COLUMN_DATE + " desc";
        String start = collectionDate + " 00:00:00";
        String end = collectionDate + " 23:59:59";
        String[] param = new String[]{String.valueOf(contentValues.get(COLUMN_REFERENCE_ID)), start, end};
        Cursor cursor = sqLiteDatabase.rawQuery(sql, param);

        if (cursor.moveToFirst()) {
            Date prevDate = AUtils.parse(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)), AUtils.SERVER_DATE_TIME_FORMATE);
            Date currDate = AUtils.parse(String.valueOf(contentValues.get(COLUMN_DATE)), AUtils.SERVER_DATE_TIME_FORMATE);
            long diff = AUtils.getDifferenceBetweenTime(prevDate, currDate, AUtils.UNITS.unit_min);
            if (cursor.getColumnCount() > 0 && diff < 10 && diff >= 0) {
                return String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            }
        }

        cursor.close();

        return null;
    }

    private static String checkHouseAvailableData(SQLiteDatabase sqLiteDatabase, ContentValues contentValues) {

        String collectionDate = AUtils.getServerDateLocal((String) contentValues.get(COLUMN_DATE));
        String sql = "SELECT * FROM " + SYNC_OFFLINE_TABLE + " WHERE " + COLUMN_REFERENCE_ID + " = ? " +
                "AND " + COLUMN_DATE + " BETWEEN ? AND ?";
        String start = collectionDate + " 00:00:00";
        String end = collectionDate + " 23:59:59";
        String[] param = new String[]{String.valueOf(contentValues.get(COLUMN_REFERENCE_ID)), start, end};

        Cursor cursor = sqLiteDatabase.rawQuery(sql, param);
        if (cursor.moveToFirst()) {
            if (cursor.getColumnCount() > 0) {
                return String.valueOf(cursor.getInt(cursor.getColumnIndex(COLUMN_ID)));
            }
        }

        cursor.close();

        return null;
    }

    public static void dropSyncTable(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SYNC_OFFLINE_TABLE);
    }

    /**
     * IsLOCATION SET 1
     * CAME FROM LOCATION MANAGER
     *
     * @param sqLiteDatabase
     * @param pojo
     */
    private static void performLocationInsert(SQLiteDatabase sqLiteDatabase, UserLocationPojo pojo) {
        ContentValues contentValues = new ContentValues();

        String gcDate = pojo.getDatetime();

        SyncOfflinePojo offlinePojo = new SyncOfflinePojo();
        offlinePojo.setIsLocation(String.valueOf(isLocationId));
        offlinePojo.setLat(pojo.getLat());
        offlinePojo.setLong(pojo.getLong());
        offlinePojo.setGcDate(gcDate);
        offlinePojo.setUserId(pojo.getUserId());
        offlinePojo.setBatteryStatus(String.valueOf(AUtils.getBatteryStatus()));
        offlinePojo.setDistance(pojo.getDistance());
        offlinePojo.setIsOffline(pojo.getIsOffline());
        Type typeNew = new TypeToken<SyncOfflinePojo>() {
        }.getType();
        String mData = new Gson().toJson(offlinePojo, typeNew);
        contentValues.put(COLUMN_POJO, mData);
        contentValues.put(COLUMN_DATE, gcDate);
        contentValues.put(COLUMN_IS_LOCATION, "true");

        insertSyncTableData(sqLiteDatabase, contentValues);
    }

    /**
     * isLocation set 0
     * CAME FROM SCANNER ACTIVITY...
     *
     * @param sqLiteDatabase
     * @param pojo
     */
    private static void performCollectionInsert(SQLiteDatabase sqLiteDatabase, OfflineGarbageColectionPojo pojo) {

        ContentValues contentValues = new ContentValues();

        String gcDate = pojo.getGcDate();
        String gcType = pojo.getGcType();
        String refId = pojo.getReferenceID();
        SyncOfflinePojo offlinePojo = new SyncOfflinePojo();
        offlinePojo.setIsLocation(String.valueOf(isCollectionId));
        offlinePojo.setGcDate(gcDate);
        offlinePojo.setGcType(gcType);
        offlinePojo.setReferenceID(refId);
        offlinePojo.setLat(pojo.getLat());
        offlinePojo.setLong(pojo.getLong());
        offlinePojo.setNote(pojo.getNote());//added by swapnil
        offlinePojo.setEmpType(pojo.getEmpType()); //added by swapnil
        offlinePojo.setLevelOS(pojo.getLevelOS()); //added by swapnil
        offlinePojo.setTOT(pojo.getTOT());
        offlinePojo.setTNS(pojo.getTNS());
        offlinePojo.setGarbageType(pojo.getGarbageType());
        offlinePojo.setTotalDryWeight(pojo.getTotalDryWeight());
        offlinePojo.setTotalWetWeight(pojo.getTotalWetWeight());
        offlinePojo.setTotalGcWeight(pojo.getTotalGcWeight());
        offlinePojo.setVehicleNumber(pojo.getVehicleNumber());
        offlinePojo.setBatteryStatus(String.valueOf(AUtils.getBatteryStatus()));
        offlinePojo.setGpAfterImage(pojo.getGpAfterImage());
        offlinePojo.setGpBeforImage(pojo.getGpBeforImage());
        offlinePojo.setDistance(pojo.getDistance());
        offlinePojo.setIsOffline(pojo.getIsOffline());

        Type typeNew = new TypeToken<SyncOfflinePojo>() {
        }.getType();
        String mData = new Gson().toJson(offlinePojo, typeNew);
        contentValues.put(COLUMN_POJO, mData);
        contentValues.put(COLUMN_DATE, gcDate);
        contentValues.put(COLUMN_GC_TYPE, Integer.parseInt(gcType));
        contentValues.put(COLUMN_REFERENCE_ID, refId);
        contentValues.put(COLUMN_IS_LOCATION, "false");

        String offlineId = null;
        if (gcType.equals(String.valueOf(houseCollectionId))) {
            offlineId = checkHouseAvailableData(sqLiteDatabase, contentValues);
        } else
            offlineId = checkDumpPointAvailableData(sqLiteDatabase, contentValues);

        if (AUtils.isNull(offlineId))
            insertSyncTableData(sqLiteDatabase, contentValues);
        else
            updateSyncTableData(sqLiteDatabase, contentValues, offlineId);
    }

    private static int deleteSyncTableData(SQLiteDatabase sqLiteDatabase, String id) {
        return sqLiteDatabase.delete(SYNC_OFFLINE_TABLE, COLUMN_ID + " = ?", new String[]{id});
    }

    private static void deleteCompleteSyncTableData(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.delete(SYNC_OFFLINE_TABLE, null, null);
        sqLiteDatabase.close();
    }

    public void dropSyncTable() {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        dropSyncTable(sqLiteDatabase);
        sqLiteDatabase.close();
    }

    public List<SyncOfflineEntity> fetchOfflineData(String offset) {
        if (AUtils.isNull(offset))
            offset = "0";
        List<SyncOfflineEntity> mList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        String sql = "select * from " + SYNC_OFFLINE_TABLE + " order by " + COLUMN_DATE + " limit " + DATA_LIMIT + " offset " + offset;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
            mList.clear();
            do {
                SyncOfflineEntity entity = new SyncOfflineEntity();
                entity.setOfflineId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                entity.setOfflineData(cursor.getString(cursor.getColumnIndex(COLUMN_POJO)));
                entity.setOfflineRefId(cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_ID)));
                entity.setOfflineGcType(cursor.getString(cursor.getColumnIndex(COLUMN_GC_TYPE)));
                //  entity.setOfflineCType(cursor.getString(cursor.getColumnIndex(COLUMN_C_TYPE)));

                entity.setOfflineRNC(cursor.getString(cursor.getColumnIndex(COLUMN_C_TYPE)));
                entity.setOfflineRBC(cursor.getString(cursor.getColumnIndex(COLUMN_C_TYPE)));
                entity.setOfflineRSC(cursor.getString(cursor.getColumnIndex(COLUMN_C_TYPE)));
                entity.setOfflineCW(cursor.getString(cursor.getColumnIndex(COLUMN_C_TYPE)));

                entity.setOfflineIsLocation(cursor.getString(cursor.getColumnIndex(COLUMN_IS_LOCATION)));
                entity.setOfflineDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));


                mList.add(entity);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mList;
    }


    public List<SyncOfflineEntity> fetchOfflineLocation(String offset) {
        if (AUtils.isNull(offset))
            offset = "0";
        List<SyncOfflineEntity> mList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        String sql = "select * from " + SYNC_OFFLINE_TABLE + " WHERE " + COLUMN_IS_LOCATION + " = ?" +
                " AND date(" + COLUMN_DATE + ") = ? order by " + COLUMN_DATE + " limit " + DATA_LIMIT + " offset " + offset;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{"true", AUtils.getLocalDate()});

        if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
            mList.clear();
            do {
                SyncOfflineEntity entity = new SyncOfflineEntity();
                entity.setOfflineId(cursor.getString(cursor.getColumnIndex(COLUMN_ID)));
                entity.setOfflineData(cursor.getString(cursor.getColumnIndex(COLUMN_POJO)));
                entity.setOfflineRefId(cursor.getString(cursor.getColumnIndex(COLUMN_REFERENCE_ID)));
                entity.setOfflineGcType(cursor.getString(cursor.getColumnIndex(COLUMN_GC_TYPE)));
                entity.setOfflineIsLocation(cursor.getString(cursor.getColumnIndex(COLUMN_IS_LOCATION)));
                entity.setOfflineDate(cursor.getString(cursor.getColumnIndex(COLUMN_DATE)));

                mList.add(entity);

            } while (cursor.moveToNext());
        }

        cursor.close();
        sqLiteDatabase.close();
        return mList;
    }

    public int deleteSyncTableData(String id) {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        int i = deleteSyncTableData(sqLiteDatabase, id);
        sqLiteDatabase.close();
        return i;
    }

    public void deleteCompleteSyncTableData() {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        deleteCompleteSyncTableData(sqLiteDatabase);
        sqLiteDatabase.close();
    }

    public List<TableDataCountPojo.WorkHistory> fetchCollectionCount() {

        List<TableDataCountPojo.WorkHistory> mList = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);



       /* for street- {"Distance":"0.0","IsLocation":"0","Lat":"21.139466","Long":"79.0595667","ReferenceID":"SSSBA1003",
                "batteryStatus":"100","garbageType":"-1","gcDate":"2022-01-18 16:04:42.000","gcType":"5",
                "isOffline":false,"totalDryWeight":"0.0","totalGcWeight":"0.0","totalWetWeight":"0.0","vehicleNumber":"6"}*/

        String countSql = "with cte as ( " +
                "select distinct date(" + COLUMN_DATE + ") as dte from " + SYNC_OFFLINE_TABLE + " order by " + COLUMN_DATE + " asc)," +
                "cteHp as ( " +
                " select count(*) as hp, date(" + COLUMN_DATE + ") as gcdateHp from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + houseCollectionId +
                " group by date(" + COLUMN_DATE + "))," +
                "cteGp as(" +
                " select count(*) as gp, date(" + COLUMN_DATE + ") as gcdateGp from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + pointCollectionId +
                " group by date(" + COLUMN_DATE + "))," +
                "cteDy as(" +
                " select count(*) as dy, date(" + COLUMN_DATE + ") as gcdateDy from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + dumpCollectionId +
                " group by date(tableSyncOffline.offlineSyncDate))," +
                "cteLw as(" +
                " select count(*) as lw, date(" + COLUMN_DATE + ") as gcdateLw from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + liquidCollectionId +
                " group by date(tableSyncOffline.offlineSyncDate))," +
                "cteSs as(" +
                " select count(*) as ss, date(" + COLUMN_DATE + ") as gcdateSs from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + streetCollectionId +
                " group by date(tableSyncOffline.offlineSyncDate))," +

                "cteCd as(" +
                " select count(*) as cd, date(" + COLUMN_DATE + ") as gcdateCd from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + candDCollectionId +
                " group by date(tableSyncOffline.offlineSyncDate))," +

                "cteHr as(" +
                " select count(*) as hr, date(" + COLUMN_DATE + ") as gcdateHr from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + hortCollectionId +
                " group by date(tableSyncOffline.offlineSyncDate))," +

                "cteRnc as(" +
                " select count(*) as rnc, date(" + COLUMN_DATE + ") as gcdateRnc from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + houseCollectionId +
                /*   " and " +COLUMN_C_TYPE  + " = " + C_TYPE_RNC +*/
                " group by date(tableSyncOffline.offlineSyncDate))," +

                "cteRbc as(" +
                " select count(*) as rbc, date(" + COLUMN_DATE + ") as gcdateRbc from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + houseCollectionId +
                /*  " and " + COLUMN_C_TYPE + " = " + C_TYPE_RBC +*/
                " group by date(tableSyncOffline.offlineSyncDate))," +

                "cteRsc as(" +
                " select count(*) as rsc, date(" + COLUMN_DATE + ") as gcdateRsc from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + houseCollectionId +
                /* " and " + COLUMN_C_TYPE + " = " + C_TYPE_RSC +*/
                " group by date(tableSyncOffline.offlineSyncDate))," +

                "cteCc as(" +
                " select count(*) as cc, date(" + COLUMN_DATE + ") as gcdateCc from " + SYNC_OFFLINE_TABLE +
                " where " + COLUMN_GC_TYPE + " = " + commercialCollectionId +
                /*  " and " + COLUMN_C_TYPE + " = " + C_TYPE_CC +*/
                " group by date(tableSyncOffline.offlineSyncDate))," +

                "ctef as (" +
                " select a.dte, " +
                " case when b.hp is null then 0 else b.hp end as hp, " +
                " case when c.gp is null then 0 else c.gp end as gp, " +
                " case when d.dy is null then 0 else d.dy end as dy, " +
                " case when e.lw is null then 0 else e.lw end as lw, " +
                " case when f.ss is null then 0 else f.ss end as ss, " +
                " case when g.cd is null then 0 else g.cd end as cd, " +
                " case when h.hr is null then 0 else h.hr end as hr, " +
                " case when i.rnc is null then 0 else i.rnc end as rnc, " +
                " case when j.rbc is null then 0 else j.rbc end as rbc, " +
                " case when k.rsc is null then 0 else k.rsc end as rsc," +
                " case when l.cc is null then 0 else l.cc end as cc " +

                " from cte a " +
                " left join cteHp b on b.gcdateHp = a.dte " +
                " left join cteGp c on c.gcdateGp = a.dte " +
                " left join cteDy d on d.gcdateDy = a.dte " +
                " left join cteLw e on e.gcdateLw = a.dte " +
                " left join cteSs f on f.gcdateSs = a.dte " +

                " left join cteCd g on g.gcdateCd = a.dte " +
                " left join cteHr h on h.gcdateHr = a.dte " +
                " left join cteRnc i on i.gcdateRnc = a.dte " +
                " left join cteRbc j on j.gcdateRbc = a.dte " +
                " left join cteRsc k on k.gcdateRsc = a.dte " +
                " left join cteCc l on l.gcdateCc = a.dte " +

                " order by dte desc)" +
                "select * from ctef";

        mList.clear();
        try {
            Cursor cursor = sqLiteDatabase.rawQuery(countSql, null);
            if (cursor.moveToFirst() && cursor.getColumnCount() > 0) {
                do {
                    TableDataCountPojo.WorkHistory entity = new TableDataCountPojo().new WorkHistory();
                    entity.setDate(AUtils.serverDateFromLocal(cursor.getString(cursor.getColumnIndex("dte"))));
                    entity.setHouseCollection(cursor.getString(cursor.getColumnIndex("hp")));
                    entity.setPointCollection(cursor.getString(cursor.getColumnIndex("gp")));
                    entity.setDumpYardCollection(cursor.getString(cursor.getColumnIndex("dy")));
                    entity.setLiquidCollection(cursor.getString(cursor.getColumnIndex("lw")));
                    entity.setStreetCollection(cursor.getString(cursor.getColumnIndex("ss")));

                    entity.setCADCollection(cursor.getString(cursor.getColumnIndex("cd")));
                    entity.setHorticultureCollection(cursor.getString(cursor.getColumnIndex("hr")));
                    entity.setResidentialCollection(cursor.getString(cursor.getColumnIndex("rnc")));
                    entity.setResidentialBCollection(cursor.getString(cursor.getColumnIndex("rbc")));
                    entity.setResidentialSCollection(cursor.getString(cursor.getColumnIndex("rsc")));
                    entity.setCommertialCollection(cursor.getString(cursor.getColumnIndex("cc")));

                    mList.add(entity);
                } while (cursor.moveToNext());
            }
            cursor.close();
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mList;
    }

    public void insetUserLocation(UserLocationPojo pojo) {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        performLocationInsert(sqLiteDatabase, pojo);
        sqLiteDatabase.close();
    }

    /**
     * Insert OfflinePojo
     *
     * @param pojo
     */
    public void insertCollection(OfflineGarbageColectionPojo pojo) {
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        performCollectionInsert(sqLiteDatabase, pojo);
        sqLiteDatabase.close();
    }

    public TableDataCountPojo.LocationCollectionCount getLocationCollectionCount(String date) {
        TableDataCountPojo.LocationCollectionCount locationCollectionCount = new TableDataCountPojo().new LocationCollectionCount();
        SQLiteDatabase sqLiteDatabase = AUtils.sqlDBInstance(mContext);
        try {
            String sql = "select " +
                    "(select count(*) from " + SYNC_OFFLINE_TABLE + " where " + COLUMN_IS_LOCATION + " = ? " +
                    "and date(" + COLUMN_DATE + ") = ?)as countLoc," +
                    "(select count(*) from " + SYNC_OFFLINE_TABLE + " where " + COLUMN_IS_LOCATION + " = ? " +
                    "and date(" + COLUMN_DATE + ") = ?)as countGc";

            Cursor cursor = sqLiteDatabase.rawQuery(sql, new String[]{"true", date, "false", date});
            if (cursor.moveToFirst()) {
                do {
                    locationCollectionCount.setCollectionCount(cursor.getInt(cursor.getColumnIndex("countGc")));
                    locationCollectionCount.setLocationCount(cursor.getInt(cursor.getColumnIndex("countLoc")));
                } while (cursor.moveToNext());
            }

            cursor.close();
            sqLiteDatabase.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return locationCollectionCount;
    }
}
