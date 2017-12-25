package com.example.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bbk.example.demo.R;

/**
 * Created by dingjun on 17-2-26.
 */
public class SAMLKeyguardActivity extends Activity {
    private final String mAssetPath = "/mnt/sdcard/theme_assets";
    private ListView mAssetsListView;
    private ThemeAssetsAdapter mThemeAssetsAdapter;
    private final String[] THEME_ARRAY = new String[]{"bxjg","xdd","zgdsbtl","jxzx","pkq","byl","xl"};
    private final String[] THEME_ARRAY_STRING = new String[]{"变形金刚","小叮当","这个大叔不太冷","机械之心","皮卡丘","不要脸","笑脸"};
    private FrameLayout mThemeLayout;
    private int mIndex = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.saml_keyguard_layout);
        init();
    }

    private void init() {
        mThemeLayout = (FrameLayout)findViewById(R.id.main_layout);
        mAssetsListView = (ListView)findViewById(R.id.theme_assets);
        mThemeAssetsAdapter = new ThemeAssetsAdapter();
        mAssetsListView.setAdapter(mThemeAssetsAdapter);
        mAssetsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(i != mIndex) {
                    String path = mAssetPath + "//" + THEME_ARRAY[i];
                    mIndex = i;
                } else {

                }
            }
        });
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public class ThemeAssetsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return THEME_ARRAY.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            TextView textView;
            if(view == null) {
                textView = new TextView(SAMLKeyguardActivity.this);
            } else {
                textView = (TextView)view;
                textView.setTextSize(50);
            }
            textView.setText(THEME_ARRAY_STRING[i]);
            return textView;
        }
    }
}
