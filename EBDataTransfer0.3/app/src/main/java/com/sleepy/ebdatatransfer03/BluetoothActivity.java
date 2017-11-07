/*
 * Created by sleepy on 17-5-8 下午11:55
 * Copyright (c) 2017. 
 */

package com.sleepy.ebdatatransfer03;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class BluetoothActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView mTVLocalParam;
    ListView mListViewPairedD;
    ListView mListViewAvailableD;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothDevice device;
    BluetoothSocket clientSock;
    Set<BluetoothDevice> pairedDevice;
    ArrayAdapter<String> pairedDLAdapter;
    ArrayAdapter<String> availableDLAdapter;
    String tmpString = "";
    String tString = "";
    String hex = "";
    int count;
    OutputStream os;
    InputStream is;
    List<String> pairedDList;
    List<String> availableDList;

    private final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    private static final String NAME = "BT04-A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        setTitle("设置蓝牙");
        mTVLocalParam = (TextView) findViewById(R.id.param_2);
        mListViewPairedD = (ListView) findViewById(R.id.list_paired);
        mListViewAvailableD = (ListView) findViewById(R.id.list_available);
    }

    private Timer mTimer;
    private TimerTask mTimerTask = new TimerTask() {
        @Override
        public void run() {
            if (clientSock != null) {
                try {
                    while (DataUtil.isTaskPause){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    is = clientSock.getInputStream();
                    os = clientSock.getOutputStream();
                    String tmp;
                    byte[] buff;
                    buff = new byte[128];
                    count = is.read(buff);

                    for (int i = 0; i < count; i++) {
                        tmp = Integer.toHexString(buff[i] & 0xff);
                        if (Integer.valueOf(tmp, 16) < 17) {
                            tmp = "0" + tmp;
                        }
                        hex = hex + tmp;
                    }

                    if (hex.length() == 64) {
                        DataUtil.data = hex;
                        DataUtil.parseData();
                        hex = "";
                    }

                    Message msg = new Message();
                    dataHandler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    };
    private Handler dataHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    public void startData(View view) {
        startActivity(new Intent(this, ShowDataActivity.class));
    }

    public void startBluetooth(View view) {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (mBluetoothAdapter == null) {
            Toast.makeText(this, "此设备没有蓝牙功能", Toast.LENGTH_SHORT).show();
        }

        mTVLocalParam.setText(mBluetoothAdapter.getName());

        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver, filter);

        IntentFilter filter2 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        registerReceiver(receiver, filter2);


        if (mBluetoothAdapter.isEnabled()) {
            Toast.makeText(this, "蓝牙已打开", Toast.LENGTH_SHORT).show();
        } else {
            boolean isOpen = mBluetoothAdapter.enable();
            if (isOpen)
                Toast.makeText(this, "重启蓝牙成功", Toast.LENGTH_SHORT).show();
            else
                Toast.makeText(this, "重启蓝牙失败", Toast.LENGTH_SHORT).show();
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        pairedDList = new ArrayList<>();
        //获取已配对的设备信息，并在mListViewPairedD中显示
        pairedDevice = mBluetoothAdapter.getBondedDevices();
        if (pairedDevice.size() > 0) {
            for (BluetoothDevice bluetoothDevice : pairedDevice) {
                pairedDList.add((bluetoothDevice.getName() + " : " + bluetoothDevice.getAddress()));
            }
        } else {
            pairedDList.add("没有已配对设备");
        }
        pairedDLAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, pairedDList);
        mListViewPairedD.setAdapter(pairedDLAdapter);
        mListViewPairedD.setOnItemClickListener(this);

        if (mBluetoothAdapter.isEnabled()) {
            setTitle("正在扫描设备...");
            if (mBluetoothAdapter.isDiscovering()) {
                mBluetoothAdapter.cancelDiscovery();
            }
            mBluetoothAdapter.startDiscovery();
            availableDList = new ArrayList<>();
        }
    }

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                    availableDList.add((device.getName() + " : " + device.getAddress()));
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                setTitle("完成设置");
            }
            if (availableDList.size() < 1) {
                availableDList.add("没有可用设备");
            }
            availableDLAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, availableDList);
            mListViewAvailableD.setAdapter(availableDLAdapter);
        }
    };

    public void closeBluetooth(View view) {
        Toast.makeText(this, "关闭蓝牙", Toast.LENGTH_SHORT).show();
        pairedDList = new ArrayList<>();
        pairedDList.add("未开启蓝牙");
        pairedDLAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, pairedDList);
        mListViewPairedD.setAdapter(pairedDLAdapter);
        availableDList = new ArrayList<>();
        availableDList.add("未开始搜索");
        availableDLAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, availableDList);
        mListViewAvailableD.setAdapter(availableDLAdapter);
        DataUtil.resetData();
        mBluetoothAdapter.disable();
        DataUtil.isTaskPause = true;
    }


    @Override
    protected void onDestroy() {
        mBluetoothAdapter.disable();
        mTimer.cancel();
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String s = pairedDLAdapter.getItem(position);
        String address = s.substring(s.indexOf(":") + 1).trim();

        if (mBluetoothAdapter.isDiscovering()) {
            mBluetoothAdapter.cancelDiscovery();
        }
        if (device == null) {
            device = mBluetoothAdapter.getRemoteDevice(address);
        }
        if (clientSock == null) {
            try {
                clientSock = device.createRfcommSocketToServiceRecord(MY_UUID);
                clientSock.connect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (clientSock != null) {
            if(!DataUtil.isTaskPause) {
                Toast.makeText(this, "连接成功", Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this, "未能配对设备，请重启应用", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "连接失败，请重启应用", Toast.LENGTH_SHORT).show();
        }

        if (!DataUtil.isTaskOn) {
            mTimer = new Timer();
            mTimer.schedule(mTimerTask, 0, 1000);
            DataUtil.isTaskOn = true;
        }else {
            DataUtil.isTaskPause = false;
        }
    }
}
