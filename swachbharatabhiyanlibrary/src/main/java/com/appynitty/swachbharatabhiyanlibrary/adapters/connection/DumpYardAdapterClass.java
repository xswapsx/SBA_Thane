package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionDumpYardPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

import java.util.List;

public class DumpYardAdapterClass {

    private List<CollectionDumpYardPointPojo> dyPojoList;

    private AreaDyListener mListener;

    public AreaDyListener getAreaDyListener() {
        return mListener;
    }

    public void setAreaDyListener(AreaDyListener mListener) {
        this.mListener = mListener;
    }

    public List<CollectionDumpYardPointPojo> getDyPojoList() {
        return dyPojoList;
    }

    private void setDyPojoList(List<CollectionDumpYardPointPojo> dyPojoList) {
        this.dyPojoList =dyPojoList;
    }

    public void fetchDyList(final String areaId) {
        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                setDyPojoList(syncServer.fetchCollectionDyPoint(AUtils.DY_AREA_TYPE_ID, areaId));
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(dyPojoList)){
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

    public interface AreaDyListener {
        void onSuccessCallBack();
        void onFailureCallBack();
    }
}
