package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

public class VersionDetailsAdapterClass {

    private VersionDetailsListener mListener;

    public VersionDetailsListener getVersionDetailsListener() {
        return mListener;
    }

    public void setVersionDetailsListener(VersionDetailsListener mListener) {
        this.mListener = mListener;
    }

    public void checkVersionDetails() {

        new MyAsyncTask(AUtils.currentContextConstant, false, new MyAsyncTask.AsynTaskListener() {
            Boolean doUpdate = false;
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                doUpdate = syncServer.checkVersionUpdate();
            }

            @Override
            public void onFinished() {
                if(doUpdate){
                    mListener.onSuccessCallBack();
                }else{
                    mListener.onFailureCallBack();
                }
            }

            @Override
            public void onInternetLost() {

            }
        }).execute();

    }

    public interface VersionDetailsListener {
        void onSuccessCallBack();
        void onFailureCallBack();
    }
}
