package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpLoginDetailsPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpLoginPojo;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface EmpLoginWebService {

    @POST("api/Account/EmployeeLogin")
    Call<EmpLoginDetailsPojo> saveLoginDetails(@Header("Content-Type") String content_type,
                                               @Body EmpLoginPojo empLoginPojo);
}
