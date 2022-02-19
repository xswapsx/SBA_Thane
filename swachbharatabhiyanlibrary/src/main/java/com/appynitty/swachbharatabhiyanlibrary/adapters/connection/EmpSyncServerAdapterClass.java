package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.EmpSyncServerRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.QrLocationWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 28/5/19.
 */
public class EmpSyncServerAdapterClass {

    EmpSyncServerAdapterClass.EmpSyncOfflineListener empSyncOfflineListener;

    private final List<QrLocationPojo> locationPojoList;
    private final Gson gson;
    private final EmpSyncServerRepository empSyncServerRepository;

    public EmpSyncServerAdapterClass() {
        empSyncServerRepository = new EmpSyncServerRepository(AUtils.mainApplicationConstant.getApplicationContext());
        locationPojoList = new ArrayList<>();
        gson = new Gson();
    }

    public void setSyncOfflineListener(EmpSyncServerAdapterClass.EmpSyncOfflineListener empSyncOfflineListener) {
        this.empSyncOfflineListener = empSyncOfflineListener;
    }

    public void syncServer() {
        if (!AUtils.isEmpSyncServerRequestEnable) {

            getDatabaseList();

            if (locationPojoList.size() > 0) {

                AUtils.isEmpSyncServerRequestEnable = true;

                Collections.reverse(locationPojoList);

                QrLocationWebService service = Connection.createService(QrLocationWebService.class, AUtils.SERVER_URL);

                service.saveQrLocationDetailsOffline(Prefs.getString(AUtils.APP_ID, ""), AUtils.CONTENT_TYPE, locationPojoList).enqueue(new Callback<List<OfflineGcResultPojo>>() {
                    @Override
                    public void onResponse(Call<List<OfflineGcResultPojo>> call, Response<List<OfflineGcResultPojo>> response) {

                        if (response.code() == 200) {
                            onResponseReceived(response.body());
                            if (locationPojoList.size() > 0) {

                            } else {
                                empSyncOfflineListener.onSuccessCallback();
                            }

                        } else {
                            Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + response.code());
                            AUtils.isEmpSyncServerRequestEnable = false;
                            empSyncOfflineListener.onFailureCallback();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OfflineGcResultPojo>> call, Throwable t) {
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                        AUtils.isEmpSyncServerRequestEnable = false;
                        empSyncOfflineListener.onErrorCallback();
                    }
                });
            } else {
                if (empSyncOfflineListener != null)
                    empSyncOfflineListener.onSuccessCallback();
            }
        } else {
            if (empSyncOfflineListener != null)
                empSyncOfflineListener.onFailureCallback();
        }
    }

    private void getDatabaseList() {

        List<EmpSyncServerEntity> entityList = empSyncServerRepository.getAllEmpSyncServerEntity();
        locationPojoList.clear();
        for (EmpSyncServerEntity entity : entityList) {
            Type type = new TypeToken<QrLocationPojo>() {
            }.getType();
            QrLocationPojo pojo = gson.fromJson(entity.getPojo(), type);

            pojo.setOfflineID(String.valueOf(entity.getIndex_id()));
            locationPojoList.add(pojo);
        }
    }

    private void onResponseReceived(List<OfflineGcResultPojo> results) {

        if (!AUtils.isNull(results) && results.size() > 0) {

            for (OfflineGcResultPojo result : results) {

                if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {

                    if (Integer.parseInt(result.getID()) != 0) {
                        empSyncServerRepository.deleteEmpSyncServerEntity(Integer.parseInt(result.getID()));
                    }

                    for (int i = 0; i < locationPojoList.size(); i++) {
                        if (locationPojoList.get(i).getOfflineID().equals(result.getID())) {
                            locationPojoList.remove(i);
                            break;
                        }
                    }

                } else {
                    if (Integer.parseInt(result.getID()) != 0) {
                        empSyncServerRepository.deleteEmpSyncServerEntity(Integer.parseInt(result.getID()));
                    }

                    for (int i = 0; i < locationPojoList.size(); i++) {
                        if (locationPojoList.get(i).getOfflineID().equals(result.getID())) {
                            locationPojoList.remove(i);
                            break;
                        }
                    }
                }
            }
        }
        AUtils.isEmpSyncServerRequestEnable = false;


    }

    public interface EmpSyncOfflineListener {
        void onSuccessCallback();

        void onFailureCallback();

        void onErrorCallback();
    }

    public interface TestEvents {
        void onSuccess();

        void onFailed();
    }


}
