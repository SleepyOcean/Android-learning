package com.sleepy.box.ball;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.sleepy.box.R;

public class BallActivity extends Activity {
    TextView gravityTv;
    TextView acceleratorTv;
    BallView ballView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_ball);
        gravityTv = findViewById(R.id.id_tv_gravity);
        acceleratorTv = findViewById(R.id.id_tv_accelerator);
        ballView = findViewById(R.id.id_ball_view);

    }

    public void insGravity(View view){
        gravityTv.setText(String.valueOf(Integer.valueOf(gravityTv.getText().toString())+1));
        ballView.setG(Integer.valueOf(gravityTv.getText().toString()));
    }

    public void descGravity(View view){
        gravityTv.setText(String.valueOf(Integer.valueOf(gravityTv.getText().toString())-1));
        ballView.setG(Integer.valueOf(gravityTv.getText().toString()));
    }
    public void insAcc(View view){
        acceleratorTv.setText(String.valueOf(Integer.valueOf(acceleratorTv.getText().toString())+1));
        ballView.setAcc(Integer.valueOf(acceleratorTv.getText().toString()));
    }
    public void descAcc(View view){
        acceleratorTv.setText(String.valueOf(Integer.valueOf(acceleratorTv.getText().toString())-1));
        ballView.setAcc(Integer.valueOf(acceleratorTv.getText().toString()));
    }

}
