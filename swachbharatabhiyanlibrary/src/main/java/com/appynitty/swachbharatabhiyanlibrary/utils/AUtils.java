package com.appynitty.swachbharatabhiyanlibrary.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageDecoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.activity.DashboardActivity;
import com.appynitty.swachbharatabhiyanlibrary.activity.EmpDashboardActivity;
import com.appynitty.swachbharatabhiyanlibrary.activity.WasteDashboardActivity;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.EmpSyncServerAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.ShareLocationAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.SyncServerAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.entity.LastLocationEntity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LanguagePojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.LastLocationRepository;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.CommonUtils;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class AUtils extends CommonUtils {

    //    Local URL
//    public static final String SERVER_URL = "http://192.168.200.4:6077/";
//    public static final String SERVER_URL = "http://192.168.200.3:6560/";
//    public static final String SERVER_URL = "http://192.168.200.3:7075/";


    //  Advanced Ghanta Gadi URL
//    public static final String SERVER_URL = "http://202.65.157.253:6560";
    //ICTSBM Thane Live
    public static final String SERVER_URL = "http://202.65.157.253:5045/";

    //    Staging URL
//    public static final String SERVER_URL = "http://115.115.153.117:4044/";
//     public static final String SERVER_URL = "http://202.65.157.254:7075";  //Thane staging

    //Relese URL
//    public static final String SERVER_URL = "https://ghantagadi.in:444/";

    //    Relese Backup URL
//    public static final String SERVER_URL = "http://202.65.157.253:4044/";

    //Testingserver
//    public static final String SERVER_URL = "http://202.65.157.254:4055/";


    //    General Constant
    public static final String STATUS_SUCCESS = "success";

    public static final String STATUS_ERROR = "error";

    public static final String CONTENT_TYPE = "application/json";

    public static final String APP_ID = "AppId";
    public static final String VERSION_CODE = "AppVersion";

    public static final String DIALOG_TYPE_VEHICLE = "DialogTypeVehicle";
    public static final String DIALOG_TYPE_LANGUAGE = "Dialog_Type_Language";

    public static final String DEFAULT_LANGUAGE_ID = LanguageConstants.ENGLISH;

    public static final int SPLASH_SCREEN_TIME = 3000;

    public static final int MY_RESULT_REQUEST_QR = 55;

    public static final int HORT_GC_TYPE = 7;
    public static final int CD_GC_TYPE = 6;
    public static final int CTPT_GC_TYPE = 10;

    public static final String REQUEST_CODE = "RequestCode";

    public static final String VEHICLE_ID = "VehicleId";

    public static final String LAT = "Lat";
    public static final String LONG = "Long";

    public static final int LOCATION_INTERVAL = 1000 * 3;
    //    public static final int LOCATION_INTERVAL = 1000 * 60 * 10; //10 Minute
    public static final int FASTEST_LOCATION_INTERVAL = 5000;

    public static final String HISTORY_DETAILS_DATE = "HistoryDetailsDate";
    public static final String RADIO_SELECTED_HP = "house_point";
    public static final String RADIO_SELECTED_LW = "liquid_point";
    public static final String RADIO_SELECTED_SW = "street_point";
    public static final String RADIO_SELECTED_CP = "commercial_point";
    public static final String RADIO_SELECTED_GP = "garbage_point";
    public static final String RADIO_SELECTED_DY = "garbage_dump_yard";

    public static final String isFromLogin = "isFromLogin";
    public static final String dumpYardId = "dumpYardId";
    public static final String slwmId = "slwmId";

    public static final String SERVER_DATE_FORMATE = "MM-dd-yyyy";
    public static final String SERVER_DATE_FORMATE_LOCAL = "yyyy-MM-dd";
    public static final String EMP_TYPE = "EmpType";
    private static final String EMP_SERVER_DATE_FORMATE = "dd-MM-yyyy";

    private static final String TITLE_DATE_FORMATE = "dd MMM yyyy";

    private static final String SEMI_MONTH_FORMATE = "MMM";
    private static final String DATE_VALUE_FORMATE = "dd";

    private static final String SERVER_TIME_FORMATE = "hh:mm a";
    private static final String SERVER_TIME_24HR_FORMATE = "HH:mm";

    public static final String SERVER_DATE_TIME_FORMATE = "MM-dd-yyyy HH:mm:ss";
    public static final String SERVER_DATE_TIME_FORMATE_LOCAL = "yyyy-MM-dd HH:mm:ss.SSS";

    public static final long LOCATION_INTERVAL_MINUTES = 10 * 60 * 1000;

    public static final String VEHICLE_NO = "VehicleNumber";

    private static final String TAG = "AUtils";

    public static final String HP_AREA_TYPE_ID = "1";

    public static final String GP_AREA_TYPE_ID = "2";

    public static final String DY_AREA_TYPE_ID = "3";

    public static final String CP_AREA_TYPE_ID = "4";

    public final static String CONNECTIVITY_ACTION = "android.net.conn.CONNECTIVITY_CHANGE";

    public static final String TAG_HTTP_RESPONSE = "HTTPResponse_Error";
    public static final String ADD_DETAILS_TYPE_KEY = "ADD_DETAILS_TYPE_KEY";
    public static final int ADD_DETAILS_REQUEST_KEY = 8986;

    public static final String DATABASE_NAME = "db_sba_offline";
    public static final String LOCATION_TABLE_NAME = "table_location";
    public static final String COLLECTION_TABLE_NAME = "table_gcollection";
    public static final String QR_TABLE_NAME = "table_qr_emp";
    public static final String OFFLINE_HOUSE_CTYPE = "offlineHouse_cType";
    public static final String DRY_IMAGE = "image1";
    public static final String WET_IMAGE = "image2";

    private static SyncServerAdapterClass syncServer;
    private static ShareLocationAdapterClass shareLocationAdapterClass;
    private static EmpSyncServerAdapterClass empSyncServer;

    public static boolean isSyncServerRequestEnable = false;
    public static boolean isLocationRequestEnable = false;
    public static boolean isEmpSyncServerRequestEnable = false;
    public static boolean isSyncOfflineDataRequestEnable = false;
    public static boolean isSyncOfflineWasteManagementDataRequestEnable = false;

    private static ArrayList<LanguagePojo> languagePojoList;

    public static boolean DutyOffFromService = false;

    public static boolean isIsOnduty() {
        return Prefs.getBoolean(PREFS.IS_ON_DUTY, false);
    }

    public static void setIsOnduty(boolean isOnduty) {
        Prefs.putBoolean(PREFS.IS_ON_DUTY, isOnduty);
    }

    public static void setEmpType(String empType) {
        Prefs.putString(PREFS.EMPLOYEE_TYPE, empType);
    }

    public interface PREFS {

        //    Save Data Constant
        String IS_USER_LOGIN = "UserLoginStatus";
        String USER_ID = "UserId";
        String USER_TYPE = "UserType";
        String USER_TYPE_ID = "UserTypeId";
        String IS_GT_FEATURE = "isGtFeature";
        String IS_ON_DUTY = "isOnDuty";

        String IN_PUNCH_DATE = "IN_PUNCH_DATE";

        String IMAGE_POJO = "ImagePojo";

        String IN_PUNCH_POJO = "InPunchPull";
        String VEHICLE_TYPE_POJO_LIST = "VehicleTypePullList";
        String USER_DETAIL_POJO = "UserDetailPull";
        String WORK_HISTORY_POJO_LIST = "WorkHistoryPullList";
        String WORK_HISTORY_DETAIL_POJO_LIST = "WorkHistoryDetailPullList";
        String LANGUAGE_POJO_LIST = "LanguagePullList";

        String EMPLOYEE_TYPE = "EmpType";
        String CTYPE = "CType";
        String HOUSE_COUNT = "HouseCount";
    }

    public interface USER_TYPE {
        String USER_TYPE_GHANTA_GADI = "0";
        String USER_TYPE_EMP_SCANNIFY = "1";
        String USER_TYPE_WASTE_MANAGER = "2";
    }

    public interface NondaniLocation {
        String OPEN_FORM_TYPE = "OPEN_FORM_TYPE";
        String SUBMIT_TYPE = "SUBMIT_TYPE";
        String REFERENCE_ID = "REFERENCE_ID";
    }

    public interface DUMPDATA {
        String dumpDataMap = "dump_data_map";
        String dumpYardId = "dump_yard_id";
        String weightTotal = "total_weight";
        String weightTotalDry = "total_weight_dry";
        String weightTotalWet = "total_weight_wet";
    }

    public interface SLWMDATA {
        String slwmDataMap = "slwm_data_map";
        String slwmId = "slwm_id";
        String slwmGarbageType = "slwm_GarbageType";
        String slwmSegregationlvl = "slwm_segregation_lvl";
        String slwmTor = "slwm_tor";
        String weightTotal = "total_weight";
        String weightTotalDry = "total_weight_dry";
        String weightTotalWet = "total_weight_wet";
    }

//    public static void saveLocation(Location location) {
//        if (!AUtils.isNull(location)) {
//            double latti = location.getLatitude();
//            double longi = location.getLongitude();
//
//            Prefs.putString(AUtils.LONG, Double.toString(longi));
//            Prefs.putString(AUtils.LAT, Double.toString(latti));
//        }
//    }

    public static String getServerDate() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getLocalDate() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateLocal(String date) {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        try {
            return format.format(format.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return format.format(new Date());
    }

    public static String serverDateFromLocal(String date) {

        SimpleDateFormat local = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE_LOCAL, Locale.ENGLISH);
        SimpleDateFormat server = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        try {
            return server.format(local.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server.format(new Date());
    }

    public static String getServerTime() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_TIME_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTime() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTimeWithMilliesSecond() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static String getServerDateTimeLocal() {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        return format.format(Calendar.getInstance().getTime());
    }

    public static Date parse(String date, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.ENGLISH);
        try {
            return simpleDateFormat.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new Date();
    }

    public static String getTitleDateFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(TITLE_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractDate(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(DATE_VALUE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractMonth(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(SEMI_MONTH_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatDate(String date, String fromFormat, String toFormat) {

        SimpleDateFormat providedFormat = new SimpleDateFormat(fromFormat, Locale.ENGLISH);
        SimpleDateFormat requiredFormat = new SimpleDateFormat(toFormat, Locale.ENGLISH);

        try {
            return requiredFormat.format(providedFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getEmpTimeLineFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(SERVER_TIME_24HR_FORMATE, Locale.ENGLISH);
        SimpleDateFormat timelineFormat = new SimpleDateFormat(SERVER_TIME_FORMATE, Locale.ENGLISH);

        try {
            return timelineFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getTitleDateFormatEmp(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(TITLE_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String getHistoryDetailsDateFormat(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(SERVER_DATE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractDateEmp(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(DATE_VALUE_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String extractMonthEmp(String date) {

        SimpleDateFormat serverFormat = new SimpleDateFormat(EMP_SERVER_DATE_FORMATE, Locale.ENGLISH);
        SimpleDateFormat titleDateFormat = new SimpleDateFormat(SEMI_MONTH_FORMATE, Locale.ENGLISH);

        try {
            return titleDateFormat.format(serverFormat.parse(date));
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void showDialog(Context context, @Nullable String Title, @Nullable String Message, DialogInterface.OnClickListener
            positiveListener, DialogInterface.OnClickListener negativeLisner) {

        String positiveText = context.getResources().getString(R.string.yes_txt);
        String negativeText = context.getResources().getString(R.string.no_txt);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(false).setPositiveButton(positiveText, positiveListener).setNegativeButton(negativeText, negativeLisner);

        if (!AUtils.isNull(Title)) {
            builder.setTitle(Title);
        }

        if (!AUtils.isNull(Message)) {
            builder.setMessage(Message);
        }

        builder.create().show();
    }

    public static void showDialog(Context context, @Nullable String Title, @Nullable String Message, DialogInterface.OnClickListener positiveListener) {

        String positiveText = context.getResources().getString(R.string.ok_txt);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        if (!AUtils.isNull(Title)) {
            builder.setTitle(Title);
        }

        if (!AUtils.isNull(Message)) {
            builder.setMessage(Message);
        }

        builder.setCancelable(false).setPositiveButton(positiveText, positiveListener).create().show();
    }

    public static void hideSnackBar() {
        if (mSnackbar != null && mSnackbar.isShown()) {
            mSnackbar.dismiss();
        }
    }

    public static SQLiteDatabase sqlDBInstance(Context mContext) {

        SbaDatabase databaseHelper = new SbaDatabase(mContext);

        return databaseHelper.getWritableDatabase();
    }

    public static Calendar getDutyEndTime() {
        Calendar cl = Calendar.getInstance();

        cl.set(Calendar.HOUR_OF_DAY, 23);
        cl.set(Calendar.MINUTE, 50);
        cl.set(Calendar.SECOND, 0);

        return cl;
    }

    public static Calendar getCurrentTime() {
        Calendar now = Calendar.getInstance();

        return now;
    }

    public static void setInPunchDate(Calendar calendar) {

        Prefs.putString(PREFS.IN_PUNCH_DATE, getServerDate(calendar));
    }

    public static String getInPunchDate() {

        return Prefs.getString(PREFS.IN_PUNCH_DATE, getServerDate(Calendar.getInstance()));
    }

    public static void removeInPunchDate() {

        Prefs.remove(PREFS.IN_PUNCH_DATE);
    }

    public static String getServerDate(Calendar calendar) {

        SimpleDateFormat format = new SimpleDateFormat(AUtils.SERVER_DATE_FORMATE, Locale.ENGLISH);
        return format.format(calendar.getTime());
    }

    public static AlertDialog getUploadingAlertDialog(Context context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        return builder.setView(R.layout.layout_progress_bar).setCancelable(false).create();
    }

    public static double calculateDistance(Context context, double newlat, double newLng) {
        LastLocationRepository lastLocationRepository = new LastLocationRepository(context);
        double startlat = 0d;
        double startLng = 0d;
        HashMap<String, Double> map = lastLocationRepository.getLastLocationCoordinates(AUtils.getLocalDate());
        if (!AUtils.isNull(map) && map.containsKey(LastLocationRepository.LatitudeKey) && map.containsKey(LastLocationRepository.LongitudeKey)) {
            startlat = map.get(LastLocationRepository.LatitudeKey);
            startLng = map.get(LastLocationRepository.LongitudeKey);
        }

        LastLocationEntity entity = new LastLocationEntity();
        entity.setColumnDate(AUtils.getServerDateTimeLocal());
        entity.setColumnLattitude(String.valueOf(newlat));
        entity.setColumnLongitude(String.valueOf(newLng));
        lastLocationRepository.insertUpdateLastLocation(entity);
        if (startlat != 0d && startLng != 0d)
            return calculateLatLngDistance(startlat, startLng, newlat, newLng, UNITS.KiloMeter);

        return 0d;
    }

    public static String setLanguage(String language) {
        switch (language) {
            case AUtils.LanguageNameConstants.ENGLISH:
                return AUtils.LanguageConstants.ENGLISH;
            case AUtils.LanguageNameConstants.MARATHI:
                return AUtils.LanguageConstants.MARATHI;
            case AUtils.LanguageNameConstants.HINDI:
                return AUtils.LanguageConstants.HINDI;
        }

        return Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID);
    }

    /*
    //added by rahul
    public static GeoPoint getLocationFromAddress(String strAddress) {

        Geocoder coder = new Geocoder(this);
        List<Address> address;
        GeoPoint p1 = null;

        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new GeoPoint((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));

            return p1;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }*/

    public static ArrayList<LanguagePojo> getLanguagePojoList() {

        if (AUtils.isNull(languagePojoList)) {

            languagePojoList = new ArrayList<>();

            LanguagePojo eng = new LanguagePojo();
            eng.setLanguage(AUtils.LanguageNameConstants.ENGLISH);
            eng.setLanguageId(AUtils.LanguageIDConstants.ENGLISH);
            languagePojoList.add(eng);

            LanguagePojo mar = new LanguagePojo();
            mar.setLanguageId(AUtils.LanguageIDConstants.MARATHI);
            mar.setLanguage(AUtils.LanguageNameConstants.MARATHI);
            languagePojoList.add(mar);

            LanguagePojo hi = new LanguagePojo();
            hi.setLanguageId(AUtils.LanguageIDConstants.HINDI);
            hi.setLanguage(AUtils.LanguageNameConstants.HINDI);
            languagePojoList.add(hi);

        }

        return languagePojoList;
    }

    public static void setLanguagePojoList(ArrayList<LanguagePojo> languagePojoList) {
        AUtils.languagePojoList = languagePojoList;
    }

    public static String getPreviousDateDutyOffTime() {

        DateFormat dateFormat = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 50);
        cal.set(Calendar.SECOND, 0);

        return dateFormat.format(cal.getTime());
    }

    public static String getCurrentDateDutyOffTime() {

        DateFormat dateFormat = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);

        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 50);
        cal.set(Calendar.SECOND, 0);

        return dateFormat.format(cal.getTime());
    }

    public static String serverTimeFromLocal(String date) {

        SimpleDateFormat local = new SimpleDateFormat(AUtils.SERVER_DATE_TIME_FORMATE_LOCAL, Locale.ENGLISH);
        SimpleDateFormat server = new SimpleDateFormat(AUtils.SERVER_TIME_FORMATE, Locale.ENGLISH);
        try {
            return server.format(local.parse(date));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return server.format(new Date());
    }

    public static Class<?> getDashboardClass(String userType) {
        switch (userType) {
            case AUtils.USER_TYPE.USER_TYPE_WASTE_MANAGER:
                return WasteDashboardActivity.class;
            case AUtils.USER_TYPE.USER_TYPE_EMP_SCANNIFY:
                return EmpDashboardActivity.class;
            case AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI:
            default:
                return DashboardActivity.class;
        }
    }


    /**
     * Encoded Image
     */


    @Nullable
    public static Bitmap getImageBitmap(String _sourcePath, Context _context) throws Resources.NotFoundException {
        Bitmap bitmap = null;
        if (_sourcePath != null) {

            File file = new File(_sourcePath);
            if (file.exists()) {
                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            }

            Uri uri = Uri.fromFile(file);
            Log.d(TAG, "getImageBitmap: " + uri);
            try {
                bitmap = MediaStore.Images.Media.getBitmap(_context.getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            bitmap= BitmapFactory.decodeFile(_sourcePath);
        } else {
            throw new Resources.NotFoundException("Invalid Source Path");
        }
        return bitmap;
    }

    @Nullable
    public Bitmap getImageSourcePath(String _sourcePath, Context _context) throws Resources.NotFoundException, IOException {
        File file = null;
        Bitmap bitmap = null;

        if (_sourcePath != null) {
            file = new File(_sourcePath);
//             if(file.exists()){
//
//                 BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//                      bitmap = BitmapFactory.decodeFile(file.getPath(), bmOptions);
//                     bitmap = Bitmap.createScaledBitmap(bitmap, 100, 100, true);
//
//             }


            Uri uri = Uri.fromFile(file);

            Log.d(TAG, "getImageSourcePath: " + _sourcePath);
//            Log.d(TAG, "getImageSourcePath: "+uri);
//            content://com.appynitty.basepta.fileProvider/external_files/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1830485390469829670621400.jpg
//            content://com.appynitty.basepta.fileProvider/external_files/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1830485390469829670621400.jpg


//            if (file.exists()) {
//                bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
//            }

//            /storage/emulated/0/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1814219021659496099062004.jpg
//             /storage/emulated/0/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1814219021659496099062004.jpg
//              /storage/emulated/0/Android/data/com.appynitty.basepta/files/Pictures/image_20201218_1814219021659496099062004.jpg

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                try {
                    bitmap = ImageDecoder.decodeBitmap(ImageDecoder.createSource(_context.getContentResolver(), uri));

                } catch (IOException e) {
                    e.printStackTrace();
                    MDToast.makeText(_context, e.getMessage());
                }
            } else {
                bitmap = MediaStore.Images.Media.getBitmap(_context.getContentResolver(), uri);
            }
        } else if (_sourcePath == null) {
            throw new Resources.NotFoundException("Invalid Source Path");
        }
        Log.d(TAG, "getImageSourcePath:  Bitmap " + file);
        return bitmap;
    }


    @Nullable
    public static String getEncodedImage(String _sourcePath, Context _context) throws IOException, Resources.NotFoundException {
        String encoded = null;
        if (TextUtils.isEmpty(_sourcePath)) {
            return "";
        }
        Bitmap bitmap = getImageBitmap(_sourcePath, _context);
        if (bitmap != null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
            byte[] array = byteArrayOutputStream.toByteArray();
            String encode = Base64.encodeToString(array, Base64.DEFAULT);
            encoded = String.format("data:image/jpg;Base64,%s", encode);
            byteArrayOutputStream.flush();
            byteArrayOutputStream.close();
        }

        Log.d(TAG, "getEncodedImage: " + encoded);
        return encoded;

    }

    public static boolean isNetworkAvailable(Context ctx) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /******** device unique id Added by Rahul  ***********/
    public static String getDeviceUniqueID(Context context) {
        String device_unique_id = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        return device_unique_id;
    }

    /******** IMEI (international mobile equipment identifier) Added by Rahul  ***********/
    @SuppressLint("HardwareIds")
    public static String getIMEI(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }

}

