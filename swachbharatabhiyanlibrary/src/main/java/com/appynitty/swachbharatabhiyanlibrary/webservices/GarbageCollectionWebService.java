package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.GcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGarbageColectionPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OfflineGcResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.SyncOfflinePojo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface GarbageCollectionWebService {

    @Multipart
    @POST("api/Save/GarbageCollection")
    Call<GcResultPojo> saveGarbageCollectionGP(@Header("appId") String appId,
                                               @Header("batteryStatus") int batteryStatus,
                                               @Part("userId") RequestBody userId,
                                               @Part("gpId") RequestBody gpId,
                                               @Part("Lat") RequestBody Lat,
                                               @Part("Long") RequestBody Long,
                                               @Part("beforeImage") RequestBody beforeImage,
                                               @Part("AfterImage") RequestBody afterImage,
                                               @Part("note") RequestBody comment,
                                               @Part("vehicleNumber") RequestBody vehicleNumber,
                                               @Part MultipartBody.Part image1,
                                               @Part MultipartBody.Part image2);


    @Multipart
    @POST("api/Save/GarbageCollection")
    Call<GcResultPojo> saveGarbageCollectionH(@Header("appId") String appId,
                                              @Header("batteryStatus") int batteryStatus,
                                              @Part("userId") RequestBody userId,
                                              @Part("houseId") RequestBody houseId,
                                              @Part("Lat") RequestBody Lat,
                                              @Part("Long") RequestBody Long,
                                              @Part("beforeImage") RequestBody beforeImage,
                                              @Part("AfterImage") RequestBody afterImage,
                                              @Part("note") RequestBody comment,
                                              @Part("vehicleNumber") RequestBody vehicleNumber,
                                              @Part MultipartBody.Part image1,
                                              @Part MultipartBody.Part image2,
                                              @Part("garbageType") RequestBody garbageType);


    @Multipart
    @POST("api/Save/DumpYardCollection")
    Call<GcResultPojo> saveGarbageCollectionDy(@Header("appId") String appId,
                                               @Header("batteryStatus") int batteryStatus,
                                               @Part("userId") RequestBody userId,
                                               @Part("dyId") RequestBody houseId,
                                               @Part("Lat") RequestBody Lat,
                                               @Part("Long") RequestBody Long,
                                               @Part("beforeImage") RequestBody beforeImage,
                                               @Part("AfterImage") RequestBody afterImage,
                                               @Part("note") RequestBody comment,
                                               @Part("vehicleNumber") RequestBody vehicleNumber,
                                               @Part MultipartBody.Part image1,
                                               @Part MultipartBody.Part image2,
                                               @Part("totalGcWeight") RequestBody weightTotal,
                                               @Part("totalDryWeight") RequestBody weightDry,
                                               @Part("EmpType") RequestBody empType,                  //added by swapnil
                                               @Part("totalWetWeight") RequestBody weightWet);

    @POST("api/Save/GarbageCollectionOfflineUpload")
    Call<List<OfflineGcResultPojo>> saveGarbageCollectionOffline(@Header("appId") String appId,
                                                                 @Header("typeId") String typeId,
                                                                 @Header("batteryStatus") int batteryStatus,
                                                                 @Header("Content-Type") String contentType,
                                                                 @Body List<OfflineGarbageColectionPojo> userLocationPojoList);

    @POST("api/Save/GarbageCollectionOfflineUpload")
    Call<List<OfflineGcResultPojo>> syncOfflineData(@Header("appId") String appId,
                                                    @Header("typeId") String typeId,
                                                    @Header("batteryStatus") int batteryStatus,
                                                    @Header("Content-Type") String contentType,
                                                    @Body List<SyncOfflinePojo> offlineDataList);   //this is for syncing the offlinedata

    @POST("api/Save/GarbageCollectionOfflineUpload")
    Call<List<OfflineGcResultPojo>> getQRCategory(@Header("appId") String appId,
                                                  @Header("typeId") String typeId,
                                                  @Header("batteryStatus") int batteryStatus,
                                                  @Header("Content-Type") String contentType,
                                                  @Body List<SyncOfflinePojo> scannedQR);
}
