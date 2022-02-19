package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.LanguagePojo;
import com.appynitty.swachbharatabhiyanlibrary.pojos.VehicleTypePojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.HashMap;

public class DialogAdapter extends BaseAdapter {

    private final Context mContext;
    private final HashMap<Integer, Object> mMapList;

    private View view;
    private ViewHolder holder;
    private final String mType;

    public DialogAdapter(Context context, HashMap<Integer, Object> mapList, String type)
    {
        mContext = context;
        mMapList = mapList;

        mType = type;
    }

    @Override
    public int getCount() {
        return mMapList.size();
    }

    @Override
    public Object getItem(int position) {
        return mMapList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dialog_adapter_layout, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.mDataText = view.findViewById(R.id.data_text);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(mMapList) && !mMapList.isEmpty()) {

            if(mType.equals(AUtils.DIALOG_TYPE_VEHICLE)) {

                VehicleTypePojo vehicleTypePojo = (VehicleTypePojo) mMapList.get(position);

                if(Prefs.getString(AUtils.LANGUAGE_NAME, AUtils.DEFAULT_LANGUAGE_ID).equals("2"))
                {
                    holder.mDataText.setText(vehicleTypePojo.getDescriptionMar());
                    //holder.mDataText.setTag(Integer.parseInt(vehicleTypePojo.getVtId()));
                }
                else {
                    holder.mDataText.setText(vehicleTypePojo.getDescription());
                    //holder.mDataText.setTag(Integer.parseInt(vehicleTypePojo.getVtId()), vehicleTypePojo.getDescription());
                }
            }
            else
            {
                LanguagePojo languagePojo = (LanguagePojo) mMapList.get(position);

                holder.mDataText.setText(languagePojo.getLanguage());
                //holder.mDataText.setTag(Integer.parseInt(languagePojo.getLanguageId()), languagePojo.getLanguage());
            }
        }

        return view;
    }

    private class ViewHolder {

        private TextView mDataText;
    }
}
