package com.sleepy.box.account;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sleepy.box.R;
import com.sleepy.box.util.SleepyUtil;

//import net.sqlcipher.database.SQLiteDatabase;



/**
 * A simple {@link Fragment} subclass.
 */
public class AccountEditFragment extends Fragment {

    EditText category;
    EditText account;
    EditText password;
    Button save;


    public AccountEditFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account_edit, container, false);
        init(view);
        return view;
    }


    private void init(View view) {
        category = (EditText) view.findViewById(R.id.id_et_category_account);
        account = (EditText) view.findViewById(R.id.id_et_account_account);
        password = (EditText) view.findViewById(R.id.id_et_pw_account);
        save = (Button) view.findViewById(R.id.id_bt_save_account);



        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (category.getText().toString().equals("") || account.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(getContext(), "表单未完成", Toast.LENGTH_SHORT).show();
                    return;
                }
                final EditText editText = new EditText(getContext());
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("起个名字吧");
                builder.setView(editText);
                builder.setNegativeButton("取消", null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        saveData(category.getText().toString().trim(), account.getText().toString().trim(), password.getText().toString().trim(), editText.getText().toString().trim(),AccountClassHelper.getCurrentTime());
                        FragmentManager manager = getFragmentManager();
                        FragmentTransaction transaction = manager.beginTransaction();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                        transaction.replace(R.id.id_fragment_container_account, new AccountMainFragment());
                        transaction.commit();
                        AccountClassHelper.isNotAccountMain = false;
                    }
                });
                builder.show();
            }
        });

    }




    private void saveData(String trim, String trim1, String trim2, String trim3,String trim4) {
//        AccountDBEncryptedHelper dbHelper = new AccountDBEncryptedHelper(getContext());
        AccountDBHelper dbHelper = new AccountDBHelper(getContext());
//        SQLiteDatabase db = dbHelper.getWritableDatabase(AccountDBEncryptedHelper.DB_PWD);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cv = new ContentValues();

//       encodeString(trim,trim1,trim2,trim3,trim4);

        cv.put("category", trim);
        cv.put("account", trim1);
        cv.put("password", trim2);
        cv.put("state", trim3);
        cv.put("time",trim4);
        db.insert("account", null, cv);
        db.close();
    }

    private void encodeString(String trim, String trim1, String trim2, String trim3, String trim4) {
        trim = SleepyUtil.encryptUtil.encode(trim);
        trim1 = SleepyUtil.encryptUtil.encode(trim1);
        trim2 = SleepyUtil.encryptUtil.encode(trim2);
        trim3 = SleepyUtil.encryptUtil.encode(trim3);
        trim4 = SleepyUtil.encryptUtil.encode(trim4);
    }

}
