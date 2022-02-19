package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import com.appynitty.swachbharatabhiyanlibrary.connection.SyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CheckAttendancePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyAsyncTask;

import java.util.List;

public class CheckAttendanceAdapterClass {

    private CheckAttendancePojo attendancePojo;

    private CheckAttendanceAdapterClass.CheckAttendanceListener mListener;

    public CheckAttendanceAdapterClass.CheckAttendanceListener getCheckAttendanceListener() {
        return mListener;
    }

    public void setCheckAttendanceListener(CheckAttendanceAdapterClass.CheckAttendanceListener mListener) {
        this.mListener = mListener;
    }

    public CheckAttendancePojo getCheckAttendancePojo() {
        return attendancePojo;
    }

    private void setCheckAttendancePojo(CheckAttendancePojo attendancePojo) {
        this.attendancePojo = attendancePojo;
    }

    public void checkAttendance() {
        new MyAsyncTask(AUtils.currentContextConstant, false, new MyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(SyncServer syncServer) {

                setCheckAttendancePojo(syncServer.checkAttendance());
            }

            @Override
            public void onFinished() {
                if(!AUtils.isNull(getCheckAttendancePojo())){
                    if(attendancePojo.getStatus().equals(AUtils.STATUS_SUCCESS) )
                    {
                        mListener.onSuccessCallBack(attendancePojo.isAttendenceOff(),
                                attendancePojo.getMessage(), attendancePojo.getMessageMar());
                    }
                    else {
                        mListener.onFailureCallBack();
                    }
                }else{
                    mListener.onNetworkFailureCallBack();
                }
            }

            @Override
            public void onInternetLost() {

            }
        }).execute();
    }

    public interface CheckAttendanceListener {
        void onSuccessCallBack(boolean isAttendanceOff, String message, String messageMar);
        void onFailureCallBack();
        void onNetworkFailureCallBack();
    }
}