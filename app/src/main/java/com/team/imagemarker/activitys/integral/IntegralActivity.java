package com.team.imagemarker.activitys.integral;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.ShoppingAdapter;
import com.team.imagemarker.entitys.home.CategoryModel;

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

    }

    private void bindView() {
        getDataFromNet();
        ShoppingAdapter shoppingAdapter = new ShoppingAdapter(this, list);
        shopping = (RecyclerView) findViewById(R.id.shopping);
        shopping.setLayoutManager(new GridLayoutManager(this,2));
        shopping.setAdapter(shoppingAdapter);
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