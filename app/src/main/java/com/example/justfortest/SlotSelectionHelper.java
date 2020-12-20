package com.example.justfortest;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Created on 12/18/20.
 *
 * @author Alan
 */
public class SlotSelectionHelper {

    List<Point> borderPoints = new ArrayList<>();

    public static final int TRANSPARENCY_THRESHOLD = 0x66;

    public static SlotSelectionHelper getInstance() {
        return new SlotSelectionHelper();
    }

    public static boolean isValidRegion(Bitmap bitmap, Point point) {
        return isTransparent(bitmap, point) && isOpaque(bitmap, point);
    }

    /**
     * whether this pixel is transparent
     * @param bitmap
     * @param point
     * @return
     */
    private static boolean isTransparent(Bitmap bitmap, Point point) {
        int leftTopX = point.x - 1;
        int rightBottomX = point.x + 1;
        int leftTopY = point.y - 1;
        int rightBottomY = point.y + 1;
        for (int x = leftTopX; x <= rightBottomX; x++) {
            for (int y = leftTopY; y <= rightBottomY; y++) {
                int color = bitmap.getPixel(x, y);
                int alpha = Color.alpha(color);

                if (point.x == x && point.y == y) {
                    continue;
                }

                if (alpha <= TRANSPARENCY_THRESHOLD) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * whether this pixel is opaque
     * @param bitmap
     * @param point
     * @return
     */
    private static boolean isOpaque(Bitmap bitmap, Point point) {
        int leftTopX = point.x - 1;
        int rightBottomX = point.x + 1;
        int leftTopY = point.y - 1;
        int rightBottomY = point.y + 1;

        for (int x = leftTopX; x <= rightBottomX; x++) {
            for (int y = leftTopY; y <= rightBottomY; y++) {
                int color = bitmap.getPixel(x, y);
                int alpha = Color.alpha(color);

                if (point.x == x && point.y == y) {
                    continue;
                }

                if (alpha > TRANSPARENCY_THRESHOLD) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     *
     * @param bitmap
     * @param point
     * @return
     */
    public List<Point> findBorder(Bitmap bitmap, Point point) {
        if (borderPoints.isEmpty()) {
            // 设置起始点
            borderPoints.add(point);
        }

        int leftTopX = point.x - 1;
        int rightBottomX = point.x + 1;
        int leftTopY = point.y - 1;
        int rightBottomY = point.y + 1;

        Point nextPoint = findNextPoint(bitmap, point, leftTopX, rightBottomX, leftTopY, rightBottomY);

        if (nextPoint == null) {
            return borderPoints;
        } else {
            borderPoints.add(nextPoint);
            findBorder(bitmap, nextPoint);
        }

        return borderPoints;
    }

    /**
     *      *           *         *
     *      *     lastValidPoint  *
     *      *           *         *
     *
     * @param bitmap overlay bitmap(original size)
     * @param lastValidPoint
     * @param leftTopX
     * @param rightBottomX
     * @param leftTopY
     * @param rightBottomY
     * @return
     */
    private Point findNextPoint(Bitmap bitmap, Point lastValidPoint, int leftTopX, int rightBottomX, int leftTopY, int rightBottomY) {
        for (int x = leftTopX; x <= rightBottomX; x++) {
            for (int y = leftTopY; y <= rightBottomY; y++) {

                Point nextPoint = new Point();
                nextPoint.x = x;
                nextPoint.y = y;

                // scan the last valid point
                if (lastValidPoint.equals(nextPoint)) {
                    continue;
                }

                // discard opaque pixel
                if (Color.alpha(bitmap.getPixel(x,y)) > TRANSPARENCY_THRESHOLD) {
                    continue;
                }

                if (!isValidRegion(bitmap, nextPoint)) {
                    continue;
                }

                // scan the pixel which has in the border list
                if (borderPoints.contains(nextPoint)) {
                    continue;
                }

                return nextPoint;
            }
        }
        return null;
    }

}