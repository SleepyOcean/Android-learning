package com.sleepy.box.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sleepy.box.R;

public class FunctionTestActivity extends AppCompatActivity {
    EditText etText;
    TextView tvEncrypted;
    TextView tvDecoded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function_test);
        init();
    }

    private void init() {
        etText = (EditText) findViewById(R.id.id_et_test_text);
        tvEncrypted= (TextView) findViewById(R.id.id_tv_test_encrypted);
        tvDecoded= (TextView) findViewById(R.id.id_tv_test_decoded);
    }

    public void onEncrypt(View view){
        tvEncrypted.setText(AESUtil.encrypt(etText.getText().toString().trim(),"1234").toString());
    }

    public void onDecode(View view){
        Log.d("TAG", "onDecode: <<<<<<<<<<<<<<<<<<<<<"+AESUtil.encrypt(etText.getText().toString().trim(),"1234"));
        Log.d("TAG", "onDecode: >>>>>>>>>>>>>>>>>>>>>"+AESUtil.decrypt(AESUtil.encrypt(etText.getText().toString().trim(),"1234"),"1234"));
//        tvDecoded.setText((AESUtil.decrypt(AESUtil.encrypt(etText.getText().toString().trim(),"1234"),"1234")).toString());
    }
}
