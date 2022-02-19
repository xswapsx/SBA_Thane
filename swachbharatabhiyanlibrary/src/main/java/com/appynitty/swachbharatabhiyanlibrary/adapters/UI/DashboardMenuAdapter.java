package com.appynitty.swachbharatabhiyanlibrary.adapters.UI;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.appynitty.swachbharatabhiyanlibrary.R;
import com.appynitty.swachbharatabhiyanlibrary.adapters.connection.VerifyDataAdapterClass;
import com.appynitty.swachbharatabhiyanlibrary.pojos.MenuListPojo;
import com.appynitty.swachbharatabhiyanlibrary.utils.AUtils;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.List;

/**
 * Created by Ayan Dey on 22/1/20.
 */
public class DashboardMenuAdapter extends RecyclerView.Adapter<DashboardMenuAdapter.WasteMenuViewHolder> {

    private final Context context;
    private List<MenuListPojo> menuList;
    private final VerifyDataAdapterClass verifyDataAdapterClass;

    public DashboardMenuAdapter(Context context) {
        this.context = context;
        verifyDataAdapterClass = new VerifyDataAdapterClass(context);
    }

    public void setMenuList(List<MenuListPojo> menuList) {
        this.menuList = menuList;
    }

    @NonNull
    @Override
    public WasteMenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.layout_dashboard_menu_card, parent, false);
        return new WasteMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WasteMenuViewHolder holder, int position) {
        final MenuListPojo menuPojo = menuList.get(position);
        holder.menuNameTextView.setText(menuPojo.getMenuName());
        holder.menuImageView.setImageResource(menuPojo.getImage());
        holder.menuCardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (Prefs.getString(AUtils.PREFS.USER_TYPE_ID, AUtils.USER_TYPE.USER_TYPE_EMP_SCANNIFY)) {
                    case AUtils.USER_TYPE.USER_TYPE_EMP_SCANNIFY:
                        break;
                    case AUtils.USER_TYPE.USER_TYPE_WASTE_MANAGER:
                        verifyDataAdapterClass.verifyWasteManagementSync();
                        break;
                    case AUtils.USER_TYPE.USER_TYPE_GHANTA_GADI:
                    default:
                        if(!AUtils.isSyncOfflineDataRequestEnable){
                            verifyDataAdapterClass.verifyOfflineSync();
                        }
                        break;
                }
                if(menuPojo.getCheckAttendance()) {
                    if(AUtils.isIsOnduty())
                        context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
                    else
                        AUtils.warning(context, context.getResources().getString(R.string.be_no_duty));
                }else
                    context.startActivity(new Intent(context, menuPojo.getNextIntentClass()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (menuList != null) ? menuList.size() : 0;
    }

    class WasteMenuViewHolder extends RecyclerView.ViewHolder{

        TextView menuNameTextView;
        ImageView menuImageView;
        LinearLayout menuCardLayout;

        WasteMenuViewHolder(@NonNull View itemView) {
            super(itemView);
            menuNameTextView = itemView.findViewById(R.id.menu_title);
            menuImageView = itemView.findViewById(R.id.menu_icon);
            menuCardLayout = itemView.findViewById(R.id.menu_card_layout);
        }
    }
}
