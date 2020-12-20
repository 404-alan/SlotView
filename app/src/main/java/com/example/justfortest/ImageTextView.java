package com.example.justfortest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;

/**
 * Created on 12/13/20.
 *
 * @author Alan
 */
public class ImageTextView extends View {
    TextPaint textPaint;
    Paint mPaint;
    public static final int IMAGE_WIDTH = Utils.dpToPx(150);
    public static final int PADDING = Utils.dpToPx(100);
    Paint.FontMetrics fontMetrics = new Paint.FontMetrics();
    Bitmap avatar;
    int viewWidth;
    int viewHeight;
    int bitmapWidth;
    int bitmapHeight;
    float[] measueredWidth = new float[1];
    String text = "context The Context the view is running in, through which"
        + "the current theme, resources, etc."
        + "attrs The attributes of the XML tag that is inflating the"
        + "defStyleAttr An attribute in the current theme that conta"
        + "ce to a style resource that supplies default values for"
        + "w. Can be 0 to not look for defaults."
        + "defStyleRes A resource identifier of a style resource tha"
        + "s default values for the view, used only if"
        + "eAttr is 0 or can not be found in the theme. Can be 0"
        + "look for defaults."
        + "context The Context the view is running in, through which"
        + "the current theme, resources, etc."
        + "attrs The attributes of the XML tag that is inflating the"
        + "defStyleAttr An attribute in the current theme that conta"
        + "ce to a style resource that supplies default values for"
        + "w. Can be 0 to not look for defaults."
        + "defStyleRes A resource identifier of a style resource tha"
        + "s default values for the view, used only if"
        + "eAttr is 0 or can not be found in the theme. Can be 0"
        + "look for defaults.";

    public ImageTextView(Context context) {
        super(context);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ImageTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public ImageTextView(Context context,
                         @Nullable AttributeSet attrs,
                         int defStyleAttr,
                         int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    {
        textPaint = new TextPaint();
        textPaint.setTextSize(Utils.spToPx(15));
        mPaint = new Paint();
        mPaint.setTextSize(Utils.dpToPx(16));
        mPaint.getFontMetrics(fontMetrics);
        mPaint.setAntiAlias(true);
        avatar = Utils.getAvatar(getResources(), IMAGE_WIDTH);
    }

    //@Override protected void onDraw(Canvas canvas) {
    //    super.onDraw(canvas);
    //
    //    canvas.drawBitmap(bitmap, getWidth() - IMAGE_WIDTH, PADDING, mPaint);
    //    // 绘制自动换行的文字
    //    //StaticLayout staticLayout = new StaticLayout(text,
    //    //                                             textPaint,
    //    //                                             getWidth(),
    //    //                                             Layout.Alignment.ALIGN_NORMAL,
    //    //                                             1,
    //    //                                             0,
    //    //                                             false);
    //    //staticLayout.draw(canvas);
    //
    //    // 处理文字被截断的问题 getFontSpacing基本等于TopLine和BottomLine距离
    //    //int textCharCount = text.length();
    //    //int lineCharCount = 0;
    //    //int yOffset = (int) mPaint.getFontSpacing();
    //    //for (int start = 0; start < textCharCount; start += lineCharCount,yOffset += mPaint.getFontSpacing()) {
    //    //    float textTop = yOffset + fontMetrics.top;
    //    //    float textBottom = yOffset + fontMetrics.bottom;
    //    //    int usableWidth;
    //    //    if (textBottom < PADDING || textTop > PADDING + bitmap.getHeight()) {
    //    //        usableWidth = getWidth();
    //    //    } else {
    //    //        usableWidth = getWidth() - IMAGE_WIDTH;
    //    //    }
    //    //
    //    //    lineCharCount = mPaint.breakText(text, start, textCharCount, true, usableWidth, measueredWidth);
    //    //    canvas.drawText(text, start, start + lineCharCount, 0, yOffset, mPaint);
    //    //}
    //
    //
    //}

    @Override protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmapWidth = avatar.getWidth();
        bitmapHeight = avatar.getHeight();
        viewHeight = getHeight();
        viewWidth = getWidth();
    }

    @Override protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制单行文本，通过FontMetrics确定文本的高度，top是负值，bottom是正值
        //float yOffset = fontMetrics.top - fontMetrics.bottom;
        //canvas.drawText(text, 0, -yOffset, mPaint);

        // 绘制自动换行的文本，需要传入最大宽度供staticlayout测量
        //StaticLayout staticLayout = new StaticLayout(text, textPaint, getWidth(), Layout.Alignment.ALIGN_NORMAL,1,0,true);
        //staticLayout.draw(canvas);

        // 自动计算文本宽进行排版
        Log.e("x",String.valueOf(viewWidth - bitmapWidth));
        Log.e("y",String.valueOf(PADDING));
        canvas.drawBitmap(avatar, viewWidth - bitmapWidth, PADDING, mPaint);
        StaticLayout staticLayout = new StaticLayout(text, textPaint, viewWidth - bitmapWidth, Layout.Alignment.ALIGN_NORMAL, 1, 0, true);
        staticLayout.draw(canvas);
    }
}
