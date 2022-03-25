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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.TableDataCountPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Swapnil Lanjewar on 18/01/2022.
 * ui update by Rahul Rokade on 25/02/22
 */

public class InflateOfflineWorkAdapter extends ArrayAdapter<TableDataCountPojo.WorkHistory> {

    private static final String TAG = "InflateHistoryAdapter";
    private final List<TableDataCountPojo.WorkHistory> historyPojoList;
    private final Context context;
    private View view;
    private InflateOfflineWorkAdapter.ViewHolder holder;
    private final String empType = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null);

    public InflateOfflineWorkAdapter(@NonNull Context context, @NonNull List<TableDataCountPojo.WorkHistory> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.historyPojoList = objects;
        Log.e("InflateHistoryAdapter", Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, ""));
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           /* //layout history card
           view = inflater.inflate(R.layout.layout_history_card, null);*/
           /* item work history
           view = inflater.inflate(R.layout.item_work_history, null);*/

            //work history offline item
            view = inflater.inflate(R.layout.work_history_offline_item, null);

            final InflateOfflineWorkAdapter.ViewHolder viewHolder = new InflateOfflineWorkAdapter.ViewHolder();
            viewHolder.date = view.findViewById(R.id.history_date_txt);
            viewHolder.month = view.findViewById(R.id.history_month_txt);

            /* ================================layout history card========================================================*/

        /*    //NormalBuildingCBox
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
            viewHolder.liLiquidColBox.setVisibility(View.GONE);
            viewHolder.txtLiquidC = view.findViewById(R.id.lwc_collection);

            viewHolder.liStreetColBox = view.findViewById(R.id.liStreetColBox);
            viewHolder.liStreetColBox.setVisibility(View.GONE);
            viewHolder.txtStreetC = view.findViewById(R.id.ss_collection);
            //Dump yard and House collection
            viewHolder.liDumpHouseCBox = view.findViewById(R.id.li_dump_house_box);
            viewHolder.liDumpHouseCBox.setVisibility(View.GONE);

            viewHolder.liDumpColBox = view.findViewById(R.id.liDumpColBox);
            viewHolder.liDumpColBox.setVisibility(View.INVISIBLE);
            viewHolder.txtDumpC = view.findViewById(R.id.dy_collection);

            viewHolder.liHouseColBox = view.findViewById(R.id.liHouseColBox);
            viewHolder.liHouseColBox.setVisibility(View.INVISIBLE);
            viewHolder.txtHouseC = view.findViewById(R.id.house_collection);
            //Ctpt and SLWM collection
            viewHolder.liCtptSwmCBox = view.findViewById(R.id.li_ctpt_swm_box);
            viewHolder.liCtptSwmCBox.setVisibility(View.GONE);
            viewHolder.txtCtptC = view.findViewById(R.id.ctpt_collection);
            viewHolder.txtSlwmC = view.findViewById(R.id.swm_collection);*/

       /* =================================item work history================================================================*/
            /*
                //res normal box
            viewHolder.rlNormalBox = view.findViewById(R.id.rl_normal_box);
            viewHolder.rlNormalBox.setVisibility(View.GONE);
            viewHolder.txtResC = view.findViewById(R.id.res_normal_collection);
                //res building box
            viewHolder.rlResBuildingBox = view.findViewById(R.id.rl_res_b_box);
            viewHolder.rlResBuildingBox.setVisibility(View.GONE);
            viewHolder.txtResBC = view.findViewById(R.id.res_b_collection);
                //res slum box
            viewHolder.rlResSlumBox = view.findViewById(R.id.rl_res_s_box);
            viewHolder.rlResSlumBox.setVisibility(View.GONE);
            viewHolder.txtResSlumC = view.findViewById(R.id.res_s_collection);
                //Commercial box
            viewHolder.rlCommercialBox = view.findViewById(R.id.rl_commercial_box);
            viewHolder.rlCommercialBox.setVisibility(View.GONE);
            viewHolder.txtResCwC = view.findViewById(R.id.commer_collection);
                //CaD box
            viewHolder.rlCadBox = view.findViewById(R.id.rl_cad_box);
            viewHolder.rlCadBox.setVisibility(View.GONE);
            viewHolder.txtCandDC = view.findViewById(R.id.cad_collection);
                //Horticulture box
            viewHolder.rlHortBox = view.findViewById(R.id.rl_horticulture_box);
            viewHolder.rlHortBox.setVisibility(View.GONE);
            viewHolder.txtHortC = view.findViewById(R.id.hort_collection);
                //Liquid Box
            viewHolder.rlLiquidBox = view.findViewById(R.id.rl_liquid_box);
            viewHolder.rlLiquidBox.setVisibility(View.GONE);
            viewHolder.txtLiquidC = view.findViewById(R.id.lwc_collection);
                //Street box
            viewHolder.rlStreetBox = view.findViewById(R.id.rl_street_box);
            viewHolder.rlStreetBox.setVisibility(View.GONE);
            viewHolder.txtStreetC = view.findViewById(R.id.ss_collection);
                    //Dump Box
            viewHolder.rlDumpBox = view.findViewById(R.id.rl_dump_box);
            viewHolder.rlDumpBox.setVisibility(View.GONE);
            viewHolder.txtDumpC = view.findViewById(R.id.dy_collection);
                    //House collection box
            viewHolder.rlHouseBox = view.findViewById(R.id.rl_house_box);
            viewHolder.rlHouseBox.setVisibility(View.GONE);
            viewHolder.txtHouseC = view.findViewById(R.id.house_collection);
                    //Ctpt box
            viewHolder.rlCtptBox = view.findViewById(R.id.rl_ctpt_box);
            viewHolder.rlCtptBox.setVisibility(View.GONE);
            viewHolder.txtCtptC = view.findViewById(R.id.ctpt_collection);
                    //SWM box
            viewHolder.rlSwmBox = view.findViewById(R.id.rl_swm_box);
            viewHolder.rlSwmBox.setVisibility(View.GONE);
            viewHolder.txtSlwmC = view.findViewById(R.id.swm_collection);*/

            /* =====================================work history offline item===================================================*/

            //Horticulture and CandD waste
            viewHolder.liHortCadCBox = view.findViewById(R.id.li_hort_cad_box);
            viewHolder.liHortCadCBox.setVisibility(View.GONE);
            viewHolder.txtCandDC = view.findViewById(R.id.cad_collection);
            viewHolder.txtHortC = view.findViewById(R.id.hort_collection);

            //House and Commercial
            viewHolder.liHouseCommercialCBox = view.findViewById(R.id.li_house_cW_box);
            viewHolder.liHouseCommercialCBox.setVisibility(View.GONE);

            viewHolder.liHouseColBox = view.findViewById(R.id.liHouseColBox);
            viewHolder.liHouseColBox.setVisibility(View.GONE);
            viewHolder.txtHouseC = view.findViewById(R.id.house_collection);

            viewHolder.liCommercialColBox = view.findViewById(R.id.liCommercialColBox);
            viewHolder.liCommercialColBox.setVisibility(View.GONE);
            viewHolder.txtCwC = view.findViewById(R.id.commer_collection);

            //Ctpt and SLWM collection
            viewHolder.liCtptSwmCBox = view.findViewById(R.id.li_ctpt_swm_box);
            viewHolder.liCtptSwmCBox.setVisibility(View.GONE);

            viewHolder.liCtptColBox = view.findViewById(R.id.ctptLayout);
            viewHolder.liCtptColBox.setVisibility(View.GONE);
            viewHolder.txtCtptC = view.findViewById(R.id.ctpt_collection);

            viewHolder.liSWMColBox = view.findViewById(R.id.slwm_layout);
            viewHolder.liSWMColBox.setVisibility(View.GONE);
            viewHolder.txtSlwmC = view.findViewById(R.id.swm_collection);

            // Liquid and street
            viewHolder.liLiqStreetCBox = view.findViewById(R.id.li_liq_street_box);
            viewHolder.liLiqStreetCBox.setVisibility(View.GONE);

            viewHolder.liLiquidColBox = view.findViewById(R.id.liLqiColBox);
            viewHolder.liLiquidColBox.setVisibility(View.GONE);
            viewHolder.txtLiquidC = view.findViewById(R.id.lwc_collection);

            viewHolder.liStreetColBox = view.findViewById(R.id.liStreetColBox);
            viewHolder.liStreetColBox.setVisibility(View.GONE);
            viewHolder.txtStreetC = view.findViewById(R.id.ss_collection);

       /* ===============================end=========================================================*/

            view.setTag(viewHolder);

        } else {
            view = convertView;
        }
        holder = (InflateOfflineWorkAdapter.ViewHolder) view.getTag();

        if (!AUtils.isNull(historyPojoList) && !historyPojoList.isEmpty()) {
            TableDataCountPojo.WorkHistory workHistoryPojo = historyPojoList.get(position);

            Log.e(TAG, "getView: WorkHistory==>" + workHistoryPojo);
            holder.date.setText(AUtils.extractDate(workHistoryPojo.getDate()));
            holder.month.setText(AUtils.extractMonth(workHistoryPojo.getDate()));

            if (empType.matches("N") || empType.isEmpty()) {

                /*
                holder.liNormalBuildingCBox.setVisibility(View.GONE);
                holder.liSlumCBox.setVisibility(View.GONE);
                holder.txtResC.setText(workHistoryPojo.getResidentialCollection());
                holder.txtResBC.setText(workHistoryPojo.getResidentialBCollection());
                holder.liResSlumCommercialCBox.setVisibility(View.VISIBLE);
                holder.txtResSlumC.setText(workHistoryPojo.getResidentialSCollection());
                holder.txtResCwC.setText(workHistoryPojo.getCommertialCollection());
                holder.liHortCadCBox.setVisibility(View.VISIBLE);
                holder.txtCandDC.setText(workHistoryPojo.getCADCollection());
                holder.txtHortC.setText(workHistoryPojo.getHorticultureCollection());
                holder.liLiqStreetCBox.setVisibility(View.GONE);
                holder.liDumpHouseCBox.setVisibility(View.VISIBLE);
                holder.liHouseColBox.setVisibility(View.VISIBLE);
                holder.txtHouseC.setText(workHistoryPojo.getHouseCollection());
                holder.liDumpColBox.setVisibility(View.GONE);
                holder.txtDumpC.setText(workHistoryPojo.getDumpYardCollection());
                holder.liCtptSwmCBox.setVisibility(View.VISIBLE);
                holder.txtCtptC.setText(workHistoryPojo.getCtptCollection());
                holder.txtSlwmC.setText(workHistoryPojo.getSwmCollection());*/

            /*
                //ite work history
                holder.rlLiquidBox.setVisibility(View.GONE);
                holder.rlStreetBox.setVisibility(View.GONE);

                holder.rlDumpBox.setVisibility(View.GONE);
                holder.txtDumpC.setText(workHistoryPojo.getDumpYardCollection());

                holder.rlNormalBox.setVisibility(View.VISIBLE);
                holder.txtResC.setText(workHistoryPojo.getResidentialCollection());

                holder.rlResBuildingBox.setVisibility(View.VISIBLE);
                holder.txtResBC.setText(workHistoryPojo.getResidentialBCollection());

                holder.rlResSlumBox.setVisibility(View.VISIBLE);
                holder.txtResSlumC.setText(workHistoryPojo.getResidentialSCollection());

                holder.rlCommercialBox.setVisibility(View.VISIBLE);
                holder.txtResCwC.setText(workHistoryPojo.getCommertialCollection());

                holder.rlCadBox.setVisibility(View.VISIBLE);
                holder.txtCandDC.setText(workHistoryPojo.getCADCollection());

                holder.rlHortBox.setVisibility(View.VISIBLE);
                holder.txtHortC.setText(workHistoryPojo.getHorticultureCollection());

                holder.rlCtptBox.setVisibility(View.GONE);
                holder.txtCtptC.setText(workHistoryPojo.getCtptCollection());

                holder.rlHouseBox.setVisibility(View.VISIBLE);
                holder.txtHouseC.setText(workHistoryPojo.getHouseCollection());

                holder.rlSwmBox.setVisibility(View.VISIBLE);
                holder.txtSlwmC.setText(workHistoryPojo.getSwmCollection());*/


                //work history offline item
                if (Prefs.contains(AUtils.PREFS.EMP_SUB_TYPE)) {
                    if (Prefs.getString(AUtils.PREFS.EMP_SUB_TYPE, null).matches("CT")) {
                        holder.liCtptSwmCBox.setVisibility(View.VISIBLE);
                        holder.liCtptColBox.setVisibility(View.VISIBLE);
                        holder.txtCtptC.setText(workHistoryPojo.getCtptCollection());
                        holder.liSWMColBox.setVisibility(View.GONE);

                        holder.liLiqStreetCBox.setVisibility(View.GONE);
                        holder.liHortCadCBox.setVisibility(View.GONE);
                        holder.liHouseCommercialCBox.setVisibility(View.GONE);
                    } else {

                        holder.liLiqStreetCBox.setVisibility(View.GONE);

                        holder.liCtptSwmCBox.setVisibility(View.VISIBLE);
                        holder.liCtptColBox.setVisibility(View.GONE);
                        holder.txtCtptC.setText(workHistoryPojo.getCtptCollection());

                        holder.liSWMColBox.setVisibility(View.VISIBLE);
                        holder.txtSlwmC.setText(workHistoryPojo.getSwmCollection());

                        holder.liHortCadCBox.setVisibility(View.VISIBLE);
                        holder.txtCandDC.setText(workHistoryPojo.getCADCollection());
                        holder.txtHortC.setText(workHistoryPojo.getHorticultureCollection());

                        holder.liHouseCommercialCBox.setVisibility(View.VISIBLE);

                        holder.liHouseColBox.setVisibility(View.VISIBLE);
                        holder.txtHouseC.setText(workHistoryPojo.getHouseCollection());

                        holder.liCommercialColBox.setVisibility(View.VISIBLE);
                        holder.txtCwC.setText(workHistoryPojo.getCommertialCollection());
                    }
                }

            } else if (empType.matches("L")) {
                /*holder.liNormalBuildingCBox.setVisibility(View.GONE);
                holder.liResSlumCommercialCBox.setVisibility(View.GONE);
                holder.liHortCadCBox.setVisibility(View.GONE);
                holder.liLiqStreetCBox.setVisibility(View.VISIBLE);
                holder.liLiquidColBox.setVisibility(View.VISIBLE);
                holder.liStreetColBox.setVisibility(View.GONE);
                holder.liDumpHouseCBox.setVisibility(View.GONE);
                holder.liCtptSwmCBox.setVisibility(View.GONE);
                holder.txtLiquidC.setText(workHistoryPojo.getLiquidCollection());*/

           /*
                //ite work history
                holder.rlNormalBox.setVisibility(View.GONE);
                holder.rlResBuildingBox.setVisibility(View.GONE);
                holder.rlResSlumBox.setVisibility(View.GONE);
                holder.rlCommercialBox.setVisibility(View.GONE);
                holder.rlCadBox.setVisibility(View.GONE);
                holder.rlHortBox.setVisibility(View.GONE);
                holder.rlCtptBox.setVisibility(View.GONE);
                holder.rlDumpBox.setVisibility(View.GONE);
                holder.rlHouseBox.setVisibility(View.GONE);
                holder.rlStreetBox.setVisibility(View.GONE);
                holder.rlSwmBox.setVisibility(View.GONE);

                holder.rlLiquidBox.setVisibility(View.VISIBLE);
                holder.txtLiquidC.setText(workHistoryPojo.getLiquidCollection());*/

                //work history offline item
                holder.liLiqStreetCBox.setVisibility(View.VISIBLE);
                holder.liLiquidColBox.setVisibility(View.VISIBLE);
                holder.txtLiquidC.setText(workHistoryPojo.getLiquidCollection());
                holder.liStreetColBox.setVisibility(View.GONE);

                holder.liCtptSwmCBox.setVisibility(View.GONE);
                holder.liHortCadCBox.setVisibility(View.GONE);
                holder.liHouseCommercialCBox.setVisibility(View.GONE);

            } else if (empType.matches("S")) {

               /* holder.liNormalBuildingCBox.setVisibility(View.GONE);
                holder.liResSlumCommercialCBox.setVisibility(View.GONE);
                holder.liHortCadCBox.setVisibility(View.GONE);
                holder.liLiqStreetCBox.setVisibility(View.VISIBLE);
                holder.liStreetColBox.setVisibility(View.VISIBLE);
                holder.liLiquidColBox.setVisibility(View.GONE);
                holder.liDumpHouseCBox.setVisibility(View.GONE);
                holder.liCtptSwmCBox.setVisibility(View.GONE);
                holder.txtStreetC.setText(workHistoryPojo.getStreetCollection());*/
        /*
                //ite work history
                holder.rlNormalBox.setVisibility(View.GONE);
                holder.rlResBuildingBox.setVisibility(View.GONE);
                holder.rlResSlumBox.setVisibility(View.GONE);
                holder.rlCommercialBox.setVisibility(View.GONE);
                holder.rlCadBox.setVisibility(View.GONE);
                holder.rlHortBox.setVisibility(View.GONE);
                holder.rlCtptBox.setVisibility(View.GONE);
                holder.rlDumpBox.setVisibility(View.GONE);
                holder.rlHouseBox.setVisibility(View.GONE);
                holder.rlLiquidBox.setVisibility(View.GONE);
                holder.rlSwmBox.setVisibility(View.GONE);

                holder.rlStreetBox.setVisibility(View.VISIBLE);
                holder.txtStreetC.setText(workHistoryPojo.getStreetCollection());*/

                //work history offline item
                holder.liLiqStreetCBox.setVisibility(View.VISIBLE);
                holder.liLiquidColBox.setVisibility(View.GONE);
                holder.liStreetColBox.setVisibility(View.VISIBLE);
                holder.txtStreetC.setText(workHistoryPojo.getStreetCollection());

                holder.liCtptSwmCBox.setVisibility(View.GONE);
                holder.liHortCadCBox.setVisibility(View.GONE);
                holder.liHouseCommercialCBox.setVisibility(View.GONE);

            }

        }

        return view;
    }

    private class ViewHolder {

        private TextView date;
        private TextView month;

        /* =================================layout history card================================================================*/

        /*
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
        private LinearLayout liSlumCBox;*/

        /* =================================item work history================================================================*/

      /*  //box
        private RelativeLayout rlNormalBox, rlResBuildingBox, rlResSlumBox, rlCommercialBox,rlLiquidBox, rlStreetBox;
        private RelativeLayout rlDumpBox, rlHouseBox, rlCtptBox, rlSwmBox, rlHortBox, rlCadBox;

        //content
        private TextView txtResC, txtResBC;
        private TextView txtResSlumC, txtResCwC;
        private TextView txtCandDC, txtHortC;
        private TextView txtLiquidC, txtStreetC;
        private TextView txtDumpC, txtHouseC;
        private TextView txtCtptC, txtSlwmC;*/

        /* =====================================work history offline item===================================================*/

        //box
        private LinearLayout liHouseCommercialCBox, liCtptSwmCBox , liHortCadCBox, liLiqStreetCBox;

        //content
        private TextView txtHouseC, txtCwC;
        private TextView txtCandDC, txtHortC;
        private TextView txtCtptC, txtSlwmC;
        private TextView txtLiquidC, txtStreetC;

        // liquid and street column
        private LinearLayout liLiquidColBox, liStreetColBox;
        private LinearLayout liHouseColBox, liCommercialColBox;
        private LinearLayout liCtptColBox, liSWMColBox;

    }

}
