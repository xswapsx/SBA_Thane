package com.riaylibrary.custom_component;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.core.view.ScaleGestureDetectorCompat;

import com.appynitty.riaylibrary.R;


public class MyZoomageView extends AppCompatImageView implements ScaleGestureDetector.OnScaleGestureListener {
    private final float MIN_SCALE;
    private final float MAX_SCALE;
    private final int RESET_DURATION;
    private final RectF bounds;
    private ScaleType startScaleType;
    private final Matrix matrix;
    private Matrix startMatrix;
    private final float[] matrixValues;
    private float[] startValues;
    private float minScale;
    private float maxScale;
    private float calculatedMinScale;
    private float calculatedMaxScale;
    private boolean translatable;
    private boolean zoomable;
    private boolean restrictBounds;
    private boolean animateOnReset;
    private boolean autoCenter;
    private int autoResetMode;
    private final PointF last;
    private float startScale;
    private float scaleBy;
    private int previousPointerCount;
    private final ScaleGestureDetector scaleDetector;

    public MyZoomageView(Context context) {
        this(context, (AttributeSet)null);
    }

    public MyZoomageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyZoomageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.MIN_SCALE = 0.6F;
        this.MAX_SCALE = 8.0F;
        this.RESET_DURATION = 200;
        this.bounds = new RectF();
        this.matrix = new Matrix();
        this.startMatrix = new Matrix();
        this.matrixValues = new float[9];
        this.startValues = null;
        this.minScale = 0.6F;
        this.maxScale = 8.0F;
        this.calculatedMinScale = 0.6F;
        this.calculatedMaxScale = 8.0F;
        this.last = new PointF(0.0F, 0.0F);
        this.startScale = 1.0F;
        this.scaleBy = 1.0F;
        this.previousPointerCount = 1;
        this.scaleDetector = new ScaleGestureDetector(context, this);
        ScaleGestureDetectorCompat.setQuickScaleEnabled(this.scaleDetector, false);
        this.startScaleType = this.getScaleType();
        TypedArray values = context.obtainStyledAttributes(attrs, R.styleable.MyZoomageView);
        this.zoomable = values.getBoolean(R.styleable.MyZoomageView_zoomage_zoomable, true);
        this.translatable = values.getBoolean(R.styleable.MyZoomageView_zoomage_translatable, true);
        this.animateOnReset = values.getBoolean(R.styleable.MyZoomageView_zoomage_animateOnReset, true);
        this.autoCenter = values.getBoolean(R.styleable.MyZoomageView_zoomage_autoCenter, true);
        this.restrictBounds = values.getBoolean(R.styleable.MyZoomageView_zoomage_restrictBounds, false);
        this.minScale = values.getFloat(R.styleable.MyZoomageView_zoomage_minScale, 0.6F);
        this.maxScale = values.getFloat(R.styleable.MyZoomageView_zoomage_maxScale, 8.0F);
        this.autoResetMode = values.getInt(R.styleable.MyZoomageView_zoomage_autoResetMode, 0);
        this.verifyScaleRange();
        values.recycle();
    }

    private void verifyScaleRange() {
        if (this.minScale >= this.maxScale) {
            throw new IllegalStateException("minScale must be less than maxScale");
        } else if (this.minScale < 0.0F) {
            throw new IllegalStateException("minScale must be greater than 0");
        } else if (this.maxScale < 0.0F) {
            throw new IllegalStateException("maxScale must be greater than 0");
        }
    }

    public void setScaleRange(float minScale, float maxScale) {
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.startValues = null;
        this.verifyScaleRange();
    }

    public boolean isTranslatable() {
        return this.translatable;
    }

    public void setTranslatable(boolean translatable) {
        this.translatable = translatable;
    }

    public boolean isZoomable() {
        return this.zoomable;
    }

    public void setZoomable(boolean zoomable) {
        this.zoomable = zoomable;
    }

    public boolean getRestrictBounds() {
        return this.restrictBounds;
    }

    public void setRestrictBounds(boolean restrictBounds) {
        this.restrictBounds = restrictBounds;
    }

    public boolean getAnimateOnReset() {
        return this.animateOnReset;
    }

    public void setAnimateOnReset(boolean animateOnReset) {
        this.animateOnReset = animateOnReset;
    }

    public int getAutoResetMode() {
        return this.autoResetMode;
    }

    public void setAutoResetMode(int autoReset) {
        this.autoResetMode = autoReset;
    }

    public boolean getAutoCenter() {
        return this.autoCenter;
    }

    public void setAutoCenter(boolean autoCenter) {
        this.autoCenter = autoCenter;
    }

    public void setScaleType(@Nullable ScaleType scaleType) {
        if (scaleType != null) {
            super.setScaleType(scaleType);
            this.startScaleType = scaleType;
            this.startValues = null;
        }

    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (!enabled) {
            this.setScaleType(this.startScaleType);
        }

    }

    public void setImageResource(int resId) {
        super.setImageResource(resId);
        this.setScaleType(this.startScaleType);
    }

    public void setImageDrawable(@Nullable Drawable drawable) {
        super.setImageDrawable(drawable);
        this.setScaleType(this.startScaleType);
    }

    public void setImageBitmap(Bitmap bm) {
        super.setImageBitmap(bm);
        this.setScaleType(this.startScaleType);
    }

    public void setImageURI(@Nullable Uri uri) {
        super.setImageURI(uri);
        this.setScaleType(this.startScaleType);
    }

    private void updateBounds(float[] values) {
        if (this.getDrawable() != null) {
            this.bounds.set(values[2], values[5], (float)this.getDrawable().getIntrinsicWidth() * values[0] + values[2], (float)this.getDrawable().getIntrinsicHeight() * values[4] + values[5]);
        }

    }

    private float getCurrentDisplayedWidth() {
        return this.getDrawable() != null ? (float)this.getDrawable().getIntrinsicWidth() * this.matrixValues[0] : 0.0F;
    }

    private float getCurrentDisplayedHeight() {
        return this.getDrawable() != null ? (float)this.getDrawable().getIntrinsicHeight() * this.matrixValues[4] : 0.0F;
    }

    private void setStartValues() {
        this.startValues = new float[9];
        this.startMatrix = new Matrix(this.getImageMatrix());
        this.startMatrix.getValues(this.startValues);
        this.calculatedMinScale = this.minScale * this.startValues[0];
        this.calculatedMaxScale = this.maxScale * this.startValues[0];
    }

    public boolean onTouchEvent(MotionEvent event) {
        if (!this.isClickable() && this.isEnabled() && (this.zoomable || this.translatable)) {
            if (this.getScaleType() != ScaleType.MATRIX) {
                super.setScaleType(ScaleType.MATRIX);
            }

            if (this.startValues == null) {
                this.setStartValues();
            }

            this.matrix.set(this.getImageMatrix());
            this.matrix.getValues(this.matrixValues);
            this.updateBounds(this.matrixValues);
            this.scaleDetector.onTouchEvent(event);
            if (event.getActionMasked() != 0 && event.getPointerCount() == this.previousPointerCount) {
                if (event.getActionMasked() == 2) {
                    float focusx = this.scaleDetector.getFocusX();
                    float focusy = this.scaleDetector.getFocusY();
                    if (this.translatable) {
                        float xdistance = this.getXDistance(focusx, this.last.x);
                        float ydistance = this.getYDistance(focusy, this.last.y);
                        this.matrix.postTranslate(xdistance, ydistance);
                    }

                    if (this.zoomable) {
                        this.matrix.postScale(this.scaleBy, this.scaleBy, focusx, focusy);
                    }

                    this.setImageMatrix(this.matrix);
                    this.last.set(focusx, focusy);
                }
            } else {
                this.last.set(this.scaleDetector.getFocusX(), this.scaleDetector.getFocusY());
            }

            if (event.getActionMasked() == 1) {
                this.scaleBy = 1.0F;
                this.resetImage();
            }

            this.previousPointerCount = event.getPointerCount();
            return true;
        } else {
            return super.onTouchEvent(event);
        }
    }

    private void resetImage() {
        switch(this.autoResetMode) {
            case 0:
                if (this.matrixValues[0] <= this.startValues[0]) {
                    this.reset();
                } else {
                    this.center();
                }
                break;
            case 1:
                if (this.matrixValues[0] >= this.startValues[0]) {
                    this.reset();
                } else {
                    this.center();
                }
                break;
            case 2:
                this.reset();
                break;
            case 3:
                this.center();
        }

    }

    private void center() {
        if (this.autoCenter) {
            this.animateTranslationX();
            this.animateTranslationY();
        }

    }

    public void reset() {
        this.reset(this.animateOnReset);
    }

    public void reset(boolean animate) {
        if (animate) {
            this.animateToStartMatrix();
        } else {
            this.setImageMatrix(this.startMatrix);
        }

    }

    private void animateToStartMatrix() {
        final Matrix beginMatrix = new Matrix(this.getImageMatrix());
        beginMatrix.getValues(this.matrixValues);
        final float xsdiff = this.startValues[0] - this.matrixValues[0];
        final float ysdiff = this.startValues[4] - this.matrixValues[4];
        final float xtdiff = this.startValues[2] - this.matrixValues[2];
        final float ytdiff = this.startValues[5] - this.matrixValues[5];
        ValueAnimator anim = ValueAnimator.ofFloat(0.0F, 1.0F);
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            final Matrix activeMatrix = new Matrix(MyZoomageView.this.getImageMatrix());
            final float[] values = new float[9];

            public void onAnimationUpdate(ValueAnimator animation) {
                float val = (Float)animation.getAnimatedValue();
                this.activeMatrix.set(beginMatrix);
                this.activeMatrix.getValues(this.values);
                this.values[2] += xtdiff * val;
                this.values[5] += ytdiff * val;
                this.values[0] += xsdiff * val;
                this.values[4] += ysdiff * val;
                this.activeMatrix.setValues(this.values);
                MyZoomageView.this.setImageMatrix(this.activeMatrix);
            }
        });
        anim.setDuration(200L);
        anim.start();
    }

    private void animateTranslationX() {
        if (this.getCurrentDisplayedWidth() > (float)this.getWidth()) {
            if (this.bounds.left > 0.0F) {
                this.animateMatrixIndex(2, 0.0F);
            } else if (this.bounds.right < (float)this.getWidth()) {
                this.animateMatrixIndex(2, this.bounds.left + (float)this.getWidth() - this.bounds.right);
            }
        } else if (this.bounds.left < 0.0F) {
            this.animateMatrixIndex(2, 0.0F);
        } else if (this.bounds.right > (float)this.getWidth()) {
            this.animateMatrixIndex(2, this.bounds.left + (float)this.getWidth() - this.bounds.right);
        }

    }

    private void animateTranslationY() {
        if (this.getCurrentDisplayedHeight() > (float)this.getHeight()) {
            if (this.bounds.top > 0.0F) {
                this.animateMatrixIndex(5, 0.0F);
            } else if (this.bounds.bottom < (float)this.getHeight()) {
                this.animateMatrixIndex(5, this.bounds.top + (float)this.getHeight() - this.bounds.bottom);
            }
        } else if (this.bounds.top < 0.0F) {
            this.animateMatrixIndex(5, 0.0F);
        } else if (this.bounds.bottom > (float)this.getHeight()) {
            this.animateMatrixIndex(5, this.bounds.top + (float)this.getHeight() - this.bounds.bottom);
        }

    }

    private void animateMatrixIndex(final int index, float to) {
        ValueAnimator animator = ValueAnimator.ofFloat(this.matrixValues[index], to);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            final float[] values = new float[9];
            final Matrix current = new Matrix();

            public void onAnimationUpdate(ValueAnimator animation) {
                this.current.set(MyZoomageView.this.getImageMatrix());
                this.current.getValues(this.values);
                this.values[index] = (Float)animation.getAnimatedValue();
                this.current.setValues(this.values);
                MyZoomageView.this.setImageMatrix(this.current);
            }
        });
        animator.setDuration(200L);
        animator.start();
    }

    private float getXDistance(float toX, float fromX) {
        float xdistance = toX - fromX;
        if (this.restrictBounds) {
            xdistance = this.getRestrictedXDistance(xdistance);
        }

        if (this.bounds.right + xdistance < 0.0F) {
            xdistance = -this.bounds.right;
        } else if (this.bounds.left + xdistance > (float)this.getWidth()) {
            xdistance = (float)this.getWidth() - this.bounds.left;
        }

        return xdistance;
    }

    private float getRestrictedXDistance(float xdistance) {
        float restrictedXDistance = xdistance;
        if (this.getCurrentDisplayedWidth() >= (float)this.getWidth()) {
            if (this.bounds.left <= 0.0F && this.bounds.left + xdistance > 0.0F && !this.scaleDetector.isInProgress()) {
                restrictedXDistance = -this.bounds.left;
            } else if (this.bounds.right >= (float)this.getWidth() && this.bounds.right + xdistance < (float)this.getWidth() && !this.scaleDetector.isInProgress()) {
                restrictedXDistance = (float)this.getWidth() - this.bounds.right;
            }
        } else if (!this.scaleDetector.isInProgress()) {
            if (this.bounds.left >= 0.0F && this.bounds.left + xdistance < 0.0F) {
                restrictedXDistance = -this.bounds.left;
            } else if (this.bounds.right <= (float)this.getWidth() && this.bounds.right + xdistance > (float)this.getWidth()) {
                restrictedXDistance = (float)this.getWidth() - this.bounds.right;
            }
        }

        return restrictedXDistance;
    }

    private float getYDistance(float toY, float fromY) {
        float ydistance = toY - fromY;
        if (this.restrictBounds) {
            ydistance = this.getRestrictedYDistance(ydistance);
        }

        if (this.bounds.bottom + ydistance < 0.0F) {
            ydistance = -this.bounds.bottom;
        } else if (this.bounds.top + ydistance > (float)this.getHeight()) {
            ydistance = (float)this.getHeight() - this.bounds.top;
        }

        return ydistance;
    }

    private float getRestrictedYDistance(float ydistance) {
        float restrictedYDistance = ydistance;
        if (this.getCurrentDisplayedHeight() >= (float)this.getHeight()) {
            if (this.bounds.top <= 0.0F && this.bounds.top + ydistance > 0.0F && !this.scaleDetector.isInProgress()) {
                restrictedYDistance = -this.bounds.top;
            } else if (this.bounds.bottom >= (float)this.getHeight() && this.bounds.bottom + ydistance < (float)this.getHeight() && !this.scaleDetector.isInProgress()) {
                restrictedYDistance = (float)this.getHeight() - this.bounds.bottom;
            }
        } else if (!this.scaleDetector.isInProgress()) {
            if (this.bounds.top >= 0.0F && this.bounds.top + ydistance < 0.0F) {
                restrictedYDistance = -this.bounds.top;
            } else if (this.bounds.bottom <= (float)this.getHeight() && this.bounds.bottom + ydistance > (float)this.getHeight()) {
                restrictedYDistance = (float)this.getHeight() - this.bounds.bottom;
            }
        }

        return restrictedYDistance;
    }

    public boolean onScale(ScaleGestureDetector detector) {
        this.scaleBy = this.startScale * detector.getScaleFactor() / this.matrixValues[0];
        float projectedScale = this.scaleBy * this.matrixValues[0];
        if (projectedScale < this.calculatedMinScale) {
            this.scaleBy = this.calculatedMinScale / this.matrixValues[0];
        } else if (projectedScale > this.calculatedMaxScale) {
            this.scaleBy = this.calculatedMaxScale / this.matrixValues[0];
        }

        return false;
    }

    public boolean onScaleBegin(ScaleGestureDetector detector) {
        this.startScale = this.matrixValues[0];
        return true;
    }

    public void onScaleEnd(ScaleGestureDetector detector) {
        this.scaleBy = 1.0F;
    }
}