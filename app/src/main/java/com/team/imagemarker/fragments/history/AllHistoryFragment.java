package com.team.imagemarker.fragments.history;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.history.ShowHistoryAdapter;
import com.team.imagemarker.bases.btnClickListener;
import com.team.imagemarker.entitys.HistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 所有历史记录
 * Created by Lmy on 2017/4/29.
 * email 1434117404@qq.com
 */

public class AllHistoryFragment extends Fragment implements btnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private ListView listView;

    private List<HistoryModel> list = new ArrayList<>();
    private ShowHistoryAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_all_history, null);
        listView = (ListView) view.findViewById(R.id.all_record_liseview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.all_record_fresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAllRecordData();//得到所有历史记录的数据
    }

    private void getAllRecordData() {
        for (int i = 0; i < 2; i++) {
            //未完成
            list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car1.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 0));
        }
        for (int i = 0; i < 2; i++) {
            //已完成
            list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car1.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 1));
        }
        setData();
    }

    private void setData() {
        adapter = new ShowHistoryAdapter(getActivity(), list, 0);//0表示所有记录
        listView.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        list.clear();
        adapter.notifyDataSetChanged();
    }

    /**
     * 继续操作
     * @param position 点击的位置
     */
    @Override
    public void btnEditClick(int position) {
        Toast.makeText(getActivity(), "继续操作", Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除记录
     * @param position 点击的位置
     */
    @Override
    public void btnDeleteClick(int position) {
        Toast.makeText(getActivity(), "删除记录", Toast.LENGTH_SHORT).show();
        list.remove(position);
        adapter.notifyDataSetChanged();
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
                int size = 3;
                for (int i = 0; i < 3; i++) {//重新加载数据
                    list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car1.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 0));
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);//刷新完成
            }
        }, 3000);
    }
}
