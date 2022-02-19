package com.riaylibrary.custom_component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

import com.appynitty.riaylibrary.R;

public class GradientTextView extends AppCompatTextView
{
    Context mContext;
    int startColor, endColor;
    public GradientTextView(Context context )
    {
        super( context, null, -1 );
        mContext = context;
    }
    public GradientTextView(Context context,
                            AttributeSet attrs )
    {
        super( context, attrs, -1 );
        mContext = context;
    }
    public GradientTextView(Context context,
                            AttributeSet attrs, int defStyle )
    {
        super( context, attrs, defStyle );
        mContext = context;
    }

    @Override
    protected void onLayout( boolean changed,
                             int left, int top, int right, int bottom )
    {
        super.onLayout( changed, left, top, right, bottom );
        if(changed)
        {
            getPaint().setShader( new LinearGradient(
                    0, 0, 0, getHeight(),
                    mContext.getResources().getColor(R.color.gradient_start_color),
                    mContext.getResources().getColor(R.color.gradient_end_color),
                    Shader.TileMode.CLAMP ) );
        }
    }
}