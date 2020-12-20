package com.example.justfortest.slotviews;

import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;

/**
 * Created on 12/19/20.
 *
 * @author Alan
 */
public class CircleSlotView extends BorderImageView {
    float slotStartX = 93.38167f;
    float slotStartY = 247.31993f;
    float slotEndX = slotStartX + 683f;
    float slotEndY = slotStartY + 689f;

    public CircleSlotView(Context context) {
        super(context);
    }

    public CircleSlotView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CircleSlotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override public RectF getSlotRect() {
        return new RectF(slotStartX, slotStartY, slotEndX, slotEndY);
    }

    @Override public Point getCenterPoint() {
        int centerX = (int) (slotStartX + slotEndX) / 2;
        int centerY = (int) (slotStartY + slotEndY) / 2;
        return new Point(centerX, centerY);
    }

    @Override public String getOverlayName() {
        return "circle.png";
    }
}
