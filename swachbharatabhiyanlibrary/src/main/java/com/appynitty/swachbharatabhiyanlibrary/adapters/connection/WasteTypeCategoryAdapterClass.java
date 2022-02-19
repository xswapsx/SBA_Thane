package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import androidx.annotation.NonNull;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.webservices.WasteManagementWebService;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ayan Dey on 25/1/20.
 */
public class WasteTypeCategoryAdapterClass {

    public static final int WASTE_TYPE_REQUEST = 1;
    public static final int WASTE_CATEGORY_REQUEST = 2;

    private WasteTypeCategoryListener wasteTypeCategoryListener;
    private CategorySubCategoryListener categorySubCategoryListener;
    private final String appId;

    public WasteTypeCategoryAdapterClass() {
        this.appId = Prefs.getString(AUtils.APP_ID, "");
    }

    public void setWasteTypeCategoryListener(WasteTypeCategoryListener wasteTypeCategoryListener) {
        this.wasteTypeCategoryListener = wasteTypeCategoryListener;
    }

    public void setCategorySubCategoryListener(WasteTypeCategoryAdapterClass.CategorySubCategoryListener categorySubCategoryListener) {
        this.categorySubCategoryListener = categorySubCategoryListener;
    }

    public void fetchWasteType(){
        WasteManagementWebService service = Connection.createService(WasteManagementWebService.class, AUtils.SERVER_URL);
        service.fetchGarbageCategory(appId)
                .enqueue(new Callback<List<WasteManagementPojo.GarbageCategoryPojo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<WasteManagementPojo.GarbageCategoryPojo>> call,@NonNull Response<List<WasteManagementPojo.GarbageCategoryPojo>> response) {
                        if(response.code() == 200){
                            wasteTypeCategoryListener.onSuccessCallback(response.body(), WASTE_TYPE_REQUEST);
                        } else {
                            wasteTypeCategoryListener.onFailureCallback();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<WasteManagementPojo.GarbageCategoryPojo>> call,@NonNull Throwable t) {
                        t.printStackTrace();
                        wasteTypeCategoryListener.onErrorCallback();
                    }
                });
    }

    public void fetchWasteCategory(String categoryId){
        WasteManagementWebService service = Connection.createService(WasteManagementWebService.class, AUtils.SERVER_URL);
        service.fetchGarbageSubCategory(appId, categoryId)
                .enqueue(new Callback<List<WasteManagementPojo.GarbageSubCategoryPojo>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<WasteManagementPojo.GarbageSubCategoryPojo>> call,@NonNull Response<List<WasteManagementPojo.GarbageSubCategoryPojo>> response) {
                        if(response.code() == 200){
                            wasteTypeCategoryListener.onSuccessCallback(response.body(), WASTE_CATEGORY_REQUEST);
                        } else {
                            wasteTypeCategoryListener.onFailureCallback();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<WasteManagementPojo.GarbageSubCategoryPojo>> call,@NonNull Throwable t) {
                        t.printStackTrace();
                        wasteTypeCategoryListener.onErrorCallback();
                    }
                });
    }

    public void fetchCategorySubCategory(){
        WasteManagementWebService service = Connection.createService(WasteManagementWebService.class, AUtils.SERVER_URL);
        service.combineCategorySubcategory(appId)
                .enqueue(new Callback<WasteManagementPojo>() {
                    @Override
                    public void onResponse(@NonNull Call<WasteManagementPojo> call,@NonNull Response<WasteManagementPojo> response) {
                        if(response.code() == 200){
                            categorySubCategoryListener.onSuccessCallback(response.body());
                        } else {
                            categorySubCategoryListener.onFailureCallback();
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<WasteManagementPojo> call,@NonNull Throwable t) {
                        t.printStackTrace();
                        categorySubCategoryListener.onErrorCallback();
                    }
                });
    }

    public interface WasteTypeCategoryListener{
        void onSuccessCallback(List<?> list, int requestType);
        void onFailureCallback();
        void onErrorCallback();
    }

    public interface CategorySubCategoryListener{
        void onSuccessCallback(WasteManagementPojo pojo);
        void onFailureCallback();
        void onErrorCallback();
    }
}
