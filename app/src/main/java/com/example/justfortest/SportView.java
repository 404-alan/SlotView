package com.example.justfortest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created on 12/13/20.
 *
 * @author Alan
 */
public class SportView extends View {

    public static final int RADIUS = Utils.dpToPx(150);

    Paint mPaint = new Paint();
    float centerX;
    float centerY;

    public SportView(Context context) {
        super(context);
    }

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SportView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public SportView(Context context,
                     @Nullable AttributeSet attrs,
                     int defStyleAttr,
                     int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        mPaint.setTextSize(Utils.spToPx(100));
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Utils.dpToPx(10));
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() /2;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(centerX,centerY,RADIUS,mPaint);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(Color.RED);
        canvas.drawArc(centerX - RADIUS,centerY - RADIUS,centerX + RADIUS,centerY + RADIUS,-90,220,false,mPaint);

        String text = "aabbj";
        //Rect rect  = new Rect();
        //mPaint.getTextBounds(text,0, text.length(),rect);
        // 静态居中
        //float offset = (rect.top + rect.bottom) / 2f;
        // 动态居中
        Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(fontMetrics);
        float offset = (fontMetrics.ascent + fontMetrics.descent) / 2;
        canvas.drawText(text,centerX,centerY - offset,mPaint);
    }
}
