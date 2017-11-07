package com.sleepy.box.account;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.sleepy.box.AboutMeFragment;
import com.sleepy.box.R;

public class AccountActivity extends AppCompatActivity {
    Toolbar toolbar;
    FragmentManager manager;
    FragmentTransaction transaction;
    AccountMainFragment mainFragment;
    AccountEditFragment editFragment;
    GeneratePWFragment generatePWFragment;
    AccountSettingFragment accountSettingFragment;
    AboutMeFragment aboutMeFragment;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorTheme, null));
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_account);

        initToolBar();
        setMainView();

    }


    private void setMainView() {
        manager = getSupportFragmentManager();
        transaction = manager.beginTransaction();
        mainFragment = new AccountMainFragment();
        transaction.add(R.id.id_fragment_container_account, mainFragment);
        transaction.commit();
        AccountClassHelper.isNotAccountMain = false;
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.id_toolbar_account);
        toolbar.inflateMenu(R.menu.account_toolbar_menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.id_toolbar_menu_account_generate_pw:
                        transaction = manager.beginTransaction();
                        generatePWFragment = new GeneratePWFragment();
                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                        transaction.replace(R.id.id_fragment_container_account, generatePWFragment);
                        transaction.commit();
                        AccountClassHelper.isNotAccountMain = true;
                        toolbar.getMenu().findItem(R.id.id_toolbar_menu_account_search).setVisible(false);
                        break;
                    case R.id.id_toolbar_menu_account_setting:
                        startSetting();
                        break;
                    case R.id.id_toolbar_menu_account_about:
                        startAboutMe();
                        break;
                    case R.id.id_toolbar_menu_account_search:
                        // TODO: 2017/9/16 add func
                        Toast.makeText(AccountActivity.this, "search", Toast.LENGTH_SHORT).show();
                        break;
                }
                return false;
            }

            private void startAboutMe() {
                transaction = manager.beginTransaction();
                aboutMeFragment = new AboutMeFragment();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.id_fragment_container_account, aboutMeFragment);
                transaction.commit();
                AccountClassHelper.isNotAccountMain = true;
                toolbar.getMenu().findItem(R.id.id_toolbar_menu_account_search).setVisible(false);
            }

            private void startSetting() {
                transaction = manager.beginTransaction();
                accountSettingFragment = new AccountSettingFragment();
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                transaction.replace(R.id.id_fragment_container_account, accountSettingFragment);
                transaction.commit();
                AccountClassHelper.isNotAccountMain = true;
                toolbar.getMenu().findItem(R.id.id_toolbar_menu_account_search).setVisible(false);
                toolbar.setTitle("设置");
            }


        });
    }



    public void addNew(View view) {
        startEditFragment();
    }

    private void startEditFragment() {
        transaction = manager.beginTransaction();
        editFragment = new AccountEditFragment();
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.replace(R.id.id_fragment_container_account, editFragment);
        transaction.commit();
        AccountClassHelper.isNotAccountMain = true;
        toolbar.getMenu().findItem(R.id.id_toolbar_menu_account_search).setVisible(false);
    }


    @Override
    public void onBackPressed() {
        if (AccountClassHelper.isNotAccountMain) {
            transaction = manager.beginTransaction();
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
            transaction.replace(R.id.id_fragment_container_account, mainFragment);
            transaction.commit();
            AccountClassHelper.isNotAccountMain = false;
            toolbar.getMenu().findItem(R.id.id_toolbar_menu_account_search).setVisible(true);
//            toolbar.getMenu().findItem(R.id.id_toolbar_menu_account_about).setVisible(true);
//            toolbar.getMenu().findItem(R.id.id_toolbar_menu_account_setting).setVisible(true);
//            toolbar.getMenu().findItem(R.id.id_toolbar_menu_account_generate_pw).setVisible(true);
        } else {
            super.onBackPressed();
        }
    }

}
