package com.appynitty.swachbharatabhiyanlibrary.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.os.SystemClock;

public class GpsTracker extends BroadcastReceiver {
    private LocationMonitoringService monitoringService;



    @Override
    public void onReceive(Context context, Intent intent) {
        scheduleExactAlarm(context, (AlarmManager)context.getSystemService(Context.ALARM_SERVICE));

        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GpsTrackerWakelock:");
        wl.acquire();


        Handler handler = new Handler();
        Runnable periodicUpdate = new Runnable() {
            @Override
            public void run() {
                // whatever you want to do
//            monitoringService.onStartTacking();

            }
        };

        handler.post(periodicUpdate);
        wl.release();
    }

    private void scheduleExactAlarm(Context context, AlarmManager systemService) {

        Intent i=new Intent(context, GpsTracker.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);
        systemService.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime()+10*1000-SystemClock.elapsedRealtime()%1000, pi);

    }

    public static void cancelAlarm(Context context, AlarmManager alarms) {
        Intent i=new Intent(context, GpsTracker.class);
        PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);
        alarms.cancel(pi);
    }
}
