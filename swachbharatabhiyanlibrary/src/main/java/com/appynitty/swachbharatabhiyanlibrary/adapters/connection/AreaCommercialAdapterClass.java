package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaHousePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionDumpYardPointPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

import java.util.List;

public class AreaCommercialAdapterClass {

    private List<CollectionDumpYardPointPojo> cpPojoList;

    private AreaCommercialAdapterClass.AreaCommercialPointListener mListener;

    public AreaCommercialAdapterClass.AreaCommercialPointListener getAreaHouseListener() {
        return mListener;
    }

    public void setAreaCommercialListener(AreaCommercialAdapterClass.AreaCommercialPointListener mListener) {
        this.mListener = mListener;
    }

    public List<CollectionDumpYardPointPojo> getCpPojoList() {
        return cpPojoList;
    }

    private void setCpPojoList(List<CollectionDumpYardPointPojo> cpPojoList) {
        this.cpPojoList = cpPojoList;
    }

    public void fetchCpList(final String areaId) {
        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                setCpPojoList(syncServer.fetchCollectionCommercialPoint(AUtils.APP_ID, areaId));
            }

            @Override
            public void onFinished() {
                if (!AUtils.isNull(cpPojoList)) {
                    mListener.onSuccessCallBack();
                } else {
                    mListener.onFailureCallBack();
                }
            }

            @Override
            public void onInternetLost() {

            }
        }).execute();
    }

    public interface AreaCommercialPointListener {
        void onSuccessCallBack();

        void onFailureCallBack();
    }
}
