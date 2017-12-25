package com.example.receiver;

import com.example.services.RebootService;
import com.example.utils.Debug;

import android.app.IntentService;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.ErrorCallback;
import android.hardware.Camera.Parameters;
import android.util.Log;

public class FlashReceiver extends BroadcastReceiver{
	private final String TAG = "FlashReceiver";
	private final String FLASH_CONTROL = "com.eebbk.flash.control";
	private Camera mCamera;
	private Context mContext;
	@Override
	public void onReceive(Context context, Intent intent) {
		String action = intent.getAction();
		mContext = context;
		if(action.equals(FLASH_CONTROL)) {
			Log.d(TAG,"action = " + FLASH_CONTROL);
			final boolean key = intent.getBooleanExtra("key", false);
			Thread thread = new Thread(new Runnable() {
				@Override
				public void run() {
					openFlashlight(key);
				}
			});
			thread.start();
		} else if(action.equals(Intent.ACTION_SCREEN_ON)) {
			Debug.d("action = " + action);
			Intent intentService = new Intent("com.example.action.RebootService");
			intentService.setClass(context, RebootService.class);
			context.startService(intentService);
		}
	}

	/**
     * 闪光灯开关
     */
    public void openFlashlight(boolean on) {
    	try
    	{
    		if(mCamera == null)
    		{
    			mCamera = Camera.open();
    			mCamera.setErrorCallback(new ErrorCallback() {
    				@Override
    				public void onError(int arg0, Camera arg1) {
    					Log.d(TAG,"onError arg0 = " + arg0);
    					notifiKey(false);
    				}
    			});
    		}
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	if(mCamera == null)
        {
        	return;
        }
        Parameters parameters = mCamera.getParameters();
        // String flashMode = parameters.getFlashMode();
        String flashMode = parameters.getFlashMode();
		if (flashMode.equals(Parameters.FLASH_MODE_TORCH) && !on) {
			parameters.setFlashMode(Parameters.FLASH_MODE_OFF);// 关闭
			mCamera.setParameters(parameters);
			mCamera.release();
			mCamera = null;
			notifiKey(false);
		} else if (!flashMode.equals(Parameters.FLASH_MODE_TORCH) && on) {
			try{
			parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// 开启
			mCamera.setParameters(parameters);
			notifiKey(true);
			}catch(Exception e) {
				notifiKey(false);
			}
		}
		else
		{
			mCamera.release();
			mCamera = null;
			notifiKey(false);
		}
    }
    
    private void notifiKey(boolean key) {
    	Intent intent = new Intent("com.android.systemui.flashliht.off");
    	intent.putExtra("key", key);
    	mContext.sendBroadcast(intent);
    }
}
