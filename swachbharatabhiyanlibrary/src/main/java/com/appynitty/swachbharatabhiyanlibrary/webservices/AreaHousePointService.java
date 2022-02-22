package com.appynitty.swachbharatabhiyanlibrary.webservices;

import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaHousePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionDumpYardPointPojo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Ayan Dey on 26/11/18.
 */
public interface AreaHousePointService {


    @GET("api/Get/CollectionArea")
    Call<List<CollectionAreaPojo>> fetchCollectionArea(@Header("appId") String appId,
                                                       @Header("type") String collectionType,
                                                       @Header("EmpType") String EmpType);

    @GET("api/Get/AreaHouse")
    Call<List<CollectionAreaHousePojo>> fetchCollectionAreaHouse(@Header("appId") String appId,
                                                                 @Header("type") String collectionType,
                                                                 @Header("areaId") String areaId,
                                                                 @Header("EmpType") String EmpType);

    @GET("api/Get/AreaPoint")
    Call<List<CollectionAreaPointPojo>> fetchCollectionAreaPoint(@Header("appId") String appId,
                                                                 @Header("type") String collectionType,
                                                                 @Header("areaId") String areaId);

    @GET("api/Get/DumpYardPoint")
    Call<List<CollectionDumpYardPointPojo>> fetchCollectionDyPoint(@Header("appId") String appId,
                                                                   @Header("type") String collectionType,
                                                                   @Header("areaId") String areaId);

    @GET("api/Get/CommercialPoint")
    Call<List<CollectionDumpYardPointPojo>> fetchCollectionCpPoint(@Header("appId") String appId,
                                                                   @Header("areaId") String areaId);

}
