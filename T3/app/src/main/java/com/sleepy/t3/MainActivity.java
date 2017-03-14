/*
 * Created by sleepy on 17-2-4 下午5:45
 * Copyright (c) 2017.
 */

package com.sleepy.t3;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MainFragment.OnListItemClickListener, ArcMenu.OnMenuItemClickListener {

    static final String IMAGE_UNSPECIFIED = "image/*";
    static final int IMAGE_CODE = 0; // 这里的IMAGE_CODE是自己任意定义的
    String imagePath;
    MainFragment mMainFragment;
    MainFragment mainFragment;
    ArcMenu arcMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Util.CurrentNum = PreferenceHelper.getInt("currentNum",getApplicationContext());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        mMainFragment = (MainFragment) getSupportFragmentManager().findFragmentById(R.id.id_fragment);
        mMainFragment.setOnListItemClickListener(this);

        arcMenu = (ArcMenu) findViewById(R.id.id_arc);
        arcMenu.setOnMenuItemClickListener(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bm = null;

        // 外界的程序访问ContentProvider所提供数据 可以通过ContentResolver接口

        ContentResolver resolver = getContentResolver();

        if (requestCode == IMAGE_CODE) {

            try {

                Uri originalUri = data.getData(); // 获得图片的uri

                bm = MediaStore.Images.Media.getBitmap(resolver, originalUri);

//                imageView.setImageBitmap(ThumbnailUtils.extractThumbnail(bm, 100, 100));  //使用系统的一个工具类，参数列表为 Bitmap Width,Height  这里使用压缩后显示，否则在华为手机上ImageView 没有显示
                // 显得到bitmap图片
                // imageView.setImageBitmap(bm);

                String[] proj = {MediaStore.Images.Media.DATA};

                // 好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = managedQuery(originalUri, proj, null, null, null);

                // 按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                // 将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                // 最后根据索引值获取图片路径
                imagePath = cursor.getString(column_index);
//                tv.setText(path);
            } catch (IOException e) {
                Log.e("TAG-->Error", e.toString());

            } finally {
                Intent designIntent = new Intent(this, DesignActivity.class);
                designIntent.putExtra("Image_Path", imagePath);
                startActivity(designIntent);
                return;
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onItemClick(View v) {
        startActivity(new Intent(this, DesignActivity.class));

//        DesignFragment designFragment = new DesignFragment();
//        Bundle args = new Bundle();
//        args.putInt("New Fragment",1);
//        designFragment.setArguments(args);
//
//        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.content_main,designFragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();

    }

    @Override
    public void onClick(View view, int pos) {
        switch (pos) {
            case 1:
                Log.e("TAG", "onClick: pos== !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1" + pos);
                break;
            case 2:
                Intent intent = new Intent(Intent.ACTION_PICK, null);
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, MainActivity.IMAGE_UNSPECIFIED);
//                            startActivityForResult(intent, MainActivity.IMAGE_CODE);
                startActivityForResult(intent, MainActivity.IMAGE_CODE);
//                            getContext().startActivity(new Intent(getContext(),DesignActivity.class));
                System.out.print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                break;
            case 3:
                Intent intentCamera = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivity(intentCamera);
                System.out.print("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!1");
                break;
        }
    }


    public void onEdit(View view) {
        Toast.makeText(this, "editing...", Toast.LENGTH_SHORT).show();
    }

}
