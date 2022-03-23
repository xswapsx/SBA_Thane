package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpOfflineCollectionCount;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.List;

/**
 * Created by Swapnil Lanjewar on 08/01/2022.
 */

public class EmpInflateOfflineHistoryAdapter extends ArrayAdapter<EmpOfflineCollectionCount> {

    List<EmpOfflineCollectionCount> items_list;
    int custom_layout_id;

    public EmpInflateOfflineHistoryAdapter(@NonNull Context context, int resource, @NonNull List<EmpOfflineCollectionCount> objects) {
        super(context, resource, objects);
        items_list = objects;
        custom_layout_id = resource;
    }

    @Override
    public int getCount() {
        return items_list.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        View v = convertView;
        if (v == null) {
            // getting reference to the main layout and
            // initializing
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = inflater.inflate(custom_layout_id, null);
        }
        //added by Rahul
        TextView day = v.findViewById(R.id.history_date_txt);
        TextView month = v.findViewById(R.id.history_month_txt);
        LinearLayout liNormalBuildingCBox = v.findViewById(R.id.li_res_normal_b_box);
        liNormalBuildingCBox.setVisibility(View.GONE);
        TextView txtResC = v.findViewById(R.id.res_normal_collection);
        TextView txtResBC = v.findViewById(R.id.res_b_collection);
        //Slum and commercial waste
        LinearLayout liResSlumCommercialCBox = v.findViewById(R.id.li_res_slum_cW_box);
        LinearLayout liSlumCBox = v.findViewById(R.id.slumColBox);
        liResSlumCommercialCBox.setVisibility(View.GONE);
        TextView txtResSlumC = v.findViewById(R.id.res_s_collection);
        TextView txtResCwC = v.findViewById(R.id.commer_collection);
        //Horticulture and CandD waste
        LinearLayout liHortCadCBox = v.findViewById(R.id.li_hort_cad_box);
        liHortCadCBox.setVisibility(View.GONE);
        TextView txtCandDC = v.findViewById(R.id.cad_collection);
        TextView txtHortC = v.findViewById(R.id.hort_collection);
        // Liquid and street
        LinearLayout liLiqStreetCBox = v.findViewById(R.id.li_liq_street_box);
        liLiqStreetCBox.setVisibility(View.GONE);

        LinearLayout liLiquidColBox = v.findViewById(R.id.liLqiColBox);
        liLiquidColBox.setVisibility(View.INVISIBLE);
        TextView txtLiquidC = v.findViewById(R.id.lwc_collection);

        LinearLayout liStreetColBox = v.findViewById(R.id.liStreetColBox);
        liStreetColBox.setVisibility(View.INVISIBLE);
        TextView txtStreetC = v.findViewById(R.id.ss_collection);
        //Dump yard and House collection
        LinearLayout liDumpHouseCBox = v.findViewById(R.id.li_dump_house_box);
        liDumpHouseCBox.setVisibility(View.GONE);

        LinearLayout liDumpColBox = v.findViewById(R.id.liDumpColBox);
        liDumpColBox.setVisibility(View.INVISIBLE);
        TextView txtDumpC = v.findViewById(R.id.dy_collection);

        LinearLayout liHouseColBox = v.findViewById(R.id.liHouseColBox);
        liHouseColBox.setVisibility(View.INVISIBLE);
        TextView txtHouseC = v.findViewById(R.id.house_collection);
        //Ctpt and SLWM collection
        LinearLayout liCtptSwmCBox = v.findViewById(R.id.li_ctpt_swm_box);
        liCtptSwmCBox.setVisibility(View.GONE);
        TextView txtCtptC = v.findViewById(R.id.ctpt_collection);
        TextView txtSlwmC = v.findViewById(R.id.swm_collection);

        // get the item using the  position param
        EmpOfflineCollectionCount item = items_list.get(position);

        day.setText(AUtils.extractDateEmp(item.getDate()));
        month.setText(AUtils.extractMonthEmp(item.getDate()));

        liNormalBuildingCBox.setVisibility(View.VISIBLE);
        txtResC.setText(item.getHouseCount());
        txtResBC.setText(item.getResBCollection());
        liResSlumCommercialCBox.setVisibility(View.VISIBLE);
        liSlumCBox.setVisibility(View.VISIBLE);
        txtResSlumC.setText(item.getResSCollection());
        txtResCwC.setText(item.getCommercialCollection());
        liHortCadCBox.setVisibility(View.GONE);
        txtCandDC.setText(item.getCadCollection());
        txtHortC.setText(item.getHortCollection());
        liLiqStreetCBox.setVisibility(View.VISIBLE);
        liLiquidColBox.setVisibility(View.VISIBLE);
        liStreetColBox.setVisibility(View.VISIBLE);
        txtLiquidC.setText(item.getLiquidWasteCount());
        txtStreetC.setText(item.getStreetSweepCount());
        liDumpHouseCBox.setVisibility(View.GONE);
        liHouseColBox.setVisibility(View.GONE);
        txtHouseC.setText(item.getHouseCount());
        liDumpColBox.setVisibility(View.VISIBLE);
        txtDumpC.setText(item.getDumpYardCount());
        liCtptSwmCBox.setVisibility(View.VISIBLE);
        txtCtptC.setText(item.getCtptCollection());
        txtSlwmC.setText(item.getSwmCollection());

        return v;
    }
}
