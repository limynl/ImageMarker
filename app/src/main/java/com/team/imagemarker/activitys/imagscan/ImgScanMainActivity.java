package com.team.imagemarker.activitys.imagscan;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.view.CoordinatorTabLayout;

import java.util.ArrayList;

/**
 * Created by Lmy on 2017/5/27.
 * email 1434117404@qq.com
 */

public class ImgScanMainActivity extends AppCompatActivity {
    private CoordinatorTabLayout mCoordinatorTabLayout;
    private int[] mImageArray, mColorArray;
    private ArrayList<Fragment> mFragments;
    private final String[] mTitles = {"每日推荐", "猜你喜欢", "领域推送", "其他"};
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_img_scan_main);
        initFragments();
        initViewPager();
        mImageArray = new int[]{R.mipmap.scan1,R.mipmap.scan2,R.mipmap.scan3,R.mipmap.scan4};
        mColorArray = new int[]{android.R.color.holo_blue_light,android.R.color.holo_red_light,android.R.color.holo_orange_light,android.R.color.holo_green_light};

        mCoordinatorTabLayout = (CoordinatorTabLayout) findViewById(R.id.coordinatortablayout);
        mCoordinatorTabLayout.setTitle("美图浏览")
                .setBackEnable(true)
                .setImageArray(mImageArray, mColorArray)
                .setupWithViewPager(mViewPager);
    }

    private void initFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(DayRecommendFragment.getInstance(mTitles[0]));
        mFragments.add(PictureGroupScanFragment.getInstance(mTitles[1]));
        mFragments.add(DayRecommendFragment.getInstance(mTitles[2]));
        mFragments.add(DayRecommendFragment.getInstance(mTitles[3]));
    }

    private void initViewPager() {
        mViewPager = (ViewPager) findViewById(R.id.img_scan);
        mViewPager.setOffscreenPageLimit(2);
        mViewPager.setAdapter(new ImgScanPagerAdapter(getSupportFragmentManager(), mFragments, mTitles));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}