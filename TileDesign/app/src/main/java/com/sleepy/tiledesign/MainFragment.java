package com.sleepy.tiledesign;


import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.glidepalette.BitmapPalette;
import com.github.florent37.glidepalette.GlidePalette;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment{

    View mView;
    RecyclerView mRecyclerView;
    List<ListItemInfo> mData;
    HomeAdapter adapter;

    CardViewOnClickListener cardViewOnClickListener;

    public void setCardViewOnClickListener(CardViewOnClickListener cardViewOnClickListener) {
        this.cardViewOnClickListener = cardViewOnClickListener;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_main, container, false);
        init();
        return mView;
    }

    private void init() {
        initData();
        initList();
    }

    private void initData() {
        mData = new ArrayList<>();
        for (int i = SleepyUtil.CurrentNum - 1; i >= 0; i--) {
            ListItemInfo bean = new ListItemInfo(PreferenceHelper.getString("path" + i, getContext()), PreferenceHelper.getString("name" + i, getContext()), PreferenceHelper.getString("time" + i, getContext()));
            mData.add(bean);
        }
    }

    private void initList() {
        mRecyclerView = mView.findViewById(R.id.id_list_home);
        mRecyclerView.setNestedScrollingEnabled(false);
        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL));
        adapter = new HomeAdapter(mData);
        mRecyclerView.setAdapter(adapter);
    }


    private class HomeViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView titleTv;
        TextView dateTv;
        LinearLayout background;
        View cardView;


        public HomeViewHolder(View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.id_img);
            titleTv = itemView.findViewById(R.id.id_tv_name);
            dateTv = itemView.findViewById(R.id.id_tv_time);
            background = itemView.findViewById(R.id.id_back_ly);
            cardView = itemView.findViewById(R.id.id_card_bg);
        }
    }

    private class HomeAdapter extends RecyclerView.Adapter<HomeViewHolder> {
        List<ListItemInfo> dataList;

        public HomeAdapter(List<ListItemInfo> dataList) {
            this.dataList = dataList;
        }

        @Override


        public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            HomeViewHolder viewHolder = new HomeViewHolder(parent.inflate(getContext(), R.layout.list_item_layout, null));
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(HomeViewHolder holder, final int position) {
            Glide.with(getContext()).load(dataList.get(position).imagePath)
                    .listener(GlidePalette.with(dataList.get(position).imagePath)
                            .use(GlidePalette.Profile.MUTED_LIGHT)
                            .intoBackground(holder.background))
                    .into(holder.image);
            holder.titleTv.setText(dataList.get(position).title);
            holder.dateTv.setText(dataList.get(position).date);
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    cardViewOnClickListener.onItemClick(v,position);
                }
            });
        }


        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }

    private class ListItemInfo {
        String imagePath;
        String title;
        String date;

        public ListItemInfo(String imagePath, String title, String date) {
            this.imagePath = imagePath;
            this.title = title;
            this.date = date;
        }
    }

    interface CardViewOnClickListener extends View.OnClickListener{
        public void onItemClick(View view,int pos);
    }
}
