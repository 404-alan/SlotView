package com.example.justfortest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created on 12/12/20.
 *
 * @author Alan
 */
public class PieChart extends View {
    Paint mPaint = new Paint();
    int[] ANGLES = {
        60, 100, 120, 80
    };
    int[] COLORS = {
        Color.parseColor("#121212"),
        Color.parseColor("#ababab"),
        Color.parseColor("#12ab34"),
        Color.parseColor("#abcd12")
    };
    int RADIUS = Utils.dpToPx(150);

    int PULL_INDEX = 3;

    int centerX;
    int centerY;
    int left;
    int top;
    int right;
    int bottom;

    public PieChart(Context context) {
        super(context);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PieChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public PieChart(Context context,
                    @Nullable AttributeSet attrs,
                    int defStyleAttr,
                    int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
        left = centerX - RADIUS;
        top = centerY - RADIUS;
        right = centerX + RADIUS;
        bottom = centerY + RADIUS;
    }

    int currentAngle = 0;

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < COLORS.length; i++) {
            mPaint.setColor(COLORS[i]);
            if (i == PULL_INDEX) {
                canvas.save();
                int translateAngle = currentAngle + ANGLES[i] /2;
                canvas.translate((float) Math.cos(Math.toRadians(translateAngle)) * 30,(float) Math.sin(Math.toRadians(translateAngle)) * 30);
            }
            canvas.drawArc(left, top, right, bottom, currentAngle, ANGLES[i], true, mPaint);
            currentAngle += ANGLES[i];
            if (i == PULL_INDEX) {
                canvas.restore();
            }
        }
    }
}
