package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.BroadcastMessageAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.CollectionAreaAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.riaylibrary.utils.LocaleHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class BroadcastActivity extends AppCompatActivity {

    private final static String TAG = "BroadcastActivity";
    private Context mContext;
    private Toolbar toolbar;
    private AutoCompleteTextView areaAutoComplete;
    private Button submitBtn;

    private HashMap<String, String> areaHash;

    private List<CollectionAreaPojo> areaPojoList;

    private CollectionAreaAdapterClass mAreaAdapter;
    private BroadcastMessageAdapterClass mAdapter;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
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
        if(AUtils.isInternetAvailable())
        {
            AUtils.hideSnackBar();
        }
        else {
            AUtils.showSnackBar(findViewById(R.id.parent));
        }
    }

    protected void generateId() {
        setContentView(R.layout.activity_broadcast);

        toolbar = findViewById(R.id.toolbar);

        mContext = BroadcastActivity.this;
        AUtils.currentContextConstant = mContext;

        mAreaAdapter = new CollectionAreaAdapterClass();
        mAdapter = new BroadcastMessageAdapterClass();

        areaAutoComplete = findViewById(R.id.txt_area_auto);
        areaAutoComplete.setThreshold(0);
        areaAutoComplete.setDropDownBackgroundResource(R.color.white);
        areaAutoComplete.setSingleLine();

        submitBtn = findViewById(R.id.submit_button);

        areaPojoList = null;

        initToolbar();
    }

    protected void initToolbar(){
        toolbar.setTitle(getResources().getString(R.string.title_activity_broadcast_page));
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    protected void registerEvents() {

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isAreaValid())
                {
                    mAdapter.sendBroadcastMessage(areaHash.get(areaAutoComplete.getText().toString().toLowerCase()));
                    AUtils.success(mContext, mContext.getResources().getString(R.string.sending_msg), Toast.LENGTH_SHORT);
                    finish();
                }
            }
        });

        areaAutoComplete.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean isFocused) {
                if(isFocused){
                    areaAutoComplete.showDropDown();
                    AUtils.showKeyboard((Activity)mContext);
                }
            }
        });

        areaAutoComplete.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String area = areaAutoComplete.getText().toString();
            }
        });

        mAreaAdapter.setCollectionAreaListener(new CollectionAreaAdapterClass.CollectionAreaListener() {
            @Override
            public void onSuccessCallBack() {
                inflateAreaAutoComplete(mAreaAdapter.getAreaPojoList());
            }

            @Override
            public void onFailureCallBack() {
                AUtils.error(mContext, getResources().getString(R.string.serverError));
            }
        });

        mAdapter.setBroadcastMessageListener(new BroadcastMessageAdapterClass.BroadcastMessageListener() {
            @Override
            public void onSuccessCallBack() {

            }

            @Override
            public void onFailureCallBack() {

            }
        });
    }

    protected void initData() {

        if(! AUtils.isConnectedFast(mContext))
        {
            AUtils.warning(mContext,getResources().getString(R.string.slow_internet));
        }
        mAreaAdapter.fetchAreaList(AUtils.HP_AREA_TYPE_ID, true);
    }

    private void inflateAreaAutoComplete(List<CollectionAreaPojo> pojoList){

        areaHash = new HashMap<>();
        ArrayList<String> keyList = new ArrayList<>();
        for(CollectionAreaPojo pojo : pojoList){
            areaHash.put(pojo.getArea().toLowerCase(), pojo.getId());
            keyList.add(pojo.getArea().trim());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(mContext, android.R.layout.simple_dropdown_item_1line, keyList);
        areaAutoComplete.setThreshold(0);
        areaAutoComplete.setAdapter(adapter);
        if(!areaAutoComplete.isFocused()){
            areaAutoComplete.requestFocus();
        }

    }

    private boolean isAreaValid() {
        String area = areaAutoComplete.getText().toString().toLowerCase();
        if(areaHash.containsKey(area)) {
            return true;
        }
        else {
            AUtils.error(mContext, mContext.getResources().getString(R.string.area_validation));
            return false;
        }
    }
}
