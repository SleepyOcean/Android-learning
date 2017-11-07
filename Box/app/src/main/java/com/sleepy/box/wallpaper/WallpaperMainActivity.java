package com.sleepy.box.wallpaper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.sleepy.box.R;

import java.util.ArrayList;
import java.util.List;

public class WallpaperMainActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;
    List<String> tabIndicators;
    List<Fragment> tabFragments;
    ContentPagerAdapter contentPagerAdapter;
    RandomWallpaperFragment randomWallpaperFragment;
    LatestWallpaperFragment latestWallpaperFragment;
    ToplistWallpaperFragment toplistWallpaperFragment;
    FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setStatusBarColor(getResources().getColor(R.color.wallpaperColorTheme));
        setContentView(R.layout.activity_wallpaper_main);



        tabLayout = (TabLayout) findViewById(R.id.id_tab_wallpaper);
        viewPager = (ViewPager) findViewById(R.id.id_viewpager_wallpaper);
        initContent();
        initTab();




    }

    public void initContent() {
        tabIndicators = new ArrayList<>();

        tabIndicators.add("Random");
        tabIndicators.add("Latest");
        tabIndicators.add("TopList");

        tabFragments = new ArrayList<>();

        randomWallpaperFragment = new RandomWallpaperFragment();
        latestWallpaperFragment = new LatestWallpaperFragment();
        toplistWallpaperFragment = new ToplistWallpaperFragment();
        tabFragments.add(randomWallpaperFragment);
        tabFragments.add(latestWallpaperFragment);
        tabFragments.add(toplistWallpaperFragment);
        contentPagerAdapter = new ContentPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(contentPagerAdapter);
    }

    private void initTab() {
        tabLayout.setTabTextColors(Color.GRAY,Color.CYAN);
        tabLayout.setSelectedTabIndicatorColor(Color.CYAN);
        ViewCompat.setElevation(tabLayout,10);
        tabLayout.setupWithViewPager(viewPager);
    }








    private class ContentPagerAdapter extends FragmentPagerAdapter{
        public ContentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return tabFragments.get(position);
        }

        @Override
        public int getCount() {
            return tabFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabIndicators.get(position);
        }
    }
}
