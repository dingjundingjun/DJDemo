package com.example.Activity;

import java.lang.reflect.Field;

import com.bbk.example.demo.R;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;

public class StatusBarActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setStatusBarColor();
		setFullScreenWithTranslate();
		setContentView(R.layout.status_bar_main);
		findViewById(R.id.status_bar_control_full).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setFullScreenWithTranslate();
			}
		});
		
		findViewById(R.id.status_bar_control).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				setStatusBarColor();
			}
		});
		
		findViewById(R.id.status_bar_color).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				compat(StatusBarActivity.this,Color.RED);
			}
		});
//		switchTransSystemUI(true);
//        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS  
//                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);  
//        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN  
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION  
//                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);  
//        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  
	}
	
	private void switchTransSystemUI(boolean on) {
		View view = getWindow().getDecorView();
		int systemUIVis = view.getSystemUiVisibility();
		if (on) {
			systemUIVis |= WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		} else {
			systemUIVis &= ~WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
		}
		view.setSystemUiVisibility(systemUIVis);
	
		Field drawsSysBackgroundsField;
		try {
			drawsSysBackgroundsField = WindowManager.LayoutParams.class
					.getField("FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS");
			getWindow().addFlags(drawsSysBackgroundsField.getInt(null));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void compat(Activity activity, int statusColor)
    {
		activity.getWindow().setStatusBarColor(statusColor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            activity.getWindow().setStatusBarColor(statusColor);
            return;
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
//        {
//            int color = COLOR_DEFAULT;
//            ViewGroup contentView = (ViewGroup) activity.findViewById(android.R.id.content);
//            if (statusColor != INVALID_VAL)
//            {
//                color = statusColor;
//            }
//            View statusBarView = new View(activity);
//            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    getStatusBarHeight(activity));
//            statusBarView.setBackgroundColor(color);
//            contentView.addView(statusBarView, lp);
//        }
    }
	
	/**
	 * 设置全屏，并且状态栏透明悬浮在应用界面之上
	 */
	public void setFullScreenWithTranslate()
	{
		Window window = getWindow();  
		window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);  
		window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);  
		window.setStatusBarColor(Color.TRANSPARENT);  
	}
	
	/**
	 * 设置状态栏颜色,并且可以选择设置图标用白色或者灰色
	 */
	public void setStatusBarColor()
	{
	   Window window = getWindow();  
       window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
       //这个设置了以后，状态栏图标将变成灰色，不设置默认白色
       //需要用6.0的sdk,否则用值0x00002000;
//       window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR); 
       window.setStatusBarColor(Color.BLUE);  
	}
}
