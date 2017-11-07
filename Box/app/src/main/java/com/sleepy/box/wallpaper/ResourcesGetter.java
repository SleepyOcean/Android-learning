package com.sleepy.box.wallpaper;

import android.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gehou on 2017/9/30.
 */

public class ResourcesGetter {
    List<WallpaperAdapter.ItemInfo> urlList;
    private int page = 1;


    private String currentFragment = RANDOM;
    private boolean loading = false;
    public boolean isloaded;

    int loadTimes = 0;

    int times = 0;

    private static ResourcesGetter instance;

    final static String RANDOM = "random";
    final static String TOPLIST = "toplist";
    final static String LATEST = "latest";

    public ResourcesGetter setPage(int page) {
        this.page = page;
        return this;
    }

    public ResourcesGetter setCurrentFragment(String currentFragment) {
        this.currentFragment = currentFragment;
        return this;
    }

    public static ResourcesGetter getInstance() {
        if (instance == null) {
            instance = new ResourcesGetter();
        }
        return instance;
    }

    public void startLoading() {
        loading = true;
        loadTimes++;
    }

    private List<WallpaperAdapter.ItemInfo> getWallpaperUrlList(String category) {
        List<WallpaperAdapter.ItemInfo> res = new ArrayList<>();
        WallpaperAdapter.ItemInfo item;
        Document doc = null;
        try {
            doc = Jsoup.connect("https://alpha.wallhaven.cc/" + category + "?page=" + page).get();
            Elements elements = doc.getElementsByTag("figure");
            for (Element e : elements) {
                item = new WallpaperAdapter.ItemInfo("https://wallpapers.wallhaven.cc/wallpapers/thumb/small/th-" + e.attr("data-wallpaper-id") + ".jpg", "https://wallpapers.wallhaven.cc/wallpapers/full/wallhaven-" + e.attr("data-wallpaper-id") + ".jpg", "");
                res.add(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    public Runnable createNewRunnable(String category) {
        return new UrlRequestRunnable(category);
    }

    class UrlRequestRunnable implements Runnable {
        private String category;

        public UrlRequestRunnable(String category) {
            this.category = category;
        }

        @Override
        public void run() {
            Log.d("TAG", "run: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>Thread is Running...");
            while (true) {
                if (loading && currentFragment.equals(category)) {
                    Log.d("TAG", "          ");
                    Log.d("TAG", "run: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=============<begin to loading..." + "     loading = " + loading + "     loadTimes =" + loadTimes + "     category =" + category + "     page =" + page);
                    isloaded = false;
                    urlList = getWallpaperUrlList(category);
                    isloaded = true;
                    Log.d("TAG", "run: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>data loaded...");
                    loading = false;
                    Log.d("TAG", "run: >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>=============>loading set false..." + "     loading = " + loading + "     loadTimes =" + loadTimes + "     category =" + category + "     page =" + page);
                }
            }
        }
    }
}
