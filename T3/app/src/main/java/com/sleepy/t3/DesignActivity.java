/*
 * Created by sleepy on 17-2-4 下午6:10
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * Created by sleepy on 2017/2/4.
 */

public class DesignActivity extends AppCompatActivity {

    private static final int IMAGE = 1;
    CustomLayout mCustomLayout;
    EditText mEditText;
    RelativeLayout mRelativeLayout;
    EditText mEditTextName;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_design);
        mRelativeLayout = (RelativeLayout) findViewById(R.id.id_show);
        mCustomLayout = (CustomLayout) findViewById(R.id.id_tile_show);
        mEditText = (EditText) findViewById(R.id.id_columnNum);

        MainActivity.activityMain.finish();
    }


    public void setColumn(View view) {
        int num = 3;
        if (TextUtils.isEmpty(mEditText.getText())) {
//            Log.e("TAG", "setColumn: ######################################################################" );
        } else {
            if (Util.isNum(mEditText.getText().toString())) {
                num = Integer.valueOf((mEditText.getText().toString()));
                if (num < 1 || num > 10) {
                    Toast.makeText(this, "Invalid Num", Toast.LENGTH_SHORT).show();
                } else {
                    mCustomLayout.setmColumn(num);
                    mCustomLayout.invalidate();
                }
//                Log.d("TAG", "initDesignFragment: =====================================================================" + num);
            } else {
                Toast.makeText(this, "Wrong input", Toast.LENGTH_SHORT).show();
            }
        }

//            Log.d("TAG", "initDesignFragment:++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public void onSaveData(View view) {
        mEditTextName = new EditText(this);
//        Toast.makeText(this, "saving...", Toast.LENGTH_SHORT).show();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Title");
        builder.setView(mEditTextName);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                Toast.makeText(DesignActivity.this, PreferenceHelper.getString("name",getApplicationContext())+Util.CurrentNum, Toast.LENGTH_SHORT).show();
                PreferenceHelper.putString("name" + Util.CurrentNum, mEditTextName.getText().toString(), getApplicationContext());
                PreferenceHelper.putString("time" + Util.CurrentNum, Util.getCurrentTime(), getApplicationContext());
//                Log.d("TAG", "onClick: Current Path:%%%%%%%%%%%%%%%%%" + Util.imagePath);
                PreferenceHelper.putString("path" + Util.CurrentNum, Util.imagePath, getApplicationContext());
                PreferenceHelper.putInt("column" + Util.CurrentNum, mCustomLayout.getColumn(), getApplicationContext());
                for(int i=0;i<mCustomLayout.getItemBitmaps().size();i++){
                    PreferenceHelper.putInt("tangle"+Util.CurrentNum+i,(int)mCustomLayout.getItemBitmaps().get(i).getCurrentTangle(),getApplicationContext());
//                    Log.d("TAG", "loadTangleRecord: #####################"+(int)mCustomLayout.getItemBitmaps().get(i).getCurrentTangle());
//                    Log.d("TAG", "loadTangleRecord: ----------------------------------------------------------------------------");
//                    Log.d("TAG", "loadTangleRecord: #####################"+mCustomLayout.getItemBitmaps().get(i).getCurrentTangle());
                }
                Util.CurrentNum++;
                PreferenceHelper.putInt("currentNum", Util.CurrentNum, getApplicationContext());
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Util.resetTangle();
        startActivity(new Intent(this, MainActivity.class));
    }
}
