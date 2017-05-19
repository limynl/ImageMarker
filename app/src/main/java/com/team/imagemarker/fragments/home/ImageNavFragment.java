package com.team.imagemarker.fragments.home;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.activitys.home.MoreCategoryActivity;
import com.team.imagemarker.activitys.user.UserSearchActivity;
import com.team.imagemarker.adapters.imgnav.GridViewLikeAdapter;
import com.team.imagemarker.adapters.imgnav.HotCategroyAdapter;
import com.team.imagemarker.adapters.imgnav.LikeViewPagerAdapter;
import com.team.imagemarker.adapters.imgnav.SelectCateGoryAdapter;
import com.team.imagemarker.entitys.home.CateGoryInfo;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.entitys.home.SelectCategoryModel;
import com.team.imagemarker.utils.EditTextWithDel;
import com.team.imagemarker.utils.MyGridView;
import com.team.imagemarker.utils.MyScrollView;
import com.team.imagemarker.viewpager.navbanner.BannerLayout;
import com.team.imagemarker.viewpager.navbanner.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/4/28.
 * email 1434117404@qq.com
 */

public class ImageNavFragment extends Fragment implements View.OnClickListener, MyScrollView.ScrollViewListener, SwipeRefreshLayout.OnRefreshListener, AdapterView.OnItemClickListener{
    private View view;

    //搜索框与banner
    private SwipeRefreshLayout refreshLayout;
    private int bannerHeight;//banner高度
    private MyScrollView navScrollView;
    private BannerLayout topBanner;
    private LinearLayout titleBarRoot;
    private RelativeLayout titleBar;
    private EditTextWithDel userSearch;

    //热门分类
    private MyGridView hotCategory;
    private List<CategoryModel> hotList = new ArrayList<>();
    private HotCategroyAdapter hotCategroyAdapter;

    //猜你喜欢
    private ViewPager likeViewPager;
    private LinearLayout dot;//指示器
    private List<View> pagerList;
    private List<CategoryModel> likeList = new ArrayList<>();
    private LayoutInflater inflater;
    private int pageCount;//总页数
    private int pageSize = 4;//每页显示的条目数
    private int currentIndex = 0;//显示当前页数

    //精选种类
    private ListView selectListView;
    private List<SelectCategoryModel> selectCategoryList;
    private SelectCateGoryAdapter selectCateGoryAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_image_nav, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.img_nav_refresh);
        topBanner = (BannerLayout) view.findViewById(R.id.top_banner);
        navScrollView = (MyScrollView) view.findViewById(R.id.nav_scrollview);
        titleBarRoot = (LinearLayout) view.findViewById(R.id.search_bar_root);
        titleBar = (RelativeLayout) view.findViewById(R.id.search_bar);
        userSearch = (EditTextWithDel) view.findViewById(R.id.user_search);

        hotCategory = (MyGridView) view.findViewById(R.id.hot_category);

        likeViewPager = (ViewPager) view.findViewById(R.id.like_viewpager);
        dot = (LinearLayout) view.findViewById(R.id.viewpager_indicator);

        selectListView = (ListView) view.findViewById(R.id.select_category);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //初始化刷新控件
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setProgressViewOffset(false,0,150);
        refreshLayout.setOnRefreshListener(this);

        titleBarRoot.setGravity(Gravity.CENTER);
        userSearch.setEnabled(false);
        titleBarRoot.setOnClickListener(this);
        setTopBanner();//viewpager滚动
        setHotCateGroy();//热门分类
        setLikeViewpager();//猜你喜欢
        setSelectCategory();//精选分类
    }

    /**
     * 设置顶部banner
     */
    private void setTopBanner() {
        List<String> bannerList = new ArrayList<>();
        bannerList.add("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner1.jpg");
        bannerList.add("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner2.jpg");
        bannerList.add("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner3.jpg");
        bannerList.add("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner4.jpg");
        bannerList.add("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner5.jpg");
        topBanner.setImageLoader(new GlideImageLoader());
        topBanner.setViewUrls(bannerList);
        topBanner.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(getActivity(), "这是第" + (position + 1) +"张图片", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(), MoreCategoryActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        //获取顶部图片高度后，获取滚动监听
        ViewTreeObserver treeObserver = topBanner.getViewTreeObserver();
        treeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                topBanner.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                bannerHeight = topBanner.getHeight();
                navScrollView.setScrollViewListener(ImageNavFragment.this);

            }
        });
    }

    private void setHotCateGroy() {
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg1, R.mipmap.system_push1, "搞笑"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg2, R.mipmap.system_push2, "摄影"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg3, R.mipmap.system_push3, "萌控"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg4, R.mipmap.system_push4, "动漫"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg5, R.mipmap.system_push5, "明星"));
        hotList.add(new CategoryModel(R.mipmap.hot_categroy_bg6, R.mipmap.system_push1, "美食"));

        hotCategroyAdapter = new HotCategroyAdapter(getActivity(), hotList);
        hotCategory.setAdapter(hotCategroyAdapter);
    }

    private void setLikeViewpager() {
        likeList.add(new CategoryModel(R.mipmap.system_push1, "这是标题一", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push2, "这是标题二", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push2, "这是标题三", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push3, "这是标题四", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push1, "这是标题一", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push2, "这是标题二", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push2, "这是标题三", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push3, "这是标题四", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push1, "这是标题一", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push2, "这是标题二", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push2, "这是标题三", "这是简要信息"));
        likeList.add(new CategoryModel(R.mipmap.system_push3, "这是标题四", "这是简要信息"));
        inflater = LayoutInflater.from(getContext());
        pageCount = (int) Math.ceil(likeList.size() * 1.0 / pageSize);//计算总页数
        pagerList = new ArrayList<>();
        for (int i = 0; i < pageCount; i++) {
            GridView gridView = (GridView) inflater.inflate(R.layout.like_gridview, likeViewPager, false);
            gridView.setAdapter(new GridViewLikeAdapter(getContext(), likeList, i, pageSize));
            pagerList.add(gridView);

            //添加点击事件
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    int pos = position + currentIndex * pageSize;
                    Toast.makeText(getContext(), "pos:" + pos, Toast.LENGTH_SHORT).show();
                }
            });
        }
        likeViewPager.setAdapter(new LikeViewPagerAdapter(pagerList));
        //设置指示器
        setOvalLayout();
    }

    private void setSelectCategory() {
        selectCategoryList = new ArrayList<SelectCategoryModel>();
        List<CateGoryInfo> itemList = new ArrayList<>();
        itemList.add(new CateGoryInfo("标题一", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner1.jpg"));
        itemList.add(new CateGoryInfo("标题二", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner2.jpg"));
        itemList.add(new CateGoryInfo("标题三", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner3.jpg"));
        itemList.add(new CateGoryInfo("标题四", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner4.jpg"));
        itemList.add(new CateGoryInfo("标题五", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner5.jpg"));
        itemList.add(new CateGoryInfo("标题六", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner1.jpg"));
        itemList.add(new CateGoryInfo("标题七", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner2.jpg"));
        selectCategoryList.add(new SelectCategoryModel("主标题一", itemList));
        selectCategoryList.add(new SelectCategoryModel("主标题二", itemList));
        selectCategoryList.add(new SelectCategoryModel("主标题三", itemList));
        selectCateGoryAdapter = new SelectCateGoryAdapter(getContext(), selectCategoryList);
        selectListView.setAdapter(selectCateGoryAdapter);
    }

    /**
     * 设置指示器(猜你喜欢)
     */
    private void setOvalLayout() {
        for (int i = 0; i < pageCount; i++) {
            dot.addView(inflater.inflate(R.layout.dot, null));
        }
        dot.getChildAt(0).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
        likeViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // 取消圆点选中
                dot.getChildAt(currentIndex).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_normal);
                // 圆点选中
                dot.getChildAt(position).findViewById(R.id.v_dot).setBackgroundResource(R.drawable.dot_selected);
                currentIndex = position;
            }

            @Override
            public void onPageSelected(int position) {}

            @Override
            public void onPageScrollStateChanged(int state) {}
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_bar_root:{
                Toast.makeText(getActivity(), "兴趣搜索界面", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), UserSearchActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }

    /**
     * 滑动监听
     */
    @Override
    public void onScrollChanged(MyScrollView scrollView, int x, int y, int oldx, int oldy) {
        if (y <= 0) {   //设置标题的背景颜色
            titleBar.setBackgroundColor(Color.argb((int) 0, 51,56,80));
        } else if (y > 0 && y <= bannerHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
            float scale = (float) y / bannerHeight;
            float alpha = (255 * scale);
            titleBar.setBackgroundColor(Color.argb((int) alpha, 51,56,80));
        } else {    //滑动到banner下面设置普通颜色
            titleBar.setBackgroundColor(Color.argb((int) 255, 51,56,80));
        }
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();
                refreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    /**
     * GridView点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}