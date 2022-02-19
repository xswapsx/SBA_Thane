package com.riaylibrary.custom_component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.appynitty.riaylibrary.R;

public class MyNoDataView extends LinearLayout {
    public MyNoDataView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.no_data_view, this, true);
    }

    public void setNoDataImage(int resourceDrawable) {
        this.findViewById(R.id.no_data_image_view).setBackgroundResource(resourceDrawable);
    }

    public void setMessage(String message) {
        ((TextView)this.findViewById(R.id.no_data_msg_tv)).setText(message);
    }
}
