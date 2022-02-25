package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.ImagePojo;
import com.riaylibrary.utils.LocaleHelper;

import java.util.Objects;

public class SLWM_WeightActivity extends AppCompatActivity {

    private static final String TAG = "SLWM_WeightActivity";
    RadioButton radioButtonDryKg, radioButtonWetKg, radioButtonDryTon, radioButtonWetTon;
    RadioGroup radioGroupDry, radioGroupWet;
    Intent intent;
    EditText editDryTotal, editWetTotal;
    TextView tvSlwm_id, tvEditTotal;
    private final String dryImageFilePath = "";
    private final String wetImageFilePath = "";
    private ImagePojo imagePojo;

    String strSlwmid, strGarbageType, strSegregationlvl, strTor;
    Context mContext;
    Toolbar toolbar;


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
        setContentView(R.layout.activity_slwm_weight);

        initViews();
        initToolbar();
        initEvents();
        initData();

        Toast.makeText(SLWM_WeightActivity.this, "houseId: " + strSlwmid + ", "
                + "garbageType: " + strGarbageType
                + ", Tor: " + strTor
                + ", segregationLvl: " + strSegregationlvl, Toast.LENGTH_SHORT).show();
    }

    private void initData() {
        strSlwmid = intent.getStringExtra("houseId");
        strGarbageType = intent.getStringExtra("garbageType");
        strSegregationlvl = intent.getStringExtra("segregationLvl");
        strTor = intent.getStringExtra("toR");

        Log.e(TAG, "onCreate: houseId:- " + strSlwmid
                + ", " + "garbageType:- " + strGarbageType
                + ", segregationLvl:- " + strSegregationlvl
                + ", Tor:- " + strSegregationlvl);

        tvSlwm_id.setText(strSlwmid);

    }

    private void initEvents() {
        editDryTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(".")) {
                    editDryTotal.setText("0.");
                    editDryTotal.setSelection(2);
                } else
                    calculateTotalWeight();
            }
        });
    }

    private void initViews() {

        Button btnSubmitSlwmDetails = findViewById(R.id.btn_submit_slwm);
        ImageView btnTakeDryPhoto = findViewById(R.id.iv_take_dry_photo);
        ImageView btnTakeWetPhoto = findViewById(R.id.iv_take_wet_photo);
        editDryTotal = findViewById(R.id.et_dry_weight);
        editWetTotal = findViewById(R.id.et_wet_weight);

        Double totalTon = 0d;
        Double dryTon = 0d;
        Double wetTon = 0d;

        mContext = SLWM_WeightActivity.this;
        toolbar = findViewById(R.id.toolbarSlwm);
        intent = getIntent();

        tvSlwm_id = findViewById(R.id.txt_house_id);
        tvEditTotal = findViewById(R.id.txtv_total_weight);


        radioGroupDry = findViewById(R.id.rbg_dry_weight);
        radioGroupWet = findViewById(R.id.rbg_wet_weight);

        radioButtonDryKg = findViewById(R.id.rb_dry_kg);
        radioButtonDryKg.setChecked(true);
        radioButtonDryTon = findViewById(R.id.rb_dry_ton);
        radioButtonWetKg = findViewById(R.id.rb_wet_kg);
        radioButtonWetKg.setChecked(true);
        radioButtonWetTon = findViewById(R.id.rb_wet_ton);


    }

    private void initToolbar() {
        toolbar.setTitle(R.string.slwm_details_title);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }


}