/*
 * Created by sleepy on 17-2-4 下午6:16
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by sleepy on 2017/1/23.
 */

public class MyAdapter extends BaseAdapter {
    private LayoutInflater mLayoutInflater;
    private List<BeanListItem> mDataList;
    private RelativeLayout mRelativeLayout;
    private Context context;                    //用于取得res中的资源


    public MyAdapter(Context context, List<BeanListItem> dataList) {
        mLayoutInflater = LayoutInflater.from(context);
        mDataList = dataList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.item_main_ly, parent, false);
            mViewHolder = new ViewHolder();
            mRelativeLayout = (RelativeLayout) convertView.findViewById(R.id.id_back_ly);
            mViewHolder.mName = (TextView) convertView.findViewById(R.id.id_tv_name);
            mViewHolder.mDate = (TextView) convertView.findViewById(R.id.id_tv_time);
            mViewHolder.mImageView = (ImageView) convertView.findViewById(R.id.id_img);

            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        BeanListItem bean = mDataList.get(position);
        mViewHolder.mName.setText(bean.getName());
        mViewHolder.mDate.setText(bean.getDate());
        mViewHolder.mImageView.setImageBitmap(BitmapFactory.decodeFile(PreferenceHelper.getString("path" + (Util.CurrentNum - position-1), convertView.getContext()),null));
        return convertView;
    }

    private class ViewHolder {
        TextView mName;
        TextView mDate;
        ImageView mImageView;
    }
}
