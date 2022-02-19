package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayan Dey on 25/10/18.
 */

public class InflateWasteSpinnerAdapter extends BaseAdapter {

    private final Context mContext;
    private final int resId;

    private List<?> dataList;

    public InflateWasteSpinnerAdapter(@NonNull Context context, int resource) {
        this.mContext = context;
        this.resId = resource;
    }

    public void setDataList(List<?> list){
        this.dataList = list;
    }

    @Override
    public View getDropDownView(int position, View convertView,@NonNull ViewGroup parent) {
        return createItemView(position, convertView, parent);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int i) {
        return dataList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, View view,@NonNull ViewGroup viewGroup) {
        return createItemView(position, view, viewGroup);
    }

    private View createItemView(int position, View convertView, ViewGroup parent){
        View view = LayoutInflater.from(this.mContext).inflate(this.resId, parent, false);
        TextView textView = view.findViewById(android.R.id.text1);
        if(dataList != null && dataList.size() > 0) {
            Object listData = dataList.get(position);
            if(listData instanceof WasteManagementPojo.GarbageCategoryPojo) {
              textView.setText(((WasteManagementPojo.GarbageCategoryPojo) listData).getGarbageCategory());
              view.setTag(((WasteManagementPojo.GarbageCategoryPojo) listData).getCategoryID());
          }

          if (listData instanceof WasteManagementPojo.GarbageSubCategoryPojo) {
              textView.setText(((WasteManagementPojo.GarbageSubCategoryPojo) listData).getGarbageSubCategory());
              view.setTag(((WasteManagementPojo.GarbageSubCategoryPojo) listData).getSubCategoryID());
          }
        }
        return view;
    }
}
