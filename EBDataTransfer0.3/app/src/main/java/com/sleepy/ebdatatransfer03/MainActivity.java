/*
 * Created by sleepy on 17-4-28 下午8:02
 * Copyright (c) 2017.
 */

package com.sleepy.ebdatatransfer03;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button mButton;
    EditText mEditTextUsername;
    EditText mEditTextPassWord;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mButton = (Button) findViewById(R.id.button_login);
        mEditTextUsername = (EditText) findViewById(R.id.editText_name);
        mEditTextPassWord = (EditText) findViewById(R.id.editText_pw);
    }

    public void login(View view) {
        if (mEditTextUsername.getText().toString().equals("SUES") && mEditTextPassWord.getText().toString().equals("123456")) {
            startActivity(new Intent(this, BluetoothActivity.class));
            finish();
        } else if (!mEditTextUsername.getText().toString().equals("SUES")) {
            Toast.makeText(this, "用户名错误~", Toast.LENGTH_SHORT).show();
        } else if (!mEditTextPassWord.getText().toString().equals("123456")) {
            Toast.makeText(this, "密码错误~", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "来自火星的错误~", Toast.LENGTH_SHORT).show();
        }

    }
}
