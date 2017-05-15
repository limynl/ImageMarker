package com.team.imagemarker.activitys.home;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import com.luseen.spacenavigation.SpaceItem;
import com.luseen.spacenavigation.SpaceNavigationView;
import com.luseen.spacenavigation.SpaceOnClickListener;
import com.team.imagemarker.R;
import com.team.imagemarker.fragments.home.FirstPageFragment;
import com.team.imagemarker.fragments.home.ImageNavFragment;
import com.team.imagemarker.fragments.home.UserCenterFragment;


/**
 * 主界面
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class HomeActivity extends FragmentActivity{
    private String[] tabNames = {"图组导航", "个人中心"};
    private int[] tabIcons = {R.mipmap.weixiushang_icon, R.mipmap.peijianshang_icon};
    private SpaceNavigationView tabs;
    private ImageNavFragment imageNavFragment;//图组导航
    private UserCenterFragment userCenterFragment;//个人中心
    private FirstPageFragment firstPageFragment;//首页
    private FragmentManager fragmentManager;
    private final int CONTENT_ID = R.id.main_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTabs();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        tabs.onSaveInstanceState(outState);
    }

    /**
     * 设置导航栏
     */
    private void setTabs() {
        tabs = (SpaceNavigationView) findViewById(R.id.main_tab);

        //初次加载首页
        fragmentManager = getSupportFragmentManager();
        firstPageFragment = new FirstPageFragment();
        fragmentManager.beginTransaction().add(CONTENT_ID, firstPageFragment).commit();
        for (int i = 0; i < tabNames.length; i++) {
            tabs.addSpaceItem(new SpaceItem(tabNames[i], tabIcons[i]));
        }
        tabs.showIconOnly();//设置只显示图片
        tabs.setSpaceOnClickListener(new SpaceOnClickListener() {
            @Override
            public void onCentreButtonClick() {
                gotoCenterFragment();
            }

            @Override
            public void onItemClick(int itemIndex, String itemName) {
                gotoOtherFragment(itemName);
            }

            @Override
            public void onItemReselected(int itemIndex, String itemName) {
                gotoOtherFragment(itemName);
            }
        });

    }

    /**
     * 进入中间的订单中心
     */
    private void gotoCenterFragment() {
        hideFragment();
        if (firstPageFragment == null) {
            firstPageFragment = new FirstPageFragment();
            fragmentManager.beginTransaction().add(CONTENT_ID, firstPageFragment).commit();
        } else {
            fragmentManager.beginTransaction().show(firstPageFragment).commit();
        }
    }

    /**
     * 点击tab显示相应的界面
     * @param itemName
     */
    private void gotoOtherFragment(String itemName) {
        hideFragment();
        switch (itemName) {
            case "图组导航":
                if (imageNavFragment == null) {
                    imageNavFragment = new ImageNavFragment();
                    fragmentManager.beginTransaction().add(CONTENT_ID, imageNavFragment).commit();
                } else {
                    fragmentManager.beginTransaction().show(imageNavFragment).commit();
                }
                break;
            case "个人中心":
                if (userCenterFragment == null) {
                    userCenterFragment = new UserCenterFragment();
                    fragmentManager.beginTransaction().add(CONTENT_ID, userCenterFragment).commit();
                } else {
                    fragmentManager.beginTransaction().show(userCenterFragment).commit();
                }
                break;
            default:
                break;
        }
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideFragment() {
        if (firstPageFragment != null) {
            fragmentManager.beginTransaction().hide(firstPageFragment).commit();
        }
        if (userCenterFragment != null) {
            fragmentManager.beginTransaction().hide(userCenterFragment).commit();
        }
        if (imageNavFragment != null) {
            fragmentManager.beginTransaction().hide(imageNavFragment).commit();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        HomeActivity.this.finish();
    }
}