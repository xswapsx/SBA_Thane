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
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.EmpInflateOfflineHistoryAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpSyncServerAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.ShareLocationAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.entity.EmpSyncServerEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOfflineCollectionCount;
import com.appynitty.swachbharatabhiyanlibrary.pojos.QrLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.EmpSyncServerRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.riaylibrary.utils.LocaleHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Swapnil Lanjewar on 08/01/2022.
 */

public class EmpSyncOfflineActivity extends AppCompatActivity {

    private final String TAG = EmpSyncOfflineActivity.class.getSimpleName();

    private Context mContext;
    private LinearLayout layoutNoOfflineData;
    private Button btnSyncOfflineData;
    private GridView gridOfflineData;

    private EmpSyncServerRepository empSyncServerRepository;
    private List<QrLocationPojo> locationPojoList;
    private Gson gson;
    private AlertDialog alertDialog;
    private int houseCount, dyCount, ssCount, lwcCount, resNCollectionC, resBCollectionC, resSCollectionC, commercialCollectionC, cadCollectionC, hortCollectionC;


    List<EmpOfflineCollectionCount> countList;

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
        mContext = AUtils.currentContextConstant = EmpSyncOfflineActivity.this;

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.title_activity_sync_offline);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        layoutNoOfflineData = findViewById(R.id.show_error_offline_data);
        btnSyncOfflineData = findViewById(R.id.btn_sync_data);
        gridOfflineData = findViewById(R.id.grid_offline_data);
        alertDialog = AUtils.getUploadingAlertDialog(mContext);

        empSyncServerRepository = new EmpSyncServerRepository(AUtils.mainApplicationConstant.getApplicationContext());
        locationPojoList = new ArrayList<>();
        gson = new Gson();
        countList = new ArrayList<>();

        houseCount = 0;
        dyCount = 0;
        ssCount = 0;
        lwcCount = 0;
//added by rahul
        resNCollectionC = 0;
        resBCollectionC = 0;
        resSCollectionC = 0;
        commercialCollectionC = 0;
        cadCollectionC = 0;
        hortCollectionC = 0;
        getDatabaseList();
    }

    private void getDatabaseList() {

        List<EmpSyncServerEntity> entityList = empSyncServerRepository.getAllEmpSyncServerEntity();
        locationPojoList.clear();
        for (EmpSyncServerEntity entity : entityList) {
            Type type = new TypeToken<QrLocationPojo>() {
            }.getType();
            QrLocationPojo pojo = gson.fromJson(entity.getPojo(), type);

            pojo.setOfflineID(String.valueOf(entity.getIndex_id()));
            locationPojoList.add(pojo);

        }

        if (locationPojoList.size() > 0) {

            String refId;
            String cType;
            String gCType;

            for (int i = 0; i < locationPojoList.size(); i++) {
                refId = locationPojoList.get(i).getReferanceId();
                cType = locationPojoList.get(i).getcType();
                gCType = locationPojoList.get(i).getGcType();
                if (refId.substring(0, 2).matches("^[HhPp]+$")) {

                    houseCount++;
                    Log.e(TAG, "getDatabaseList: houseId:-" + refId);
                    //added by rahul
                    /*if (cType.matches("")){
                        resNCollectionC++;
                    }else if (cType.matches("RBW")){
                        resBCollectionC++;
                    }else if (cType.matches("RSW")){
                        resSCollectionC++;
                    }else if (cType.matches("CW")){
                        commercialCollectionC++;
                    }else {
                        houseCount++;
                    }*/


                    if (gCType.matches("6")) {
                        cadCollectionC++;
                    } else if (gCType.matches("7")) {
                        hortCollectionC++;
                    }

                } else if (refId.substring(0, 2).matches("^[LlWw]+$")) {
                    lwcCount++;
                } else if (refId.substring(0, 2).matches("^[DdYy]+$")) {
                    dyCount++;
                } else if (refId.substring(0, 2).matches("^[SsSs]+$")) {
                    ssCount++;
                } else if (refId.substring(0, 2).matches("^[CcPp]+$")) {
                    commercialCollectionC++;
                }
            }
            Log.e(TAG, "House count: " + houseCount
                    + ", dyCount: " + dyCount
                    + ", ssCount: " + ssCount
                    + ", lwcCount: " + lwcCount
                    + ", resNCollectionC: " + resNCollectionC
                    + ", resBCollectionC: " + resBCollectionC
                    + ", resSCollectionC: " + resSCollectionC
                    + ", commercialCollectionC: " + commercialCollectionC
                    + ", cadCollectionC: " + cadCollectionC
                    + ", hortCollectionC: " + hortCollectionC
            );

            countList.add(new EmpOfflineCollectionCount(
                            String.valueOf(houseCount),
                            String.valueOf(dyCount),
                            String.valueOf(ssCount),
                            String.valueOf(lwcCount), locationPojoList.get(0).getDate(),
                            String.valueOf(resNCollectionC),
                            String.valueOf(resBCollectionC),
                            String.valueOf(resSCollectionC),
                            String.valueOf(commercialCollectionC),
                            String.valueOf(cadCollectionC),
                            String.valueOf(hortCollectionC)
                    )
            );
        }

    }

    private void registerEvents() {

        final EmpSyncServerAdapterClass empSyncAdapter = new EmpSyncServerAdapterClass();
        btnSyncOfflineData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!alertDialog.isShowing())
                    alertDialog.show();

                empSyncAdapter.syncServer();

                empSyncAdapter.setSyncOfflineListener(new EmpSyncServerAdapterClass.EmpSyncOfflineListener() {
                    @Override
                    public void onSuccessCallback() {

                        if (alertDialog.isShowing())
                            alertDialog.hide();
                        AUtils.success(mContext, getString(R.string.success_offline_sync), Toast.LENGTH_LONG);
                        locationPojoList.clear();
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

                ShareLocationAdapterClass shareLocationAdapterClass = new ShareLocationAdapterClass();
                shareLocationAdapterClass.shareLocation();
            }
        });


    }

    private void initData() {
        inflateData();
    }

    private void inflateData() {

        if (locationPojoList.size() > 0) {
            gridOfflineData.setVisibility(View.VISIBLE);
            btnSyncOfflineData.setVisibility(View.VISIBLE);
            layoutNoOfflineData.setVisibility(View.GONE);
            Log.e(TAG, "inflateData: countList" + countList.toString());
            EmpInflateOfflineHistoryAdapter historyAdapter = new EmpInflateOfflineHistoryAdapter(mContext, R.layout.layout_history_card, countList);
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
