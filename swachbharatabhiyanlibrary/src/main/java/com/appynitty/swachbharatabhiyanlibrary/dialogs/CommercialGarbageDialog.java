package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.activity.SLWM_WeightActivity;
import com.appynitty.swachbharatabhiyanlibrary.db.AppDatabase;
import com.appynitty.swachbharatabhiyanlibrary.fragment.CommercialFirstDialog;
import com.appynitty.swachbharatabhiyanlibrary.fragment.CommercialNextDialog;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

/**
 * Created by Swapnil on 25/01/22.
 */


public class CommercialGarbageDialog extends DialogFragment implements CommercialFirstDialog.FirstDialog, CommercialFirstDialog.FirstSLWMDialog, CommercialNextDialog.SecondDialog {
    private static final String TAG = "CommercialGarbageDialog";
    Context mContext;
    String mHouseId;
    String mGarbageType;
    String cType;
    String mTOR = "nuffin";
    String innerCType;

    CommercialGarbageDialog.CustomDialogInterface mListener;

    public CommercialGarbageDialog(Context context, String mHouseId, String cType, CustomDialogInterface mListener) {
        this.mContext = context;
        this.mListener = mListener;
        this.mHouseId = mHouseId;
        this.cType = cType;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.getDialog().setCanceledOnTouchOutside(false);
        return inflater.inflate(R.layout.commercial_dialog_container, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        AppDatabase db = AppDatabase.getDbInstance(AUtils.mainApplicationConstant);
        innerCType = db.houseDao().getCtypeFromHouseID(mHouseId);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        CommercialFirstDialog commercialFirstDialog = new CommercialFirstDialog(mContext, mHouseId, cType, innerCType);
        transaction.replace(R.id.commercialDialog_container, commercialFirstDialog);
        transaction.commit();
    }

    @Override
    public void onNextBtnPressed(String houseId, String mGarbageType) {
//        Toast.makeText(mContext, " " + houseId + ", " + mGarbageType, Toast.LENGTH_SHORT).show();
        this.mHouseId = houseId;
        this.mGarbageType = mGarbageType;

        /*if (!mGarbageType.equals("-1")) {*/
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        CommercialNextDialog commercialNextDialog = new CommercialNextDialog();
        transaction.replace(R.id.commercialDialog_container, commercialNextDialog);
        transaction.addToBackStack("commercialNextDialog");
        transaction.commit();
        /*}*/ /*else {
            AUtils.warning(mContext, getResources().getString(R.string.pls_slct_garbageType));
        }*/


    }

    @Override
    public void onSubmit(String segregationLevel) {
        if (!(segregationLevel == null)) {
            mListener.onSubmitButtonClicked(mHouseId, mGarbageType, segregationLevel, mTOR);
            this.dismiss();
/*            this.dismiss();
            Intent i = new Intent(mContext, SLWM_WeightActivity.class);

            i.putExtra("houseId", mHouseId);
            i.putExtra("garbageType", mGarbageType);
            i.putExtra("segregationLvl", segregationLevel);
            i.putExtra("toR", mTOR);
            mContext.startActivity(i);*/
        } else {
            AUtils.warning(mContext, getResources().getString(R.string.pls_slct_segregationLvl));
        }

    }

    @Override
    public void slwmOnNextBtnPressed(String houseId, String GarbageType, String TOR) {
        this.mHouseId = houseId;
        this.mGarbageType = GarbageType;
        this.mTOR = TOR;
        Log.e(TAG, "slwmOnNextBtnPressed: houseId:- " + mHouseId + ", GarbageType:- " + mGarbageType + ", Tor:- " + mTOR);

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        CommercialNextDialog commercialNextDialog = new CommercialNextDialog();
        transaction.replace(R.id.commercialDialog_container, commercialNextDialog);
        transaction.addToBackStack("commercialNextDialog");
        transaction.commit();
    }

    public interface CustomDialogInterface {

        void onSubmitButtonClicked(String houseId, String garbageType, String segregationLevel, String tor);
    }


}
