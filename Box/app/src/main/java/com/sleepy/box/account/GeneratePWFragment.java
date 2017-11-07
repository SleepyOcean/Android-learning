package com.sleepy.box.account;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sleepy.box.R;
import com.sleepy.box.util.SleepyUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class GeneratePWFragment extends Fragment {
    Button generate;
    TextView generatedPassword;
    CheckBox ch1;
    CheckBox ch2;
    CheckBox ch3;
    EditText pwBit;

    public GeneratePWFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_generate_pw, container, false);
        init(view);
        return view;
    }

    public void init(View view){
        generate = (Button) view.findViewById(R.id.id_bt_generate);
        generatedPassword = (TextView) view.findViewById(R.id.id_tv_generatedpw_account);
        ch1 = view.findViewById(R.id.id_checkbox1_account);
        ch2 = view.findViewById(R.id.id_checkbox2_account);
        ch3 = view.findViewById(R.id.id_checkbox3_account);
        pwBit = view.findViewById(R.id.id_et_bit_account);

        generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                generatedPassword.setText(generateCode());

            }
        });
    }

    private String generateCode() {
        int bit = Integer.valueOf(pwBit.getText().toString().trim().equals("")?"8":pwBit.getText().toString().trim());
        if(bit>4&&bit<20){
            return SleepyUtil.generatePassWord(ch1.isChecked(),ch3.isChecked(),ch2.isChecked(),bit);
        }
        Toast.makeText(getContext(), "位数太多，有效范围4~20", Toast.LENGTH_SHORT).show();
        return "******";

    }


}
