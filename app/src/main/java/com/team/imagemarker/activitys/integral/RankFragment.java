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
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img1.jpg", "Limynl", "100"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img2.jpg", "Limynl", "90"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img3.jpg", "Limynl", "80"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img4.jpg", "Limynl", "70"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img1.jpg", "Limynl", "60"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img2.jpg", "Limynl", "50"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img3.jpg", "Limynl", "40"));
        adapter = new UserIntegralAdapter(getContext(), list);
        listView.setAdapter(adapter);
    }
}
