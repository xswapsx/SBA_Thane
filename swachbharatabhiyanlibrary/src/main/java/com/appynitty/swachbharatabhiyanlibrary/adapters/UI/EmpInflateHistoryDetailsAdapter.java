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
import com.appynitty.swachbharatabhiyanlibrary.pojos.EmpWorkHistoryDetailPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.List;

/**
 * Created by Ayan Dey on 25/10/18.
 */

public class EmpInflateHistoryDetailsAdapter extends ArrayAdapter<EmpWorkHistoryDetailPojo> {

    private final List<EmpWorkHistoryDetailPojo> workHistoryDetailPojoList;
    private final Context context;
    private View view;
    private ViewHolder holder;

    public EmpInflateHistoryDetailsAdapter(@NonNull Context context, @NonNull List<EmpWorkHistoryDetailPojo> objects) {
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
            view = inflater.inflate(R.layout.layout_history_detail_card_emp, null);
            final ViewHolder viewHolder = new ViewHolder();
            viewHolder.time = view.findViewById(R.id.history_details_time);
            viewHolder.id = view.findViewById(R.id.history_details_name);
            viewHolder.liEmpHistoryBox = view.findViewById(R.id.li_emp_work_details_box);
            /***added by rahul**/
            viewHolder.house_num = view.findViewById(R.id.house_n);

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
            EmpWorkHistoryDetailPojo workHistoryDetailPojo = workHistoryDetailPojoList.get(position);

            if (workHistoryDetailPojo.getType().equals("2")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_pink_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText(context.getResources().getString(R.string.string_point_id));
                holder.house_num.setText(workHistoryDetailPojo.getPointNo());
            } /*else if (workHistoryDetailPojo.getType().equals("1")) {
                holder.time.setBackgroundResource(R.drawable.rounded_blue_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.id.setText( context.getResources().getString(R.string.house_id_txt));
                holder.house_num.setText(workHistoryDetailPojo.getHouseNo());
            }*/ else if (workHistoryDetailPojo.getType().equals("4")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_cyan_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText( context.getResources().getString(R.string.string_liquid_waste_id));
                holder.house_num.setText(workHistoryDetailPojo.getLiquidWasteNo());
            } else if (workHistoryDetailPojo.getType().equals("5")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_pink_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText( context.getResources().getString(R.string.string_street_sweep_id));
                holder.house_num.setText(workHistoryDetailPojo.getStreetWasteNo());
            } else if (workHistoryDetailPojo.getType().equals("3")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_orange_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText( context.getResources().getString(R.string.string_dump_yard_id));
                holder.house_num.setText(workHistoryDetailPojo.getDumpYardNo());
            }
            //rahul
            else if (workHistoryDetailPojo.getType().equals("6")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_orange_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText(context.getResources().getString(R.string.string_res_id));
                holder.house_num.setText(workHistoryDetailPojo.getResidentNO());
            }
            else if (workHistoryDetailPojo.getType().equals("7")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_brown_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText(context.getResources().getString(R.string.string_res_b_id));
                holder.house_num.setText(workHistoryDetailPojo.getResidentBNO());
            }
            else if (workHistoryDetailPojo.getType().equals("9")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_red_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText( context.getResources().getString(R.string.string_comercial_id));
                holder.house_num.setText(workHistoryDetailPojo.getCommercialNO());
            }
            else if (workHistoryDetailPojo.getType().equals("8")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_yellow_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText( context.getResources().getString(R.string.string_res_s_id));
                holder.house_num.setText(workHistoryDetailPojo.getResidentSNO());
            }
            else if (workHistoryDetailPojo.getType().equals("10")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_ctpt_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText( context.getResources().getString(R.string.string_ctpt_waste_id));
                holder.house_num.setText(workHistoryDetailPojo.getCTPTNO());
            }
            else if (workHistoryDetailPojo.getType().equals("11")) {
                holder.time.setVisibility(View.VISIBLE);
                holder.time.setBackgroundResource(R.drawable.rounded_swm_button);
                holder.time.setPadding(0, 0, 0, 0);
                holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
                holder.id.setText( context.getResources().getString(R.string.string_swm_waste_id));
                holder.house_num.setText(workHistoryDetailPojo.getSWMNO());
            }

//            holder.time.setText(workHistoryDetailPojo.getTime());
           // holder.time.setText(AUtils.getEmpTimeLineFormat(workHistoryDetailPojo.getTime()));
        }

        return view;
    }

    private class ViewHolder {

        private TextView time;
        private TextView id;
        private TextView house_num;
        private LinearLayout liEmpHistoryBox;
    }


}
