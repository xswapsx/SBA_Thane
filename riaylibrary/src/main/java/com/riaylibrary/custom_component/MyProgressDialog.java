package com.riaylibrary.custom_component;

import android.app.Dialog;
import android.content.Context;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.appynitty.riaylibrary.R;

public class MyProgressDialog extends Dialog {
    private final ImageView dialogImageView;
    private Animation animation = null;

    public MyProgressDialog(Context context, int resourceIdOfImage, boolean cancelableStatus, int resourceIdOfAnimation) {
        super(context, R.style.MyProgressDialog);
        this.setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.animation = AnimationUtils.loadAnimation(context, resourceIdOfAnimation);
        this.animation.reset();
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = 1;
        this.getWindow().setAttributes(layoutParams);
        this.setTitle((CharSequence)null);
        this.setCancelable(cancelableStatus);
        this.setOnCancelListener((OnCancelListener)null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        this.dialogImageView = new ImageView(context);
        this.dialogImageView.setImageResource(resourceIdOfImage);
        layout.addView(this.dialogImageView, params);
        this.addContentView(layout, params);
    }

    public MyProgressDialog(Context context, int resourceIdOfImage, boolean cancelableStatus) {
        super(context, R.style.MyProgressDialog);
        this.setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        this.animation.reset();
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = 1;
        this.getWindow().setAttributes(layoutParams);
        this.setTitle((CharSequence)null);
        this.setCancelable(cancelableStatus);
        this.setOnCancelListener((OnCancelListener)null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        this.dialogImageView = new ImageView(context);
        this.dialogImageView.setImageResource(resourceIdOfImage);
        layout.addView(this.dialogImageView, params);
        this.addContentView(layout, params);
    }

    public MyProgressDialog(Context context, boolean cancelableStatus) {
        super(context, R.style.MyProgressDialog);
        this.setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        this.animation = AnimationUtils.loadAnimation(context, R.anim.rotate);
        this.animation.reset();
        WindowManager.LayoutParams layoutParams = this.getWindow().getAttributes();
        layoutParams.gravity = 1;
        this.getWindow().setAttributes(layoutParams);
        this.setTitle((CharSequence)null);
        this.setCancelable(cancelableStatus);
        this.setOnCancelListener((OnCancelListener)null);
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(-2, -2);
        this.dialogImageView = new ImageView(context);
        this.dialogImageView.setImageResource(R.drawable.progress_bar);
        layout.addView(this.dialogImageView, params);
        this.addContentView(layout, params);
    }

    public void show() {
        super.show();
        RotateAnimation anim = new RotateAnimation(0.0F, 360.0F, 1, 0.5F, 1, 0.5F);
        anim.setInterpolator(new LinearInterpolator());
        anim.setRepeatCount(-1);
        anim.setDuration(3000L);
        this.dialogImageView.setAnimation(this.animation);
        this.dialogImageView.startAnimation(this.animation);
    }
}
