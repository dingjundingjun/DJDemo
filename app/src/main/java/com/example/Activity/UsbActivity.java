package com.example.Activity;


import java.lang.reflect.Field;

import com.bbk.example.demo.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class UsbActivity extends Activity implements OnClickListener
{
	public static final int FLAG_TRANSLUCENT_STATUS = 0x04000000;
	public static final int FLAG_TRANSLUCENT_NAVIGATION = 0x08000000;
	public static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = 0x80000000;
	private View mMainView;
	private Button mMountBtn;
	private Button mUnMountBtn;
	private Button mChargeBtn;
	private ProgressBar mProgressBar;
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.usb_storage_activity);
		init();
	}
	
	private void init()
	{
		mMainView = findViewById(R.id.main);
		mMountBtn = (Button) findViewById(R.id.mount_button);
		mUnMountBtn = (Button)findViewById(R.id.unmount_button);
		mChargeBtn = (Button)findViewById(R.id.only_charge_button);
		mProgressBar = (ProgressBar)findViewById(R.id.progress);
		mMountBtn.setOnClickListener(this);
		mUnMountBtn.setOnClickListener(this);
		mChargeBtn.setOnClickListener(this);;
		switchTransSystemUI(true);
	}
	
	private void switchTransSystemUI(boolean on) {
		int systemUIVis = mMainView.getSystemUiVisibility();
		if (on) {
			systemUIVis |= FLAG_TRANSLUCENT_STATUS;
		} else {
			systemUIVis &= ~FLAG_TRANSLUCENT_STATUS;
		}
		mMainView.setSystemUiVisibility(systemUIVis);

		Field drawsSysBackgroundsField;
		try {
			drawsSysBackgroundsField = WindowManager.LayoutParams.class
					.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
			getWindow().addFlags(drawsSysBackgroundsField.getInt(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.mount_button:
		{
			mMountBtn.setVisibility(View.GONE);
			mUnMountBtn.setVisibility(View.VISIBLE);
			mChargeBtn.setVisibility(View.GONE);
			mProgressBar.setVisibility(View.VISIBLE);
			break;
		}
		case R.id.unmount_button:
		{
			mMountBtn.setVisibility(View.VISIBLE);
			mUnMountBtn.setVisibility(View.GONE);
			mChargeBtn.setVisibility(View.VISIBLE);
			mProgressBar.setVisibility(View.GONE);
			break;
		}
		case R.id.only_charge_button:
		{
			break;
		}
		}
	}
}
