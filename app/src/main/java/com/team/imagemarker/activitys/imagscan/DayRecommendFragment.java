package com.team.imagemarker.activitys.imagscan;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.imgscan.DayRecommendAdapter;
import com.team.imagemarker.entitys.imgscan.DayRecommendModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/5/27.
 * email 1434117404@qq.com
 */

public class DayRecommendFragment extends Fragment {
    private static final String ARG_TITLE = "title";
    private String mTitle;
    private ListView listView;
    private DayRecommendAdapter adapter;
    private List<DayRecommendModel> datas;

    public static DayRecommendFragment getInstance(String title) {
        DayRecommendFragment fra = new DayRecommendFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_TITLE, title);
        fra.setArguments(bundle);
        return fra;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mTitle = bundle.getString(ARG_TITLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_day_recommend, container, false);
        listView = (ListView) v.findViewById(R.id.day_recommend);
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
    }

    private void setData() {
        datas = new ArrayList<>();
        datas.add(new DayRecommendModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-计算机-树木-草坪.jpg", "草坪，笔记本电脑"));
        datas.add(new DayRecommendModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-水杯-桌子-手机.jpg", "笔记本电脑"));
        datas.add(new DayRecommendModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-椅子-眼睛-桌子.jpg", "笔记本电脑，眼镜，桌子"));
        datas.add(new DayRecommendModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-桌子-鼠标-手机.jpg", "鼠标，笔记本电脑，桌子"));
        datas.add(new DayRecommendModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/键盘-鼠标-桌子.jpg", "键盘"));
        adapter = new DayRecommendAdapter(getContext(), datas);
        listView.setAdapter(adapter);
    }

}