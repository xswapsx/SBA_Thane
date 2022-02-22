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

import androidx.annotation.NonNull;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

/******** Rahul Rokade 02-21_22 ********/

public class EmpSWMTypePopUpDialog extends Dialog {
    public Dialog d;

    private Context mContext;
    private  String mSwmId;
    private String SwmType;

    private RadioButton rdBtnTs, rdBtnWwpf, rdBtnDwpf, rdBtnCdwpf,rdBtnIfdSwp, rdBtnSlf, rdBtnLwbf, rdBtnStp, rdBtnBwgWwp;
    private Button btnSwmSubmit;

    private int mGarbageType = -1;
    private boolean isSubmit = false;

    private EmpSWMTypePopUpDialogListener mListener;


    public EmpSWMTypePopUpDialog(@NonNull Context context, String mSwmId, EmpSWMTypePopUpDialogListener mListener) {
        super(context);
        this.mContext = context;
        this.mSwmId = mSwmId;
        this.mListener = mListener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.swm_type_dialog_layout);

        initComponents();
    }

    private void initComponents() {

        generateID();
        registerEvents();
    }

    private void generateID() {

        rdBtnTs = findViewById(R.id.rd_btn_ts);
        rdBtnWwpf = findViewById(R.id.rd_btn_wwpf);
        rdBtnDwpf = findViewById(R.id.rd_btn_dwpf);
        rdBtnCdwpf = findViewById(R.id.rd_btn_cdwpf);
        rdBtnIfdSwp = findViewById(R.id.rd_btn_ifd_swp);
        rdBtnSlf = findViewById(R.id.rd_btn_slf);
        rdBtnLwbf = findViewById(R.id.rd_btn_lwbf);
        rdBtnStp = findViewById(R.id.rd_btn_stp);
        rdBtnBwgWwp = findViewById(R.id.rd_btn_bwg_wwp);


        btnSwmSubmit = findViewById(R.id.btn_swm_submit);
        btnSwmSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDismiss();
            }
        });
        SwmType = "NA";
        if (mSwmId.substring(0, 2).matches("^[SsWwMm]+$")) {
            registerEvents();
        }
    }

    private void registerEvents() {
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                popUpDismiss();
            }
        });
        rdBtnTs.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "TS";
                }
            }
        });
        rdBtnWwpf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "WWPF";
                }
            }
        });
        rdBtnDwpf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "DWPF";
                }
            }
        });
        rdBtnCdwpf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "CDWPF";
                }
            }
        });
        rdBtnIfdSwp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "IFDSWP";
                }
            }
        });
        rdBtnSlf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "SLF";
                }
            }
        });
        rdBtnLwbf.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "LWBF";
                }
            }
        });
        rdBtnStp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "STP";
                }
            }
        });
        rdBtnBwgWwp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    SwmType = "BWGWWP";
                }
            }
        });

        btnSwmSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitClick();
            }
        });
    }

    private void onSubmitClick() {

        if (!SwmType.matches("NA")) {
            this.dismiss();
            isSubmit = true;

        } else {
            AUtils.warning(mContext, mContext.getResources().getString(R.string.str_err_select_swm_type));
        }
    }

    private void popUpDismiss() {

        if (isSubmit) {
            mListener.onEmpSWMTypePopUpDialogDismissed(mSwmId, SwmType);
        }
    }

    public interface EmpSWMTypePopUpDialogListener {
        void onEmpSWMTypePopUpDialogDismissed(String swmId, String swmType);
    }
}
