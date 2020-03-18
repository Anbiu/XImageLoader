package com.anbi.ximageloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.anbi.ximageloader.cache.DiskCache;
import com.anbi.ximageloader.cache.DoubleCache;

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    XImageLoader mXImageLoader;
    String[] strings = new String[]{
            "http://dpic.tiankong.com/02/d6/QJ7100013424.jpg",
            "http://dpic.tiankong.com/t2/d6/QJ7100013431.jpg",
            "http://dpic.tiankong.com/6w/d6/QJ7100013443.jpg",
            "http://dpic.tiankong.com/zw/d6/QJ7100013453.jpg",
            "http://dpic.tiankong.com/9w/d6/QJ7100013460.jpg",
            "http://dpic.tiankong.com/xw/d6/QJ7100013470.jpg",
            "http://dpic.tiankong.com/ij/d6/QJ7100013482.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.mImageView);
        mXImageLoader = new XImageLoader();
        mXImageLoader.setmImageCache(new DoubleCache(this));
        mXImageLoader.displayImage(strings[getUrl()], mImageView);
    }

    private int getUrl() {
        int i = 0;
        i = (int) Math.abs(Math.random() * 10 - 3);
        System.out.println("i =" + i);
        return i;
    }

    public void refresh(View view) {
        mXImageLoader.displayImage(strings[getUrl()], mImageView);
    }
}
