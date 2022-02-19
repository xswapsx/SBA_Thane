package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateWasteHistoryUIAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.SyncOfflineWasteManagementAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VerifyDataAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementHistoryPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteManagementRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.riaylibrary.utils.LocaleHelper;

import java.util.List;
import java.util.Objects;

public class WasteSyncOfflineActivity extends AppCompatActivity {

    private Context context;
    private RecyclerView recyclerViewData;
    private LinearLayout linearLayoutNoData;
    private ConstraintLayout constraintLayoutContent;
    private Button buttonSyncOffline;
    private AlertDialog alertDialog;

    private InflateWasteHistoryUIAdapter wasteHistoryUIAdapter;
    private SyncWasteManagementRepository syncWasteManagementRepository;
    private SyncOfflineWasteManagementAdapterClass adapterClass;

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
        setContentView(R.layout.activity_waste_sync_offline);

        context = WasteSyncOfflineActivity.this;
        AUtils.currentContextConstant = context;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        buttonSyncOffline = findViewById(R.id.btn_sync_data);
        constraintLayoutContent = findViewById(R.id.layout_content);
        recyclerViewData = findViewById(R.id.recycler_view_offline_history);
        linearLayoutNoData = findViewById(R.id.show_error_offline_data);

        recyclerViewData.setLayoutManager(new GridLayoutManager(context, 2));
        wasteHistoryUIAdapter = new InflateWasteHistoryUIAdapter(context, false);
        syncWasteManagementRepository = new SyncWasteManagementRepository(context);

        adapterClass = new SyncOfflineWasteManagementAdapterClass(context);
        alertDialog = AUtils.getUploadingAlertDialog(context);
    }

    private void registerEvents() {
        adapterClass.setSyncOfflineListener(new SyncOfflineWasteManagementAdapterClass.SyncOfflineListener() {
            @Override
            public void onSuccessCallback() {
                if(alertDialog.isShowing())
                    alertDialog.hide();
                AUtils.success(context, getString(R.string.success_offline_sync), Toast.LENGTH_LONG);
                inflateRecycler();
            }

            @Override
            public void onFailureCallback() {
                if(alertDialog.isShowing())
                    alertDialog.hide();
                AUtils.warning(context, getResources().getString(R.string.try_after_sometime));
            }

            @Override
            public void onErrorCallback() {
                if(alertDialog.isShowing())
                    alertDialog.hide();
                AUtils.warning(context, getResources().getString(R.string.serverError));
            }
        });

        buttonSyncOffline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (AUtils.isInternetAvailable()) {
                    if(!alertDialog.isShowing())
                        alertDialog.show();
                    adapterClass.syncOfflineWasteManagementData();
                } else
                    AUtils.error(context, getResources().getString(R.string.no_internet_error));

            }
        });
    }

    private void initData() {
        inflateRecycler();
    }

    private void inflateRecycler() {
        List<WasteManagementHistoryPojo> offlineWasteManagementHistory = syncWasteManagementRepository.getOfflineWasteManagementHistory();
        if (offlineWasteManagementHistory.size() > 0) {
            constraintLayoutContent.setVisibility(View.VISIBLE);
            linearLayoutNoData.setVisibility(View.GONE);
            wasteHistoryUIAdapter.setWasteHistoryList(offlineWasteManagementHistory);
            recyclerViewData.setAdapter(wasteHistoryUIAdapter);
        } else {
            constraintLayoutContent.setVisibility(View.GONE);
            linearLayoutNoData.setVisibility(View.VISIBLE);
        }
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
        AUtils.currentContextConstant = context;
        if(AUtils.isInternetAvailable())
        {
            AUtils.hideSnackBar();
        }
        else {
            AUtils.showSnackBar(findViewById(R.id.parent));
        }
    }
}
