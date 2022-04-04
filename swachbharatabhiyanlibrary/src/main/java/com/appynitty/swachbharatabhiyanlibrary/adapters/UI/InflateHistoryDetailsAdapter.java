package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WorkHistoryDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

/**
 * Created by Ayan Dey on 25/10/18.
 * Updated Ui with this file : Rahul Rokade on 03/25/22
 */

public class InflateHistoryDetailsAdapter extends ArrayAdapter<WorkHistoryDetailPojo> {

    private final List<WorkHistoryDetailPojo> workHistoryDetailPojoList;
    private final Context context;
    private View view;
    private ViewHolder holder;
    private final String empType = Prefs.getString(AUtils.PREFS.EMPLOYEE_TYPE, null);
    private String empSubType = Prefs.getString(AUtils.PREFS.EMP_SUB_TYPE, null);

    public InflateHistoryDetailsAdapter(@NonNull Context context, @NonNull List<WorkHistoryDetailPojo> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
        this.context = context;
        this.workHistoryDetailPojoList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if (convertView == null) {

            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.layout_history_detail_card, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.time = view.findViewById(R.id.history_details_time);
            viewHolder.id = view.findViewById(R.id.history_details_id);
            viewHolder.vehicleNo = view.findViewById(R.id.history_details_vehicle);
            viewHolder.area = view.findViewById(R.id.history_details_area);
            viewHolder.name = view.findViewById(R.id.history_details_name);
            viewHolder.liHistoryDetail = view.findViewById(R.id.li_history_detail);
            viewHolder.liHistoryDetail.setVisibility(View.GONE);

            view.setTag(viewHolder);

            /**Added by Swapnil*/
            viewHolder.time.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String text = viewHolder.time.getText().toString();
                    AUtils.success(context, text);

                }
            });  //End of addition

        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(workHistoryDetailPojoList) && !workHistoryDetailPojoList.isEmpty()) {
            WorkHistoryDetailPojo workHistoryDetailPojo = workHistoryDetailPojoList.get(position);
            //added by Rahul
            if (empType.matches("N") || empType.isEmpty()) {
                if (empSubType.matches("CT")){
                   if (workHistoryDetailPojo.getType().equals("10")){
                       holder.liHistoryDetail.setVisibility(View.VISIBLE);
                       holder.time.setBackgroundResource(R.drawable.rounded_ctpt_button);
                       holder.time.setPadding(0, 0, 0, 0);
                       holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_ctpt_waste_id), workHistoryDetailPojo.getRefid()));
                       holder.time.setText(workHistoryDetailPojo.getTime());

                       holder.vehicleNo.setVisibility(View.GONE);
                       holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                       holder.area.setText(workHistoryDetailPojo.getAreaName());
                       holder.name.setText(workHistoryDetailPojo.getName());
                   }

                }
                else {

                    if (workHistoryDetailPojo.getType().equals("2")) {
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_pink_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_point_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }else if (workHistoryDetailPojo.getType().equals("1")) {
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_brown_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_house_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }else if (workHistoryDetailPojo.getType().equals("6")) {
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_gray_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_CandD_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }else if (workHistoryDetailPojo.getType().equals("12")) {
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_gray_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_CandD_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }else if (workHistoryDetailPojo.getType().equals("7")) {
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_green_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_Hort_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }else if (workHistoryDetailPojo.getType().equals("13")) {
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_green_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_Hort_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }else if (workHistoryDetailPojo.getType().equals("9")) {
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_red_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_comercial_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }else if (workHistoryDetailPojo.getType().equals("11")) {
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_swm_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_swm_waste_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }else if (workHistoryDetailPojo.getType().equals("3")){
                        holder.liHistoryDetail.setVisibility(View.VISIBLE);
                        holder.time.setBackgroundResource(R.drawable.rounded_orange_button);
                        holder.time.setPadding(0, 0, 0, 0);
                        holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_dump_yard_id), workHistoryDetailPojo.getRefid()));
                        holder.time.setText(workHistoryDetailPojo.getTime());
                        holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                        holder.area.setText(workHistoryDetailPojo.getAreaName());
                        holder.name.setText(workHistoryDetailPojo.getName());
                    }
                }

            }
            else if (empType.matches("S")){
                if (workHistoryDetailPojo.getType().equals("5")) {
                    holder.liHistoryDetail.setVisibility(View.VISIBLE);
                    holder.time.setBackgroundResource(R.drawable.rounded_pink_button);
                    holder.time.setPadding(0, 0, 0, 0);
                    holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_street_sweep_id), workHistoryDetailPojo.getRefid()));
                    holder.time.setText(workHistoryDetailPojo.getTime());
                    holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                    holder.area.setText(workHistoryDetailPojo.getAreaName());
                    holder.name.setText(workHistoryDetailPojo.getName());
                }

            }
            else if (empType.matches("L")){
                if (workHistoryDetailPojo.getType().equals("4")) {
                    holder.liHistoryDetail.setVisibility(View.VISIBLE);
                    holder.time.setBackgroundResource(R.drawable.rounded_cyan_button);
                    holder.time.setPadding(0, 0, 0, 0);
                    holder.id.setText(String.format("%s %s", context.getResources().getString(R.string.string_liquid_waste_id), workHistoryDetailPojo.getRefid()));
                    holder.time.setText(workHistoryDetailPojo.getTime());
                    holder.vehicleNo.setText(String.format("%s %s", context.getResources().getString(R.string.vehicle_number_txt), workHistoryDetailPojo.getVehicleNumber()));
                    holder.area.setText(workHistoryDetailPojo.getAreaName());
                    holder.name.setText(workHistoryDetailPojo.getName());
                }

            }

        }

        return view;
    }

    private class ViewHolder {

        private TextView time;
        private TextView id;
        private TextView vehicleNo;
        private TextView area;
        private TextView name;

        private LinearLayout liHistoryDetail;
    }


}
