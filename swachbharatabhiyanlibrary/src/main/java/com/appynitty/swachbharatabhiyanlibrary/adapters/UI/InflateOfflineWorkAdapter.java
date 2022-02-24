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
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

/**
 * Created by Swapnil Lanjewar on 18/01/2022.
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
            view = inflater.inflate(R.layout.layout_history_card, null);
            final InflateOfflineWorkAdapter.ViewHolder viewHolder = new InflateOfflineWorkAdapter.ViewHolder();
            viewHolder.date = view.findViewById(R.id.history_date_txt);
            viewHolder.month = view.findViewById(R.id.history_month_txt);
            viewHolder.houseCollection = view.findViewById(R.id.house_collection);
            viewHolder.dyCollection = view.findViewById(R.id.dy_collection);
            viewHolder.dyCollectionlbl = view.findViewById(R.id.dy_collection_lbl);
            viewHolder.houseCollectionTitle = view.findViewById(R.id.house_collection_lbl);
            viewHolder.liquidCollection = view.findViewById(R.id.lwc_collection);
            viewHolder.liquidCollectionLbl = view.findViewById(R.id.lwc_collection_lbl);
            viewHolder.streetCollection = view.findViewById(R.id.ss_collection);
            viewHolder.streetCollectionLbl = view.findViewById(R.id.ss_collection_lbl);
            viewHolder.resBuildingCollectionLbl = view.findViewById(R.id.res_b_collection_lbl);
            viewHolder.resSlumCollectionLbl = view.findViewById(R.id.res_s_collection_lbl);
            //added code by rahul
            viewHolder.resNormalCollection = view.findViewById(R.id.res_normal_collection);
            viewHolder.resBuildingCollection = view.findViewById(R.id.res_b_collection);
            viewHolder.resSlumCollection = view.findViewById(R.id.res_s_collection);
            viewHolder.commercialCollection = view.findViewById(R.id.commer_collection);
            viewHolder.liHortCadBox = view.findViewById(R.id.li_hort_cad_box);
            viewHolder.liHouseColBox = view.findViewById(R.id.li_house_col_box);
            viewHolder.liHortCadBox.setVisibility(View.VISIBLE);
//            viewHolder.liHouseColBox.setVisibility(View.VISIBLE);
            viewHolder.cadCollection = view.findViewById(R.id.cad_collection);
            viewHolder.hortCollection = view.findViewById(R.id.hort_collection);

            view.setTag(viewHolder);



        } else {
            view = convertView;
        }
        holder = (InflateOfflineWorkAdapter.ViewHolder) view.getTag();

        if (!AUtils.isNull(historyPojoList) && !historyPojoList.isEmpty()) {
            TableDataCountPojo.WorkHistory workHistoryPojo = historyPojoList.get(position);

            Log.e(TAG, "getView: WorkHistory==>" + workHistoryPojo);

            if (empType.matches("N") || empType.isEmpty()) {
                holder.date.setText(AUtils.extractDate(workHistoryPojo.getDate()));
                holder.month.setText(AUtils.extractMonth(workHistoryPojo.getDate()));
                holder.houseCollection.setText(workHistoryPojo.getHouseCollection());
                holder.dyCollection.setText(workHistoryPojo.getDumpYardCollection());
                if (AUtils.isInternetAvailable(AUtils.mainApplicationConstant)) {
//                    Toast.makeText(context, "Its online!", Toast.LENGTH_SHORT).show();
                    holder.resBuildingCollection.setText(workHistoryPojo.getResidentialBCollection());
                    holder.resSlumCollection.setText(workHistoryPojo.getResidentialSCollection());
                } else {
//                    Toast.makeText(context, "Its offline!", Toast.LENGTH_SHORT).show();
                    holder.resBuildingCollection.setText("0");
                    holder.resSlumCollection.setText("0");
                }
                //added by rahul
                holder.resNormalCollection.setText(workHistoryPojo.getResidentialCollection());

                holder.commercialCollection.setText(workHistoryPojo.getCommertialCollection());
                holder.cadCollection.setText(workHistoryPojo.getCADCollection());
                holder.hortCollection.setText(workHistoryPojo.getHorticultureCollection());

                holder.liquidCollection.setVisibility(View.GONE);
                holder.liquidCollectionLbl.setVisibility(View.GONE);
                holder.streetCollection.setVisibility(View.GONE);
                holder.streetCollectionLbl.setVisibility(View.GONE);

            } else if (empType.matches("L")) {
                holder.houseCollectionTitle.setText(R.string.liquid_collection);
                holder.houseCollection.setText(workHistoryPojo.getLiquidCollection());
                holder.date.setText(AUtils.extractDate(workHistoryPojo.getDate()));
                holder.month.setText(AUtils.extractMonth(workHistoryPojo.getDate()));
                holder.dyCollectionlbl.setVisibility(View.GONE);
                holder.dyCollection.setVisibility(View.GONE);
                holder.liquidCollection.setVisibility(View.GONE);
                holder.liquidCollectionLbl.setVisibility(View.GONE);
                holder.streetCollection.setVisibility(View.GONE);
                holder.streetCollectionLbl.setVisibility(View.GONE);
            } else if (empType.matches("S")) {
                holder.houseCollectionTitle.setText(R.string.street_collection);
                holder.houseCollection.setText(workHistoryPojo.getStreetCollection());
                holder.date.setText(AUtils.extractDate(workHistoryPojo.getDate()));
                holder.month.setText(AUtils.extractMonth(workHistoryPojo.getDate()));
                holder.dyCollectionlbl.setVisibility(View.GONE);
                holder.dyCollection.setVisibility(View.GONE);
                holder.liquidCollection.setVisibility(View.GONE);
                holder.liquidCollectionLbl.setVisibility(View.GONE);
                holder.streetCollection.setVisibility(View.GONE);
                holder.streetCollectionLbl.setVisibility(View.GONE);
            }

        }

        return view;
    }

    private class ViewHolder {

        private TextView date;
        private TextView month;
        private TextView houseCollection;
        private TextView gpCollection;
        private TextView dyCollection;
        private TextView dyCollectionlbl;
        private TextView houseCollectionTitle;
        private TextView pointCollectionTitle;
        private TextView liquidCollection;
        private TextView liquidCollectionLbl;
        private TextView streetCollection;
        private TextView streetCollectionLbl;
        private TextView resBuildingCollectionLbl;
        private TextView resSlumCollectionLbl;
        //added code by rahul
        private TextView resNormalCollection;
        private TextView resBuildingCollection;
        private TextView resSlumCollection;
        private TextView commercialCollection;
        private TextView cadCollection;
        private TextView hortCollection;
        private LinearLayout liHortCadBox;
        private LinearLayout liHouseColBox;
    }

}
