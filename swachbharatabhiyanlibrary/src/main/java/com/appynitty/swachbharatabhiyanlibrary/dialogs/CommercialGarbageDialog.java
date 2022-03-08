package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.content.Context;
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
import com.appynitty.swachbharatabhiyanlibrary.db.AppDatabase;
import com.appynitty.swachbharatabhiyanlibrary.fragment.CommercialFirstDialog;
import com.appynitty.swachbharatabhiyanlibrary.fragment.CommercialNextDialog;
import com.appynitty.swachbharatabhiyanlibrary.fragment.SegreMultiSelectDialog;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

/**
 * Created by Swapnil on 25/01/22.
 */


public class CommercialGarbageDialog extends DialogFragment implements CommercialFirstDialog.FirstDialog, CommercialFirstDialog.FirstSLWMDialog,
        CommercialNextDialog.SecondDialog, SegreMultiSelectDialog.SegreMultiDialog {
    private static final String TAG = "CommercialGarbageDialog";
    Context mContext;
    String mHouseId, mGarbageType, cType, innerCType, sComment, Wet, Dry, Domestic, Sanitary;
    String mTOR = "nuffin";

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
    public void onNextBtnPressed(String houseId, String mGarbageType, String sComment) {
        this.mHouseId = houseId;
        this.mGarbageType = mGarbageType;
        this.sComment = sComment;

        if (mGarbageType.equals("1")) {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            SegreMultiSelectDialog multiSelectDialog = new SegreMultiSelectDialog(mContext, mHouseId, mGarbageType);
            transaction.replace(R.id.commercialDialog_container, multiSelectDialog);
            transaction.addToBackStack("segreMultiselectDialog");
            transaction.commit();
        } else {
            FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
            CommercialNextDialog commercialNextDialog = new CommercialNextDialog();
            transaction.replace(R.id.commercialDialog_container, commercialNextDialog);
            transaction.addToBackStack("commercialNextDialog");
            transaction.commit();
        }
    }

    @Override
    public void onSubmit(String segregationLevel) {
        if (!(segregationLevel == null)) {
//            mListener.onSubmitButtonClicked(mHouseId, mGarbageType, segregationLevel, mTOR, sComment);
            mListener.onSubmitButtonSegregated(mHouseId, mGarbageType, segregationLevel, mTOR, sComment, Wet, Dry, Domestic, Sanitary);
            this.dismiss();
        } /*else if (Wet.matches("1") || Dry.matches("1") || Domestic.matches("1") || Sanitary.matches("1")) {
            mListener.onSubmitButtonSegregated(mHouseId, mGarbageType, segregationLevel, mTOR, sComment, Wet, Dry, Domestic, Sanitary);
        }*/ else {
            AUtils.warning(mContext, getResources().getString(R.string.pls_slct_segregationLvl));
        }

    }

    @Override
    public void slwmOnNextBtnPressed(String houseId, String GarbageType, String TOR, String sComment) {
        this.mHouseId = houseId;
        this.mGarbageType = GarbageType;
        this.mTOR = TOR;
        this.sComment = sComment;
        Log.e(TAG, "slwmOnNextBtnPressed: houseId:- " + mHouseId + ", GarbageType:- " + mGarbageType + ", Tor:- " + mTOR + ", comment:- " + sComment);

        /*FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        CommercialNextDialog commercialNextDialog = new CommercialNextDialog();
        transaction.replace(R.id.commercialDialog_container, commercialNextDialog);
        transaction.addToBackStack("commercialNextDialog");
        transaction.commit();*/
        mListener.onSubmitButtonClicked(mHouseId, mGarbageType, "", mTOR, sComment);
        this.dismiss();
    }

    @Override
    public void onSegreNextPressed(String Wet, String Dry, String Domestic, String Sanitary) {
        this.Wet = Wet;
        this.Dry = Dry;
        this.Domestic = Domestic;
        this.Sanitary = Sanitary;

        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        CommercialNextDialog commercialNextDialog = new CommercialNextDialog();
        transaction.replace(R.id.commercialDialog_container, commercialNextDialog);
        transaction.addToBackStack("commercialNextDialog");
        transaction.commit();

    }

    public interface CustomDialogInterface {

        void onSubmitButtonClicked(String houseId, String garbageType, String segregationLevel, String tor, String comment);

        void onSubmitButtonSegregated(String houseId, String garbageType, String segregationLevel, String tor,
                                      String comment, String Wet, String Dry, String Domestic, String Sanitary);
    }


}
