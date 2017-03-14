/*
 * Created by sleepy on 17-2-4 下午6:10
 * Copyright (c) 2017.
 */

package com.sleepy.t3;


import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sleepy on 2017/2/3.
 */

public class MainFragment extends Fragment implements AdapterView.OnItemClickListener {
    private ListView mListView;
    List<BeanListItem> mData;
    private MyAdapter myAdapter;
    View view;


    interface OnListItemClickListener{
        void onItemClick(View v);
    }

    private OnListItemClickListener mOnListItemClickListener;

    public void setOnListItemClickListener(OnListItemClickListener onListItemClickListener) {
        mOnListItemClickListener = onListItemClickListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_main, container);

        initData();

        initView();

        return view;
    }

 


    private void addData(String name,String path) {
        BeanListItem bean = new BeanListItem(name,Util.getCurrentTime());
        mData.add(bean);
    }

    private void initData() {
        mData = new ArrayList<BeanListItem>();
        for (int i = Util.CurrentNum-1; i >= 0 ; i--) {
            BeanListItem bean = new BeanListItem(PreferenceHelper.getString("name"+i,getContext()), PreferenceHelper.getString("time"+i,getContext()));
            mData.add(bean);
        }
        myAdapter = new MyAdapter(getActivity(), mData);
    }

    private void initView() {
        mListView = (ListView) view.findViewById(R.id.id_listView);
        mListView.setAdapter(myAdapter);
        mListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        startActivity(new Intent(getActivity(), DesignActivity.class));

        mOnListItemClickListener.onItemClick(view);

    }
}
