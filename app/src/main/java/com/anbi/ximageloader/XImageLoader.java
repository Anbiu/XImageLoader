package com.anbi.ximageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

import com.anbi.ximageloader.cache.MemoryCache;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @description: 图片加载类
 * @author: anbi
 * @time: 2020-03-17 18:33
 */
public class XImageLoader {
    //图片缓存
    private MemoryCache mImageCache = new MemoryCache();
    //线程池
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //UI Handler
    private Handler mHandler = new Handler(Looper.getMainLooper());


    /**
     * 加载图片
     *
     * @param url       图片地址
     * @param imageView 图片控件
     */
    public void displayImage(final String url, final ImageView imageView) {
        Bitmap bitmap = mImageCache.get(url);
        if (bitmap != null) {
            System.out.println("读取缓存");
            imageView.setImageBitmap(bitmap);
            return;
        }
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                //缓存中没有图片 从网络中下载
                System.out.println("开始下载");
                Bitmap bitmap = downloadImage(url);
                System.out.println("下载结束");
                //没有下载到图片
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(url)) {
                    //将图片显示在控件中
                    updateImageView(imageView, bitmap);
                }
                //将图片塞进缓存
                mImageCache.put(url, bitmap);
            }
        });
    }

    /**
     * 更新图片至容器中
     *
     * @param imageView 图片容器
     * @param bitmap    图片
     */
    private void updateImageView(final ImageView imageView, final Bitmap bitmap) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                imageView.setImageBitmap(bitmap);
            }
        });
    }

    /**
     * 下载图片
     *
     * @param imageUrl 图片地址
     * @return
     */
    private Bitmap downloadImage(String imageUrl) {
        Bitmap bitmap = null;
        try {
            URL url = new URL(imageUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            bitmap = BitmapFactory.decodeStream(conn.getInputStream());
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bitmap;
    }
}
