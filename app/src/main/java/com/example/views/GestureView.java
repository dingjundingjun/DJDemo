package com.example.views;
import com.example.utils.GestureUtil;
import com.example.utils.GestureUtil.GestureListener;

import android.content.Context;
import android.util.AttributeSet;
import android.util.MonthDisplayHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;


public class GestureView extends View{
	private GestureUtil mGestureUtil;
	private Context mContext;
	public GestureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		mGestureUtil = new GestureUtil();
		mGestureUtil.setOnGestureListener(new GestureListener() {
			@Override
			public void onThreePullDown() {
				Toast.makeText(mContext, "this is three point pull down", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		int count = event.getPointerCount();
		if(count >= 1) {
			mGestureUtil.jugeGesture(event);
			return true;
		}
		return super.onTouchEvent(event);
	}
}
