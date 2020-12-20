package com.example.justfortest.slotviews;

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
import androidx.appcompat.widget.AppCompatImageView;
import com.example.justfortest.SlotSelectionHelper;
import com.example.justfortest.ViewInfo;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created on 12/19/20.
 *
 * @author Alan
 */
public abstract class BorderImageView extends AppCompatImageView implements ViewInfo {

    protected Path path;
    private final Paint mPaint;
    private RectF mSlotRect;

    public BorderImageView(Context context) {
        super(context);
    }

    public BorderImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BorderImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    {
        path = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeWidth(getApplyDimension(6));
    }

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mSlotRect = getSlotRect();
        Bitmap overlayBitmap = getOverlayBitmap();
        float scaleRatio = (float)w / (float)overlayBitmap.getWidth();
        scanBorderPath(overlayBitmap, scaleRatio);
    }

    private int getApplyDimension(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                                               dp,
                                               getResources().getDisplayMetrics());
    }

    private void scanBorderPath(Bitmap overlayBitmap, float scaleRatio) {
        int centerX = getCenterPoint().x;
        int centerY = getCenterPoint().y;
        Point point = new Point();

        for (int i = (int) mSlotRect.left; i <= centerX; i += 2) {
            if (SlotSelectionHelper.getInstance().isValidRegion(overlayBitmap, new Point(i, centerY))) {
                point.set(i, centerY);
                break;
            }
        }
        // 设置Path的起点
        if (path.isEmpty()) {
            path.moveTo(point.x, centerY);
        }
        List<Point> borderPoints = SlotSelectionHelper.getInstance().findBorder(overlayBitmap, point);

        for (int i = 0; i < borderPoints.size(); i++) {
            Point borderPoint = borderPoints.get(i);
            // 我们求出来的坐标是原始坐标，但是Bitmap加载到图片上的时候，因为Fix_center的原因会有缩放，用原始坐标乘以缩放比例就就可以了
            path.lineTo(borderPoint.x * scaleRatio, borderPoint.y * scaleRatio);
        }
    }

    private Bitmap getOverlayBitmap() {
        try {
            InputStream inputStream = getContext().getAssets().open(getOverlayName());
            return BitmapFactory.decodeStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override protected void onDraw(Canvas canvas) {
        canvas.drawPath(path, mPaint);
        super.onDraw(canvas);
    }
}
