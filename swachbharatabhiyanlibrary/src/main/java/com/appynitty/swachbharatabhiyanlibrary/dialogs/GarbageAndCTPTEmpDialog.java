package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

public class GarbageAndCTPTEmpDialog extends Dialog {

    String empType;
    private final Context mContext;
    private final EmpTypeDialogListener mListener;
    Boolean isDutyOn = AUtils.isIsOnduty();
    Button btnDismiss;
    private static final String TAG = "GarbageAndCTPTEmpDialog";
    RadioButton rbGarbageCollectionEmp, rbCT_PT_Emp;
    RadioGroup rGroupEmpType;


    public GarbageAndCTPTEmpDialog(@NonNull Context context, EmpTypeDialogListener mListener) {
        super(context);
        mContext = context;
        this.mListener = mListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_select_emptype);
        rbGarbageCollectionEmp = findViewById(R.id.rb_garbageCollectionEmp);
        rbCT_PT_Emp = findViewById(R.id.rb_ct_pt_Emp);
        rGroupEmpType = findViewById(R.id.rg_emptype);
        registerEvents();
    }

    private void registerEvents() {

        rbGarbageCollectionEmp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                isDutyOn = AUtils.isIsOnduty();
                if (Prefs.contains(AUtils.PREFS.IS_ON_DUTY)) {
                    if (isDutyOn) {
                        AUtils.info(mContext, mContext.getResources().getString(R.string.off_duty_warning));
                        rbGarbageCollectionEmp.setChecked(false);
                        GarbageAndCTPTEmpDialog.this.dismiss();
                    } else {

                        if (isChecked) {
                            empType = "N";
                            mListener.onDismissEmpTypeDialog(empType);
                            GarbageAndCTPTEmpDialog.this.dismiss();
                        }
                    }
                } else {
                    if (isChecked) {
                        empType = "N";
                        mListener.onDismissEmpTypeDialog(empType);
                        GarbageAndCTPTEmpDialog.this.dismiss();
                    }else{
                        GarbageAndCTPTEmpDialog.this.dismiss();
                    }
                }

            }
        });

        rbCT_PT_Emp.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (Prefs.contains(AUtils.PREFS.IS_ON_DUTY)) {
                    isDutyOn = AUtils.isIsOnduty();
                    if (isDutyOn) {
                        AUtils.info(mContext, mContext.getResources().getString(R.string.off_duty_warning));
                        rbCT_PT_Emp.setChecked(false);
                        GarbageAndCTPTEmpDialog.this.dismiss();
                    } else {

                        if (isChecked) {
                            empType = "CT";
                            mListener.onDismissEmpTypeDialog(empType);
                            GarbageAndCTPTEmpDialog.this.dismiss();
                        }else{
                            GarbageAndCTPTEmpDialog.this.dismiss();
                        }
                    }

                } else {
                    if (isChecked) {
                        empType = "CT";
                        mListener.onDismissEmpTypeDialog(empType);
                        GarbageAndCTPTEmpDialog.this.dismiss();
                    }
                }
            }
        });

    }

    public interface EmpTypeDialogListener {
        void onDismissEmpTypeDialog(String EmpType);
    }
}
