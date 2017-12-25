package com.example.views;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.utils.Debug;

/**
 * Created by dingjun on 17-1-14.
 */
public class TestKeyguardView extends RelativeLayout {
    private final String mPicPath = "/mnt/sdcard/advance/";
    private Context mContext;
    private final String CENTER = "center";
    private int oXbh = 0;
    private int oXbm = 0;
    private int oXl = 0;
    private int oXq = 0;
    private int oXv = 1012;
    public TestKeyguardView(Context context) {
        super(context);
        mContext = context;
    }

    public TestKeyguardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestKeyguardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        ImageView imageWallPaper = setImagePosition("default_lock_wallpaper.jpg",0,0);

        ImageView imageBg2 = setImagePosition("bg2.png",0,288);

        ImageView imagePower = setImagePosition("power.png",101,401);

        LinearInterpolator lin = new LinearInterpolator();
        ImageView imageCircle = setImagePosition("circle.png",604,1200,CENTER,CENTER);
        final Animation rotateAnimation = new
                RotateAnimation(0f,360f,Animation.ABSOLUTE,57,Animation.ABSOLUTE,57);
        rotateAnimation.setInterpolator(lin);
        rotateAnimation.setDuration(5000);
        rotateAnimation.setRepeatCount(Animation.INFINITE);
        imageCircle.setAnimation(rotateAnimation);

        ImageView imageCircle2 = setImagePosition("circle.png",635,416);
        final Animation rotateAnimation2 = new
                RotateAnimation(0f,-360f,Animation.ABSOLUTE,57,Animation.ABSOLUTE,57);
        rotateAnimation2.setInterpolator(lin);
        rotateAnimation2.setDuration(5000);
        rotateAnimation2.setRepeatCount(Animation.INFINITE);
        imageCircle2.setAnimation(rotateAnimation2);

        ImageView imageCircle3 = setImagePosition("circle.png",28,416,CENTER,null);
        final Animation rotateAnimation3 = new
                RotateAnimation(0f,-360f,Animation.ABSOLUTE,57,Animation.ABSOLUTE,57);
        rotateAnimation3.setInterpolator(lin);
        rotateAnimation3.setDuration(5000);
        rotateAnimation3.setRepeatCount(Animation.INFINITE);
        imageCircle3.setAnimation(rotateAnimation3);

        ImageView imageCircle4 = setImagePosition("circle.png",59,1143);
        final Animation rotateAnimation4 = new
                RotateAnimation(0f,360f,Animation.ABSOLUTE,57,Animation.ABSOLUTE,57);
        rotateAnimation4.setInterpolator(lin);
        rotateAnimation4.setDuration(5000);
        rotateAnimation4.setRepeatCount(Animation.INFINITE);
        imageCircle4.setAnimation(rotateAnimation);

        ImageView leftt = setImagePosition("leftt.png",oXbh,390);
        ImageView rightt = setImagePosition("rightt.png",391+oXbm,390);
        ImageView leftb = setImagePosition("leftb.png",oXl,718);
        ImageView rightb = setImagePosition("rightb.png",418+oXq,718);
        ImageView oXs = setImagePosition("bottom.png",135,oXv);

        ImageView lvyan = setImagePosition("lvyan.png",0,324);
        final ObjectAnimator animator1 = ObjectAnimator.ofFloat(lvyan, "alpha", 0f,1f);
        animator1.setDuration(500);
        final ObjectAnimator animator2 = ObjectAnimator.ofFloat(lvyan, "alpha", 1f,1f);
        animator2.setDuration(500);
        final ObjectAnimator animator3 = ObjectAnimator.ofFloat(lvyan, "alpha", 1f,0f);
        animator3.setDuration(1500);
        final AnimatorSet set = new AnimatorSet();
        set.playSequentially(animator1,animator2,animator3);
        set.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                set.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        set.start();

        ImageView bg = setImagePosition("bg.png",0,0);

        ImageView unlocker = setImagePosition("unlocker.png",360,960,CENTER,CENTER);
        final Animation rotateAnimation5 = new RotateAnimation(0f,360f,Animation.ABSOLUTE,125,Animation.ABSOLUTE,125);
        rotateAnimation5.setInterpolator(lin);
        rotateAnimation5.setDuration(5000);
        rotateAnimation5.setRepeatCount(Animation.INFINITE);
        unlocker.setAnimation(rotateAnimation5);


    }

    private ImageView getImage(String name) {
        ImageView image = new ImageView(mContext);
        Bitmap bmpWallPaper = BitmapFactory.decodeFile(mPicPath + name);
        image.setImageBitmap(bmpWallPaper);
        return image;
    }

    private RelativeLayout.LayoutParams getParams(int x, int y) {
        RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.leftMargin = x;
        params.topMargin = y;
        return params;
    }

    private ImageView setImagePosition(String name,int x,int y,String alignX,String alignY) {
        ImageView image = new ImageView(mContext);
        Bitmap bmp = BitmapFactory.decodeFile(mPicPath + name);
        image.setImageBitmap(bmp);
        int marginLeft = x;
        int marginTop = y;
        if(alignX != null) {
            if (alignX.equals("center")) {
                marginLeft = x - bmp.getWidth() / 2;
            } else if (alignX.equals("right")) {
                marginLeft = x - bmp.getWidth();
            }
        }
        if(alignY != null) {
            if (alignY.equals("center")) {
                marginTop = y - bmp.getHeight() / 2;
            } else if (alignY.equals("bottom")) {
                marginTop = y - bmp.getHeight();
            }
        }
        RelativeLayout.LayoutParams params = getParams(marginLeft, marginTop);
        this.addView(image, params);
        return image;
    }

    private ImageView setImagePosition(String name,int x,int y) {
        return setImagePosition(name,x,y,null,null);
    }
}
