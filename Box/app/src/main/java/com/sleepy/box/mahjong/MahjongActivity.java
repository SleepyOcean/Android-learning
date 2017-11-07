package com.sleepy.box.mahjong;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.sleepy.box.R;

public class MahjongActivity extends AppCompatActivity {
    MahjongLayout mahjongLayout;
    TextView levelTv;
    TextView scoreTv;
    TextView timerTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mahjong);
        levelTv = (TextView) findViewById(R.id.id_mahjong_tv_level);
        scoreTv = (TextView) findViewById(R.id.id_mahjong_tv_score);
        timerTv = (TextView) findViewById(R.id.id_mahjong_tv_timer);

        mahjongLayout = (MahjongLayout) findViewById(R.id.id_mahjong_game_ly);
        mahjongLayout.setCounterPad(levelTv,scoreTv,timerTv);


    }
}
