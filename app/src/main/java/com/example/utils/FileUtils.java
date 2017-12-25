package com.example.utils;

import android.app.Activity;
import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class FileUtils 
{
	/**
	 * 读取一个文件
	 * @param 文件路径
	 * @return 读取的字节
	 */
	public static byte[] readFile(String path) {
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

	public boolean copy(Context context, String src, String dest) {
		try {
			File inputFile = new File(src);
			FileInputStream inputStream = new FileInputStream(inputFile);
			FileOutputStream outputStream = context.openFileOutput(dest, Activity.MODE_PRIVATE);
			byte b[] = new byte[(int) inputFile.length()];
			inputStream.read(b);
			outputStream.write(b);
			inputStream.close();
			outputStream.close();
		} catch(Exception e) {
			return false;
		}
		return true;
	}

}
