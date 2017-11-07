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
public class ToplistWallpaperFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    WallpaperAdapter adapter;
    List<WallpaperAdapter.ItemInfo> urlList;
    LinearLayoutManager linearLayoutManager;

    int lastVisibleItem;
    int page = 0;
    boolean isLoading = false;//用来控制进入getdata()的次数
    int totlePage = 1000;//模拟请求的一共的页数

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_toplist_wallpaper, container, false);

        initParams();
        return view;
    }

    private void initParams() {
        new Thread(ResourcesGetter.getInstance().createNewRunnable(ResourcesGetter.TOPLIST)).start();
        swipeRefreshLayout = view.findViewById(R.id.id_swipeRefresh_layout_toplist_wallpaper);
        recyclerView = (RecyclerView) view.findViewById(R.id.id_container_toplist_wallpaper);
        linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new WallpaperAdapter(getContext());
        loadingData();
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



    private void refreshData() {
        ResourcesGetter.getInstance().setPage(1).setCurrentFragment(ResourcesGetter.TOPLIST).startLoading();
        while (!ResourcesGetter.getInstance().isloaded) ;
        adapter.dataList = ResourcesGetter.getInstance().urlList;
        isLoading = false;
    }


    public void loadingData() {
        ResourcesGetter.getInstance().setPage(page+1).setCurrentFragment(ResourcesGetter.TOPLIST).startLoading();
        while (!ResourcesGetter.getInstance().isloaded) ;
        adapter.dataList.addAll(ResourcesGetter.getInstance().urlList);
        isLoading = false;
    }
}
