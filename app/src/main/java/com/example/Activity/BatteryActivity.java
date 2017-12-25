package com.example.Activity;

import com.bbk.example.demo.R;
import com.example.utils.Debug;
import com.example.views.BatteryView;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.BatteryManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class BatteryActivity extends Activity{
public static final String ACTION_LEVEL_TEST_ON = "battery_test_action_on";
public static final String ACTION_LEVEL_TEST_OFF = "battery_test_action_off";
private BatteryView mBatteryView; 
private BroadcastReceiver mBatteryLevelRcvr;  
private IntentFilter mBatteryLevelFilter;  
private TextView mBatteryInfo;
private Button mChangeColorBtn;
private Button mAddBtn;
private Button mDownBtn;
private CheckBox mCheckPlugged;
private CheckBox mCheckDebug;
private CheckBox mCheckError;
private EditText mNumLength;

private int mLevel;
private int mStatus;
private int mHealth;
private int mPlugged;
private int mScale;
private boolean bTest = false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.battery_activity);
		init();
	}
	
	private void init() {
		Configuration c = this.getResources().getConfiguration();
		Log.d("Configuration_dingjun","c = " + c);
		mBatteryView = (BatteryView)findViewById(R.id.battery);
		mBatteryInfo = (TextView)findViewById(R.id.battery_info);
		mChangeColorBtn = (Button)findViewById(R.id.btn_change_color);
		mAddBtn = (Button)findViewById(R.id.btn_add);
		mDownBtn = (Button)findViewById(R.id.btn_down);
		mCheckPlugged = (CheckBox)findViewById(R.id.check_chargeing);
		mCheckDebug = (CheckBox)findViewById(R.id.check_debug);
		mCheckError = (CheckBox)findViewById(R.id.check_battery_error);
		mNumLength = (EditText)findViewById(R.id.num_length);
		mNumLength.setText("1");
		
		mCheckError.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				Intent intent = new Intent(ACTION_LEVEL_TEST_ON);
				intent.putExtra("level", mLevel);
				intent.putExtra("status",  arg1?BatteryManager.BATTERY_STATUS_UNKNOWN:4);
				intent.putExtra("health", mHealth);
				intent.putExtra("scale", mScale);
				intent.putExtra("plugged", 1);
				sendBroadcast(intent);
			}
		});
		
		mCheckDebug.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
				bTest = arg1;
				Intent intent;
				if(bTest) {
					intent = new Intent(ACTION_LEVEL_TEST_ON);
				}else {
					intent = new Intent(ACTION_LEVEL_TEST_OFF);
				}
				intent.putExtra("level", mLevel);
				intent.putExtra("status", mStatus);
				intent.putExtra("health", mHealth);
				intent.putExtra("scale", mScale);
				intent.putExtra("plugged",0);
				sendBroadcast(intent);
			}
		});
		
		mCheckPlugged.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton arg0, boolean checked) {
				Intent intent = new Intent(ACTION_LEVEL_TEST_ON);
				intent.putExtra("level", mLevel);
				intent.putExtra("status", mStatus);
				intent.putExtra("health", mHealth);
				intent.putExtra("scale", mScale);
				intent.putExtra("plugged", checked?1:0);
				sendBroadcast(intent);
			}
		});
		
		mAddBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(mLevel >= 100) {
					return;
				}
				int numLength;
				if(mNumLength.getText().toString().equals("")) {
					numLength = 1;
				}else {
					numLength = Integer.parseInt(mNumLength.getText().toString());
				}
				if(mLevel+numLength > 100) {
					return;
				}
				Intent intent = new Intent(ACTION_LEVEL_TEST_ON);
				intent.putExtra("level", mLevel+numLength);
				intent.putExtra("status", mStatus);
				intent.putExtra("health", mHealth);
				intent.putExtra("scale", mScale);
				intent.putExtra("plugged", mPlugged);
				sendBroadcast(intent);
			}
		});
		
		mDownBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(mLevel <= 0) {
					return;
				}
				int numLength;
				if(mNumLength.getText().toString().equals("")) {
					numLength = 1;
				}else {
					numLength = Integer.parseInt(mNumLength.getText().toString());
				}
				if(mLevel-numLength < 0) {
					return;
				}
				Intent intent = new Intent(ACTION_LEVEL_TEST_ON);
				intent.putExtra("level", mLevel-numLength);
				intent.putExtra("status", mStatus);
				intent.putExtra("health", mHealth);
				intent.putExtra("scale", mScale);
				intent.putExtra("plugged", mPlugged);
				sendBroadcast(intent);
			}
		});
		
		mChangeColorBtn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mBatteryView.toggleDark();
			}
		});
		mBatteryView.init(this);
		monitorBatteryState();
	}
	
	private void monitorBatteryState() {  
        mBatteryLevelRcvr = new BroadcastReceiver() {  
  
            public void onReceive(Context context, Intent intent) {  
            	if(bTest && !intent.getAction().equals(ACTION_LEVEL_TEST_ON)) {
            		return;
            	}
                StringBuilder sb = new StringBuilder();  
                int rawlevel = intent.getIntExtra("level", -1);  
                int scale = intent.getIntExtra("scale", -1);  
                int status = intent.getIntExtra("status", -1);  
                int health = intent.getIntExtra("health", -1);  
                int plugged = intent.getIntExtra("plugged", 0);
                int level = -1; // percentage, or -1 for unknown  
                if (rawlevel >= 0 && scale > 0) {  
                    level = (rawlevel * 100) / scale;  
                }  
                mLevel = level;
                mStatus = status;
                mHealth = health;
                mPlugged = plugged;
                mScale = scale;
                mBatteryView.update(level, status, health,plugged);
                sb.append("The phone");  
                if (BatteryManager.BATTERY_HEALTH_OVERHEAT == health) {  
                    sb.append("'s battery feels very hot!");  
                } else {  
                    switch (status) {  
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:  
                        sb.append("no battery.");  
                        break;  
                    case BatteryManager.BATTERY_STATUS_CHARGING:  
                        sb.append("'s battery");  
                        if (level <= 33)  
                            sb.append(" is charging, battery level is low"  
                                    + "[" + level + "]");  
                        else if (level <= 84)  
                            sb.append(" is charging." + "[" + level + "]");  
                        else  
                            sb.append(" will be fully charged level = " + level);  
                        break;  
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:  
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:  
                        if (level == 0)  
                            sb.append(" needs charging right away.");  
                        else if (level > 0 && level <= 33)  
                            sb.append(" is about ready to be recharged, battery level is low"  
                                    + "[" + level + "]");  
                        else  
                            sb.append("'s battery level is" + "[" + level + "]");  
                        break;  
                    case BatteryManager.BATTERY_STATUS_FULL:  
                        sb.append(" is fully charged. level = " + level);  
                        break;  
                    default:  
                        sb.append("'s battery is indescribable!");  
                        break;  
                    }  
                }  
                sb.append(' ');  
                Debug.d("battery:" + sb.toString());
                mBatteryInfo.setText(sb.toString());
            }  
        };  
        mBatteryLevelFilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED); 
        mBatteryLevelFilter.addAction(ACTION_LEVEL_TEST_ON);
        mBatteryLevelFilter.addAction(ACTION_LEVEL_TEST_OFF);
        registerReceiver(mBatteryLevelRcvr, mBatteryLevelFilter);
		Map map = new HashMap();
		map.put("a1", "a11");
		map.put("a2", "a22");
		Set set = map.keySet();
		for(Iterator iter = set.iterator(); iter.hasNext();){
			String key = (String)iter.next();
			String value = (String)map.get(key);
			System.out.println(key);
			System.out.println(value);
		}
    }

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mBatteryInfo != null) {
			unregisterReceiver(mBatteryLevelRcvr);
		}
	} 
	
	/***
	  “status”（int类型）…状态，定义值是BatteryManager.BATTERY_STATUS_XXX。
	  public static final int BATTERY_STATUS_UNKNOWN = 1;
      public static final int BATTERY_STATUS_CHARGING = 2;
      public static final int BATTERY_STATUS_DISCHARGING = 3;
      public static final int BATTERY_STATUS_NOT_CHARGING = 4;
      public static final int BATTERY_STATUS_FULL = 5;

    “health”（int类型）…健康，定义值是BatteryManager.BATTERY_HEALTH_XXX。
    // BatteryManager.BATTERY_STATUS_CHARGING 表示是充电状态
    // BatteryManager.BATTERY_STATUS_DISCHARGING 放电中
    // BatteryManager.BATTERY_STATUS_NOT_CHARGING 未充电
    // BatteryManager.BATTERY_STATUS_FULL 电池满
    
     //BatteryManager.BATTERY_HEALTH_GOOD 良好
     //BatteryManager.BATTERY_HEALTH_OVERHEAT 过热
     //BatteryManager.BATTERY_HEALTH_DEAD 没电
     /BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE 过电压
     //BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE 未知错误
      * 
    public static final int BATTERY_HEALTH_UNKNOWN = 1;
    public static final int BATTERY_HEALTH_GOOD = 2;
    public static final int BATTERY_HEALTH_OVERHEAT = 3;
    public static final int BATTERY_HEALTH_DEAD = 4;
    public static final int BATTERY_HEALTH_OVER_VOLTAGE = 5;
    public static final int BATTERY_HEALTH_UNSPECIFIED_FAILURE = 6;
    public static final int BATTERY_HEALTH_COLD = 7;

    
    “present”（boolean类型）
    “level”（int类型）…电池剩余容量
    “scale”（int类型）…电池最大值。通常为100。
    “icon-small”（int类型）…图标ID。
    “plugged”（int类型）…连接的电源插座，定义值是BatteryManager.BATTERY_PLUGGED_XXX。

    public static final int BATTERY_PLUGGED_AC = 1;
    public static final int BATTERY_PLUGGED_USB = 2;
    public static final int BATTERY_PLUGGED_WIRELESS = 4;
    public static final int BATTERY_PLUGGED_ANY =
            BATTERY_PLUGGED_AC | BATTERY_PLUGGED_USB | BATTERY_PLUGGED_WIRELESS;

    “voltage”（int类型）…mV。   电压
    “temperature”（int类型）…温度，0.1度单位。例如 表示197的时候，意思为19.7度。
    “technology”（String类型）…电池类型
	 * */
}
