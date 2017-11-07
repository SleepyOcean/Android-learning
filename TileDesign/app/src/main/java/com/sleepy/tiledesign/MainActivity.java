package com.sleepy.tiledesign;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zhihu.matisse.Matisse;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainFragment.CardViewOnClickListener {
    static final int REQUEST_CODE_CHOOSE = 1;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    MainFragment mMainFragment;
    EditorFragment mEditorFragment;
    AboutFragment mAboutFragment;
    List<String> pagerTitles;
    List<Fragment> pagerFragments;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.TRANSPARENT);
        window.setNavigationBarColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);
        SleepyUtil.CurrentNum = PreferenceHelper.getInt("currentNum", this);

        init();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_CHOOSE && resultCode == RESULT_OK) {
            List<Uri> images = Matisse.obtainResult(data);

            Uri originalUri = images.get(0);
            String[] pro = {MediaStore.Images.Media.DATA};
            Cursor cursor = managedQuery(originalUri, pro, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String imagePath = cursor.getString(column_index);
            startActivity(new Intent(this, EditorActivity.class).putExtra(EditorActivity.EXTRA_IMAGES, imagePath));
        }
    }

    private void init() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.id_tab_layout);
        initFragments();
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mViewPager.setCurrentItem(1);
        initTab();
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    private void initTab() {
        ViewCompat.setElevation(mTabLayout, 10);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initFragments() {
        pagerFragments = new ArrayList<>();
        pagerTitles = new ArrayList<>();
        mMainFragment = new MainFragment();
        mEditorFragment = new EditorFragment();
        mAboutFragment = new AboutFragment();
        pagerFragments.add(mMainFragment);
        pagerFragments.add(mEditorFragment);
        pagerFragments.add(mAboutFragment);
        pagerTitles.add(getResources().getString(R.string.fragment_title_1));
        pagerTitles.add(getResources().getString(R.string.fragment_title_2));
        pagerTitles.add(getResources().getString(R.string.fragment_title_3));
        mMainFragment.setCardViewOnClickListener(this);
    }

    @Override
    public void onItemClick(View view, int pos) {
        SleepyUtil.position = pos;
        SleepyUtil.isLoad = true;
        startActivity(new Intent(this,EditorActivity.class));
    }

    @Override
    public void onClick(View v) {

    }


    private class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return pagerFragments.get(position);
        }

        @Override
        public int getCount() {
            return pagerFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return pagerTitles.get(position);
        }
    }

}
