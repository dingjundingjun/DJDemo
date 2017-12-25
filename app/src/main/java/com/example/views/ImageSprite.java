package com.example.views;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by dingjun on 17-1-13.
 */
public class ImageSprite {
    private Canvas mCanvas;
    private Matrix mMatrix = new Matrix();
    //x,y 相对于屏幕左上角的坐标
    private int mX;
    private int mY;
    //w,h 宽和高
    private int mWidth;
    private int mHeight;
    //旋转中心
    private int mPivotX = 0;
    private int mPivotY = 0;
    //旋转角度
    private int mRotation;
    //图片在资源包内的相对路径
    private String mSrc;
    //图片序列后缀数字，一般用变量表示，可以根据变量显示不同的图片，如果src="pic.png" srcid="1" 则最后会显示图片 "pic_1.png"
    private int mSrcId;
    //透明度 0-255, 小于等于0不显示
    private int mAlpha;
    //抗锯齿，如果为true图片在变形旋转时不会有锯齿，但是速度会慢
    private boolean bAntiAlias;
    //left,center,right坐标点水平对齐方式，分别表示居左，居中（实际坐标点 = 指定坐标点- w/2），居右（实际坐标点 = 指定坐标点- w）。默认left
    private String mAlign;
    private Bitmap mBitmap;
    public ImageSprite(Canvas canvas) {
        this.mCanvas = canvas;
    }

    public ImageSprite(Canvas canvas, int x, int y, int w, int h, int pivotX, int pivotY, int rotation, String src, int srcId, int alpha, String align) {
        this.mCanvas = canvas;
        this.mX = x;
        this.mY = y;
        this.mWidth = w;
        this.mHeight = h;
        this.mPivotX = pivotX;
        this.mPivotY = pivotY;
        this.mRotation = rotation;
        this.mSrc = src;
        this.mSrcId = srcId;
        this.mAlpha = alpha;
        this.mAlign = align;
        init();
    }

    public int getX() {
        return mX;
    }

    public void setX(int mX) {
        this.mX = mX;
    }

    public int getY() {
        return mY;
    }

    public void setY(int mY) {
        this.mY = mY;
    }

    public int getWidth() {
        return mWidth;
    }

    public void setWidth(int mWidth) {
        this.mWidth = mWidth;
    }

    public int getHeight() {
        return mHeight;
    }

    public void setHeight(int mHeight) {
        this.mHeight = mHeight;
    }

    public int getPivotX() {
        return mPivotX;
    }

    public void setPivotX(int mPivotX) {
        this.mPivotX = mPivotX;
    }

    public int getPivotY() {
        return mPivotY;
    }

    public void setPivotY(int mPivotY) {
        this.mPivotY = mPivotY;
    }

    public int getRotation() {
        return mRotation;
    }

    public void setRotation(int mRotation) {
        this.mRotation = mRotation;
    }

    public String getSrc() {
        return mSrc;
    }

    public void setSrc(String mSrc) {
        this.mSrc = mSrc;
    }

    public int getSrcId() {
        return mSrcId;
    }

    public void setSrcId(int mSrcId) {
        this.mSrcId = mSrcId;
    }

    public int getAlpha() {
        return mAlpha;
    }

    public void setAlpha(int mAlpha) {
        this.mAlpha = mAlpha;
    }

    public boolean isAntiAlias() {
        return bAntiAlias;
    }

    public void setAntiAlias(boolean bAntiAlias) {
        this.bAntiAlias = bAntiAlias;
    }

    public String getAlign() {
        return mAlign;
    }

    public void setAlign(String mAlign) {
        this.mAlign = mAlign;
    }

    private void draw() {
        if(mCanvas != null && mBitmap != null) {
            mCanvas.drawBitmap(mBitmap,mMatrix,null);
        }

    }

    private void init() {
        if(mSrc != null && !mSrc.equals("")) {
            mBitmap = BitmapFactory.decodeFile(mSrc);
            mCanvas = new Canvas(mBitmap);
        }
    }

    private void handleMatrix() {
        if(mRotation >= 0) {
            mMatrix.setRotate(mRotation,mPivotX,mPivotY);
        }
    }

    private class AnimationThread extends Thread {
        private boolean bStart = false;
        public void animateStart() {
            bStart = true;
        }

        public void animateStop() {
            bStart = false;
            this.run();
        }

        @Override
        public void run() {
            super.run();
            while(bStart) {
                handleMatrix();
                draw();
                try {
                    Thread.sleep(33);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
