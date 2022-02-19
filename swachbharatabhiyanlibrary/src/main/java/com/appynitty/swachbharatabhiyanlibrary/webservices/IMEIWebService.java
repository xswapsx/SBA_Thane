package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface IMEIWebService {
    //api/Get/GetUserMobileIdentification
    // url=http://115.115.153.117:4044/api/Get/GetUserMobileIdentification
    @GET("api/Get/GetUserMobileIdentification")
    Call<ResponseBody> compareIMEINumber (
            @Header("appId") String appId,
            @Header("userId") String userId,
            @Header("isSync") boolean isSync,
            @Header("imeino") String deviceId,
            @Header("batteryStatus") int batteryStatus,
            @Header("Content-Type") String contentType );

}