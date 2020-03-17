package com.anbi.ximageloader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.util.LruCache;
import android.widget.ImageView;

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
   private LruCache<String, Bitmap> mImageCache;
    //线程池
    private ExecutorService mExecutorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    //UI Handler
   private Handler mHandler = new Handler(Looper.getMainLooper());

    public XImageLoader() {
        initXImageLoader();
    }

    /**
     * 初始化
     */
    private void initXImageLoader() {
        //计算可使用的最大内存
        int maxMermory = (int) (Runtime.getRuntime().maxMemory() / 1024);
        //使用1/8作为图片缓存
        int cacheSize = maxMermory / 8;
        mImageCache = new LruCache<String, Bitmap>(cacheSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //计算单个元素所用内存
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    /**
     * 加载图片
     * @param url 图片地址
     * @param imageView 图片控件
     */
    public void displayImage(final String url, final ImageView imageView) {
        imageView.setTag(url);
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = mImageCache.get(url);
                //缓存中没有图片 从网络中下载
                if (bitmap == null) {
                    bitmap = downloadImage(url);
                }
                //没有下载到图片
                if (bitmap == null) {
                    return;
                }
                if (imageView.getTag().equals(url)) {
                    //将图片显示在控件中
                    updateImageView(imageView,bitmap);
                }
                //将图片塞进缓存
                mImageCache.put(url, bitmap);
            }
        });
    }

    /**
     * 更新图片至容器中
     * @param imageView 图片容器
     * @param bitmap 图片
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
