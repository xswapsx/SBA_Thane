package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

public class BroadcastMessageAdapterClass {

    private BroadcastMessageListener mListener;

    public BroadcastMessageListener getBroadcastMessageListener() {
        return mListener;
    }

    public void setBroadcastMessageListener(BroadcastMessageListener mListener) {
        this.mListener = mListener;
    }

    public void sendBroadcastMessage(final String areaID){

        new MyAsyncTask(AUtils.currentContextConstant, false, new MyAsyncTask.AsynTaskListener() {
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                syncServer.pullAreaBroadcastFromServer(areaID);
            }

            @Override
            public void onFinished() {

            }

            @Override
            public void onInternetLost() {

            }
        }).execute();
    }

    public interface BroadcastMessageListener {
        void onSuccessCallBack();

        void onFailureCallBack();
    }

}
