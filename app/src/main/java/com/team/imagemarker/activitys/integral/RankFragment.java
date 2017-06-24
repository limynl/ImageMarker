package com.team.imagemarker.activitys.integral;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.UserIntegralAdapter;
import com.team.imagemarker.entitys.UserIntegralModel;

import java.util.ArrayList;
import java.util.List;

import static com.tencent.open.utils.Global.getContext;

/**
 * Created by Lmy on 2017/6/20.
 * email 1434117404@qq.com
 */

public class RankFragment extends Fragment {
    private View view;
    private ListView listView;
    private UserIntegralAdapter adapter;
    private List<UserIntegralModel> list = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_rank, null);
        listView = (ListView) view.findViewById(R.id.user_integral_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.add(new UserIntegralModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/手机-桌子.jpg", "Rose", "100"));
        list.add(new UserIntegralModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-桌子-鼠标-手机.jpg", "Jack", "1000"));
        list.add(new UserIntegralModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-椅子-眼睛-桌子.jpg", "Tom", "90"));
        list.add(new UserIntegralModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-水杯-桌子-手机.jpg", "Json", "80"));
        list.add(new UserIntegralModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-计算机-树木-草坪.jpg", "Gson", "70"));
        list.add(new UserIntegralModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-22/矿用挖掘机-天空-机器.jpg", "Lisi", "60"));
        list.add(new UserIntegralModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/键盘-鼠标-桌子.jpg", "Zhhangsan", "50"));
        list.add(new UserIntegralModel("http://obs.myhwclouds.com/look.admin.image/腾讯/2017-5-23/火车-天空-夕阳-.jpg", "G.qz", "40"));
        adapter = new UserIntegralAdapter(getContext(), list);
        listView.setAdapter(adapter);
    }
}
