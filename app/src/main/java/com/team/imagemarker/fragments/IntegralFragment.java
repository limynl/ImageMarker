package com.team.imagemarker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.UserIntegralAdapter;
import com.team.imagemarker.entity.UserIntegralModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分排行
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class IntegralFragment extends Fragment {
    private View view;
    private ListView listView;
    private UserIntegralAdapter adapter;
    private List<UserIntegralModel> list = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_integral, null);
        listView = (ListView) view.findViewById(R.id.user_integral_list);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/test.png", "Limynl", "100"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/test.png", "Limynl", "90"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/test.png", "Limynl", "80"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/test.png", "Limynl", "70"));
        list.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/test.png", "Limynl", "60"));
        adapter = new UserIntegralAdapter(getActivity(), list);
        listView.setAdapter(adapter);
    }
}