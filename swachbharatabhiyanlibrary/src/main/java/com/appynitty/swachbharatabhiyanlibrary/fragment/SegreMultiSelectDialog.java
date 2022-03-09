package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appynitty.swachbharatabhiyanlibrary.R;

public class SegreMultiSelectDialog extends Fragment {
    private static final String TAG = "SegreMultiSelectDialog";
    private final Context mContext;
    private final String mHouseId;
    private final String mGarbageType;

    String Wet, Dry, Sanitary, Domestic, cType;

    CheckBox cbWetWaste, cbDryWaste, cbDomestic, cbSanitary;
    Button btnNext;

    public SegreMultiSelectDialog(Context mContext, String mHouseId, String mGarbageType) {
        this.mContext = mContext;
        this.mHouseId = mHouseId;
        this.mGarbageType = mGarbageType;

        if (mHouseId.substring(0, 2).matches("^[CcPp]+$")) {
            cType = "CW";
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate: " + mHouseId + ", " + mGarbageType);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.garbage_type_dialog_segregation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cbWetWaste = view.findViewById(R.id.cb_wetWaste);
        cbDryWaste = view.findViewById(R.id.cb_dryWaste);
        cbDomestic = view.findViewById(R.id.cb_domesticHazardWaste);
        cbSanitary = view.findViewById(R.id.cb_sanitaryWaste);

        if (cType.equals("CW")) {
            cbDomestic.setVisibility(View.GONE);
            cbSanitary.setVisibility(View.GONE);
        }
        btnNext = view.findViewById(R.id.btn_segregatedNext);

        registerEvents();
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG, "onClick: Wet: " + Wet + ", Dry: " + Dry + ", Domestic: " + Domestic + ", Sanitary: " + Sanitary);
                onNextbtnPressed(Wet, Dry, Domestic, Sanitary);
            }
        });
    }

    private void onNextbtnPressed(String wet, String dry, String domestic, String sanitary) {
        SegreMultiDialog listener = (SegreMultiDialog) getParentFragment();
        listener.onSegreNextPressed(wet, dry, domestic, sanitary);
    }


    private void registerEvents() {
        cbWetWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Wet = "1";
                else
                    Wet = "0";
            }
        });

        cbDryWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Dry = "1";
                else
                    Dry = "0";
            }
        });

        cbDomestic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Domestic = "1";
                else
                    Domestic = "0";
            }
        });

        cbSanitary.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    Sanitary = "1";
                else
                    Sanitary = "0";
            }
        });
    }

    public interface SegreMultiDialog {
        void onSegreNextPressed(String Wet, String Dry, String Domestic, String Sanitary);
    }
}
