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
public class ToiletTypePopUp extends Dialog {

    public Dialog d;

    private Context mContext;
    private  String mToiletId;
    private String ToiletType;

    private RadioButton rb_communityToilet, rb_publicToilet, rb_urinal;

    private int mGarbageType = -1;

    private Button btnSubmit;
    private boolean isSubmit = false;

    private ToiletTypePopUpDialogListener mListener;


    public ToiletTypePopUp(@NonNull Context context, String mToiletId, ToiletTypePopUpDialogListener mListener) {
        super(context);
        this.mContext = context;
        this.mToiletId = mToiletId;
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.ctpt_toilet_type_dialog_layout);

        initComponents();
    }

    private void initComponents() {

        generateID();
        registerEvents();
    }

    private void generateID() {

        rb_communityToilet = findViewById(R.id.rb_community_toilet);
        rb_publicToilet = findViewById(R.id.rb_public_toilet);
        rb_urinal = findViewById(R.id.rb_urinal);
        btnSubmit = findViewById(R.id.btn_toilet_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popUpDismiss();
            }
        });
        ToiletType = "NA";
        if (mToiletId.substring(0, 2).matches("^[CcTtPpTt]+$")) {
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
        rb_communityToilet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToiletType = "CT";
                }
            }
        });

        rb_publicToilet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToiletType = "PT";
                }
            }
        });

        rb_urinal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    ToiletType = "U";
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

        if (!ToiletType.matches("NA")) {
            this.dismiss();
            isSubmit = true;

        } else {
            AUtils.warning(mContext, mContext.getResources().getString(R.string.str_err_select_type));
        }
    }

    private void popUpDismiss() {

        if (isSubmit) {
            mListener.onToiletTypePopUpDismissed(mToiletId, ToiletType);
        }
    }

    public interface ToiletTypePopUpDialogListener {
        void onToiletTypePopUpDismissed(String toiletId, String toiletType);
    }

}
