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
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appynitty.swachbharatabhiyanlibrary.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swapnil on 25/01/22.
 */

public class CommercialFirstDialog extends Fragment {
    Button btn_bifurcatedNext;
    private int mGarbageType = -1;
    private final Context mContext;
    private final String mHouseId;
    private final String cType;
    private CheckBox cbWetWaste, cbDryWaste, cbMixedWaste, cbDomesticHazardWaste, cbSanitaryWaste, cbWasteNotReceived;
    private RadioButton rb_wetWaste, rb_dryWaste, rb_mixedWaste;    //for Commercial
    private RadioButton rb_domesticHazardWaste, rb_sanitaryWaste;      //for residential
    String text;
    List<String> items;

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
        return inflater.inflate(R.layout.garbage_type_dialog, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        btn_bifurcatedNext = view.findViewById(R.id.btn_bifurcatedNext);


        cbWetWaste = view.findViewById(R.id.cb_wetWaste);
        cbDryWaste = view.findViewById(R.id.cb_dryWaste);
        cbMixedWaste = view.findViewById(R.id.cb_mixedWaste);
        cbDomesticHazardWaste = view.findViewById(R.id.cb_domesticHazardWaste);
        cbSanitaryWaste = view.findViewById(R.id.cb_sanitaryWaste);
        cbWasteNotReceived = view.findViewById(R.id.cb_wasteNotReceived);
        items = new ArrayList<>();
        /*rb_wetWaste = view.findViewById(R.id.rb_wetWaste);
        rb_dryWaste = view.findViewById(R.id.rb_dryWaste);
        rb_mixedWaste = view.findViewById(R.id.rb_mixedWaste);
        rb_domesticHazardWaste = view.findViewById(R.id.rb_domesticHazardWaste);
        rb_sanitaryWaste = view.findViewById(R.id.rb_sanitaryWaste);*/

        registerEvents();


        btn_bifurcatedNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Log.e(TAG, "onClick: Selected items are: " + items.toString());
//                onBtnPressed(mHouseId, String.valueOf(mGarbageType));
                onBtnPressed(mHouseId, items);
            }
        });
    }

    private void registerEvents() {

        if (cType.equals("CW")) {
            cbDomesticHazardWaste.setVisibility(View.GONE);
            cbSanitaryWaste.setVisibility(View.GONE);

            /*rb_domesticHazardWaste.setVisibility(View.GONE);
            rb_sanitaryWaste.setVisibility(View.GONE);*/
        }

        cbWetWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!items.contains("6")) {
                        items.add("6");
                        cbWasteNotReceived.setChecked(false);
                    } else
                        items.remove("6");
                }
            }
        });

        cbDryWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!items.contains("7")) {
                        items.add("7");
                        cbWasteNotReceived.setChecked(false);
                    } else
                        items.remove("7");
                }
            }
        });

        cbMixedWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!items.contains("0")) {
                        items.add("0");
                        cbWasteNotReceived.setChecked(false);
                    } else
                        items.remove("0");

                }
            }
        });

        cbDomesticHazardWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!items.contains("8")) {
                        items.add("8");
                        cbWasteNotReceived.setChecked(false);
                    } else
                        items.remove("8");
                }
            }
        });

        cbSanitaryWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    if (!items.contains("9")) {
                        items.add("9");
                        cbWasteNotReceived.setChecked(false);
                    } else
                        items.remove("9");
                }
            }
        });

        cbWasteNotReceived.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    cbWetWaste.setChecked(false);
                    cbDryWaste.setChecked(false);
                    cbMixedWaste.setChecked(false);
                    cbDomesticHazardWaste.setChecked(false);
                    cbSanitaryWaste.setChecked(false);

                    if (!items.contains("-1")) {
                        items.removeAll(items);
                        items.add("-1");

                    } /*else
                        items.remove("-1");*/
                } else {
                    items.remove("-1");
                }
            }
        });
        /*rb_wetWaste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        });*/

    }

    void onBtnPressed(String HouseID, List<String> GarbageType) {

        Log.e(TAG, "onButtonPressed: " + HouseID + ", " + GarbageType);
        FirstDialog listener = (FirstDialog) getParentFragment();
        listener.onNextBtnPressed(HouseID, GarbageType);
    }

    public interface FirstDialog {
        void onNextBtnPressed(String houseId, List<String> garbageTypeList);
    }
}