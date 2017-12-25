package com.example.views;

import com.bbk.example.demo.R;
import com.example.utils.Debug;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class M1000PowerOffDialog extends Dialog
{

	private View mTopView;
	private View mBottomView;
	private View mRootView;
	private RelativeLayout mFrameBackView;
	private ImageView mStudyIcon;
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
    private final int HANDLE_ICON_DISMISS = 3;
    private final int ROOT_VIEW_INIT_Y = -630;
    private final int ROOT_ICON_INIT_Y = 570;
    private final int ICON_STOP_Y = 800;
    private OnCallBack mOnCallBack;
    private boolean isCanScroll = true;
    private UIHandle mUIHandle = new UIHandle();
    private BroadcastReceiver mBatInfoReceiver;
	public M1000PowerOffDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
		
	}

	public M1000PowerOffDialog(Context context) {
		super(context);
		mContext = context;
	}

	public M1000PowerOffDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	@SuppressLint("InlinedApi") @Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
//		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN );
		super.onCreate(savedInstanceState);
		setContentView(R.layout.power_off_dialog_layout);
		Window win = getWindow();
		Debug.d("x = " + win.getDecorView().getX() + " y = " + win.getDecorView().getY());
		Debug.d("height = " + win.getDecorView().getHeight() + " width = " + win.getDecorView().getWidth());
		win.getDecorView().setPadding(0, -50, 0, 0);
		WindowManager.LayoutParams lp = win.getAttributes();
		        lp.width = WindowManager.LayoutParams.FILL_PARENT;
		        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		        win.setAttributes(lp);
//		lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN; 
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        getWindow().setType(WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL);
		init();
		mRootView.setY(ROOT_VIEW_INIT_Y);
		
		setOnDismissListener(new OnDismissListener() {
			
			@Override
			public void onDismiss(DialogInterface dialog) {
				Log.d("dingjun","dismiss power off dialog");
			}
		});
		registerScreenOff();
	}
	
	private void init() 
	{
		mTopView = findViewById(R.id.top_layout);
		mBottomView = findViewById(R.id.bottom_layout);
		mRootView = findViewById(R.id.root_layout);
		mPowerOffTextView = (TextView)findViewById(R.id.power_off_text);
		mRebootTextView = (TextView)findViewById(R.id.reboot_text);
		mStudyIcon = (ImageView)findViewById(R.id.top_view);
		mFrameBackView = (RelativeLayout)findViewById(R.id.frame_back_view);
		updateText();
//		this.setCancelable(false);
	}

	private void getScreenShot()
	{
		
	}
	
	private void setBackground()
	{
		
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(isCanScroll == false)
		{
			return true;
		}
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
			float dy_icon = (event.getY() - mLastY) + ROOT_ICON_INIT_Y;
			if(dy < SCROOL_TO_REBOOT_Y || dy > SCROOL_TO_POWER_OFF_Y)
			{
				if(dy > SCROOL_TO_POWER_OFF_Y)
				{
					dy = SCROOL_TO_POWER_OFF_Y;
				}
				else if(dy < SCROOL_TO_REBOOT_Y)
				{
					dy = SCROOL_TO_REBOOT_Y;
				}
				mRootView.setY(dy);
				break;
			}
			mRootView.setY(dy);
			Debug.d("dy_icon = " + dy_icon + "event.getY() = " + event.getY() + " mLastY = " + mLastY);
			if(dy_icon < ICON_STOP_Y)
			{
				mStudyIcon.setY(dy_icon);
			}
			else
			{
				mStudyIcon.setY(ICON_STOP_Y);
			}
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
		mStudyIcon.setAlpha(0f);
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
		mStudyIcon.animate().translationY(0);
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
				//变黑动画
//				mTopView.setBackgroundColor(Color.BLACK);
				mFrameBackView.setVisibility(View.VISIBLE);
				mFrameBackView.setBackgroundColor(Color.BLACK);
				mFrameBackView.setAlpha(0);
				mFrameBackView.animate().setDuration(4000).alpha(1.0f);
//				mStudyIcon.animate().setDuration(8000).alpha(0.0f);
				mUIHandle.sendEmptyMessageDelayed(HANDLE_ICON_DISMISS, 3000);
				mPowerOffTextView.setVisibility(View.GONE);
//				mPowerOffTextView.setText("正在关机");
//				mPowerOffTextView.animate().setDuration(4000).alpha(0.0f);
				isCanScroll = false;
				break;
			}
			case HANDLE_REBOOT:
			{
//				mRootView.setY(SCROOL_TO_POWER_OFF_Y);
				mStudyIcon.setY(ICON_STOP_Y);
				mStudyIcon.animate().setDuration(1000).alpha(1.0f);
//				mTopView.setBackgroundColor(Color.argb(27, 255, 255, 255));
				mFrameBackView.setVisibility(View.VISIBLE);
				mFrameBackView.setBackgroundColor(Color.BLACK);
				mFrameBackView.setAlpha(0);
				mFrameBackView.animate().setDuration(4000).alpha(1.0f);
				mUIHandle.sendEmptyMessageDelayed(HANDLE_ICON_DISMISS, 4000);
				mRebootTextView.setVisibility(View.GONE);
//				mStudyIcon.animate().setDuration(8000).alpha(0f);
//				mPowerOffTextView.setVisibility(View.VISIBLE);
//				mPowerOffTextView.setText("正在重启");
//				mPowerOffTextView.animate().setDuration(4000).alpha(0.0f);
				isCanScroll = false;
				break;
			}
			case HANDLE_ICON_DISMISS:
			{
				mStudyIcon.animate().setDuration(1000).alpha(0f);
				break;
			}
			}
		}
	}
	
	public void registerScreenOff()
	{
		final IntentFilter filter = new IntentFilter();  
	    filter.addAction(Intent.ACTION_SCREEN_OFF);  
	    mBatInfoReceiver = new BroadcastReceiver() {  
	        @Override  
	        public void onReceive(final Context context, final Intent intent) {  
	            Debug.d("onReceive");  
	            String action = intent.getAction();  
	            if (Intent.ACTION_SCREEN_OFF.equals(action)) {  
	            	dismiss();
	            } 
	    };  };
	    mContext.registerReceiver(mBatInfoReceiver, filter);  
	}

	@Override
	protected void onStop() {
		super.onStop();
		if(mBatInfoReceiver != null)
		{
			mContext.unregisterReceiver(mBatInfoReceiver);
		}
	}
	
	
}
