package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpRegistrationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserLocationResultPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by Ayan Dey on 28/5/19.
 */
public interface QrLocationWebService {



    @POST("api/Save/QrHPDCollections")
    Call<ResultPojo> saveQrLocationDetails(@Header("appId") String appId,
                                           @Header("referanceId") String refId,
                                           @Header("gcType") String gcType,
                                           @Header("Content-Type") String contentType,
                                           @Body QrLocationPojo locationPojo);


    @GET("api/Get/ScanifyHouse")
    Call<EmpRegistrationPojo> fetchRegistrationDetails(@Header("appId") String appId,
                                                       @Header("ReferenceId") String refId,
                                                       @Header("gcType") String gcType);

    @POST("api/Save/QrHPDCollectionsOffline")
    Call<List<OfflineGcResultPojo>> saveQrLocationDetailsOffline(@Header("appId") String appId,
                                                                 @Header("Content-Type") String contentType,
                                                                 @Body List<QrLocationPojo> locationPojoList);
}
