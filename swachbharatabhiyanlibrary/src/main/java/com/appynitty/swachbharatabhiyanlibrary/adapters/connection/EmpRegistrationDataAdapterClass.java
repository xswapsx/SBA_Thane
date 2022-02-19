package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import androidx.annotation.NonNull;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpRegistrationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.QrLocationWebService;
import com.pixplicity.easyprefs.library.Prefs;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 20/6/19.
 */
public class EmpRegistrationDataAdapterClass {

    private EmpRegistrationDetailsListner empRegistrationDetailsListner;

    public void setEmpRegistrationDetailsListner(EmpRegistrationDetailsListner empQrLocationListner) {
        this.empRegistrationDetailsListner = empQrLocationListner;
    }

    public void fetchRegistrationDetails(QrLocationPojo submitPojo){
        QrLocationWebService qrLocationWebService = Connection.createService(QrLocationWebService.class,
                AUtils.SERVER_URL);
        qrLocationWebService.fetchRegistrationDetails(
                Prefs.getString(AUtils.APP_ID, ""),
                submitPojo.getReferanceId(), submitPojo.getGcType())
                .enqueue(new Callback<EmpRegistrationPojo>() {
                    @Override
                    public void onResponse(@NonNull Call<EmpRegistrationPojo> call, @NonNull Response<EmpRegistrationPojo> response) {
                        if(response.code() == 200)
                            empRegistrationDetailsListner.onSuccessCallback(response.body());
                        else
                            empRegistrationDetailsListner.onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<EmpRegistrationPojo> call, @NonNull Throwable t) {
                        empRegistrationDetailsListner.onErrorCallback();
                        t.printStackTrace();
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Throwable");
                    }
                });
    }

    public interface EmpRegistrationDetailsListner{
        void onSuccessCallback(EmpRegistrationPojo empRegistrationPojo);
        void onFailureCallback();
        void onErrorCallback();
    }
}
