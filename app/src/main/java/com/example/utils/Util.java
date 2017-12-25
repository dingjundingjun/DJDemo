package com.example.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import com.bbk.example.demo.R;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.view.Menu;

public class Util
{
	public static byte[] charToByte(char c) {   
        byte[] b = new byte[2];   
        b[0] = (byte) ((c & 0xFF00) >> 8);   
        b[1] = (byte) (c & 0xFF);   
        return b;   
    }  
	
	public static String decodeUnicode(final String dataStr) {   
        int start = 0;   
          int end = 0;   
         final StringBuffer buffer = new StringBuffer();   
          while (start > -1) {   
             end = dataStr.indexOf("\\u", start + 2);   
              String charStr = "";   
              if (end == -1) {   
                  charStr = dataStr.substring(start + 2, dataStr.length());   
             } else {   
                 charStr = dataStr.substring(start + 2, end);   
              }   
              char letter = (char) Integer.parseInt(charStr, 16); // 16进制parse整形字符串。   
            buffer.append(new Character(letter).toString());   
            start = end;   
          }   
          return buffer.toString();   
      }  
	
	public static String getAllUnicode(int unic)
	{
		final StringBuffer buffer = new StringBuffer();  
		char letter = 0;
		for(int i = 0; i < 65535;i++)
		{
			letter = (char) unic;
			String t = new Character(letter).toString();
			buffer.append(t);
		}
		return buffer.toString();
	}
	
	public static String getAllUnicode(int start,int count)
	{
		final StringBuffer buffer = new StringBuffer();  
		char letter = 0;
		int end = start + count;
		for(int i = start; i < end;i++)
		{
			letter = (char) i;
			String t = new Character(letter).toString() + "(" + i +")";
			buffer.append(t);
		}
		return buffer.toString();
	}
	
	public static byte[] readFile(String path)
	{
		byte[] rData = null;
		try {
			FileInputStream fis = new FileInputStream(path);
			int length = fis.available();
			rData = new byte[length];
			fis.read(rData, 0, length);
			fis.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rData;
	}
	
	 public static Map<String, Integer> buildEffectMap(Context context) {
	        Map<String, Integer> effectMap = new LinkedHashMap<>();
	        int i = 0;
	        String[] effects = context.getResources().getStringArray(R.array.jazzy_effects);
	        for (String effect : effects) {
	            effectMap.put(effect, i++);
	        }
	        return effectMap;
	    }

	    public static void populateEffectMenu(Menu menu, List<String> effectNames, Context context) {
	        Collections.sort(effectNames);
	        effectNames.remove(context.getString(R.string.standard));
	        effectNames.add(0, context.getString(R.string.standard));
	        for (String effectName : effectNames) {
	            menu.add(effectName);
	        }
	    }

	    public static int getBackgroundColorRes(int position, int itemLayoutRes) {
	        if (itemLayoutRes == R.layout.item) {
	            return position % 2 == 0 ? R.color.even : R.color.odd;
	        } else {
	            return (position % 4 == 0 || position % 4 == 3) ? R.color.even : R.color.odd;
	        }
	    }
	    
	    public static String getCurrentTime() {
			return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
		}
	    
	    public static String getCurrentTime(String format) {
			Date date = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
			String currentTime = sdf.format(date);
			return currentTime;
		}
	    
	    public static void killProcess(Context context, String pkgname)
		{
			ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
			
//			am.killBackgroundProcesses(pkgname); 
			
			Method forceStopPackage;
			try {
				forceStopPackage = am.getClass().getDeclaredMethod("forceStopPackage", String.class);
				forceStopPackage.setAccessible(true);  
				forceStopPackage.invoke(am, pkgname);  
				return;
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}

	}
