package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpWorkHistoryDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementHistoryPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WorkHistoryDetailPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface WorkHistoryWebService {

    @GET("api/Get/WorkHistory")
    Call<List<TableDataCountPojo.WorkHistory>> pullWorkHistoryList(@Header("appId") String appId,
                                                                   @Header("userId") String userId,
                                                                   @Header("year") String year,
                                                                   @Header("month") String month,
                                                                   @Header("EmpType") String empType);


    @GET("api//Get/WorkHistory/Details")
    Call<List<WorkHistoryDetailPojo>> pullWorkHistoryDetailList(@Header("appId") String appId,
                                                                @Header("userId") String userId,
                                                                @Header("fdate") String fDate,
                                                                @Header("LanguageId") String languageId);

    @GET("api/Get/QrWorkHistory ")
    Call<List<TableDataCountPojo.WorkHistory>> pullEmpWorkHistoryList(@Header("appId") String appId,
                                                                      @Header("userId") String userId,
                                                                      @Header("year") String year,
                                                                      @Header("month") String month);


    @GET("api/Get/QrWorkHistoryDetails")
    Call<List<EmpWorkHistoryDetailPojo>> pullEmpWorkHistoryDetailList(@Header("appId") String appId,
                                                                      @Header("userId") String userId,
                                                                      @Header("Date") String fDate);


    @GET("api/Get/GarbageHistory")
    Call<List<WasteManagementHistoryPojo>> pullWasteManagementHistory(@Header("appId") String appId,
                                                                      @Header("userId") String userId,
                                                                      @Header("year") String year,
                                                                      @Header("month") String month);


    @GET("api/Get/GarbageHistory/Details")
    Call<List<WasteManagementHistoryPojo>> pullWasteManagementHistoryDetails(@Header("appId") String appId,
                                                                             @Header("userId") String userId,
                                                                             @Header("fdate") String fDate);
}
