package com.team.imagemarker.activitys.imagscan;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class PictureGroupScanFragment extends Fragment implements View.OnClickListener, PictureGroupAdapter.OnItemActionListener{
    private View view;
    private static final String ARG_TITLE = "title";
    private String mTitle;
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
     * recycleView中条目点击事件
     * @param view 操作的视图
     * @param position 数据的位置
     */
    @Override
    public void OnItemClickListener(View view, int position) {
        Intent intent = new Intent(getContext(), LookPictureActivity.class);
        startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

}