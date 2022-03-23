package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.List;

/**
 * Created by Ayan Dey on 25/10/18.
 */

public class EmpInflateHistoryAdapter extends ArrayAdapter<TableDataCountPojo.WorkHistory> {

    private static final String TAG = "EmpInflateHistoryAdapter";
    private final List<TableDataCountPojo.WorkHistory> historyPojoList;
    private final Context context;
    private View view;
    private ViewHolder holder;

    public EmpInflateHistoryAdapter(@NonNull Context context, @NonNull List<TableDataCountPojo.WorkHistory> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.historyPojoList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_history_card, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.date = view.findViewById(R.id.history_date_txt);
            viewHolder.month = view.findViewById(R.id.history_month_txt);
            //NormalBuildingCBox
            viewHolder.liNormalBuildingCBox = view.findViewById(R.id.li_res_normal_b_box);
            viewHolder.liNormalBuildingCBox.setVisibility(View.GONE);
            viewHolder.txtResC = view.findViewById(R.id.res_normal_collection);
            viewHolder.txtResBC = view.findViewById(R.id.res_b_collection);
            //Slum and commercial waste
            viewHolder.liResSlumCommercialCBox = view.findViewById(R.id.li_res_slum_cW_box);
            viewHolder.liSlumCBox = view.findViewById(R.id.slumColBox);
            viewHolder.liResSlumCommercialCBox.setVisibility(View.GONE);
            viewHolder.txtResSlumC = view.findViewById(R.id.res_s_collection);
            viewHolder.txtResCwC = view.findViewById(R.id.commer_collection);
            //Horticulture and CandD waste
            viewHolder.liHortCadCBox = view.findViewById(R.id.li_hort_cad_box);
            viewHolder.liHortCadCBox.setVisibility(View.GONE);
            viewHolder.txtCandDC = view.findViewById(R.id.cad_collection);
            viewHolder.txtHortC = view.findViewById(R.id.hort_collection);
            // Liquid and street
            viewHolder.liLiqStreetCBox = view.findViewById(R.id.li_liq_street_box);
            viewHolder.liLiqStreetCBox.setVisibility(View.GONE);

            viewHolder.liLiquidColBox = view.findViewById(R.id.liLqiColBox);
            viewHolder.liLiquidColBox.setVisibility(View.INVISIBLE);
            viewHolder.txtLiquidC = view.findViewById(R.id.lwc_collection);

            viewHolder.liStreetColBox = view.findViewById(R.id.liStreetColBox);
            viewHolder.liStreetColBox.setVisibility(View.INVISIBLE);
            viewHolder.txtStreetC = view.findViewById(R.id.ss_collection);
            //Dump yard and House collection
            viewHolder.liDumpHouseCBox = view.findViewById(R.id.li_dump_house_box);
            viewHolder.liDumpHouseCBox.setVisibility(View.GONE);

            viewHolder.liDumpColBox = view.findViewById(R.id.liDumpColBox);
            viewHolder.liDumpColBox.setVisibility(View.GONE);
            viewHolder.txtDumpC = view.findViewById(R.id.dy_collection);

            viewHolder.liHouseColBox = view.findViewById(R.id.liHouseColBox);
            viewHolder.liHouseColBox.setVisibility(View.INVISIBLE);
            viewHolder.txtHouseC = view.findViewById(R.id.house_collection);
            //Ctpt and SLWM collection
            viewHolder.liCtptSwmCBox = view.findViewById(R.id.li_ctpt_swm_box);
            viewHolder.liCtptSwmCBox.setVisibility(View.GONE);
            viewHolder.txtCtptC = view.findViewById(R.id.ctpt_collection);
            viewHolder.txtSlwmC = view.findViewById(R.id.swm_collection);

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(historyPojoList) && !historyPojoList.isEmpty()) {
            TableDataCountPojo.WorkHistory workHistoryPojo = historyPojoList.get(position);
            holder.date.setText(AUtils.extractDateEmp(workHistoryPojo.getDate()));
            holder.month.setText(AUtils.extractMonthEmp(workHistoryPojo.getDate()));

            holder.liNormalBuildingCBox.setVisibility(View.VISIBLE);
            holder.txtResC.setText(workHistoryPojo.getResidentialCollection());
            holder.txtResBC.setText(workHistoryPojo.getResidentialBCollection());
            holder.liResSlumCommercialCBox.setVisibility(View.VISIBLE);
            holder.liSlumCBox.setVisibility(View.VISIBLE);
            holder.txtResSlumC.setText(workHistoryPojo.getResidentialSCollection());
            holder.txtResCwC.setText(workHistoryPojo.getCommertialCollection());
            holder.liHortCadCBox.setVisibility(View.GONE);
            holder.txtCandDC.setText(workHistoryPojo.getCADCollection());
            holder.txtHortC.setText(workHistoryPojo.getHorticultureCollection());
            holder.liLiqStreetCBox.setVisibility(View.VISIBLE);
            holder.liLiquidColBox.setVisibility(View.VISIBLE);
            holder.liStreetColBox.setVisibility(View.VISIBLE);
            holder.txtLiquidC.setText(workHistoryPojo.getLiquidCollection());
            holder.txtStreetC.setText(workHistoryPojo.getStreetCollection());
            holder.liDumpHouseCBox.setVisibility(View.GONE);
            holder.liHouseColBox.setVisibility(View.GONE);
            holder.txtHouseC.setText(workHistoryPojo.getHouseCollection());
            holder.liDumpColBox.setVisibility(View.VISIBLE);
            holder.txtDumpC.setText(workHistoryPojo.getDumpYardCollection());
            holder.liCtptSwmCBox.setVisibility(View.VISIBLE);
            holder.txtCtptC.setText(workHistoryPojo.getCtptCollection());
            holder.txtSlwmC.setText(workHistoryPojo.getSwmCollection());
        }

        return view;
    }

    private class ViewHolder {

        private TextView date;
        private TextView month;
        //box
        private LinearLayout liNormalBuildingCBox, liResSlumCommercialCBox, liLiqStreetCBox;
        private LinearLayout liDumpHouseCBox, liCtptSwmCBox , liHortCadCBox;

        //content
        private TextView txtResC, txtResBC;
        private TextView txtResSlumC, txtResCwC;
        private TextView txtCandDC, txtHortC;
        private TextView txtLiquidC, txtStreetC;
        private TextView txtDumpC, txtHouseC;
        private TextView txtCtptC, txtSlwmC;

        // liquid and street column
        private LinearLayout liLiquidColBox, liStreetColBox;
        //dump and house collection
        private LinearLayout liDumpColBox, liHouseColBox;
        private LinearLayout liSlumCBox;
    }


}
