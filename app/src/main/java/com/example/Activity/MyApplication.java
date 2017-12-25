package com.example.Activity;

import android.app.Application;
import android.util.Log;

public class MyApplication extends Application
{
	@Override
	public void onCreate() {
		new Exception().printStackTrace();
		Log.d("dingjun111111111111","application onCreate");
		super.onCreate();
//		JuheSDKInitializer.initialize(getApplicationContext());
	}
}
