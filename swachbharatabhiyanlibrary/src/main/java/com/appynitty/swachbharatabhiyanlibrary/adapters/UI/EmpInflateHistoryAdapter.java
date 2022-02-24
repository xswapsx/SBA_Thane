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
            viewHolder.houseCollection = view.findViewById(R.id.house_collection);
//            viewHolder.gpCollection = view.findViewById(R.id.gp_collection);
            viewHolder.lwCollection = view.findViewById(R.id.lwc_collection);
            viewHolder.ssCollection = view.findViewById(R.id.ss_collection);
            viewHolder.dyCollection = view.findViewById(R.id.dy_collection);
            //Rahul
            viewHolder.resNormalCollection = view.findViewById(R.id.res_normal_collection);
            viewHolder.resBuildCollection = view.findViewById(R.id.res_b_collection);
            viewHolder.resSlumCollection = view.findViewById(R.id.res_s_collection);
            viewHolder.commerCollection = view.findViewById(R.id.commer_collection);
            viewHolder.commerCollection = view.findViewById(R.id.commer_collection);
            viewHolder.cadCollection = view.findViewById(R.id.cad_collection);
            viewHolder.hortCollection = view.findViewById(R.id.hort_collection);
            viewHolder.ctptCollection = view.findViewById(R.id.ctpt_collection);
            viewHolder.swmCollection = view.findViewById(R.id.swm_collection);
            viewHolder.liCtptSwmBox = view.findViewById(R.id.li_ctpt_swm_box);
            viewHolder.liCtptSwmBox.setVisibility(View.VISIBLE);
            view.setTag(viewHolder);

        } else {
            view = convertView;
        }
        holder = (ViewHolder) view.getTag();

        if (!AUtils.isNull(historyPojoList) && !historyPojoList.isEmpty()) {
            TableDataCountPojo.WorkHistory workHistoryPojo = historyPojoList.get(position);
            holder.date.setText(AUtils.extractDateEmp(workHistoryPojo.getDate()));
            holder.month.setText(AUtils.extractMonthEmp(workHistoryPojo.getDate()));
            holder.houseCollection.setText(workHistoryPojo.getHouseCollection());
//            holder.gpCollection.setText(workHistoryPojo.getPointCollection());
            holder.lwCollection.setText(workHistoryPojo.getLiquidCollection());
            holder.ssCollection.setText(workHistoryPojo.getStreetCollection());
            holder.dyCollection.setText(workHistoryPojo.getDumpYardCollection());
            //rahul
            holder.resNormalCollection.setText(workHistoryPojo.getResidentialCollection());
            holder.resBuildCollection.setText(workHistoryPojo.getResidentialBCollection());
            holder.resSlumCollection.setText(workHistoryPojo.getResidentialSCollection());
            holder.commerCollection.setText(workHistoryPojo.getCommertialCollection());
            holder.cadCollection.setText(workHistoryPojo.getCADCollection());
            holder.hortCollection.setText(workHistoryPojo.getHorticultureCollection());
            holder.ctptCollection.setText(workHistoryPojo.getCtptCollection());
            holder.swmCollection.setText(workHistoryPojo.getSwmCollection());
        }

        return view;
    }

    private class ViewHolder {

        private TextView date;
        private TextView month;
        private TextView houseCollection;
        //        private TextView gpCollection;
        private TextView dyCollection;
        private TextView lwCollection;
        private TextView ssCollection;
        //rahul
        private TextView resNormalCollection;
        private TextView resBuildCollection;
        private TextView resSlumCollection;
        private TextView commerCollection;
        private TextView cadCollection;
        private TextView hortCollection;
        private TextView ctptCollection;
        private TextView swmCollection;
        private LinearLayout liCtptSwmBox;
    }


}
