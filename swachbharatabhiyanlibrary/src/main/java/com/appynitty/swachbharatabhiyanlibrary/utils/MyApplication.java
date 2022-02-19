package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.app.Activity;
import android.app.Application;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;

import com.appynitty.swachbharatabhiyanlibrary.services.LocationService;
import com.appynitty.swachbharatabhiyanlibrary.services.NetworkSchedulerService;
import com.facebook.stetho.Stetho;
import com.pixplicity.easyprefs.library.Prefs;

public class MyApplication extends Application {

    public static boolean activityVisible;

    @Override
    public void onCreate() {
        super.onCreate();

//        init Easy Prefs lib
        new Prefs.Builder()
                .setContext(this)
                .setMode(ContextWrapper.MODE_PRIVATE)
                .setPrefsName(getPackageName())
                .setUseDefaultSharedPreference(true)
                .build();
        Stetho.initializeWithDefaults(this);

        AUtils.mainApplicationConstant = this;
        //FirebaseApp.initializeApp(getApplicationContext());

//        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "MYRIADPRO-REGULAR.OTF"); // font from assets: "assets/fonts/Roboto-Regular.ttf

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }

            @Override
            public void onActivityStarted(Activity activity) {
                Intent startServiceIntent = new Intent(activity, NetworkSchedulerService.class);
                startService(startServiceIntent);
            }

            @Override
            public void onActivityResumed(Activity activity) {
                activityVisible = true;
            }

            @Override
            public void onActivityPaused(Activity activity) {
                activityVisible = false;
            }

            @Override
            public void onActivityStopped(Activity activity) {
                stopService(new Intent(activity, NetworkSchedulerService.class));
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });

        scheduleJob();
    }

    public void startLocationTracking()
    {
        Intent intent = new Intent(this, LocationService.class);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
           startForegroundService(intent);
        } else {
        startService(intent);
        }
    }

    public void stopLocationTracking()
    {
        stopService(new Intent(this, LocationService.class));
    }

    private void scheduleJob() {
        JobInfo myJob = new JobInfo.Builder(0, new ComponentName(this, NetworkSchedulerService.class))
                .setRequiresCharging(true)
                .setMinimumLatency(1000*60*10)
                .setOverrideDeadline(1000*60*15)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setPersisted(true)
                .build();

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        jobScheduler.schedule(myJob);
    }

    public static boolean isActivityVisible() {
        return activityVisible; // return true or false
    }
}