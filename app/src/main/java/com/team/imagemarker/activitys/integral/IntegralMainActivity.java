package com.team.imagemarker.activitys.integral;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.StepArcView;

import java.util.ArrayList;
import java.util.List;

public class IntegralMainActivity extends FragmentActivity implements TabLayout.OnTabSelectedListener, ShoppingFragment.CallBack{
    private StepArcView cc;

    private TabLayout loginTabs;
    private ViewPager loginViewPager;

    private TabViewPagerAdapter viewPagerAdapter;
    private ImageView leftIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_integral);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        cc = (StepArcView) findViewById(R.id.cc);
        cc.setCurrentCount(270, 30);

        //初始化控件
        loginTabs = (TabLayout) findViewById(R.id.login_tabs);
        loginViewPager = (ViewPager) findViewById(R.id.task_viewpager);
        setTabViewPager();

        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntegralMainActivity.this.finish();
                IntegralMainActivity.this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });
    }

    private void setTabViewPager() {
        viewPagerAdapter = new TabViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new ShoppingFragment(), "积分商城");
        viewPagerAdapter.addFragment(new RankFragment(), "积分排行");
        loginViewPager.setAdapter(viewPagerAdapter);
        loginTabs.setupWithViewPager(loginViewPager);
        //为TabLayout添加点击事件
        loginTabs.setOnTabSelectedListener(this);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int location = tab.getPosition();
        loginViewPager.setCurrentItem(location);
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    @Override
    public void setData(int count) {
        cc.setCurrentCount(270, Integer.parseInt(cc.getCurrentCounts()) - count);
    }


    class TabViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public TabViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }



}
