package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.AddWasteListAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.WasteTypeCategoryAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.AddWasteDetailsPopup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteCategoriesRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteManagementRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteSubCategoriesRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.riaylibrary.custom_component.MyProgressDialog;
import com.riaylibrary.custom_component.RecyclerViewDecorator;
import com.riaylibrary.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WasteAddDetailsActivity extends AppCompatActivity {

    private static final String TAG = "WasteAddDetailsActivity";

    private Context mContext;
    private MyProgressDialog progressDialog;
    private ConstraintLayout constraintLayoutBaseAdd, constraintLayoutWasteList;
    private RecyclerView recyclerViewWasteList;
    private Button buttonAddMoreWaste, buttonSaveWaste;
    private AddWasteListAdapter wasteListAdapter;
    private AddWasteDetailsPopup addWasteDetailsPopup;
    private WasteTypeCategoryAdapterClass wasteTypeCategoryAdapterClass;
    private List<WasteManagementPojo.GarbageCategoryPojo> categoryPojos;
    private List<WasteManagementPojo> wasteManagementPojoList;

    private SyncWasteCategoriesRepository wasteCategoriesRepository;
    private SyncWasteManagementRepository wasteManagementRepository;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    private void initComponents() {
        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {
        setContentView(R.layout.activity_waste_add_details);

        mContext = WasteAddDetailsActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        addWasteDetailsPopup = new AddWasteDetailsPopup(mContext);
        addWasteDetailsPopup.create();

        progressDialog = new MyProgressDialog(mContext, false);
        constraintLayoutBaseAdd = findViewById(R.id.base_add_waste_layout);
        constraintLayoutWasteList = findViewById(R.id.add_more_waste_layout);
        recyclerViewWasteList = findViewById(R.id.recycler_view_details);
        recyclerViewWasteList.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerViewWasteList.addItemDecoration(new RecyclerViewDecorator(1));

        wasteListAdapter = new AddWasteListAdapter(mContext);
        wasteTypeCategoryAdapterClass = new WasteTypeCategoryAdapterClass();

        buttonAddMoreWaste = findViewById(R.id.button_add_more_waste);
        buttonSaveWaste = findViewById(R.id.button_save_waste);

        wasteManagementPojoList = new ArrayList<>();
        wasteCategoriesRepository = new SyncWasteCategoriesRepository(mContext);
        wasteManagementRepository = new SyncWasteManagementRepository(mContext);
    }

    private void registerEvents() {

        wasteTypeCategoryAdapterClass.setWasteTypeCategoryListener(new WasteTypeCategoryAdapterClass.WasteTypeCategoryListener() {
            @Override
            public void onSuccessCallback(List<?> list, int requestType) {
                if(progressDialog.isShowing()) progressDialog.dismiss();
                if (requestType == WasteTypeCategoryAdapterClass.WASTE_TYPE_REQUEST && list != null && list.size() > 0)
                    categoryPojos = (List<WasteManagementPojo.GarbageCategoryPojo>) list;
            }

            @Override
            public void onFailureCallback() {
                if(progressDialog.isShowing()) progressDialog.dismiss();
                AUtils.warning(mContext, getResources().getString(R.string.try_after_sometime));
            }

            @Override
            public void onErrorCallback() {
                if(progressDialog.isShowing()) progressDialog.dismiss();
                AUtils.warning(mContext, getResources().getString(R.string.serverError));
            }
        });

        wasteTypeCategoryAdapterClass.setCategorySubCategoryListener(new WasteTypeCategoryAdapterClass.CategorySubCategoryListener() {
            @Override
            public void onSuccessCallback(WasteManagementPojo pojo) {
                if (pojo != null && pojo.getCategory() != null && pojo.getCategory().size() > 0) {
                    wasteCategoriesRepository.truncateWasteCategories();
                    wasteCategoriesRepository.insertWasteCategory(pojo.getCategory());
                }
                if (pojo != null && pojo.getSubCategory() != null && pojo.getSubCategory().size() > 0) {
                    SyncWasteSubCategoriesRepository wasteSubCategoriesRepository = new SyncWasteSubCategoriesRepository(mContext);
                    wasteSubCategoriesRepository.truncateWasteSubCategories();
                    wasteSubCategoriesRepository.insertWasteSubCategory(pojo.getSubCategory());
                }
            }

            @Override
            public void onFailureCallback() {
                Log.d(TAG, "onFailureCallback: Combine Category Sub-Category");
            }

            @Override
            public void onErrorCallback() {
                Log.d(TAG, "onErrorCallback: Combine Category Sub-Category");
            }
        });

        wasteListAdapter.setWasteListActionListener(new AddWasteListAdapter.WasteListActionListener() {

            @Override
            public void onEditButtonClicked(int position) {
                editWasteListItem(position);
            }

            @Override
            public void onRemoveButtonClicked(int position) {
                removeWasteListItem(position);
            }
        });

        constraintLayoutBaseAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWastePopup();
            }
        });

        buttonAddMoreWaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddWastePopup();
            }
        });

        buttonSaveWaste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveWasteDetails();
            }
        });

        addWasteDetailsPopup.getWasteManagementPojoMutableLiveData().observe(this, new Observer<WasteManagementPojo>() {
            @Override
            public void onChanged(WasteManagementPojo wasteManagementPojo) {
                populateWasteList(wasteManagementPojo);
                addWasteDetailsPopup.dismiss();
            }
        });
    }

    private void initData() {
        if (AUtils.isInternetAvailable()) {
            if(!progressDialog.isShowing()) progressDialog.show();
            wasteTypeCategoryAdapterClass.fetchWasteType();
            wasteTypeCategoryAdapterClass.fetchCategorySubCategory();
        } else {
            categoryPojos = wasteCategoriesRepository.fetchWasteCategories();
        }
    }

    private void showAddWastePopup() {
        addWasteDetailsPopup.setTypeList(categoryPojos);
        if(!AUtils.isNull(categoryPojos) && categoryPojos.size() > 0) {
            addWasteDetailsPopup.initDialog(null);
            addWasteDetailsPopup.show();
        } else {
            AUtils.info(mContext, getResources().getString(R.string.something_error));
        }
    }

    private void populateWasteList(WasteManagementPojo pojo) {
        if(!pojo.getUpdate()){
            wasteManagementPojoList.add(pojo);
            wasteListAdapter.setWasteManagementList(wasteManagementPojoList);
            if(wasteManagementPojoList.size() == 1)
                recyclerViewWasteList.setAdapter(wasteListAdapter);

            wasteListAdapter.notifyDataSetChanged();
        } else {
            wasteManagementPojoList.set(pojo.getID(), pojo);
            wasteListAdapter.notifyItemChanged(pojo.getID());
        }
        constraintLayoutBaseAdd.setVisibility(View.GONE);
        constraintLayoutWasteList.setVisibility(View.VISIBLE);
    }

    private void removeWasteListItem(int position) {
        wasteManagementPojoList.remove(position);
        wasteListAdapter.setWasteManagementList(wasteManagementPojoList);
        wasteListAdapter.notifyItemChanged(position);
        if(wasteManagementPojoList.size() == 0) {
            constraintLayoutBaseAdd.setVisibility(View.VISIBLE);
            constraintLayoutWasteList.setVisibility(View.GONE);
        }
    }

    private void editWasteListItem(int position) {
        addWasteDetailsPopup.setTypeList(categoryPojos);
        if(!AUtils.isNull(categoryPojos) && categoryPojos.size() > 0) {
            WasteManagementPojo pojo = wasteManagementPojoList.get(position);
            pojo.setID(position);
            addWasteDetailsPopup.initDialog(pojo);
            addWasteDetailsPopup.show();
        }
    }

    private void saveWasteDetails() {
        Long l = wasteManagementRepository.saveWasteManagementData(wasteManagementPojoList);
        if (l != null && l > 0) {
            AUtils.success(mContext, getResources().getString(R.string.waste_details_success));
            ((Activity) mContext).finish();
        }
        else
            AUtils.error(mContext, getResources().getString(R.string.something_error));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        AUtils.currentContextConstant = mContext;
        if(AUtils.isInternetAvailable())
        {
            AUtils.hideSnackBar();
        }
        else {
            AUtils.showSnackBar(findViewById(R.id.parent));
        }
    }
}
