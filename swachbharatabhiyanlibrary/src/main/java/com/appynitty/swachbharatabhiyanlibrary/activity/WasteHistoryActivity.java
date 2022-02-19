package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.EmpInflateHistoryAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateWasteHistoryUIAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.HistoryAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.WasteHistoryAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementHistoryPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.riaylibrary.custom_component.MyProgressDialog;
import com.riaylibrary.utils.LocaleHelper;

import java.util.List;
import java.util.Objects;

public class WasteHistoryActivity extends AppCompatActivity {

    private static final String TAG = "HistoryPageActivity";

    private Context mContext;
    private Toolbar toolbar;
    private Spinner yearSpinner, monthSpinner;
    private RecyclerView historyGrid;
    private LinearLayout noDataErrorLayout, noInternetErrorLayout;
    private MyProgressDialog myProgressDialog;

    private boolean isYearSet, isMonthSet;

    private WasteHistoryAdapterClass mAdapter;

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
        setContentView(R.layout.activity_waste_history);

        toolbar = findViewById(R.id.toolbar);

        mContext = WasteHistoryActivity.this;
        AUtils.currentContextConstant = mContext;

        mAdapter = new WasteHistoryAdapterClass();
        myProgressDialog = new MyProgressDialog(mContext, false);

        isYearSet = false;
        isMonthSet = false;

        monthSpinner = findViewById(R.id.spinner_month);
        yearSpinner = findViewById(R.id.spinner_year);
        historyGrid = findViewById(R.id.grid_history);
        historyGrid.setLayoutManager(new GridLayoutManager(mContext, 2));
        noDataErrorLayout = findViewById(R.id.show_error_data);
        noInternetErrorLayout = findViewById(R.id.show_error_internet);

        initToolbar();
    }

    private void initToolbar() {
        toolbar.setTitle(getResources().getString(R.string.title_activity_waste_history));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    private void registerEvents() {

        monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(isMonthSet) {
                    if(position > 0 && yearSpinner.getSelectedItemPosition() > 0){
                        initHistoryData(
                                yearSpinner.getSelectedItem().toString(),
                                String.valueOf(position)
                        );
                    }else{
                        AUtils.warning(mContext, getResources().getString(R.string.select_month_year_warn));
                    }
                } else {
                    isMonthSet = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
                if(isYearSet) {
                    if(position > 0 && monthSpinner.getSelectedItemPosition() > 0){
                        initHistoryData(
                                yearSpinner.getSelectedItem().toString(),
                                String.valueOf(monthSpinner.getSelectedItemPosition())
                        );
                    }else{
                        AUtils.info(mContext, getResources().getString(R.string.select_month_year_warn));
                    }
                } else {
                    isYearSet = true;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        mAdapter.setHistoryListener(new WasteHistoryAdapterClass.HistoryListener() {
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
        initSpinner();
        initHistoryData(null, null);
    }

    private void initSpinner() {
        setMonthSpinner(monthSpinner);
        setYearSpinner(yearSpinner);
    }

    public void setMonthSpinner(Spinner monthSpinner) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mContext,
                R.layout.layout_simple_white_textview, AUtils.getMonthList());
        monthSpinner.setAdapter(spinnerAdapter);
        monthSpinner.setSelection((AUtils.getCurrentMonth()+1), true);
    }

    public void setYearSpinner(Spinner yearSpinner) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(mContext,
                R.layout.layout_simple_white_textview, AUtils.getYearList());
        yearSpinner.setAdapter(spinnerAdapter);
        yearSpinner.setSelection(1, true);
    }

    private void initHistoryData(@Nullable String year,@Nullable String month) {
        if(AUtils.isInternetAvailable()) {
            noInternetErrorLayout.setVisibility(View.GONE);
            if(!myProgressDialog.isShowing())
                myProgressDialog.show();

            if(year == null)
                year = AUtils.getYearList().get(1);

            if(month == null)
                month = String.valueOf(AUtils.getCurrentMonth()+1);

            mAdapter.fetchHistory(year, month);
        } else {
            noInternetErrorLayout.setVisibility(View.VISIBLE);
        }

        noDataErrorLayout.setVisibility(View.GONE);
        historyGrid.setVisibility(View.GONE);
    }

    private void setHistoryData(List<WasteManagementHistoryPojo> data) {
        noInternetErrorLayout.setVisibility(View.GONE);

        if(!AUtils.isNull(data) && !data.isEmpty()){
            historyGrid.setVisibility(View.VISIBLE);
            noDataErrorLayout.setVisibility(View.GONE);
            InflateWasteHistoryUIAdapter adapter = new InflateWasteHistoryUIAdapter(mContext, true);
            adapter.setWasteHistoryList(data);
            historyGrid.setAdapter(adapter);
        }else{
            historyGrid.setVisibility(View.GONE);
            noDataErrorLayout.setVisibility(View.VISIBLE);
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
