package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.entity.UserLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserLocationResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.LocationRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.UserLocationWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShareLocationAdapterClass {

    private ShareLocationListener mListener;

    HashMap<String, UserLocationPojo> hashMap = new HashMap<>();

    private final LocationRepository mLocationRepository;

    private final String empType = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null);
    ;

    private final List<UserLocationPojo> userLocationPojoList;

    public ShareLocationAdapterClass() {
        mLocationRepository = new LocationRepository(AUtils.mainApplicationConstant.getApplicationContext());
        userLocationPojoList = new ArrayList<>();
    }

    public ShareLocationListener getShareLocationListener() {
        return mListener;
    }

    public void setShareLocationListener(ShareLocationListener mListener) {
        this.mListener = mListener;
    }

    /**
     * Share Location To server
     * <p>
     * set Hashmap for not send multiple location
     *
     * @param userLocationPojos
     */
    public void shareLocation(final List<UserLocationPojo> userLocationPojos) {
        if (userLocationPojos.size() > 0) {
            for (UserLocationPojo user : userLocationPojos) {

                if (!hashMap.containsKey(user.getDatetime())) {
                    hashMap.put(user.getDatetime(), user);
                }

            }

            hitLocationServer(new ArrayList<UserLocationPojo>(hashMap.values()));

        }


    }

    private void hitLocationServer(final List<UserLocationPojo> userLocationPojos) {

        UserLocationWebService service = Connection.createService(UserLocationWebService.class, AUtils.SERVER_URL);


        service.saveUserLocation(Prefs.getString(AUtils.APP_ID, "1"),
                AUtils.CONTENT_TYPE,
                Prefs.getString(AUtils.PREFS.USER_TYPE_ID, "0"), empType,
                AUtils.getBatteryStatus(), userLocationPojos)
                .enqueue(new Callback<List<UserLocationResultPojo>>() {
                    @Override
                    public void onResponse(Call<List<UserLocationResultPojo>> call, Response<List<UserLocationResultPojo>> response) {

                        if (response.code() == 200) {
                            List<UserLocationResultPojo> resultPojoList = response.body();
                            if (!AUtils.isNull(resultPojoList) && resultPojoList.size() > 0) {
                                for (UserLocationResultPojo pojo : resultPojoList) {
                                    if (pojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                                        if (!AUtils.isNull(mListener)) {
                                            mListener.onSuccessCallBack(pojo.getIsAttendenceOff());
                                            hashMap.clear();
                                        }
                                    } else {
                                        if (!AUtils.isNull(mListener)) {
                                            mListener.onFailureCallBack();
                                            hashMap.clear();
                                        }
                                    }
                                }
                            }
                        } else {
                            mListener.onFailureCallBack();
                            Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + response.code());
                            hashMap.clear();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserLocationResultPojo>> call, Throwable t) {
                        for (UserLocationPojo pojo : userLocationPojos) {

                            if (pojo.getOfflineId().equals("0")) {
                                Type type = new TypeToken<UserLocationPojo>() {
                                }.getType();
                                mLocationRepository.insertUserLocationEntity(new Gson().toJson(pojo, type));
                            }
                        }
                        if (!AUtils.isNull(mListener)) {
                            mListener.onFailureCallBack();
                        }
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                    }
                });
    }

    public void shareLocation() {
        if (!AUtils.isLocationRequestEnable) {

            getDBList();
            Log.d("TAG", "shareLocation: " + new Gson().toJson(userLocationPojoList));

            if (userLocationPojoList.size() > 0) {

                AUtils.isLocationRequestEnable = true;

                Collections.reverse(userLocationPojoList);

                UserLocationWebService service = Connection.createService(UserLocationWebService.class, AUtils.SERVER_URL);

                service.saveUserLocation(Prefs.getString(AUtils.APP_ID, "1"), AUtils.CONTENT_TYPE, Prefs.getString(AUtils.PREFS.USER_TYPE_ID, "0"), empType, AUtils.getBatteryStatus(), userLocationPojoList).enqueue(new Callback<List<UserLocationResultPojo>>() {
                    @Override
                    public void onResponse(Call<List<UserLocationResultPojo>> call, Response<List<UserLocationResultPojo>> response) {

                        if (response.code() == 200) {
                            onResponseReceived(response.body(), userLocationPojoList);
                        } else {
                            if (!AUtils.isNull(mListener)) {
                                mListener.onFailureCallBack();
                            }
                            Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + response.code());
                            AUtils.isLocationRequestEnable = false;
                        }
                    }

                    @Override
                    public void onFailure(Call<List<UserLocationResultPojo>> call, Throwable t) {
                        if (!AUtils.isNull(mListener)) {
                            mListener.onFailureCallBack();
                        }
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                        AUtils.isLocationRequestEnable = false;
                    }
                });
            }
        }
    }

    public List<UserLocationResultPojo> saveUserLocation(List<UserLocationPojo> userLocationPojoList) {

        List<UserLocationResultPojo> resultPojo = null;
        try {

            UserLocationWebService service = Connection.createService(UserLocationWebService.class, AUtils.SERVER_URL);


            resultPojo = service.saveUserLocation(Prefs.getString(AUtils.APP_ID, "1"), AUtils.CONTENT_TYPE, Prefs.getString(AUtils.PREFS.USER_ID, null), empType, AUtils.getBatteryStatus(), userLocationPojoList).execute().body();

        } catch (Exception e) {

            e.printStackTrace();
        }
        return resultPojo;
    }

    private void onResponseReceived(List<UserLocationResultPojo> results, List<UserLocationPojo> userLocationPojos) {

        if (!AUtils.isNull(results) && results.size() > 0) {

            for (UserLocationResultPojo result : results) {

                if (Integer.parseInt(result.getId()) != 0) {
                    mLocationRepository.deleteUserLocationEntity(Integer.parseInt(result.getId()));
                }
                for (int i = 0; i < userLocationPojos.size(); i++) {
                    if (userLocationPojos.get(i).getOfflineId().equals(result.getId())) {
                        userLocationPojos.remove(i);
                        break;
                    }
                }

            }
        }
        AUtils.isLocationRequestEnable = false;
    }

    private void getDBList() {
        List<UserLocationEntity> userLocationEntities = mLocationRepository.getAllUserLocationEntity();

        if (userLocationEntities.size() > 0) {

            userLocationPojoList.clear();

            for (UserLocationEntity entity : userLocationEntities) {

                Type type = new TypeToken<UserLocationPojo>() {
                }.getType();
                UserLocationPojo pojo = new Gson().fromJson(entity.getPojo(), type);
                pojo.setOfflineId(String.valueOf(entity.getIndex_id()));

                userLocationPojoList.add(pojo);
            }
        }
    }

    public interface ShareLocationListener {
        void onSuccessCallBack(boolean isAttendanceOff);

        void onFailureCallBack();
    }
}
