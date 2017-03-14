/*
 * Created by sleepy on 17-2-4 下午6:11
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sleepy on 2017/2/4.
 */

public class DesignFragment extends Fragment {
    View view;
    CustomLayout mCustomLayout;
    Bitmap mBitmap;
    EditText mEditText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_design,container);

        mCustomLayout = (CustomLayout) view.findViewById(R.id.id_tile_show);
        mEditText = (EditText) view.findViewById(R.id.id_columnNum);


        initDesignFragment();

        return view;
    }

    private void initDesignFragment() {
        Intent intent = getActivity().getIntent();
        String path = intent.getStringExtra("Image_Path");
        PreferenceHelper.putString("path"+Util.CurrentNum,path,getContext());
        Log.d("TAG", "initDesignFragment: ________"+path);
        mBitmap = BitmapFactory.decodeFile(path);
        mCustomLayout.setBitmap(mBitmap);
    }




}
