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
        datas.add(new DayRecommendModel(R.mipmap.scan1, "图片标签"));
        datas.add(new DayRecommendModel(R.mipmap.scan2, "图片标签"));
        datas.add(new DayRecommendModel(R.mipmap.scan3, "图片标签"));
        adapter = new DayRecommendAdapter(getContext(), datas);
        listView.setAdapter(adapter);
    }

}