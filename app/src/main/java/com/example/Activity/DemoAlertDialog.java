package com.example.Activity;

import java.lang.reflect.Field;

import com.bbk.example.demo.R;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class DemoAlertDialog extends AlertDialog
{
	private View mView;
	private Context mContext;
	protected DemoAlertDialog(Context context, int theme) 
	{
		super(context, theme);
		mContext = context;
	}


	public DemoAlertDialog(Context context, boolean cancelable,
			OnCancelListener cancelListener) {
		super(context, cancelable, cancelListener);
		// TODO Auto-generated constructor stub
		mContext = context;
	}


	public DemoAlertDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		mContext = context;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		mView = LinearLayout.inflate(mContext, R.layout.demo_alertdialog, null);
		WindowManager.LayoutParams lp = getWindow().getAttributes();
		mView.setBackgroundColor(Color.RED);
		switchTransSystemUI(true);
		setContentView(mView, lp);
	}

	public void setPositiveButton(String string,
			android.content.DialogInterface.OnClickListener onClickListener) {
		// TODO Auto-generated method stub
		BitmapDrawable v;
	}

	@Override
	public void show() {
		super.show();
	}
	
	public static final int FLAG_TRANSLUCENT_STATUS = 0x04000000;
	public static final int FLAG_TRANSLUCENT_NAVIGATION = 0x08000000;
	public static final int FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS = 0x80000000;

	private void switchTransSystemUI(boolean on) {
		View view = getWindow().getDecorView();
		int systemUIVis = view .getSystemUiVisibility();
		if (on) {
			systemUIVis |= FLAG_TRANSLUCENT_STATUS;
		} else {
			systemUIVis &= ~FLAG_TRANSLUCENT_STATUS;
		}
		view.setSystemUiVisibility(systemUIVis);

//		Field drawsSysBackgroundsField;
//		try {
//			drawsSysBackgroundsField = WindowManager.LayoutParams.class
//					.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
//			getWindow().addFlags(drawsSysBackgroundsField.getInt(null));
//		} catch (Exception e) {
//			e.printStackTrace();
//		}\
		
		
	}
	
	@Override
	public void setView(View view) 
	{
		super.setView(view);
	}
	
	
}
