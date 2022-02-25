package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.HouseCTypePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;

public interface GetHouseDetailService {

    @GET("/api/Get/GetSWMFType")
    Call<List<HouseCTypePojo>> getHouseDetails(@Header("appId") String appId,
                                               @Header("Content-Type") String content_type);
}
