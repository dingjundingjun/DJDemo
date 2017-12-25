package com.example.Activity;

import android.app.Activity;
import android.content.res.AssetManager;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.Button;
import android.widget.FrameLayout;

import com.bbk.example.demo.R;
import com.example.views.TestKeyguardView;

/**
 * Created by dingjun on 16-9-10.
 */
public class ChangeFontActivity extends Activity {
    private Button mBtnFont1;
    private Button mBtnFont2;
    private Button mBtnFont3;
    private Button mBtnFont4;
    private Button mBtnFont5;
    private FrameLayout mMainLayout;
    private final String[] fontsArray = {"/data/data/com.example.demo/files/font1.ttf","/data/data/com.example.demo/files/font2.ttf","/data/data/com.example.demo/files/font3.ttf","/data/data/com.example.demo/files/font4.ttf","/data/data/com.example.demo/files/font5.ttf"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //取消状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fonts);
        init();
    }

    private void init() {
        mMainLayout = (FrameLayout)findViewById(R.id.main_layout);
        TestKeyguardView keyguardView = new TestKeyguardView(this);
        keyguardView.init();
        mMainLayout.addView(keyguardView);

        /*AssetManager assetManager = this.getAssets();
        Typeface.createFromAsset(assetManager,"font1.ttf");
        Typeface typeface1 = Typeface.createFromAsset(assetManager,"font1.ttf");
        mBtnFont1 = (Button)findViewById(R.id.btn_change_font_1);
        mBtnFont1.setTypeface(typeface1);
        mBtnFont1.setOnClickListener(this);

        Typeface typeface2 = Typeface.createFromAsset(assetManager,"font2.ttf");
        mBtnFont2 = (Button)findViewById(R.id.btn_change_font_2);
        mBtnFont2.setOnClickListener(this);
        mBtnFont2.setTypeface(typeface2);

        Typeface typeface3 = Typeface.createFromAsset(assetManager,"font3.ttf");
        mBtnFont3 = (Button)findViewById(R.id.btn_change_font_3);
        mBtnFont3.setOnClickListener(this);
        mBtnFont3.setTypeface(typeface3);

        Typeface typeface4 = Typeface.createFromAsset(assetManager,"font4.ttf");
        mBtnFont4 = (Button)findViewById(R.id.btn_change_font_4);
        mBtnFont4.setOnClickListener(this);
        mBtnFont4.setTypeface(typeface4);

        Typeface typeface5 = Typeface.createFromAsset(assetManager,"font5.ttf");
        mBtnFont5 = (Button)findViewById(R.id.btn_change_font_5);
        mBtnFont5.setOnClickListener(this);
        mBtnFont5.setTypeface(typeface5);

        System.currentTimeMillis();*/
    }

    /*@Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.btn_change_font_1: {
                break;
            }
            case R.id.btn_change_font_2: {
                break;
            }
            case R.id.btn_change_font_3: {
                break;
            }
            case R.id.btn_change_font_4: {
                break;
            }
            case R.id.btn_change_font_5: {
                break;
            }
        }
    }
    */
}
