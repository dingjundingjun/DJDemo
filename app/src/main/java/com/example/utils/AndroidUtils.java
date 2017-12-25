package com.example.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.DisplayMetrics;

public class AndroidUtils {
	/**
	 * 杀掉指定包名的进程
	 * @param context
	 * @param pkgname
	 */
	public static void killProcess(Context context, String pkgname)
	{
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		Method forceStopPackage;
		try {
			forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
			forceStopPackage.setAccessible(true);  
			forceStopPackage.invoke(am, pkgname);  
			return;
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}  
	}
	
	/**
	 * 打印设备的dpi width height
	 * @param context
	 */
	public static void showDeviceDesityInfo(Context context)
	{
        DisplayMetrics dm = context.getApplicationContext().getResources().getDisplayMetrics();
        float density = dm.density;
        int dpi = dm.densityDpi;
        int height = dm.heightPixels;
        int width = dm.widthPixels;
		Debug.d(" devices info:\n density = " + density + "\n dpi = " + dpi
				+ "\n height = " + height + "\n width = " + width);
	}
	
	/**
	 * 判断应用包名是否存在
	 * @param context
	 * @param packageName
	 * @return
	 */
	public boolean checkApplication(Context context,String packageName) {
  	  if (packageName == null || "".equals(packageName)){
  	      return false;
  	  }
  	  try {
  	      ApplicationInfo info = context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
  	      if(info != null)
  	      {
  	    	  return true;
  	      }
  	      return false;
  	  } catch (NameNotFoundException e) {
  	      return false;
  	  }
  	}
}
