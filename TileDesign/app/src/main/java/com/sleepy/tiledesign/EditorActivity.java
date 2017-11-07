package com.sleepy.tiledesign;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


public class EditorActivity extends AppCompatActivity {
    static final String EXTRA_IMAGES = "image";
    EditorLayout editorLayout;
    EditText colEt;
    SeekBar seekBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_editor);

        init();

    }

    private void init() {
        editorLayout = (EditorLayout) findViewById(R.id.id_editor_layout);
        editorLayout.setDefaultPath(getIntent().getStringExtra(EXTRA_IMAGES));
        seekBar = (SeekBar) findViewById(R.id.seekBar);
        colEt = (EditText) findViewById(R.id.id_et_col);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                editorLayout.setOffset(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        colEt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE)
                    resetCol();
                return false;
            }
        });
    }

    public void saveWork(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editText = new EditText(this);
        builder.setTitle(getResources().getString(R.string.set_title));
        builder.setView(editText);
        builder.setPositiveButton(R.string.bt_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                editorLayout.saveSingleEditWork(editText.getText().toString());
                Toast.makeText(EditorActivity.this, editText.getText().toString()+" saved", Toast.LENGTH_SHORT).show();
            }
        });
        builder.show();

    }

    public void resetCol() {
        String col = colEt.getText().toString().trim();
        if("".equals(col)){
            return;
        }
        int column = Integer.valueOf(col);
        if(column>6||column<2){
            Toast.makeText(this, "数值不在范围（范围2~6）", Toast.LENGTH_SHORT).show();
            return;
        }
        editorLayout.setCol(column);
        editorLayout.invalidate();
    }


}
