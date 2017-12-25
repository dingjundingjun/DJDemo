package com.example.Activity;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;

import com.bbk.example.demo.R;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class TestCameraActivity extends Activity implements OnClickListener
{
	private Button mTestCameraBtn;
	private Button mStopCameraBtn;
	private TextView mTextInfo;
	private int mTestTimes = 0;
	private final String TAG = "TestCameraActivity";
	private final int START_CAMERA_HANDLER = 1;
	private final int CLOSE_CAMERA_HANDLER = 2;
	private CameraHandler mCameraHandler = new CameraHandler();
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test_camera);
		init();
	}
	
	private void init()
	{
		mTestCameraBtn = (Button)findViewById(R.id.start_btn);
		mStopCameraBtn = (Button)findViewById(R.id.stop_btn);
		mTestCameraBtn.setOnClickListener(this);
		mStopCameraBtn.setOnClickListener(this);
		mTextInfo = (TextView)findViewById(R.id.test_info);
		showTestInfo();
	}

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.start_btn:
		{
			mCameraHandler.sendEmptyMessage(START_CAMERA_HANDLER);
			break;
		}
		case R.id.stop_btn:
		{
			mCameraHandler.removeMessages(START_CAMERA_HANDLER);
			mCameraHandler.removeMessages(CLOSE_CAMERA_HANDLER);
			break;
		}
		}
	}
	
	public void openCamera()
	{
		Intent intent = new Intent();
		ComponentName comp = new ComponentName("com.android.camera2","com.android.camera.CameraActivity");
		intent.setComponent(comp);
		startActivityForResult(intent, 100);
		
	}
	
	private void killCameraProcess()
	{
		finishActivity(100);
	}

	private void showTestInfo()
	{
		mTextInfo.setText("测试了 " + mTestTimes + "次");
	}
	
	class CameraHandler extends Handler
	{
		@Override
		public void handleMessage(Message msg) 
		{
			super.handleMessage(msg);
			switch(msg.what)
			{
			case START_CAMERA_HANDLER:
			{
				openCamera();
				this.sendEmptyMessageDelayed(CLOSE_CAMERA_HANDLER, 6000);
				break;
			}
			case CLOSE_CAMERA_HANDLER:
			{
				killCameraProcess();
				this.sendEmptyMessageDelayed(START_CAMERA_HANDLER, 3000);
				mTestTimes++;
				showTestInfo();
				break;
			}
			}
		}
	}
}
