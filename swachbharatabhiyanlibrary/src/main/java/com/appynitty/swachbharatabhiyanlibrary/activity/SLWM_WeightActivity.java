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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by Swapnil Lanjewar 23-02-22
 */
public class SLWM_WeightActivity extends AppCompatActivity {

    private static final String TAG = "SLWM_WeightActivity";
    RadioButton radioButtonDryKg, radioButtonWetKg, radioButtonDryTon, radioButtonWetTon;
    RadioGroup radioGroupDry, radioGroupWet;
    Button btnSubmitSlwmDetails;
    ImageView btnTakeDryPhoto, btnTakeWetPhoto;
    Intent intent;
    EditText editDryTotal, editWetTotal;
    TextView tvSlwm_id, tvEditTotal;
    private String dryImageFilePath = "";
    private String wetImageFilePath = "";
    private static int imageViewNo = 0;
    private ImagePojo imagePojo;
    private Double totalTon, dryTon, wetTon;
    private static final int REQUEST_CAMERA = 23;
    String strSlwmid, strGarbageType, strSegregationlvl, strTor;
    Context mContext;
    Toolbar toolbar;


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
        setContentView(R.layout.activity_slwm_weight);

        initViews();
        initToolbar();
        initEvents();
        initData();

    }

    private void initData() {

        if (intent.hasExtra(AUtils.slwmId)) {
            strSlwmid = intent.getStringExtra(AUtils.slwmId);
        }
        tvSlwm_id.setText(strSlwmid);

        if (Prefs.contains(AUtils.DRY_IMAGE)) {
            Bitmap myBitmap = BitmapFactory.decodeFile(Prefs.getString(AUtils.DRY_IMAGE, null));
            btnTakeDryPhoto.setImageBitmap(myBitmap);
        }

        if (Prefs.contains(AUtils.WET_IMAGE)) {
            Bitmap myBitmap = BitmapFactory.decodeFile(Prefs.getString(AUtils.WET_IMAGE, null));
            btnTakeWetPhoto.setImageBitmap(myBitmap);
        }
    }

    private void initEvents() {
        editDryTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(".")) {
                    editDryTotal.setText("0.");
                    editDryTotal.setSelection(2);
                } else
                    calculateTotalWeight();
            }
        });

        editWetTotal.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().equals(".")) {
                    editWetTotal.setText("0.");
                    editWetTotal.setSelection(2);
                } else
                    calculateTotalWeight();
            }
        });

        radioGroupDry.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculateTotalWeight();
            }
        });

        radioGroupWet.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                calculateTotalWeight();
            }
        });

        btnSubmitSlwmDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateForm()) {
                    submitDumpDetailData();
                }
            }
        });

        btnTakeDryPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewNo = 1;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                } else {
                    // do something for phones running an SDK before marshmallow
                    checkGpsStatus();
                }
            }
        });

        btnTakeWetPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageViewNo = 2;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    // Do something for marshmallow and above versions
                    isCameraPermissionGiven();
                } else {
                    // do something for phones running an SDK before marshmallow
                    checkGpsStatus();
                }
            }
        });
    }

    private void calculateTotalWeight() {
        double total = 0;

        double dryInKgs = getDryWeightInKgs();
        double wetInKgs = getWetWeightInKgs();

        total = dryInKgs + wetInKgs;

        tvEditTotal.setText(String.format(Locale.ENGLISH, getString(R.string.restrict_two_decimal), total));

        dryTon = getWeightInTons(dryInKgs);
        wetTon = getWeightInTons(wetInKgs);

        totalTon = getWeightInTons(total);
    }

    private double getWeightInTons(double weightInKg) {
        double returnValue = 0f;
        if (weightInKg != 0) {
            returnValue = weightInKg / 1000;
        }

        return returnValue;
    }

    private double getDryWeightInKgs() {
        double returnValue = 0f;
        String dryWt = editDryTotal.getText().toString();
        if (!AUtils.isNull(dryWt)) {
            if (radioButtonDryKg.isChecked()) {
                returnValue = Double.parseDouble(dryWt);
            } else if (radioButtonDryTon.isChecked()) {
                returnValue = Double.parseDouble(dryWt) * 1000;
            }
        }

        return returnValue;
    }

    private double getWetWeightInKgs() {
        double returnValue = 0f;
        String wetWt = editWetTotal.getText().toString();
        if (!AUtils.isNull(wetWt)) {
            if (radioButtonWetKg.isChecked()) {
                returnValue = Double.parseDouble(wetWt);
            } else if (radioButtonWetTon.isChecked()) {
                returnValue = Double.parseDouble(wetWt) * 1000;
            }
        }

        return returnValue;
    }


    private void initViews() {

        btnSubmitSlwmDetails = findViewById(R.id.btn_submit_slwm);
        btnTakeDryPhoto = findViewById(R.id.iv_take_dry_photo);
        btnTakeWetPhoto = findViewById(R.id.iv_take_wet_photo);
        editDryTotal = findViewById(R.id.et_dry_weight);
        editWetTotal = findViewById(R.id.et_wet_weight);

        totalTon = 0d;
        dryTon = 0d;
        wetTon = 0d;

        mContext = SLWM_WeightActivity.this;
        toolbar = findViewById(R.id.toolbarSlwm);
        intent = getIntent();

        tvSlwm_id = findViewById(R.id.txt_house_id);
        tvEditTotal = findViewById(R.id.txtv_total_weight);


        radioGroupDry = findViewById(R.id.rbg_dry_weight);
        radioGroupWet = findViewById(R.id.rbg_wet_weight);

        radioButtonDryKg = findViewById(R.id.rb_dry_kg);
        radioButtonDryKg.setChecked(true);
        radioButtonDryTon = findViewById(R.id.rb_dry_ton);
        radioButtonWetKg = findViewById(R.id.rb_wet_kg);
        radioButtonWetKg.setChecked(true);
        radioButtonWetTon = findViewById(R.id.rb_wet_ton);

    }

    private void initToolbar() {
        toolbar.setTitle(R.string.slwm_details_title);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
    }


    private Boolean validateForm() {

        if (
                AUtils.isNullString(tvEditTotal.getText().toString()) ||
                        AUtils.isNullString(editDryTotal.getText().toString()) ||
                        AUtils.isNullString(editWetTotal.getText().toString())
        ) {
            AUtils.error(mContext, mContext.getString(R.string.plz_ent_all_fields));
            return false;
        } else if (
                !AUtils.isNumeric(tvEditTotal.getText().toString()) ||
                        !AUtils.isNumeric(editDryTotal.getText().toString()) ||
                        !AUtils.isNumeric(editWetTotal.getText().toString())
        ) {
            AUtils.error(mContext, mContext.getString(R.string.plz_ent_only_number));
            return false;
        }

        return true;
    }

    private void submitDumpDetailData() {

        boolean isImgPojo = getFormData();

        Intent intent = new Intent();
        intent.putExtra(AUtils.SLWMDATA.slwmDataMap, getIntentMap());
        intent.putExtra(AUtils.REQUEST_CODE, AUtils.MY_RESULT_REQUEST_QR);
        setResult(RESULT_OK, intent);
        finish();
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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.CAMERA)) {

                AUtils.showPermissionDialog(mContext, "CAMERA", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            requestPermissions(new String[]{Manifest.permission.CAMERA}, AUtils.MY_PERMISSIONS_REQUEST_CAMERA);
                        }
                    }
                });
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

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
            } else if (ActivityCompat.shouldShowRequestPermissionRationale((Activity) mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

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
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CAMERA) {
                onCaptureImageResult(data);
            }
        }
    }

    private void onCaptureImageResult(Intent data) {

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
                contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + "Gram Panchayat");
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

        switch (imageViewNo) { //Mod by swapnil

            case 1:
                Uri tempUri;
                String finalPath;

                btnTakeDryPhoto.setImageBitmap(thumbnail);
                tempUri = getImageUri(getApplicationContext(), thumbnail);

                finalPath = getRealPathFromURI(tempUri);
                dryImageFilePath = finalPath; //setting image1 path that will be set in imageDTO.

                Prefs.putString(AUtils.DRY_IMAGE, dryImageFilePath);

                break;
            case 2:
                btnTakeWetPhoto.setImageBitmap(thumbnail);
                tempUri = getImageUri(getApplicationContext(), thumbnail);

                finalPath = getRealPathFromURI(tempUri);
                wetImageFilePath = finalPath; //setting image2 path that will be set in imageDTO.

                Prefs.putString(AUtils.WET_IMAGE, wetImageFilePath);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            onBackPressed();
        return super.onOptionsItemSelected(item);
    }

    private HashMap<String, String> getIntentMap() {

        HashMap<String, String> map = new HashMap<>();
        map.put(AUtils.SLWMDATA.slwmId, strSlwmid);
        map.put(AUtils.SLWMDATA.weightTotal, String.valueOf(totalTon));
        map.put(AUtils.SLWMDATA.weightTotalDry, String.valueOf(dryTon));
        map.put(AUtils.SLWMDATA.weightTotalWet, String.valueOf(wetTon));

        return map;
    }

    private boolean getFormData() {

        imagePojo = new ImagePojo();

        if (!AUtils.isNullString(dryImageFilePath)) {
            imagePojo.setImage1(dryImageFilePath);
            imagePojo.setBeforeImage("DRY");
        }
        if (!AUtils.isNullString(wetImageFilePath)) {
            imagePojo.setImage2(wetImageFilePath);
            imagePojo.setAfterImage("WET");
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
