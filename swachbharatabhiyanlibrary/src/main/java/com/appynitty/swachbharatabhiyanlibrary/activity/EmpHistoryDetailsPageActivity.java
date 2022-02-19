package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.EmpInflateHistoryDetailsAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpHistoryDetailsAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpWorkHistoryDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.riaylibrary.utils.LocaleHelper;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class EmpHistoryDetailsPageActivity extends AppCompatActivity {

    private static final String TAG = "EmpHistoryDetailsPageActivity";
    private Toolbar toolbar;
    private Context mContext;
    private GridView detailsGrid;
    private List<EmpWorkHistoryDetailPojo> workHistoryDetailPojoList;
    private String historyDate;
    private View lineView;
    private LinearLayout noDataErrorLayout;

    private EmpHistoryDetailsAdapterClass mAdapter;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initComponents() {
        generateId();
        registerEvents();
        initData();
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

    private void generateId() {
        setContentView(R.layout.emp_activity_history_details_page);

        toolbar = findViewById(R.id.toolbar);

        mContext = EmpHistoryDetailsPageActivity.this;
        AUtils.currentContextConstant = mContext;

        mAdapter = new EmpHistoryDetailsAdapterClass();

        detailsGrid = findViewById(R.id.history_detail_grid);
        lineView = findViewById(R.id.line_view);
        noDataErrorLayout = findViewById(R.id.show_error_data);

        initToolbar();
    }

    private void registerEvents() {
        mAdapter.setHistoryDetailsListener(new EmpHistoryDetailsAdapterClass.HistoryDetailsListener() {
            @Override
            public void onSuccessCallBack() {
                setHistoryDetails();
            }

            @Override
            public void onFailureCallBack() {

            }
        });
    }

    private void initToolbar() {

        Intent intent = getIntent();

        if (intent.hasExtra(AUtils.HISTORY_DETAILS_DATE)) {
            String date = intent.getStringExtra(AUtils.HISTORY_DETAILS_DATE);
            toolbar.setTitle(AUtils.getTitleDateFormatEmp(date));
            historyDate = intent.getStringExtra(AUtils.HISTORY_DETAILS_DATE);
            historyDate = AUtils.getHistoryDetailsDateFormat(historyDate);
        }

        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void initData() {
        mAdapter.fetchHistoryDetails(historyDate);
    }

    private void setHistoryDetails() {
        workHistoryDetailPojoList = mAdapter.getWorkHistoryDetailTypePojoList();

        Collections.reverse(workHistoryDetailPojoList);     //added by swapnil

        if (!AUtils.isNull(workHistoryDetailPojoList)) {
            noDataErrorLayout.setVisibility(View.GONE);
            lineView.setVisibility(View.VISIBLE);
            EmpInflateHistoryDetailsAdapter adapter = new EmpInflateHistoryDetailsAdapter(mContext, workHistoryDetailPojoList);
            detailsGrid.setAdapter(adapter);
        } else {
            noDataErrorLayout.setVisibility(View.VISIBLE);
        }
    }
}
