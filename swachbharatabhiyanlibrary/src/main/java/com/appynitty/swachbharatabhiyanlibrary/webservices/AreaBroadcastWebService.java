package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleTypePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface AreaBroadcastWebService {

    @GET("api/Get/SendSms")
    Call<ResultPojo> pullAreaBroadcast(@Header("appId") String appId, @Header("areaId") String areaId);
}
