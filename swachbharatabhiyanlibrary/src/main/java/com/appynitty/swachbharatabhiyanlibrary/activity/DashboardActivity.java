package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.camera2.CameraCharacteristics;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.retrofitconnectionlibrary.connection.Connection;
import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.DashboardMenuAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.AttendanceAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.CheckAttendanceAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.OfflineAttendanceAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.SyncOfflineAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.UserDetailAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VehicleTypeAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VerifyDataAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.db.AppDatabase;
import com.appynitty.swachbharatabhiyanlibrary.db.HouseEntity;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.IdCardDialog;
import com.appynitty.swachbharatabhiyanlibrary.dialogs.PopUpDialog;
import com.appynitty.swachbharatabhiyanlibrary.pojos.AttendancePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.HouseCTypePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LanguagePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LoginPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.MenuListPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleTypePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.LastLocationRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineAttendanceRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.services.LocationService;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyApplication;
import com.appynitty.swachbharatabhiyanlibrary.webservices.GetHouseDetailService;
import com.appynitty.swachbharatabhiyanlibrary.webservices.IMEIWebService;
import com.bumptech.glide.Glide;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.GlideCircleTransformation;
import com.riaylibrary.utils.LocaleHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import io.github.kobakei.materialfabspeeddial.FabSpeedDial;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DashboardActivity extends AppCompatActivity implements PopUpDialog.PopUpDialogListener {

    private final static String TAG = "DashboardActivity";

    private static final int REQUEST_CAMERA = 22;
    private static final int SELECT_FILE = 33;
    public boolean isView = false;
    public boolean isSync = true;
    //    private WorkManager workManager;
    MediaPlayer mp = null;
    private Context mContext;
    private FabSpeedDial fab;
    private RecyclerView menuGridView;
    private Toolbar toolbar;
    private TextView attendanceStatus;
    private TextView vehicleStatus;
    private Switch markAttendance;
    private ImageView profilePic;
    private TextView userName;
    private TextView empId, txtEmpId;
    private AttendancePojo attendancePojo = null;
    private List<VehicleTypePojo> vehicleTypePojoList;
    private UserDetailPojo userDetailPojo;
    private boolean isLocationPermission = false;
    private boolean isSwitchOn = false;
    private boolean isFromLogin;
    private LoginPojo loginPojo;
    private CheckAttendanceAdapterClass mCheckAttendanceAdapter;
    private AttendanceAdapterClass mAttendanceAdapter;
    private VehicleTypeAdapterClass mVehicleTypeAdapter;
    private UserDetailAdapterClass mUserDetailAdapter;
    private OfflineAttendanceAdapterClass mOfflineAttendanceAdapter;
    private VerifyDataAdapterClass verifyDataAdapterClass;
    private LastLocationRepository lastLocationRepository;
    private SyncOfflineRepository syncOfflineRepository;
    private SyncOfflineAttendanceRepository syncOfflineAttendanceRepository;
    private boolean isFromAttendanceChecked = false;
    private boolean isDeviceMatch = false;

    AppDatabase db;
    HouseEntity houseEntity;
    ArrayList<HouseCTypePojo> houseList;
    String houseCount = "";
    ProgressDialog progressBar;

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
        statusCheck();
        onSwitchStatus(AUtils.isIsOnduty());
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

                IdCardDialog cardDialog = new IdCardDialog(mContext, userDetailPojo.getName(), userDetailPojo.getUserId(), userDetailPojo.getProfileImage(), userDetailPojo.getType());
                cardDialog.show();
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
            for (int grantResult : grantResults) {
                if (grantResult == PackageManager.PERMISSION_GRANTED) {
                    allgranted = true;
                } else {
                    allgranted = false;
                    break;
                }
            }

            if (allgranted) {
                isLocationPermission = allgranted;

            } else if (ActivityCompat.shouldShowRequestPermissionRationale(DashboardActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AUtils.showPermissionDialog(mContext, "This service is using Location Service", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{
                                            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION},
                                    AUtils.MY_PERMISSIONS_REQUEST_LOCATION);
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
            switch (type) {
                case AUtils.DIALOG_TYPE_VEHICLE: {
                    onVehicleTypeDialogClose(listItemSelected, vehicleNo);
                }
                break;
                case AUtils.DIALOG_TYPE_LANGUAGE: {

                    onLanguageTypeDialogClose(listItemSelected);
                }
                break;
            }
        } else {
            switch (type) {
                case AUtils.DIALOG_TYPE_VEHICLE: {
                    if (AUtils.isIsOnduty()) {

                    } else {
                        markAttendance.setChecked(false);
                        ((MyApplication) AUtils.mainApplicationConstant).stopLocationTracking();
                    }
                }
                break;
                case AUtils.DIALOG_TYPE_LANGUAGE: {

                }
                break;
            }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        try {
            if (AUtils.isInternetAvailable(AUtils.mainApplicationConstant)) {
                AUtils.hideSnackBar();
            } else {
                AUtils.showSnackBar(findViewById(R.id.parent));
            }

            String inDate = AUtils.getInPunchDate();
            String currentDate = AUtils.getServerDate();

            Calendar CurrentTime = AUtils.getCurrentTime();
            Calendar DutyOffTime = AUtils.getDutyEndTime();

            if (inDate.equals(currentDate) && CurrentTime.after(DutyOffTime)) {

                isFromAttendanceChecked = true;

                String dutyEndTime = AUtils.getCurrentDateDutyOffTime();

                syncOfflineAttendanceRepository.performCollectionInsert(mContext,
                        syncOfflineAttendanceRepository.checkAttendance(), dutyEndTime);

                onOutPunchSuccess();
            }

            if (!inDate.equals(currentDate)) {
                isFromAttendanceChecked = true;

                String dutyEndTime = AUtils.getPreviousDateDutyOffTime();

                syncOfflineAttendanceRepository.performCollectionInsert(mContext,
                        syncOfflineAttendanceRepository.checkAttendance(), dutyEndTime);

                onOutPunchSuccess();
            }

            if (!AUtils.isNull(syncOfflineAttendanceRepository) && !isFromLogin) {
                attendancePojo = syncOfflineAttendanceRepository.checkAttendance();

                if (!AUtils.isNull(attendancePojo)) {
                    markAttendance.setChecked(true);
                    onInPunchSuccess();
                }
            }

            if (!AUtils.isSyncOfflineDataRequestEnable) {
                verifyDataAdapterClass.verifyOfflineSync();
            }
            if (isView)
                checkDutyStatus();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {

//                onSelectFromGalleryResult(data);

            } else if (requestCode == REQUEST_CAMERA) {

                onCaptureImageResult(data);
            }
        }

        final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        if (requestCode == 101) {
            switch (resultCode) {
                case Activity.RESULT_OK:
                    // All required changes were successfully made
                    break;
                case Activity.RESULT_CANCELED:
                    // The user was asked to change settings, but chose not to
                    Toast.makeText(DashboardActivity.this, "Canceled", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }
    }

    private void initComponents() {
        isView = true;

        db = AppDatabase.getDbInstance(this.getApplicationContext());

        if (AUtils.isNetworkAvailable(AUtils.mainApplicationConstant)) {
            db.houseDao().deleteAllHouse();
            houseEntity = new HouseEntity();
            houseCount = Prefs.getString(AUtils.PREFS.HOUSE_COUNT, null);
            getHouseDetails(houseCount);
        }

        getPermission();
        generateId();
        registerEvents();
        initData();

    }

    @Override
    protected void onStart() {
        super.onStart();
        isUserLoginValidIMEINumber();
    }

    /**
     * Call this method first of page for found any user login with the diffrent mobile to avoid to conflit in data
     */

    private void isUserLoginValidIMEINumber() {

        syncOfflineData(isSync);

    }

    private void syncOfflineData(final boolean isSync) {
        SyncOfflineAdapterClass offlineAdapterClasss = new SyncOfflineAdapterClass(this);
        offlineAdapterClasss.SyncOfflineData();

        offlineAdapterClasss.setSyncOfflineListener(new SyncOfflineAdapterClass.SyncOfflineListener() {
            @Override
            public void onSuccessCallback() {
                DashboardActivity.this.isSync = true;
            }

            @Override
            public void onFailureCallback() {
                isValidIMEINumber(isSync);
            }

            @Override
            public void onErrorCallback() {
                DashboardActivity.this.isSync = false;
                isValidIMEINumber(isSync);
            }

        });


    }

    private void isValidIMEINumber(boolean isSync) {

        String deviceId = getDeviceId();

        IMEIWebService service = Connection.createService(IMEIWebService.class, AUtils.SERVER_URL);
        service.compareIMEINumber(
                Prefs.getString(AUtils.APP_ID, ""),
                Prefs.getString(AUtils.PREFS.USER_ID, ""),
                isSync,
                deviceId,
                AUtils.getBatteryStatus(),
                AUtils.CONTENT_TYPE
        ).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.code() == 200) {
                    if (response.body() != null) {
                        Log.d(TAG, "onResponse: " + new Gson().toJson(response.body()));
                        responseIMEINumber(response.body());
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.d(AUtils.TAG_HTTP_RESPONSE, "onFailureCallback: Response Code-" + t.getMessage());
            }
        });

    }

    private void responseIMEINumber(ResponseBody response) {
        try {
            JSONObject jsonObject = null;
            jsonObject = new JSONObject(response.string());

            if (jsonObject.has("IsInSync")) {
                if (jsonObject.getBoolean("IsInSync")) {
                    if (jsonObject.has("UserId"))
                        //   if (jsonObject.getString("UserId").equalsIgnoreCase(Prefs.getString(AUtils.APP_ID, ""))) {
                        if (jsonObject.has("imei")) {
                            String imei = jsonObject.getString("imei");

                            if (compareImeiNumber(imei)) {
                                performLogoutDirectly();
                            }
                        }
                } else {
                    Log.d(TAG, "responseIMEINumber: " + jsonObject.getBoolean("IsInSync"));
                }
            }


        } catch (JSONException | IOException e) {
            e.printStackTrace();
            Log.d(TAG, "responseIMEINumber: " + e.getMessage());
        }
    }

    private boolean compareImeiNumber(String imei) {
        String deviceId = getDeviceId();
        return deviceId.equalsIgnoreCase(imei);

    }

    private String getDeviceId() {
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = AUtils.getAndroidId();

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            deviceId = telephonyManager.getDeviceId();
        }
        return deviceId;
    }

    private void performLogoutDirectly() {

        stopServiceIfRunning();
        verifyOfflineData(LoginActivity.class, true);

    }

    private void generateId() {

        setContentView(R.layout.activity_dashboard);
        mContext = DashboardActivity.this;
        AUtils.currentContextConstant = mContext;
        checkIsFromLogin();
        mCheckAttendanceAdapter = new CheckAttendanceAdapterClass();
        mAttendanceAdapter = new AttendanceAdapterClass();
        mVehicleTypeAdapter = new VehicleTypeAdapterClass();
        mUserDetailAdapter = new UserDetailAdapterClass();
        verifyDataAdapterClass = new VerifyDataAdapterClass(mContext);
        mOfflineAttendanceAdapter = new OfflineAttendanceAdapterClass(mContext);

        lastLocationRepository = new LastLocationRepository(mContext);
        syncOfflineRepository = new SyncOfflineRepository(mContext);
        syncOfflineAttendanceRepository = new SyncOfflineAttendanceRepository(mContext);

        if (AUtils.isNull(attendancePojo)) {
            attendancePojo = new AttendancePojo();
        }

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

        mCheckAttendanceAdapter.setCheckAttendanceListener(new CheckAttendanceAdapterClass.CheckAttendanceListener() {
            @Override
            public void onSuccessCallBack(boolean isAttendanceOff, String message, String messageMar) {

                if (isAttendanceOff) {
                    isFromAttendanceChecked = true;
                    onOutPunchSuccess();
                    if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI)) {
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

        mAttendanceAdapter.setAttendanceListener(new AttendanceAdapterClass.AttendanceListener() {
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
                } else if (itemId == R.id.action_setting) {
                    startActivity(new Intent(mContext, SettingsActivity.class));
                } else if (itemId == R.id.action_rate_app) {
                    AUtils.rateApp(mContext);
                } else if (itemId == R.id.action_share_app) {
                    AUtils.shareThisApp(mContext, null);
                } else if (itemId == R.id.action_logout) {
                    if (AUtils.isInternetAvailable(AUtils.mainApplicationConstant)) {
                        performLogout();
                    }

                } else if (itemId == R.id.privacy_policy) {
                    startActivity(new Intent(DashboardActivity.this, PrivacyPage.class));

                }
            }
        });

        markAttendance.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                statusCheck();
                if (AUtils.isInternetAvailable(AUtils.mainApplicationConstant)) {
                    onSwitchStatus(isChecked);
                } else {
                    Toast.makeText(mContext, getResources().getString(R.string.no_internet_error), Toast.LENGTH_SHORT).show();
                    markAttendance.setChecked(AUtils.isIsOnduty());
                }

            }
        });

        mUserDetailAdapter.setUserDetailListener(new UserDetailAdapterClass.UserDetailListener() {
            @Override
            public void onSuccessCallBack() {
                initUserDetails();
            }

            @Override
            public void onFailureCallBack() {

            }
        });

        verifyDataAdapterClass.setVerifyAdapterListener(new VerifyDataAdapterClass.VerifyAdapterListener() {
            @Override
            public void onDataVerification(Context context, Class<?> providedClass, boolean killActivity) {
                if (providedClass.getSimpleName().equals("LoginActivity")) {
                    openLogin(context, providedClass);
                } else {
                    context.startActivity(new Intent(context, providedClass));
                }

                if (killActivity)
                    ((Activity) context).finish();
            }
        });

    }

    private void playsound() {
        mp = MediaPlayer.create(this, R.raw.abc);
        mp.start();

    }

    private void initData() {
        Log.e(TAG, "EmpType- " + Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null));
        lastLocationRepository.clearUnwantedRows();
        initUserDetails();

        mVehicleTypeAdapter.getVehicleType();
        mUserDetailAdapter.getUserDetail();

        List<MenuListPojo> menuPojoList = new ArrayList<MenuListPojo>();
        //added by Rahul
        String empType = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null);
        if (empType.matches("N")){
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_qrcode_scanner), R.drawable.ic_qr_code, QRcodeScannerActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_take_photo), R.drawable.ic_photograph, TakePhotoActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.string_horticulture_waste), R.drawable.ic_hort_waste, HorticultureActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.string_c_and_d_waste), R.drawable.ic_cad_waste, CandDActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.string_ctpt_heading), R.drawable.ic_ctpt_toilet_new, CommunityAndPublicToiletActivity.class, true));
            // menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_broadcast_page), R.drawable.ic_broadcast_icon, BroadcastActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_history_page), R.drawable.ic_history, HistoryPageActivity.class, false));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_profile_page), R.drawable.ic_id_card, ProfilePageActivity.class, false));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_sync_offline), R.drawable.ic_sync, SyncOfflineActivity.class, false));

        }else if (empType.matches("L")){
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_qrcode_scanner), R.drawable.ic_qr_code, QRcodeScannerActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_take_photo), R.drawable.ic_photograph, TakePhotoActivity.class, true));
            // menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_broadcast_page), R.drawable.ic_broadcast_icon, BroadcastActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_history_page), R.drawable.ic_history, HistoryPageActivity.class, false));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_profile_page), R.drawable.ic_id_card, ProfilePageActivity.class, false));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_sync_offline), R.drawable.ic_sync, SyncOfflineActivity.class, false));
        }else if (empType.matches("S")){
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_qrcode_scanner), R.drawable.ic_qr_code, QRcodeScannerActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_take_photo), R.drawable.ic_photograph, TakePhotoActivity.class, true));
            // menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_broadcast_page), R.drawable.ic_broadcast_icon, BroadcastActivity.class, true));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_history_page), R.drawable.ic_history, HistoryPageActivity.class, false));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_profile_page), R.drawable.ic_id_card, ProfilePageActivity.class, false));
            menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_sync_offline), R.drawable.ic_sync, SyncOfflineActivity.class, false));

        }

     /*   menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_qrcode_scanner), R.drawable.ic_qr_code, QRcodeScannerActivity.class, true));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_take_photo), R.drawable.ic_photograph, TakePhotoActivity.class, true));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.string_horticulture_waste), R.drawable.ic_hort_waste, HorticultureActivity.class, true));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.string_c_and_d_waste), R.drawable.ic_cad_waste, CandDActivity.class, true));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.string_ctpt_heading), R.drawable.ic_ctpt_toilet, CommunityAndPublicToiletActivity.class, true));
        // menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_broadcast_page), R.drawable.ic_broadcast_icon, BroadcastActivity.class, true));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_history_page), R.drawable.ic_history, HistoryPageActivity.class, false));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_profile_page), R.drawable.ic_id_card, ProfilePageActivity.class, false));
        menuPojoList.add(new MenuListPojo(getResources().getString(R.string.title_activity_sync_offline), R.drawable.ic_sync, SyncOfflineActivity.class, false));
*/
        DashboardMenuAdapter mainMenuAdaptor = new DashboardMenuAdapter(mContext);
        mainMenuAdaptor.setMenuList(menuPojoList);
        menuGridView.setAdapter(mainMenuAdaptor);

        Type type = new TypeToken<AttendancePojo>() {
        }.getType();
        attendancePojo = new Gson().fromJson(Prefs.getString(AUtils.PREFS.IN_PUNCH_POJO, null), type);

        if (isView)
            checkDutyStatus();
    }

    private void performLogout() {
        AUtils.showConfirmationDialog(mContext, AUtils.CONFIRM_LOGOUT_DIALOG, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                if (!AUtils.isIsOnduty()) {
                    if (AUtils.isInternetAvailable())
                        verifyOfflineData(LoginActivity.class, true);
                    else
                        AUtils.warning(mContext, getResources().getString(R.string.no_internet_error));
                } else {
                    AUtils.info(mContext, getResources().getString(R.string.off_duty_warning));
                }
                Prefs.remove(AUtils.PREFS.EMPLOYEE_TYPE);

            }

        }, null);
    }

    private void openLogin(Context context, Class<?> providedClass) {

        lastLocationRepository.clearTableRows();
        syncOfflineRepository.deleteCompleteSyncTableData();
        syncOfflineAttendanceRepository.deleteCompleteAttendanceSyncTableData();

        Prefs.remove(AUtils.PREFS.IS_USER_LOGIN);
        Prefs.remove(AUtils.PREFS.USER_ID);
        Prefs.remove(AUtils.PREFS.USER_TYPE);
        Prefs.remove(AUtils.PREFS.VEHICLE_TYPE_POJO_LIST);
        Prefs.remove(AUtils.PREFS.USER_DETAIL_POJO);
        AUtils.setIsOnduty(false);
        Prefs.remove(AUtils.PREFS.IMAGE_POJO);
        Prefs.remove(AUtils.PREFS.WORK_HISTORY_DETAIL_POJO_LIST);
        Prefs.remove(AUtils.PREFS.WORK_HISTORY_POJO_LIST);
        Prefs.remove(AUtils.LAT);
        Prefs.remove(AUtils.LONG);
        Prefs.remove(AUtils.VEHICLE_NO);
        Prefs.remove(AUtils.VEHICLE_ID);
        startActivity(new Intent(context, providedClass));
    }

    private void changeLanguage() {

        HashMap<Integer, Object> mLanguage = new HashMap<>();
        List<LanguagePojo> mLanguagePojoList = AUtils.getLanguagePojoList();
        AUtils.changeLanguage(this, Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID));

        if (!AUtils.isNull(mLanguagePojoList) && !mLanguagePojoList.isEmpty()) {
            for (int i = 0; i < mLanguagePojoList.size(); i++) {
                mLanguage.put(i, mLanguagePojoList.get(i));
            }

            PopUpDialog dialog = new PopUpDialog(DashboardActivity.this, AUtils.DIALOG_TYPE_LANGUAGE, mLanguage, this);
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
            playsound();
            onSwitchOn();

        } else {
            playsound();
            onSwitchOff();

        }

    }

    private void onVehicleTypeDialogClose(Object listItemSelected, String vehicleNo) {

        if (!AUtils.isNull(vehicleNo) && !vehicleNo.isEmpty()) {
            VehicleTypePojo vehicleTypePojo = (VehicleTypePojo) listItemSelected;

            Prefs.putString(AUtils.VEHICLE_ID, vehicleTypePojo.getVtId());

            Prefs.putString(AUtils.VEHICLE_NO, vehicleNo);

            if (AUtils.isNull(attendancePojo)) {
                attendancePojo = new AttendancePojo();
            }

            try {
                syncOfflineAttendanceRepository.insertCollection(attendancePojo, SyncOfflineAttendanceRepository.InAttendanceId);
                onInPunchSuccess();
                if (AUtils.isInternetAvailable()) {
                    if (!syncOfflineAttendanceRepository.checkIsInAttendanceSync())
                        mOfflineAttendanceAdapter.SyncOfflineData();
                }
            } catch (Exception e) {
                e.printStackTrace();
                markAttendance.setChecked(false);
                AUtils.error(mContext, mContext.getString(R.string.something_error), Toast.LENGTH_SHORT);
            }
        } else {
            markAttendance.setChecked(false);
        }
    }

    private void onInPunchSuccess() {
        attendanceStatus.setText(this.getResources().getString(R.string.status_on_duty));
        attendanceStatus.setTextColor(this.getResources().getColor(R.color.colorONDutyGreen));

        String vehicleType = null;

        for (int i = 0; i < vehicleTypePojoList.size(); i++) {
            if (Prefs.getString(AUtils.VEHICLE_ID, "0").equals(vehicleTypePojoList.get(i).getVtId())) {
//                if(Prefs.getString(AUtils.LANGUAGE_NAME,AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
//                    vehicleType = vehicleTypePojoList.get(intent).getDescriptionMar();
//                else
                vehicleType = vehicleTypePojoList.get(i).getDescription();
            }
        }

        if (!AUtils.isNullString(attendancePojo.getVehicleNumber())) {

            vehicleStatus.setText(String.format("%s%s %s %s%s", this.getResources().getString(R.string.opening_round_bracket), vehicleType,
                    this.getResources().getString(R.string.hyphen), attendancePojo.getVehicleNumber(),
                    this.getResources().getString(R.string.closing_round_bracket)));
        } else {
            vehicleStatus.setText(String.format("%s%s%s", this.getResources().getString(R.string.opening_round_bracket),
                    vehicleType, this.getResources().getString(R.string.closing_round_bracket)));
        }

        AUtils.setInPunchDate(Calendar.getInstance());
        Log.i(TAG, AUtils.getInPunchDate());
        AUtils.setIsOnduty(true);
    }

    private void onOutPunchSuccess() {
        attendanceStatus.setText(this.getResources().getString(R.string.status_off_duty));
        attendanceStatus.setTextColor(this.getResources().getColor(R.color.colorOFFDutyRed, getResources().newTheme()));

        vehicleStatus.setText("");

        stopServiceIfRunning();

        markAttendance.setChecked(false);

        attendancePojo = null;
        AUtils.removeInPunchDate();
        AUtils.setIsOnduty(false);
    }

    private void stopServiceIfRunning() {
        boolean isservicerunning = AUtils.isMyServiceRunning(AUtils.mainApplicationConstant, LocationService.class);

        if (isservicerunning)
            ((MyApplication) AUtils.mainApplicationConstant).stopLocationTracking();
    }

    private void getPermission() {

        isLocationPermission = AUtils.isLocationPermissionGiven(DashboardActivity.this);
    }

    private void initUserDetails() {
        userDetailPojo = mUserDetailAdapter.getUserDetailPojo();

        if (!AUtils.isNull(userDetailPojo)) {

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

            String vehicleName = "";

            if (AUtils.isNull(vehicleTypePojoList)) {
                vehicleTypePojoList = mVehicleTypeAdapter.getVehicleTypePojoList();
            }
            for (int i = 0; i < vehicleTypePojoList.size(); i++) {

                if (Prefs.getString(AUtils.VEHICLE_ID, "").equals(vehicleTypePojoList.get(i).getVtId())) {
                    if (Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals(AUtils.LanguageConstants.MARATHI))
                        vehicleName = vehicleTypePojoList.get(i).getDescription(); //Before it was getDescriptionMar
                    else
                        vehicleName = vehicleTypePojoList.get(i).getDescription();
                }
            }

            if (!AUtils.isNullString(Prefs.getString(AUtils.VEHICLE_NO, ""))) {

                vehicleStatus.setText(String.format("%s%s %s %s%s", this.getResources().getString(R.string.opening_round_bracket), vehicleName, this.getResources().getString(R.string.hyphen), Prefs.getString(AUtils.VEHICLE_NO, ""), this.getResources().getString(R.string.closing_round_bracket)));
            } else {
                vehicleStatus.setText(String.format("%s%s%s", this.getResources().getString(R.string.opening_round_bracket), vehicleName, this.getResources().getString(R.string.closing_round_bracket)));
            }
        } else {
            markAttendance.setChecked(false);
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

    private void verifyOfflineData(Class<?> nextClass, boolean killPreviousActivity) {
        verifyDataAdapterClass.setRequiredParameters(nextClass, killPreviousActivity);
        verifyDataAdapterClass.verifyData();
    }

    private void takePhoto() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("android.intent.extras.CAMERA_FACING", CameraCharacteristics.LENS_FACING_BACK);
        intent.putExtra("android.intent.extra.USE_FRONT_CAMERA", true);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    private void onSwitchOn() {

        if (isLocationPermission) {

            if (AUtils.isGPSEnable(AUtils.currentContextConstant)) {
                if (!AUtils.DutyOffFromService) {
                    HashMap<Integer, Object> mLanguage = new HashMap<>();

                    vehicleTypePojoList = mVehicleTypeAdapter.getVehicleTypePojoList();

                    if (!AUtils.isNull(vehicleTypePojoList) && !vehicleTypePojoList.isEmpty()) {
                        for (int i = 0; i < vehicleTypePojoList.size(); i++) {
                            mLanguage.put(i, vehicleTypePojoList.get(i));
                        }

                        if (!AUtils.isIsOnduty()) {
                            ((MyApplication) AUtils.mainApplicationConstant).startLocationTracking();

                            PopUpDialog dialog = new PopUpDialog(DashboardActivity.this, AUtils.DIALOG_TYPE_VEHICLE, mLanguage, this);
                            dialog.show();
                        }
                    } else {
                        mVehicleTypeAdapter.getVehicleType();
                        markAttendance.setChecked(false);
                        AUtils.error(mContext, mContext.getString(R.string.vehicle_not_found_error), Toast.LENGTH_SHORT);
                    }
                } else {
                    AUtils.DutyOffFromService = false;
                }
            } else {
                markAttendance.setChecked(false);
                Toast.makeText(mContext, getResources().getString(R.string.no_internet_error), Toast.LENGTH_LONG).show();
//                AUtils.showGPSSettingsAlert(mContext);
            }
        } else {
            isLocationPermission = AUtils.isLocationPermissionGiven(DashboardActivity.this);
        }
    }

    private void onSwitchOff() {

        if (AUtils.isIsOnduty()) {
            try {
                if (!isFromAttendanceChecked) {
                    AUtils.showConfirmationDialog(mContext, AUtils.CONFIRM_OFFDUTY_DIALOG, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                            if (AUtils.isNull(attendancePojo))
                                attendancePojo = new AttendancePojo();

                            syncOfflineAttendanceRepository.insertCollection(attendancePojo, SyncOfflineAttendanceRepository.OutAttendanceId);
                            onOutPunchSuccess();

                            if (AUtils.isInternetAvailable()) {
                                mOfflineAttendanceAdapter.SyncOfflineData();
                            }
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
        }


    }

    private void onCaptureImageResult(Intent data) {

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        File destination = null;
        try {
            File dir = mContext.getExternalFilesDir(null);

            if (!dir.exists()) {
                boolean b = dir.mkdir();
                Log.i(TAG, String.valueOf(b));
            }

            destination = new File(dir, System.currentTimeMillis() + ".jpg");

            FileOutputStream fOut = new FileOutputStream(destination);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, fOut);

            attendancePojo.setImagePath(destination.getAbsolutePath());

            onSwitchOn();

        } catch (Exception e) {

            e.printStackTrace();
            AUtils.error(mContext, mContext.getResources().getString(R.string.image_add_error), Toast.LENGTH_SHORT);
        }
    }


    @Override
    protected void onDestroy() {
        isView = false;
        isDeviceMatch = false;
        super.onDestroy();
    }

    public void statusCheck() {

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10);
        mLocationRequest.setSmallestDisplacement(10);
        mLocationRequest.setFastestInterval(10);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);

        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());


        task.addOnCompleteListener(task1 -> {
            try {
                LocationSettingsResponse response = task1.getResult(ApiException.class);
                // All location settings are satisfied. The client can initialize location
                // requests here.

            } catch (ApiException exception) {
                switch (exception.getStatusCode()) {
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        // Location settings are not satisfied. But could be fixed by showing the
                        // user a dialog.
                        try {
                            // Cast to a resolvable exception.
                            ResolvableApiException resolvable = (ResolvableApiException) exception;
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            resolvable.startResolutionForResult(
                                    DashboardActivity.this,
                                    101);
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        } catch (ClassCastException e) {
                            // Ignore, should be an impossible error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
                        break;
                }
            }
        });
    }

    private void getHouseDetails(String house_Count) {
        houseList = new ArrayList<>();

        progressBar = new ProgressDialog(DashboardActivity.this);
        progressBar.setTitle("Loading...");
        progressBar.setMessage("Please wait.");
        progressBar.show();


        GetHouseDetailService service = Connection.createService(GetHouseDetailService.class, AUtils.SERVER_URL);
        Call<List<HouseCTypePojo>> call = service.getHouseDetails(Prefs.getString(AUtils.APP_ID, null),
                Prefs.getString(AUtils.CONTENT_TYPE, null));
        call.enqueue(new Callback<List<HouseCTypePojo>>() {
            @Override
            public void onResponse(Call<List<HouseCTypePojo>> call, Response<List<HouseCTypePojo>> response) {
                progressBar.dismiss();
                if (response.body() != null) {
                    for (int i = 0; i < response.body().size(); i++) {
                        saveNewHouse(response.body().get(i).getHouseid(), response.body().get(i).getCtype());
                    }
                }
                Log.e(TAG, "size " + response.body().size());

            }

            @Override
            public void onFailure(Call<List<HouseCTypePojo>> call, Throwable t) {
                progressBar.dismiss();
                Log.e(TAG, "onFailure: " + t.getMessage());
            }
        });
    }

    private void saveNewHouse(String houseId, String cType) {
        houseEntity.houseId = houseId;
        houseEntity.cType = cType;
        db.houseDao().insertHouse(houseEntity);

    }

}
