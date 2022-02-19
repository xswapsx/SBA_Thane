package com.appynitty.swachbharatabhiyanlibrary.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.UI.InflateWasteSpinnerAdapter;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.WasteTypeCategoryAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;
import com.appynitty.swachbharatabhiyanlibrary.repository.SyncWasteSubCategoriesRepository;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;
import com.riaylibrary.custom_component.MyProgressDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayan Dey on 25/1/20.
 */
public class AddWasteDetailsPopup extends Dialog {

    private final MutableLiveData<WasteManagementPojo> wasteManagementPojoMutableLiveData;
    private final Context mContext;
    private final int resId;
    private int unitId;
    private final String userId;
    private final String sourceId;
    private String unit;
    private final AddWasteDetailsPopup popupInstance;
    private MyProgressDialog progressDialog;
    private Spinner wasteTypeSpinner;
    private Spinner wasteCategorySpinner;
    private EditText wasteQuantity;
    private Button cancelButton, doneButton;
    private RadioGroup radioGroupUnits;
    private RadioButton radioButtonKg, radioButtonTon;
    private List<WasteManagementPojo.GarbageCategoryPojo> categoryList;
    private List<WasteManagementPojo.GarbageSubCategoryPojo> subCategoryList;
    private WasteTypeCategoryAdapterClass wasteTypeCategoryAdapterClass;
    private InflateWasteSpinnerAdapter categoryAdapter;
    private WasteManagementPojo wasteManagementPojo;
    private WasteManagementPojo tempWasteManagementPojo;

    public AddWasteDetailsPopup(@NonNull Context context) {
        super(context);
        this.popupInstance = this;
        this.mContext = context;
        this.resId = android.R.layout.simple_list_item_1;
        this.userId = Prefs.getString(AUtils.PREFS.USER_ID, "");
        this.sourceId = "2";

        wasteManagementPojoMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<WasteManagementPojo> getWasteManagementPojoMutableLiveData() {
        return wasteManagementPojoMutableLiveData;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_add_garbage_waste);

        progressDialog = new MyProgressDialog(mContext, false);
        wasteTypeSpinner = findViewById(R.id.spinner_waste_type);
        wasteCategorySpinner = findViewById(R.id.spinner_waste_category);
        wasteQuantity = findViewById(R.id.edit_text_waste_quantity);
        doneButton = findViewById(R.id.button_done);
        cancelButton = findViewById(R.id.button_cancel);
        radioGroupUnits = findViewById(R.id.radio_group_units);
        radioButtonKg = findViewById(R.id.radio_button_kg);
        radioButtonTon = findViewById(R.id.radio_button_ton);

        wasteTypeCategoryAdapterClass = new WasteTypeCategoryAdapterClass();

        registerEvents();
    }

    private void registerEvents() {
        wasteQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().equals("."))
                {
                    wasteQuantity.setText("0.");
                    wasteQuantity.setSelection(2);
                }
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isValid()){
                    wasteManagementPojo.setWeight(wasteQuantity.getText().toString());
                    wasteManagementPojo.setWasteUnitName(unit);
                    wasteManagementPojo.setUnitID(unitId);
                    if(tempWasteManagementPojo != null)
                        wasteManagementPojo.setID(tempWasteManagementPojo.getID());
                    wasteManagementPojoMutableLiveData.setValue(wasteManagementPojo);
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupInstance.dismiss();
            }
        });

        wasteCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position > 0) {
                    String subCategoryId = (String) view.getTag();
                    wasteManagementPojo.setSubCategoryID(subCategoryId);
                    wasteManagementPojo.setWasteCategoryName(subCategoryList.get(position).getGarbageSubCategory());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        wasteTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                if(position > 0) {
                    String categoryId = (String) view.getTag();
                    if (AUtils.isInternetAvailable()) {
                        if(!progressDialog.isShowing()) progressDialog.show();
                        wasteTypeCategoryAdapterClass.fetchWasteCategory(categoryId);
                    } else {
                        SyncWasteSubCategoriesRepository subCategoriesRepository = new SyncWasteSubCategoriesRepository(mContext);
                        setCategoryList(subCategoriesRepository.fetchWasteSubCategories(categoryId));
                        setCategoryAdapter();
                    }
                    wasteManagementPojo.setWasteTypeName(categoryList.get(position).getGarbageCategory());
                    wasteManagementPojo.setCategoryID(categoryList.get(position).getCategoryID());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        radioGroupUnits.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                unit = mContext.getResources().getString(R.string.unit_kg);
                unitId = 1;
                if(radioGroup.getCheckedRadioButtonId() == R.id.radio_button_ton) {
                    unit = mContext.getResources().getString(R.string.unit_ton);
                    unitId = 2;
                }
            }
        });

        wasteTypeCategoryAdapterClass.setWasteTypeCategoryListener(new WasteTypeCategoryAdapterClass.WasteTypeCategoryListener() {
            @Override
            public void onSuccessCallback(List<?> list, int requestType) {
                if(progressDialog.isShowing()) progressDialog.dismiss();
                if (requestType == WasteTypeCategoryAdapterClass.WASTE_CATEGORY_REQUEST && list.size() > 0) {
                    setCategoryList((List<WasteManagementPojo.GarbageSubCategoryPojo>) list);
                    setCategoryAdapter();
                }
            }

            @Override
            public void onFailureCallback() {
                if(progressDialog.isShowing()) progressDialog.dismiss();
                AUtils.warning(mContext, mContext.getResources().getString(R.string.try_after_sometime));
            }

            @Override
            public void onErrorCallback() {
                if(progressDialog.isShowing()) progressDialog.dismiss();
                AUtils.warning(mContext, mContext.getResources().getString(R.string.serverError));
            }
        });
    }

    private void setCategoryAdapter() {
        categoryAdapter.setDataList(subCategoryList);

        if(tempWasteManagementPojo != null) {
            for (int i = 0; i < subCategoryList.size(); i++){
                if (subCategoryList.get(i).getSubCategoryID().equals(tempWasteManagementPojo.getSubCategoryID())) {
                    wasteCategorySpinner.setSelection(i);
                    wasteManagementPojo.setSubCategoryID(tempWasteManagementPojo.getSubCategoryID());
                    wasteManagementPojo.setWasteCategoryName(tempWasteManagementPojo.getWasteCategoryName());
                    break;
                }else
                    wasteCategorySpinner.setSelection(0);
            }
        }
    }

    private boolean isValid() {

        if(wasteTypeSpinner.getSelectedItemPosition() == 0){
            AUtils.error(mContext, mContext.getResources().getString(R.string.plz_select_waste_type));
            return false;
        }

        if(wasteCategorySpinner.getSelectedItemPosition() == 0){
            AUtils.error(mContext, mContext.getResources().getString(R.string.plz_select_waste_category));
            return false;
        }

        if(wasteQuantity.getText().toString().isEmpty() && (wasteQuantity.getText().toString().equals("0") || wasteQuantity.getText().toString().equals("0."))) {
            AUtils.error(mContext, mContext.getResources().getString(R.string.plz_enter_waste_quantity));
            wasteQuantity.setError(mContext.getResources().getString(R.string.plz_enter_waste_quantity));
            return false;
        }
        
        return true;
    }

    public void initDialog(@Nullable WasteManagementPojo pojo) {
        unit = mContext.getResources().getString(R.string.unit_kg);
        unitId = 1;
        wasteManagementPojo = new WasteManagementPojo();
        wasteManagementPojo.setUpdate(false);

        if(pojo != null) {
            tempWasteManagementPojo = pojo;
            wasteManagementPojo.setUpdate(true);
        }

        wasteManagementPojo.setSource(this.sourceId);
        wasteManagementPojo.setUserID(this.userId);

        wasteQuantity.setText((pojo == null) ? "" : pojo.getWeight());
        wasteQuantity.setSelection((pojo == null) ? 0 : pojo.getWeight().length());

        InflateWasteSpinnerAdapter typeAdapter = new InflateWasteSpinnerAdapter(this.mContext, this.resId);
        typeAdapter.setDataList(categoryList);
        wasteTypeSpinner.setAdapter(typeAdapter);

        subCategoryList = new ArrayList<>();
        subCategoryList.add(new WasteManagementPojo().new GarbageSubCategoryPojo("0", mContext.getResources().getString(R.string.select_waste_type)));

        categoryAdapter = new InflateWasteSpinnerAdapter(mContext, android.R.layout.simple_list_item_1);
        categoryAdapter.setDataList(subCategoryList);
        wasteCategorySpinner.setAdapter(categoryAdapter);

        if(pojo != null) {
            for (int i = 0; i < categoryList.size(); i++) {
                if(categoryList.get(i).getCategoryID().equals(pojo.getCategoryID())){
                    wasteTypeSpinner.setSelection(i);
                    break;
                }
            }

            unit = pojo.getWasteUnitName();
            unitId = pojo.getUnitID();
        }

        if (unitId == 1) {
            radioButtonKg.setChecked(true);
        } else {
            radioButtonTon.setChecked(true);
        }
    }

    public void setTypeList(List<WasteManagementPojo.GarbageCategoryPojo> categoryPojos) {
        categoryList = new ArrayList<>();
        categoryList.add(new WasteManagementPojo(). new GarbageCategoryPojo("0", mContext.getResources().getString(R.string.select_waste_type)));
        if(!AUtils.isNull(categoryPojos) && categoryPojos.size() > 0)
            categoryList.addAll(categoryPojos);
        else
            AUtils.warning(mContext, mContext.getResources().getString(R.string.something_error));
    }

    private void setCategoryList(List<WasteManagementPojo.GarbageSubCategoryPojo> subCategoryPojos) {
        subCategoryList.clear();
        subCategoryList.add(new WasteManagementPojo(). new GarbageSubCategoryPojo("0", mContext.getResources().getString(R.string.select_waste_category)));
        if(subCategoryPojos.size() > 0)
            subCategoryList.addAll(subCategoryPojos);
    }
}
