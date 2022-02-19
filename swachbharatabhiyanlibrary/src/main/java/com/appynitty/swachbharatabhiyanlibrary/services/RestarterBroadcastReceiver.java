package com.appynitty.swachbharatabhiyanlibrary.services;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.PowerManager;
import android.util.Log;

import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.appynitty.swachbharatabhiyanlibrary.utils.MyApplication;
import com.pixplicity.easyprefs.library.Prefs;

public class RestarterBroadcastReceiver extends BroadcastReceiver {
    LocationMonitoringService locationMonitoringService;
    private Handler mHandler = new Handler();


    @Override
    public void onReceive(Context context, Intent intent) {
//        scheduleExactAlarm(context, (AlarmManager)context.getSystemService(Context.ALARM_SERVICE));
        Log.e(RestarterBroadcastReceiver.class.getSimpleName(), "Service Stops! Oooooooooooooppppssssss!!!!");
        if(Prefs.getBoolean(AUtils.PREFS.IS_ON_DUTY,false))
        {
            if(!AUtils.isMyServiceRunning(AUtils.mainApplicationConstant,LocationService.class))
            {
                ((MyApplication)AUtils.mainApplicationConstant).startLocationTracking();
            }
        }
        //context.startService(new Intent(context, ForgroundService.class));
//        PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
//        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "GpsTrackerWakelock:");
//        wl.acquire();
//
//
//        mHandler = new Handler();
//        Runnable periodicUpdate = new Runnable() {
//            @Override
//            public void run() {
//                // whatever you want to do
////          sendLocation();
//                locationMonitoringService.onStartTacking();
//
//            }
//        };
//
//        mHandler.post(periodicUpdate);
//        wl.release();
//    }




//    private void scheduleExactAlarm(Context context, AlarmManager systemService) {
//        Intent i=new Intent(context, LocationMonitoringService.class);
//        PendingIntent pi=PendingIntent.getBroadcast(context, 0, i, 0);
//        systemService.setExact(AlarmManager.ELAPSED_REALTIME_WAKEUP, 1000 * 60 * 10, pi);
//
//    }
}
}