package com.demo.simplecook.ui;

import android.os.Bundle;

import com.demo.simplecook.R;
import com.demo.simplecook.databinding.ActivityMainBinding;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mBinding.navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mBinding.navViewPager.setAdapter(new NavPagerAdapter(getSupportFragmentManager()));
        mBinding.navViewPager.addOnPageChangeListener(mOnPageChangeListener);
    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // XXX - Find a better way to bind the two listeners below
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageSelected(int position) {
            switch (position) {
                case 0:
                    mBinding.navigation.setSelectedItemId(R.id.nav_explore);
                    break;
                case 1:
                    mBinding.navigation.setSelectedItemId(R.id.nav_saved);
                    break;
                default:
                    throw new IllegalArgumentException("Fragment position item not found");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) { }
    };
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.nav_explore:
                    mBinding.navViewPager.setCurrentItem(0);
                    return true;
                case R.id.nav_saved:
                    mBinding.navViewPager.setCurrentItem(1);
                    return true;
            }
            return false;
        }
    };

    private static class NavPagerAdapter extends FragmentPagerAdapter {
        private final int NUM_DRAWER_PAGER_ITEMS = 2;

        public NavPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return ExploreFragment.newInstance();
                case 1:
                    return SavedFragment.newInstance(2);
                default:
                    throw new IllegalArgumentException("Fragment position item not found");
            }
        }

        @Override
        public int getCount() {
            return NUM_DRAWER_PAGER_ITEMS;
        }
    }
}
