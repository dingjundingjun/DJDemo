package com.example.utils;

import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.KeyEvent;

import org.apache.commons.codec.binary.StringUtils;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Created by dingjun on 16-11-14.
 */
public class IconPackHelper {
    private static final String DINGJUN_TAG = "DINGJUN_THEME_DEBUG";
    public static final String THEME_APK_DIR = "/data/system/users/0/themes/theme.apk";
    private static final String THEME_ICON_PATH = "/data/system/users/0/themes/icons/resources.apk";
//    public static final String ICONS_PATH = "/data/system/users/0/themes/icons/icons";
    public static final String ICONS_PATH = "/mnt/sdcard/themes/icons/icons";
    private static final String THEME_ICON_CONFIG_PATH = "/data/system/users/0/themes/icons/allApps.xml";
    private static final String THEME_ICON_DEFAULT_CONFIG_PATH = "/data/system/users/0/themes/default_icon.xml";

    //以下是测试方法，不属于主题调用
    public Map<String, String> getIconResMapFromXml(Resources res, String packageName) {
        XmlPullParser parser = null;
        InputStream inputStream = null;
        Map<String, String> iconPackResources = new HashMap<String, String>();
        try {
            File file = new File("/mnt/sdcard/icons/allApps.xml");
            if(!file.exists()) {
                return null;
            }
            inputStream = new FileInputStream(file);
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            parser = factory.newPullParser();
            parser.setInput(inputStream, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (parser != null) {
            try {
                loadResourcesFromXmlParser(parser, iconPackResources);
                Debug.d("iconPackResources = " + iconPackResources);
                return iconPackResources;
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // Cleanup resources
                if (parser instanceof XmlResourceParser) {
                    ((XmlResourceParser) parser).close();
                } else if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        return iconPackResources;
    }

    private void loadResourcesFromXmlParser(XmlPullParser parser,
                                            Map<String, String> iconPackResources)
            throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        do {
            if (eventType != XmlPullParser.START_TAG) {
                continue;
            }

            if (!parser.getName().equalsIgnoreCase("icon")) {
                continue;
            }

            String iconName = parser.getAttributeValue(null, "name");
            String packageName = parser.getAttributeValue(null, "package");

            // Validate component/drawable exist
            if (TextUtils.isEmpty(iconName) || TextUtils.isEmpty(packageName)) {
                continue;
            }
            iconPackResources.put(iconName, packageName);
        } while ((eventType = parser.next()) != XmlPullParser.END_DOCUMENT);
    }

    public static Drawable getIconByPackageName(String name) {
        try {
            long lastTime = System.currentTimeMillis();
            InputStream stream = getZipFile(ICONS_PATH, name);
//            InputStream stream = getZipFile(ICONS_PATH, "com.android.settings.png");
            if(stream != null) {
                Bitmap bitmap = BitmapFactory.decodeStream(stream);
                BitmapDrawable bd = new BitmapDrawable(bitmap);
                Drawable d = (Drawable) bd;
                long costTime = System.currentTimeMillis() - lastTime;
                Debug.d("get icon use time = " + costTime);
                return d;
            }
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static InputStream getZipFile(String zipFileString, String fileString)throws Exception {
        ZipFile zipFile = new ZipFile(zipFileString);
        ZipEntry zipEntry = zipFile.getEntry(fileString);
//        fileString = fileString.substring(1);
        Debug.d("fileString = " + fileString + " zipFileString = " + zipFileString);
        if(zipEntry != null) {
            Debug.d("fileString111 = " + fileString);
            return zipFile.getInputStream(zipEntry);
        }
        return null;
    }

    public static List<File> getFileList(String zipFileString, boolean bContainFolder, boolean bContainFile)throws Exception {
        List<File> fileList = new ArrayList<File>();
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zipFileString));
        ZipEntry zipEntry;
        String szName = "";
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            Debug.d("szName = " + szName);
            if (zipEntry.isDirectory()) {
                // get the folder name of the widget
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(szName);
                if (bContainFolder) {
                    fileList.add(folder);
                }

            } else {
                File file = new File(szName);
                if (bContainFile) {
                    fileList.add(file);
                }
            }
        }
        inZip.close();
        return fileList;
    }

    /**
     * 获得压缩文件内文件列表
     *
     * @param zipFile 压缩文件
     * @return 压缩文件内文件名称
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException 当解压缩过程出错时抛出
     */
    public static ArrayList<String> getEntriesNames(File zipFile) throws ZipException, IOException {
        ArrayList<String> entryNames = new ArrayList<String>();
        Enumeration<?> entries = getEntriesEnumeration(zipFile);
        while (entries.hasMoreElements()) {
            ZipEntry entry = ((ZipEntry)entries.nextElement());
            entryNames.add(new String(getEntryName(entry).getBytes("GB2312"), "8859_1"));
        }
        return entryNames;
    }

    /**
     * 获得压缩文件内压缩文件对象以取得其属性
     *
     * @param zipFile 压缩文件
     * @return 返回一个压缩文件列表
     * @throws ZipException 压缩文件格式有误时抛出
     * @throws IOException IO操作有误时抛出
     */
    public static Enumeration<?> getEntriesEnumeration(File zipFile) throws ZipException,
            IOException {
        ZipFile zf = new ZipFile(zipFile);
        return zf.entries();
    }

    /**
     * 取得压缩文件对象的名称
     *
     * @param entry 压缩文件对象
     * @return 压缩文件对象的名称
     * @throws UnsupportedEncodingException
     */
    public static String getEntryName(ZipEntry entry) throws UnsupportedEncodingException {
        return new String(entry.getName().getBytes("GB2312"), "8859_1");
    }

    /**
     * 压缩文件,文件夹
     * @param srcFileString 要压缩的文件/文件夹名字
     * @param zipFileString 指定压缩的目的和名字
     * @throws Exception
     */
    public static void ZipFolder(String srcFileString, String zipFileString)throws Exception {
        android.util.Log.v("XZip", "ZipFolder(String, String)");

        //创建Zip包
        java.util.zip.ZipOutputStream outZip = new java.util.zip.ZipOutputStream(new java.io.FileOutputStream(zipFileString));

        //打开要输出的文件
        java.io.File file = new java.io.File(srcFileString);

        //压缩
        ZipFiles(file.getParent()+java.io.File.separator, file.getName(), outZip);

        //完成,关闭
        outZip.finish();
        outZip.close();

    }//end of func

    /**
     * 压缩文件
     * @param folderString
     * @param fileString
     * @param zipOutputSteam
     * @throws Exception
     */
    private static void ZipFiles(String folderString, String fileString, java.util.zip.ZipOutputStream zipOutputSteam)throws Exception{
        android.util.Log.v("XZip", "ZipFiles(String, String, ZipOutputStream)");

        if(zipOutputSteam == null)
            return;

        java.io.File file = new java.io.File(folderString+fileString);

        //判断是不是文件
        if (file.isFile()) {

            java.util.zip.ZipEntry zipEntry =  new java.util.zip.ZipEntry(fileString);
            java.io.FileInputStream inputStream = new java.io.FileInputStream(file);
            zipOutputSteam.putNextEntry(zipEntry);

            int len;
            byte[] buffer = new byte[4096];

            while((len=inputStream.read(buffer)) != -1)
            {
                zipOutputSteam.write(buffer, 0, len);
            }

            zipOutputSteam.closeEntry();
        }
        else {

            //文件夹的方式,获取文件夹下的子文件
            String fileList[] = file.list();

            //如果没有子文件, 则添加进去即可
            if (fileList.length <= 0) {
                java.util.zip.ZipEntry zipEntry =  new java.util.zip.ZipEntry(fileString+java.io.File.separator);
                zipOutputSteam.putNextEntry(zipEntry);
                zipOutputSteam.closeEntry();
            }

            //如果有子文件, 遍历子文件
            for (int i = 0; i < fileList.length; i++) {
                ZipFiles(folderString, fileString+java.io.File.separator+fileList[i], zipOutputSteam);
            }//end of for

        }//end of if

    }//end of func

    public void finalize() throws Throwable {

    }
}
