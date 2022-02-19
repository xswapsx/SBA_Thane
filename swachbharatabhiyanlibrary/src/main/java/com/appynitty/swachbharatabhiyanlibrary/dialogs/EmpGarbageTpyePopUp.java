package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

public class EmpGarbageTpyePopUp extends Dialog {

    public Dialog d;

    private final Context mContext;
    private final String mHouseId;

    private RadioButton rb_resident, rb_resident_building_waste, rb_resident_slum_waste, rb_commercial_waste;

    private Button btnSubmit;

    private int mGarbageType = -1;
    private String CType;
    private String mComment;

    private final EmpGarbageTpyePopUp.EmpGarbagePopUpDialogListener mListener;

    private boolean isSubmit = false;

    public EmpGarbageTpyePopUp(@NonNull Context context, String houseId, EmpGarbageTpyePopUp.EmpGarbagePopUpDialogListener listener) {
        super(context);
        mContext = context;
        mHouseId = houseId;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.emp_garbage_type_dialog_layout);

        initComponents();
    }

    private void initComponents() {

        generateID();
        registerEvents();

    }

    private void generateID() {

        rb_resident = findViewById(R.id.rb_resident);
        rb_resident_building_waste = findViewById(R.id.rb_resident_building_waste);
        rb_resident_slum_waste = findViewById(R.id.rb_resident_slum_waste);
        rb_commercial_waste = findViewById(R.id.rb_commercial_waste);
        btnSubmit = findViewById(R.id.btn_empGarbage_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDismiss();
            }
        });
        CType = "NA";
        if (mHouseId.substring(0, 2).matches("^[HhPp]+$")) {
            rb_commercial_waste.setVisibility(View.GONE);
        } else {

        }
    }


    private void registerEvents() {
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                popUpDismiss();
            }
        });

        rb_resident.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CType = "R";
                }
            }
        });

        rb_resident_building_waste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CType = "RBW";
                }
            }
        });

        rb_resident_slum_waste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CType = "RSW";
                }
            }
        });

        rb_commercial_waste.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    CType = "CW";
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick();
            }
        });

    }

    private void onSubmitClick() {

        if (!CType.matches("NA")) {
            this.dismiss();
            isSubmit = true;

        } else {
            AUtils.warning(mContext, mContext.getResources().getString(R.string.garbage_error));
        }
    }

    private void popUpDismiss() {

        if (isSubmit) {
            mListener.onEmpGarbagePopUpDismissed(mHouseId, CType);
        } /*else {
//            mListener.onEmpGarbagePopUpDismissed(mHouseId, "NA");
            Toast.makeText(mContext, "You bad bad!", Toast.LENGTH_SHORT).show();

        }
*/
    }

    public interface EmpGarbagePopUpDialogListener {
        void onEmpGarbagePopUpDismissed(String houseID, String cType);
    }

}
