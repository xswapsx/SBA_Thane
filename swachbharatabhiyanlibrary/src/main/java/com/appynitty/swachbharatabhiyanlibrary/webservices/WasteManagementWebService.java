package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SyncOfflinePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ayan Dey on 25/1/20.
 */
public interface WasteManagementWebService {

    @GET("api/Get/GarbageCategory")
    Call<List<WasteManagementPojo.GarbageCategoryPojo>> fetchGarbageCategory(@Query("appId") String appId);

    @GET("api/Get/GarbageSubCategory")
    Call<List<WasteManagementPojo.GarbageSubCategoryPojo>> fetchGarbageSubCategory(@Header("appId") String appId,
                                                                             @Header("CategoryID")String categoryId);

    @GET("api/Get/CombineCategorySubcategory")
    Call<WasteManagementPojo> combineCategorySubcategory(@Header("appId") String appId);

    @POST("api/Save/GarbageDetails")
    Call<List<WasteManagementPojo>> syncOfflineWasteManagementData(@Header("appId") String appId,
                                                                   @Body List<WasteManagementPojo> wasteManagementList);
}
