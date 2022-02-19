package com.appynitty.swachbharatabhiyanlibrary.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.Timer;

public class ForgroundService extends Service {

    private static final String TAG = ForgroundService.class.getSimpleName();
    private LocationMonitoringService monitoringService;

    private Handler locationHandler;

    private Runnable locationThread;

    private static final String PACKAGE_NAME =
            "com.appynitty.swachbharatabhiyanlibrary.services";

    private static final String EXTRA_STARTED_FROM_NOTIFICATION = PACKAGE_NAME +
            ".started_from_notification";

    private static String channelId;

    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    long notify_interval = 1000 * 60 * 10;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO Auto-generated method stub
        return null;
    }


    @Override
    public void onCreate() {
//        Toast.makeText(this, " MyService Created ", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate: " + System.currentTimeMillis());
        monitoringService = new LocationMonitoringService(this);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

//        Toast.makeText(this, " MyService Started", Toast.LENGTH_LONG).show();
        Log.d(TAG, "onCreate: " + System.currentTimeMillis());

        Log.d(TAG, "onStartCommand: ");
        final int currentId = startId;
//        mTimer = new Timer();
//        mTimer.schedule(new TimerTaskToGetLocation(), 1,notify_interval);

        locationThread = new Runnable() {
            public void run() {

                monitoringService.onStartTacking();

            }
        };

        locationHandler = new Handler(Looper.getMainLooper());
        locationHandler.post(locationThread);

        startLocationForeground();

        return Service.START_STICKY;

//        return Service.START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        //Toast.makeText(this, "MyService Stopped", Toast.LENGTH_LONG).show();
        locationHandler.removeCallbacks(locationThread);
        monitoringService.onStopTracking(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            stopForeground(STOP_FOREGROUND_REMOVE);
        }

        if (Prefs.getBoolean(AUtils.PREFS.IS_ON_DUTY, false)) {
            Intent broadcastIntent = new Intent(this, RestarterBroadcastReceiver.class);
            sendBroadcast(broadcastIntent);
        }
    }

    private void startLocationForeground() {

        if (Build.VERSION.SDK_INT >= 26) {
            String channelId = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O
                    ? this.createNotificationChannel("my_service", "My Background Service")
                    : "";

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder((Context) this,
                    channelId);


            Notification notification = notificationBuilder
                    .setOngoing(true)
                    .setContentText("Please don't kill the app from background.\nThank you!!")
                    .setSmallIcon(getNotificationIcon(notificationBuilder))
                    .setPriority(-2)
                    .setCategory("service")
                    .build();

            startForeground(101, notification);
        }
    }

    @RequiresApi(26)
    private String createNotificationChannel(String channelId, String channelName) {

        NotificationChannel chan = new NotificationChannel(channelId, (CharSequence) channelName,
                NotificationManager.IMPORTANCE_NONE);

        chan.setLightColor(Color.BLUE);
        chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        NotificationManager service = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        service.createNotificationChannel(chan);
        return channelId;
    }

    private int getNotificationIcon(NotificationCompat.Builder notificationBuilder) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            int color = 0x008000;
            notificationBuilder.setColor(color);
            return R.drawable.ic_noti_icon;

        }
        return R.drawable.ic_noti_icon;
    }


//    private class TimerTaskToGetLocation extends TimerTask {
//        @Override
//        public void run() {
//            mHandler.post(new Runnable() {
//                @Override
//                public void run() {
//
//                    monitoringService.onStartTacking();
//
//                }
//            });
//        }
//    }


}
