package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginDetailsPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

public class LoginAdapterClass {

    public LoginDetailsPojo loginDetailsPojo = null;

    private LoginListener mListener;

    public LoginListener getLoginListener() {
        return mListener;
    }

    public void setLoginListener(LoginListener mListener) {
        this.mListener = mListener;
    }

    public LoginDetailsPojo getLoginDetailsPojo() {
        return loginDetailsPojo;
    }

    private void setLoginDetailsPojo(LoginDetailsPojo loginDetailsPojo) {
        this.loginDetailsPojo = loginDetailsPojo;
    }

    public void onLogin(final LoginPojo loginPojo) {
        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                setLoginDetailsPojo(syncServer.saveLoginDetails(loginPojo));
            }

            @Override
            public void onFinished() {

                if (!AUtils.isNull(getLoginDetailsPojo())) {

                    if (getLoginDetailsPojo().getStatus().equals(AUtils.STATUS_SUCCESS)) {
                        mListener.onSuccessCallBack();
                    } else {
                        mListener.onSuccessFailureCallBack();
                    }
                } else {
                    mListener.onFailureCallBack();
                }

            }
            @Override
            public void onInternetLost() {

            }
        }).execute();
    }

    public interface LoginListener {
        void onSuccessCallBack();
        void onSuccessFailureCallBack();
        void onFailureCallBack();
    }
}
