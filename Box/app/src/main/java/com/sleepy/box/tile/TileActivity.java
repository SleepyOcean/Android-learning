package com.sleepy.box.tile;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.sleepy.box.R;

import java.util.ArrayList;
import java.util.List;

public class TileActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<TileItem> data;
    TileAdapter adapter;
    Handler handler;
    TileGetterThread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tile);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.id_container_tile);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        if (data.isEmpty()) {
            Log.d("TAG", "onCreate: data is empty");
        }
        adapter = new TileAdapter(this, data);
        recyclerView.setAdapter(adapter);

    }

    public void refresh(View view) {
        if (!data.isEmpty()) {
            adapter.setData(data);
            adapter.notifyDataSetChanged();
            Toast.makeText(this, "data has been set", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "data is empty", Toast.LENGTH_SHORT).show();
        }
    }

    private void initData() {
        data = new ArrayList<>();
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case TileGetterThread.MESSAGE_SUCCESS:
                        data = (ArrayList<TileItem>) msg.obj;
                        Log.d("TAG", "handleMessage:  data :" + data.isEmpty() + " " + data.size());
                        int i = 0;
                        for (TileItem item : data) {
                            Log.d("TAG", "handleMessage:  data " + (i++) + " link: " + item.link + "   name: " + item.name);
                        }
                        break;
                    case TileGetterThread.MESSAGE_FAILURE:
                        break;
                    default:

                }
            }
        };
        thread = new TileGetterThread(handler);
        thread.start();
    }
}
