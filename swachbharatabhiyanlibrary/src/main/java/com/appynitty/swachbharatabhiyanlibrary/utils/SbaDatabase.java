package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.LastLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.UserLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.repository.LastLocationRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineAttendanceRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteCategoriesRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteManagementRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteSubCategoriesRepository;
import com.pixplicity.easyprefs.library.Prefs;

public class SbaDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 3;

    SbaDatabase(Context context) {
        super(context, AUtils.DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(EmpSyncServerEntity.CREATE_TABLE);
        sqLiteDatabase.execSQL(UserLocationEntity.CREATE_TABLE);
        sqLiteDatabase.execSQL(SyncOfflineRepository.CREATE_TABLE);

//        Added in version 2
        sqLiteDatabase.execSQL(LastLocationRepository.CREATE_TABLE);
        sqLiteDatabase.execSQL(SyncOfflineAttendanceRepository.CREATE_TABLE);

//        Added in version 3
        sqLiteDatabase.execSQL(SyncWasteManagementRepository.CREATE_TABLE);
        sqLiteDatabase.execSQL(SyncWasteCategoriesRepository.CREATE_TABLE);
        sqLiteDatabase.execSQL(SyncWasteSubCategoriesRepository.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int prevVersion, int newVersion) {
        if(prevVersion == 2 && newVersion == 3) {
            update_2_3(sqLiteDatabase);
        } else {
            update_1_2(sqLiteDatabase);
        }
    }

    private void update_2_3(SQLiteDatabase sqLiteDatabase){
        sqLiteDatabase.execSQL(EmpSyncServerEntity.CREATE_TEMP_TABLE);
        sqLiteDatabase.execSQL(UserLocationEntity.CREATE_TEMP_TABLE);
        sqLiteDatabase.execSQL(SyncOfflineRepository.CREATE_TEMP_TABLE);
        sqLiteDatabase.execSQL(LastLocationRepository.CREATE_TEMP_TABLE);
        sqLiteDatabase.execSQL(SyncOfflineAttendanceRepository.CREATE_TEMP_TABLE);

        onCreate(sqLiteDatabase);

        sqLiteDatabase.execSQL(EmpSyncServerEntity.RESTORE_TABLE);
        sqLiteDatabase.execSQL(UserLocationEntity.RESTORE_TABLE);
        sqLiteDatabase.execSQL(SyncOfflineRepository.RESTORE_TABLE);
        sqLiteDatabase.execSQL(LastLocationRepository.RESTORE_TABLE);
        sqLiteDatabase.execSQL(SyncOfflineAttendanceRepository.RESTORE_TABLE);

        sqLiteDatabase.execSQL(EmpSyncServerEntity.DROP_TEMP_TABLE);
        sqLiteDatabase.execSQL(UserLocationEntity.DROP_TEMP_TABLE);
        sqLiteDatabase.execSQL(SyncOfflineRepository.DROP_TEMP_TABLE);
        sqLiteDatabase.execSQL(LastLocationRepository.DROP_TEMP_TABLE);
        sqLiteDatabase.execSQL(SyncOfflineAttendanceRepository.DROP_TEMP_TABLE);
    }

    private void update_1_2(SQLiteDatabase sqLiteDatabase){

        sqLiteDatabase.execSQL(EmpSyncServerEntity.CREATE_TEMP_TABLE);
        sqLiteDatabase.execSQL(UserLocationEntity.CREATE_TEMP_TABLE);
        sqLiteDatabase.execSQL(SyncServerEntity.CREATE_TEMP_TABLE);

        onCreate(sqLiteDatabase);

        if(Prefs.getString(AUtils.PREFS.USER_TYPE_ID, "0").equals("0")){
            SyncOfflineRepository.restoreData_1_2(sqLiteDatabase);
        }else{
            sqLiteDatabase.execSQL(EmpSyncServerEntity.RESTORE_TABLE);
            sqLiteDatabase.execSQL(UserLocationEntity.RESTORE_TABLE);
        }

        sqLiteDatabase.execSQL(EmpSyncServerEntity.DROP_TEMP_TABLE);
        sqLiteDatabase.execSQL(UserLocationEntity.DROP_TEMP_TABLE);
        sqLiteDatabase.execSQL(SyncServerEntity.DROP_TEMP_TABLE);
    }
}