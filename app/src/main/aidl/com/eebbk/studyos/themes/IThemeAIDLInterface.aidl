// IThemeAIDLInterface.aidl
package com.eebbk.studyos.themes;
import com.eebbk.studyos.themes.IThemeAIDLCallback;
// Declare any non-default types here with import statements

interface IThemeAIDLInterface {
    void registerCallback(IThemeAIDLCallback cb);
    void unregisterCallback(IThemeAIDLCallback cb);
    /**
    * update a Zip file to Theme
    */
    void updateThemeZip(String path);
    /*
    * change the Keyguard Wallpaper
    */
    void updateKeyguardWallpaper(String path);
    /*
    * change the Wallpaper
    */
    void updateWallpaper(String path);
    /*
    * change the Ringtone
    */
    void updateRingtone(String path,int type);
    /*
    * change the fonts
    */
    void updateFont(String path);
    /*
    * reset the fonts
    */
    void resetFont();
    void updateIcons(String path);
    void deleteIcons();
    void updateKeyguardFromZK(String path);
    void deleteKeyguard();
    void resetTheme();
}
