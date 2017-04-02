package com.team.imagemarker.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.team.imagemarker.R;


/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class RegisterFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d("FragmentRegist", "注册界面创建了");
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register, null);
        return view;
    }
}
