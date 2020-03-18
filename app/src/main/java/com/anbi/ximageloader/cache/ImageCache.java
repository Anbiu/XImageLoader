package com.anbi.ximageloader.cache;

import android.graphics.Bitmap;

/**
 * @description: 图片缓存接口
 * @author: anbi
 * @time: 2020-03-18 15:50
 */
public interface ImageCache {
    public Bitmap get(String url);

    public void put(String url, Bitmap bitmap);
}
