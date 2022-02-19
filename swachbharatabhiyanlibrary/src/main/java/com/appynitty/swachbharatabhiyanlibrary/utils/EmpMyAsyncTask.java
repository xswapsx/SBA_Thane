package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.content.Context;
import android.os.AsyncTask;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.connection.EmpSyncServer;
import com.riaylibrary.custom_component.MyProgressDialog;

public class EmpMyAsyncTask extends AsyncTask {

    public EmpSyncServer empSyncServer;
    private final Context context;
    private boolean isNetworkAvail = false;
    private final boolean isShowPrgressDialog;
    private MyProgressDialog myProgressDialog;
    private final AsynTaskListener asynTaskListener;


    public EmpMyAsyncTask(Context context, boolean isShowPrgressDialog, AsynTaskListener asynTaskListener) {

        this.asynTaskListener = asynTaskListener;
        this.context = context;
        this.empSyncServer = new EmpSyncServer(context);
        this.isShowPrgressDialog = isShowPrgressDialog;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        myProgressDialog = new MyProgressDialog(context, R.drawable.progress_bar, false);
        if (isShowPrgressDialog) {
            myProgressDialog.show();
        }
    }

    @Override
    protected Object doInBackground(Object[] objects) {

        if (AUtils.isInternetAvailable()) {
            try {

                isNetworkAvail = true;
                asynTaskListener.doInBackgroundOpration(empSyncServer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (myProgressDialog.isShowing()) {
            myProgressDialog.dismiss();
        }
        if (isNetworkAvail) {

            asynTaskListener.onFinished();
        } else {

            if (isShowPrgressDialog) {
                AUtils.warning(context, context.getString(R.string.no_internet_error));
                asynTaskListener.onInternetLost();
            }
        }
    }

    public interface AsynTaskListener {

        void doInBackgroundOpration(EmpSyncServer empSyncServer);

        void onFinished();
        void onInternetLost();
    }
}