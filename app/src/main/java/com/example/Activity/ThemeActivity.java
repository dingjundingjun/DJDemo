package com.example.Activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bbk.example.demo.R;
import com.example.utils.BitmapUtils;
import com.example.utils.Debug;
import com.example.utils.IconPackHelper;
import com.example.utils.ThemeControl;
import com.ibimuyu.lockscreen.sdk.wrapper.LockscreenWrapper;
import com.qq.e.v2.net.GDTADNetRequest;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dingjun on 16-10-8.
 */
public class ThemeActivity extends Activity implements View.OnClickListener{
    private final String TEST_PIC = "/mnt/sdcard/themes/test.jpg";
    private final String TEST_RINGTONE = "/mnt/sdcard/themes/test_ringtone.mp3";
    private final String TEST_FONT = "/mnt/sdcard/themes/font.ttf";
    private ImageView mBtnUpdateZip;
    private Button mBtnUpdateWallpaper;
    private Button mBtnUpdateKeyguardWallpaper;
    private ThemeControl mThemeControl;
    private ListView mIconList;
    private IconAdapter mIconAdapter;
    LockscreenWrapper mWapper;
    private RelativeLayout mKeyguardThemeView;
    View view;
    public static final String BROADCAST_NOTIFI_LAUNCHER_UPDATE_ICON = "com.eebbk.studyos.themes.action.UPDATE_LAUNCHER_ICONS";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_themes);
        init();
    }

    private void init() {
//        setFilePermission("/data/data/")
        mThemeControl = new ThemeControl(this);
        mBtnUpdateZip = (ImageView)findViewById(R.id.update_zip);
        mBtnUpdateWallpaper = (Button)findViewById(R.id.update_wallpaper);
        mBtnUpdateKeyguardWallpaper = (Button)findViewById(R.id.update_keyguard_wallpaper);
        mKeyguardThemeView = (RelativeLayout) findViewById(R.id.keyguard_theme_view);
        findViewById(R.id.update_ringtone_alarm).setOnClickListener(this);
        findViewById(R.id.update_ringtone_notification).setOnClickListener(this);
        findViewById(R.id.update_ringtone_telephone).setOnClickListener(this);
        findViewById(R.id.update_font).setOnClickListener(this);
        findViewById(R.id.reset_font).setOnClickListener(this);
        findViewById(R.id.test_icon_xml_parse).setOnClickListener(this);
        findViewById(R.id.test_read_icon).setOnClickListener(this);
        findViewById(R.id.update_launcher_icon).setOnClickListener(this);
        findViewById(R.id.zhangku_test).setOnClickListener(this);
        findViewById(R.id.reset_theme).setOnClickListener(this);
        mIconList = (ListView)findViewById(R.id.icon_list);
        mBtnUpdateZip.setOnClickListener(this);
        mBtnUpdateWallpaper.setOnClickListener(this);
        mBtnUpdateKeyguardWallpaper.setOnClickListener(this);
        mIconAdapter = new IconAdapter();
        mIconList.setAdapter(mIconAdapter);
        mThemeControl.bindService();
        mThemeControl.setOnThemeCallback(new ThemeControl.OnThemeCallback() {
            @Override
            public void onResult(int ret) {
                switch(ret) {
                    case ThemeControl.THEME_SET_WALLPAPER_SUCCESS: {
                        Toast.makeText(ThemeActivity.this, "set Wallpaper success!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case ThemeControl.THEME_SET_KEYGUARD_WALLPAPER_FAILED: {
                        Toast.makeText(ThemeActivity.this, "set Wallpaper failed!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case ThemeControl.THEME_SET_KEYGUARD_WALLPAPER_SUCCESS: {
                        Toast.makeText(ThemeActivity.this, "set keyguard Wallpaper success!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case ThemeControl.THEME_SET_WALLPAPER_FAILED: {
                        Toast.makeText(ThemeActivity.this, "set keyguard Wallpaper failed!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case ThemeControl.THEME_SET_RINGTONE_SUCCESS: {
                        Toast.makeText(ThemeActivity.this, "set Ringtone success!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case ThemeControl.THEME_SET_RINGTONE_FAILED: {
                        Toast.makeText(ThemeActivity.this, "set Ringtone failed!", Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
            }
        });

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(BROADCAST_NOTIFI_LAUNCHER_UPDATE_ICON);
        this.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                clearCache();
            }
        },intentFilter);
    }

    private void clearCache() {
        try{
            PackageManager pm = ThemeActivity.this.getApplication().getPackageManager();
            Method clearCache =  PackageManager.class.getMethod("clearCache");
            clearCache.invoke(pm);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()) {
            case R.id.update_zip: {
                PackageManager pm = this.getPackageManager();
                try {
                    ApplicationInfo info = pm.getApplicationInfo("com.android.settings", 0);
                    mBtnUpdateZip.setBackground(info.loadIcon(pm));
                } catch(Exception e) {

                }
//                mThemeControl.updateZip("test path");
                break;
            }
            case R.id.update_wallpaper: {
                mThemeControl.updateWallpaper(TEST_PIC);
                break;
            }
            case R.id.update_keyguard_wallpaper: {
                mThemeControl.updateKeyguardWallpaper(TEST_PIC);
                break;
            }
            case R.id.update_ringtone_notification: {
                mThemeControl.updateRingtone(TEST_RINGTONE,RingtoneManager.TYPE_NOTIFICATION);
                break;
            }
            case R.id.update_ringtone_alarm: {
//                mThemeControl.updateRingtone(TEST_RINGTONE,RingtoneManager.TYPE_ALARM);
                Debug.d("onClick11");
//                this.getApplication().getPackageManager().clearPackagePreferredActivities("com.eebbk.studyos.themes.clearcache");
                PackageManager pm = this.getPackageManager();
                try {
                    //com.tencent.mobileqq
                    //com.android.settings
                    ApplicationInfo info = pm.getApplicationInfo("com.android.settings", 0);
                    mBtnUpdateZip.setBackground(info.loadIcon(pm));
//                    Drawable drawable = pm.getActivityIcon(new Intent("com.dingjun.demo.ThemeActivity"));
//                    mBtnUpdateZip.setBackground(drawable);
                } catch(Exception e) {

                }
                break;
            }
            case R.id.update_ringtone_telephone: {
                mThemeControl.updateRingtone(TEST_RINGTONE,RingtoneManager.TYPE_RINGTONE);
                break;
            }
            case R.id.update_font: {
                mThemeControl.updateFont(TEST_FONT);
                break;
            }
            case R.id.reset_font: {
                mThemeControl.resetFont();
                break;
            }
            case R.id.test_icon_xml_parse: {
//                IconPackHelper iconPackHelper = new IconPackHelper();
//                Map<String,String> iconMap = iconPackHelper.getIconResMapFromXml(null,null);
//                Debug.d("iconMap = " + iconMap);
                PackageManager pm = this.getPackageManager();
                try {
                    ApplicationInfo info = pm.getApplicationInfo("com.android.settings", 0);
//                    mBtnUpdateZip.s
                } catch(Exception e) {

                }
                break;
            }
            case R.id.test_read_icon: {
                readIconZip();
                break;
            }
            case R.id.update_launcher_icon: {
//                updateLauncherIcons("/mnt/sdcard/icons");
                Drawable icon = this.getResources().getDrawable(R.drawable.icon);
                Bitmap iconBg = BitmapFactory.decodeResource(this.getResources(),R.drawable.icon_mask_bg);
                Bitmap iconFg = BitmapFactory.decodeResource(this.getResources(),R.drawable.icon_mask_fg);
                Bitmap iconMask = BitmapFactory.decodeResource(this.getResources(),R.drawable.icon_mask);
//                Bitmap iconWithMask = BitmapUtils.createIconBitmap(icon,this,iconFg,iconBg,iconMask);
//                Debug.d("iconWithMask = " + iconWithMask.getHeight() + " " + iconWithMask.getWidth());
//                mBtnUpdateZip.setBackground(new BitmapDrawable(iconWithMask));
                break;
            }
            case R.id.zhangku_test: {
                testZhangku();
                break;
            }
            case R.id.reset_theme: {
                resetTheme();
                break;
            }
        }
    }

    private void resetTheme() {
        mThemeControl.resetTheme();
    }

    public View testZhangku() {
//        mWapper = new LockscreenWrapper(getApplicationContext());
//        if (!mWapper.create()) {
//            return null;
//        }
//        try {
//            FileInputStream in = new FileInputStream(new File("/mnt/sdcard/keyguard_theme"));
//            view = mWapper.load("", in, true, true);
////            view = mWapper.load(true,true);
//            if(view == null) {
//                //do something for load failed!
//                return null;
//            }
//            mKeyguardThemeView.setVisibility(View.VISIBLE);
//            mKeyguardThemeView.removeAllViews();
//            mKeyguardThemeView.addView(view);
//        }catch(Exception e) {
//            e.printStackTrace();
//        }
//        mWapper.setUnlockRunnable(new Runnable() {
//            @Override
//            public void run() {
//                Debug.d("do something after unlocked v show = " + view.isShown());
////                mKeyguardThemeView.setVisibility(View.GONE);
//            }
//        });
        mThemeControl.updateThemeZip("/mnt/sdcard/theme_zip_test");
        return view;
//        mThemeControl.updateKeyguardFromZK("/mnt/sdcard/themes/keyguard");
//        return null;
    }

    private void updateLauncherIcons(String path) {
        mThemeControl.updateLauncherIcons(path);
    }

    @Override
    protected void onDestroy() {
        mThemeControl.unBindService();
        if (mWapper != null) {
            mWapper.destory();
            mWapper = null;
        }
        super.onDestroy();
    }

    private void readIconZip() {

//        Debug.d("readIconZip = ");
//        List<Drawable> drawableList = new ArrayList<Drawable>();
//        PackageManager pm = this.getPackageManager();
//        try {
//            List<ApplicationInfo> infoList = pm.getInstalledApplications(0);
//            for(int i = 0; i < infoList.size(); i++) {
//                drawableList.add(infoList.get(i).loadIcon(pm));
//            }
//        } catch(Exception e) {
//            e.printStackTrace();
//        }
//        mIconAdapter.setDrawableList(drawableList);
//        mIconAdapter.notifyDataSetChanged();
        mThemeControl.deleteIcons();
    }

    public static boolean setFilePermission(String path) throws Exception {
        Process p;
        int status;
        p = Runtime.getRuntime().exec("chmod 777 " + path);
        status = p.waitFor();
        if (status == 0) {
            return true;
        }
        return false;
    }


    class IconAdapter extends BaseAdapter {
        private List<Drawable> mDrawableList;
        private void setDrawableList(List<Drawable> list) {
            mDrawableList = list;
        }
        @Override
        public int getCount() {
            if(mDrawableList != null) {
                return mDrawableList.size();
            }
            return 0;
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
            ImageView image = new ImageView(ThemeActivity.this);
            Debug.d("111111111111 mDrawableList = " + mDrawableList);
            if(mDrawableList != null && mDrawableList.get(i) != null) {
                Debug.d("111111111111 mDrawableList.get(i) = " + mDrawableList.get(i));
                image.setBackgroundDrawable(mDrawableList.get(i));
            }
            return image;
        }
    }
}
