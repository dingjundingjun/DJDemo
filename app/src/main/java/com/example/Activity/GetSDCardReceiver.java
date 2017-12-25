package com.example.Activity ;

import android.content.BroadcastReceiver ;
import android.content.Context ;
import android.content.Intent ;
import android.util.Log;

/**
 * @title SD卡广播
 * @description 监听SD卡广播
 * @company 步步高教育电子
 * @author LinTx
 * @version 1.0
 * @created 2013-4-16
 * 
 */
public class GetSDCardReceiver extends BroadcastReceiver
{

	public final String TAG = "GetSDCardReceiver" ;

	public String ACTION_SDCARD_MEDIA_MOUNTED = "android.intent.action.MEDIA_MOUNTED" ;
	public String ACTION_SDCARD_MEDIA_REMOVED = "android.intent.action.MEDIA_REMOVED" ;
	public String ACTION_SDCARD_MEDIA_UNMOUNTED = "android.intent.action.MEDIA_UNMOUNTED" ;
	public String ACTION_SDCARD_MEDIA_BAD_REMOVAL = "android.intent.action.MEDIA_BAD_REMOVAL" ;
	public String ACTION_SDCARD_MEDIA_SHARED = "android.intent.action.MEDIA_SHARED" ;
	public String ACTION_SDCARD_MEDIA_EJECT = "android.intent.action.MEDIA_EJECT" ;
	public String ACTION_SDCARD_MEDIA_SCANNER_STARTED = "android.intent.action.MEDIA_SCANNER_STARTED" ;
	public String ACTION_SDCARD_MEDIA_SCANNER_FINISHED = "android.intent.action.MEDIA_SCANNER_FINISHED" ;

	@Override
	public void onReceive(Context context, Intent intent)
	{

		String action = intent.getAction() ;

		if ( action.equals(ACTION_SDCARD_MEDIA_MOUNTED) )
		{

			Log.d(TAG, "SD卡被插入，且已经挂载") ;

		}
		else if ( action.equals(ACTION_SDCARD_MEDIA_REMOVED) )
		{

			Log.d(TAG, "SD卡被移除") ;

		}
		else if ( action.equals(ACTION_SDCARD_MEDIA_UNMOUNTED) )
		{

			Log.d(TAG, "SD卡存在,但还没挂载") ;

		}
		else if ( action.equals(ACTION_SDCARD_MEDIA_BAD_REMOVAL) )
		{

			Log.d(TAG, "SD卡已从插槽拔出，但挂载点还没解除") ;

		}
		else if ( action.equals(ACTION_SDCARD_MEDIA_SHARED) )
		{

			Log.d(TAG, "SD卡作为USB大容量存储被共享，挂载被解除") ;

		}
		else if ( action.equals(ACTION_SDCARD_MEDIA_EJECT) || action.equals(ACTION_SDCARD_MEDIA_BAD_REMOVAL) || action.equals(ACTION_SDCARD_MEDIA_REMOVED) )
		{

			Log.d(TAG, "物理的拔出SD卡") ;
		}
		else if ( intent.getAction().equals(ACTION_SDCARD_MEDIA_SCANNER_STARTED) )
		{

			Log.d(TAG, "开始扫描SD卡") ;

		}
		else if ( intent.getAction().equals(ACTION_SDCARD_MEDIA_SCANNER_FINISHED) )
		{
			Log.d(TAG, "SD卡扫描完成") ;
			System.currentTimeMillis();
		}
	}

}
