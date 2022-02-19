package com.riaylibrary.custom_component;

import android.view.View;

import androidx.viewpager.widget.ViewPager;

public class ViewPagerTransformer implements ViewPager.PageTransformer {
    private static final float MIN_SCALE_DEPTH = 0.75F;
    private static final float MIN_SCALE_ZOOM = 0.85F;
    private static final float MIN_ALPHA_ZOOM = 0.5F;
    private static final float SCALE_FACTOR_SLIDE = 0.85F;
    private static final float MIN_ALPHA_SLIDE = 0.35F;
    private final ViewPagerTransformer.TransformType mTransformType;

    public ViewPagerTransformer(ViewPagerTransformer.TransformType transformType) {
        this.mTransformType = transformType;
    }

    public void transformPage(View page, float position) {
        float alpha;
        float scale;
        float translationX;
        float hMargin;
        switch(this.mTransformType) {
            case FLOW:
                page.setRotationY(position * -30.0F);
                return;
            case SLIDE_OVER:
                if (position < 0.0F && position > -1.0F) {
                    scale = Math.abs(Math.abs(position) - 1.0F) * 0.14999998F + 0.85F;
                    alpha = Math.max(0.35F, 1.0F - Math.abs(position));
                    int pageWidth = page.getWidth();
                    hMargin = position * (float)(-pageWidth);
                    if (hMargin > (float)(-pageWidth)) {
                        translationX = hMargin;
                    } else {
                        translationX = 0.0F;
                    }
                } else {
                    alpha = 1.0F;
                    scale = 1.0F;
                    translationX = 0.0F;
                }
                break;
            case DEPTH:
                if (position > 0.0F && position < 1.0F) {
                    alpha = 1.0F - position;
                    scale = 0.75F + 0.25F * (1.0F - Math.abs(position));
                    translationX = (float)page.getWidth() * -position;
                } else {
                    alpha = 1.0F;
                    scale = 1.0F;
                    translationX = 0.0F;
                }
                break;
            case ZOOM:
                if (position >= -1.0F && position <= 1.0F) {
                    scale = Math.max(0.85F, 1.0F - Math.abs(position));
                    alpha = 0.5F + (scale - 0.85F) / 0.14999998F * 0.5F;
                    float vMargin = (float)page.getHeight() * (1.0F - scale) / 2.0F;
                    hMargin = (float)page.getWidth() * (1.0F - scale) / 2.0F;
                    if (position < 0.0F) {
                        translationX = hMargin - vMargin / 2.0F;
                    } else {
                        translationX = -hMargin + vMargin / 2.0F;
                    }
                } else {
                    alpha = 1.0F;
                    scale = 1.0F;
                    translationX = 0.0F;
                }
                break;
            default:
                return;
        }

        page.setAlpha(alpha);
        page.setTranslationX(translationX);
        page.setScaleX(scale);
        page.setScaleY(scale);
    }

    public enum TransformType {
        FLOW,
        DEPTH,
        ZOOM,
        SLIDE_OVER;

        TransformType() {
        }
    }
}