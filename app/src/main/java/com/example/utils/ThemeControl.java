package com.example.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.eebbk.studyos.themes.IThemeAIDLCallback;
import com.eebbk.studyos.themes.IThemeAIDLInterface;

/**
 * Created by dingjun on 16-10-8.
 */
public class ThemeControl {
    private final String THEME_SERVICE_ACTION = "com.eebbk.studyos.themes.service";
    private Context mContext;
    private Intent mIntent;
    private OnThemeCallback mOnThemeCallback;
    private UIHandler mUIHandler = new UIHandler();
    private final int THEME_UI_HANDLER_RESULT = 0;
    public static final int THEME_SET_WALLPAPER_SUCCESS = 1;
    public static final int THEME_SET_WALLPAPER_FAILED = 2;
    public static final int THEME_SET_KEYGUARD_WALLPAPER_SUCCESS = 3;
    public static final int THEME_SET_KEYGUARD_WALLPAPER_FAILED = 4;
    public static final int THEME_SET_RINGTONE_SUCCESS = 5;
    public static final int THEME_SET_RINGTONE_FAILED = 6;
    public static final int THEME_UPDATE_ICONS_SUCCESS = 7;
    public static final int THEME_UPDATE_ICONS_FAILED = 8;
    public static final int THEME_UPDATE_ZIP_SUCCESS = 9;
    public static final int THEME_UPDATE_ZIP_FAILED = 10;
    public static final int THEME_RESET_SUCCESS = 11;
    public static final int THEME_RESET_FAILED = 12;
    public static final int THEME_SERVICE_CONNECTED = 100;
    public static final int THEME_SERVICE_DISCONNECTED = 101;
    public ThemeControl(Context context) {
        mContext = context;
    }

    /**
     *  this method use in app start
     */
    public void bindService() {
        mIntent = new Intent();
        mIntent.setPackage("com.eebbk.studyos.themes");
        mIntent.setAction(THEME_SERVICE_ACTION);
        mContext.bindService(mIntent,mConnection, Context.BIND_AUTO_CREATE);
    }

    public void unBindService() {
        if(mConnection != null) {
            mContext.unbindService(mConnection);
        }
    }

    public void updateThemeZip(String path) {
        if(mService != null) {
            try {
                mService.updateThemeZip(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetTheme() {
        if(mService != null) {
            try {
                mService.resetTheme();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * update a launcher wallpaper
     */
    public void updateWallpaper(String path) {
        if(mService != null) {
            try {
                mService.updateWallpaper(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * update a keyguard wallpaper
     */
    public void updateKeyguardWallpaper(String path) {
        if(mService != null) {
            try {
                mService.updateKeyguardWallpaper(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /*
    * update a Ringtone
    * RingtoneManager.TYPE_RINGTONE
    * RingtoneManager.TYPE_NOTIFICATION
    * RingtoneManager.TYPE_ALARM
     */
    public void updateRingtone(String path,int type) {
        if(mService != null) {
            try {
                mService.updateRingtone(path,type);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateFont(String path) {
        if(mService != null) {
            try {
                mService.updateFont(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void resetFont() {
        if(mService != null) {
            try {
                mService.resetFont();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateLauncherIcons(String path) {
        if(mService != null) {
            try {
                mService.updateIcons(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteIcons() {
        if(mService != null) {
            try {
                mService.deleteIcons();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateKeyguardFromZK(String path) {
        if(mService != null) {
            try {
                mService.updateKeyguardFromZK(path);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteKeyguard() {
        if(mService != null) {
            try {
                mService.deleteKeyguard();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    IThemeAIDLInterface mService;
    private ServiceConnection mConnection = new ServiceConnection(){
        public void onServiceConnected(ComponentName className,
                                       IBinder service){
            mService = IThemeAIDLInterface.Stub.asInterface(service);
            try {
                mService.registerCallback(mThemeCallback);
                if(mOnThemeCallback != null) {
                    mOnThemeCallback.onResult(THEME_SERVICE_CONNECTED);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        public void onServiceDisconnected(ComponentName className){
            try {
                mService.unregisterCallback(mThemeCallback);
                if(mOnThemeCallback != null) {
                    mOnThemeCallback.onResult(THEME_SERVICE_DISCONNECTED);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            mService = null;
        }
    };

    private IThemeAIDLCallback mThemeCallback = new IThemeAIDLCallback.Stub() {
        @Override
        public void onResult(int ret) throws RemoteException {
            Message msg = mUIHandler.obtainMessage();
            msg.what = THEME_UI_HANDLER_RESULT;
            msg.arg1 = ret;
            mUIHandler.sendMessage(msg);
        }
    };

    public void setOnThemeCallback(OnThemeCallback tc) {
        mOnThemeCallback = tc;
    }

    public interface OnThemeCallback {
        /*
         THEME_SET_WALLPAPER_SUCCESS = 1;
         THEME_SET_WALLPAPER_FAILED = 2;
         THEME_SET_KEYGUARD_WALLPAPER_SUCCESS = 3;
         THEME_SET_KEYGUARD_WALLPAPER_FAILED = 4;
         THEME_SET_RINGTONE_SUCCESS = 5;
         THEME_SET_RINGTONE_FAILED = 6;
         * */
        void onResult(int ret);
    }

    public class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch(msg.what) {
                case THEME_UI_HANDLER_RESULT: {
                    int ret = msg.arg1;
                    if(mOnThemeCallback != null) {
                        mOnThemeCallback.onResult(ret);
                    }
                }
            }
        }
    }
}
