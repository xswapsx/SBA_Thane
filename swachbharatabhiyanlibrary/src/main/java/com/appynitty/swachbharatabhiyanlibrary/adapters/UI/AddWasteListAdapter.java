package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.pojos.WasteManagementPojo;

import java.util.List;

/**
 * Created by Ayan Dey on 25/1/20.
 */
public class AddWasteListAdapter extends RecyclerView.Adapter<AddWasteListAdapter.AddWasteListViewHolder> {

    private final Context context;
    private List<WasteManagementPojo> wasteManagementList;
    private WasteListActionListener wasteListActionListener;

    public AddWasteListAdapter(Context context) {
        this.context = context;
    }

    public void setWasteManagementList(List<WasteManagementPojo> list){
        this.wasteManagementList = list;
    }

    public void setWasteListActionListener(WasteListActionListener wasteListActionListener) {
        this.wasteListActionListener = wasteListActionListener;
    }

    @NonNull
    @Override
    public AddWasteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_waste_management_details, parent, false);
        return new AddWasteListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddWasteListViewHolder holder, int position) {
        WasteManagementPojo pojo = wasteManagementList.get(position);
        holder.textViewWasteType.setText(pojo.getWasteTypeName());
        holder.textViewWasteCategory.setText(pojo.getWasteCategoryName());

        String quantity = String.format("%s %s", pojo.getWeight(), pojo.getWasteUnitName());
        holder.textViewWasteQuantity.setText(quantity);
    }

    @Override
    public int getItemCount() {
        return wasteManagementList.size();
    }

    public class AddWasteListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final TextView textViewWasteType;
        private final TextView textViewWasteCategory;
        private final TextView textViewWasteQuantity;

        public AddWasteListViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewWasteType = itemView.findViewById(R.id.text_view_waste_type);
            textViewWasteCategory = itemView.findViewById(R.id.text_view_waste_category);
            textViewWasteQuantity = itemView.findViewById(R.id.text_view_waste_quantity);

            Button buttonViewRemove = itemView.findViewById(R.id.button_view_remove);
            buttonViewRemove.setOnClickListener(this);

            Button buttonEditDetails = itemView.findViewById(R.id.edit_details_button);
            buttonEditDetails.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.button_view_remove){
                wasteListActionListener.onRemoveButtonClicked(getAdapterPosition());
            } else if (view.getId() == R.id.edit_details_button) {
                wasteListActionListener.onEditButtonClicked(getAdapterPosition());
            }
        }
    }

    public interface WasteListActionListener {
        void onEditButtonClicked(int position);
        void onRemoveButtonClicked(int position);
    }
}
