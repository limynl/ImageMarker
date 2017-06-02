package com.team.imagemarker.activitys.integral;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.ShoppingAdapter;
import com.team.imagemarker.adapters.task.TaskBoxAdapter;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * 积分商城
 * Created by Lmy on 2017/6/1.
 * email 1434117404@qq.com
 */

public class IntegralActivity extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon,rightIcon;
    private RelativeLayout titleBar;

    private RecyclerView shopping;
    private List<CategoryModel> list;

    private MyGridView taskBox;
    private List<CategoryModel> systemPushList = new ArrayList<>();
    private TaskBoxAdapter adapterSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_integral);

        bindView();

        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        title.setText("积分商城");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);

        taskBox = (MyGridView) findViewById(R.id.task_box);
        taskBox = (MyGridView) findViewById(R.id.task_box);
        setTaskBox();

    }

    private void bindView() {
        getDataFromNet();
        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(this, list);
        shopping = (RecyclerView) findViewById(R.id.shopping);
        shopping.setLayoutManager(new GridLayoutManager(this,2));
        shopping.setAdapter(shoppingAdapter);
    }

    private void setTaskBox() {
        systemPushList.add(new CategoryModel(R.mipmap.task1, "这是标题", "这是简要信息"));
        systemPushList.add(new CategoryModel(R.mipmap.task2, "这是标题", "这是简要信息"));
        systemPushList.add(new CategoryModel(R.mipmap.task3, "这是标题", "这是简要信息"));
        adapterSystem = new TaskBoxAdapter(this, systemPushList);
        taskBox.setAdapter(adapterSystem);
        taskBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(IntegralActivity.this, "position:" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            break;
        }
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
}