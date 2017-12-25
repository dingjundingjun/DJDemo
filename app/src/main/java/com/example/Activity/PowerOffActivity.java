package com.example.Activity;

import com.bbk.example.demo.R;
import com.example.utils.Debug;
import com.example.views.M1000PowerOffDialog.NotificationCollopseAnimateUpdateListener;
import com.example.views.M1000PowerOffDialog.OnCallBack;
import com.example.views.M1000PowerOffDialog.UIHandle;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class PowerOffActivity extends Activity
{
	private View mTopView;
	private View mBottomView;
	private View mRootView;
	private Context mContext;
	private TextView mPowerOffTextView;
	private TextView mRebootTextView;
	private boolean isMoving = false;
	private float mLastY = 0;
	private int mState = 0;
	/**加速度*/
    private int VELOCITY_DY = 1500;
    private final int SCROOL_POWER_OFF_LINE_Y = -280;
    private final int SCROOL_REBOOT_LINE_Y = -1130;
    private final int SCROOL_TO_POWER_OFF_Y = 0;
    private final int SCROOL_TO_REBOOT_Y = -1920;
    private NotificationCollopseAnimateUpdateListener mNotificationCollopseAnimateUpdateListener = new NotificationCollopseAnimateUpdateListener(); 
    private final int SCROOL_STATE_NORMAL = 0;
    private final int SCROOL_STATE_POWER = 1;
    private final int SCROOL_STATE_REBOOT = 2;
    private final int HANDLE_POWER_OFF = 1;
    private final int HANDLE_REBOOT = 2;
    private final int ROOT_VIEW_INIT_Y = -630;
    private OnCallBack mOnCallBack;
    private UIHandle mUIHandle = new UIHandle();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
		
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.power_off_dialog_layout);
		Window win = getWindow();
		win.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = win.getAttributes();
		        lp.width = WindowManager.LayoutParams.FILL_PARENT;
		        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		        win.setAttributes(lp);
		init();
		mRootView.setY(ROOT_VIEW_INIT_Y);
	}
	
	private void init() 
	{
		mTopView = findViewById(R.id.top_layout);
		mBottomView = findViewById(R.id.bottom_layout);
		mRootView = findViewById(R.id.root_layout);
		mPowerOffTextView = (TextView)findViewById(R.id.power_off_text);
		mRebootTextView = (TextView)findViewById(R.id.reboot_text);
		updateText();
	}

	private void getScreenShot()
	{
		
	}
	
	private void setBackground()
	{
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch(event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
		{
			mLastY = event.getY();
			isMoving = true;
			break;
		}
		case MotionEvent.ACTION_MOVE:
		{
			float dy = (event.getY() - mLastY) + ROOT_VIEW_INIT_Y;
			if(dy < SCROOL_TO_REBOOT_Y || dy > SCROOL_TO_POWER_OFF_Y)
			{
				break;
			}
			mRootView.setY(dy);
			if(mRootView.getY() > SCROOL_POWER_OFF_LINE_Y)
			{
				if(mState != SCROOL_STATE_POWER)
				{
					mState = SCROOL_STATE_POWER;
					updateText();
				}
			}
			else if(mRootView.getY() <= SCROOL_POWER_OFF_LINE_Y && mRootView.getY() >= SCROOL_REBOOT_LINE_Y)
			{
				if(mState != SCROOL_STATE_NORMAL)
				{
					mState = SCROOL_STATE_NORMAL;
					updateText();
				}
			}
			else
			{
				if(mState != SCROOL_STATE_REBOOT)
				{
					mState = SCROOL_STATE_REBOOT;
					updateText();
				}
			}
			break;
		}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP:
		{
			Debug.d("ACTION_UP OR ACTION_CANCEL");
			Debug.d("mRootView.getY() = " + mRootView.getY());
			if(mRootView.getY() > SCROOL_POWER_OFF_LINE_Y)
			{
				Debug.d("power off");
				powerOffAnimate();
			}
			else if(mRootView.getY() <= SCROOL_POWER_OFF_LINE_Y && mRootView.getY() >= SCROOL_REBOOT_LINE_Y)
			{
				Debug.d("do nothing");
				resetAnimate();
			}
			else
			{
				Debug.d("reboot system");
				rebootAnimate();
			}
			isMoving = false;
			break;
		}
		}
		return super.onTouchEvent(event);
	}

	private void updateText()
	{
		if(mState == SCROOL_STATE_NORMAL)
		{
			mPowerOffTextView.setText("下滑关机");
			mRebootTextView.setText("上滑重启");
			mPowerOffTextView.setVisibility(View.VISIBLE);
			mRebootTextView.setVisibility(View.VISIBLE);
		}
		else if(mState == SCROOL_STATE_POWER)
		{
			mPowerOffTextView.setText("释放关机");
			mRebootTextView.setText("上滑重启");
			mPowerOffTextView.setVisibility(View.VISIBLE);
			mRebootTextView.setVisibility(View.INVISIBLE);
		}
		else
		{
			mPowerOffTextView.setText("下滑关机");
			mRebootTextView.setText("释放重启");
			mPowerOffTextView.setVisibility(View.INVISIBLE);
			mRebootTextView.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 *  滑动到关机的动画
	 *  
	 */
	private void powerOffAnimate()
	{
		mRootView.animate().
		translationY(SCROOL_TO_POWER_OFF_Y)
		.withEndAction(new Runnable() {
			@Override
			public void run() {
				mUIHandle.sendEmptyMessage(HANDLE_POWER_OFF);
			}
		}).setUpdateListener(mNotificationCollopseAnimateUpdateListener);
	}
	
	/**
	 * 滑动到重启的动画
	 * */
	private void rebootAnimate()
	{
		mRootView.animate().
		translationY(SCROOL_TO_REBOOT_Y)
		.withEndAction(new Runnable() {
			@Override
			public void run() {
				mUIHandle.sendEmptyMessage(HANDLE_REBOOT);
			}
		}).setUpdateListener(mNotificationCollopseAnimateUpdateListener);
	}
	
	/** 
	 * 还原位置的动画
	 */
	private void resetAnimate()
	{
		mRootView.animate().
		translationY(ROOT_VIEW_INIT_Y)
		.withEndAction(new Runnable() {
			@Override
			public void run() {
			}
		}).setUpdateListener(mNotificationCollopseAnimateUpdateListener);
	}
	
public class NotificationCollopseAnimateUpdateListener  implements ValueAnimator.AnimatorUpdateListener
{
	@Override
	public void onAnimationUpdate(ValueAnimator arg0) {
		
	}
}

	public interface OnCallBack
	{
		public void onPowerOff();
		public void onReboot();
	}

	public void setCallBack(OnCallBack cb)
	{
		mOnCallBack = cb;
	}
	
	public class UIHandle extends Handler
	{
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what)
			{
			case HANDLE_POWER_OFF:
			{
				if(mOnCallBack != null)
				{
					mOnCallBack.onPowerOff();
				}
				break;
			}
			case HANDLE_REBOOT:
			{
				if(mOnCallBack != null)
				{
					mOnCallBack.onReboot();
				}
				break;
			}
			}
		}
	}

	@Override
	protected void onStop() {
		super.onStop();
		finish();
	}
	
	
	
}
