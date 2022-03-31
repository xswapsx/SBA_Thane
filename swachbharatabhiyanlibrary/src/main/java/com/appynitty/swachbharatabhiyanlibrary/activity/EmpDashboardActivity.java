package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.DashboardMenuAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpAttendanceAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpCheckAttendanceAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpSyncServerAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpUserDetailAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.ShareLocationAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.EmpPopUpDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.IdCardDialog;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpInPunchPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LanguagePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.MenuListPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.services.LocationService;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyApplication;
import com.bumptech.glide.Glide;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.GlideCircleTransformation;
import com.riaylibrary.utils.LocaleHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;


public class EmpDashboardActivity extends AppCompatActivity implements EmpPopUpDialog.PopUpDialogListener {

    private final static String TAG = "EmpDashboardActivity";

    private Context mContext;
    private FabSpeedDial fab;
    private RecyclerView menuGridView;
    private Toolbar toolbar;
    private TextView attendanceStatus;
    private TextView vehicleStatus;
    private Switch markAttendance;
    private TextView userName;
    private TextView empId;
    private ImageView profilePic;
    CardView pb;
    private EmpInPunchPojo empInPunchPojo = null;

    private UserDetailPojo userDetailPojo;

    private boolean isLocationPermission = false;
    private boolean isSwitchOn = false;

    private boolean isFromLogin;

    private EmpCheckAttendanceAdapterClass mCheckAttendanceAdapter;

    private EmpAttendanceAdapterClass mAttendanceAdapter;

    private EmpUserDetailAdapterClass mUserDetailAdapter;

    private boolean isFromAttendanceChecked = false;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponents();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_id_card == item.getItemId()) {
            if (!AUtils.isNull(userDetailPojo)) {
//                if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals("2")) {
//                    IdCardDialog cardDialog = new IdCardDialog(mContext, userDetailPojo.getNameMar(), userDetailPojo.getUserId(), userDetailPojo.getProfileImage());
//                    cardDialog.show();
//                } else {
                IdCardDialog cardDialog = new IdCardDialog(mContext, userDetailPojo.getName(), userDetailPojo.getUserId(), userDetailPojo.getProfileImage(), userDetailPojo.getType());
                cardDialog.show();
//                }
            } else {
                AUtils.warning(mContext, mContext.getResources().getString(R.string.try_after_sometime));
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();

        AUtils.currentContextConstant = mContext;
        checkIsFromLogin();

        initUserDetails();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_LOCATION) {
            //check if all permissions are granted
            boolean allgranted = false;
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                isLocationPermission = allgranted;
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(EmpDashboardActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AUtils.showPermissionDialog(mContext, "Location Service", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION}, AUtils.MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }
                });
            } else {
                if (isSwitchOn) {
                    markAttendance.setChecked(false);
                }
            }

        }
    }

    @Override
    public void onPopUpDismissed(String type, Object listItemSelected, @Nullable String vehicleNo) {

        if (!AUtils.isNull(listItemSelected)) {
            if (AUtils.DIALOG_TYPE_LANGUAGE.equals(type)) {
                onLanguageTypeDialogClose(listItemSelected);
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (AUtils.isInternetAvailable()) {
            AUtils.hideSnackBar();
        } else {
            AUtils.showSnackBar(findViewById(R.id.parent));

        }

        if (!AUtils.isNull(mCheckAttendanceAdapter) && !isFromLogin) {
            mCheckAttendanceAdapter.checkAttendance();
        }

        EmpSyncServerAdapterClass empSyncServer = new EmpSyncServerAdapterClass();
        empSyncServer.syncServer();

        empSyncServer.setSyncOfflineListener(new EmpSyncServerAdapterClass.EmpSyncOfflineListener() {
            @Override
            public void onSuccessCallback() {

            }

            @Override
            public void onFailureCallback() {

            }

            @Override
            public void onErrorCallback() {

            }
        });

        ShareLocationAdapterClass shareLocationAdapterClass = new ShareLocationAdapterClass();
        shareLocationAdapterClass.shareLocation();

        checkDutyStatus();
    }

    private void initComponents() {

        getPermission();

        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {

        setContentView(R.layout.emp_activity_dashboard);

        mContext = EmpDashboardActivity.this;
        AUtils.currentContextConstant = mContext;
        checkIsFromLogin();

        mCheckAttendanceAdapter = new EmpCheckAttendanceAdapterClass();
        mAttendanceAdapter = new EmpAttendanceAdapterClass();
        mUserDetailAdapter = new EmpUserDetailAdapterClass();
        pb = findViewById(R.id.empProgress_layout);
        fab = findViewById(R.id.fab_setting);
        menuGridView = findViewById(R.id.menu_grid);
        menuGridView.setLayoutManager(new GridLayoutManager(mContext, 2));

        toolbar = findViewById(R.id.toolbar);
        attendanceStatus = findViewById(R.id.user_attendance_status);
        vehicleStatus = findViewById(R.id.user_vehicle_type);
        markAttendance = findViewById(R.id.user_attendance_toggle);
        userName = findViewById(R.id.user_full_name);
        empId = findViewById(R.id.user_emp_id);
        profilePic = findViewById(R.id.user_profile_pic);

        initToolBar();
    }

    private void initToolBar() {
        toolbar.setNavigationIcon(R.drawable.ic_app_id_icon_white);
        toolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    private void registerEvents() {

        mCheckAttendanceAdapter.setCheckAttendanceListener(new EmpCheckAttendanceAdapterClass.CheckAttendanceListener() {
            @Override
            public void onSuccessCallBack(boolean isAttendanceOff, String message, String messageMar) {


                if (isAttendanceOff) {
                    isFromAttendanceChecked = true;
                    onOutPunchSuccess();
                    if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageIDConstants.MARATHI)) {
                        AUtils.info(mContext, messageMar, Toast.LENGTH_LONG);
                    } else {
                        AUtils.info(mContext, message, Toast.LENGTH_LONG);
                    }
                }
            }

            @Override
            public void onFailureCallBack() {
                if (!Prefs.getBoolean(AUtils.PREFS.IS_ON_DUTY, false)) {
                    onInPunchSuccess();
                }
            }

            @Override
            public void onNetworkFailureCallBack() {
                AUtils.error(mContext, getResources().getString(R.string.serverError), Toast.LENGTH_LONG);
            }
        });

        mAttendanceAdapter.setAttendanceListener(new EmpAttendanceAdapterClass.AttendanceListener() {
            @Override
            public void onSuccessCallBack(int type) {

                if (type == 1) {
                    onInPunchSuccess();
                } else if (type == 2) {

                    onOutPunchSuccess();
                }
            }

            @Override
            public void onFailureCallBack(int type) {

                if (type == 1) {
                    markAttendance.setChecked(false);
                    AUtils.setIsOnduty(false);
                } else if (type == 2) {
                    markAttendance.setChecked(true);
                    AUtils.setIsOnduty(true);
                }
            }
        });

        fab.addOnMenuItemClickListener(new FabSpeedDial.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(FloatingActionButton miniFab, @Nullable TextView label, int itemId) {
                if (itemId == R.id.action_change_language) {
                    changeLanguage();
                }
//                else if (itemId == R.id.action_setting) {
//                    startActivity(new Intent(mContext, SettingsActivity.class));
//                }
                else if (itemId == R.id.action_rate_app) {
                    AUtils.rateApp(mContext);
                } else if (itemId == R.id.action_share_app) {
                    AUtils.shareThisApp(mContext, null);
                } else if (itemId == R.id.action_logout) {
                    performLogout();
                }
            }
        });

        markAttendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                onSwitchStatus(isChecked);
            }
        });

        mUserDetailAdapter.setUserDetailListener(new EmpUserDetailAdapterClass.UserDetailListener() {
            @Override
            public void onSuccessCallBack() {
                initUserDetails();
            }

            @Override
            public void onFailureCallBack() {

            }
        });

    }

    private void initData() {

        initUserDetails();

        mUserDetailAdapter.getUserDetail();

        List<MenuListPojo> menuPojoList = new ArrayList<MenuListPojo>();

        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_qrcode_scanner), R.drawable.ic_qr_code, EmpQRcodeScannerActivity.class, true));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_history_page), R.drawable.ic_history, EmpHistoryPageActivity.class, false));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_sync_offline), R.drawable.ic_sync, EmpSyncOfflineActivity.class, false));

        DashboardMenuAdapter mainMenuAdaptor = new DashboardMenuAdapter(EmpDashboardActivity.this);
        mainMenuAdaptor.setMenuList(menuPojoList);
        menuGridView.setAdapter(mainMenuAdaptor);

        Type type = new TypeToken<EmpInPunchPojo>() {
        }.getType();
        empInPunchPojo = new Gson().fromJson(Prefs.getString(AUtils.PREFS.IN_PUNCH_POJO, null), type);

        checkDutyStatus();
    }

    private void performLogout() {
        AUtils.showConfirmationDialog(mContext, AUtils.CONFIRM_LOGOUT_DIALOG, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();

                if (!AUtils.isIsOnduty()) {
                    Prefs.remove(AUtils.PREFS.IS_USER_LOGIN);
                    Prefs.remove(AUtils.PREFS.USER_ID);
                    Prefs.remove(AUtils.PREFS.USER_TYPE);
                    Prefs.remove(AUtils.PREFS.USER_TYPE_ID);
                    Prefs.remove(AUtils.PREFS.VEHICLE_TYPE_POJO_LIST);
                    Prefs.remove(AUtils.PREFS.USER_DETAIL_POJO);
                    //Prefs.remove(AUtils.PREFS.IS_ON_DUTY);
                    AUtils.setIsOnduty(false); //= false;
                    Prefs.remove(AUtils.PREFS.IMAGE_POJO);
                    Prefs.remove(AUtils.PREFS.WORK_HISTORY_DETAIL_POJO_LIST);
                    Prefs.remove(AUtils.PREFS.WORK_HISTORY_POJO_LIST);
                    Prefs.remove(AUtils.LAT);
                    Prefs.remove(AUtils.LONG);
                    Prefs.remove(AUtils.VEHICLE_NO);
                    Prefs.remove(AUtils.VEHICLE_ID);

                    openLogin();
                } else {
                    AUtils.info(mContext, getResources().getString(R.string.off_duty_warning));
                }
            }
        }, null);
    }

    private void openLogin() {

        startActivity(new Intent(EmpDashboardActivity.this, LoginActivity.class));
        EmpDashboardActivity.this.finish();
    }

    private void changeLanguage() {

        HashMap<Integer, Object> mLanguage = new HashMap<>();

        List<LanguagePojo> mLanguagePojoList = AUtils.getLanguagePojoList();

        AUtils.changeLanguage(this, Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID));

        if (!AUtils.isNull(mLanguagePojoList) && !mLanguagePojoList.isEmpty()) {
            for (int i = 0; i < mLanguagePojoList.size(); i++) {
                mLanguage.put(i, mLanguagePojoList.get(i));
            }

            EmpPopUpDialog dialog = new EmpPopUpDialog(EmpDashboardActivity.this, AUtils.DIALOG_TYPE_LANGUAGE, mLanguage, this);
            dialog.show();
        }
    }

    public void changeLanguage(String type) {

        AUtils.changeLanguage(this, type);

        recreate();
    }

    private void onSwitchStatus(boolean isChecked) {

        isSwitchOn = isChecked;

        if (isChecked) {
            if (isLocationPermission) {
                if (AUtils.isGPSEnable(AUtils.currentContextConstant)) {

                    if (!AUtils.isIsOnduty()) {
                        ((MyApplication) AUtils.mainApplicationConstant).startLocationTracking();
                        onChangeDutyStatus();
                    }
                } else {
                    markAttendance.setChecked(false);
                    AUtils.gpsStatusCheck(mContext);
                }
            } else {
                isLocationPermission = AUtils.isLocationPermissionGiven(EmpDashboardActivity.this);
            }
        } else {
            if (AUtils.isIsOnduty()) {
                if (AUtils.isInternetAvailable()) {
                    try {
                        if (!isFromAttendanceChecked) {
                            AUtils.showConfirmationDialog(mContext, AUtils.CONFIRM_OFFDUTY_DIALOG, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    mAttendanceAdapter.MarkOutPunch();
                                }
                            }, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.dismiss();
                                    markAttendance.setChecked(true);
                                }
                            });
                        } else {
                            isFromAttendanceChecked = false;
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        markAttendance.setChecked(true);
                    }
                } else {
                    AUtils.warning(mContext, mContext.getString(R.string.no_internet_error));
                    markAttendance.setChecked(true);
                }
            }
        }
    }

    private void onChangeDutyStatus() {

        if (AUtils.isInternetAvailable()) {

            if (AUtils.isNull(empInPunchPojo)) {
                empInPunchPojo = new EmpInPunchPojo();
            }

            empInPunchPojo.setStartDate(AUtils.getServerDate());
            empInPunchPojo.setStartTime(AUtils.getServerTime());

            try {
                mAttendanceAdapter.MarkInPunch(empInPunchPojo);
            } catch (Exception e) {
                e.printStackTrace();
                markAttendance.setChecked(false);
                AUtils.error(mContext, mContext.getString(R.string.something_error), Toast.LENGTH_SHORT);
            }
        } else {
            AUtils.warning(mContext, mContext.getString(R.string.no_internet_error));
            markAttendance.setChecked(false);
        }
    }

    private void onInPunchSuccess() {
        attendanceStatus.setText(this.getResources().getString(R.string.status_on_duty));
        attendanceStatus.setTextColor(this.getResources().getColor(R.color.colorONDutyGreen));

//        String vehicleType = null;
//
//        if (!AUtils.isNullString(empInPunchPojo.getVehicleNumber())) {
//
//            vehicleStatus.setText(String.format("%s%s %s %s%s", this.getResources().getString(R.string.opening_round_bracket), vehicleType,
//                    this.getResources().getString(R.string.hyphen), empInPunchPojo.getVehicleNumber(),
//                    this.getResources().getString(R.string.closing_round_bracket)));
//        } else {
//            vehicleStatus.setText(String.format("%s%s%s", this.getResources().getString(R.string.opening_round_bracket),
//                    vehicleType, this.getResources().getString(R.string.closing_round_bracket)));
//        }

        AUtils.setIsOnduty(true);
    }

    private void onOutPunchSuccess() {
        attendanceStatus.setText(this.getResources().getString(R.string.status_off_duty));
        attendanceStatus.setTextColor(this.getResources().getColor(R.color.colorOFFDutyRed));

        vehicleStatus.setText("");

        boolean isservicerunning = AUtils.isMyServiceRunning(AUtils.mainApplicationConstant, LocationService.class);

//
        if (isservicerunning)
            ((MyApplication) AUtils.mainApplicationConstant).stopLocationTracking();

        markAttendance.setChecked(false);

        AUtils.setIsOnduty(false);
    }

    private void getPermission() {

        isLocationPermission = AUtils.isLocationPermissionGiven(EmpDashboardActivity.this);
    }

    private void initUserDetails() {
        userDetailPojo = mUserDetailAdapter.getUserDetailPojo();

        if (!AUtils.isNull(userDetailPojo)) {

//            if(Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageIDConstants.MARATHI))
//                userName.setText(userDetailPojo.getNameMar());
//            else
            userName.setText(userDetailPojo.getName());

            empId.setText(userDetailPojo.getUserId());
            if (!AUtils.isNullString(userDetailPojo.getProfileImage())) {
                try {
                    Glide.with(mContext).load(userDetailPojo.getProfileImage())
                            .placeholder(R.drawable.ic_user)
                            .error(R.drawable.ic_user)
                            .centerCrop()
                            .bitmapTransform(new GlideCircleTransformation(getApplicationContext()))
                            .into(profilePic);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onLanguageTypeDialogClose(Object listItemSelected) {

        LanguagePojo languagePojo = (LanguagePojo) listItemSelected;
        changeLanguage(AUtils.setLanguage(languagePojo.getLanguage()));
    }

    private void checkDutyStatus() {

        if (AUtils.isIsOnduty()) {

            if (!AUtils.isMyServiceRunning(AUtils.mainApplicationConstant, LocationService.class)) {
                ((MyApplication) AUtils.mainApplicationConstant).startLocationTracking();
            }
            markAttendance.setChecked(true);

            attendanceStatus.setText(this.getResources().getString(R.string.status_on_duty));
            attendanceStatus.setTextColor(this.getResources().getColor(R.color.colorONDutyGreen));

//            String vehicleName = "";
//
//            if (!AUtils.isNullString(Prefs.getString(AUtils.VEHICLE_NO,""))) {
//
//                vehicleStatus.setText(String.format("%s%s %s %s%s", this.getResources().getString(R.string.opening_round_bracket), vehicleName, this.getResources().getString(R.string.hyphen), empInPunchPojo.getVehicleNumber(), this.getResources().getString(R.string.closing_round_bracket)));
//            } else {
//                vehicleStatus.setText(String.format("%s%s%s", this.getResources().getString(R.string.opening_round_bracket), vehicleName, this.getResources().getString(R.string.closing_round_bracket)));
//            }
        }
    }

    private void checkIsFromLogin() {

        if (getIntent().hasExtra(AUtils.isFromLogin)) {
            isFromLogin = getIntent().getBooleanExtra(AUtils.isFromLogin, true);
        } else {
            isFromLogin = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        getIntent().removeExtra(AUtils.isFromLogin);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 101) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // All required changes were successfully made
                    pb.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            pb.setVisibility(View.GONE);
                        }
                    }, 5000);
                    break;
                case Activity.RESULT_CANCELED:
                    // The user was asked to change settings, but chose not to
                    Toast.makeText(EmpDashboardActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }
}
