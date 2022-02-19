package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appynitty.swachbharatabhiyanlibrary.R;

/**
 * Created by Swapnil on 25/01/22.
 */

public class CommercialFirstDialog extends Fragment {
    Button btn_CommercialGarbageNext;
    private int mGarbageType = -1;
    private final Context mContext;
    private final String mHouseId;
    private final String cType;

    private RadioButton rb_wetWaste, rb_dryWaste, rb_mixedWaste;    //for Commercial
    private RadioButton rb_domesticHazardWaste, rb_sanitaryWaste;      //for residential

    private static final String TAG = "CommercialFirstDialog";


    public CommercialFirstDialog(Context mContext, String mHouseId, String cType) {
        this.mContext = mContext;
        this.mHouseId = mHouseId;
        this.cType = cType;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.garbage_type_dialog_commercial, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn_CommercialGarbageNext = view.findViewById(R.id.btn_garbage_next);
        rb_wetWaste = view.findViewById(R.id.rb_wetWaste);
        rb_dryWaste = view.findViewById(R.id.rb_dryWaste);
        rb_mixedWaste = view.findViewById(R.id.rb_mixedWaste);
        rb_domesticHazardWaste = view.findViewById(R.id.rb_domesticHazardWaste);
        rb_sanitaryWaste = view.findViewById(R.id.rb_sanitaryWaste);

        registerEvents();
        btn_CommercialGarbageNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBtnPressed(mHouseId, String.valueOf(mGarbageType));
            }
        });
    }

    private void registerEvents() {

        if (cType.equals("CW")) {
            rb_domesticHazardWaste.setVisibility(View.GONE);
            rb_sanitaryWaste.setVisibility(View.GONE);
        }

        rb_wetWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 6;
                }
            }
        });

        rb_dryWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 7;
                }
            }
        });

        rb_mixedWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 0;
                }
            }
        });

        rb_domesticHazardWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 8;
                }
            }
        });

        rb_sanitaryWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 9;
                }
            }
        });

    }

    void onBtnPressed(String HouseID, String GarbageType) {

        Log.e(TAG, "onButtonPressed: " + HouseID + ", " + GarbageType);
        FirstDialog listener = (FirstDialog) getParentFragment();
        listener.onNextBtnPressed(HouseID, GarbageType);
    }

    public interface FirstDialog {
        void onNextBtnPressed(String n, String p);
    }
}