package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.annotation.Nullable;

import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.DialogAdapter;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.HashMap;
import java.util.List;

public class PopUpDialog extends Dialog {

    private final Context mContext;
    private final String mType;

    private ListView mItemList;

    private LinearLayout hiddenView;
    private EditText txtVehicleNo;
    private Button btnSubmit;
    private TextView lblTitle;
    private DialogAdapter mAdapter;
    private final HashMap<Integer, Object> mList;

    private Object mReturnData;
    private String mVehicleNo;

    private final PopUpDialogListener mListener;

    public PopUpDialog(Context context, String type, HashMap<Integer, Object> list, PopUpDialogListener listener) {
        super(context);
        // TODO Auto-generated constructor stub
        mContext = context;
        mType = type;
        mList = list;
        mListener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_layout);

        initComponents();
    }

    private void initComponents() {

        generateID();
        registerEvents();
        initData();
    }

    private void generateID() {

        lblTitle = findViewById(R.id.lbl_title);
        mItemList = findViewById(R.id.dialog_listview);

        hiddenView = findViewById(R.id.dialog_input_view);

        if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE))
        {

            txtVehicleNo = findViewById(R.id.txt_vehicle_no);
            txtVehicleNo.setSingleLine(true);
            btnSubmit = findViewById(R.id.btn_submit);
        }
    }

    private void initData() {
        if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE))
        {
            lblTitle.setText(mContext.getResources().getString(R.string.dialog_title_txt_vehicle));
        }
        else
        {
            lblTitle.setText(mContext.getResources().getString(R.string.change_language));
        }

        mAdapter = new DialogAdapter(mContext, mList, mType);

        mItemList.setAdapter(mAdapter);


    }

    private void registerEvents() {

        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                popUpDismiss();
            }
        });

        mItemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                itemSelected(position);
            }
        });
        if(!AUtils.isNull(btnSubmit))
        {
            btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onSubmitClick();
                }
            });
        }
    }

    private void itemSelected(int postion)
    {
         mReturnData = mList.get(postion);
         if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE))
         {
             hiddenView.setVisibility(View.VISIBLE);
             mItemList.setVisibility(View.GONE);
         }
         else {
             this.dismiss();
         }
    }

    private void onSubmitClick()
    {
        mVehicleNo = txtVehicleNo.getText().toString();
        if(!mVehicleNo.isEmpty()){
            this.dismiss();
        }else {
            txtVehicleNo.setError(mContext.getString(R.string.noVehicleNo));
        }
    }

    private void popUpDismiss()
    {
        if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE))
        {
            /*if(!AUtils.isNull(mListener))
            {
                mListener.onPopUpDismissed(mType, mReturnData,mVehicleNo);
            }*/

         /****** Rahul**********/
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if(!AUtils.isNull(mListener))
                    {
                        mListener.onPopUpDismissed(mType, mReturnData,mVehicleNo);
                    }
                }
            }, 1000*2);
        }
        else {
            if(!AUtils.isNull(mListener))
            {
                mListener.onPopUpDismissed(mType, mReturnData,"");
            }
        }
    }

    public interface PopUpDialogListener {
        void onPopUpDismissed(String type, Object listItemSelected, @Nullable String vehicleNo);
    }

}



