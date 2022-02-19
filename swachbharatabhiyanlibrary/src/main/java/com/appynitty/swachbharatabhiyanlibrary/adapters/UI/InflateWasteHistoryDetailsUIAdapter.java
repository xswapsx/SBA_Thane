package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementHistoryPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.List;

/**
 * Created by Ayan Dey on 3/2/20.
 */
public class InflateWasteHistoryDetailsUIAdapter extends RecyclerView.Adapter<InflateWasteHistoryDetailsUIAdapter.ViewHolderWasteHistoryDetails> {

    private final Context mContext;
    private List<WasteManagementHistoryPojo> historyList;

    public InflateWasteHistoryDetailsUIAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setWasteHistoryList(List<WasteManagementHistoryPojo> wasteHistoryList) {
        this.historyList = wasteHistoryList;
    }

    @NonNull
    @Override
    public ViewHolderWasteHistoryDetails onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_waste_management_details, parent, false);
        return new ViewHolderWasteHistoryDetails(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWasteHistoryDetails holder, int position) {
        WasteManagementHistoryPojo pojo = historyList.get(position);

        holder.textViewCategory.setText(pojo.getCategory());
        holder.textViewType.setText(pojo.getSubCategory());
        holder.textViewWeight.setText(String.valueOf(pojo.getWeight()));
        holder.textViewEntryTime.setText(pojo.getTime());
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    class ViewHolderWasteHistoryDetails extends RecyclerView.ViewHolder {

        private final TextView textViewCategory;
        private final TextView textViewType;
        private final TextView textViewWeight;
        private final TextView textViewEntryTime;

        ViewHolderWasteHistoryDetails(@NonNull View itemView) {
            super(itemView);
            textViewCategory = itemView.findViewById(R.id.text_view_waste_category);
            textViewType = itemView.findViewById(R.id.text_view_waste_type);
            textViewWeight = itemView.findViewById(R.id.text_view_waste_quantity);
            textViewEntryTime = itemView.findViewById(R.id.waste_entry_time);

            View dividerView = itemView.findViewById(R.id.separator_view);
            LinearLayout layoutButtonBar = itemView.findViewById(R.id.button_bar_layout);

            dividerView.setVisibility(View.GONE);
            layoutButtonBar.setVisibility(View.GONE);
            textViewEntryTime.setVisibility(View.VISIBLE);

        }
    }
}
