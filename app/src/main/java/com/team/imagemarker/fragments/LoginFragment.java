package com.team.imagemarker.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.imagemarker.R;
import com.team.imagemarker.activitys.HomeActivity;
import com.team.imagemarker.utils.PaperButton;


/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class LoginFragment extends Fragment {
    private PaperButton button;
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        button = (PaperButton) view.findViewById(R.id.bt_login);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
            }
        });
    }
}
