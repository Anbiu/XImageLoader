package com.anbi.ximageloader.cache;

import android.graphics.Bitmap;
import android.util.LruCache;


/**
 * @description: 在内存中缓存
 * @author: anbi
 * @time: 2020-03-18 15:50
 */
public class MemoryCache implements ImageCache{

    //图片缓存
    private LruCache<String, Bitmap> mImageCache;

    /**
     * 构造
     */
    public MemoryCache() {
        initMemoryCache();
    }

    /**
     * 初始化
     */
    private void initMemoryCache() {
        //计算可使用的最大内存
        int maxCache = (int) Runtime.getRuntime().maxMemory();
        //使用1/8作为图片缓存
        int cacheSize = maxCache / 8;

        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //单个元素所占内存
                return value.getHeight() * value.getHeight() / 1024;
            }
        };
    }

    public void put(String url, Bitmap bitmap) {
        mImageCache.put(url, bitmap);
    }

    public Bitmap get(String url) {
        System.out.println("读取缓存");
       return mImageCache.get(url);
    }
}
