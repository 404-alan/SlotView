package com.example.justfortest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created on 2020/10/31.
 *
 * @author Alan
 */
public class RectView extends View {

    private Paint mBorderPaint = new Paint();
    private RectF mBorderRect = new RectF();
    int width = 0;
    int height = 0;
    final float delta = Math.round(20);

    public RectView(Context context) {
        super(context);
        init();
    }

    public RectView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public RectView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(){
        mBorderPaint.setColor(getResources().getColor(android.R.color.holo_red_dark));
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(100);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
        mBorderRect.left = 0;
        mBorderRect.top = 0;
        mBorderRect.right = width ;
        mBorderRect.bottom = height;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRoundRect(mBorderRect,0,0, mBorderPaint);
    }
}
