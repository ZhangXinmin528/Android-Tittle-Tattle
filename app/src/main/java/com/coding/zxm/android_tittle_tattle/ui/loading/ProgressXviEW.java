package com.coding.zxm.android_tittle_tattle.ui.loading;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;

public class ProgressXviEW extends View {

    private float minWidth;
    private float minHeight;

    private Paint mPaint;

    private RectF rectF;
    private Path bgPath = new Path();
    private float cornerRadius = 30;

    private int stepW;
    private float animW;

    private int count;

    public ProgressXviEW(Context context) {
        this(context, null);
    }

    public ProgressXviEW(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressXviEW(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        minWidth = dip2px(200);
        minHeight = dip2px(20);

        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectF = new RectF(0, 0, w, h);

        bgPath.reset();
        bgPath.addRoundRect(rectF, cornerRadius, cornerRadius, Path.Direction.CW);
        bgPath.close();

        count = 36;
        stepW = w / (count + 1);
    }

    @Override
    public void draw(Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(bgPath);
        super.draw(canvas);
        canvas.restoreToCount(save);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        int save = canvas.save();
        canvas.clipPath(bgPath);
        super.dispatchDraw(canvas);
        canvas.restoreToCount(save);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Path path = new Path();

        for (int i = -2; i <= count + 1; i++) {
            path.reset();

            path.moveTo(stepW * (i + 1) + animW, 0);
            path.lineTo(stepW * (i + 2) + animW, 0);
            path.lineTo(stepW * (i + 1) + animW, getHeight());
            path.lineTo(stepW * (i) + animW, getHeight());
            path.close();

            if (i % 2 == 0) {
                mPaint.setColor(Color.parseColor("#A8B2C8"));
            } else {
                mPaint.setColor(Color.parseColor("#D8D8D8"));
            }

            canvas.drawPath(path, mPaint);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width;
        int height;
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = (int) (getPaddingLeft() + minWidth + getPaddingRight());
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = (int) (getPaddingTop() + minHeight + getPaddingBottom());
        }

        setMeasuredDimension(width, height);
    }

    private void anim(){
        clearAnimation();
        ValueAnimator animator = ValueAnimator.ofFloat(0, stepW);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                animW = (float) animation.getAnimatedValue();
                Log.d("ppp","animW: " + animW);
                invalidate();
            }
        });
        animator.setRepeatCount(Animation.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(500);
        animator.start();
    }

    public void startAnim(){
        this.post(new Runnable() {
            @Override
            public void run() {
                anim();
            }
        });
    }

    public void stopAnim(){
        clearAnimation();
        animW = 0;
        invalidate();
    }

    public float dip2px(float dpValue) {
        return dpValue * getResources().getDisplayMetrics().density + 0.5f;
    }
}
