package com.example.progressview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * Created by zhongyu on 3/3/2017.
 */

public class ProgressLineView extends View{
    private static final String TAG = "ProgressLineView";

    private int bgColor;
    private int fgColor;
    private float height;
    private float width;
    private float fontSzie;
    private int fontColor;

    /**
     *测试动画
     */
    ValueAnimator mTestAnim;
    private float mFaction;

    public ProgressLineView(Context context) {
        this(context, null);
    }

    public ProgressLineView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressLineView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension((int)width, (int) height);
    }

    private void init(Context mContext, AttributeSet attrs, int defStyleAttr) {
        initDefaultValue(mContext);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.ProgressView, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            if(index == R.styleable.ProgressView_bgColor) {
                bgColor = typedArray.getColor(index, bgColor);
            }else if(index == R.styleable.ProgressView_fgColor) {
                fgColor = typedArray.getColor(index, fgColor);
            }else if(index == R.styleable.ProgressView_fontColor) {
                fontColor = typedArray.getColor(index, fontColor);
            }else if(index == R.styleable.ProgressView_fontSize) {
                fontSzie = typedArray.getDimension(index, fontSzie);
            }else if(index == R.styleable.ProgressView_height) {
                height = typedArray.getDimension(index, height);
            }else if(index == width) {
                width = typedArray.getDimension(index, width);
            }
        }
        typedArray.recycle();
        animTest();
    }

    private void animTest() {
        mTestAnim = ValueAnimator.ofFloat(0, 1);
        mTestAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mFaction = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        mTestAnim.setDuration(15000);
        mTestAnim.start();
    }

    private void initDefaultValue(Context mContext) {
        bgColor = Color.GRAY;
        fgColor = Color.RED;
        fontColor = Color.RED;

        height = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30,
                mContext.getResources().getDisplayMetrics());
        width = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 301,
                mContext.getResources().getDisplayMetrics());
        fontSzie = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 18,
                mContext.getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //抗锯齿
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(bgColor);
        paint.setStrokeWidth(5);
        canvas.drawLine(0, height / 2, width, height / 2, paint);
        paint.setColor(Color.RED);
        canvas.drawCircle(mFaction * (width - 80) + 40, height / 2, 40, paint);
        paint.setColor(fgColor);
        canvas.drawLine(0, height / 2, mFaction * width, height / 2, paint);
        paint.setColor(Color.WHITE);
        paint.setTextSize(50);
        String str = String.valueOf((int)(mFaction * 100));
        canvas.drawText(str,40 + mFaction * (width - 80) - paint.measureText(str) / 2, height / 2 + 13, paint);
    }

}












