package com.appynitty.swachbharatabhiyanlibrary.activity;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.ImagePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.utils.LocaleHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.util.Calendar;
import java.util.Objects;


/******  Rahul Rokade 20/02/22 **************/
public class CommunityAndPublicToiletActivity extends AppCompatActivity {

    private final static String TAG = "CommunityAndPublicToiletActivity";
    private static final int REQUEST_CAMERA = 22;
    private static final int SELECT_FILE = 33;

    private static int imageViewNo = 0;


    private Context mContext;
    private Toolbar toolbarCtpt;

    private EditText comments;
    private ImageView beforeImage;
    private ImageView afterImage;
    private CardView openQR;
    private EditText edtToiletSeatsCount;
    private TextView txtDataTimeBefore, txtDateTimeAfter;

    private final String resumeFilePath = "";
    private String beforeImageFilePath = "";
    private String afterImageFilePath = "";

    private String dateTime = AUtils.getServerDate() + " " + AUtils.getServerTime();

    private ImagePojo imagePojo;

    @Override
    protected void attachBaseContext(Context newBase) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            super.attachBaseContext(LocaleHelper.onAttach(newBase));
        } else {
            super.attachBaseContext(newBase);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initComponents();
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
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_CAMERA) {
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
                isStoragePermissionGiven();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(CommunityAndPublicToiletActivity.this, Manifest.permission.CAMERA)) {

                AUtils.showPermissionDialog(mContext, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(CommunityAndPublicToiletActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AUtils.showPermissionDialog(mContext, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
            } else {
//                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_STORAGE) {
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
                isLocationPermissionGiven();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(CommunityAndPublicToiletActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AUtils.showPermissionDialog(mContext, "EXTERNAL STORAGE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, AUtils.MY_PERMISSIONS_REQUEST_STORAGE);
                        }
                    }
                });
            } else {
//                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }

        } else if (requestCode == AUtils.MY_PERMISSIONS_REQUEST_LOCATION) {
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
                checkGpsStatus();
            } else if (ActivityCompat.shouldShowRequestPermissionRationale(CommunityAndPublicToiletActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                AUtils.showPermissionDialog(mContext, "Location Service", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, AUtils.MY_PERMISSIONS_REQUEST_LOCATION);
                        }
                    }
                });
            } else {
//                Toast.makeText(getActivity(), "Unable to get Permission", Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        if (AUtils.isInternetAvailable()) {
            AUtils.hideSnackBar();
        } else {
            AUtils.showSnackBar(findViewById(R.id.parent));
        }
    }

    private void initComponents() {
        generateId();
        registerEvents();
        initData();
    }

    private void generateId() {

        setContentView(R.layout.activity_take_ctpt_photo);
        toolbarCtpt = findViewById(R.id.toolbar_ctpt);
        edtToiletSeatsCount = findViewById(R.id.edt_numberOf_seats);

        mContext = CommunityAndPublicToiletActivity.this;
        AUtils.currentContextConstant = mContext;

        beforeImage = findViewById(R.id.img_before_photo_ctpt);
        afterImage = findViewById(R.id.img_after_photo_ctpt);
        txtDateTimeAfter = findViewById(R.id.txt_date_time_after);
        txtDataTimeBefore = findViewById(R.id.txt_date_time_before);

        comments = findViewById(R.id.txt_comments_ctpt);

        openQR = findViewById(R.id.open_qr_ctpt);
        String dateTime = AUtils.getServerDateTimeLocal();
        Log.e("CTPT Toilet Activity time", dateTime);

        initToolbar();
    }

    private void registerEvents() {
        beforeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewNo = 1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                    txtDataTimeBefore.setText(dateTime);
                } else {
                    // do something for phones running an SDK before marshmallow
                    checkGpsStatus();
                }

            }
        });

        afterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                imageViewNo = 2;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                    txtDateTimeAfter.setText(dateTime);
                } else {
                    // do something for phones running an SDK before marshmallow
                    checkGpsStatus();
                }

            }
        });

        openQR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openQRClicked();
            }
        });

        comments.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (comments.hasFocus()) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);
                    switch (event.getAction() & MotionEvent.ACTION_MASK) {
                        case MotionEvent.ACTION_SCROLL:
                            v.getParent().requestDisallowInterceptTouchEvent(false);
                            return true;
                    }
                }
                return false;
            }
        });

    }

    private void initData() {

        Type type = new TypeToken<ImagePojo>() {
        }.getType();

        imagePojo = new Gson().fromJson(
                Prefs.getString(AUtils.PREFS.IMAGE_POJO, null), type);

        if (Prefs.contains(AUtils.BEFORE_IMAGE)) {
            Bitmap myBitmap = BitmapFactory.decodeFile(Prefs.getString(AUtils.BEFORE_IMAGE, null));
            Log.e(TAG, "initData: before Image:- " + Prefs.getString(AUtils.BEFORE_IMAGE, null));
            beforeImage.setImageBitmap(myBitmap);
        }

    }

    private void openQRClicked() {

        if (validateForm()) {

            if (getFormData()) {

                startActivity(new Intent(CommunityAndPublicToiletActivity.this,
                        QRcodeScannerCtptActivity.class).putExtra(AUtils.REQUEST_CODE, AUtils.MY_RESULT_REQUEST_QR).putExtra("CTPT", AUtils.CTPT_GC_TYPE));
                CommunityAndPublicToiletActivity.this.finish();
            }
        }
    }

    private void initToolbar() {
        toolbarCtpt.setTitle(R.string.string_ctpt_heading);
        setSupportActionBar(toolbarCtpt);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        //autoRefresh();
    }

    private void isCameraPermissionGiven() {
        if (AUtils.isCameraPermissionGiven(mContext)) {
            //You already have the permission, just go ahead.
            isStoragePermissionGiven();
        }
    }

    private void isStoragePermissionGiven() {
        if (AUtils.isStoragePermissionGiven(mContext)) {
            //You already have the permission, just go ahead.
            isLocationPermissionGiven();
        }
    }

    private void isLocationPermissionGiven() {
        if (AUtils.isLocationPermissionGiven(mContext)) {
            //You already have the permission, just go ahead.
            checkGpsStatus();
        }
    }

    private void checkGpsStatus() {

        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        boolean GpsStatus = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (GpsStatus) {

            takePhotoImageViewOnClick();
        } else {

            startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
        }
    }

    private void takePhotoImageViewOnClick() {

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_CAMERA);

    }

    private void onCaptureImageResult(Intent data) {

        File destinationT = null;
        Bitmap thumbnailT = (Bitmap) data.getExtras().get("data");
        try {

            File dir = new File(
                    getExternalFilesDir(null).getAbsolutePath()
                            + "/Gram Panchayat");

            if (!dir.exists()) {
                dir.mkdirs();
            }
            destinationT = new File(dir, System.currentTimeMillis() + ".jpg");

            FileOutputStream fOut = new FileOutputStream(destinationT);
            thumbnailT.compress(Bitmap.CompressFormat.PNG, 100, fOut);

        } catch (Exception e) {

            e.printStackTrace();
            AUtils.error(mContext, "Unable to add image", Toast.LENGTH_SHORT);
        }

        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        File destination;

        OutputStream fos;
        try {

            final File dir;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {  //added by swapnil for android version 10 and above

                ContentResolver resolver = mContext.getContentResolver();
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, System.currentTimeMillis() + ".jpg");
                contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + "/SBA Thane");
                Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                fos = resolver.openOutputStream(imageUri);
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fos);
                destination = new File(String.valueOf(contentValues), System.currentTimeMillis() + ".jpg");
                Log.e(TAG, "onCaptureImageResult: thumbnail" + thumbnail);

            } else {

                dir = new File(Environment.getExternalStoragePublicDirectory(
                        Environment.DIRECTORY_DCIM), "Gram Panchayat");

                if (!dir.exists()) {
                    dir.mkdirs();
                }
                destination = new File(dir, System.currentTimeMillis() + ".jpg");
                fos = new FileOutputStream(destination);
                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fos);
                Log.e(TAG, "onCaptureImageResult: thumbnail" + thumbnail);
            }

            fos.flush();
            fos.close();

        } catch (Exception e) {

            e.printStackTrace();

            AUtils.error(mContext, "Unable to add image", Toast.LENGTH_SHORT);

        }

        switch (imageViewNo) {

            case 1:
                Uri tempUri;
                String finalPath;

                beforeImage.setImageBitmap(thumbnail);
                tempUri = getImageUri(getApplicationContext(), thumbnail);

                finalPath = getRealPathFromURI(tempUri);
                beforeImageFilePath = finalPath; //setting image1 path that will be set in imageDTO.

                Prefs.putString(AUtils.BEFORE_IMAGE, beforeImageFilePath);
                Prefs.putString(AUtils.BEFORE_IMAGE_TIME, AUtils.getServerDateTimeLocal());

                break;
            case 2:
                afterImage.setImageBitmap(thumbnail);
                tempUri = getImageUri(getApplicationContext(), thumbnail);

                finalPath = getRealPathFromURI(tempUri);
                afterImageFilePath = finalPath; //setting image2 path that will be set in imageDTO.

                Prefs.putString(AUtils.AFTER_IMAGE, afterImageFilePath);


                afterImage.setImageBitmap(thumbnailT);
                afterImageFilePath = destinationT.getAbsolutePath();
                break;

        }

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
    }

    private boolean validateForm() {

        if (AUtils.isNullString(afterImageFilePath)) {
            AUtils.warning(mContext, mContext.getString(R.string.plz_capture_after_img), Toast.LENGTH_SHORT);
            return false;
        }


        return true;
    }

    private boolean getFormData() {

        imagePojo = new ImagePojo();

        if (!AUtils.isNullString(beforeImageFilePath)) {
            imagePojo.setImage1(beforeImageFilePath);
            imagePojo.setBeforeImage("Before");
        }
        if (!AUtils.isNullString(afterImageFilePath)) {
            imagePojo.setImage2(afterImageFilePath);
            imagePojo.setAfterImage("After");
        }

        if (!AUtils.isNullString(comments.getText().toString())) {
            imagePojo.setComment(comments.getText().toString());
            Log.e(TAG, "ImageComment: " + imagePojo.getComment());
        }
        if (!AUtils.isNullString(edtToiletSeatsCount.getText().toString())) {
            imagePojo.setTNS(edtToiletSeatsCount.getText().toString());
            Log.e(TAG, "Toilet Seats Count: " + imagePojo.getTNS());
        }


        if (!AUtils.isNull(imagePojo)) {

            Type type = new TypeToken<ImagePojo>() {
            }.getType();
            Prefs.putString(AUtils.PREFS.IMAGE_POJO, new Gson().toJson(imagePojo, type));

            return true;
        } else {
            return false;
        }
    }

}