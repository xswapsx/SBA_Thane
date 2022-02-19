package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncOfflineEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SyncOfflinePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteManagementRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.WasteManagementWebService;
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
public class SyncOfflineWasteManagementAdapterClass {

    private SyncOfflineListener syncOfflineListener;
    private final Context mContext;
    private final SyncWasteManagementRepository syncWasteManagementRepository;
    private String offset = "0";
    private List<WasteManagementPojo> syncWasteManagementList;

    public SyncOfflineWasteManagementAdapterClass(Context context) {
        this.mContext = context;
        syncWasteManagementRepository = new SyncWasteManagementRepository(mContext);
        syncWasteManagementList = new ArrayList<>();
    }

    public void setSyncOfflineListener(SyncOfflineListener syncOfflineListener) {
        this.syncOfflineListener = syncOfflineListener;
    }

    public void syncOfflineWasteManagementData(){
        if(!AUtils.isSyncOfflineWasteManagementDataRequestEnable) {

            setOfflineData();

            if (syncWasteManagementList.size() > 0) {

                AUtils.isSyncOfflineWasteManagementDataRequestEnable = true;

                WasteManagementWebService service = Connection.createService(WasteManagementWebService.class, AUtils.SERVER_URL);

                service.syncOfflineWasteManagementData(Prefs.getString(AUtils.APP_ID, ""),
                        syncWasteManagementList)
                        .enqueue(new Callback<List<WasteManagementPojo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<WasteManagementPojo>> call,
                                           @NonNull Response<List<WasteManagementPojo>> response) {

                        if (response.code() == 200) {
                            onResponseReceived(response.body());
                            syncOfflineWasteManagementData();
                        } else {
                            Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + response.code());
                            AUtils.isSyncOfflineWasteManagementDataRequestEnable = false;
                            syncOfflineListener.onFailureCallback();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<WasteManagementPojo>> call,@NonNull Throwable t) {
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
                        AUtils.isSyncOfflineWasteManagementDataRequestEnable = false;
                        syncOfflineListener.onErrorCallback();
                    }
                });
            }else
                syncOfflineListener.onSuccessCallback();
        }else
            syncOfflineListener.onFailureCallback();

    }

    private void onResponseReceived(List<WasteManagementPojo> results) {

        if (!AUtils.isNull(results) && results.size() > 0) {

            for (WasteManagementPojo result : results) {

                if (result.getStatus().equals(AUtils.STATUS_SUCCESS)) {

                    if (result.getID() != 0) {
                        int deleteCount = syncWasteManagementRepository.removeWasteManagementData(String.valueOf(result.getID()));
                        if(deleteCount == 0){
                            offset = String.valueOf(Integer.parseInt(offset)+1);
                        }
                    }
                    for (int i = 0; i < syncWasteManagementList.size(); i++) {
                        if (syncWasteManagementList.get(i).getID() == result.getID()) {
                            syncWasteManagementList.remove(i);
                            break;
                        }
                    }
                }else
                    offset = String.valueOf(Integer.parseInt(offset)+1);
            }
        }

        AUtils.isSyncOfflineWasteManagementDataRequestEnable = false;
    }

    private void setOfflineData() {
        syncWasteManagementList.clear();
        syncWasteManagementList = syncWasteManagementRepository.getWasteManagementData(offset);
    }

    public interface SyncOfflineListener{
        void onSuccessCallback();
        void onFailureCallback();
        void onErrorCallback();
    }
}
