package com.example.Activity;

import com.bbk.example.demo.R;
import com.example.utils.Debug;
import com.example.views.AutoListView;
import com.example.views.AutoListView.OnLoadListener;
import com.example.views.AutoListView.OnRefreshListener;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ReflashListViewActivity extends Activity
{
	private AutoListView mAutoListView;
	private TestAdapter mTestAdapter;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reflash_list_layout);
		Debug.d("222222222222222222");
		init();
	}

	private void init()
	{
		Log.d("1111111", "init");
		Debug.d("init11111111111111111");
		mAutoListView = (AutoListView)findViewById(R.id.auto_list);
		mAutoListView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				Toast.makeText(ReflashListViewActivity.this, "下拉刷新", Toast.LENGTH_SHORT).show();
				mAutoListView.onRefreshComplete();
			}
		});
		
		mAutoListView.setOnLoadListener(new OnLoadListener() {
			@Override
			public void onLoad() {
				Toast.makeText(ReflashListViewActivity.this, "加载更多", Toast.LENGTH_SHORT).show();
				mAutoListView.onLoadComplete();
				mAutoListView.noLoadDate();
			}
		});
		
		mTestAdapter = new TestAdapter();
		mAutoListView.setAdapter(mTestAdapter);
		mTestAdapter.notifyDataSetChanged();
		
		
		
	}
	
	public class TestAdapter extends BaseAdapter
	{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 50;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Debug.d("position = " + position);
			if(convertView == null)
			{
				TextView view = new TextView(ReflashListViewActivity.this);
				view.setWidth(500);
				view.setHeight(100);
				view.setGravity(Gravity.CENTER);
				view.setTextSize(50);
				view.setText("" + position);
				view.setBackgroundColor(Color.GREEN);
				return view;
			}
			else
			{
				TextView v = (TextView)convertView;
				v.setText(""+position);
				v.setBackgroundColor(Color.GREEN);
				v.setWidth(500);
				v.setHeight(100);
				v.setGravity(Gravity.CENTER);
				v.setTextSize(50);
				return v;
			}
		}
		
	}
}
