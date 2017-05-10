/*
 * Created by sleepy on 17-4-28 下午8:03
 * Copyright (c) 2017.
 */

package com.sleepy.ebdatatransfer03;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Timer;

public class ShowDataActivity extends AppCompatActivity {
    TextView statusTV;
    TextView temperatureTV;
    TextView humidityTV;
    TextView uVTV;
    TextView powerTV;
    TextView voltTV;
    TextView electricityTV;
    TextView dirtTV;


    RefreshThread refreshThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);
        setTitle("实时数据显示");
        statusTV = (TextView) findViewById(R.id.tv_status);
        temperatureTV = (TextView) findViewById(R.id.tv_temperature);
        humidityTV = (TextView) findViewById(R.id.tv_humidity);
        uVTV = (TextView) findViewById(R.id.tv_UV);
        powerTV = (TextView) findViewById(R.id.tv_power);
        voltTV = (TextView) findViewById(R.id.tv_volt);
        electricityTV = (TextView) findViewById(R.id.tv_electricity);
        dirtTV = (TextView) findViewById(R.id.tv_dirt);


//        initData();
        refreshThread = new RefreshThread();
        refreshThread.start();
    }

    class RefreshThread extends Thread {
        @Override
        public void run() {
            while (true) {
                Message message = new Message();
                mHandler.sendMessage(message);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            setData();
            super.handleMessage(msg);
        }
    };


    private void initData() {
        statusTV.setText("优");
    }

    private void setData() {
        temperatureTV.setText(DataUtil.temperature);
        humidityTV.setText(DataUtil.humidity);
        uVTV.setText(String.valueOf(DataUtil.uV));
        voltTV.setText(String.valueOf(DataUtil.volt));
        electricityTV.setText(String.valueOf(DataUtil.electricity));
        powerTV.setText(DataUtil.power);
        dirtTV.setText(DataUtil.dirt);
        statusTV.setText(DataUtil.status);
    }

    public void startClean(View view) {
        Toast.makeText(this, "开始清理...", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
