package com.appynitty.swachbharatabhiyanlibrary.services;

import android.Manifest;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.activity.DashboardActivity;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.ShareLocationAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.UserLocationPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.LocationRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineAttendanceRepository;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncOfflineRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyApplication;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.tasks.Task;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Swapnil Lanjewar on 30/01/2022.
 */

public class LocationService extends Service {

    private static final long UPDATE_INTERVAL_IN_MILLISECONDS = 3000;
    private static final int LOCATION_SERVICE_NOTIF_ID = 1001;
    private static final String CHANNEL_ID = "my_service";
    private static final String TAG = "LocationUpdateService";
    private FusedLocationProviderClient mFusedLocationClient;
    private LocationRequest locationRequest;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 1000 * 60;

    private final LocationRepository mLocationRepository;

    private final ShareLocationAdapterClass mAdapter;

    private final SyncOfflineRepository syncOfflineRepository;

    private long updatedTime = 0;

    private final List<UserLocationPojo> mUserLocationPojoList;

    private final SyncOfflineAttendanceRepository syncOfflineAttendanceRepository;
    LocationCallback locationCallback1;


    public LocationService() {

        mLocationRepository = new LocationRepository(AUtils.mainApplicationConstant.getApplicationContext());

        syncOfflineRepository = new SyncOfflineRepository(AUtils.mainApplicationConstant.getApplicationContext());

        syncOfflineAttendanceRepository = new SyncOfflineAttendanceRepository(AUtils.mainApplicationConstant.getApplicationContext());

        mUserLocationPojoList = new ArrayList<>();

        mAdapter = new ShareLocationAdapterClass();

        mAdapter.setShareLocationListener(new ShareLocationAdapterClass.ShareLocationListener() {

            @Override
            public void onSuccessCallBack(boolean isAttendanceOff) {
                if (isAttendanceOff && !syncOfflineAttendanceRepository.checkIsAttendanceIn()) {
                    AUtils.setIsOnduty(false);
                    ((MyApplication) AUtils.mainApplicationConstant).stopLocationTracking();
                }
            }

            @Override
            public void onFailureCallBack() {

            }
        });

    }

    @Override
    public void onCreate() {
        super.onCreate();
        initData();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        prepareForegroundNotification();
        startLocationUpdates();

        mTimer = new Timer();
        mTimer.schedule(new TimerTaskToSendLocation(), 10, notify_interval);

        return START_STICKY;
    }


    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mFusedLocationClient.requestLocationUpdates(this.locationRequest,
                this.locationCallback1, Looper.myLooper());
    }

    private void prepareForegroundNotification() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Location Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

        Bitmap largeBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle(getString(R.string.app_name))
                .setContentTitle(getString(R.string.app_notification_description))
                .setSmallIcon(R.drawable.app_icon_white)
                .setColor(getResources().getColor(R.color.colorPrimary, getResources().newTheme()))
                .setLargeIcon(largeBitmap)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        startForeground(LOCATION_SERVICE_NOTIF_ID, notification);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void initData() {

        locationRequest = LocationRequest.create();
        locationRequest.setInterval(UPDATE_INTERVAL_IN_MILLISECONDS);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new
                LocationSettingsRequest.Builder();
        builder.addLocationRequest(locationRequest);
        Task<LocationSettingsResponse> task = LocationServices.getSettingsClient(this).checkLocationSettings(builder.build());
        mFusedLocationClient =
                LocationServices.getFusedLocationProviderClient(getApplicationContext());

        locationCallback1 = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                if (locationResult == null) {
                    return;
                }
                for (Location location : locationResult.getLocations()) {

                    Prefs.putString(AUtils.LAT, String.valueOf(location.getLatitude()));
                    Prefs.putString(AUtils.LONG, String.valueOf(location.getLongitude()));

                    if (Prefs.getBoolean(AUtils.PREFS.IS_ON_DUTY, false)) {
                        if (updatedTime == 0) {
                            updatedTime = System.currentTimeMillis();
                            Log.d(TAG, "updated Time ==== " + updatedTime);
                        }

                        if ((updatedTime + AUtils.LOCATION_INTERVAL_MINUTES) <= System.currentTimeMillis()) {
                            updatedTime = System.currentTimeMillis();
                            Log.d(TAG, "updated Time ==== " + updatedTime);
                        }

                    }
                }
            }
        };

    }

    private class TimerTaskToSendLocation extends TimerTask {
        @Override
        public void run() {
            sendLocation();
        }
    }

    private void sendLocation() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTime = sdf.format(new Date());

        Log.e(TAG, "sendLocation: sending location=> "
                + Prefs.getString(AUtils.LAT, null)
                + ", " + Prefs.getString(AUtils.LONG, null)
                + " at " + currentTime);

        try {
            Calendar CurrentTime = AUtils.getCurrentTime();
            Calendar DutyOffTime = AUtils.getDutyEndTime();

            if (CurrentTime.before(DutyOffTime)) {

                Log.i(TAG, "Before");

                UserLocationPojo userLocationPojo = new UserLocationPojo();

                userLocationPojo.setUserId(Prefs.getString(AUtils.PREFS.USER_ID, ""));
                userLocationPojo.setLat(Prefs.getString(AUtils.LAT, ""));
                userLocationPojo.setLong(Prefs.getString(AUtils.LONG, ""));
                double startLat = Double.parseDouble(Prefs.getString(AUtils.LAT, "0"));
                double startLng = Double.parseDouble(Prefs.getString(AUtils.LONG, "0"));
                userLocationPojo.setDistance(String.valueOf(AUtils.calculateDistance(
                        AUtils.mainApplicationConstant.getApplicationContext(), startLat, startLng)));
                userLocationPojo.setDatetime(AUtils.getServerDateTimeLocal());
                userLocationPojo.setOfflineId("0");

                userLocationPojo.setIsOffline(AUtils.isInternetAvailable() && AUtils.isConnectedFast(this));

                String UserTypeId = Prefs.getString(AUtils.PREFS.USER_TYPE_ID, AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI);
                if (AUtils.isInternetAvailable()) {
                    TableDataCountPojo.LocationCollectionCount count = syncOfflineRepository.getLocationCollectionCount(AUtils.getLocalDate());
                    if ((UserTypeId.equals(AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI) || UserTypeId.equals(AUtils.USER_TYPE.USER_TYPE_WASTE_MANAGER))
                            && (count.getLocationCount() > 0 || count.getCollectionCount() > 0)) {
                        syncOfflineRepository.insetUserLocation(userLocationPojo);
                    } else {
                        mUserLocationPojoList.add(userLocationPojo);
                        mAdapter.shareLocation(mUserLocationPojoList);
                        mUserLocationPojoList.clear();
                    }
                } else {
                    if (UserTypeId.equals(AUtils.USER_TYPE.USER_TYPE_EMP_SCANNIFY)) {
                        Type type = new TypeToken<UserLocationPojo>() {
                        }.getType();
                        mLocationRepository.insertUserLocationEntity(new Gson().toJson(userLocationPojo, type));
                    } else {
                        syncOfflineRepository.insetUserLocation(userLocationPojo);
                    }
                    mUserLocationPojoList.clear();
                }

            } else {
                Log.i(TAG, "After");

                syncOfflineAttendanceRepository.performCollectionInsert(this,
                        syncOfflineAttendanceRepository.checkAttendance(), AUtils.getCurrentDateDutyOffTime());

                AUtils.setIsOnduty(false);
                ((MyApplication) AUtils.mainApplicationConstant).stopLocationTracking();

                Activity activity = ((Activity) AUtils.currentContextConstant);

                if (activity instanceof DashboardActivity) {
                    ((Activity) AUtils.currentContextConstant).recreate();
                    AUtils.DutyOffFromService = true;
                }

                if (!AUtils.isNull(AUtils.currentContextConstant)) {
                    ((Activity) AUtils.currentContextConstant).recreate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }


}
