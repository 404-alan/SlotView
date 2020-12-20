package com.example.justfortest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created on 12/11/20.
 *
 * @author Alan
 */
public class TestView extends View {

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    public static final int SINGLE_DASH_WIDTH = Utils.dpToPx(2);
    public static final int SINGLE_DASH_HEIGHT = Utils.dpToPx(10);
    public static final int LENGTH = Utils.dpToPx(100);
    public static final float RADIUS = Utils.dpToPx(160);
    Path singleDash = new Path();
    PathMeasure pathMeasure;
    Path arcPath;
    PathEffect dashEffect;
    int centerY;
    int centerX;
    int left;
    int top;
    int right;
    int bottom;
    public static final int ANGLE = 60;

    public TestView(Context context) {
        super(context);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TestView(Context context,
                    @Nullable AttributeSet attrs,
                    int defStyleAttr,
                    int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        arcPath = new Path();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(Utils.dpToPx(3));
        // dash宽为2，高为10
        singleDash.addRect(0, 0, SINGLE_DASH_WIDTH, SINGLE_DASH_HEIGHT, Path.Direction.CW);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerY = getHeight() / 2;
        centerX = getWidth() / 2;
        left = (int) (centerX - RADIUS);
        top = (int) (centerY - RADIUS);
        right = (int) (centerX + RADIUS);
        bottom = (int) (centerY + RADIUS);
        arcPath.addArc(left, top, right, bottom, 120, 300);
        pathMeasure = new PathMeasure(arcPath, false);
        float dashSpacing = (pathMeasure.getLength() - SINGLE_DASH_WIDTH) / 20;
        // SingleDash是需要绘制的形状，paint的形状也会根据dash定义的坐标系来画
        dashEffect =
            new PathDashPathEffect(singleDash, dashSpacing, 0, PathDashPathEffect.Style.MORPH);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 画弧
        canvas.drawPath(arcPath, paint);

        // 画刻度
        paint.setPathEffect(dashEffect);
        canvas.drawPath(arcPath, paint);
        paint.setPathEffect(null);

        // 画指针
        float deltaX = (float) (Math.cos(Math.toRadians(getAngleFromMark(6))) * LENGTH);
        float deltaY = (float) (Math.sin(Math.toRadians(getAngleFromMark(6))) * LENGTH);
        canvas.drawLine(centerX, centerY, centerX + deltaX, centerY + deltaY, paint);
    }

    private int getAngleFromMark(int index) {
        return 90 + ANGLE / 2 + (360 - ANGLE) / 20 * index;
    }
}
