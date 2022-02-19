package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.activity.WasteHistoryDetailsActivity;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementHistoryPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;

import java.util.List;

/**
 * Created by Ayan Dey on 3/2/20.
 */
public class InflateWasteHistoryUIAdapter extends RecyclerView.Adapter<InflateWasteHistoryUIAdapter.ViewHolderWasteHistory> {

    private final Context mContext;
    private final boolean performClick;
    private List<WasteManagementHistoryPojo> historyList;

    public InflateWasteHistoryUIAdapter(Context mContext, boolean performClick) {
        this.mContext = mContext;
        this.performClick = performClick;
    }

    public void setWasteHistoryList(List<WasteManagementHistoryPojo> wasteHistoryList) {
        this.historyList = wasteHistoryList;
    }

    @NonNull
    @Override
    public ViewHolderWasteHistory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.layout_waste_history_card, parent, false);
        return new ViewHolderWasteHistory(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderWasteHistory holder, int position) {
        WasteManagementHistoryPojo pojo = historyList.get(position);

        holder.textViewCount.setText(String.valueOf(pojo.getGarbageCount()));
        holder.textViewDate.setText(AUtils.extractDate(pojo.getDate()));
        holder.textViewMonth.setText(AUtils.extractMonth(pojo.getDate()));
    }

    @Override
    public int getItemCount() {
        return (historyList == null) ? 0 : historyList.size();
    }

    class ViewHolderWasteHistory extends RecyclerView.ViewHolder {

        private final TextView textViewDate;
        private final TextView textViewMonth;
        private final TextView textViewCount;

        ViewHolderWasteHistory(@NonNull View itemView) {
            super(itemView);
            textViewDate = itemView.findViewById(R.id.history_date_txt);
            textViewMonth = itemView.findViewById(R.id.history_month_txt);
            textViewCount = itemView.findViewById(R.id.waste_details);
            ConstraintLayout constraintLayoutBase = itemView.findViewById(R.id.layout_base_constraint);

            constraintLayoutBase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (performClick) {
                        Intent intent = new Intent(mContext, WasteHistoryDetailsActivity.class);
                        intent.putExtra(AUtils.HISTORY_DETAILS_DATE, historyList.get(getAdapterPosition()).getDate());
                        mContext.startActivity(intent);
                    }
                }
            });
        }
    }
}
