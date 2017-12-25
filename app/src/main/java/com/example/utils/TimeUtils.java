package com.example.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeUtils 
{
	/**
	 * 获取当前时间
	 * @return 格式为:"yyyy-MM-dd  HH:mm:ss"
	 */
	public static String getCurrentTime() {
		return getCurrentTime("yyyy-MM-dd  HH:mm:ss");
	}
    
    /**
     * 获取当前时间
     * @param 格式
     * @return 返回当前时间字符串
     */
    public static String getCurrentTime(String format) {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.getDefault());
		String currentTime = sdf.format(date);
		return currentTime;
	}
}
