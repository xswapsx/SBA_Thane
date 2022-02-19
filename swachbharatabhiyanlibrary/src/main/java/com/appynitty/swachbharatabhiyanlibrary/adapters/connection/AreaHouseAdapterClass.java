package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CollectionAreaHousePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

import java.util.List;

public class AreaHouseAdapterClass {

    private List<CollectionAreaHousePojo> hpPojoList;

    private AreaHouseListener mListener;

    public AreaHouseListener getAreaHouseListener() {
        return mListener;
    }

    public void setAreaHouseListener(AreaHouseListener mListener) {
        this.mListener = mListener;
    }

    public List<CollectionAreaHousePojo> getHpPojoList() {
        return hpPojoList;
    }

    private void setHpPojoList(List<CollectionAreaHousePojo> hpPojoList) {
        this.hpPojoList = hpPojoList;
    }

    public void fetchHpList(final String areaId) {
        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                setHpPojoList(syncServer.fetchCollectionAreaHouse(AUtils.HP_AREA_TYPE_ID, areaId));
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(hpPojoList)){
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

    public interface AreaHouseListener {
        void onSuccessCallBack();
        void onFailureCallBack();
    }
}
