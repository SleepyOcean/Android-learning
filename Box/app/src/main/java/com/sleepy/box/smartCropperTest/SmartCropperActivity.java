package com.sleepy.box.smartCropperTest;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.sleepy.box.R;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import me.pqpo.smartcropperlib.view.CropImageView;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class SmartCropperActivity extends AppCompatActivity {
    public final int TYPE_TAKE_PHOTO = 1;//Uri获取类型判断

    public final int CODE_TAKE_PHOTO = 1;//相机RequestCode

    public final int CODE_PERMISSION = 200;

    Uri photoUri;
    CropImageView imageView;
    ImageView img;

    // 声明一个数组，用来存储所有需要动态申请的权限
    String[] permissions = new String[]{
            Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
    // 声明一个集合，在后面的代码中用来存储用户拒绝授权的权
    List<String> mPermissionList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smart_cropper);

        imageView = (CropImageView) findViewById(R.id.id_cropper_img);
        img = (ImageView) findViewById(R.id.id_img_result_crop);
    }

    private void permissionAsk() {
        if (Build.VERSION.SDK_INT >= 23) {
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(SmartCropperActivity.this, Manifest.permission.CAMERA);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SmartCropperActivity.this, new String[]{Manifest.permission.CAMERA}, 222);
                return;
            } else {
                openCamera();
            }
        } else {
            openCamera();
        }
    }

    private void checkPermission() {
        mPermissionList.clear();
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permission);
            }
        }
        if (!mPermissionList.isEmpty()) {
            for (String permission : mPermissionList){
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{permission},CODE_PERMISSION);
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode==CODE_PERMISSION) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    //判断是否勾选禁止后不再询问
                    boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(SmartCropperActivity.this, permissions[i]);
                    if (showRequestPermission) {
                    } else {
                    }
                }
            }

        }
//        switch (requestCode) {
//            //就像onActivityResult一样这个地方就是判断你是从哪来的。
//            case 222:
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    // Permission Granted
//                    openCamera();
//                } else {
//                    // Permission Denied
//                    Toast.makeText(SmartCropperActivity.this, "很遗憾你把相机权限禁用了。请务必开启相机权限享受我们提供的服务吧。", Toast.LENGTH_SHORT)
//                            .show();
//                }
//                break;
//            default:
//                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        }

    }

    private void openCamera() {
        Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        photoUri = getMediaFileUri(TYPE_TAKE_PHOTO);
//        takeIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
//        startActivityForResult(takeIntent, CODE_TAKE_PHOTO);
        startActivityForResult(takeIntent, CODE_TAKE_PHOTO);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void shootPhoto(View view) {
        permissionAsk();
    }

    public Uri getMediaFileUri(int type) {
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "tmpCamera");
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }
        //创建Media File
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == TYPE_TAKE_PHOTO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator + "IMG_" + timeStamp + ".jpg");
        } else {
            return null;
        }
        return FileProvider.getUriForFile(this, getPackageName() + ".fileprovider", mediaFile);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case CODE_TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (data != null) {
                        if (data.hasExtra("data")) {
                            Log.i("URI", "data is not null");
                            Bitmap bitmap = data.getParcelableExtra("data");
                            imageView.setImageToCrop(bitmap);
                        }
                    } else {
                        Log.i("TAG", "Data is null  photoUri:     " + photoUri.toString());
                        Log.i("TAG", "Data is null  photoUri.getPath():      " + photoUri.getPath().toString());
                        Bitmap bitmap = BitmapFactory.decodeFile(photoUri.getPath());
                        imageView.setImageToCrop(bitmap);
                        Toast.makeText(this, "smartCropper执行了", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    public void finishCrop(View view){
        Bitmap bm = imageView.crop();
        imageView.setVisibility(View.INVISIBLE);
        img.setVisibility(View.VISIBLE);
        img.setImageBitmap(bm);
    }
}
