package com.example.views;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Interpolator;
import android.widget.Button;

/**
 * Created by dingjun on 17-12-25.
 */
public class ZoomButton extends Button {
    private Context mContext;
    private AnimatorSet mScaleDownAnimation;
    private AnimatorSet mScaleUpAnimation;
    public ZoomButton(Context context) {
        super(context);
    }

    public ZoomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZoomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init(Context context) {
        mContext = context;
        mScaleDownAnimation = loadButtonDownAnim(this);
        mScaleUpAnimation = loadButtonUpAnim(this);
    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if(event.getAction()==MotionEvent.ACTION_DOWN){
            if(mScaleUpAnimation.isRunning()) {
                mScaleUpAnimation.cancel();
            }
            mScaleDownAnimation.start();
        }
        if(event.getAction()==MotionEvent.ACTION_UP || event.getAction()==MotionEvent.ACTION_CANCEL){
            if(mScaleDownAnimation.isRunning()) {
                mScaleDownAnimation.cancel();
            }
            mScaleUpAnimation.start();
        }
        return super.onTouchEvent(event);
    }

    public AnimatorSet loadButtonDownAnim(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX1 = ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.90f);
        scaleX1.setDuration(120);
        ObjectAnimator scaleY1 = ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.90f);
        scaleY1.setDuration(120);
        ObjectAnimator scaleX2 = ObjectAnimator.ofFloat(view, "scaleX", 0.90f, 0.95f);
        scaleX2.setDuration(100);
        ObjectAnimator scaleY2 = ObjectAnimator.ofFloat(view, "scaleY", 0.90f, 0.95f);
        scaleY2.setDuration(100);
        ObjectAnimator scaleX3 = ObjectAnimator.ofFloat(view, "scaleX", 0.95f, 0.90f);
        scaleX3.setDuration(120);
        ObjectAnimator scaleY3 = ObjectAnimator.ofFloat(view, "scaleY", 0.95f, 0.90f);
        scaleY3.setDuration(120);

        animatorSet.setInterpolator(new EaseCubicInterpolator(0.17f, 0f, 0.3f, 1f));
        animatorSet.play(scaleX1).with(scaleY1);
        animatorSet.play(scaleX2).with(scaleY2).after(scaleX1);
        animatorSet.play(scaleX3).with(scaleY3).after(scaleX2);
        return animatorSet;
    }

    public AnimatorSet loadButtonUpAnim(View view) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator scaleX4 = ObjectAnimator.ofFloat(view, "scaleX", 0.9f, 1.05f);
        scaleX4.setDuration(120);
        ObjectAnimator scaleY4 = ObjectAnimator.ofFloat(view, "scaleY", 0.9f, 1.05f);
        scaleY4.setDuration(120);
        ObjectAnimator scaleX5 = ObjectAnimator.ofFloat(view, "scaleX", 1.05f, 1f);
        scaleX5.setDuration(120);
        ObjectAnimator scaleY5 = ObjectAnimator.ofFloat(view, "scaleY", 1.05f, 1f);
        scaleY5.setDuration(120);

        animatorSet.setInterpolator(new EaseCubicInterpolator(0.17f, 0f, 0.3f, 1f));
        animatorSet.play(scaleX4).with(scaleY4);
        animatorSet.play(scaleX5).with(scaleY5).after(scaleX4);
        return animatorSet;
    }

    public class EaseCubicInterpolator implements Interpolator {


        private final static int ACCURACY = 4096;

        private int mLastI = 0;

        private final PointF mControlPoint1 = new PointF();

        private final PointF mControlPoint2 = new PointF();


        /**
         * 设置中间两个控制点.<br>
         * <p>
         * 在线工具: http://cubic-bezier.com/<br>
         *
         * @param x1
         * @param y1
         * @param x2
         * @param y2
         */

        public EaseCubicInterpolator(float x1, float y1, float x2, float y2) {

            mControlPoint1.x = x1;

            mControlPoint1.y = y1;

            mControlPoint2.x = x2;

            mControlPoint2.y = y2;

        }


        @Override

        public float getInterpolation(float input) {

            float t = input;

            // 近似求解t的值[0,1]

            for (int i = mLastI; i < ACCURACY; i++) {

                t = 1.0f * i / ACCURACY;

                double x = cubicCurves(t, 0, mControlPoint1.x, mControlPoint2.x, 1);

                if (x >= input) {

                    mLastI = i;

                    break;

                }

            }

            double value = cubicCurves(t, 0, mControlPoint1.y, mControlPoint2.y, 1);

            if (value > 0.999d) {

                value = 1;

                mLastI = 0;

            }

            return (float) value;

        }


        /**
         * 求三次贝塞尔曲线(四个控制点)一个点某个维度的值.<br>
         * <p>
         * 参考资料: <em> http://devmag.org.za/2011/04/05/bzier-curves-a-tutorial/ </em>
         *
         * @param t      取值[0, 1]
         * @param value0
         * @param value1
         * @param value2
         * @param value3
         * @return
         */

        public double cubicCurves(double t, double value0, double value1,

                                         double value2, double value3) {

            double value;

            double u = 1 - t;

            double tt = t * t;

            double uu = u * u;

            double uuu = uu * u;

            double ttt = tt * t;


            value = uuu * value0;

            value += 3 * uu * t * value1;

            value += 3 * u * tt * value2;

            value += ttt * value3;

            return value;

        }
    }
}
