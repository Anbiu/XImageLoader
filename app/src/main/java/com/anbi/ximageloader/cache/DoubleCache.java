package com.anbi.ximageloader.cache;

import android.app.Activity;
import android.graphics.Bitmap;

/**
 * @description: 内存+SD卡缓存
 * @author: anbi
 * @time: 2020-03-18 16:19
 */
public class DoubleCache implements ImageCache {
    //内存缓存
    private MemoryCache mMemoryCache;
    //SD卡缓存
    private DiskCache mDiskCache;

    public DoubleCache(Activity mContext) {
        this.mMemoryCache = new MemoryCache();
        this.mDiskCache = new DiskCache(mContext);
    }

    /**
     * 先从内存取图片 没有再从SD卡取
     * @param url 图片地址
     * @return
     */
    @Override
    public Bitmap get(String url) {
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap == null) {
            bitmap = mDiskCache.get(url);
        }
        return bitmap;
    }

    /**
     * 将图片缓存到内存和SD卡中
     * @param url 图片地址
     * @param bitmap 图片
     */
    @Override
    public void put(String url, Bitmap bitmap) {
        mMemoryCache.put(url,bitmap);
        mDiskCache.put(url, bitmap);
    }
}
