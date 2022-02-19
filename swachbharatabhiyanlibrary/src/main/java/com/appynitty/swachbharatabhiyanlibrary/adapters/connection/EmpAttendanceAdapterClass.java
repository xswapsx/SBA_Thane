package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.widget.Toast;

import com.appynitty.retrofitconnectionlibrary.pojos.ResultPojo;
import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.connection.EmpSyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpInPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOutPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.EmpMyAsyncTask;
import com.pixplicity.easyprefs.library.Prefs;

public class EmpAttendanceAdapterClass {

    private AttendanceListener mListener;

    public AttendanceListener getAttendanceListener() {
        return mListener;
    }

    public void setAttendanceListener(AttendanceListener mListener) {
        this.mListener = mListener;
    }

    public void MarkInPunch(final EmpInPunchPojo empInPunchPojo) {

        new EmpMyAsyncTask(AUtils.currentContextConstant, true, new EmpMyAsyncTask.AsynTaskListener() {
            ResultPojo resultPojo = null;
            @Override
            public void doInBackgroundOpration(EmpSyncServer empSyncServer) {

                if(!AUtils.isNull(empInPunchPojo)) {
                    try {
                        resultPojo = empSyncServer.saveInPunch(empInPunchPojo);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
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

        new EmpMyAsyncTask(AUtils.currentContextConstant, true, new EmpMyAsyncTask.AsynTaskListener() {
            ResultPojo resultPojo = null;
            @Override
            public void doInBackgroundOpration(EmpSyncServer empSyncServer) {

                EmpOutPunchPojo empOutPunchPojo = new EmpOutPunchPojo();
                empOutPunchPojo.setEndDate(AUtils.getServerDate());
                empOutPunchPojo.setEndTime(AUtils.getServerTime());
                resultPojo = empSyncServer.saveOutPunch(empOutPunchPojo);

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
