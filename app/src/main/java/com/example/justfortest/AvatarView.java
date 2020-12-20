package com.example.justfortest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.Xfermode;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created on 12/12/20.
 *
 * @author Alan
 */
public class AvatarView extends View {
    public static final int WIDTH = Utils.dpToPx(150);
    public static final int PADDING = Utils.dpToPx(20);

    RectF bounds = new RectF();

    Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    Xfermode xfermode = new PorterDuffXfermode(PorterDuff.Mode.SRC_IN);
    Bitmap avatar;

    public AvatarView(Context context) {
        super(context);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AvatarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public AvatarView(Context context,
                      @Nullable AttributeSet attrs,
                      int defStyleAttr,
                      int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        avatar = getAvatar(WIDTH);
        bounds.set(PADDING, PADDING, PADDING + WIDTH, PADDING + WIDTH);
    }

    // 当Bitmap配合Canvas的原生图形，使用Xformode会产生效果不统一的问题，
    // 这个时候Bitmap不能作为DST来使用
    //@Override protected void onDraw(Canvas canvas) {
    //    super.onDraw(canvas);
    //
    //    int count = canvas.saveLayer(bounds, paint);
    //    // DST:先画的是DST
    //    canvas.drawOval(bounds, paint);
    //    paint.setXfermode(xfermode);
    //    // SRC:后画的是SRC
    //    canvas.drawBitmap(avatar,PADDING,PADDING,paint);
    //    paint.setXfermode(null);
    //    canvas.restoreToCount(count);
    //}

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int count = canvas.saveLayer(bounds,paint);
        // DST first
        canvas.drawOval(bounds,paint);
        paint.setXfermode(xfermode);
        // SRC next
        canvas.drawBitmap(avatar,PADDING,PADDING,paint);
        paint.setXfermode(null);
        canvas.restoreToCount(count);
    }

    Bitmap getAvatar(int width) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(getResources(), R.drawable.cat, options);
        options.inJustDecodeBounds = false;
        options.inDensity = options.outWidth;
        options.inTargetDensity = width;
        return BitmapFactory.decodeResource(getResources(), R.drawable.cat, options);
    }
}
