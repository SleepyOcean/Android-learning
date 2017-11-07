package com.sleepy.box.tile;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.florent37.glidepalette.GlidePalette;
import com.sleepy.box.R;

import java.util.List;

/**
 * Created by gehou on 2017/11/6.
 */

public class TileAdapter extends RecyclerView.Adapter<TileAdapter.TileViewHolder> {
    Context context;

    public void setData(List<TileItem> data) {
        this.data = data;
    }

    List<TileItem> data;

    public TileAdapter(Context context, List<TileItem> data) {
        this.context = context;
        this.data = data;
    }


    @Override
    public TileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TileViewHolder viewHolder = new TileViewHolder(parent.inflate(context,R.layout.list_item_layout,null));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TileViewHolder holder, int position) {
        Glide.with(context).load(data.get(position).link)
                .listener(GlidePalette.with(data.get(position).link)
                        .use(GlidePalette.Profile.MUTED_LIGHT)
                        .intoBackground(holder.background))
                .into(holder.tileImg);
        holder.tileName.setText(data.get(position).name);
//        holder.cardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                cardViewOnClickListener.onItemClick(v,position);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class TileViewHolder extends RecyclerView.ViewHolder{
        ImageView tileImg;
        TextView tileName;
        LinearLayout background;


        public TileViewHolder(View itemView) {
            super(itemView);
            tileImg = itemView.findViewById(R.id.id_img);
            tileName = itemView.findViewById(R.id.id_tv_name);
            background = itemView.findViewById(R.id.id_back_ly);
        }
    }
}
