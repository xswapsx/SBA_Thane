package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import androidx.annotation.NonNull;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.QrLocationWebService;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 28/5/19.
 */
public class EmpQrLocationAdapterClass {

    private EmpQrLocationListner empQrLocationListner;

    public void setEmpQrLocationListner(EmpQrLocationListner empQrLocationListner) {
        this.empQrLocationListner = empQrLocationListner;
    }

    public void saveQrLocation(QrLocationPojo submitPojo){
        QrLocationWebService qrLocationWebService = Connection.createService(QrLocationWebService.class,
                AUtils.SERVER_URL);
        qrLocationWebService.saveQrLocationDetails(
                Prefs.getString(AUtils.APP_ID, ""),
                submitPojo.getReferanceId(), submitPojo.getGcType(), AUtils.CONTENT_TYPE, submitPojo)
                .enqueue(new Callback<ResultPojo>() {
                    @Override
                    public void onResponse(@NonNull Call<ResultPojo> call, @NonNull Response<ResultPojo> response) {
                        if(response.code() == 200)
                            empQrLocationListner.onSuccessCallback(response.body());
                        else
                            empQrLocationListner.onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResultPojo> call, @NonNull Throwable t) {
                        empQrLocationListner.onErrorCallback();
                        t.printStackTrace();
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Throwable");
                    }
                });
    }

    public interface EmpQrLocationListner{
        void onSuccessCallback(ResultPojo resultPojo);
        void onFailureCallback();
        void onErrorCallback();
    }
}
