package com.sleepy.box.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sleepy.box.R;

public class TestActivity extends AppCompatActivity {
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        imageView = (ImageView) findViewById(R.id.id_img_test);
        String imageUrl = "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-567305.jpg";
        Glide.with(this).load(imageUrl).into(imageView);

    }


}
