package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.ZoneWardAreaMasterPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Ayan Dey on 29/5/19.
 */
public interface ZoneWardAreaWebService {

    @GET("api/Get/Zone")
    Call<List<ZoneWardAreaMasterPojo>> fetchZone(@Query("AppId") String appId,
                                                 @Query("SearchString") String searchString);

    @GET("api/Get/WardZoneList")
    Call<List<ZoneWardAreaMasterPojo>> fetchWardZone(@Query("AppId") String appId);

    @GET("api/Get/AreaList")
    Call<List<ZoneWardAreaMasterPojo>> fetchArea(@Query("AppId") String appId,
                                                 @Query("SearchString") String searchString);
}
