package com.example.justfortest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created on 12/13/20.
 *
 * @author Alan
 */
public class CameraView extends View {
    public static final int IMAGE_WIDTH = Utils.dpToPx(10);
    public static final int PADDING = Utils.dpToPx(100);
    Bitmap bitmap;
    Camera camera;
    Paint mPaint;

    {
        bitmap = Utils.getAvatar(getResources(), Utils.dpToPx(150));
        camera = new Camera();
        mPaint = new Paint();
    }

    public CameraView(Context context) {
        super(context);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CameraView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CameraView(Context context,
                      @Nullable AttributeSet attrs,
                      int defStyleAttr,
                      int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        camera.rotateX(30);
        //camera.setLocation(0,0,-4 * getResources().getDisplayMetrics().density);
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int centerX = bitmap.getWidth() / 2;
        int centerY = bitmap.getHeight() / 2;

        canvas.save();
        canvas.translate(PADDING + centerX, PADDING + centerY);
        canvas.clipRect(-centerX, -centerY, centerX, 0);
        canvas.translate(-(PADDING + centerX), -(PADDING + centerY));
        canvas.rotate(30);
        canvas.drawBitmap(bitmap, PADDING, PADDING, mPaint);
        canvas.restore();

        canvas.save();
        canvas.translate(PADDING + centerX, PADDING + centerY);
        canvas.rotate(-30);
        camera.applyToCanvas(canvas);
        canvas.clipRect(-centerX, 0, centerX, centerY);
        canvas.rotate(30);
        canvas.translate(-(PADDING + centerX), -(PADDING + centerY));
        canvas.drawBitmap(bitmap, PADDING, PADDING, mPaint);
        canvas.restore();
        //canvas.translate(PADDING + centerX, PADDING + centerY);
        //camera.applyToCanvas(canvas);
        //canvas.translate(-(PADDING + centerX), -(PADDING + centerY));
        //canvas.drawBitmap(bitmap,PADDING, PADDING,mPaint);
    }
}
