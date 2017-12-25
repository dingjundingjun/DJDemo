package com.example.Activity;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.bbk.example.demo.R;
import com.example.views.TitleScroolView;
import com.example.views.TitleScroolView.OnTitleClickListener;

public class TitleScroolActivity extends Activity
{
	private TitleScroolView mTitleScroolView;
	private List<String> mTestList = new ArrayList<String>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.title_scrool_activity_layout);
		//test data
		mTestList.clear();
		for(int i = 1 ;i < 6;i++)
		{
			mTestList.add("label_" + i);
		}
		mTitleScroolView = (TitleScroolView)findViewById(R.id.titlescrool);
        mTitleScroolView.setTitleList(mTestList);
        mTitleScroolView.init();
        mTitleScroolView.setOnTitleClickListener(new OnTitleClickListener() {
			
			@Override
			public void onClick(int id) {
				Toast.makeText(TitleScroolActivity.this, "点击了 " + mTestList.get(id), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
}
