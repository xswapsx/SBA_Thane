package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.util.Log;

import androidx.annotation.NonNull;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.pojos.ZoneWardAreaMasterPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.ZoneWardAreaWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 29/5/19.
 */
public class EmpWardZoneAreaAdapterClass {

    public static final int CALL_REQUEST_ZONE = 1;
    public static final int CALL_REQUEST_WARD_ZONE = 2;
    public static final int CALL_REQUEST_AREA = 3;

    private EmpZoneWardAreaListner empZoneWardAreaListner;

    public void setEmpZoneWardAreaListner(EmpZoneWardAreaListner empZoneWardAreaListner) {
        this.empZoneWardAreaListner = empZoneWardAreaListner;
    }

    public void fetchZone(){
        ZoneWardAreaWebService zoneWardAreaWebService = Connection.createService(ZoneWardAreaWebService.class,
                AUtils.SERVER_URL);
        zoneWardAreaWebService.fetchZone(
                Prefs.getString(AUtils.APP_ID, ""), "")
                .enqueue(new Callback<List<ZoneWardAreaMasterPojo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ZoneWardAreaMasterPojo>> call, @NonNull Response<List<ZoneWardAreaMasterPojo>> response) {
                        if(response.code() == 200)
                            empZoneWardAreaListner.onSuccessCallback(response.body(), CALL_REQUEST_ZONE);
                        else
                            empZoneWardAreaListner.onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ZoneWardAreaMasterPojo>> call, @NonNull Throwable t) {
                        empZoneWardAreaListner.onErrorCallback();
                        t.printStackTrace();
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Throwable");
                    }
                });
    }

    public void fetchWardZone(){
        ZoneWardAreaWebService zoneWardAreaWebService = Connection.createService(ZoneWardAreaWebService.class,
                AUtils.SERVER_URL);
        zoneWardAreaWebService.fetchWardZone(
                Prefs.getString(AUtils.APP_ID, ""))
                .enqueue(new Callback<List<ZoneWardAreaMasterPojo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ZoneWardAreaMasterPojo>> call, @NonNull Response<List<ZoneWardAreaMasterPojo>> response) {
                        if(response.code() == 200)
                            empZoneWardAreaListner.onSuccessCallback(response.body(), CALL_REQUEST_WARD_ZONE);
                        else
                            empZoneWardAreaListner.onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ZoneWardAreaMasterPojo>> call, @NonNull Throwable t) {
                        empZoneWardAreaListner.onErrorCallback();
                        t.printStackTrace();
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Throwable");
                    }
                });
    }

    public void fetchArea(){

        ZoneWardAreaWebService zoneWardAreaWebService = Connection.createService(ZoneWardAreaWebService.class,
                AUtils.SERVER_URL);
        zoneWardAreaWebService.fetchArea(
                Prefs.getString(AUtils.APP_ID, ""), "")
                .enqueue(new Callback<List<ZoneWardAreaMasterPojo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<ZoneWardAreaMasterPojo>> call, @NonNull Response<List<ZoneWardAreaMasterPojo>> response) {
                        if(response.code() == 200)
                            empZoneWardAreaListner.onSuccessCallback(response.body(), CALL_REQUEST_AREA);
                        else
                            empZoneWardAreaListner.onFailureCallback();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<ZoneWardAreaMasterPojo>> call, @NonNull Throwable t) {
                        empZoneWardAreaListner.onErrorCallback();
                        t.printStackTrace();
                        Log.i(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Throwable");
                    }
                });
    }

    public interface EmpZoneWardAreaListner{
        void onSuccessCallback(List<ZoneWardAreaMasterPojo> data, int CallRequestCode);
        void onFailureCallback();
        void onErrorCallback();
    }
}
