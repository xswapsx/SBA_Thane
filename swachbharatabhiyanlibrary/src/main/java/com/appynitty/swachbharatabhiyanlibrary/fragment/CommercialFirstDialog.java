package com.appynitty.swachbharatabhiyanlibrary.fragment;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.appynitty.swachbharatabhiyanlibrary.R;

/**
 * Created by Swapnil on 25/01/22.
 */

public class CommercialFirstDialog extends Fragment {
    Button btn_CommercialGarbageNext, btnSLWM_garbage_next;
    private int mGarbageType;
    private final Context mContext;
    private final String mHouseId;
    private final String cType;
    String slwmCtype, sComment;
    private String selectedFacility = "Nuffin";
    private EditText etComment, etsComment;
    private RadioButton rb_wetWaste, rb_dryWaste, rb_mixedWaste;    //for Commercial
    private RadioButton rb_domesticHazardWaste, rb_sanitaryWaste, rb_garbageNotReceived, rb_segregated, rb_garbageNotSpecified;      //for residential
    private RadioButton rb_municipleSW, rb_Compost, rb_Biogas, rb_biodigesterBS,    //for SLWM
            rb_refuseDerivedFuel, rb_Plastics, rb_metal_and_glass, rb_cardboards, rb_anyOtherVeriety, rb_Soil,
            rb_Sand, rb_8mmAggregates, rb_8_16mm_aggregates, rb_16mm_aggregates, rb_manufactured_goods_bricks_tiles_etc,
            rb_fly_ash, rb_sand_and_soil, rb_dried_sludge, rb_treated_wastewater, rb_sanitaryLandfill;
    private static final String TAG = "CommercialFirstDialog";


    public CommercialFirstDialog(Context mContext, String mHouseId, String cType, String slwmCtype) {
        this.mContext = mContext;
        this.mHouseId = mHouseId;
        this.cType = cType;
        this.slwmCtype = slwmCtype;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        if (cType.matches("CW") || cType.matches("R"))
            return inflater.inflate(R.layout.garbage_type_dialog_commercial, container, false);
        else
            return inflater.inflate(R.layout.slwm_popup_layout, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        // For residential and commercial
        btn_CommercialGarbageNext = view.findViewById(R.id.btn_garbage_next);
        etComment = view.findViewById(R.id.txt_garbage_comments);
        rb_wetWaste = view.findViewById(R.id.rb_wetWaste);
        rb_dryWaste = view.findViewById(R.id.rb_dryWaste);
        rb_mixedWaste = view.findViewById(R.id.rb_mixedWaste);
        rb_domesticHazardWaste = view.findViewById(R.id.rb_domesticHazardWaste);
        rb_sanitaryWaste = view.findViewById(R.id.rb_sanitaryWaste);
        rb_garbageNotReceived = view.findViewById(R.id.rb_garbage_not_received);
        rb_segregated = view.findViewById(R.id.rb_segregated);
//        rb_garbageNotSpecified = view.findViewById(R.id.rb_garbage_not_specified);

        // For SLWM
        btnSLWM_garbage_next = view.findViewById(R.id.btn_slwm_garbage_next);
        etsComment = view.findViewById(R.id.et_garbage_comments);

        rb_sanitaryLandfill = view.findViewById(R.id.rb_SLF);
        rb_municipleSW = view.findViewById(R.id.rb_municipleSW);
        rb_Compost = view.findViewById(R.id.rb_Compost);
        rb_Biogas = view.findViewById(R.id.rb_Biogas);
        rb_biodigesterBS = view.findViewById(R.id.rb_biodigesterBS);
        rb_refuseDerivedFuel = view.findViewById(R.id.rb_refuseDerivedFuel);
        rb_Plastics = view.findViewById(R.id.rb_Plastics);
        rb_metal_and_glass = view.findViewById(R.id.rb_metal_and_glass);
        rb_cardboards = view.findViewById(R.id.rb_cardboards);
        rb_anyOtherVeriety = view.findViewById(R.id.rb_anyOtherVeriety);
        rb_Soil = view.findViewById(R.id.rb_Soil);
        rb_Sand = view.findViewById(R.id.rb_Sand);
        rb_8mmAggregates = view.findViewById(R.id.rb_8mmAggregates);
        rb_8_16mm_aggregates = view.findViewById(R.id.rb_8_16mm_aggregates);
        rb_16mm_aggregates = view.findViewById(R.id.rb_16mm_aggregates);
        rb_manufactured_goods_bricks_tiles_etc = view.findViewById(R.id.rb_manufactured_goods_bricks_tiles_etc);
        rb_fly_ash = view.findViewById(R.id.rb_fly_ash);
        rb_sand_and_soil = view.findViewById(R.id.rb_sand_and_soil);
        rb_dried_sludge = view.findViewById(R.id.rb_dried_sludge);
        rb_treated_wastewater = view.findViewById(R.id.rb_treated_wastewater);

        if (cType.matches("CW") || cType.matches("R")) {
            registerEventsForResiAndCommercials();

            btn_CommercialGarbageNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sComment = etComment.getText().toString();
                    onBtnPressed(mHouseId, String.valueOf(mGarbageType));
                }
            });
        } else {
            registerEventsForSLWM();

            btnSLWM_garbage_next.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    sComment = etsComment.getText().toString();
                    if (selectedFacility.matches("Nuffin"))
                        Toast.makeText(mContext, "please select a facility", Toast.LENGTH_SHORT).show();
                    else {
                        onSlwmNextBtnPressed(mHouseId, String.valueOf(mGarbageType), selectedFacility);
                    }
                }
            });
        }

    }

    private void registerEventsForSLWM() {
        init();

        rb_municipleSW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_municipleSW.getText().toString();

            }
        });

        rb_Compost.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_Compost.getText().toString();
            }
        });

        rb_Biogas.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_Biogas.getText().toString();
            }
        });

        rb_biodigesterBS.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_biodigesterBS.getText().toString();
            }
        });

        rb_refuseDerivedFuel.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_refuseDerivedFuel.getText().toString();
            }
        });

        rb_Plastics.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_Plastics.getText().toString();
            }
        });

        rb_metal_and_glass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_metal_and_glass.getText().toString();
            }
        });

        rb_cardboards.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_cardboards.getText().toString();
            }
        });

        rb_anyOtherVeriety.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_anyOtherVeriety.getText().toString();
            }
        });

        rb_Soil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_Soil.getText().toString();
            }
        });
        rb_Sand.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_Sand.getText().toString();
            }
        });
        rb_8mmAggregates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_8mmAggregates.getText().toString();
            }
        });
        rb_8_16mm_aggregates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_8_16mm_aggregates.getText().toString();
            }
        });
        rb_16mm_aggregates.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_16mm_aggregates.getText().toString();
            }
        });
        rb_manufactured_goods_bricks_tiles_etc.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_manufactured_goods_bricks_tiles_etc.getText().toString();
            }
        });
        rb_fly_ash.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_fly_ash.getText().toString();
            }
        });
        rb_sand_and_soil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_sand_and_soil.getText().toString();
            }
        });
        rb_dried_sludge.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_dried_sludge.getText().toString();
            }
        });
        rb_treated_wastewater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_treated_wastewater.getText().toString();
            }
        });

        rb_sanitaryLandfill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                    selectedFacility = rb_sanitaryLandfill.getText().toString();
            }
        });

    }

    private void registerEventsForResiAndCommercials() {

        if (cType.equals("CW")) {
            rb_domesticHazardWaste.setVisibility(View.GONE);
            rb_sanitaryWaste.setVisibility(View.GONE);
        } else if (cType.equals("R")) {
            rb_dryWaste.setVisibility(View.GONE);
            rb_wetWaste.setVisibility(View.GONE);
            rb_sanitaryWaste.setVisibility(View.GONE);
            rb_domesticHazardWaste.setVisibility(View.GONE);
            rb_segregated.setVisibility(View.VISIBLE);
        }

        rb_segregated.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    mGarbageType = 1;
                }
            }
        });

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

        rb_garbageNotReceived.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = 2;
                }
            }
        });

        /*rb_garbageNotSpecified.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    mGarbageType = -1;
                }
            }
        });*/  //Garbage not collected => mGarbageType = 2 and Garbage not specified => mGarbageType = -1

    }

    private void init() {
        if (slwmCtype.matches("TS")) {
            rb_municipleSW.setVisibility(View.VISIBLE);
        } else if (slwmCtype.matches("CDWPF")) {
            rb_Soil.setVisibility(View.VISIBLE);
            rb_Sand.setVisibility(View.VISIBLE);
            rb_8mmAggregates.setVisibility(View.VISIBLE);
            rb_8_16mm_aggregates.setVisibility(View.VISIBLE);
            rb_16mm_aggregates.setVisibility(View.VISIBLE);
            rb_manufactured_goods_bricks_tiles_etc.setVisibility(View.VISIBLE);
        } else if (slwmCtype.matches("WWPF")) {
            rb_Compost.setVisibility(View.VISIBLE);
            rb_Biogas.setVisibility(View.VISIBLE);
            rb_biodigesterBS.setVisibility(View.VISIBLE);
        } else if (slwmCtype.matches("STP")) {
            rb_dried_sludge.setVisibility(View.VISIBLE);
            rb_treated_wastewater.setVisibility(View.VISIBLE);
        } else if (slwmCtype.matches("SLF")) {
            rb_sanitaryLandfill.setVisibility(View.VISIBLE);
        } else if (slwmCtype.matches("DWPF")) {
            rb_refuseDerivedFuel.setVisibility(View.VISIBLE);
            rb_Plastics.setVisibility(View.VISIBLE);
            rb_metal_and_glass.setVisibility(View.VISIBLE);
            rb_cardboards.setVisibility(View.VISIBLE);
            rb_anyOtherVeriety.setVisibility(View.VISIBLE);
        }
    }

    void onBtnPressed(String HouseID, String GarbageType) {
        sComment = etComment.getText().toString();

        Log.e(TAG, "onButtonPressed: " + HouseID + ", GarbageType: " + GarbageType + ", Comment: " + sComment);
        FirstDialog listener = (FirstDialog) getParentFragment();
        listener.onNextBtnPressed(HouseID, GarbageType, sComment);
    }

    void onSlwmNextBtnPressed(String mHouseId, String GarbageType, String selectedFacility) {
        sComment = etsComment.getText().toString();
        Log.e(TAG, "onSlwmNextBtnPressed: " + selectedFacility + ", comment:- " + sComment);
        FirstSLWMDialog slwmDialogListener = (FirstSLWMDialog) getParentFragment();
        slwmDialogListener.slwmOnNextBtnPressed(mHouseId, GarbageType, selectedFacility, sComment);
    }

    public interface FirstDialog {
        void onNextBtnPressed(String HouseID, String GarbageType, String sComment);
    }

    public interface FirstSLWMDialog {
        void slwmOnNextBtnPressed(String houseId, String GarbageType, String TOR, String sComment);
    }
}