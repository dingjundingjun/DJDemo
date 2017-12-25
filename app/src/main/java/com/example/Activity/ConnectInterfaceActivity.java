package com.example.Activity;

//import org.apache.http.Header;
import com.bbk.example.demo.R;
import com.example.utils.Debug;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.RequestHandle;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ConnectInterfaceActivity extends Activity implements OnClickListener
{
	public AsyncHttpClient mAsyncHttpClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		System.out.println();
		mAsyncHttpClient = new AsyncHttpClient();
		/*
		 http://api.juheapi.com/japi/toh?key=562d9e6978df961aa77d8506edb3bf13&v=1.0&month=11&day=1
		 * */
		setContentView(R.layout.connect_interface_layout);
		this.findViewById(R.id.interface_weather).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
//		case R.id.interface_weather:
//		{
//			//http://v.juhe.cn/weather/index?format=2&cityname=%e5%ae%81%e6%b3%a2&key=a7a65a7a60e33c1a9fe8928143cc3047
//			String url = "http://v.juhe.cn/weather/index?format=2&cityname=%e5%ae%81%e6%b3%a2&key=a7a65a7a60e33c1a9fe8928143cc3047";
//			RequestHandle handle = mAsyncHttpClient.get(url, new BaseJsonHttpResponseHandler()
//			{
//				@Override
//				public void onFailure(int arg0, Header[] arg1, Throwable arg2,
//						String arg3, Object arg4) {
//					// TODO Auto-generated method stub
//					
//				}
//
//				@Override
//				public void onSuccess(int arg0, Header[] arg1, String arg2,
//						Object arg3) {
//					Debug.d("arg2 = " + arg2 );
//				}
//
//				@Override
//				protected Object parseResponse(String arg0, boolean arg1)
//						throws Throwable {
//					// TODO Auto-generated method stub
//					return null;
//				}});
//			break;
//		}
		}
	}
	
}
