package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateHistoryAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateOfflineWorkAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.SyncOfflineAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.db.AppDatabase;
import com.appynitty.swachbharatabhiyanlibrary.db.HouseEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.entity.SyncOfflineEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOfflineCollectionCount;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SyncOfflineActivity extends AppCompatActivity {

    private static final String TAG = "SyncOfflineActivity";
    private Context mContext;
    private LinearLayout layoutNoOfflineData;
    private Button btnSyncOfflineData;
    private GridView gridOfflineData;
    private SyncOfflineRepository syncOfflineRepository;
    private List<TableDataCountPojo.WorkHistory> workHistoryList;
    private SyncOfflineAdapterClass syncOfflineAdapter;
    private AlertDialog alertDialog;
    private Gson gson;

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

        setContentView(R.layout.activity_sync_offline);

        mContext = AUtils.currentContextConstant = SyncOfflineActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_sync_offline);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        layoutNoOfflineData = findViewById(R.id.show_error_offline_data);
        btnSyncOfflineData = findViewById(R.id.btn_sync_data);
        gridOfflineData = findViewById(R.id.grid_offline_data);

        workHistoryList = new ArrayList<>();
        syncOfflineRepository = new SyncOfflineRepository(mContext);
        syncOfflineAdapter = new SyncOfflineAdapterClass(mContext);
        alertDialog = AUtils.getUploadingAlertDialog(mContext);




        gson = new Gson();
        syncOfflineRepository = new SyncOfflineRepository(AUtils.mainApplicationConstant.getApplicationContext());

    }

    private void registerEvents() {

        btnSyncOfflineData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!alertDialog.isShowing())
                    alertDialog.show();
                syncOfflineAdapter.SyncOfflineData();
            }
        });

        syncOfflineAdapter.setSyncOfflineListener(new SyncOfflineAdapterClass.SyncOfflineListener() {
            @Override
            public void onSuccessCallback() {
                if (alertDialog.isShowing())
                    alertDialog.hide();
                AUtils.success(mContext, getString(R.string.success_offline_sync), Toast.LENGTH_LONG);
                inflateData();
            }

            @Override
            public void onFailureCallback() {
                if (alertDialog.isShowing())
                    alertDialog.hide();
                AUtils.warning(mContext, getResources().getString(R.string.try_after_sometime));
            }

            @Override
            public void onErrorCallback() {
                if (alertDialog.isShowing())
                    alertDialog.hide();
                AUtils.warning(mContext, getResources().getString(R.string.serverError));
            }
        });
    }

    private void initData() {
        inflateData();
    }

    private void inflateData() {
        workHistoryList.clear();
        workHistoryList = syncOfflineRepository.fetchCollectionCount();
        if (workHistoryList.size() > 0) {
            gridOfflineData.setVisibility(View.VISIBLE);
            btnSyncOfflineData.setVisibility(View.VISIBLE);
            layoutNoOfflineData.setVisibility(View.GONE);
            InflateOfflineWorkAdapter historyAdapter = new InflateOfflineWorkAdapter(mContext, workHistoryList);
            Log.e(TAG, "inflateData:- " + "OfflineWorkHistory=> " + workHistoryList);
            gridOfflineData.setAdapter(historyAdapter);
        } else {
            gridOfflineData.setVisibility(View.GONE);
            btnSyncOfflineData.setVisibility(View.GONE);
            layoutNoOfflineData.setVisibility(View.VISIBLE);

        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (AUtils.isInternetAvailable()) {
            AUtils.hideSnackBar();
        } else {
            AUtils.showSnackBar(findViewById(R.id.parent));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }



}
