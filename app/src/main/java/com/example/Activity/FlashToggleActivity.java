package com.example.Activity;

import com.bbk.example.demo.R;

import android.app.Activity;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class FlashToggleActivity extends Activity{
	private Button mOpenFlash;
	private Button mCloseFlash;
	private Camera mCamera;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.flash_light_toggle_layout);
		init();
	}
	
	private void init() {
		mOpenFlash = (Button)findViewById(R.id.open_flash_light);
		mCloseFlash = (Button)findViewById(R.id.close_flash_light);
		mOpenFlash.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("com.eebbk.flash.control");
				intent.putExtra("key", true);
				sendBroadcast(intent);
			}
		});
		
		mCloseFlash.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent("com.eebbk.flash.control");
				intent.putExtra("key", false);
				sendBroadcast(intent);
			}
		});
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
		} else if (!flashMode.equals(Parameters.FLASH_MODE_TORCH) && on) {
			parameters.setFlashMode(Parameters.FLASH_MODE_TORCH);// 开启
			mCamera.setParameters(parameters);
		}
		else
		{
			mCamera.release();
			mCamera = null;
		}
    }
    
}
