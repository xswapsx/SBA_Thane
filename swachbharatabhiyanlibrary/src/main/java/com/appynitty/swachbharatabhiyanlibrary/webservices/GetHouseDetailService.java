package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.HouseCTypePojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface GetHouseDetailService {
    @Headers({
            "appId:3088",
            "Content-Type:application/json"
    })
    @GET("/api/Get/GetSWMFType")
    Call<List<HouseCTypePojo>> getHouseDetails();
}
