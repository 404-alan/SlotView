package com.example.justfortest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created on 12/16/20.
 *
 * @author Alan
 */
public class PixelImageView extends AppCompatImageView {

    Path path;
    Path mSlotPath;
    Paint mPaint;
    RectF slotRect = new RectF();

    /**
     * 圆形图片信息
     */
    //float slotStartX = 93.38167f;
    //float slotStartY = 247.31993f;
    //public static final int width = (int) (683f);
    //public static final int height = (int) (689f);

    /**
     * 方形图片信息
     */
    float slotStartX = 137.1768f;
    float slotStartY = 136.7521f;
    public static final int width = (int) (633f);
    public static final int height = (int) (380f);
    private final Bitmap sourceBitmap;

    {
        sourceBitmap = getTargetBitmap();
        path = new Path();
        mSlotPath = new Path();
        slotRect = new RectF(slotStartX, slotStartY, slotStartX + width, slotStartY + height);
        mSlotPath.addRect(slotRect, Path.Direction.CW);
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        //mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(getApplyDimension(10));
        scanBitmap();
    }

    public PixelImageView(@NonNull Context context) {
        super(context);
    }

    public PixelImageView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PixelImageView(@NonNull Context context,
                          @Nullable AttributeSet attrs,
                          int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private int getApplyDimension(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                               dp,
                                               getResources().getDisplayMetrics());
    }

    private Bitmap getTargetBitmap() {
        try {
            InputStream inputStream = getContext().getAssets().open("transparent.png");
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void scanBitmap() {
        int centerX = (int) ((slotRect.left + slotRect.right) / 2);
        int centerY = (int) ((slotRect.top + slotRect.bottom) / 2);
        int startX = centerX;
        Point point = new Point();

        for (int i = (int) slotRect.left; i <= centerX; i += 2) {
            int pixel = sourceBitmap.getPixel(i, centerY);
            int alpha = Color.alpha(pixel);
            if (alpha == 0) {
                startX = i;
                point.set(i, centerY);
                break;
            }
        }
        // 设置Path的起点
        if (path.isEmpty()) {
            path.moveTo(startX, centerY);
        }
        List<Point> borderPoints = SlotSelectionHelper.getInstance().findBorder(sourceBitmap, point);

        for (int i = 0; i < borderPoints.size(); i++) {
            Point borderPoint = borderPoints.get(i);
            path.lineTo(borderPoint.x, borderPoint.y);
        }
    }

    @Override protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.RED);

        path.reset();
        path.addRect(slotRect, Path.Direction.CW);
        canvas.drawPath(path, mPaint);
        super.onDraw(canvas);
    }
}