package com.appynitty.swachbharatabhiyanlibrary.connection;

import android.content.Context;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpUserDetailAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CheckAttendancePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpInPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpLoginDetailsPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpLoginPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOutPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpWorkHistoryDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.CheckAttendanceWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.EmpLoginWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.EmpPunchWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.UserDetailsWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.VersionCheckWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.WorkHistoryWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.List;

public class EmpSyncServer {

    private static final String TAG = "EmpSyncServer";
    private static Gson gson;
    private final Context context;

    public EmpSyncServer(Context context) {

        this.context = context;
        gson = new Gson();
    }

    public EmpLoginDetailsPojo saveLoginDetails(EmpLoginPojo empLoginPojo) {

        EmpLoginDetailsPojo resultPojo = null;
        try {

            EmpLoginWebService service = Connection.createService(EmpLoginWebService.class, AUtils.SERVER_URL);
            resultPojo = service.saveLoginDetails(
                    AUtils.CONTENT_TYPE,
                    empLoginPojo).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveInPunch(EmpInPunchPojo empInPunchPojo) {

        ResultPojo resultPojo = null;
        try {

            EmpPunchWebService service = Connection.createService(EmpPunchWebService.class, AUtils.SERVER_URL);

            if (Prefs.getString(AUtils.LAT,"").equals("") &&
                    Prefs.getString(AUtils.LONG,"").equals(""))
            {
                Thread.currentThread();
                Thread.sleep(3000);
            }

            empInPunchPojo.setQrEmpId(Prefs.getString(AUtils.PREFS.USER_ID,""));
            empInPunchPojo.setStartLat(Prefs.getString(AUtils.LAT,""));
            empInPunchPojo.setStartLong(Prefs.getString(AUtils.LONG,""));

            Type type = new TypeToken<EmpInPunchPojo>() {
            }.getType();
            Prefs.putString(AUtils.PREFS.IN_PUNCH_POJO, gson.toJson(empInPunchPojo, type));

            resultPojo = service.saveInPunchDetails(Prefs.getString(AUtils.APP_ID, "1"), AUtils.CONTENT_TYPE,
                    AUtils.getBatteryStatus(), empInPunchPojo).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveOutPunch(EmpOutPunchPojo empOutPunchPojo) {

        ResultPojo resultPojo = null;
        try {

            EmpPunchWebService service = Connection.createService(EmpPunchWebService.class, AUtils.SERVER_URL);

            empOutPunchPojo.setQrEmpId(Prefs.getString(AUtils.PREFS.USER_ID,""));
            empOutPunchPojo.setEndLat(Prefs.getString(AUtils.LAT,""));
            empOutPunchPojo.setEndLong(Prefs.getString(AUtils.LONG,""));

            resultPojo = service.saveOutPunchDetails(Prefs.getString(AUtils.APP_ID, "1"), AUtils.CONTENT_TYPE,
                    AUtils.getBatteryStatus(), empOutPunchPojo).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public boolean pullUserDetailsFromServer() {

        UserDetailPojo userDetailPojo = null;

        try {

            UserDetailsWebService service = Connection.createService(UserDetailsWebService.class, AUtils.SERVER_URL);
            userDetailPojo = service.pullUserDetails(Prefs.getString(AUtils.APP_ID, ""),
                    AUtils.CONTENT_TYPE, Prefs.getString(AUtils.PREFS.USER_ID,null),
                    Prefs.getString(AUtils.PREFS.USER_TYPE_ID, "0")).execute().body();

            if (!AUtils.isNull(userDetailPojo)) {

                EmpUserDetailAdapterClass.setUserDetailPojo(userDetailPojo);

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.USER_DETAIL_POJO, null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullWorkHistoryListFromServer(String year, String month) {

        List<TableDataCountPojo.WorkHistory> workHistoryPojoList = null;

        try {

            WorkHistoryWebService service = Connection.createService(WorkHistoryWebService.class, AUtils.SERVER_URL);
            workHistoryPojoList = service.pullEmpWorkHistoryList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.PREFS.USER_ID,null), year, month).execute().body();

            if (!AUtils.isNull(workHistoryPojoList)) {

                Type type = new TypeToken<List<TableDataCountPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.WORK_HISTORY_POJO_LIST, gson.toJson(workHistoryPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.WORK_HISTORY_POJO_LIST, null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullWorkHistoryDetailListFromServer(String fDate) {

        List<EmpWorkHistoryDetailPojo> workHistoryDetailPojoList = null;

        try {

            WorkHistoryWebService service = Connection.createService(WorkHistoryWebService.class, AUtils.SERVER_URL);
            workHistoryDetailPojoList = service.pullEmpWorkHistoryDetailList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.PREFS.USER_ID,null), fDate).execute().body();

            if (!AUtils.isNull(workHistoryDetailPojoList) && !workHistoryDetailPojoList.isEmpty()) {

                Type type = new TypeToken<List<EmpWorkHistoryDetailPojo>>() {
                }.getType();
                Prefs.putString(AUtils.PREFS.WORK_HISTORY_DETAIL_POJO_LIST, gson.toJson(workHistoryDetailPojoList, type));

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.WORK_HISTORY_DETAIL_POJO_LIST, null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public Boolean checkVersionUpdate(){

        Boolean doUpdate = false;

        VersionCheckWebService checkService = Connection.createService(VersionCheckWebService.class, AUtils.SERVER_URL);

        try {
            ResultPojo resultPojo = checkService.checkVersion(Prefs.getString(AUtils.APP_ID, "1"),
                    Prefs.getInt(AUtils.VERSION_CODE, 0)).execute().body();

            if (resultPojo != null) {
                doUpdate = Boolean.parseBoolean(resultPojo.getStatus());
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return doUpdate;
    }

    public CheckAttendancePojo checkAttendance(){

        CheckAttendancePojo checkAttendancePojo = null;

        CheckAttendanceWebService checkAttendanceWebService = Connection.createService(CheckAttendanceWebService.class, AUtils.SERVER_URL);

        try {
            checkAttendancePojo = checkAttendanceWebService.CheckAttendance(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.PREFS.USER_ID, ""),
                    Prefs.getString(AUtils.PREFS.USER_TYPE_ID, "")).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }

        return checkAttendancePojo;
    }
}
