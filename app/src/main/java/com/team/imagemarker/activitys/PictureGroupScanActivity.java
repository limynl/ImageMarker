package com.team.imagemarker.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.PictureGroupAdapter;
import com.team.imagemarker.entitys.Constants;
import com.team.imagemarker.entitys.PictureGroupModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/4/15.
 * email 1434117404@qq.com
 */

public class PictureGroupScanActivity extends Activity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, PictureGroupAdapter.OnItemActionListener{
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton toTop;

    private RecyclerView recyclerView;
    private List<PictureGroupModel> list = new ArrayList<PictureGroupModel>();
    private PictureGroupAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_group_scan);
        bindView();//初始化控件
        setData();//设置数据
    }

    /**
     * 初始化控件
     */
    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.picture_group_refreshlayout);
        recyclerView = (RecyclerView) findViewById(R.id.picture_group_recycle);
        toTop = (FloatingActionButton) findViewById(R.id.to_top);
    }

    /**
     * 设置数据
     */
    private void setData() {
        titleBar.setBackgroundColor(getResources().getColor(R.color.titleBar1));
        title.setText("图组选择");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);

        refreshLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorAccent);
//        refreshLayout.post(new Runnable() {//进入界面首先进行加载
//            @Override
//            public void run() {
//                refreshLayout.setRefreshing(true);
//            }
//        });

        //测试数据
        int size = Constants.name.length;
        for (int i = 0; i < size; i++) {
            list.add(new PictureGroupModel(Constants.imageURL[i], Constants.name[i]));
        }

        adapter = new PictureGroupAdapter(this, list);
        recyclerView.setHasFixedSize(true);//当确定数据的变化不会影响RecycleView布局的大小时，设置该属性提高性能
        recyclerView.setAdapter(adapter);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);//防止Item切换、闪烁、跳页现象
        recyclerView.setLayoutManager(layoutManager);//使用不规则的网格布局，实现瀑布流效果

        refreshLayout.setOnRefreshListener(this);
        adapter.setOnItemActionListener(this);
        toTop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{//返回
                PictureGroupScanActivity.this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            break;
            case R.id.to_top:{//回到顶部
                recyclerView.smoothScrollToPosition(0);
            }
            break;
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
                list.clear();//清空数据
                int size = Constants.name.length;
                for (int i = 0; i < size; i++) {//重新加载数据
                    list.add(new PictureGroupModel(Constants.imageURL[i], Constants.name[i]));
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);//刷新完成
            }
        }, 4000);
    }

    /**
     * recycleView中条目点击事件
     * @param view 操作的视图
     * @param position 数据的位置
     */
    @Override
    public void OnItemClickListener(View view, int position) {
        Toast.makeText(PictureGroupScanActivity.this, "这是第" + position + "组图片", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(PictureGroupScanActivity.this, DetailPictureActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}