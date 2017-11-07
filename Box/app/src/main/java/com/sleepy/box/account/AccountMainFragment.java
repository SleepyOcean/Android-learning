package com.sleepy.box.account;


import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sleepy.box.R;
import com.sleepy.box.util.SleepyUtil;

import java.util.ArrayList;
import java.util.List;

//import net.sqlcipher.Cursor;



/**
 * A simple {@link Fragment} subclass.
 */
public class AccountMainFragment extends Fragment {
    View view;
    RecyclerView recyclerView;
    List<ItemData> dataList;

//    AccountDBEncryptedHelper dbHelper;
    AccountDBHelper dbHelper;
//    net.sqlcipher.database.SQLiteDatabase db;
    SQLiteDatabase db;


    private class ItemData {
        int id;
        String categoryStr;
        String accountStr;
        String pwStr;
        String stateStr;
        String dateStr;

        public ItemData(int id, String categoryStr, String accountStr, String pwStr, String stateStr, String dateStr) {
            this.id = id;
            this.categoryStr = categoryStr;
            this.accountStr = accountStr;
            this.pwStr = pwStr;
            this.stateStr = stateStr;
            this.dateStr = dateStr;
        }

        protected void decodeString(){
            categoryStr = SleepyUtil.encryptUtil.decode(categoryStr);
            accountStr = SleepyUtil.encryptUtil.decode(accountStr);
            pwStr = SleepyUtil.encryptUtil.decode(pwStr);
            stateStr = SleepyUtil.encryptUtil.decode(stateStr);
            dateStr = SleepyUtil.encryptUtil.decode(dateStr);
        }

    }

    public AccountMainFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_account_main, container, false);
        init();
        return view;
    }

    private void init() {
        dataList = new ArrayList<>();
        getData();
        recyclerView = view.findViewById(R.id.id_rc_account);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new AccountMainRCAdapter());
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }


    private void getData() {
//        dbHelper = new AccountDBEncryptedHelper(getContext());
        dbHelper = new AccountDBHelper(getContext());
//        db = dbHelper.getReadableDatabase(AccountDBEncryptedHelper.DB_PWD);
        db = dbHelper.getReadableDatabase();
        Cursor c = db.query("account", null, null, null, null, null, null);

        ItemData item;
        while (c.moveToNext()) {
            String id = c.getString(c.getColumnIndex("id"));
            String category = c.getString(c.getColumnIndex("category"));
            String account = c.getString(c.getColumnIndex("account"));
            String password = c.getString(c.getColumnIndex("password"));
            String state = c.getString(c.getColumnIndex("state"));
            String date = c.getString(c.getColumnIndex("time"));
            item = new ItemData(Integer.valueOf(id), category, account, password, state, date);
            if (item != null && Integer.valueOf(id) != 0)
//                item.decodeString();
                dataList.add(item);
        }
        db.close();
    }


    class AccountMainRCAdapter extends RecyclerView.Adapter<AccountMainRCAdapter.AccountMainRCViewHolder> {

        @Override
        public AccountMainRCViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            AccountMainRCViewHolder holder = new AccountMainRCViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_rc_account, parent, false));
            return holder;
        }

        @Override
        public void onBindViewHolder(final AccountMainRCViewHolder holder, final int position) {
            holder.container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("是否要删除该条目");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData(position);
                            notifyItemRemoved(position);
                            if (position != dataList.size()) {
                                notifyItemRangeChanged(position, dataList.size() - position);
                            }
                        }
                    });
                    builder.setNegativeButton("取消", null);
                    builder.show();
                    return true;
                }
            });
            holder.category.setText(dataList.get(position).categoryStr);
            holder.account.setText(dataList.get(position).accountStr);
            holder.password.setText(dataList.get(position).pwStr);
            holder.state.setText(dataList.get(position).stateStr);
            holder.date.setText(dataList.get(position).dateStr);
        }

        private void deleteData(int position) {
//            dbHelper = new AccountDBEncryptedHelper(getContext());
            dbHelper = new AccountDBHelper(getContext());
//            db = dbHelper.getReadableDatabase(AccountDBEncryptedHelper.DB_PWD);
            db = dbHelper.getReadableDatabase();
            String[] args = new String[]{String.valueOf(dataList.get(position).id)};
            db.delete("account", "id=?", args);
            dataList.clear();
            getData();
        }


        @Override
        public int getItemCount() {
            return dataList.size();
        }


        class AccountMainRCViewHolder extends RecyclerView.ViewHolder {
            int id;
            TextView category;
            TextView account;
            TextView password;
            TextView state;
            TextView date;
            View container;

            public AccountMainRCViewHolder(View itemView) {
                super(itemView);
                container = itemView;
                category = itemView.findViewById(R.id.id_tv_category_account);
                account = itemView.findViewById(R.id.id_tv_account_account);
                password = itemView.findViewById(R.id.id_tv_pw_account);
                state = itemView.findViewById(R.id.id_tv_state_account);
                date = itemView.findViewById(R.id.id_tv_date_account);
            }


        }




    }

}
