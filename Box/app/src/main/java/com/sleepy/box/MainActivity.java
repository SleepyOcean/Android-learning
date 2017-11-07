package com.sleepy.box;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.sleepy.box.account.AccountActivity;
import com.sleepy.box.ball.BallActivity;
import com.sleepy.box.custom.CustomMainActivity;
import com.sleepy.box.diary.DiaryMainActivity;
import com.sleepy.box.mahjong.MahjongActivity;
import com.sleepy.box.smartCropperTest.SmartCropperActivity;
import com.sleepy.box.tile.TileActivity;
import com.sleepy.box.wallpaper.WallpaperMainActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startAccount(View view) {
        startActivity(new Intent(this, AccountActivity.class));
    }

    public void startDiary(View view) {
        startActivity(new Intent(this, DiaryMainActivity.class));
    }

    public void startCustom(View view) {
        startActivity(new Intent(this, CustomMainActivity.class));
    }

    public void startWallpaper(View view) {
        startActivity(new Intent(this, WallpaperMainActivity.class));
    }

    public void startBall(View view) {
        startActivity(new Intent(this, BallActivity.class));
    }

    public void startMahjong(View view) {
        startActivity(new Intent(this, MahjongActivity.class));
    }

    public void startCropperImage(View view) {
        startActivity(new Intent(this, SmartCropperActivity.class));
    }

    public void startTile(View view) {
        startActivity(new Intent(this, TileActivity.class));
    }
}
