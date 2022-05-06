package com.appynitty.swachbharatabhiyanlibrary.dialogs;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.io.File;

public class ChooseActionPopUp extends Dialog {

    private static final String TAG = "ChooseActionPopUp";
    private ChooseActionPopUpDialogListener chooseActionPopUpDialogListener;
    private TextView tvHouseId;
    private Button addDetailsBtn, skipBtn;
    ImageView ivQR_image;
    private Object data;
    private String mId, mPath;
    public static final int SKIP_BUTTON_CLICKED = 0;
    public static final int ADD_DETAILS_BUTTON_CLICKED = 1;
    Context mContext;
    Bitmap bmp = null;
    Bitmap result = null;
    Bitmap result1 = null;
    Uri uri;

    boolean isImageFitToScreen;

    public ChooseActionPopUp(Context context) {
        super(context);
        mContext = context;
    }

    public void setChooseActionPopUpDialogListener(ChooseActionPopUpDialogListener chooseActionPopUpDialogListener) {
        this.chooseActionPopUpDialogListener = chooseActionPopUpDialogListener;
    }

    public void setData(String mId, String mPath) {
        this.mId = mId;
        this.mPath = mPath;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_choose_option);

        initComponents();
    }

    private void initComponents() {

        generateID();
        registerEvents();
        initData();
    }

    private void generateID() {
        skipBtn = findViewById(R.id.btn_skip);
        addDetailsBtn = findViewById(R.id.btn_add_details);
        addDetailsBtn.setVisibility(View.GONE);
        ivQR_image = findViewById(R.id.imgQRimg);
        tvHouseId = findViewById(R.id.txt_houseId);
    }

    private void initData() {

        if (mPath != null && !mPath.trim().isEmpty()) {
            uri = Uri.fromFile(new File(mPath));
            ivQR_image.setImageBitmap(AUtils.loadFromUri(uri, mContext));
            tvHouseId.setText(mId);
        }

    }

    private void registerEvents() {

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dismissPopup();
            }
        });

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseActionPopUpDialogListener.onChooseActionPopUpDismissed(mId, SKIP_BUTTON_CLICKED);
                dismissPopup();
            }
        });

        addDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chooseActionPopUpDialogListener.onChooseActionPopUpDismissed(mId, ADD_DETAILS_BUTTON_CLICKED);
                dismissPopup();
            }
        });

        ivQR_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isImageFitToScreen) {
                    isImageFitToScreen = false;
                    ivQR_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                    ivQR_image.setAdjustViewBounds(true);
                } else {
                    isImageFitToScreen = true;
                    ivQR_image.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
                    ivQR_image.setScaleType(ImageView.ScaleType.FIT_XY);
                }
            }
        });

    }

    private void dismissPopup() {
        this.dismiss();
    }

    public interface ChooseActionPopUpDialogListener {
        void onChooseActionPopUpDismissed(String mId, int actionType);
    }

}
