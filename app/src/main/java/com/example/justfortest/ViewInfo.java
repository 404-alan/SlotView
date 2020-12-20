package com.example.justfortest;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.RectF;

/**
 * Created on 12/19/20.
 *
 * @author Alan
 */
public interface ViewInfo {
    RectF getSlotRect();
    Point getCenterPoint();
    String getOverlayName();
}
