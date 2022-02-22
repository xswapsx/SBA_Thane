package com.appynitty.swachbharatabhiyanlibrary.connection;

import android.content.Context;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.UserDetailAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VehicleTypeAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CheckAttendancePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaHousePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionDumpYardPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GarbageCollectionPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.GcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.ImagePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.InPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginDetailsPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OutPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleTypePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WorkHistoryDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.AreaBroadcastWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.AreaHousePointService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.CheckAttendanceWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.GarbageCollectionWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.LoginWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.PunchWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.UserDetailsWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.VehicleTypeWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.VersionCheckWebService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.WorkHistoryWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class SyncServer {

    private static final String TAG = "SyncServer";
    private static Gson gson;
    private final Context context;
    private static String empType = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null);
    private Context mContext;
    private String offset = "0";

    public SyncServer(Context context) {

        this.context = context;
        gson = new Gson();
    }

    public LoginDetailsPojo saveLoginDetails(LoginPojo loginPojo) {

        LoginDetailsPojo resultPojo = null;
        try {

            LoginWebService service = Connection.createService(LoginWebService.class, AUtils.SERVER_URL);
            resultPojo = service.saveLoginDetails(Prefs.getString(AUtils.APP_ID, "")
                    , AUtils.CONTENT_TYPE, loginPojo.getEmployeeType(),
                    loginPojo).execute().body();

//            AUtils.setEmpType(resultPojo.getEmpType());
//            String str = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE,"");

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public GcResultPojo saveGarbageCollection(GarbageCollectionPojo garbageCollectionPojo) {

        GcResultPojo gcResultPojo = null;

        try {

            RequestBody requestBody1 = null;
            MultipartBody.Part imageFileMultiBody1 = null;

            if (!AUtils.isNull(garbageCollectionPojo.getImage1())) {
                File startImageFile = new File(garbageCollectionPojo.getImage1());
                requestBody1 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody1 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody1);
            }

            RequestBody requestBody2 = null;
            MultipartBody.Part imageFileMultiBody2 = null;

            if (!AUtils.isNull(garbageCollectionPojo.getImage2())) {
                File startImageFile = new File(garbageCollectionPojo.getImage2());
                requestBody2 = RequestBody.create(MediaType.parse("multipart/form-data"), startImageFile);
                imageFileMultiBody2 = MultipartBody.Part.createFormData("vmImage1", startImageFile.getName(), requestBody2);
            }

            String pointId = garbageCollectionPojo.getId();
            RequestBody id = RequestBody.create(okhttp3.MultipartBody.FORM, pointId);

            RequestBody userId = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.PREFS.USER_ID, ""));

            RequestBody Lat = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LAT, ""));
            RequestBody Long = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.LONG, ""));

            RequestBody comment = null;
            if (!AUtils.isNull(garbageCollectionPojo.getComment())) {
                comment = RequestBody.create(okhttp3.MultipartBody.FORM, garbageCollectionPojo.getComment());
            }

            RequestBody beforeImage = null;
            if (!AUtils.isNullString(garbageCollectionPojo.getBeforeImage())) {
                beforeImage = RequestBody.create(okhttp3.MultipartBody.FORM, garbageCollectionPojo.getBeforeImage());
            }

            RequestBody afterImage = null;
            if (!AUtils.isNullString(garbageCollectionPojo.getAfterImage())) {
                afterImage = RequestBody.create(okhttp3.MultipartBody.FORM, garbageCollectionPojo.getAfterImage());
            }

            RequestBody vehicleNo = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.VEHICLE_NO, ""));

            RequestBody empType = RequestBody.create(okhttp3.MultipartBody.FORM, Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, ""));
            RequestBody garbageType = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(garbageCollectionPojo.getGarbageType()));

            RequestBody weightTotal = null;
            if (!AUtils.isNull(garbageCollectionPojo.getWeightTotal())) {
                weightTotal = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(garbageCollectionPojo.getWeightTotal()));
            }

            RequestBody weightTotalDry = null;
            if (!AUtils.isNull(garbageCollectionPojo.getWeightTotalDry())) {
                weightTotalDry = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(garbageCollectionPojo.getWeightTotalDry()));
            }

            RequestBody weightTotalWet = null;
            if (!AUtils.isNull(garbageCollectionPojo.getWeightTotalWet())) {
                weightTotalWet = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(garbageCollectionPojo.getWeightTotalWet()));
            }

            GarbageCollectionWebService service = Connection.createService(GarbageCollectionWebService.class, AUtils.SERVER_URL);

            String vehicleId = Prefs.getString(AUtils.VEHICLE_ID, "0");

            if (pointId.substring(0, 2).matches("^[HhPp]+$")) {
                gcResultPojo = service.saveGarbageCollectionH(Prefs.getString(AUtils.APP_ID, ""),
                        AUtils.getBatteryStatus(), userId, id, Lat, Long, beforeImage, afterImage, comment, vehicleNo, imageFileMultiBody1,
                        imageFileMultiBody2, garbageType).execute().body();
            } else if (pointId.substring(0, 2).matches("^[GgPp]+$")) {
                gcResultPojo = service.saveGarbageCollectionGP(Prefs.getString(AUtils.APP_ID, ""),
                        AUtils.getBatteryStatus(), userId, id, Lat, Long, beforeImage, afterImage, comment, vehicleNo, imageFileMultiBody1,
                        imageFileMultiBody2).execute().body();
            } else if (pointId.substring(0, 2).matches("^[DdYy]+$")) {
                gcResultPojo = service.saveGarbageCollectionDy(Prefs.getString(AUtils.APP_ID, ""),
                        AUtils.getBatteryStatus(), userId, id, Lat, Long, beforeImage, afterImage, comment, vehicleNo, imageFileMultiBody1,
                        imageFileMultiBody2, weightTotal, weightTotalDry, empType, weightTotalWet).execute().body();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        return gcResultPojo;
    }

    public static boolean saveImage(ImagePojo imagePojo) {
        if (!AUtils.isNull(imagePojo)) {

            Type type = new TypeToken<ImagePojo>() {
            }.getType();
            Prefs.putString(AUtils.PREFS.IMAGE_POJO + Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID), gson.toJson(imagePojo, type));

            return true;
        } else {
            return false;
        }
    }

    public ResultPojo saveInPunch(InPunchPojo inPunchPojo) {

        ResultPojo resultPojo = null;
        try {

            PunchWebService service = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);

            inPunchPojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
            inPunchPojo.setVtId(String.valueOf(Prefs.getString(AUtils.VEHICLE_ID, "0")));
            inPunchPojo.setStartLat(Prefs.getString(AUtils.LAT, ""));
            inPunchPojo.setStartLong(Prefs.getString(AUtils.LONG, ""));

            Type type = new TypeToken<InPunchPojo>() {
            }.getType();
            Prefs.putString(AUtils.PREFS.IN_PUNCH_POJO, gson.toJson(inPunchPojo, type));

            resultPojo = service.saveInPunchDetails(Prefs.getString(AUtils.APP_ID, "1"), AUtils.CONTENT_TYPE,
                    AUtils.getBatteryStatus(), inPunchPojo).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public ResultPojo saveOutPunch(OutPunchPojo outPunchPojo) {

        ResultPojo resultPojo = null;
        try {

            PunchWebService service = Connection.createService(PunchWebService.class, AUtils.SERVER_URL);

            outPunchPojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
            outPunchPojo.setEndLat(Prefs.getString(AUtils.LAT, ""));
            outPunchPojo.setEndLong(Prefs.getString(AUtils.LONG, ""));

            resultPojo = service.saveOutPunchDetails(Prefs.getString(AUtils.APP_ID, "1"), AUtils.CONTENT_TYPE,
                    AUtils.getBatteryStatus(), outPunchPojo).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    public boolean pullVehicleTypeListFromServer() {

        List<VehicleTypePojo> vehicleTypePojoList = null;

        try {

            VehicleTypeWebService service = Connection.createService(VehicleTypeWebService.class, AUtils.SERVER_URL);
            vehicleTypePojoList = service.pullVehicleTypeList(Prefs.getString(AUtils.APP_ID, ""),
                    AUtils.CONTENT_TYPE).execute().body();

            if (!AUtils.isNull(vehicleTypePojoList) && !vehicleTypePojoList.isEmpty()) {

                VehicleTypeAdapterClass.setVehicleTypePojoList(vehicleTypePojoList);

                return true;
            } else {

                Prefs.putString(AUtils.PREFS.VEHICLE_TYPE_POJO_LIST, null);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return false;
    }

    public boolean pullUserDetailsFromServer() {

        UserDetailPojo userDetailPojo = null;

        try {

            UserDetailsWebService service = Connection.createService(UserDetailsWebService.class, AUtils.SERVER_URL);
            userDetailPojo = service.pullUserDetails(Prefs.getString(AUtils.APP_ID, ""),
                    AUtils.CONTENT_TYPE, Prefs.getString(AUtils.PREFS.USER_ID, null),
                    Prefs.getString(AUtils.PREFS.USER_TYPE_ID, "0")).execute().body();

            if (!AUtils.isNull(userDetailPojo)) {

                UserDetailAdapterClass.setUserDetailPojo(userDetailPojo);

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

        String empTyp = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null);

//        Log.e("SyncServer.class", empTyp);

        try {

            WorkHistoryWebService service = Connection.createService(WorkHistoryWebService.class, AUtils.SERVER_URL);
            workHistoryPojoList = service.pullWorkHistoryList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.PREFS.USER_ID, null), year, month, empTyp).execute().body();

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

        List<WorkHistoryDetailPojo> workHistoryDetailPojoList = null;

        try {

            WorkHistoryWebService service = Connection.createService(WorkHistoryWebService.class, AUtils.SERVER_URL);
            workHistoryDetailPojoList = service.pullWorkHistoryDetailList(Prefs.getString(AUtils.APP_ID, ""),
                    Prefs.getString(AUtils.PREFS.USER_ID, null), fDate,
                    "1").execute().body();

            if (!AUtils.isNull(workHistoryDetailPojoList) && !workHistoryDetailPojoList.isEmpty()) {

                Type type = new TypeToken<List<WorkHistoryDetailPojo>>() {
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

    public Boolean checkVersionUpdate() {

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

    public List<CollectionAreaPojo> fetchCollectionArea(String areaType) {
        LoginPojo loginPojo = new LoginPojo();
        List<CollectionAreaPojo> areaPojoList = null;

        try {

            AreaHousePointService areaHousePointService = Connection.createService(AreaHousePointService.class, AUtils.SERVER_URL);
            areaPojoList = areaHousePointService.fetchCollectionArea(Prefs.getString(AUtils.APP_ID, ""), areaType, Prefs.getString(AUtils.EMP_TYPE, loginPojo.getEmployeeType()))
                    .execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return areaPojoList;
    }

    public List<CollectionAreaHousePojo> fetchCollectionAreaHouse(String areaType, String areaId) {
        LoginPojo loginPojo = new LoginPojo();
        List<CollectionAreaHousePojo> areaPojoList = null;

        try {

            AreaHousePointService areaHousePointService = Connection.createService(AreaHousePointService.class, AUtils.SERVER_URL);
            areaPojoList = areaHousePointService.fetchCollectionAreaHouse(
                    Prefs.getString(AUtils.APP_ID, ""), areaType, areaId, Prefs.getString(AUtils.EMP_TYPE, loginPojo.getEmployeeType()))
                    .execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return areaPojoList;
    }

    public List<CollectionDumpYardPointPojo> fetchCollectionCommercialPoint(String appId, String areaId) {
        List<CollectionDumpYardPointPojo> areaPojoList = null;

        try {

            AreaHousePointService areaHousePointService = Connection.createService(AreaHousePointService.class, AUtils.SERVER_URL);
            areaPojoList = areaHousePointService.fetchCollectionCpPoint(
                    Prefs.getString(AUtils.APP_ID, ""), areaId)
                    .execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return areaPojoList;
    }

    public List<CollectionAreaPointPojo> fetchCollectionAreaPoint(String areaType, String areaId) {

        List<CollectionAreaPointPojo> areaPojoList = null;

        try {

            AreaHousePointService areaHousePointService = Connection.createService(AreaHousePointService.class, AUtils.SERVER_URL);
            areaPojoList = areaHousePointService.fetchCollectionAreaPoint(
                    Prefs.getString(AUtils.APP_ID, ""), areaType, areaId)
                    .execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return areaPojoList;
    }

    //get House id with cType


    public List<CollectionDumpYardPointPojo> fetchCollectionDyPoint(String areaType, String areaId) {

        List<CollectionDumpYardPointPojo> areaPojoList = null;

        try {

            AreaHousePointService areaHousePointService = Connection.createService(AreaHousePointService.class, AUtils.SERVER_URL);
            areaPojoList = areaHousePointService.fetchCollectionDyPoint(
                    Prefs.getString(AUtils.APP_ID, ""), areaType, areaId)
                    .execute().body();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return areaPojoList;
    }

    public void pullAreaBroadcastFromServer(String areaID) {

        ResultPojo resultPojo = null;

        try {

            AreaBroadcastWebService service = Connection.createService(AreaBroadcastWebService.class, AUtils.SERVER_URL);
            resultPojo = service.pullAreaBroadcast(Prefs.getString(AUtils.APP_ID, ""),
                    areaID).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    public CheckAttendancePojo checkAttendance() {

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
