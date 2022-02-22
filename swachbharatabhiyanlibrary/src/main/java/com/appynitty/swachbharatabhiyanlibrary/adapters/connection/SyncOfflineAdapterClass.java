package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.content.Context;
import android.util.Log;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncOfflineEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SyncOfflinePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.GarbageCollectionWebService;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 9/10/19.
 */
public class SyncOfflineAdapterClass {

    private SyncOfflineListener syncOfflineListener;
    private final Context mContext;
    private final SyncOfflineRepository syncOfflineRepository;
    private String offset = "0";
    private final List<SyncOfflinePojo> syncOfflineList;
    private final static String TAG = "SyncOfflineAdapterClass";


    public SyncOfflineAdapterClass(Context context) {
        this.mContext = context;
        syncOfflineRepository = new SyncOfflineRepository(mContext);
        syncOfflineList = new ArrayList<>();
    }

    public void setSyncOfflineListener(SyncOfflineListener syncOfflineListener) {
        this.syncOfflineListener = syncOfflineListener;
    }

    public void SyncOfflineData() {
        if (!AUtils.isSyncOfflineDataRequestEnable) {

            setOfflineData();

            if (syncOfflineList.size() > 0) {
//                String myLevelOS = syncOfflineList.get(1).getLevelOS();
//                Log.e(TAG, "SyncOfflineData: " + myLevelOS);
                AUtils.isSyncOfflineDataRequestEnable = true;

                GarbageCollectionWebService service = Connection.createService(GarbageCollectionWebService.class, AUtils.SERVER_URL);

                service.syncOfflineData(Prefs.getString(AUtils.APP_ID, ""),
                        Prefs.getString(AUtils.PREFS.USER_TYPE_ID, ""),
                        AUtils.getBatteryStatus(), AUtils.CONTENT_TYPE, syncOfflineList)
                        .enqueue(new Callback<List<OfflineGcResultPojo>>() {
                            @Override
                            public void onResponse(Call<List<OfflineGcResultPojo>> call,
                                                   Response<List<OfflineGcResultPojo>> response) {

                                if (response.code() == 200) {
                                    onResponseReceived(response.body());
                                    SyncOfflineData();
                                } else {
                                    Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + response.code());
                                    AUtils.isSyncOfflineDataRequestEnable = false;
                                    syncOfflineListener.onFailureCallback();
                                }
                            }

                            @Override
                            public void onFailure(Call<List<OfflineGcResultPojo>> call, Throwable t) {
                                Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                                AUtils.isSyncOfflineDataRequestEnable = false;
                                syncOfflineListener.onErrorCallback();
                            }
                        });
            } else {
                if (syncOfflineListener != null)
                    syncOfflineListener.onSuccessCallback();
            }
        } else {
            if (syncOfflineListener != null)
                syncOfflineListener.onFailureCallback();
        }

    }

    private void onResponseReceived(List<OfflineGcResultPojo> results) {

        Log.e(SyncOfflineAdapterClass.class.getSimpleName(), results.get(0).getMessage());
        if (!AUtils.isNull(results) && results.size() > 0) {

            for (OfflineGcResultPojo result : results) {

                if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {

                    if (Integer.parseInt(result.getID()) != 0) {
                        int deleteCount = syncOfflineRepository.deleteSyncTableData(result.getID());
                        if (deleteCount == 0) {
                            offset = String.valueOf(Integer.parseInt(offset) + 1);
                        }
                    }
                    for (int i = 0; i < syncOfflineList.size(); i++) {
                        if (syncOfflineList.get(i).getOfflineID().equals(result.getID())) {
                            syncOfflineList.remove(i);
                            break;
                        }
                    }
                } else
                    offset = String.valueOf(Integer.parseInt(offset) + 1);
            }
        }

        AUtils.isSyncOfflineDataRequestEnable = false;
    }

    private void setOfflineData() {
        syncOfflineList.clear();
        List<SyncOfflineEntity> offlineEntities = syncOfflineRepository.fetchOfflineData(offset);
        for (SyncOfflineEntity entity : offlineEntities) {
            SyncOfflinePojo offlinePojo;
            Type typeToken = new TypeToken<SyncOfflinePojo>() {
            }.getType();
            offlinePojo = new Gson().fromJson(entity.getOfflineData(), typeToken);
            offlinePojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
            offlinePojo.setOfflineID(String.valueOf(entity.getOfflineId()));
            offlinePojo.setIsLocation(entity.getOfflineIsLocation());
            offlinePojo.setEmpType(Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null));
//            offlinePojo.setLevelOS("");
            Log.d(TAG, "setOfflineData: " + new Gson().toJson(offlinePojo));
            syncOfflineList.add(offlinePojo);

        }
    }

    public interface SyncOfflineListener {
        void onSuccessCallback();

        void onFailureCallback();

        void onErrorCallback();
    }
}
