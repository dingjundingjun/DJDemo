package com.example.views;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.TextView;

public class TouchTextView extends TextView{
	private boolean bMove = false;
	private TouchClickListener mTouchClickListener;
	public TouchTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction()) {
		case MotionEvent.ACTION_DOWN:{
			bMove = false;
			break;
		}
		case MotionEvent.ACTION_MOVE:{
			bMove = true;
			return false;
		}
		case MotionEvent.ACTION_UP:{
			if(bMove == false) {
				if(mTouchClickListener != null) {
				    mTouchClickListener.onClick();
				}
			}
			break;
		}
		}
		return super.onTouchEvent(event);
	}

	public interface TouchClickListener{
		public void onClick();
	}
	
	public void setTouchListener(TouchClickListener tc) {
		mTouchClickListener = tc;
	}
}
