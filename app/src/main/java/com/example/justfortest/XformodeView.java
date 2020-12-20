package com.example.justfortest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created on 12/13/20.
 *
 * @author Alan
 */
public class XformodeView extends View {
    public static final int LENGTH = Utils.dpToPx(200);
    public static final int RADIUS = LENGTH / 4;
    public static final int SIDE_LENGTH = LENGTH / 2;
    private static int centerX;
    private static int centerY;
    private Paint mPaint = new Paint();
    Bitmap dstBitmap;
    Bitmap srcBitmap;
    Xfermode xfermode;

    public XformodeView(Context context) {
        super(context);
    }

    public XformodeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public XformodeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public XformodeView(Context context,
                        @Nullable AttributeSet attrs,
                        int defStyleAttr,
                        int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        mPaint.setAntiAlias(true);
        dstBitmap = makeDST();
        srcBitmap = makeSRC();
        xfermode = new PorterDuffXfermode(PorterDuff.Mode.MULTIPLY);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        centerX = getWidth() / 2;
        centerY = getHeight() / 2;
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        RectF rectF = new RectF();
        rectF.set(centerX - SIDE_LENGTH,
                  centerY - SIDE_LENGTH,
                  centerX + SIDE_LENGTH,
                  centerY + SIDE_LENGTH);
        int count = canvas.saveLayer(rectF, mPaint);
        canvas.drawBitmap(dstBitmap, centerX - SIDE_LENGTH, centerY - SIDE_LENGTH, mPaint);
        mPaint.setXfermode(xfermode);
        canvas.drawBitmap(srcBitmap, centerX - SIDE_LENGTH, centerY - SIDE_LENGTH, mPaint);
        mPaint.setXfermode(null);
        canvas.restoreToCount(count);
    }

    static Bitmap makeDST() {
        Bitmap bitmap = Bitmap.createBitmap(SIDE_LENGTH + RADIUS, SIDE_LENGTH + RADIUS, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setAntiAlias(true);

        RectF rectF = new RectF();
        rectF.set(0, 0, SIDE_LENGTH, SIDE_LENGTH);
        c.drawRect(rectF, paint);
        return bitmap;
    }

    static Bitmap makeSRC() {
        Bitmap bitmap = Bitmap.createBitmap(SIDE_LENGTH + RADIUS, SIDE_LENGTH + RADIUS, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bitmap);

        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setAntiAlias(true);

        RectF rectF = new RectF();
        rectF.set(RADIUS, RADIUS, SIDE_LENGTH + RADIUS, SIDE_LENGTH +RADIUS);
        c.drawOval(rectF, paint);

        return bitmap;
    }
}
