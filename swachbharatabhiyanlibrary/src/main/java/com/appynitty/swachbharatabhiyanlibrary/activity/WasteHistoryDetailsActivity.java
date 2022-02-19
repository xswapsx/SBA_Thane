package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateWasteHistoryDetailsUIAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateWasteHistoryUIAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.WasteHistoryAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementHistoryPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.LinearLayout;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.riaylibrary.custom_component.MyProgressDialog;
import com.riaylibrary.utils.LocaleHelper;

import java.util.List;
import java.util.Objects;

public class WasteHistoryDetailsActivity extends AppCompatActivity {

    private Context mContext;
    private RecyclerView recyclerViewHistoryDetails;

    private LinearLayout noDataErrorLayout, noInternetErrorLayout;
    private MyProgressDialog myProgressDialog;

    private String detailsDate;

    private WasteHistoryAdapterClass adapterClass;

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
        initComponent();
    }

    private void initComponent() {
        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {
        setContentView(R.layout.activity_waste_history_details);

        mContext = WasteHistoryDetailsActivity.this;
        AUtils.currentContextConstant = mContext;

        if(getIntent().hasExtra(AUtils.HISTORY_DETAILS_DATE)) {
            detailsDate = getIntent().getStringExtra(AUtils.HISTORY_DETAILS_DATE);
        } else
            detailsDate = getResources().getString(R.string.hyphen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(AUtils.getTitleDateFormat(detailsDate));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        myProgressDialog = new MyProgressDialog(mContext, false);
        noDataErrorLayout = findViewById(R.id.show_error_data);
        noInternetErrorLayout = findViewById(R.id.show_error_internet);

        recyclerViewHistoryDetails = findViewById(R.id.recycler_view_history_details);
        recyclerViewHistoryDetails.setLayoutManager(new LinearLayoutManager(mContext));

        adapterClass = new WasteHistoryAdapterClass();
    }

    private void registerEvents() {
        adapterClass.setHistoryListener(new WasteHistoryAdapterClass.HistoryListener() {
            @Override
            public void onSuccessCallBack(List<WasteManagementHistoryPojo> data) {
                if(myProgressDialog.isShowing())
                    myProgressDialog.dismiss();
                setHistoryData(data);
            }

            @Override
            public void onErrorCallBack() {
                if(myProgressDialog.isShowing())
                    myProgressDialog.dismiss();
                AUtils.error(mContext, mContext.getResources().getString(R.string.serverError));
            }

            @Override
            public void onFailureCallBack() {
                if(myProgressDialog.isShowing())
                    myProgressDialog.dismiss();
                AUtils.error(mContext, mContext.getResources().getString(R.string.something_error));
            }
        });
    }

    private void initData() {
        if(AUtils.isInternetAvailable()) {
            noInternetErrorLayout.setVisibility(View.GONE);
            if(!myProgressDialog.isShowing())
                myProgressDialog.show();
            adapterClass.fetchHistoryDetails(detailsDate);
        } else {
            noInternetErrorLayout.setVisibility(View.VISIBLE);
        }

        noDataErrorLayout.setVisibility(View.GONE);
        recyclerViewHistoryDetails.setVisibility(View.GONE);
    }

    private void setHistoryData(List<WasteManagementHistoryPojo> data) {
        noInternetErrorLayout.setVisibility(View.GONE);

        if(!AUtils.isNull(data) && !data.isEmpty()){
            recyclerViewHistoryDetails.setVisibility(View.VISIBLE);
            noDataErrorLayout.setVisibility(View.GONE);
            InflateWasteHistoryDetailsUIAdapter adapter = new InflateWasteHistoryDetailsUIAdapter(mContext);
            adapter.setWasteHistoryList(data);
            recyclerViewHistoryDetails.setAdapter(adapter);
        }else{
            recyclerViewHistoryDetails.setVisibility(View.GONE);
            noDataErrorLayout.setVisibility(View.VISIBLE);
        }
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
