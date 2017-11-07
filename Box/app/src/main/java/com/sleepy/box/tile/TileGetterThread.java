package com.sleepy.box.tile;

import android.os.Handler;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gehou on 2017/11/7.
 */

public class TileGetterThread extends Thread {
    public static final int MESSAGE_FAILURE = 0;
    public static final int MESSAGE_SUCCESS = 1;

    TileGetter getter;
    Handler handler;

    public TileGetterThread(Handler handler) {
        getter = new TileGetter();
        this.handler = handler;
    }

    @Override
    public void run() {
        List<TileItem> data;
        data = TileGetter.getTile();
        Log.d("TAG", "run: data :"+data.isEmpty());
        handler.obtainMessage(MESSAGE_SUCCESS, data).sendToTarget();
    }
}
