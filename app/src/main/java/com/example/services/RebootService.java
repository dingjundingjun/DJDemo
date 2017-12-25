package com.example.services;

import java.util.ArrayList;
import java.util.List;

import com.example.utils.Debug;

import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.hardware.input.InputManager;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.os.SystemClock;
import android.view.inputmethod.InputMethodManager;

public class RebootService extends Service{
	private String TAG = "RebootService";
	WakeLock mWakeLock;
	PowerManager mPowerManager;
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}



	@Override
	public void onCreate() {
		super.onCreate();
		KeyguardManager k;
		Debug.d("onCreate33333333333333");
		mPowerManager = (PowerManager)this.getSystemService(Service.POWER_SERVICE);
        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK,"My Lock");
//        mWakeLock = mPowerManager.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK,"demo Lock");
//        mWakeLock.setReferenceCounted(false);
        mWakeLock.acquire();
        Thread thread = new Thread(new Runnable() {
			@Override
			public void run() {
				Debug.d("sleep 11111111111000");
				while(true) {
					try {
						Debug.d("sleep 1000");
						Thread.sleep(1000);
						KeyguardManager mKeyguardManager = (KeyguardManager) getSystemService(KEYGUARD_SERVICE);   
					    if (mKeyguardManager.inKeyguardRestrictedInputMode()) {  
					    	Debug.d("screen on");
					    } else {
					    	Debug.d("screen off");
					    }
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
				}
			}
		});
//    	thread.start();
//    	KeyguardManager manager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);  
//        KeyguardManager.KeyguardLock lock = manager  
//                .newKeyguardLock("KeyguardLock");  
//        lock.disableKeyguard();  
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Debug.d("onStartCommand333");
		return START_STICKY;
//		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}
