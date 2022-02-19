package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

import java.util.List;

public class CollectionAreaAdapterClass {

    private List<CollectionAreaPojo> areaPojoList;

    private CollectionAreaListener mListener;

    public CollectionAreaListener getCollectionAreaListener() {
        return mListener;
    }

    public void setCollectionAreaListener(CollectionAreaListener mListener) {
        this.mListener = mListener;
    }

    public List<CollectionAreaPojo> getAreaPojoList() {
        return areaPojoList;
    }

    private void setAreaPojoList(List<CollectionAreaPojo> areaPojoList) {
        this.areaPojoList = areaPojoList;
    }

    public void fetchAreaList(final String type, boolean isProgress) {
        new MyAsyncTask(AUtils.currentContextConstant, isProgress, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                setAreaPojoList(syncServer.fetchCollectionArea(type));
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(areaPojoList)){
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

    public interface CollectionAreaListener {
        void onSuccessCallBack();

        void onFailureCallBack();
    }


}
