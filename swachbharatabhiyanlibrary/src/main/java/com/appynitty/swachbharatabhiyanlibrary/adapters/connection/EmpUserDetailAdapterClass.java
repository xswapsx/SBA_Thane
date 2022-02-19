package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.EmpSyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.EmpMyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;

public class EmpUserDetailAdapterClass {

    private UserDetailListener mListener;

    private UserDetailPojo userDetailPojo;

    private static final Gson gson = new Gson();

    public UserDetailListener getUserDetailListener() {
        return mListener;
    }

    public void setUserDetailListener(UserDetailListener mListener) {
        this.mListener = mListener;
    }

    public UserDetailPojo getUserDetailPojo() {
        Type type = new TypeToken<UserDetailPojo>() {
        }.getType();

        userDetailPojo = new Gson().fromJson(
                Prefs.getString(AUtils.PREFS.USER_DETAIL_POJO, null), type);

        return userDetailPojo;
    }

    public static void setUserDetailPojo(UserDetailPojo userDetailPojo) {
        Type type = new TypeToken<UserDetailPojo>() {
        }.getType();
        Prefs.putString(AUtils.PREFS.USER_DETAIL_POJO, gson.toJson(userDetailPojo, type));
    }

    public void getUserDetail() {

        new EmpMyAsyncTask(AUtils.currentContextConstant, false, new EmpMyAsyncTask.AsynTaskListener() {
            public boolean isDataPull = false;

            @Override
            public void doInBackgroundOpration(EmpSyncServer empSyncServer) {

                isDataPull = empSyncServer.pullUserDetailsFromServer();
            }

            @Override
            public void onFinished() {

                getUserDetailPojo();


                if (!AUtils.isNull(userDetailPojo)) {
                    if(!AUtils.isNull(mListener))
                        mListener.onSuccessCallBack();

                }
                else {
                    if(!AUtils.isNull(mListener))
                        mListener.onFailureCallBack();
                }

            }

            @Override
            public void onInternetLost() {

            }
        }).execute();
    }

    public interface UserDetailListener {
        void onSuccessCallBack();
        void onFailureCallBack();
    }
}
