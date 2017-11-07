package com.sleepy.box.wallpaper;

import android.app.DownloadManager;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.RequestOptions;
import com.github.florent37.glidepalette.GlidePalette;
import com.sleepy.box.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gehou on 2017/9/30.
 */

public class WallpaperAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<ItemInfo> dataList = new ArrayList<>();

    static Context context;

    final static int TYPE_ITEM = 0;
    final static int TYPE_HEADER = 1;
    final static int TYPE_FOOTER = 2;
    static final int PULL_LOAD_MORE = 0;
    static final int LOADING_MORE = 1;
    static final int NO_MORE = 2;
    int footer_state = 1;

    int width;
    int height;

    public WallpaperAdapter(Context context) {
        this.context = context;
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density = dm.density;
        width = dm.widthPixels;
        height = dm.heightPixels;
    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            View view = parent.inflate(context, R.layout.item_rc_wallpaper, null);
            ItemViewHolder holder = new ItemViewHolder(view);
            return holder;
        } else if (viewType == TYPE_FOOTER) {
            View view = parent.inflate(context, R.layout.footer_layout, null);
            FooterViewHolder footViewHolder = new FooterViewHolder(view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            Glide.with(context).load(dataList.get(position).fullImagePath)
                    .listener(GlidePalette.with(dataList.get(position).path)
                            .use(GlidePalette.Profile.VIBRANT)
                            .intoBackground(((ItemViewHolder) holder).bg))
                    .apply(RequestOptions.fitCenterTransform()).apply(RequestOptions.centerInsideTransform()).into(((ItemViewHolder) holder).imageView);
            ((ItemViewHolder) holder).infoTv.setText(dataList.get(position).info);
            ((ItemViewHolder) holder).floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    downloadWallpaper(dataList.get(position).fullImagePath);
                    Toast.makeText(context, "begin to download...", Toast.LENGTH_SHORT).show();
                }
            });
        } else if (holder instanceof FooterViewHolder) {
            FooterViewHolder footViewHolder = (FooterViewHolder) holder;
            if (position == 0) {
                footViewHolder.mProgressBar.setVisibility(View.GONE);
                footViewHolder.tv_line1.setVisibility(View.GONE);
                footViewHolder.tv_line2.setVisibility(View.GONE);
                footViewHolder.tv_state.setText("");
            }
            switch (footer_state) {
                case LOADING_MORE:
                    footViewHolder.mProgressBar.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line1.setVisibility(View.GONE);
                    footViewHolder.tv_line2.setVisibility(View.GONE);
                    footViewHolder.tv_state.setText("正在加载...");
                    break;
                case NO_MORE:
                    footViewHolder.mProgressBar.setVisibility(View.GONE);
                    footViewHolder.tv_line1.setVisibility(View.VISIBLE);
                    footViewHolder.tv_line2.setVisibility(View.VISIBLE);
                    footViewHolder.tv_state.setText("我是有底线的");
                    footViewHolder.tv_state.setTextColor(Color.parseColor("#ff00ff"));
                    break;
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        } else {
            return TYPE_ITEM;
        }
    }

    private void downloadWallpaper(String url) {
        //创建下载任务,downloadUrl就是下载链接
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        String fileName = url.substring(url.indexOf("full/"), url.length());
        //指定下载路径和下载文件名
        request.setDestinationInExternalPublicDir("/download/", fileName);
        //获取下载管理器
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载任务加入下载队列，否则不会进行下载
        downloadManager.enqueue(request);
    }

    @Override
    public int getItemCount() {
        return dataList != null ? dataList.size() + 1 : 0;
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView infoTv;
        FloatingActionButton floatingActionButton;
        TextView bg;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.id_item_img_wallpaper);
            infoTv = itemView.findViewById(R.id.id_item_tv_wallpaper);
            floatingActionButton = itemView.findViewById(R.id.id_fbt_download_wallpaper);
            bg = itemView.findViewById(R.id.id_tv_background_wallpaper);
        }
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar mProgressBar;
        private TextView tv_state;
        private TextView tv_line1;
        private TextView tv_line2;

        public FooterViewHolder(View itemView) {
            super(itemView);
            mProgressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            tv_state = (TextView) itemView.findViewById(R.id.foot_view_item_tv);
            tv_line1 = (TextView) itemView.findViewById(R.id.tv_line1);
            tv_line2 = (TextView) itemView.findViewById(R.id.tv_line2);
        }
    }

    public void changeState(int state) {
        this.footer_state = state;
        notifyDataSetChanged();
    }

    public static class ItemInfo {
        String path;
        String fullImagePath;
        String info;
        RequestBuilder requestBuilder;

        public ItemInfo(String path,String path2, String info) {
            this.path = path;
            fullImagePath=path2;
            this.info = info;
            requestBuilder = Glide.with(context).load(path);
        }
    }
}
