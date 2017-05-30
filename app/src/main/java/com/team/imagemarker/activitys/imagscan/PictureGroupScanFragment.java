package com.team.imagemarker.activitys.imagscan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.imgscan.PictureGroupAdapter;
import com.team.imagemarker.entitys.Constants;
import com.team.imagemarker.entitys.PictureGroupModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/4/15.
 * email 1434117404@qq.com
 */

public class PictureGroupScanFragment extends Fragment implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener, PictureGroupAdapter.OnItemActionListener{
    private View view;
    private static final String ARG_TITLE = "title";
    private String mTitle;
//    private TextView title;
//    private ImageView leftIcon, rightIcon;
//    private RelativeLayout titleBar;

    private SwipeRefreshLayout refreshLayout;
    private FloatingActionButton toTop;

    private RecyclerView recyclerView;
    private List<PictureGroupModel> list = new ArrayList<PictureGroupModel>();
    private PictureGroupAdapter adapter;

    public static PictureGroupScanFragment getInstance(String title){
        PictureGroupScanFragment fragment = new PictureGroupScanFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_picture_group_scan, null);
//        titleBar = (RelativeLayout) view.findViewById(R.id.title_bar);
//        title = (TextView) view.findViewById(R.id.title);
//        leftIcon = (ImageView) view.findViewById(R.id.left_icon);
//        rightIcon = (ImageView) view.findViewById(R.id.right_icon);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.picture_group_refreshlayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.picture_group_recycle);
        toTop = (FloatingActionButton) view.findViewById(R.id.to_top);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();//设置数据
    }

    /**
     * 设置数据
     */
    private void setData() {
//        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
//        title.setText("图组选择");
//        rightIcon.setVisibility(View.GONE);
//        leftIcon.setOnClickListener(this);

        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
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

        adapter = new PictureGroupAdapter(getContext(), list);
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
        Toast.makeText(getContext(), "这是第" + position + "组图片", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), LookPictureActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}