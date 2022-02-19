package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.UserDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleTypePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface UserDetailsWebService {

    @GET("api/Get/User")
    Call<UserDetailPojo> pullUserDetails(@Header("appId") String appId,
                                         @Header("Content-Type") String content_type,
                                         @Header("userId") String userId,
                                         @Header("typeId") String typeId);
}
