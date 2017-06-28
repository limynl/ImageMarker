package com.team.imagemarker.activitys.tasks;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.adapters.task.TaskBoxAdapter;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.entitys.marker.ItemEntity;
import com.team.imagemarker.utils.chart.LineChartView;
import com.team.imagemarker.utils.chart.SlimChart;
import com.team.imagemarker.utils.scrollview.MyHorizontalScrollView;
import com.wangjie.rapidfloatingactionbutton.textlabel.LabelView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
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
//    private MyGridView taskBox;
//    private ViewPager taskViewPager;
//    private CardPagerAdapter cardPagerAdapter;
//    private ShadowTransformer shadowTransformer;//ViewPager切换动画
    private MyHorizontalScrollView recentTask;
    private List<CategoryModel> taskList = new ArrayList<>();

    private List<String> dateList = new ArrayList<>();// 日期
    private List<Double> earnList = new ArrayList<>();// 图片数目 共8组值
    private List<CategoryModel> systemPushList = new ArrayList<>();
    private TaskBoxAdapter adapterSystem;

//    private HobbyPushAdapter adapterHobby;
//    private RecyclerView hobbyRecycle;
//    private List<CategoryModel> hobbyPushList = new ArrayList<>();

    private static List<ItemEntity> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task);
        bindView();
        setSlimChart();//设置Slimart圆圈
        setLineChart();//设置折现统计图
        setTaskBox();
        initDataList();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        subTitle = (TextView) findViewById(R.id.sub_title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        slimChart = (SlimChart) findViewById(R.id.slimChart);
        chartView = (LineChartView) findViewById(R.id.chartView);
//        taskBox = (MyGridView) findViewById(R.id.task_box);
//        taskViewPager = (ViewPager) findViewById(R.id.card_viewpager);
        recentTask = (MyHorizontalScrollView) findViewById(R.id.recent_task);
//        hobbyRecycle = (RecyclerView) findViewById(R.id.hobby_push);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
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
        slimChart.setText("30");
        slimChart.setTextColorInt(Color.parseColor("#464e76"));
        slimChart.setRoundEdges(true);
    }

    private void setLineChart() {
        dateList.add("06-24");
        dateList.add("06-23");
        dateList.add("06-22");
        dateList.add("06-21");
        dateList.add("06-20");
        dateList.add("06-19");
        dateList.add("06-18");
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
        taskList.add(new CategoryModel(R.mipmap.task2, R.mipmap.task, "06-24", "风景", "兴趣推送"));
        taskList.add(new CategoryModel(R.mipmap.task1, R.mipmap.shopping2, "06-23", "植物", "系统推送"));
        taskList.add(new CategoryModel(R.mipmap.task3, R.mipmap.shopping3, "06-23", "大自然", "系统推送"));
        taskList.add(new CategoryModel(R.mipmap.task2, R.mipmap.shopping1, "06-23", "动物", "热门种类"));
        taskList.add(new CategoryModel(R.mipmap.task1, R.mipmap.shopping2, "06-22", "建筑", "猜你喜欢"));
        taskList.add(new CategoryModel(R.mipmap.task3, R.mipmap.shopping3, "06-21", "风景", "热门分类"));
        taskList.add(new CategoryModel(R.mipmap.task2, R.mipmap.shopping3, "06-21", "风景", "兴趣推送"));
        View recentItem = null;
        ViewHolder viewHolder = new ViewHolder();
        LinearLayout rootview = new LinearLayout(this);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(250,340);//SizeUtils.px2dip(this, 130),SizeUtils.px2dip(this, 160)250,330
        param.setMargins(0, 0, 20, 0);
        for (int i = 0; i < taskList.size(); i++) {
            recentItem = LayoutInflater.from(this).inflate(R.layout.item_recent_task, null);
            viewHolder.categoryImg = (ImageView) recentItem.findViewById(R.id.category_img);
            viewHolder.userHead = (ImageView) recentItem.findViewById(R.id.user_head);
            viewHolder.userRanking = (LabelView) recentItem.findViewById(R.id.user_ranking);
            viewHolder.category = (TextView) recentItem.findViewById(R.id.category);
            viewHolder.opterator = (TextView) recentItem.findViewById(R.id.opterator);
            viewHolder.toMark = (Button) recentItem.findViewById(R.id.toMark);
            viewHolder.categoryImg.setImageResource(taskList.get(i).getImgId());
            viewHolder.userHead.setImageResource(taskList.get(i).getImgId1());
            viewHolder.userRanking.setText(taskList.get(i).getName());
            viewHolder.category.setText("所选类别: " + taskList.get(i).getUserName());
            viewHolder.opterator.setText("操作类型: " + taskList.get(i).getIntegral());
            viewHolder.toMark.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserTaskActivity.this, MarkHomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("pageTag", "userTask");
                    bundle.putSerializable("item", (Serializable) dataList);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    UserTaskActivity.this.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
            rootview.addView(recentItem, param);
        }
        recentTask.removeAllViews();
        recentTask.addView(rootview);
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

    class ViewHolder{
        public ImageView categoryImg;
        public ImageView userHead;
        public LabelView userRanking;
        public TextView category;
        public TextView opterator;
        public Button toMark;
    }

    private void initDataList() {
        try {
            InputStream in = getAssets().open("task.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity(itemJsonObject);
                    dataList.add(itemEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}