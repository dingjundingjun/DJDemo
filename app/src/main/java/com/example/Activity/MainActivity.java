package com.example.Activity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.ActivityManager.TaskDescription;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.KeyguardManager;
import android.app.KeyguardManager.KeyguardLock;
import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.TextView;

import com.bbk.example.demo.R;
import com.example.services.RebootService;
import com.example.utils.Debug;

public class MainActivity extends Activity implements OnChildClickListener,OnSeekBarChangeListener
{
	private ExpandableListView mChooseDemoList;
	private List<List<ActionEx>> mParentList = new ArrayList<List<ActionEx>>();
	private String mParentLabel[];
	private DemoAdapter mDemoAdapter = new DemoAdapter();

	@Override
	public void onBackPressed() {
		Log.d("dingjun_demo_test","onBackPressed");
		super.onBackPressed();
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		init();
	}

	@Override
	protected void onPause() {
		super.onPause();
		Debug.d("onPause");
	}

    @Override
	protected void onResume() {
    	Debug.d("onResume");
		super.onResume();
	}

    
	@Override
	protected void onStart() {
		super.onStart();
		Debug.d("onStart");
	}

	@Override
	protected void onStop() {
		super.onStop();
		Debug.d("onStop");
	}
	

	public void init()
    {
		LinearLayout ll = (LinearLayout)findViewById(R.id.main_view);
    	mChooseDemoList = (ExpandableListView)findViewById(R.id.choose_demo_list);
    	mParentLabel = this.getResources().getStringArray(R.array.parent_text);
    	initParentList(mParentLabel);
    	mChooseDemoList.setAdapter(mDemoAdapter);
    	mChooseDemoList.setOnChildClickListener(this);
    }
	
	/**
	 * 0:system
	 * 1:ui
	 * */
	private void initParentList(String pl[])
	{
		int len = pl.length;
		for(int i = 0 ; i < len;i++)
		{
			List<ActionEx> aeList = new ArrayList<ActionEx>();
			String actions[] = null;
			String actionsText[] = null;
			if(i == 0) 
			{
				actions = this.getResources().getStringArray(R.array.actions_system);
		    	actionsText = this.getResources().getStringArray(R.array.actions_system_text);
			}
			else if(i == 1)
			{
				actions = this.getResources().getStringArray(R.array.actions_ui);
		    	actionsText = this.getResources().getStringArray(R.array.actions_ui_text);
			}
			else if(i == 2)
			{
				actions = this.getResources().getStringArray(R.array.actions_demo);
		    	actionsText = this.getResources().getStringArray(R.array.actions_demo_text);
			}
			if(actions != null && actionsText != null)
			{
				int lenA = actions.length;
		    	for(int j = 0;j < lenA;j++)
		    	{
		    		ActionEx ae = new ActionEx();
					ae.action = actions[j];
					ae.actionText = actionsText[j];
					aeList.add(ae);
		    	}
		    	mParentList.add(aeList);
			}
			
		}
	}
    
    @Override
	protected void onDestroy() {
		super.onDestroy();
		Debug.d("onDestroy");
	}

	class DemoAdapter extends BaseExpandableListAdapter
    {
		@Override
		public int getGroupCount() {
			return mParentList.size();
		}

		@Override
		public int getChildrenCount(int groupPosition) 
		{
			return mParentList.get(groupPosition).size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return mParentList.get(groupPosition);
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return mParentList.get(groupPosition).get(childPosition);
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
				View convertView, ViewGroup parent) 
		{
			TextView item = null;
			if(convertView == null)
			{
				item = new TextView(MainActivity.this);
			}
			else
			{
				item = (TextView)convertView;
			}
			item.setText(mParentLabel[groupPosition]);
			item.setTextSize(50);
			return item;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
				boolean isLastChild, View convertView, ViewGroup parent) {
			TextView item = null;
			if(convertView == null)
			{
				item = new TextView(MainActivity.this);
			}
			else
			{
				item = (TextView)convertView;
			}
			item.setText(mParentList.get(groupPosition).get(childPosition).actionText);
			item.setTextSize(20);
			item.setFocusable(false);
			return item;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
    }

	@Override
	public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
		gotoDemo(mParentList.get(groupPosition).get(childPosition).action);
		return false;
	}
	
	public void gotoDemo(String action)
	{
		Intent intent = new Intent(action);
		startActivity(intent);
	}

	public class ActionEx
	{
		public String action;
		public String actionText; 
	}

	public void registerScreenOn()
	{
		final IntentFilter filter = new IntentFilter();  
	    // 屏幕灭屏广播  
	    filter.addAction(Intent.ACTION_SCREEN_OFF);  
	    // 屏幕亮屏广播  
	    filter.addAction(Intent.ACTION_SCREEN_ON);  
	    // 屏幕解锁广播  
	    filter.addAction(Intent.ACTION_USER_PRESENT);  
	    filter.addAction("android.net.wifi.STATE_CHANGE");
	    filter.addAction("com.eebbk.action.wallpaper_change");
	    filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
	    filter.addAction(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
	   BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {  
	        @Override  
	        public void onReceive(final Context context, final Intent intent) {  
	            Debug.d("onReceive");  
	            String action = intent.getAction();
	            if (Intent.ACTION_SCREEN_ON.equals(action)) {  
	            	Debug.d("screen on");  
					Debug.d("action = " + action);
					Intent intentService = new Intent("com.example.action.RebootService");
					intentService.setClass(context, RebootService.class);
					context.startService(intentService);
	            	Debug.d("screen on  111");  
	            } else if (Intent.ACTION_SCREEN_OFF.equals(action)) {
	            	Debug.d("screen off");  
	            } else if (Intent.ACTION_USER_PRESENT.equals(action)) {
	            	Debug.d("screen unlock");  
	            } else if (Intent.ACTION_CLOSE_SYSTEM_DIALOGS.equals(intent.getAction())) {  
	            	Debug.d(" receive Intent.ACTION_CLOSE_SYSTEM_DIALOGS");  
	            }  else if(("android.net.wifi.STATE_CHANGE").equals(intent.getAction()))  {
	            	Debug.d("android.net.wifi.STATE_CHANGE");  
	            	final NetworkInfo networkInfo = (NetworkInfo) intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
	            	State state = networkInfo.getState();  
	                boolean isConnected = state == State.CONNECTED;// 当然，这边可以更精确的确定状态  
	                Debug.d("isConnected " + isConnected);  
	                if (isConnected) {  
	                } else {  
	                }  
	            	if (networkInfo != null && networkInfo.isConnected()) {
	            		Debug.d("isConnected1111 " + isConnected);  

	            	}
	            } else if(("android.net.conn.CONNECTIVITY_CHANGE").equals(intent.getAction())) {
	            	Debug.d("android.net.conn.CONNECTIVITY_CHANGE");
	            	Debug.d("android.net.conn.CONNECTIVITY_CHANGE end");
	            }
	        }  
	    };  
	    Debug.d("registerReceiver");  
	    registerReceiver(mBatInfoReceiver, filter);  
	}
	

	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
}
