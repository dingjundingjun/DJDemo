package com.example.utils;
import android.util.Log;
import android.view.MotionEvent;

public class GestureUtil {
	private final int POINT_COUNT = 3;
	/**这个记录按下的位置*/
	private float mTouchDownX[] = new float[POINT_COUNT];
	private float mTouchDownY[] = new float[POINT_COUNT];
	private float mLastX[] = new float[POINT_COUNT];
	private float mLastY[] = new float[POINT_COUNT];
	private final float THREE_POINT_PULL_DOWN_X_LIMIT = 100;
	private final float THREE_POINT_PULL_DOWN_Y_MIN = 800;
	private final int THREE_POINT_PULL_DOWN_MODE = 0; 
	private final int SUPPORT_GESTURE_NUMBER = 1;
	private final int THREE_POINT_PULL_DOWN_COUNT = 3;
	private final int GESTURE_ON = 1;
	private final int GESTURE_OFF = 0;
	private GestureListener mGestureListener;
	/**支持的手势种类，我们这里通过手势移动，一一去排除不是哪一种*/
	private int mSupportGesture[] = new int[SUPPORT_GESTURE_NUMBER];
	
	private void reset() {
		for(int i =0 ; i < POINT_COUNT; i++) {
			mLastX[i] = -1;
			mLastY[i] = -1;
		}
		for(int i = 0; i < SUPPORT_GESTURE_NUMBER; i++) {
			mSupportGesture[i] = GESTURE_ON;
		}
	}
	
	public boolean jugeGesture(MotionEvent event) {
			int count = event.getPointerCount();
			if(POINT_COUNT < count) {
				return false;
			}
			switch(event.getAction()) {
			case MotionEvent.ACTION_DOWN: {
				reset();
				return false;
			}
			case MotionEvent.ACTION_MOVE: {
				Log.d("dingjun","ACTION_MOVE count = " + count);
				for(int i =0 ; i < count; i++) {
					float currentX = event.getX(i);
					float currentY = event.getY(i);
					if(mLastX[event.getPointerId(i)] == -1) {
						mLastX[i] = currentX;
						mLastY[i] = currentY;
					}
					Log.d("dingjun","ACTION_MOVE event.getPointerId(i) = " + event.getPointerId(i) + " i = " + i);
					if((currentY - mTouchDownY[event.getPointerId(i)] < THREE_POINT_PULL_DOWN_Y_MIN ) && (Math.abs(currentX - mLastX[event.getPointerId(i)]) > THREE_POINT_PULL_DOWN_X_LIMIT || count != THREE_POINT_PULL_DOWN_COUNT || currentY < mLastY[event.getPointerId(i)])) {
						Log.d("dingjun","Math.abs(currentX - mLastX[i] " + Math.abs(currentX - mLastX[i]) + " currentY = " +currentY+"mLastY = " + mLastY[i]);
						mSupportGesture[THREE_POINT_PULL_DOWN_MODE] = GESTURE_OFF;
						return false;
					} else if((currentY - mTouchDownY[event.getPointerId(i)] > THREE_POINT_PULL_DOWN_Y_MIN) && (mSupportGesture[THREE_POINT_PULL_DOWN_MODE] != GESTURE_OFF)) {
						mSupportGesture[THREE_POINT_PULL_DOWN_MODE] = GESTURE_ON;
					}
					mLastX[i] = currentX;
					mLastY[i] = currentY;
				}
				return true;
			}
			case MotionEvent.ACTION_UP: {
				Log.d("dingjun","ACTION_UP count = " + count);
				if(mGestureListener != null) {
					if(mSupportGesture[THREE_POINT_PULL_DOWN_MODE] == GESTURE_ON) {
						mGestureListener.onThreePullDown();
						return true;
					}
				}
			}
			}
			return true;
		}
		
		public interface GestureListener {
			
			public void onThreePullDown();
		}
		
		public void setOnGestureListener(GestureListener gl) {
			mGestureListener = gl;
		}
}
