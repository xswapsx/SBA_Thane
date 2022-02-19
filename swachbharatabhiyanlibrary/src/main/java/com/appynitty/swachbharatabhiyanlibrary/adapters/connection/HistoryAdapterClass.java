package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.util.List;

public class HistoryAdapterClass {

    private List<TableDataCountPojo.WorkHistory> workHistoryPojoList;



    private static final Gson gson = new Gson();

    private HistoryListener mListener;

    public HistoryListener getHistoryListener() {
        return mListener;
    }

    public void setHistoryListener(HistoryListener mListener) {
        this.mListener = mListener;
    }

    public List<TableDataCountPojo.WorkHistory> getworkHistoryTypePojoList() {

        Type type = new TypeToken<List<TableDataCountPojo.WorkHistory>>() {
        }.getType();

        workHistoryPojoList = gson.fromJson(Prefs.getString(AUtils.PREFS.WORK_HISTORY_POJO_LIST, null), type);
        return workHistoryPojoList;
    }

    public static void setworkHistoryTypePojoList(List<TableDataCountPojo> workHistoryDetailPojoList) {
        Type type = new TypeToken<List<TableDataCountPojo>>() {
        }.getType();
        Prefs.putString(AUtils.PREFS.WORK_HISTORY_POJO_LIST, gson.toJson(workHistoryDetailPojoList, type));
    }

    public void fetchHistory(final String year, final String month) {
        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {
                Boolean isSuccess = syncServer.pullWorkHistoryListFromServer(year, month);
            }

            @Override
            public void onFinished() {

                if(!AUtils.isNull(getworkHistoryTypePojoList()) && !getworkHistoryTypePojoList().isEmpty()){
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

    public interface HistoryListener {
        void onSuccessCallBack();

        void onFailureCallBack();
    }
}
