package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WorkHistoryDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.List;

public class HistoryDetailsAdapterClass {

    private List<WorkHistoryDetailPojo> workHistoryDetailPojoList;

    private static final Gson gson = new Gson();

    private HistoryDetailsListener mListener;

    public HistoryDetailsListener getHistoryDetailsListener() {
        return mListener;
    }

    public void setHistoryDetailsListener(HistoryDetailsListener mListener) {
        this.mListener = mListener;
    }

    public List<WorkHistoryDetailPojo> getWorkHistoryDetailTypePojoList() {

        Type type = new TypeToken<List<WorkHistoryDetailPojo>>() {
        }.getType();

        workHistoryDetailPojoList = gson.fromJson(
                Prefs.getString(AUtils.PREFS.WORK_HISTORY_DETAIL_POJO_LIST, null), type);
        return workHistoryDetailPojoList;
    }

    public static void setWorkHistoryDetailTypePojoList(List<WorkHistoryDetailPojo> workHistoryDetailPojoList) {
        Type type = new TypeToken<List<WorkHistoryDetailPojo>>() {
        }.getType();
        Prefs.putString(AUtils.PREFS.WORK_HISTORY_DETAIL_POJO_LIST, gson.toJson(workHistoryDetailPojoList, type));
    }

    public void fetchHistoryDetails(final String historyDate) {
        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                Boolean isSuccess = syncServer.pullWorkHistoryDetailListFromServer(historyDate);
            }

            @Override
            public void onFinished() {

                if(!AUtils.isNull(getWorkHistoryDetailTypePojoList())){
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

    public interface HistoryDetailsListener {
        void onSuccessCallBack();

        void onFailureCallBack();
    }
}

