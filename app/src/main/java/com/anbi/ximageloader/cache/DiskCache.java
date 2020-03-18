package com.anbi.ximageloader.cache;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Environment;

import androidx.core.app.ActivityCompat;

import com.anbi.ximageloader.utils.PermissionUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @description: SD卡缓存
 * @author: anbi
 * @time: 2020-03-18 16:13
 */
public class DiskCache implements ImageCache {
    private static String cacheDir = Environment.getExternalStorageDirectory() + "/XImageLoader/cache/";


    public DiskCache(Activity mContext) {
        if (Build.VERSION.SDK_INT >= 23) {
            int REQUEST_CODE_CONTACT = 101;
            String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
            //验证是否许可权限
            for (String str : permissions) {
                if (mContext.checkSelfPermission(str) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    ActivityCompat.requestPermissions(mContext,permissions, REQUEST_CODE_CONTACT);
                }
            }
        }
        if (!isSdCardExist()) {
            System.out.println("SD卡不存在");
            return;
        }
        //创建文件夹
        File destDir = new File(cacheDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }
    public static boolean isSdCardExist() {
        return Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED);
    }
    public static void setCacheDir(String cacheDir) {
        DiskCache.cacheDir = cacheDir;
        //创建文件夹
        File destDir = new File(cacheDir);
        if (!destDir.exists()) {
            destDir.mkdirs();
        }
    }

    @Override
    public Bitmap get(String url) {
        System.out.println("读取SD卡");
        String fileName = url.substring(url.lastIndexOf('/'));
        return BitmapFactory.decodeFile(cacheDir + fileName);
    }

    //将图片存到本地
    @Override
    public void put(String url, Bitmap bitmap) {

        FileOutputStream fileOutputStream = null;
        try {
            String fileName = url.substring(url.lastIndexOf('/'));
            File file = new File(cacheDir,
                    fileName);
            fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream == null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
