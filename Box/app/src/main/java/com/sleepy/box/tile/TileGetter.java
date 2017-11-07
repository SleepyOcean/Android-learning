package com.sleepy.box.tile;

import com.sleepy.box.wallpaper.WallpaperAdapter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by gehou on 2017/11/6.
 */

public class TileGetter {

    public static List<TileItem> getTile() {
        List<TileItem> res = new ArrayList<>();
        Document doc = null;
        try {
            doc = Jsoup.connect("http://caizhi.shejiben.com/more_material-h3s1").get();
            Elements elements = doc.getElementsByTag("span");
            for (Element e : elements) {
                String link = e.getElementsByTag("img").attr("src");
                String name = e.getElementsByTag("img").attr("alt");
                if ("".equals(link) || "".equals(name)) continue;
                res.add(new TileItem(link, name));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}
