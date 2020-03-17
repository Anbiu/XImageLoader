package com.anbi.ximageloader;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView mImageView;
    XImageLoader xImageLoader;
    String[] strings = new String[]{
            "http://dpic.tiankong.com/02/d6/QJ7100013424.jpg?x-oss-process=style/show_794s",
            "http://dpic.tiankong.com/t2/d6/QJ7100013431.jpg?x-oss-process=style/show_794s",
            "http://dpic.tiankong.com/6w/d6/QJ7100013443.jpg?x-oss-process=style/240h",
            "http://dpic.tiankong.com/zw/d6/QJ7100013453.jpg?x-oss-process=style/240h",
            "http://dpic.tiankong.com/9w/d6/QJ7100013460.jpg?x-oss-process=style/240h",
            "http://dpic.tiankong.com/xw/d6/QJ7100013470.jpg?x-oss-process=style/240h",
            "http://dpic.tiankong.com/ij/d6/QJ7100013482.jpg?x-oss-process=style/240h"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageView = findViewById(R.id.mImageView);
        xImageLoader = new XImageLoader();
        xImageLoader.displayImage(strings[getUrl()], mImageView);
    }

    private int getUrl() {
        int i = 0;
        i = (int) Math.abs(Math.random() * 10 - 3);
        System.out.println("i =" + i);
        return i;
    }

    public void refresh(View view) {
        xImageLoader.displayImage(strings[getUrl()], mImageView);
    }
}
