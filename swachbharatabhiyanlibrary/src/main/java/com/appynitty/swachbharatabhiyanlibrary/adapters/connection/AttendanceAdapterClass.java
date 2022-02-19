package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.widget.Toast;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.activity.DashboardActivity;
import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.InPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.OutPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.CommonUtils;

public class AttendanceAdapterClass {

    private AttendanceListener mListener;

    public AttendanceListener getAttendanceListener() {
        return mListener;
    }

    public void setAttendanceListener(AttendanceListener mListener) {
        this.mListener = mListener;
    }

    public void MarkInPunch(final InPunchPojo inPunchPojo) {

        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {
            ResultPojo resultPojo = null;
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                if(!AUtils.isNull(inPunchPojo)) {
                    resultPojo = syncServer.saveInPunch(inPunchPojo);
                }
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(resultPojo)) {
                    if (resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                        if(!AUtils.isNull(mListener))
                        {
                            mListener.onSuccessCallBack(1);
                        }

                        String message = null;
                        if(Prefs.getString(AUtils.LANGUAGE_NAME,AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
                        {
                            message = resultPojo.getMessageMar();
                        }
                        else
                        {
                            message = resultPojo.getMessage();
                        }
                        AUtils.success(AUtils.currentContextConstant, "" + message, Toast.LENGTH_SHORT);
                    } else {
                        if(!AUtils.isNull(mListener))
                        {
                            mListener.onFailureCallBack(1);
                        }
                        String message = null;
                        if(Prefs.getString(AUtils.LANGUAGE_NAME,AUtils.DEFAULT_LANGUAGE_ID).equals("2"))
                        {
                            message = resultPojo.getMessageMar();
                        }
                        else
                        {
                            message = resultPojo.getMessage();
                        }
                        AUtils.error(AUtils.currentContextConstant, "" + message, Toast.LENGTH_SHORT);
                    }
                } else {
                    if(!AUtils.isNull(mListener))
                    {
                        mListener.onFailureCallBack(1);
                    }
                    AUtils.error(AUtils.currentContextConstant, "" + AUtils.currentContextConstant.getString(R.string.serverError), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onInternetLost() {

            }
        }).execute();
    }

    public void MarkOutPunch() {

        new MyAsyncTask(AUtils.currentContextConstant, true, new MyAsyncTask.AsynTaskListener() {
            ResultPojo resultPojo = null;
            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                OutPunchPojo outPunchPojo = new OutPunchPojo();
                outPunchPojo.setDaDate(AUtils.getServerDate());
                outPunchPojo.setEndTime(AUtils.getServerTime());
                resultPojo = syncServer.saveOutPunch(outPunchPojo);

            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(resultPojo)) {
                    if (resultPojo.getStatus().equals(AUtils.STATUS_SUCCESS)) {
                        if(!AUtils.isNull(mListener))
                        {
                            mListener.onSuccessCallBack(2);
                        }
                        String message = null;
                        if(Prefs.getString(AUtils.LANGUAGE_NAME,AUtils.DEFAULT_LANGUAGE_ID).equals("2"))
                        {
                            message = resultPojo.getMessageMar();
                        }
                        else
                        {
                            message = resultPojo.getMessage();
                        }

                        AUtils.success(AUtils.currentContextConstant, "" + message, Toast.LENGTH_SHORT);
                    } else {
                        if(!AUtils.isNull(mListener))
                        {
                            mListener.onFailureCallBack(2);
                        }
                        String message = null;
                        if(Prefs.getString(AUtils.LANGUAGE_NAME,AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
                        {
                            message = resultPojo.getMessageMar();
                        }
                        else
                        {
                            message = resultPojo.getMessage();
                        }
                        AUtils.error(AUtils.currentContextConstant, "" + message, Toast.LENGTH_SHORT);
                    }
                } else {
                    if(!AUtils.isNull(mListener))
                    {
                        mListener.onFailureCallBack(2);
                    }
                    AUtils.error(AUtils.currentContextConstant, "" + AUtils.currentContextConstant.getString(R.string.serverError), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onInternetLost() {

            }
        }).execute();


    }

    public interface AttendanceListener {
        void onSuccessCallBack(int type);
        void onFailureCallBack(int type);
    }
}
