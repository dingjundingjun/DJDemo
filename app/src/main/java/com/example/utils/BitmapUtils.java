package com.example.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.PaintDrawable;
import android.util.DisplayMetrics;

import java.io.File;
import java.util.ArrayList;

/**
 *  * author: liujian
 *   * created on: 17-1-3 上午9:54
 *    * description:
 *     */
public class BitmapUtils {

    private static Paint sMaskPaint;
    private static Paint sNormalPaint = new Paint();
    private static ArrayList<Bitmap> sIconMasks = new ArrayList<Bitmap>();
    private static int sIconWidth = -1;
    private static int sIconHeight = -1;
    private static final Canvas sCanvas = new Canvas();
    private static final Canvas sMaskCanvas = new Canvas();
    private static PorterDuffXfermode sMaskSrcIn = new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP);
    private static final Rect sOldBounds = new Rect();

    /* 李通 提供7种地板 */
    private static int sIconMaskCount = 9;
    //private static Bitmap sIconMask;

    static {
        sCanvas.setDrawFilter(new PaintFlagsDrawFilter(Paint.DITHER_FLAG, Paint.FILTER_BITMAP_FLAG));
    }

    private static void initStatics() {
        //final Resources resources = context.getResources();
        //final DisplayMetrics metrics = resources.getDisplayMetrics();
        //final float density = metrics.density;

        /* 李通 fix start 添加绘制定制图标的Mask底图和画笔 */
        sIconWidth = sIconHeight = 104;//(int) resources.getDimension(R.dimen.app_icon_size);

        sMaskPaint = new Paint();
        sMaskPaint.setXfermode(sMaskSrcIn);
        sMaskPaint.setAntiAlias(true);
        sMaskPaint.setFilterBitmap(true);
        sNormalPaint.setAntiAlias(true);
        sNormalPaint.setFilterBitmap(true);
        /*if(sIconMask == null) {
            sIconMask = BitmapFactory.decodeResource(resources, R.drawable.icon_mask);
        }*/
    }

    /**@hide*/
    public static void clear() {
        sIconWidth = -1;
    }

    private static void drawMask(Canvas canvas, Bitmap mask) {
        if (mask != null)
            canvas.drawBitmap(mask, (sIconWidth - mask.getWidth()) / 2, (sIconHeight - mask.getHeight()) / 2,
                    sNormalPaint);
    }

    /**@hide*/
    public static Bitmap createIconBitmap(Drawable icon,Bitmap bmpMask,Bitmap fg,Bitmap bg) {
        Matrix matrix = new Matrix();
        synchronized (sCanvas) { // we share the statics :-(
            if (sIconWidth == -1) {
                initStatics();
            }

            int width = sIconWidth;
            int height = sIconHeight;

            if (icon instanceof PaintDrawable) {
                PaintDrawable painter = (PaintDrawable) icon;
                painter.setIntrinsicWidth(width);
                painter.setIntrinsicHeight(height);
            } else if (icon instanceof BitmapDrawable) {
                // Ensure the bitmap has a density.
                BitmapDrawable bitmapDrawable = (BitmapDrawable) icon;
                Bitmap bitmap = bitmapDrawable.getBitmap();

                if (bitmap == null) {
                    return null;
                }

                if (bitmap.getDensity() == Bitmap.DENSITY_NONE) {
                    //bitmapDrawable.setTargetDensity(context.getResources().getDisplayMetrics());
                }
            }


            // final Bitmap bitmap = Bitmap.createBitmap(textureWidth,
            // textureHeight, Bitmap.Config.ARGB_8888);
            final Bitmap merge = Bitmap.createBitmap(sIconWidth, sIconHeight, Bitmap.Config.ARGB_8888);
            final Bitmap maskBmp = Bitmap.createBitmap(sIconWidth, sIconHeight, Bitmap.Config.ARGB_8888);
            final Canvas maskCanvas = sMaskCanvas;
            final Canvas canvas = sCanvas;
            //canvas.setBitmap(merge);

            //int maskIndex = className == null ? 0 : className.hashCode() % sIconMaskCount;
            //int thumb = Math.min(Math.abs(maskIndex), Math.max(0, sIconMasks.size() - 1));
            Bitmap mask = bmpMask;
            maskCanvas.setBitmap(maskBmp); 
            Paint drawPain = sMaskPaint;

            /* 李通 */
            if (icon instanceof BitmapDrawable) {
                sOldBounds.set(icon.getBounds());

                Bitmap iconBitmap = ((BitmapDrawable) icon).getBitmap();
                float scale;

                int maskInnerWidth = mask != null ? mask.getWidth() : iconBitmap.getWidth();
                int maskInnerHeight = mask != null ? mask.getHeight() : iconBitmap.getHeight();

                scale = Math.max(sIconWidth * 1.0f / iconBitmap.getWidth(),
                        sIconHeight * 1.0f / iconBitmap.getHeight());
                int left = (sIconWidth - iconBitmap.getWidth()) / 2;
                int top = (sIconHeight - iconBitmap.getHeight()) / 2;
                if (scale != 1.0f) {
                    scale = Math.max(maskInnerWidth * 1.0f / iconBitmap.getWidth(),
                            maskInnerHeight * 1.0f / iconBitmap.getHeight());
                    matrix.setTranslate(left, top);
                    matrix.postScale(Math.min(4.0f, scale), Math.min(4.0f, scale), sIconWidth / 2, sIconHeight / 2);
//                    if(bg != null) {
//                        canvas.drawBitmap(bg,0,0,null);
//                    }
//                    //canvas.drawBitmap(iconBitmap, matrix, drawPain);
//                    canvas.drawBitmap(iconBitmap,left,top,null);
//                    drawMask(canvas, mask);
//                    if(fg != null) {
//                        canvas.drawBitmap(fg,0,0,null);
//                    }
                    maskCanvas.drawBitmap(mask,0,0,null);
                    maskCanvas.drawBitmap(iconBitmap, matrix, drawPain);
                    canvas.setBitmap(merge);
                    if(bg != null) {
                        canvas.drawBitmap(bg,0,0,null);
                    }
                    canvas.drawBitmap(maskBmp,0,0,null);
                    if(fg != null) {
                        canvas.drawBitmap(fg,0,0,null);
                    }
                } else {
                    maskCanvas.drawBitmap(mask,0,0,null);
                    maskCanvas.drawBitmap(iconBitmap, 0,0, drawPain);
                    canvas.setBitmap(merge);
                    if(bg != null) {
                        canvas.drawBitmap(bg,0,0,null);
                    }
                    canvas.drawBitmap(maskBmp,0,0,null);
                    if(fg != null) {
                        canvas.drawBitmap(fg,0,0,null);
                    }
                }
                maskCanvas.setBitmap(null); 
                canvas.setBitmap(null);
            }
            return merge;
        }
    }
}

