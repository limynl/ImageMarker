package com.team.imagemarker.activitys.home;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.home.MoreCateGoryAdaper;
import com.team.imagemarker.entitys.home.CateGoryInfo;
import com.team.imagemarker.entitys.home.SelectCategoryModel;

import java.util.ArrayList;
import java.util.List;

public class MoreCategoryActivity extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView moreCategory;
    private List<SelectCategoryModel> list = new ArrayList<>();
    private MoreCateGoryAdaper adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_category);
        bindView();
        setData();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.more_category_refresh);
        moreCategory = (RecyclerView) findViewById(R.id.more_category_recycleview);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title.setText("更多");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);

        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setOnRefreshListener(this);
    }

    private void setData() {
        List<CateGoryInfo> itemList = new ArrayList<>();
        itemList.add(new CateGoryInfo("种类一", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner1.jpg"));
        itemList.add(new CateGoryInfo("种类二", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner2.jpg"));
        itemList.add(new CateGoryInfo("种类三", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner3.jpg"));
        itemList.add(new CateGoryInfo("种类四", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner4.jpg"));
        itemList.add(new CateGoryInfo("种类五", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner5.jpg"));
        itemList.add(new CateGoryInfo("种类一", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner1.jpg"));
        itemList.add(new CateGoryInfo("种类二", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner2.jpg"));
        itemList.add(new CateGoryInfo("种类三", "http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner3.jpg"));
        list.add(new SelectCategoryModel("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner1.jpg", "这是一句描述，什么都行", itemList));
        list.add(new SelectCategoryModel("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner2.jpg", "这是一句描述，什么都行", itemList));
        list.add(new SelectCategoryModel("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner3.jpg", "这是一句描述，什么都行", itemList));
        list.add(new SelectCategoryModel("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner4.jpg", "这是一句描述，什么都行", itemList));
        list.add(new SelectCategoryModel("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner5.jpg", "这是一句描述，什么都行", itemList));
        adapter = new MoreCateGoryAdaper(this, list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        moreCategory.setLayoutManager(layoutManager);
        moreCategory.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        moreCategory.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);//刷新完成
            }
        }, 4000);
    }
}