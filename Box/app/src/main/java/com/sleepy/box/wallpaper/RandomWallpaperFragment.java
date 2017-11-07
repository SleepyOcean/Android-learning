package com.sleepy.box.wallpaper;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sleepy.box.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RandomWallpaperFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    WallpaperAdapter adapter;
    List<WallpaperAdapter.ItemInfo> urlList;
    LinearLayoutManager linearLayoutManager;

    boolean isFirstLoad = true;

    int lastVisibleItem;
    int page = 0;
    boolean isLoading = false;//用来控制进入getdata()的次数
    int totlePage = 1000;//模拟请求的一共的页数


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_random_wallpaper, container, false);
        isFirstLoad = true;
        initParams();
        isFirstLoad = false;
        return view;
    }

    private void initParams() {
        new Thread(ResourcesGetter.getInstance().createNewRunnable(ResourcesGetter.RANDOM)).start();
        adapter = new WallpaperAdapter(getContext());
        loadingData();
        swipeRefreshLayout = view.findViewById(R.id.id_swipeRefresh_layout_random_wallpaper);
        recyclerView = view.findViewById(R.id.id_container_random_wallpaper);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        recyclerView.setAdapter(adapter);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount() && !isLoading) {
                    if (page < totlePage) {
                        isLoading = true;
                        adapter.changeState(WallpaperAdapter.LOADING_MORE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loadingData();
                                page++;
                            }
                        }, 2000);
                    } else {
                        adapter.changeState(WallpaperAdapter.NO_MORE);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                refreshData();
                adapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            if (!isFirstLoad) {
                loadingData();
            }
        }
    }

    private void refreshData() {
        ResourcesGetter.getInstance().setPage(1).setCurrentFragment(ResourcesGetter.RANDOM).startLoading();
        while (!ResourcesGetter.getInstance().isloaded) ;
        adapter.dataList = ResourcesGetter.getInstance().urlList;
        isLoading = false;
    }


    public void loadingData() {
        ResourcesGetter.getInstance().setPage(page + 1).setCurrentFragment(ResourcesGetter.RANDOM).startLoading();
        while (!ResourcesGetter.getInstance().isloaded) ;
        adapter.dataList.addAll(ResourcesGetter.getInstance().urlList);
        isLoading = false;

    }

}
