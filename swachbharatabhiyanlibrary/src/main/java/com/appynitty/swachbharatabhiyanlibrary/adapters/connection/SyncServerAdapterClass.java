package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGarbageColectionPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncServerRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.GarbageCollectionWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncServerAdapterClass {

    private final SyncServerRepository mSyncServerRepository;
    private final List<OfflineGarbageColectionPojo> offlineGarbageColectionPojoList;


    public SyncServerAdapterClass(){
        mSyncServerRepository = new SyncServerRepository(AUtils.mainApplicationConstant.getApplicationContext());
        offlineGarbageColectionPojoList = new ArrayList<>();
    }

    public void syncServer() {
        if(!AUtils.isSyncServerRequestEnable) {

            getDBList();

            if (offlineGarbageColectionPojoList.size() > 0) {

                AUtils.isSyncServerRequestEnable = true;

                Collections.reverse(offlineGarbageColectionPojoList);

                GarbageCollectionWebService service = Connection.createService(GarbageCollectionWebService.class, AUtils.SERVER_URL);

                service.saveGarbageCollectionOffline(Prefs.getString(AUtils.APP_ID, ""),
                        Prefs.getString(AUtils.PREFS.USER_TYPE_ID, ""),
                        AUtils.getBatteryStatus(), AUtils.CONTENT_TYPE, offlineGarbageColectionPojoList).enqueue(new Callback<List<OfflineGcResultPojo>>() {
                    @Override
                    public void onResponse(Call<List<OfflineGcResultPojo>> call, Response<List<OfflineGcResultPojo>> response) {

                        if (response.code() == 200) {
                            onResponseReceived(response.body());
                        } else {
                            Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + response.code());
                            AUtils.isSyncServerRequestEnable =false;
                        }
                    }

                    @Override
                    public void onFailure(Call<List<OfflineGcResultPojo>> call, Throwable t) {
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                        AUtils.isSyncServerRequestEnable =false;
                    }
                });
            }
        }
    }

    private void onResponseReceived(List<OfflineGcResultPojo> results) {

        if (!AUtils.isNull(results) && results.size() > 0) {

            for (OfflineGcResultPojo result : results) {

                if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {

                    if (Integer.parseInt(result.getID()) != 0) {
                        mSyncServerRepository.deleteSyncServerEntity(Integer.parseInt(result.getID()));
                    }
                    for (int i = 0; i < offlineGarbageColectionPojoList.size(); i++) {
                        if (offlineGarbageColectionPojoList.get(i).getOfflineID().equals(result.getID())) {
                            offlineGarbageColectionPojoList.remove(i);
                            break;
                        }
                    }
                } else {
                    if (Integer.parseInt(result.getID()) != 0) {
                        mSyncServerRepository.deleteSyncServerEntity(Integer.parseInt(result.getID()));
                    }
                    for (int i = 0; i < offlineGarbageColectionPojoList.size(); i++) {
                        if (offlineGarbageColectionPojoList.get(i).getOfflineID().equals(result.getID())) {
                            offlineGarbageColectionPojoList.remove(i);
                            break;
                        }
                    }
                }
            }
        }
        AUtils.isSyncServerRequestEnable =false;
    }

    private void getDBList(){
        List<SyncServerEntity> entityList = mSyncServerRepository.getAllSyncServerEntity();
        offlineGarbageColectionPojoList.clear();
        for (SyncServerEntity entity : entityList) {
            Type type = new TypeToken<OfflineGarbageColectionPojo>() {}.getType();
            OfflineGarbageColectionPojo pojo = new Gson().fromJson(entity.getPojo(), type);
            pojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
            pojo.setOfflineID(String.valueOf(entity.getIndex_id()));
            offlineGarbageColectionPojoList.add(pojo);
        }
    }
}
