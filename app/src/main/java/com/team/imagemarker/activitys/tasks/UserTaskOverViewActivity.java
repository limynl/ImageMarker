package com.team.imagemarker.activitys.tasks;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.task.TimeLineAdapter;
import com.team.imagemarker.entitys.task.TimeLineModel;

import java.util.ArrayList;
import java.util.List;

public class UserTaskOverViewActivity extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private RecyclerView mRecyclerView;
    private TimeLineAdapter mTimeLineAdapter;
    private List<TimeLineModel> mDataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_task_over_view);
        bindView();
        setTimeLine();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title.setText("任务概览");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
    }

    private void setTimeLine() {
        mRecyclerView = (RecyclerView) findViewById(R.id.timeline_recycleview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);

        setDataListItems();
        mTimeLineAdapter = new TimeLineAdapter(mDataList);
        mRecyclerView.setAdapter(mTimeLineAdapter);
    }

    /**
     * 设置数据
     */
    private void setDataListItems(){
        mDataList.add(new TimeLineModel("历史记录列表", "", "INACTIVE"));
        mDataList.add(new TimeLineModel("历史记录列表", "2017-02-12 08:00", "ACTIVE"));
        mDataList.add(new TimeLineModel("历史记录列表", "2017-02-11 21:00", "COMPLETED"));
        mDataList.add(new TimeLineModel("历史记录列表", "2017-02-11 18:00", "COMPLETED"));
        mDataList.add(new TimeLineModel("历史记录列表", "2017-02-11 09:30", "COMPLETED"));
        mDataList.add(new TimeLineModel("历史记录列表", "2017-02-11 08:00", "COMPLETED"));
        mDataList.add(new TimeLineModel("历史记录列表", "2017-02-10 15:00", "COMPLETED"));
        mDataList.add(new TimeLineModel("历史记录列表", "2017-02-10 14:30", "COMPLETED"));
        mDataList.add(new TimeLineModel("历史记录列表", "2017-02-10 14:00", "COMPLETED"));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        this.overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}