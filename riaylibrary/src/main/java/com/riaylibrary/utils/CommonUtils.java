package com.riaylibrary.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Application;
import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.PictureDrawable;
import android.location.LocationManager;
import android.media.MediaScannerConnection;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.webkit.MimeTypeMap;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.appynitty.riaylibrary.R;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.StreamEncoder;
import com.bumptech.glide.load.resource.file.FileToStreamDecoder;
import com.caverock.androidsvg.SVG;
import com.google.android.material.snackbar.Snackbar;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.SvgDecoder;
import com.riaylibrary.custom_component.SvgDrawableTranscoder;
import com.riaylibrary.custom_component.SvgSoftwareLayerSetter;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static final int MY_PERMISSIONS_REQUEST_CAMERA = 444;
    public static final int MY_PERMISSIONS_REQUEST_STORAGE = 555;
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 666;
    public static final String LANGUAGE_NAME = "LanguageName";
    public static final String CONFIRM_LOGOUT_DIALOG = "confirmLogout";
    public static final String CONFIRM_OFFDUTY_DIALOG = "confirmOffDuty";
    public static final String VERSION_CODE = "AppVersion";
    private static final String TAG = "CommonUtils";
    private static final Pattern numberPattern = Pattern.compile("-?\\d+");
    private static final String[] tensNames = {"", " ten", " twenty", " thirty", " forty", " fifty",
            " sixty", " seventy", " eighty", " ninety"};
    private static final String[] numNames = {"", " one", " two", " three", " four", " five", " six",
            " seven", " eight", " nine", " ten", " eleven", " twelve", " thirteen", " fourteen", " fifteen",
            " sixteen", " seventeen", " eighteen", " nineteen"};
    public static Application mainApplicationConstant;
    public static Context currentContextConstant;
    public static Snackbar mSnackbar;

    //app setting for permissions dialog
    public static void showPermissionDialog(Context context, String message,
                                            DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(context)
                .setTitle("Need Permission")
                .setMessage("App needs a permission to access " + message)
                .setPositiveButton("Grant", okListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }

    //separate dialog for Location permission
    public static void showLocationPermissionDialog(Context context, String message,
                                                    DialogInterface.OnClickListener okListener) {

        new AlertDialog.Builder(context)
                .setTitle("Location Permission")
                .setMessage("This app collects location data to " + message)
                .setPositiveButton("Grant", okListener)
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                })
                .create()
                .show();
    }



    public static void showConfirmationDialog(Context context, String type, DialogInterface.OnClickListener positiveListener, @Nullable DialogInterface.OnClickListener negativeLisner) {

        String message = "";
        String title = "";
        String positiveText = context.getResources().getString(R.string.yes_txt);
        String negativeText = context.getResources().getString(R.string.no_txt);

        if (type.equals(CONFIRM_LOGOUT_DIALOG)) {
            title = context.getResources().getString(R.string.logout_confirmation_title);
            message = context.getResources().getString(R.string.logout_confirmation_msg);
        } else if (type.equals(CONFIRM_OFFDUTY_DIALOG)) {
            title = context.getResources().getString(R.string.offduty_confirmation_title);
            message = context.getResources().getString(R.string.offduty_confirmation_msg);
        } else if (type.equals(VERSION_CODE)) {
            title = context.getResources().getString(R.string.update_title);
            message = context.getResources().getString(R.string.update_message);
            positiveText = context.getResources().getString(R.string.update_txt);
            negativeText = context.getResources().getString(R.string.no_thanks_txt);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setMessage(message)
                .setCancelable(false)
                .setPositiveButton(positiveText, positiveListener);

        if (negativeLisner != null) {
            builder.setNegativeButton(negativeText, negativeLisner);
        } else {
            builder.setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
        }

        builder.create()
                .show();
    }

    public static boolean isCameraPermissionGiven(final Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.CAMERA)) {
                //Show Information about why you need the permission

                showPermissionDialog(context, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ((Activity) context).requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });

            } else if (Prefs.getBoolean(Manifest.permission.CAMERA, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                showPermissionDialog(context, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        goToAppSettings(context);
                    }
                });

            } else {
                //just request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.CAMERA}, MY_PERMISSIONS_REQUEST_CAMERA);
                }
            }

            Prefs.putBoolean(Manifest.permission.CAMERA, true);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isStoragePermissionGiven(final Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                //Show Information about why you need the permission

                showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ((Activity) context).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
                        }
                    }
                });

            } else if (Prefs.getBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                showPermissionDialog(context, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        goToAppSettings(context);
                    }
                });

            } else {
                //just request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_STORAGE);
                }
            }

            Prefs.putBoolean(Manifest.permission.WRITE_EXTERNAL_STORAGE, true);
            return false;
        } else {
            return true;
        }
    }

    public static boolean isLocationPermissionGiven(final Context context) {

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_FINE_LOCATION)) {
                //Show Information about why you need the permission

                showLocationPermissionDialog(context, " track the user collected garbage location even when the app is closed or not in use.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ((Activity) context).requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }
                });

            }

            else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) context, Manifest.permission.ACCESS_BACKGROUND_LOCATION)) {
                //Show Information about why you need the permission

                showLocationPermissionDialog(context, "track the user collected garbage location even when the app is closed or not in use.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            ((Activity) context).requestPermissions(new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }
                });

            }

            else if (Prefs.getBoolean(Manifest.permission.ACCESS_FINE_LOCATION, false)) {
                //Previously Permission Request was cancelled with 'Dont Ask Again',
                // Redirect to Settings after showing Information about why you need the permission

                showLocationPermissionDialog(context, " track the user collected garbage location even when the app is closed or not in use.", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.cancel();
                        goToAppSettings(context);
                    }
                });

            } else {
                //just request the permission
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    ((Activity) context).requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, MY_PERMISSIONS_REQUEST_LOCATION);
                }
            }

            Prefs.putBoolean(Manifest.permission.ACCESS_FINE_LOCATION, true);
            return false;
        } else {
            return true;
        }
    }

    public static void runOnUIThread(Runnable r) {
        new Handler(Looper.getMainLooper()).post(r);
    }

    public static ArrayList<String> getMonthList() {
        ArrayList<String> spinnerList = new ArrayList<>();
        //    String[] monthNames = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        spinnerList.add("Select Month");
        spinnerList.add("Jan");
        spinnerList.add("Feb");
        spinnerList.add("Mar");
        spinnerList.add("Apr");
        spinnerList.add("May");
        spinnerList.add("June");
        spinnerList.add("Jul");
        spinnerList.add("Aug");
        spinnerList.add("Sep");
        spinnerList.add("Oct");
        spinnerList.add("Nov");
        spinnerList.add("Dec");

//        for (int i = 0; i < 12; i++) {
//            Calendar cal = Calendar.getInstance();
//            SimpleDateFormat month_date = new SimpleDateFormat("MMM");
//            cal.set(Calendar.MONTH, i);
//            String month_name = month_date.format(cal.getTime());
////            String month_name = month_date.format(cal.get(Calendar.MONTH)+1);
//            spinnerList.add(month_name);
//            Log.d(TAG, "getMonthList:"+month_name +" Print i"+i);
//
//        }

        return spinnerList;
    }

    public static ArrayList<String> getYearList() {
        ArrayList<String> spinnerList = new ArrayList<>();

        spinnerList.add("Select Year");
        for (int i = 0; i > -5; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.YEAR, i);
            spinnerList.add(String.valueOf(calendar.get(Calendar.YEAR)));
        }

        return spinnerList;
    }

    public static Integer getCurrentMonth() {
        Calendar currMonth = Calendar.getInstance();
        currMonth.add(Calendar.MONTH, 0);
        return currMonth.get(Calendar.MONTH);
    }

    public static Integer getCurrentYear() {
        Calendar currYear = Calendar.getInstance();
        currYear.add(Calendar.YEAR, 0);
        return currYear.get(Calendar.YEAR);
    }

    public static void warning(Context context, String message) {
        MDToast.makeText(context, message, MDToast.LENGTH_SHORT, MDToast.TYPE_WARNING).show();
    }

    public static void warning(Context context, String message, int duration) {
        MDToast.makeText(context, message, duration, MDToast.TYPE_WARNING).show();
    }

    public static void info(Context context, String message) {
        MDToast.makeText(context, message, MDToast.LENGTH_SHORT, MDToast.TYPE_INFO).show();
    }

    public static void info(Context context, String message, int duration) {
        MDToast.makeText(context, message, duration, MDToast.TYPE_INFO).show();
    }

    public static void error(Context context, String message) {
        MDToast.makeText(context, message, MDToast.LENGTH_SHORT, MDToast.TYPE_ERROR).show();
    }

    public static void error(Context context, String message, int duration) {
        MDToast.makeText(context, message, duration, MDToast.TYPE_ERROR).show();
    }

    public static void success(Context context, String message) {
        MDToast.makeText(context, message, MDToast.LENGTH_SHORT, MDToast.TYPE_SUCCESS).show();
    }

    public static void success(Context context, String message, int duration) {
        MDToast.makeText(context, message, duration, MDToast.TYPE_SUCCESS).show();
    }

    public static boolean isGPSEnable(Context context) {
        LocationManager manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }


    public static boolean isMyServiceRunning(Application application, Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) application.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            Log.d(TAG, service.toString());
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public static void showKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            InputMethodManager methodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hideKeyboard(Activity activity) {
        try {
            View view = activity.getCurrentFocus();
            InputMethodManager methodManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (view != null) {
                methodManager.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getBatteryStatus(Application application) {
        BatteryManager batteryManager = (BatteryManager) application.getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

    public static int getBatteryStatus() {
        BatteryManager batteryManager = (BatteryManager) mainApplicationConstant.getApplicationContext().getSystemService(Context.BATTERY_SERVICE);
        return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY);
    }

    public static boolean isInternetAvailable(Application application) {
        ConnectivityManager cm = (ConnectivityManager)
                application.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isInternetAvailable() {
        ConnectivityManager cm = (ConnectivityManager)
                mainApplicationConstant.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting();
    }

    public static boolean isConnectedFast(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        return (info != null && info.isConnected() && isConnectionFast(info.getType(), info.getSubtype()));
    }

    public static boolean isConnectionFast(int type, int subType) {
        if (type == ConnectivityManager.TYPE_WIFI) {
            return true;
        } else if (type == ConnectivityManager.TYPE_MOBILE) {
            switch (subType) {
                case TelephonyManager.NETWORK_TYPE_1xRTT:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_CDMA:
                    return false; // ~ 14-64 kbps
                case TelephonyManager.NETWORK_TYPE_EDGE:
                    return false; // ~ 50-100 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    return true; // ~ 400-1000 kbps
                case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    return true; // ~ 600-1400 kbps
                case TelephonyManager.NETWORK_TYPE_GPRS:
                    return false; // ~ 100 kbps
                case TelephonyManager.NETWORK_TYPE_HSDPA:
                    return true; // ~ 2-14 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPA:
                    return true; // ~ 700-1700 kbps
                case TelephonyManager.NETWORK_TYPE_HSUPA:
                    return true; // ~ 1-23 Mbps
                case TelephonyManager.NETWORK_TYPE_UMTS:
                    return true; // ~ 400-7000 kbps
                /*
                 * Above API level 7, make sure to set android:targetSdkVersion
                 * to appropriate level to use these
                 */
                case TelephonyManager.NETWORK_TYPE_EHRPD: // API level 11
                    return true; // ~ 1-2 Mbps
                case TelephonyManager.NETWORK_TYPE_EVDO_B: // API level 9
                    return true; // ~ 5 Mbps
                case TelephonyManager.NETWORK_TYPE_HSPAP: // API level 13
                    return true; // ~ 10-20 Mbps
                case TelephonyManager.NETWORK_TYPE_IDEN: // API level 8
                    return false; // ~25 kbps
                case TelephonyManager.NETWORK_TYPE_LTE: // API level 11
                    return true; // ~ 10+ Mbps
                // Unknown
                case TelephonyManager.NETWORK_TYPE_UNKNOWN:
                default:
                    return false;
            }
        } else {
            return false;
        }
    }

    public static void goToAppSettings(Context context) {
        Intent myAppSettings = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS", Uri.parse("package:" + context.getPackageName()));
        myAppSettings.addCategory("android.intent.category.DEFAULT");
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(myAppSettings);
    }

    public static boolean isNull(Object object) {
        boolean isValid = false;

        try {
            if (object == null) {
                isValid = true;
            } else if (object.equals("")) {
                isValid = true;
            }
        } catch (Exception var3) {
            System.err.println("Error in LibWeb.isNull:" + var3.getMessage());
        }

        return isValid;
    }

    public static void rateApp(Context context) {
        Intent rateIntent = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + context.getApplicationContext().getPackageName()));
        boolean marketFound = false;
        List<ResolveInfo> otherApps = context.getPackageManager().queryIntentActivities(rateIntent, 0);
        Iterator var4 = otherApps.iterator();

        while (var4.hasNext()) {
            ResolveInfo otherApp = (ResolveInfo) var4.next();
            if (otherApp.activityInfo.applicationInfo.packageName.equals("com.android.vending")) {
                ActivityInfo otherAppActivity = otherApp.activityInfo;
                ComponentName componentName = new ComponentName(otherAppActivity.applicationInfo.packageName, otherAppActivity.name);
                rateIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                rateIntent.setComponent(componentName);
                context.startActivity(rateIntent);
                marketFound = true;
                break;
            }
        }

        if (!marketFound) {
            Intent webIntent = new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName()));
            context.startActivity(webIntent);
        }

    }

    public static void shareThisApp(Context context, String appLink) {
        Intent sharingIntent = new Intent("android.intent.action.SEND");
        sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra("android.intent.extra.SUBJECT", "Share " + context.getString(R.string.app_name));
        if (isNullString(appLink)) {
            sharingIntent.putExtra("android.intent.extra.TEXT", context.getString(R.string.app_share_text) + "\nhttps://play.google.com/store/apps/details?id=" + context.getApplicationContext().getPackageName());
        } else {
            sharingIntent.putExtra("android.intent.extra.TEXT", context.getString(R.string.app_share_text) + "\n" + appLink);
        }

        Intent chooser = Intent.createChooser(sharingIntent, "Share via");
        chooser.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(chooser);
    }

    public static boolean isNullString(String object) {
        boolean isValid = false;

        try {
            if (object == null) {
                isValid = true;
            } else {
                if (object.trim().isEmpty()) {
                    isValid = true;
                }

                if (object.trim().equalsIgnoreCase("null")) {
                    isValid = true;
                }
            }
        } catch (Exception var3) {
            System.err.println("Error in LibWeb.isNullString:" + var3.getMessage());
        }

        return isValid;
    }

    public static void showGPSSettingsAlert(final Context context) {
        android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(context);
        alertDialog.setTitle("GPS is settings");
        alertDialog.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent("android.settings.LOCATION_SOURCE_SETTINGS");
                context.startActivity(intent);
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public static boolean isNumeric(Object object) {
        boolean isValid = false;

        try {
            String expression = "^[-+]?[0-9]*\\.?[0-9]+$";
            CharSequence inputStr = (CharSequence) object;
            Pattern pattern = Pattern.compile(expression);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            }
        } catch (Exception var6) {
            System.err.println("Error in WebUtils.isNumeric:" + var6.getMessage());
        }

        return isValid;
    }

    public static Number randomNumber() {
        return (int) (Math.random() * 32767.0D);
    }

    public static String randomString() {
        return Integer.toString((int) (Math.random() * 2.147483647E9D), 36);
    }

    public static boolean isNumber(String string) {
        return string != null && numberPattern.matcher(string).matches();
    }

    public static boolean isValidDate(String date) {
        boolean isValid = false;

        try {
            String expression = "^[0-1][1-9][- / ]?(0[1-9]|[12][0-9]|3[01])[- /]?(18|19|20|21)\\d{2}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(date);
            if (matcher.matches()) {
                isValid = true;
            }
        } catch (Exception var6) {
            System.err.println("Error in WebUtils.isValidDate:" + var6.getMessage());
        }

        return isValid;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        try {
            String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(email);
            if (matcher.matches()) {
                isValid = true;
            }
        } catch (Exception var6) {
            System.err.println("Error in WebUtils.isEmailValid:" + var6.getMessage());
        }

        return isValid;
    }

    public static boolean isInLength(String object, int length) {
        boolean isValid = false;

        try {
            if (object.length() <= length) {
                isValid = true;
            }
        } catch (Exception var4) {
            System.err.println("Error in WebUtils.isInLength:" + var4.getMessage());
        }

        return isValid;
    }

    public static boolean isPastDate(Date firstDate, Date lastDate) {
        boolean isValid = false;

        try {
            if (firstDate.before(lastDate)) {
                isValid = true;
            }
        } catch (Exception var4) {
            System.err.println("Error in WebUtils.isPastDate:" + var4.getMessage());
        }

        return isValid;
    }

    public static boolean isValidRegExpr(Object object, String regularExpression) {
        boolean isValid = false;

        try {
            CharSequence inputStr = (CharSequence) object;
            Pattern pattern = Pattern.compile(regularExpression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches()) {
                isValid = true;
            }
        } catch (Exception var7) {
            System.err.println("Error in WebUtils.isValid:" + var7.getMessage());
        }

        return isValid;
    }

    public static boolean isValidMD5(String s) {
        return s.matches("[a-fA-F0-9]{32}");
    }

    public static void callDialog(Context context, String phoneNo) {
        try {
            TextView textView = new TextView(context);
            textView.setGravity(17);
            textView.setText("Would u like to call?");
            textView.setPadding(10, 10, 10, 10);
            textView.setTextColor(-1);
            textView.setTextSize(18.0F);
            android.app.AlertDialog.Builder dialog = new android.app.AlertDialog.Builder(context);
            dialog.setTitle("");
            dialog.setView(textView);
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            dialog.show();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public static void emailDialog(Context context, String emailId) {
        Intent i = new Intent("android.intent.action.SEND");
        i.setType("text/html");
        i.putExtra("android.intent.extra.EMAIL", new String[]{emailId});
        i.putExtra("android.intent.extra.SUBJECT", "");
        i.putExtra("android.intent.extra.TEXT", "");

        try {
            context.startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (ActivityNotFoundException var4) {
            Toast.makeText(context, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    public static void smsDialog(Context context, String phNo) {
        try {
            context.startActivity(new Intent("android.intent.action.VIEW",
                    Uri.fromParts("sms", phNo, (String) null)));
        } catch (ActivityNotFoundException var3) {
            Toast.makeText(context, "There are no SMS clients installed.", Toast.LENGTH_SHORT).show();
        }

    }

    public static String hashMD5(String string) {
        byte[] hash;
        try {
            hash = MessageDigest.getInstance("MD5").digest(string.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException var7) {
            throw new RuntimeException("MD5 should be supported?", var7);
        }

        StringBuilder hex = new StringBuilder(hash.length * 2);
        byte[] var3 = hash;
        int var4 = hash.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            byte b = var3[var5];
            if ((b & 255) < 16) {
                hex.append("0");
            }

            hex.append(Integer.toHexString(b & 255));
        }

        return hex.toString();
    }

    public static long roundOff(double originalValue) {
        long tempValue = (long) originalValue;
        double decimalVal = originalValue - (double) tempValue;
        if (decimalVal > 0.0D) {
            ++tempValue;
        } else {
            tempValue = (long) originalValue;
        }

        return tempValue;
    }

    public static String getIMEINo(Context ctx) {
        TelephonyManager telephonyManager = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        @SuppressLint("MissingPermission") String deviceId = telephonyManager.getDeviceId();
        return deviceId;
    }

    public static String getAndroidId(Context context) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public static String getProperty(String key) {
        String propertiesPath = Environment.getExternalStorageDirectory() + "/KSMS/Properties/app.properties";
        String value = null;
        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream(propertiesPath);
            prop.load(fis);
            value = prop.getProperty(key);
            fis.close();
        } catch (Exception var5) {
            var5.printStackTrace();
        }

        return value;
    }

    public static void setProperty(String key, String value) {
        String propertiesPath = Environment.getExternalStorageDirectory() + "/KSMS/Properties/app.properties";
        File file = new File(propertiesPath);
        Properties prop = new Properties();

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            prop.load(new FileInputStream(propertiesPath));
            FileOutputStream fos = new FileOutputStream(propertiesPath);
            prop.setProperty(key, value);
            prop.store(fos, (String) null);
            fos.close();
        } catch (Exception var6) {
            var6.printStackTrace();
        }

    }

    public static String convertStringToUTF8(String s) {
        String out = null;

        try {
            out = URLEncoder.encode(s, "UTF-8");
            return out;
        } catch (UnsupportedEncodingException var3) {
            return null;
        }
    }

    public static Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public static File viewToJpgImageFile(View view, String folderName, String fileName) {
        FileOutputStream fOut = null;
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        File dir = new File(Environment.getExternalStorageDirectory().toString() + "/" + folderName);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName + ".jpg");
        if (file.exists()) {
            file.delete();
        }

        try {
            fOut = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            Log.w("Utils", "View Save As Images File =" + fileName);
        } catch (FileNotFoundException var9) {
            var9.printStackTrace();
            Log.e("Utils", "Fail To Save View As Image File");
        }

        return file;
    }

    public static int getAppVersion(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException var2) {
            throw new RuntimeException("Could not get package name: " + var2);
        }
    }

    public static String getAppVersionName(Context context) {
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException var2) {
            throw new RuntimeException("Could not get package name: " + var2);
        }
    }

    public static void downloadImageFromUrl(Context context, String imageUrl, String folderName, String fileName) {
        if (!isNullString(imageUrl)) {
            File dir = new File(Environment.getExternalStorageDirectory(), folderName);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            File file = new File(dir, fileName);
            if (file.exists()) {
                file.delete();
            }

            DownloadManager mgr = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Uri downloadUri = Uri.parse(imageUrl);
            DownloadManager.Request request = new DownloadManager.Request(downloadUri);
            request.setAllowedNetworkTypes(3).setAllowedOverRoaming(true).setTitle("Downloading app data").setDestinationInExternalPublicDir(folderName, fileName);
            mgr.enqueue(request);
        }

    }

    public static void showMessageGoToSettingsDialog(Context context, String message, DialogInterface.OnClickListener okListener) {
        (new AlertDialog.Builder(context)).setMessage(message).setPositiveButton("GO TO SETTINGS", okListener).create().show();
    }

    public static void notifyMediaScannerService(Context context, String path) {
        MediaScannerConnection.scanFile(context, new String[]{path}, (String[]) null, new MediaScannerConnection.OnScanCompletedListener() {
            public void onScanCompleted(String path, Uri uri) {
                Log.i("ExternalStorage", "Scanned " + path + ":");
                Log.i("ExternalStorage", "-> uri=" + uri);
            }
        });
    }

    public static void clearSharedPref(Context context) {
        SharedPreferences preferences = context.getSharedPreferences((String) null, 0);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.commit();
    }

    public static int hexToRgb(String hex) {
        int color = (int) Long.parseLong(hex.replace("#", ""), 16);
        int r = color >> 16 & 255;
        int g = color >> 8 & 255;
        int b = color >> 0 & 255;
        return Color.rgb(r, g, b);
    }

    public static String convertNumberToText(int number) {

        if (number == 0) {
            return "zero";
        }

        String snumber = Integer.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions) + " billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions) + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands) + " thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    public static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) return soFar;
        return numNames[number] + " hundred" + soFar;
    }

    public static Bitmap scaleBitmap(Bitmap bm, int maxWidth, int maxHeight) {
        int width = bm.getWidth();
        int height = bm.getHeight();
        int ratio;
        if (width > height) {
            ratio = width / maxWidth;
            width = maxWidth;
            if (ratio > 1) {
                height /= ratio;
            }
        } else if (height > width) {
            ratio = height / maxHeight;
            height = maxHeight;
            if (ratio > 1) {
                width /= ratio;
            }
        } else {
            height = maxHeight;
            width = maxWidth;
        }

        bm = Bitmap.createScaledBitmap(bm, width, height, true);
        return bm;
    }

    public static String getYoutubeVideoId(String youtubeVideoUrl) {
        String videoId = "";
        if (youtubeVideoUrl != null && youtubeVideoUrl.trim().length() > 0 && youtubeVideoUrl.startsWith("http")) {
            String expression = "^.*((youtu.be\\/)|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(youtubeVideoUrl);
            if (matcher.matches()) {
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11) {
                    videoId = groupIndex1;
                }
            }
        }

        return videoId;
    }

    public static void changeLanguage(Activity context, String languageId) {
        Prefs.putString(LANGUAGE_NAME, languageId);
        LocaleHelper.setLocale(context, languageId);
    }

    public static String getLanguageId(String languageName) {
        switch (languageName) {
            case LanguageConstants.ASSAMESE:
                return LanguageIDConstants.ASSAMESE;
            case LanguageConstants.BENGALI:
                return LanguageIDConstants.BENGALI;
            case LanguageConstants.BIHARI:
                return LanguageIDConstants.BIHARI;
            case LanguageConstants.BODO:
                return LanguageIDConstants.BODO;
            case LanguageConstants.GUJARATI:
                return LanguageIDConstants.GUJARATI;
            case LanguageConstants.HINDI:
                return LanguageIDConstants.HINDI;
            case LanguageConstants.KANADA:
                return LanguageIDConstants.KANADA;
            case LanguageConstants.KASHMIRI:
                return LanguageIDConstants.KASHMIRI;
            case LanguageConstants.KONKANI:
                return LanguageIDConstants.KONKANI;
            case LanguageConstants.MALAYALAM:
                return LanguageIDConstants.MALAYALAM;
            case LanguageConstants.MARATHI:
                return LanguageIDConstants.MARATHI;
            case LanguageConstants.NEPALI:
                return LanguageIDConstants.NEPALI;
            case LanguageConstants.ORIYA:
                return LanguageIDConstants.ORIYA;
            case LanguageConstants.PUNJABI:
                return LanguageIDConstants.PUNJABI;
            case LanguageConstants.SANSKRIT:
                return LanguageIDConstants.SANSKRIT;
            case LanguageConstants.TAMIL:
                return LanguageIDConstants.TAMIL;
            case LanguageConstants.TELUGU:
                return LanguageIDConstants.TELUGU;
            case LanguageConstants.TIBETAN:
                return LanguageIDConstants.TIBETAN;
            case LanguageConstants.URDU:
                return LanguageIDConstants.URDU;
            default:
                return LanguageIDConstants.ENGLISH;
        }
    }

    public static void hideSnackBar() {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }

    public static void showSnackBar(View parent) {
        mSnackbar = Snackbar.make(parent, "", Snackbar.LENGTH_INDEFINITE);
        Snackbar.SnackbarLayout v = (Snackbar.SnackbarLayout) mSnackbar.getView();
        View layout = LayoutInflater.from(currentContextConstant).inflate(R.layout.snackbar_custom_layout, null);
        v.addView(layout, 0);
        mSnackbar.show();
    }

    public static GenericRequestBuilder<Uri, InputStream, SVG, PictureDrawable> getGenericRequestBuilder
            (Context context, int placeholderImage, int errorImage) {
        return Glide.with(context)
                .using(Glide.buildStreamModelLoader(Uri.class, context), InputStream.class)
                .from(Uri.class)
                .as(SVG.class)
                .transcode(new SvgDrawableTranscoder(), PictureDrawable.class)
                .sourceEncoder(new StreamEncoder())
                .cacheDecoder(new FileToStreamDecoder<SVG>(new SvgDecoder()))
                .decoder(new SvgDecoder())
                .placeholder(placeholderImage)
                .error(errorImage)
                .animate(android.R.anim.fade_in)
                .listener(new SvgSoftwareLayerSetter<Uri>());
    }

    public static String getMimeType(String url) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(url);
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension);
        }
        return type;
    }

    public static long getDifferenceBetweenTime(Date first, Date second, @Nullable String returnUnit) {
        long difference = 0;

        try {

            if (returnUnit == null)
                returnUnit = UNITS.unit_min;

            difference = second.getTime() - first.getTime();

            switch (returnUnit) {
                case UNITS.unit_sec:
                    difference = difference / 1000;
                    break;
                case UNITS.unit_min:
                    difference = difference / 60000;
                    break;
                case UNITS.unit_hour:
                    difference = difference / 3600000;
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return difference;
    }

    public static HashMap<String, String> getLanguageHashMapList() {

        HashMap<String, String> languageHashMap = new HashMap<>();

        //English
        languageHashMap.put(LanguageConstants.ENGLISH, LanguageNameConstants.ENGLISH);
        languageHashMap.put(LanguageNameConstants.ENGLISH, LanguageConstants.ENGLISH);
        //ASSAMESE
        languageHashMap.put(LanguageConstants.ASSAMESE, LanguageNameConstants.ASSAMESE);
        languageHashMap.put(LanguageNameConstants.ASSAMESE, LanguageConstants.ASSAMESE);
        //BENGALI
        languageHashMap.put(LanguageConstants.BENGALI, LanguageNameConstants.BENGALI);
        languageHashMap.put(LanguageNameConstants.BENGALI, LanguageConstants.BENGALI);
        //BIHARI
        languageHashMap.put(LanguageConstants.BIHARI, LanguageNameConstants.BIHARI);
        languageHashMap.put(LanguageNameConstants.BIHARI, LanguageConstants.BIHARI);
        //BODO
        languageHashMap.put(LanguageConstants.BODO, LanguageNameConstants.BODO);
        languageHashMap.put(LanguageNameConstants.BODO, LanguageConstants.BODO);
        //GUJARATI
        languageHashMap.put(LanguageConstants.GUJARATI, LanguageNameConstants.GUJARATI);
        languageHashMap.put(LanguageNameConstants.GUJARATI, LanguageConstants.GUJARATI);
        //HINDI
        languageHashMap.put(LanguageConstants.HINDI, LanguageNameConstants.HINDI);
        languageHashMap.put(LanguageNameConstants.HINDI, LanguageConstants.HINDI);
        //KANADA
        languageHashMap.put(LanguageConstants.KANADA, LanguageNameConstants.KANADA);
        languageHashMap.put(LanguageNameConstants.KANADA, LanguageConstants.KANADA);
        //KASHMIRI
        languageHashMap.put(LanguageConstants.KASHMIRI, LanguageNameConstants.KASHMIRI);
        languageHashMap.put(LanguageNameConstants.KASHMIRI, LanguageConstants.KASHMIRI);
        //KONKANI
        languageHashMap.put(LanguageConstants.KONKANI, LanguageNameConstants.KONKANI);
        languageHashMap.put(LanguageNameConstants.KONKANI, LanguageConstants.KONKANI);
        //MALAYALAM
        languageHashMap.put(LanguageConstants.MALAYALAM, LanguageNameConstants.MALAYALAM);
        languageHashMap.put(LanguageNameConstants.MALAYALAM, LanguageConstants.MALAYALAM);
        //MARATHI
        languageHashMap.put(LanguageConstants.MARATHI, LanguageNameConstants.MARATHI);
        languageHashMap.put(LanguageNameConstants.MARATHI, LanguageConstants.MARATHI);
        //NEPALI
        languageHashMap.put(LanguageConstants.NEPALI, LanguageNameConstants.NEPALI);
        languageHashMap.put(LanguageNameConstants.NEPALI, LanguageConstants.NEPALI);
        //ORIYA
        languageHashMap.put(LanguageConstants.ORIYA, LanguageNameConstants.ORIYA);
        languageHashMap.put(LanguageNameConstants.ORIYA, LanguageConstants.ORIYA);
        //PUNJABI
        languageHashMap.put(LanguageConstants.PUNJABI, LanguageNameConstants.PUNJABI);
        languageHashMap.put(LanguageNameConstants.PUNJABI, LanguageConstants.PUNJABI);
        //SANSKRIT
        languageHashMap.put(LanguageConstants.SANSKRIT, LanguageNameConstants.SANSKRIT);
        languageHashMap.put(LanguageNameConstants.SANSKRIT, LanguageConstants.SANSKRIT);
        //TAMIL
        languageHashMap.put(LanguageConstants.TAMIL, LanguageNameConstants.TAMIL);
        languageHashMap.put(LanguageNameConstants.TAMIL, LanguageConstants.TAMIL);
        //TELUGU
        languageHashMap.put(LanguageConstants.TELUGU, LanguageNameConstants.TELUGU);
        languageHashMap.put(LanguageNameConstants.TELUGU, LanguageConstants.TELUGU);
        //TIBETAN
        languageHashMap.put(LanguageConstants.TIBETAN, LanguageNameConstants.TIBETAN);
        languageHashMap.put(LanguageNameConstants.TIBETAN, LanguageConstants.TIBETAN);
        //URDU
        languageHashMap.put(LanguageConstants.URDU, LanguageNameConstants.URDU);
        languageHashMap.put(LanguageNameConstants.URDU, LanguageConstants.URDU);

        return languageHashMap;
    }

    public static String getAndroidId() {
        return Settings.Secure.getString(currentContextConstant.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static double calculateLatLngDistance(double startLat, double startLng, double endLat, double endLng, String unit) {
        if (startLat == endLat && startLng == endLng) {
            return 0.0D;
        } else {
            double theta = startLng - endLng;
            double dist = Math.sin(Math.toRadians(startLat)) * Math.sin(Math.toRadians(endLat)) + Math.cos(Math.toRadians(startLat)) * Math.cos(Math.toRadians(endLat)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60.0D * 1.1515D;
            if (unit.equals(UNITS.KiloMeter)) {
                dist *= 1.609344D;
            } else if (unit.equals(UNITS.NauticalMiles)) {
                dist *= 0.8684D;
            }

            return dist;
        }
    }

    public static AlertDialog getAlertDialog(Context context, int layoutId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        AlertDialog alertDialog = builder.setCancelable(true)
                .setView(view)
                .create();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        return alertDialog;
    }

    public interface LanguageConstants {
        String ENGLISH = "en";
        String ASSAMESE = "as";
        String BENGALI = "bn";
        String BIHARI = "bh";
        String BODO = "brx";
        String GUJARATI = "gu";
        String HINDI = "hi";
        String KANADA = "kn";
        String KASHMIRI = "ks";
        String KONKANI = "kok";
        String MALAYALAM = "ml";
        String MARATHI = "mr";
        //        String MARATHI = "mr-rIN";
        String NEPALI = "ne";
        String ORIYA = "or";
        String PUNJABI = "pa";
        String SANSKRIT = "sa";
        String TAMIL = "ta";
        String TELUGU = "te";
        String TIBETAN = "bo";
        String URDU = "ur";
    }

    public interface LanguageNameConstants {
        String ENGLISH = "ENGLISH";
        String ASSAMESE = "অসমিয়া";
        String BENGALI = "বাংলা";
        String BIHARI = "बिहारी";
        String BODO = "बड़ो";
        String GUJARATI = "ગુજરતી";
        String HINDI = "हिंदी";
        String KANADA = "ಕೆನೆಡಾದ";
        String KASHMIRI = "कॉशुर";
        String KONKANI = "कोंकणी";
        String MALAYALAM = "മലയാളം";
        String MARATHI = "मराठी";
        String NEPALI = "नेपाली";
        String ORIYA = "ଓଡ଼ିଆ";
        String PUNJABI = "ਪੰਜਾਬੀ";
        String SANSKRIT = "संस्कृत";
        String TAMIL = "தமிழ்";
        String TELUGU = "తెలుగు";
        String TIBETAN = "བོད་ཡིག";
        String URDU = "اردو";
    }

    public interface LanguageIDConstants {
        String ENGLISH = "1";
        String ASSAMESE = "5";
        String BENGALI = "6";
        String BIHARI = "7";
        String BODO = "8";
        String GUJARATI = "9";
        String HINDI = "3";
        String KANADA = "10";
        String KASHMIRI = "11";
        String KONKANI = "12";
        String MALAYALAM = "13";
        String MARATHI = "2";
        String NEPALI = "14";
        String ORIYA = "15";
        String PUNJABI = "4";
        String SANSKRIT = "16";
        String TAMIL = "17";
        String TELUGU = "18";
        String TIBETAN = "19";
        String URDU = "20";
    }

    public interface FileExtentions {
        String XLS = ".xls";
        String XLSX = ".xls";
        String DOC = ".doc";
        String DOCX = ".docx";
        String PDF = ".pdf";
        String APK = ".apk";
        String JPG = ".jpg";
        String JPEG = ".jpeg";
        String GIF = ".gif";
        String PNG = ".png";
        String RAR = ".rar";
        String TXT = ".txt";
        String PPT = ".ppt";
        String PPTX = ".pptx";
        String ACC = ".acc";
        String MP3 = ".mp3";
        String AVI = ".avi";
        String MP4 = ".mp4";
        String RTF = ".rtf";
        String ZIP = ".zip";
        String KEY_FILTER_FILES_EXTENSIONS = "EXTENSIONS";
        String KEY_FILE_SELECTED = "FILE_SELECTED";
        String FOLDER = "Folder";
        String PARENT_FOLDER = "ParentDirectory";
    }

    public interface UNITS {
        String KiloMeter = "K";
        String NauticalMiles = "N";
        String Meter = "M";
        String unit_sec = "sec";
        String unit_min = "min";
        String unit_hour = "hr";
    }

    public static String getLanguageName(String languageId) {
        switch (languageId) {
            case LanguageConstants.ASSAMESE:
                return LanguageNameConstants.ASSAMESE;
            case LanguageConstants.BENGALI:
                return LanguageNameConstants.BENGALI;
            case LanguageConstants.BIHARI:
                return LanguageNameConstants.BIHARI;
            case LanguageConstants.BODO:
                return LanguageNameConstants.BODO;
            case LanguageConstants.GUJARATI:
                return LanguageNameConstants.GUJARATI;
            case LanguageConstants.HINDI:
                return LanguageNameConstants.HINDI;
            case LanguageConstants.KANADA:
                return LanguageNameConstants.KANADA;
            case LanguageConstants.KASHMIRI:
                return LanguageNameConstants.KASHMIRI;
            case LanguageConstants.KONKANI:
                return LanguageNameConstants.KONKANI;
            case LanguageConstants.MALAYALAM:
                return LanguageNameConstants.MALAYALAM;
            case LanguageConstants.MARATHI:
                return LanguageNameConstants.MARATHI;
            case LanguageConstants.NEPALI:
                return LanguageNameConstants.NEPALI;
            case LanguageConstants.ORIYA:
                return LanguageNameConstants.ORIYA;
            case LanguageConstants.PUNJABI:
                return LanguageNameConstants.PUNJABI;
            case LanguageConstants.SANSKRIT:
                return LanguageNameConstants.SANSKRIT;
            case LanguageConstants.TAMIL:
                return LanguageNameConstants.TAMIL;
            case LanguageConstants.TELUGU:
                return LanguageNameConstants.TELUGU;
            case LanguageConstants.TIBETAN:
                return LanguageNameConstants.TIBETAN;
            case LanguageConstants.URDU:
                return LanguageNameConstants.URDU;
            default:
                return LanguageNameConstants.ENGLISH;
        }
    }
}

