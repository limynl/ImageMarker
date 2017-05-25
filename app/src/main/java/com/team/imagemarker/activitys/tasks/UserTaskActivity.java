package com.team.imagemarker.activitys.tasks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.task.TaskBoxAdapter;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.utils.MyGridView;
import com.team.imagemarker.utils.chart.LineChartView;
import com.team.imagemarker.utils.chart.SlimChart;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/5/18.
 * email 1434117404@qq.com
 */

public class UserTaskActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView title, subTitle;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private SlimChart slimChart;
    private LineChartView chartView;
    private MyGridView taskBox;

    private List<String> dateList = new ArrayList<>();// 日期
    private List<Double> earnList = new ArrayList<>();// 图片数目 共8组值
    private List<CategoryModel> systemPushList = new ArrayList<>();
    private TaskBoxAdapter adapterSystem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task);
        bindView();
        setSlimChart();//设置Slimart圆圈
        setLineChart();//设置折现统计图
        setTaskBox();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        subTitle = (TextView) findViewById(R.id.sub_title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        slimChart = (SlimChart) findViewById(R.id.slimChart);
        chartView = (LineChartView) findViewById(R.id.chartView);
        taskBox = (MyGridView) findViewById(R.id.task_box);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title.setText("任务情况");
        subTitle.setVisibility(View.VISIBLE);
        subTitle.setText("概览");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
        subTitle.setOnClickListener(this);
    }

    private void setSlimChart() {
        //设置颜色
        int[] colors = new int[4];
        colors[0] = Color.parseColor("#4dd0e1");
        colors[1] = Color.parseColor("#4fc3f7");
        colors[2] = Color.parseColor("#29b6f6");
        colors[3] = Color.parseColor("#03a9f4");
        slimChart.setColors(colors);

        //设置状态
        final float[] stats = new float[4];
        stats[0] = 100;
        stats[1] = 85;
        stats[2] = 40;
        stats[3] = 25;
        slimChart.setStats(stats);

        //动画显示
        slimChart.setStartAnimationDuration(3500);
        slimChart.playStartAnimation();

        //设置文本
        slimChart.setStrokeWidth(13);
        slimChart.setText("234");
        slimChart.setTextColorInt(Color.parseColor("#464e76"));
        slimChart.setRoundEdges(true);
    }

    private void setLineChart() {
        dateList.add("05-01");
        dateList.add("05-02");
        dateList.add("05-03");
        dateList.add("05-04");
        dateList.add("05-05");
        dateList.add("05-06");
        dateList.add("05-07");
        earnList.add(12.0);
        earnList.add(31.0);
        earnList.add(16.0);
        earnList.add(85.0);
        earnList.add(22.0);
        earnList.add(52.0);
        earnList.add(67.0);
        earnList.add(12.0);
        // 折线图
        chartView.setTextSize(25, 25, 25, 25);
        chartView.setData(earnList,dateList,true);
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
                Toast.makeText(UserTaskActivity.this, "position:" + (position + 1), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.sub_title:{
                Intent intent = new Intent(this, UserTaskOverViewActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}