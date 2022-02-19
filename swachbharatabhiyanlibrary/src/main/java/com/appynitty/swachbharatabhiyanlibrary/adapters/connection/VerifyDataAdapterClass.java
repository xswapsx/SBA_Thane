package com.appynitty.swachbharatabhiyanlibrary.adapters.connection;

import android.content.Context;

import androidx.appcompat.app.AlertDialog;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineAttendanceRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

/**
 * Created by Ayan Dey on 11/10/19.
 */
public class VerifyDataAdapterClass {

    private final Context mContext;
    private Class<?> mClass;
    private boolean mKillActivity;
    private String verifyType;
    private VerifyAdapterListener verifyAdapterListener;
    private final SyncOfflineRepository syncOfflineRepository;
    private final SyncOfflineAdapterClass syncOfflineAdapterClass;
    private final SyncOfflineAttendanceRepository syncOfflineAttendanceRepository;
    private final OfflineAttendanceAdapterClass offlineAttendanceAdapterClass;

    //    private SyncWasteManagementRepository syncWasteManagementRepository;
    private final SyncOfflineWasteManagementAdapterClass wasteManagementAdapterClass;
    private AlertDialog alertDialog;

    public VerifyDataAdapterClass(Context context) {
        this.mContext = context;
        syncOfflineRepository = new SyncOfflineRepository(this.mContext);
        syncOfflineAdapterClass = new SyncOfflineAdapterClass(this.mContext);

        offlineAttendanceAdapterClass = new OfflineAttendanceAdapterClass(this.mContext);
        syncOfflineAttendanceRepository = new SyncOfflineAttendanceRepository(this.mContext);

//        syncWasteManagementRepository = new SyncOfflineAttendanceRepository(mContext);
        wasteManagementAdapterClass = new SyncOfflineWasteManagementAdapterClass(mContext);

        registerListener();
    }

    public void setVerifyAdapterListener(VerifyAdapterListener verifyAdapterListener) {
        this.verifyAdapterListener = verifyAdapterListener;
    }

    public void setRequiredParameters(Class<?> nextClass, boolean killPrevActivity) {
        this.mClass = nextClass;
        this.mKillActivity = killPrevActivity;
        alertDialog = AUtils.getUploadingAlertDialog(this.mContext);
    }

    private void registerListener() {

        syncOfflineAdapterClass.setSyncOfflineListener(new SyncOfflineAdapterClass.SyncOfflineListener() {
            @Override
            public void onSuccessCallback() {
                if (mClass != null) {
                    if (alertDialog.isShowing()) alertDialog.hide();
                    verifyAdapterListener.onDataVerification(mContext, mClass, mKillActivity);
                }
            }

            @Override
            public void onFailureCallback() {
                if (mClass != null) {
                    if (alertDialog.isShowing()) alertDialog.hide();
                    AUtils.warning(mContext, mContext.getResources().getString(R.string.try_after_sometime));
                }
            }

            @Override
            public void onErrorCallback() {
                if (mClass != null) {
                    if (alertDialog.isShowing()) alertDialog.hide();
                    AUtils.warning(mContext, mContext.getResources().getString(R.string.serverError));
                }
            }
        });

        offlineAttendanceAdapterClass.setSyncOfflineListener(new OfflineAttendanceAdapterClass.OfflineAttendanceListener() {
            @Override
            public void onSuccessCallback() {
                attendanceSyncCompleted();
            }

            @Override
            public void onFailureCallback() {

            }

            @Override
            public void onErrorCallback() {

            }
        });

        wasteManagementAdapterClass.setSyncOfflineListener(new SyncOfflineWasteManagementAdapterClass.SyncOfflineListener() {
            @Override
            public void onSuccessCallback() {
                if (mClass != null) {
                    if (alertDialog.isShowing()) alertDialog.hide();
                    verifyAdapterListener.onDataVerification(mContext, mClass, mKillActivity);
                }
            }

            @Override
            public void onFailureCallback() {
                if (mClass != null) {
                    if (alertDialog.isShowing()) alertDialog.hide();
                    AUtils.warning(mContext, mContext.getResources().getString(R.string.try_after_sometime));
                }
            }

            @Override
            public void onErrorCallback() {
                if (mClass != null) {
                    if (alertDialog.isShowing()) alertDialog.hide();
                    AUtils.warning(mContext, mContext.getResources().getString(R.string.serverError));
                }
            }
        });
    }

    public void verifyData() {
        TableDataCountPojo.LocationCollectionCount count = syncOfflineRepository.getLocationCollectionCount(AUtils.getLocalDate());

        if (count.getLocationCount() > 0 || count.getCollectionCount() > 0) {
            if (!alertDialog.isShowing()) alertDialog.show();
            syncOfflineAdapterClass.SyncOfflineData();
        } else
            verifyAdapterListener.onDataVerification(mContext, mClass, mKillActivity);

//        if (count.getCollectionCount() > 0) {
//            AUtils.warning(mContext, mContext.getString(R.string.hint_first_sync));
//            mContext.startActivity(new Intent(mContext, SyncOfflineActivity.class));
//        } else if (count.getCollectionCount() == 0 && count.getLocationCount() > 0) {
//            if (!alertDialog.isShowing()) alertDialog.show();
//            syncOfflineAdapterClass.SyncOfflineData();
//        } else
//            verifyAdapterListener.onDataVerification(mContext, mClass, mKillActivity);

    }

    public void verifyOfflineSync() {
        verifyType = AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI;
        if (AUtils.isInternetAvailable() && syncOfflineAttendanceRepository.checkAnyInAttendanceSyncAvailable()) {
            offlineAttendanceAdapterClass.SyncOfflineData();
        }

    }

    public void verifyWasteManagementSync() {
        verifyType = AUtils.USER_TYPE.USER_TYPE_WASTE_MANAGER;
        if (AUtils.isInternetAvailable() && syncOfflineAttendanceRepository.checkAnyInAttendanceSyncAvailable()) {
            offlineAttendanceAdapterClass.SyncOfflineData();
        }
    }

    private void attendanceSyncCompleted() {
        switch (verifyType) {
            case AUtils.USER_TYPE.USER_TYPE_WASTE_MANAGER:
                wasteManagementAdapterClass.syncOfflineWasteManagementData();
            case AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI:
            default:
                syncOfflineAdapterClass.SyncOfflineData();
        }
    }

    public interface VerifyAdapterListener {
        void onDataVerification(Context context, Class<?> providedClass, boolean killActivity);
    }
}
