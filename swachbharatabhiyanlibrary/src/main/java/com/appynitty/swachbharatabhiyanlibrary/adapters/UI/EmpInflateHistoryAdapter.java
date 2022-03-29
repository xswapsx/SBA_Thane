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
 * update UI and this file :
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

       /*     //NormalBuildingCBox
            viewHolder.liNormalBuildingCBox = view.findViewById(R.id.li_res_normal_b_box);
            viewHolder.liNormalBuildingCBox.setVisibility(View.GONE);
            viewHolder.txtResC = view.findViewById(R.id.res_normal_collection);
            viewHolder.txtResBC = view.findViewById(R.id.res_b_collection);  */

            // updated res building and slum
            viewHolder.liResBuildCWBox = view.findViewById(R.id.li_res_b_s_box);
            viewHolder.liResBuildCWBox.setVisibility(View.GONE);

            viewHolder.liResBuildColBox = view.findViewById(R.id.liResBuildColBox);
            viewHolder.liResBuildColBox.setVisibility(View.GONE);
            viewHolder.txtResBuildC = view.findViewById(R.id.res_build_collection);

            viewHolder.liResSlumColBox = view.findViewById(R.id.li_slumColBox);
            viewHolder.liResSlumColBox.setVisibility(View.GONE);
            viewHolder.txtResSC = view.findViewById(R.id.res_slum_collection);

         /*   //Slum and commercial waste
            viewHolder.liResSlumCommercialCBox = view.findViewById(R.id.li_res_slum_cW_box);
            viewHolder.liSlumCBox = view.findViewById(R.id.slumColBox);
            viewHolder.liResSlumCommercialCBox.setVisibility(View.GONE);
            viewHolder.txtResSlumC = view.findViewById(R.id.res_s_collection);
            viewHolder.txtResCwC = view.findViewById(R.id.commer_collection);  */

            //updated swm and cw
            viewHolder.liSwmCWBox = view.findViewById(R.id.li_cW_swm_box);
            viewHolder.liSwmCWBox.setVisibility(View.GONE);

            viewHolder.liSwmLayoutBox = view.findViewById(R.id.Li_slwm_layout);
            viewHolder.liSwmLayoutBox.setVisibility(View.GONE);
            viewHolder.txtSwmCount = view.findViewById(R.id.txt_swm_collection);

            viewHolder.liCwLayoutBox = view.findViewById(R.id.cwColBox);
            viewHolder.liCwLayoutBox.setVisibility(View.GONE);
            viewHolder.txtCwCount = view.findViewById(R.id.cw_collection);

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

        /*    //Dump yard and House collection
            viewHolder.liDumpHouseCBox = view.findViewById(R.id.li_dump_house_box);
            viewHolder.liDumpHouseCBox.setVisibility(View.GONE);

            viewHolder.liDumpColBox = view.findViewById(R.id.liDumpColBox);
            viewHolder.liDumpColBox.setVisibility(View.GONE);
            viewHolder.txtDumpC = view.findViewById(R.id.dy_collection);

            viewHolder.liHouseColBox = view.findViewById(R.id.liHouseColBox);
            viewHolder.liHouseColBox.setVisibility(View.GONE);
            viewHolder.txtHouseC = view.findViewById(R.id.house_collection);  */

        /*    //Ctpt and SLWM collection
            viewHolder.liCtptSwmCBox = view.findViewById(R.id.li_ctpt_swm_box);
            viewHolder.liCtptSwmCBox.setVisibility(View.GONE);

            viewHolder.ctptLayout = view.findViewById(R.id.ctptLayout);
            viewHolder.ctptLayout.setVisibility(View.GONE);
            viewHolder.txtCtptC = view.findViewById(R.id.ctpt_collection);

            viewHolder.slwmLayout = view.findViewById(R.id.slwm_layout);
            viewHolder.slwmLayout.setVisibility(View.GONE);
            viewHolder.txtSlwmC = view.findViewById(R.id.swm_collection);  */

            //updated CTPT and blank
            viewHolder.liCtptBlankBox = view.findViewById(R.id.li_ctpt_blank_box);
            viewHolder.liCtptBlankBox.setVisibility(View.GONE);

            viewHolder.liCtptLayoutBox = view.findViewById(R.id.Li_ctptLayout);
            viewHolder.liCtptLayoutBox.setVisibility(View.GONE);
            viewHolder.txtCtptCount = view.findViewById(R.id.txt_ctpt_collection);

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(historyPojoList) && !historyPojoList.isEmpty()) {
            TableDataCountPojo.WorkHistory workHistoryPojo = historyPojoList.get(position);
            holder.date.setText(AUtils.extractDateEmp(workHistoryPojo.getDate()));
            holder.month.setText(AUtils.extractMonthEmp(workHistoryPojo.getDate()));

        /*    holder.liNormalBuildingCBox.setVisibility(View.VISIBLE);
            holder.txtResC.setText(workHistoryPojo.getResidentialCollection());
            holder.txtResBC.setText(workHistoryPojo.getResidentialBCollection());
            holder.liResSlumCommercialCBox.setVisibility(View.VISIBLE);
            holder.liSlumCBox.setVisibility(View.VISIBLE);
            holder.txtResSlumC.setText(workHistoryPojo.getResidentialSCollection());
            holder.txtResCwC.setText(workHistoryPojo.getCommertialCollection());    */

            //res building and slum
            holder.liResBuildCWBox.setVisibility(View.VISIBLE);
            holder.liResBuildColBox.setVisibility(View.VISIBLE);
            holder.txtResBuildC.setText(workHistoryPojo.getResidentialBCollection());
            holder. liResSlumColBox.setVisibility(View.VISIBLE);
            holder.txtResSC.setText(workHistoryPojo.getResidentialSCollection());

            //updated swm and cw
            holder.liSwmCWBox.setVisibility(View.VISIBLE);
            holder.liSwmLayoutBox.setVisibility(View.VISIBLE);
            holder.txtSwmCount.setText(workHistoryPojo.getSwmCollection());
            holder.liCwLayoutBox.setVisibility(View.VISIBLE);
            holder.txtCwCount.setText(workHistoryPojo.getCommertialCollection());

            //hort
            holder.liHortCadCBox.setVisibility(View.GONE);
            holder.txtCandDC.setText(workHistoryPojo.getCADCollection());
            holder.txtHortC.setText(workHistoryPojo.getHorticultureCollection());

            holder.liLiqStreetCBox.setVisibility(View.VISIBLE);
            holder.liLiquidColBox.setVisibility(View.VISIBLE);
            holder.liStreetColBox.setVisibility(View.VISIBLE);
            holder.txtLiquidC.setText(workHistoryPojo.getLiquidCollection());
            holder.txtStreetC.setText(workHistoryPojo.getStreetCollection());

            //updated CTPT and blank
            holder.liCtptBlankBox.setVisibility(View.VISIBLE);
            holder.liCtptLayoutBox.setVisibility(View.VISIBLE);
            holder.txtCtptCount.setText(workHistoryPojo.getCtptCollection());


       /*     holder.liDumpHouseCBox.setVisibility(View.VISIBLE);
            holder.liHouseColBox.setVisibility(View.GONE);
            holder.txtHouseC.setText(workHistoryPojo.getHouseCollection());
            holder.liDumpColBox.setVisibility(View.VISIBLE);
            holder.txtDumpC.setText(workHistoryPojo.getDumpYardCollection());
            holder.liCtptSwmCBox.setVisibility(View.VISIBLE);
            holder.ctptLayout.setVisibility(View.VISIBLE);
            holder.txtCtptC.setText(workHistoryPojo.getCtptCollection());
            holder.slwmLayout.setVisibility(View.VISIBLE);
            holder.txtSlwmC.setText(workHistoryPojo.getSwmCollection());  */
        }

        return view;
    }

    private class ViewHolder {

        private TextView date;
        private TextView month;
        //box
       /* private LinearLayout liNormalBuildingCBox, liResSlumCommercialCBox, liLiqStreetCBox;
        private LinearLayout liDumpHouseCBox, liCtptSwmCBox , liHortCadCBox;*/
        private LinearLayout liHortCadCBox, liLiqStreetCBox;

        //content
       /* private TextView txtResC, txtResBC;
        private TextView txtResSlumC, txtResCwC;*/
        private TextView txtCandDC, txtHortC;
        private TextView txtLiquidC, txtStreetC;
       /* private TextView txtDumpC, txtHouseC;
        private TextView txtCtptC, txtSlwmC;*/

        // liquid and street column
        /*private LinearLayout liLiquidColBox, liStreetColBox, slwmLayout, ctptLayout;*/
        private LinearLayout liLiquidColBox, liStreetColBox;
        //dump and house collection
       /* private LinearLayout liDumpColBox, liHouseColBox;
        private LinearLayout liSlumCBox;*/

        //updated 25/03 ui and functionality
        //layout box
        LinearLayout liResBuildCWBox;
        LinearLayout liCtptBlankBox;
        LinearLayout liSwmCWBox;
        // col box
        LinearLayout liResBuildColBox;
        LinearLayout liResSlumColBox;

        LinearLayout liSwmLayoutBox;
        LinearLayout liCwLayoutBox;
        LinearLayout liCtptLayoutBox;

        // contain
        TextView txtResBuildC;
        TextView txtResSC;
        TextView txtSwmCount;
        TextView txtCwCount;
        TextView txtCtptCount;
    }


}
