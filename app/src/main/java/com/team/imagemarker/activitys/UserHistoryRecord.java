package com.team.imagemarker.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.imageloder.ImageLoader;
import com.team.imagemarker.utils.imageloder.ImageLoaderUtil;

public class UserHistoryRecord extends Activity {
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_record);
        imageView = (ImageView) findViewById(R.id.test_glide_img);

        ImageLoader imageLoader = new ImageLoader.Builder().url("http://139.199.23.142:8080/TestShowMessage1/marker/test1.jpg")
                .imgView(imageView).build();

        ImageLoaderUtil.getInstance().loadImage(this, imageLoader);

    }
}
