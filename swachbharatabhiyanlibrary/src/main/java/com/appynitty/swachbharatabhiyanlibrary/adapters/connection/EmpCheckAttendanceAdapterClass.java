package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;


import com.appynitty.swachbharatabhiyanlibrary.connection.EmpSyncServer;
import com.appynitty.swachbharatabhiyanlibrary.pojos.CheckAttendancePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.EmpMyAsyncTask;

public class EmpCheckAttendanceAdapterClass {

    private CheckAttendancePojo attendancePojo;

    private EmpCheckAttendanceAdapterClass.CheckAttendanceListener mListener;

    public EmpCheckAttendanceAdapterClass.CheckAttendanceListener getCheckAttendanceListener() {
        return mListener;
    }

    public void setCheckAttendanceListener(EmpCheckAttendanceAdapterClass.CheckAttendanceListener mListener) {
        this.mListener = mListener;
    }

    public CheckAttendancePojo getCheckAttendancePojo() {
        return attendancePojo;
    }

    private void setCheckAttendancePojo(CheckAttendancePojo attendancePojo) {
        this.attendancePojo = attendancePojo;
    }

    public void checkAttendance() {
        new EmpMyAsyncTask(AUtils.currentContextConstant, false, new EmpMyAsyncTask.AsynTaskListener() {

            @Override
            public void doInBackgroundOpration(EmpSyncServer empSyncServer) {

                setCheckAttendancePojo(empSyncServer.checkAttendance());
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