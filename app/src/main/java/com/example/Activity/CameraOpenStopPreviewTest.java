package com.example.Activity;


import com.bbk.example.demo.R;

import android.app.Activity;
import android.content.res.Configuration;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class CameraOpenStopPreviewTest extends Activity //implements SurfaceHolder.Callback
{
	private SurfaceView mSurfaceView;
	private Camera mCamera;
	private UIHandler mUIHandler = new UIHandler();
	private TextView mTextView;
	private Button mBtn;
	private boolean isOpen = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera_preview_open_stop);
		init();
	}
	
	public void init()
	{
//		mSurfaceView = (SurfaceView)this.findViewById(R.id.myCameraView); 
//		mSurfaceView.setFocusable(true);  
//		mSurfaceView.setFocusableInTouchMode(true); 
//		mSurfaceView.setClickable(true); 
//        //SurfaceView中的getHolder方法可以获取到一个SurfaceHolder实例 
//        SurfaceHolder holder = mSurfaceView.getHolder(); 
//        //为了实现照片预览功能，需要将SurfaceHolder的类型设置为PUSH 
//        //这样，画图缓存就由Camera类来管理，画图缓存是独立于Surface的 
//        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); 
//        holder.addCallback(this); 
		mTextView = (TextView)findViewById(R.id.test_info);
		mBtn = (Button)findViewById(R.id.key);
		mBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isOpen)
				{
					mUIHandler.sendEmptyMessage(2);
				}
				else
				{
					mUIHandler.sendEmptyMessage(1);
				}
			}
		});
		
		mCamera = Camera.open(); //获取Camera实例 
		mCamera.startPreview(); 
		setTextInfo(true);
//		mUIHandler.sendEmptyMessageDelayed(2, 2000);
	}
	
	private void setTextInfo(boolean b)
	{
		if(b)
		{
			mTextView.setText("相机打开");
		}
		else
		{
			mTextView.setText("相机关闭");
		}
	}
	public class UIHandler extends Handler
	{

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch(msg.what)
			{
			case 1:
			{
				mCamera.startPreview();
				setTextInfo(true);
				isOpen = true;
//				mUIHandler.sendEmptyMessageDelayed(2, 2000);
				break;
			}
			case 2:
			{
				mCamera.stopPreview();
				setTextInfo(false);
				isOpen = false;
//				mUIHandler.sendEmptyMessageDelayed(1, 2000);
			}
			}
		}
		
	}
//
//	@Override
//	public void surfaceChanged(SurfaceHolder holder, int format, int width,
//			int height) {
//		// 当Surface被创建的时候，该方法被调用，可以在这里实例化Camera对象 
//        //同时可以对Camera进行定制 
//		mCamera = Camera.open(); //获取Camera实例 
//        /** 
//         * Camera对象中含有一个内部类Camera.Parameters.该类可以对Camera的特性进行定制 
//         * 在Parameters中设置完成后，需要调用Camera.setParameters()方法，相应的设置才会生效 
//         * 由于不同的设备，Camera的特性是不同的，所以在设置时，需要首先判断设备对应的特性，再加以设置 
//         * 比如在调用setEffects之前最好先调用getSupportedColorEffects。如果设备不支持颜色特性，那么该方法将 
//         * 返回一个null 
//         */ 
//        try { 
//             
//            Camera.Parameters param = mCamera.getParameters(); 
//            if(this.getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE){ 
//                //如果是竖屏 
//                param.set("orientation", "portrait"); 
//                //在2.2以上可以使用 
//                //camera.setDisplayOrientation(90); 
//            }else{ 
//                param.set("orientation", "landscape"); 
//                //在2.2以上可以使用 
//                //camera.setDisplayOrientation(0);               
//            } 
//            //首先获取系统设备支持的所有颜色特效，有复合我们的，则设置；否则不设置 
//            //设置完成需要再次调用setParameter方法才能生效 
//            mCamera.setParameters(param); 
//            mCamera.setPreviewDisplay(holder); 
//             
//        } catch (Exception e) { 
//            // 如果出现异常，则释放Camera对象 
//        	mCamera.release(); 
//        } 
//         
//        //启动预览功能 
//        mCamera.startPreview(); 
//	}
//
//	@Override
//	public void surfaceCreated(SurfaceHolder holder) {
//		// TODO Auto-generated method stub
//		
//	}
//
//	@Override
//	public void surfaceDestroyed(SurfaceHolder holder) {
//		mCamera.stopPreview(); 
//		mCamera.release(); 
//	}

	@Override
	protected void onDestroy() {
		mCamera.stopPreview();
		mCamera.release();
		super.onDestroy();
	}
	
	
}
