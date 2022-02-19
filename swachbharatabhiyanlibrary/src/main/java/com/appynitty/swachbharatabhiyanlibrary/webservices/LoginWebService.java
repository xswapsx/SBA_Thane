package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginDetailsPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginPojo;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface LoginWebService {

    @POST("api/Account/Login")
    Call<LoginDetailsPojo> saveLoginDetails(@Header("appId") String appId,
                                            @Header("Content-Type") String content_type,
                                            @Header("EmpType") String emp_type,
                                            @Body LoginPojo loginPojo);
}
