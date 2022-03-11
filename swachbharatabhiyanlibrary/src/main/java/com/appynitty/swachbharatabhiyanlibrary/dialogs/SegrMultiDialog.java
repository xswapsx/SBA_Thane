package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;

public class SegrMultiDialog extends Dialog {

    private  Context mContext;
    private String mHouseId;
    private String mGarbageType;
    private SegrMultiDialogListener listener;

    String Wet, Dry, Sanitary, Domestic, cType;

    CheckBox cbWetWaste, cbDryWaste, cbDomestic, cbSanitary;
    Button btnNext;

    public SegrMultiDialog(Context context, String mHouseId, String mGarbageType, SegrMultiDialogListener listener) {
        super(context);
        this.mContext = context;
        this.mHouseId = mHouseId;
        this.mGarbageType = mGarbageType;
        this.listener = listener;

        if (mHouseId.substring(0, 2).matches("^[CcPp]+$")) {
            cType = "CW";
        }
    }

    public interface SegrMultiDialogListener {
        void onSegrMultiDialogPressed(String Wet, String Dry, String Domestic, String Sanitary);
    }
}
