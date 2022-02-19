package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpInPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOutPunchPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EmpPunchWebService {


    @POST("api/Save/QrEmployeeAttendenceIn")
    Call<ResultPojo> saveInPunchDetails(@Header("appId") String appId,
                                        @Header("Content-Type") String content_type,
                                        @Header("batteryStatus") int batteryStatus,
                                        @Body EmpInPunchPojo loginPojo);

    @POST("api/Save/QrEmployeeAttendenceOut")
    Call<ResultPojo> saveOutPunchDetails(@Header("appId") String appId,
                                         @Header("Content-Type") String content_type,
                                         @Header("batteryStatus") int batteryStatus,
                                         @Body EmpOutPunchPojo outPojo);
}
