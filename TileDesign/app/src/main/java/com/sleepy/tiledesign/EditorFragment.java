package com.sleepy.tiledesign;


import android.app.Activity;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.engine.impl.PicassoEngine;


public class EditorFragment extends Fragment {
    View view;
    Button galleryBt;
    Button cameraBt;
    Button cloudBt;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_editor, container, false);
        init();
        return view;
    }

    private void init() {
        ImageView imageView = view.findViewById(R.id.id_edit_bg_img);
        imageView.setColorFilter(Color.GRAY, PorterDuff.Mode.MULTIPLY);
        galleryBt = view.findViewById(R.id.id_edit_bt_album);
        cameraBt = view.findViewById(R.id.id_edit_bt_camera);
        cloudBt = view.findViewById(R.id.id_edit_bt_cloud);
        galleryBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Matisse.from(getActivity())
                        .choose(MimeType.allOf()) // 选择 mime 的类型
                        .countable(true)
                        .maxSelectable(1) // 图片选择的最多数量
                        .thumbnailScale(0.85f) // 缩略图的比例
                        .imageEngine(new PicassoEngine()) // 使用的图片加载引擎
                        .theme(R.style.PhotoPickerTheme)
                        .forResult(MainActivity.REQUEST_CODE_CHOOSE); // 设置作为标记的请求码

            }
        });
    }

}
