package com.example.views;

import com.bbk.example.demo.R;
import com.example.utils.Debug;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ActionBar.LayoutParams;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.AnimationDrawable;
import android.nfc.tech.MifareClassic;
import android.opengl.Visibility;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.OrientationEventListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class M1000PowerOffDialog1 extends Dialog {
	private int FrameId[] = {R.anim.power_off,R.anim.power_off1,R.anim.power_off2,R.anim.power_off3,R.anim.power_off4,R.anim.power_off5};
	private int mFrameIndex = 0;
	private boolean bLandScape = false;
	private RelativeLayout mFrameBackView;
	private Context mContext;
	private boolean isMoving = false;
	private float mLastX = 0;
	private NotificationCollopseAnimateUpdateListener mNotificationCollopseAnimateUpdateListener = new NotificationCollopseAnimateUpdateListener();
	private final int HANDLE_POWER_OFF = 1;
	private final int HANDLE_REBOOT = 2;
	private final int HANDLE_ICON_DISMISS = 3;
	private final int HANDLE_UPDATE_ANIM = 4;
	private OnCallBack mOnCallBack;
	private UIHandle mUIHandle = new UIHandle();
	private BroadcastReceiver mBatInfoReceiver;

	private ImageView mDragBtn;
	private Button mCanCelBtn;
	private TextView mDragTextView;
	private RelativeLayout mDragLayout;
	private ImageView mLogo;
	private int mDragLayoutWidth;
	private final int DRAG_TO_POWER_OFF = 665;
	private final int DRAG_TO_POWER_OFF_LANDSCAPE = 1100;
	
	private final int mAnima[] = { R.drawable.power_off_1,
			R.drawable.power_off_2, R.drawable.power_off_3,
			R.drawable.power_off_4, R.drawable.power_off_5,
			R.drawable.power_off_6, R.drawable.power_off_7,
			R.drawable.power_off_8, R.drawable.power_off_9 ,
			R.drawable.power_off_10, R.drawable.power_off_11,
			R.drawable.power_off_12, R.drawable.power_off_13,
			R.drawable.power_off_14, R.drawable.power_off_15,
			R.drawable.power_off_16, R.drawable.power_off_17,
			R.drawable.power_off_18, R.drawable.power_off_19,
			R.drawable.power_off_20, R.drawable.power_off_21,
			R.drawable.power_off_22, R.drawable.power_off_23,
			R.drawable.power_off_24, R.drawable.power_off_25,
			R.drawable.power_off_26, R.drawable.power_off_27,
			R.drawable.power_off_28, R.drawable.power_off_29,
			R.drawable.power_off_30, R.drawable.power_off_31,
			R.drawable.power_off_32, R.drawable.power_off_33,
			R.drawable.power_off_34, R.drawable.power_off_35,
			R.drawable.power_off_36, R.drawable.power_off_37,
			R.drawable.power_off_38, R.drawable.power_off_39,
			R.drawable.power_off_40, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,
			R.drawable.power_off_1, R.drawable.power_off_1,};

	public M1000PowerOffDialog1(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		mContext = context;
	}

	public M1000PowerOffDialog1(Context context) {
		super(context);
		mContext = context;
	}

	public M1000PowerOffDialog1(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	@SuppressLint("InlinedApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
		// getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		// getWindow().setFlags( WindowManager.LayoutParams.FLAG_FULLSCREEN,
		// WindowManager.LayoutParams.FLAG_FULLSCREEN );
		super.onCreate(savedInstanceState);
		setContentView(R.layout.power_off_dialog_layout1);
		Window win = getWindow();
		win.getDecorView().setPadding(0, -50, 0, 0);
		WindowManager.LayoutParams lp = win.getAttributes();
		lp.width = WindowManager.LayoutParams.FILL_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.screenOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
		win.setAttributes(lp);
		// lp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
//		int height = getWindow().getDecorView().getDisplay().getHeight();
//		int width = getWindow().getDecorView().getDisplay().getWidth();
//		Debug.d("height = " + height + " width = " + width + " mContext.getResources().getConfiguration().orientation = " + mContext.getResources().getConfiguration().orientation );
		Debug.d( " mContext.getResources().getConfiguration().orientation = " + mContext.getResources().getConfiguration().orientation );
		if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {    
	        bLandScape = true;
	    } else if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {    
	        bLandScape = false;
	    }    
		// getWindow().setType(WindowManager.LayoutParams.TYPE_STATUS_BAR_PANEL);
		init();
		// mRootView.setY(ROOT_VIEW_INIT_Y);

		setOnDismissListener(new OnDismissListener() {

			@Override
			public void onDismiss(DialogInterface dialog) {
				Log.d("dingjun", "dismiss power off dialog");
			}
		});
		// registerScreenOff();
	}

	private void init() {
		mFrameBackView = (RelativeLayout) findViewById(R.id.frame_back_view);
		// updateText();
		// this.setCancelable(false);
		mDragBtn = (ImageView) findViewById(R.id.drag_btn);
		mCanCelBtn = (Button) findViewById(R.id.drag_cancel);
		mDragLayout = (RelativeLayout) findViewById(R.id.drag_layout);
		mDragTextView = (TextView) findViewById(R.id.drag_text);
		mLogo = (ImageView)findViewById(R.id.bbk_logo);
		mLogo.setAlpha(0f);
		mLogo.setBackgroundResource(FrameId[0]);
		mCanCelBtn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		
		if(bLandScape) {
			Debug.d("bLandScape = true");
			RelativeLayout.LayoutParams dragLayoutParams = (RelativeLayout.LayoutParams)mDragLayout.getLayoutParams();
			dragLayoutParams.rightMargin = 620;
			mDragLayout.setLayoutParams(dragLayoutParams);
			RelativeLayout.LayoutParams cancelBtnParams = (RelativeLayout.LayoutParams)mCanCelBtn.getLayoutParams();
			cancelBtnParams.topMargin = 800;
			mCanCelBtn.setLayoutParams(cancelBtnParams);
		}
		
		OrientationEventListener mScreenOrientationEventListener = new OrientationEventListener(mContext) {  
            @Override  
            public void onOrientationChanged(int i) {  
            	if(mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {    
        	        Debug.d("ORIENTATION_LANDSCAPE");
        	    } else if (mContext.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {    
        	        Debug.d("ORIENTATION_PORTRAIT");
        	    }    
            }  
        };  
        mScreenOrientationEventListener.enable();
	}

	private void getScreenShot() {
		
	}

	private void setBackground() {

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			mLastX = event.getX();
			if (touchInDragBtn(event.getX(), event.getY())) {
				Debug.d("touchInDragBtn");
				isMoving = true;
				mDragLayoutWidth = mDragLayout.getWidth();
				mDragTextView.setVisibility(View.INVISIBLE);
				mDragBtn.setBackgroundResource(R.drawable.power_dialog_drag_btn_press);
				return true;
			} else {
				return false;
			}
		}
		case MotionEvent.ACTION_MOVE: {
			if (!isMoving) {
				return false;
			}
			Debug.d("ACTION_MOVE");
			RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) mDragLayout.getLayoutParams();
			params.width = (int) (mDragLayoutWidth - (event.getX() - mLastX));
			if((event.getX() - mLastX) < 0) {
				params.width = mDragLayoutWidth;
			}
			if(params.width < 180) {
				params.width = 180;
			}
			mDragLayout.setLayoutParams(params);
			float alpha = (float)params.width / (float)mDragLayoutWidth;
			if(alpha < 0.3){
				alpha = 0;
			}
			mDragLayout.setAlpha(alpha);
			break;
		}
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_UP: {
			if (!isMoving) {
				return false;
			}
			Debug.d("ACTION_UP OR ACTION_CANCEL mDragBtn.getX() = " + mDragBtn.getX());
			if (mDragBtn.getX() > DRAG_TO_POWER_OFF && bLandScape == false || mDragBtn.getX() > DRAG_TO_POWER_OFF_LANDSCAPE && bLandScape) {
				Debug.d("power off");
				powerOffAnimate();
			} else {
				Debug.d("do nothing");
				resetAnimate();
			}
			isMoving = false;
			break;
		}
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 滑动到关机的动画
	 * 
	 */
	private void powerOffAnimate() {
		mUIHandle.sendEmptyMessage(HANDLE_POWER_OFF);
	}

	/**
	 * 还原位置的动画
	 */
	private void resetAnimate() {
//		mDragBtn.animate().translationX(DRAG_BTN_INIT_X)
//				.withEndAction(new Runnable() {
//					@Override
//					public void run() {
//					}
//				})power_off5.xml
//				.setUpdateListener(mNotificationCollopseAnimateUpdateListener);
		Debug.d("mDragLayoutWidth = " + mDragLayoutWidth + " mDragLayout.getWidth() = " + mDragLayout.getWidth());
//		float scaleX = ((float)mDragLayoutWidth)/(float)mDragLayout.getWidth();
//		mDragLayout.setPivotX(mDragLayout.getWidth());
//		mDragLayout.animate().scaleX(scaleX).withEndAction(new Runnable() {
//			@Override
//			public void run() {
//			}
//		})
//		.setUpdateListener(mNotificationCollopseAnimateUpdateListener);
		updateDragLayoutWidth(mDragLayoutWidth);
		mDragTextView.setVisibility(View.VISIBLE);
		mDragLayout.setAlpha(1.0f);
		mDragBtn.setBackgroundResource(R.drawable.power_dialog_drag_btn_normal);
	}

	private void updateDragLayoutWidth(int width)
	{
		RelativeLayout.LayoutParams params;
		params = (android.widget.RelativeLayout.LayoutParams) mDragLayout.getLayoutParams();
		params.width = width;
		mDragLayout.setLayoutParams(params);
	}
	
	public class NotificationCollopseAnimateUpdateListener implements
			ValueAnimator.AnimatorUpdateListener {
		@Override
		public void onAnimationUpdate(ValueAnimator arg0) {

		}
	}

	public interface OnCallBack {
		public void onPowerOff();

		public void onReboot();
	}

	public void setCallBack(OnCallBack cb) {
		mOnCallBack = cb;
	}

	public class UIHandle extends Handler {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case HANDLE_POWER_OFF: {
				mCanCelBtn.setEnabled(false);
				mDragBtn.animate().setDuration(500).alpha(0f);
				mCanCelBtn.animate().setDuration(500).alpha(0f);
				mFrameBackView.setVisibility(View.VISIBLE);
				mFrameBackView.setBackgroundColor(Color.BLACK);
				mFrameBackView.setAlpha(0);
				mFrameBackView.animate().setStartDelay(5000).setDuration(1000).alpha(1.0f);
				mLogo.setAlpha(1.0f);
				AnimationDrawable animDrawable = (AnimationDrawable)mLogo.getBackground();
				animDrawable.start();
				play(++mFrameIndex);
//				mLogo.setVisibility(View.VISIBLE);
//				mLogo.animate().setDuration(10).alpha(1.0f);
				mUIHandle.sendEmptyMessageDelayed(HANDLE_ICON_DISMISS, 3000);
				break;
			}
			case HANDLE_REBOOT: {
				mFrameBackView.setVisibility(View.VISIBLE);
				mFrameBackView.setBackgroundColor(Color.BLACK);
				mFrameBackView.setAlpha(0);
				mFrameBackView.animate().setDuration(4000).alpha(1.0f);
				mUIHandle.sendEmptyMessageDelayed(HANDLE_ICON_DISMISS, 4000);
				break;
			}
			case HANDLE_ICON_DISMISS: {
				break;
			}
			case HANDLE_UPDATE_ANIM: {
				Debug.d("update anim frame index = " + mFrameIndex);
//				mLogo.setBackgroundResource(FrameId[mFrameIndex]);
				AnimationDrawable animDrawable = (AnimationDrawable)mLogo.getBackground();
				animDrawable.start();
				play(++mFrameIndex);
				break;
			}
		}
		}
	}

		private void play(final int pFrameNo) {
			if(mFrameIndex != pFrameNo) {
				mFrameIndex = pFrameNo;
			}
			if(mFrameIndex >= 6) {
				return;
			}
			mLogo.post(new Runnable() {
				@Override
				public void run() {
					    if (mFrameIndex <= 5) {
					    	mLogo.setBackgroundResource(FrameId[mFrameIndex]);
					    	mUIHandle.sendEmptyMessageDelayed(HANDLE_UPDATE_ANIM,400);
					    }
				}
			});
		}
		
	public void registerScreenOff() {
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
			};
		};
		mContext.registerReceiver(mBatInfoReceiver, filter);
	}

	protected void onStop() {
		super.onStop();
		if (mBatInfoReceiver != null) {
			mContext.unregisterReceiver(mBatInfoReceiver);
		}
	}

	private boolean touchInDragBtn(float x, float y) {
		Debug.d("getX = " + mDragBtn.getX() + " mDragBtn.getY() = "
				+ mDragBtn.getY());
		if (mDragBtn.getX() < x
				&& mDragBtn.getX() +  mDragBtn.getWidth() > x
				&& mDragBtn.getY() < y
				&& mDragBtn.getY() + mDragBtn.getHeight() > y) {
			return true;
		}
		return false;
	}
	
	
	}
	
	
