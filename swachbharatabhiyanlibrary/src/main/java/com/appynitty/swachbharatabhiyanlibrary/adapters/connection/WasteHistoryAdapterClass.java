package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import androidx.annotation.NonNull;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementHistoryPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.WorkHistoryWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 31/1/20.
 */
public class WasteHistoryAdapterClass {

    private WasteHistoryAdapterClass.HistoryListener mListener;

    public void setHistoryListener(WasteHistoryAdapterClass.HistoryListener mListener) {
        this.mListener = mListener;
    }

    public void fetchHistory(String year, String month) {
        WorkHistoryWebService webService = Connection.createService(WorkHistoryWebService.class, AUtils.SERVER_URL);
        webService.pullWasteManagementHistory(Prefs.getString(AUtils.APP_ID, ""),
                Prefs.getString(AUtils.PREFS.USER_ID, ""), year, month)
                .enqueue(new Callback<List<WasteManagementHistoryPojo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<WasteManagementHistoryPojo>> call,@NonNull Response<List<WasteManagementHistoryPojo>> response) {
                        if (response.code() == 200) {
                            mListener.onSuccessCallBack(response.body());
                        } else {
                            mListener.onFailureCallBack();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<WasteManagementHistoryPojo>> call,@NonNull Throwable t) {
                        mListener.onErrorCallBack();
                    }
                });

    }

    public void fetchHistoryDetails(String date) {
        WorkHistoryWebService webService = Connection.createService(WorkHistoryWebService.class, AUtils.SERVER_URL);
        webService.pullWasteManagementHistoryDetails(Prefs.getString(AUtils.APP_ID, ""),
                Prefs.getString(AUtils.PREFS.USER_ID, ""), date)
                .enqueue(new Callback<List<WasteManagementHistoryPojo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<WasteManagementHistoryPojo>> call,@NonNull Response<List<WasteManagementHistoryPojo>> response) {
                        if (response.code() == 200) {
                            mListener.onSuccessCallBack(response.body());
                        } else {
                            mListener.onFailureCallBack();
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<List<WasteManagementHistoryPojo>> call,@NonNull Throwable t) {
                        mListener.onErrorCallBack();
                    }
                });

    }

    public interface HistoryListener {
        void onSuccessCallBack(List<WasteManagementHistoryPojo> data);
        void onErrorCallBack();
        void onFailureCallBack();
    }
}
