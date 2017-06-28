package com.team.imagemarker.activitys.hobby;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.fragments.hobby.DragAdapter;
import com.team.imagemarker.fragments.hobby.DragGridView;

import java.util.ArrayList;

public class HobbySelectActivity extends Activity implements View.OnClickListener{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private ArrayList<String> channels = new ArrayList<>();
    private ArrayList<String> channels_other = new ArrayList<>();
    private DragGridView gridView;
    private DragGridView gridView_other;
    private DragAdapter dragAdapter;
    private DragAdapter other_adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hobby_select);
        bindView();

        initData();
        initDataOther();

        gridView = (DragGridView) findViewById(R.id.gridView_channel);
        gridView_other = (DragGridView) findViewById(R.id.gridView_channel_other);

        gridView.setNumColumns(4);
        dragAdapter = new DragAdapter(this, channels);
        gridView.setAdapter(dragAdapter);

        other_adapter = new DragAdapter(this, channels_other);
        gridView_other.setAdapter(other_adapter);
        gridView_other.setNumColumns(4);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String channel = channels.get(position);
                channels.remove(position);
                channels_other.add(channel);
                dragAdapter.notifyDataSetChanged();
                other_adapter.notifyDataSetChanged();
            }
        });
        gridView_other.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String channel = channels_other.get(position);
                channels_other.remove(position);
                channels.add(channel);
                dragAdapter.notifyDataSetChanged();
                other_adapter.notifyDataSetChanged();
            }
        });
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("兴趣管理");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
    }

    private void initDataOther() {
        channels_other.add("花草");
        channels_other.add("娱乐");
        channels_other.add("笑话");
        channels_other.add("游戏");
        channels_other.add("电台");
        channels_other.add("NAB");
        channels_other.add("数码");
        channels_other.add("教育");
        channels_other.add("论坛");
        channels_other.add("旅游");
        channels_other.add("手机");
        channels_other.add("博客");
        channels_other.add("社会");
        channels_other.add("家具");
        channels_other.add("暴雪");
        channels_other.add("亲子");
        channels_other.add("CBA");
        channels_other.add("消息");
    }

    private void initData() {
        channels.add("植物");
        channels.add("动物");
        channels.add("山水");
        channels.add("生态");
        channels.add("城市");
        channels.add("科技");
        channels.add("财经");
        channels.add("军事");
        channels.add("体育");
        channels.add("建筑");
        channels.add("时尚");
        channels.add("汽车");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                Intent intent = new Intent();
                intent.putExtra("resultCode", "test");
                this.setResult(1, intent);
                this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent();
//        intent.putExtra("resultCode", "test");
//        this.setResult(1, intent);
//        this.finish();
//        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}