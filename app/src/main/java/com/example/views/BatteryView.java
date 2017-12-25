package com.example.views;

import com.bbk.example.demo.R;
import com.example.utils.Debug;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.BatteryManager;
import android.util.AttributeSet;
import android.view.View;


public class BatteryView  extends View {
	private BroadcastReceiver batteryLevelRcvr;  
	private IntentFilter batteryLevelFilter;  
	private Context mContext;
	private int mLevel;
	private int mStatus;
	private int mHealth;
	private int mPlugged;
	private boolean bPulugged = false;;
	private boolean bDark = false;
	private final int LOW_BATTERY = 20;
	private final int BITMAP_PADDING = 4;
	private Bitmap mBatteryFrame;
	private Bitmap mBatteryProgress;
	
	private Bitmap mBatteryFrameNormalLight;
	private Bitmap mBatteryFrameErrorLight;
	private Bitmap mBatteryChargeFullLight;
	private Bitmap mBatteryFrameChargeingLight;
	private Bitmap mBatteryChargeingLight;
	private Bitmap mBatteryLowLight;
	private Bitmap mBatteryErrorLight;
	
	/**电池异常*/
	private Bitmap mBatteryFrameErrorDark;
	/**充电状态的外框*/
	private Bitmap mBatteryFrameChargeingDark;
	/**没充电状态下的外框*/
	private Bitmap mBatteryFrameNormalDark;
	/**没充电状态下的进度*/
	private Bitmap mBatteryChargeFullDark;
	/**充电状态下的进度**/
	private Bitmap mBatteryChargeingDark;
	/**没充电状态下的低电进度*/
	private Bitmap mBatteryLowDark;
	/**电池异常*/
	private Bitmap mBatteryErrorDark;
	
	public BatteryView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void init(Context c) {
		mContext = c;
		initBitmap();
		setDark(true);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(mStatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {
			//没有找到电池
		} 
//		else if(mStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
//			//充电状态
//		} else if(mStatus == BatteryManager.BATTERY_STATUS_DISCHARGING || mStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
//			//没有 充电
//		} else if(mStatus == BatteryManager.BATTERY_STATUS_FULL) 
			//充满电
			Rect srcRect = new Rect(0,0,mBatteryProgress.getWidth(),mBatteryProgress.getHeight());
			int width = mLevel * mBatteryProgress.getWidth() / 100; 
			Rect distRect = new Rect(BITMAP_PADDING,BITMAP_PADDING,width + BITMAP_PADDING,mBatteryProgress.getHeight() + BITMAP_PADDING);
			canvas.drawBitmap(mBatteryProgress, srcRect, distRect, null);
			canvas.drawBitmap(mBatteryFrame, 0, 0, null);
//		}
	}
	
	private void initBitmap() {
		mBatteryFrameNormalLight = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_frame_normal);
		mBatteryChargeFullLight = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_normal_charge_full);
		mBatteryFrameChargeingLight = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_frame_chargeing_light);
		mBatteryLowLight = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_normal_low_light);
		mBatteryChargeingLight = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_chargeing_light);
		mBatteryFrameErrorLight = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_frame_error_light);
		mBatteryErrorLight = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_error_light);
		
		mBatteryFrameNormalDark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_frame_normal_dark);
		mBatteryChargeFullDark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_normal_charge_full_dark);
		mBatteryFrameChargeingDark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_frame_chargeing_dark);
		mBatteryLowDark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_normal_low_dark);
		mBatteryChargeingDark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_chargeing_dark);
		mBatteryFrameErrorDark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_frame_error_dark);
		mBatteryErrorDark = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.battery_error_dark);
	}
	
	public void toggleDark() {
	    bDark = bDark ? false:true;
	    setDark(bDark);
	}
	
	public void setDark(boolean dark) {
		bDark = dark;
		updateBmp();
		invalidate();
	}
	
	private void updateBmp() {
		if(bDark) {
			if(mStatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {
				mBatteryFrame = mBatteryFrameErrorDark;
				mBatteryProgress = mBatteryErrorDark;
				mLevel = 100;
			}else {
			if(bPulugged) {
				mBatteryFrame= mBatteryFrameChargeingDark;
				mBatteryProgress = mBatteryChargeingDark;
			} else {
				mBatteryFrame = mBatteryFrameNormalDark;
				if(mLevel < LOW_BATTERY) {
					mBatteryProgress = mBatteryLowDark;
				}else {
					mBatteryProgress = mBatteryChargeFullDark;
				}
			}
			}
		} else {
			if(mStatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {
				mBatteryFrame = mBatteryFrameErrorLight;
				mBatteryProgress = mBatteryErrorLight;
				mLevel = 100;
			}else {
			if(bPulugged) {
				mBatteryFrame= mBatteryFrameChargeingLight;
				mBatteryProgress = mBatteryChargeingLight;
			} else {
				mBatteryFrame = mBatteryFrameNormalLight;
				if(mLevel < LOW_BATTERY) {
					mBatteryProgress = mBatteryLowLight;
				}else {
					mBatteryProgress = mBatteryChargeFullLight;
				}
			}
			}
		}
	}
	
	public void update(int l,int s,int h,int p) {
		mLevel = l;
		mStatus = s;
		mHealth = h;
		mPlugged = p;
		bPulugged = p != 0 ? true:false;
		Debug.d("update l = " + l + "  s = " + s + " h = " + h + " p = "  + p + " bPulugged = " + bPulugged);
		updateBmp();
		invalidate();
	}
}
