package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

import java.util.List;

public class AreaPointAdapterClass {

    private List<CollectionAreaPointPojo> gpPojoList;

    private AreaPointListener mListener;

    public AreaPointListener getAreaPointListener() {
        return mListener;
    }

    public void setAreaPointListener(AreaPointListener mListener) {
        this.mListener = mListener;
    }

    public List<CollectionAreaPointPojo> getGpPojoList() {
        return gpPojoList;
    }

    private void setGpPojoList(List<CollectionAreaPointPojo> gpPojoList) {
        this.gpPojoList = gpPojoList;
    }

    public void fetchGpList(final String areaId) {
        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                setGpPojoList(syncServer.fetchCollectionAreaPoint(AUtils.GP_AREA_TYPE_ID, areaId));
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(gpPojoList)){
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

    public interface AreaPointListener {
        void onSuccessCallBack();
        void onFailureCallBack();
    }
}
