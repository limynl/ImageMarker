package com.team.imagemarker.activitys.integral;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.ShoppingAdapter;
import com.team.imagemarker.adapters.UserIntegralAdapter;
import com.team.imagemarker.adapters.task.TaskBoxAdapter;
import com.team.imagemarker.entitys.UserIntegralModel;
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

    private ImageView luckDraw;
    private ImageView chouJiang;

    private View integralView;//积分排行
    private PopupWindow popupWindow;
    private ListView listView;
    private UserIntegralAdapter adapter;
    private List<UserIntegralModel> integralList = new ArrayList<>();

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
        luckDraw = (ImageView) findViewById(R.id.luck_draw);
        chouJiang = (ImageView) findViewById(R.id.choujiang);

        luckDraw.setOnClickListener(this);
        chouJiang.setOnClickListener(this);
        setTaskBox();
        setIntegral();
    }

    private void bindView() {
        getDataFromNet();
        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(this, list);
        shopping = (RecyclerView) findViewById(R.id.shopping);
        shopping.setLayoutManager(new GridLayoutManager(this,2));
        shopping.setAdapter(shoppingAdapter);
    }

    private void setTaskBox() {
        systemPushList.add(new CategoryModel(R.mipmap.task2, R.mipmap.shopping1, "第一名"));
        systemPushList.add(new CategoryModel(R.mipmap.task1, R.mipmap.shopping2, "第二名"));
        systemPushList.add(new CategoryModel(R.mipmap.task3, R.mipmap.shopping3, "第三名"));
        adapterSystem = new TaskBoxAdapter(this, systemPushList);
        taskBox.setAdapter(adapterSystem);
        taskBox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(IntegralActivity.this, "position:" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setIntegral() {
        integralView = LayoutInflater.from(this).inflate(R.layout.user_integral_ranking_layout, null);
        listView = (ListView) integralView.findViewById(R.id.user_integral_list);
        integralList.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img1.jpg", "Limynl", "100"));
        integralList.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img2.jpg", "Limynl", "90"));
        integralList.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img3.jpg", "Limynl", "80"));
        integralList.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img4.jpg", "Limynl", "70"));
        integralList.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img1.jpg", "Limynl", "60"));
        integralList.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img2.jpg", "Limynl", "50"));
        integralList.add(new UserIntegralModel("http://139.199.23.142:8080/TestShowMessage1/marker/banner/img3.jpg", "Limynl", "40"));
        adapter = new UserIntegralAdapter(this, integralList);
        listView.setAdapter(adapter);
    }

    public void showCenterPopupWindow(View view) {
        popupWindow = new PopupWindow(integralView, LinearLayout.LayoutParams.WRAP_CONTENT, 900);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.4f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {// 当PopupWindow消失时,恢复其为原来的颜色
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);//设置PopupWindow进入和退出动画
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);// 设置PopupWindow显示在中间
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.luck_draw:{
                showCenterPopupWindow(v);
            }
            break;
            case R.id.choujiang:{
                Intent intent = new Intent(this, LuckDrawActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}