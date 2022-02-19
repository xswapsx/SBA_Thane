package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.CheckAttendancePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface CheckAttendanceWebService {

    @GET("api/Get/IsAttendence")
    Call<CheckAttendancePojo> CheckAttendance(@Header("appId") String appId,
                                              @Header("UserId") String userId,
                                              @Header("typeId") String typeId);
}
