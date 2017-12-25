package com.example.Activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import com.bbk.example.demo.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.widget.Button;
import android.widget.TextView;

public class OtaPushTestActivity extends Activity implements OnClickListener
{
	private Button mOtaSendBroadCastBtn;
	private Button mCameraDistanceBtn;
	private int mSync = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ota_push_test);
        initUI();
    }
    
    public void initUI()
    {
    	mOtaSendBroadCastBtn = (Button)findViewById(R.id.ota_send_broadcast);
    	mCameraDistanceBtn = (Button)findViewById(R.id.camera_distance_send_broadcast);
    	mOtaSendBroadCastBtn.setOnClickListener(this);
    	mCameraDistanceBtn.setOnClickListener(this);
    	
    	////
    	WebView webview = (WebView)findViewById(R.id.webview);
    	final WebSettings webSettings = webview.getSettings();
		webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
		webSettings.setAllowContentAccess(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setAppCacheEnabled(false);
		webSettings.setBuiltInZoomControls(false);
		webSettings.setDatabaseEnabled(true);
		webSettings.setDisplayZoomControls(false);
		webSettings.setDomStorageEnabled(true);
		webSettings.setSupportZoom(false);
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAppCacheMaxSize(1024*1024*10);
		webSettings.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
		webSettings.setDefaultTextEncodingName("utf-8");
		webSettings.setBlockNetworkImage( true );
		webview.setWebChromeClient(new WebChromeClient());
    	FileInputStream fis;
		try {
			fis = new FileInputStream("/mnt/sdcard/test_data");
			int l = fis.available();
	    	byte[] buf = new byte[l];
	    	fis.read(buf, 0, l);
	    	String dataStr = new String(buf);
	    	Log.d("dingjun","dataStr = " + dataStr);
	    	webview.loadDataWithBaseURL("",dataStr, "text/html","UTF-8","");
	    	TextView testtext = (TextView)findViewById(R.id.testtext);
	    	testtext.setText("this is a bbk toast");
	    	fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }

	@Override
	public void onClick(View v) 
	{
		switch(v.getId())
		{
		case R.id.ota_send_broadcast:
		{
			otaSendBroadCast();
			break;
		}
		case R.id.camera_distance_send_broadcast:
		{
			sendCameraDistanceBC();
			break;
		}
		}
	}
	
	public void sendCameraDistanceBC()
	{
		Intent intent = new Intent("com.eebbk.cameradistance.broadcast.control");
		if(mSync == 0)
		{
			intent.putExtra("open", true);
			mSync = 1;
			mCameraDistanceBtn.setText("关闭距离保护");
		}
		else
		{
			mSync = 0;
			intent.putExtra("open", false);
			mCameraDistanceBtn.setText("打开距离保护");
		}
		this.sendBroadcast(intent);
	}
	
	public void otaSendBroadCast()
	{
		String action = "pushio.bfc.android.MESSAGE_RECEIVED_ACTION";
		Intent intent = new Intent(action);
		intent.putExtra("content", "");
		intent.putExtra("tag", "11111");
		intent.putExtra("type", "22222");
		this.sendBroadcast(intent);
	}
}
