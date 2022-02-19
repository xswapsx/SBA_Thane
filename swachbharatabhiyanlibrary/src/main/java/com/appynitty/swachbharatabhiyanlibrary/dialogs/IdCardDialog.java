package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.bumptech.glide.Glide;
import com.riaylibrary.custom_component.GlideCircleTransformation;

public class IdCardDialog extends Dialog {

    public Dialog d;

    private final Context mContext;
    private final String mUserName;
    private final String mEmpID;
    private final String mProfileUrl;
    private final String mEmpType;

    private TextView lblUserName, lblEmpId, lblEmpType;

    private ImageView imgProfilePic;

    private ImageView imgClose;

    public IdCardDialog(Context context, String userName, String empId, String profileUrl, String empTypeName) {
        super(context);

        mContext = context;
        mUserName = userName;
        mEmpID = empId;
        mProfileUrl = profileUrl;
        //rahul
        mEmpType = empTypeName;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_id_card);
        this.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = this.getWindow().getAttributes();
        params.height = (int) mContext.getResources().getDimension(R.dimen.dim_480_dp);

        this.getWindow().setAttributes(params);

        initComponents();
    }

    private void initComponents() {

        generateID();
        registerEvents();
        initData();
    }

    private void generateID() {

        lblUserName = findViewById(R.id.user_name);
        lblEmpId = findViewById(R.id.emp_id);
        //rahul
        lblEmpType = findViewById(R.id.txt_emp_type);
        imgProfilePic = findViewById(R.id.profile_pic);

        imgClose = findViewById(R.id.btn_close);
    }

    private void initData() {

        if(!AUtils.isNullString(mProfileUrl))
        {
            try{
                Glide.with(mContext).load(mProfileUrl)
                        .placeholder(R.drawable.ic_user_white)
                        .error(R.drawable.ic_user_white)
                        .centerCrop()
                        .bitmapTransform(new GlideCircleTransformation(mContext.getApplicationContext()))
                        .into(imgProfilePic);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        lblUserName.setText(mUserName);
        lblEmpId.setText(mEmpID);
        //rahul
        String empType = mEmpType;
        if (empType != null){
            if (empType.matches("N")) {
                lblEmpType.setText(mContext.getResources().getString(R.string.household_collection));

            } else if (empType.matches("S")) {
                lblEmpType.setText(mContext.getResources().getString(R.string.street_sweeping));

            } else if (empType.matches("L")) {
                lblEmpType.setText(mContext.getResources().getString(R.string.liquid_waste_cleaning));

            }
        }
        else {
            lblEmpType.setText(mContext.getResources().getString(R.string.household_collection));
        }
    }

    private void registerEvents() {
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClose();
            }
        });

    }

    private void onClose()
    {
        this.dismiss();
    }
}
