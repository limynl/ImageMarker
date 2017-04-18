package com.team.imagemarker.activitys;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.PictureGroupAdapter;
import com.team.imagemarker.entitys.Constants;
import com.team.imagemarker.entitys.PictureGroupModel;

import java.util.ArrayList;
import java.util.List;

public class PictureGroupScanActivity extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private RecyclerView recyclerView;
    private List<PictureGroupModel> list = new ArrayList<PictureGroupModel>();
    private PictureGroupAdapter adapter;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_group_scan);
        bindView();//初始化控件
        setData();//设置数据
    }

    /**
     * 初始化控件
     */
    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        recyclerView = (RecyclerView) findViewById(R.id.picture_group_recycle);
    }

    /**
     * 设置数据
     */
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setData() {
        titleBar.setElevation(24);
        title.setText("图组选择");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);

        //测试数据
        int size = Constants.name.length;
        for (int i = 0; i < size; i++) {
            list.add(new PictureGroupModel(Constants.imageURL[i], Constants.name[i]));
        }
        adapter = new PictureGroupAdapter(this, list);
        recyclerView.setHasFixedSize(true);//当确定数据的变化不会影响RecycleView布局的大小时，设置该属性提高性能
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));//使用不规则的网格布局，实现瀑布流效果
        adapter.setOnItemActionListener(new PictureGroupAdapter.OnItemActionListener() {
            @Override
            public void OnItemClickListener(View view, int position) {
                Toast.makeText(PictureGroupScanActivity.this, "这是第" + position + "组图片", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{//返回
                PictureGroupScanActivity.this.finish();
            }
        }
    }
}