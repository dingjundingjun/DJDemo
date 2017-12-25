package com.example.views;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by dingjun on 17-1-12.
 */
public class KeyguardSurfaceView extends SurfaceView implements SurfaceHolder.Callback{
    private Context mContext;
    private SurfaceHolder mHolder;
    private DrawThread mDrawThread;
    private Canvas mCanvas;
    public KeyguardSurfaceView(Context context) {
        super(context);
        mContext = context;
        mHolder = this.getHolder();
        mHolder.addCallback(this);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        if(null == mDrawThread) {
            mDrawThread = new DrawThread();
            mDrawThread.start();
            mCanvas = mHolder.lockCanvas();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if(null != mDrawThread) {
            mDrawThread.stopThread();
        }
    }

    private class DrawThread extends Thread {
        public boolean isRunning = false;

        public DrawThread() {
            isRunning = true;
        }

        public void stopThread() {
            isRunning = false;
//            boolean workIsNotFinish = true;
//            while (workIsNotFinish) {
//                try {
//                    this.join();// 保证run方法执行完毕
//                } catch (InterruptedException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//                workIsNotFinish = false;
//            }
        }

        public void run() {
            while (isRunning) {
                Canvas canvas = null;
                try {
                    synchronized (mHolder) {
                        canvas = mHolder.lockCanvas();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    if (null != mHolder) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}
