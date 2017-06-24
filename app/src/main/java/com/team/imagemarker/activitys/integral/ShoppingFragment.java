package com.team.imagemarker.activitys.integral;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.ShoppingAdapter;
import com.team.imagemarker.adapters.task.TaskBoxAdapter;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.fragments.history.Wite;
import com.team.imagemarker.utils.MyGridView;
import com.team.loading.SweetAlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * Created by Lmy on 2017/6/20.
 * email 1434117404@qq.com
 */

public class ShoppingFragment extends Fragment {
    private View view;
    private CallBack callBack;

    private RecyclerView shopping;
    private List<CategoryModel> list;
//    private TextView integral;

    private MyGridView taskBox;
    private List<CategoryModel> systemPushList = new ArrayList<>();
    private TaskBoxAdapter adapterSystem;

    private FloatingActionButton luck;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        callBack = (CallBack) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_shopping, null);
        taskBox = (MyGridView) view.findViewById(R.id.task_box);
        luck = (FloatingActionButton) view.findViewById(R.id.luck);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        bindView();
        setTaskBox();
        luck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LuckDrawActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    private void setTaskBox() {
        systemPushList.add(new CategoryModel(R.mipmap.task2, R.mipmap.shopping3, "第一名"));
        systemPushList.add(new CategoryModel(R.mipmap.task1, R.mipmap.shopping2, "第二名"));
        systemPushList.add(new CategoryModel(R.mipmap.task3, R.mipmap.shopping1, "第三名"));
        adapterSystem = new TaskBoxAdapter(getContext(), systemPushList);
        taskBox.setAdapter(adapterSystem);
        taskBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void bindView() {
        getDataFromNet();
        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(getContext(), list);
        shopping = (RecyclerView) view.findViewById(R.id.shopping);
        shopping.setLayoutManager(new GridLayoutManager(getContext(),2));
        shopping.setAdapter(shoppingAdapter);
        shoppingAdapter.setOnItemClickListner(new ShoppingAdapter.ItemClickListener() {
            @Override
            public void onItemClick(final View view) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("积分兑换")
                        .setContentText("该件商品将消耗10积分，确认兑换吗？")
                        .setCancelText("确 认")
                        .setConfirmText("取 消")
                        .showConfirmButton(true)
                        .showCancelButton(true)
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.setTitleText("兑换成功!")
                                        .setContentText("稍后我们将与您取得联系!")
                                        .showConfirmButton(false)
                                        .showCancelButton(false)
                                        .setCancelClickListener(null)
                                        .setConfirmClickListener(null)
                                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                Timer timer=new Timer();
                                timer.schedule(new Wite(sDialog), 3000);
//                                integral.setText((Integer.parseInt(integral.getText().toString()) - 10) + "");
                                callBack.setData(10);
                                ((TextView) view).setText((Integer.parseInt(((TextView) view).getText().toString().substring(0, 1)) + 1) + "人已兑换");
                                ((TextView) view).setTextColor(Color.parseColor("#FF5C5D"));
                            }
                        })
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismiss();
                            }
                        })
                        .show();
            }
        });
    }

    private void getDataFromNet(){
        list = new ArrayList<>();
        list.add(new CategoryModel(R.mipmap.shopping1, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.shopping2, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.shopping3, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.shopping4, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.shopping5, "this is a taxt", "5人已兑换"));
        list.add(new CategoryModel(R.mipmap.shopping6, "this is a taxt", "5人已兑换"));
    }

    public interface CallBack{
        public void setData(int count);
    }




}
