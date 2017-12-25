package com.example.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.Log;
import android.view.View;

import org.w3c.dom.Text;

/**
 * Created by dingjun on 17-10-9.
 */
public class ViewText extends View {

    private Paint mPaint = new Paint();
    private float mTextSize;
    public ViewText(Context context) {
        super(context);
        mPaint.setColor(Color.BLACK);
        mTextSize = mPaint.getTextSize();
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
//        canvas.drawText("我们都是中国人",0,mTextSize,mPaint);
    }
}
