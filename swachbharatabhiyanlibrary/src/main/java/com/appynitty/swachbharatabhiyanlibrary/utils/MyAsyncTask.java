package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.riaylibrary.custom_component.MyProgressDialog;

public class MyAsyncTask extends AsyncTask {

    public SyncServer syncServer;
    private final Context context;
    private boolean isNetworkAvail = false;
    private final boolean isShowPrgressDialog;
    private MyProgressDialog myProgressDialog;
    private final AsynTaskListener asynTaskListener;


    public MyAsyncTask(Context context, boolean isShowPrgressDialog, AsynTaskListener asynTaskListener) {

        this.asynTaskListener = asynTaskListener;
        this.context = context;
        this.syncServer = new SyncServer(context);
        this.isShowPrgressDialog = isShowPrgressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myProgressDialog = new MyProgressDialog(context, R.drawable.progress_bar, false);
        if (!AUtils.isNull(myProgressDialog) && isShowPrgressDialog) {
            myProgressDialog.show();
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (AUtils.isInternetAvailable()) {
            try {

                isNetworkAvail = true;
                asynTaskListener.doInBackgroundOpration(syncServer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (!AUtils.isNull(myProgressDialog) && myProgressDialog.isShowing()) {
            myProgressDialog.dismiss();
        }
        if (isNetworkAvail) {

            asynTaskListener.onFinished();
        } else {

            if (!AUtils.isNull(isShowPrgressDialog) && isShowPrgressDialog) {
                AUtils.warning(context, context.getString(R.string.no_internet_error));
                asynTaskListener.onInternetLost();
            }
        }
    }

    public interface AsynTaskListener {

        void doInBackgroundOpration(SyncServer syncServer);

        void onFinished();
        void onInternetLost();
    }
}