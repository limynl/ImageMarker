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
import com.team.imagemarker.entitys.task.TaskHistory;
import com.team.imagemarker.entitys.task.TimeLineModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
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

        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "INACTIVE", "系统推送"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "ACTIVE", "兴趣选择"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "COMPLETED", "系统推送"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "COMPLETED", "系统推送"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "COMPLETED", "兴趣选择"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "COMPLETED", "系统推送"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "COMPLETED", "系统推送"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "COMPLETED", "兴趣选择"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "COMPLETED", "兴趣选择"));
        mDataList.add(new TimeLineModel("图组列表名称", "2017-02-11 19:13", "COMPLETED", "系统推送"));
    }

    private void getDataFromNet() {
        try {
            InputStream in = getAssets().open("taskHistory.json");
            byte[] buffer = new byte[in.available()];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            for (int i = 0; i < jsonArray.length(); i++) {
                jsonObject = jsonArray.optJSONObject(i);
                TimeLineModel timeLineModel = new TimeLineModel();
                timeLineModel.setDate(jsonObject.optString("mData"));
                timeLineModel.setStatus("ACTIVE");
                JSONArray jsonArray1 = jsonObject.optJSONArray("historyRecord");
                List<TaskHistory> histories = new ArrayList<>();
                for (int j = 0; j < jsonArray1.length(); j++) {
                    JSONObject jsonObject1 = jsonArray1.optJSONObject(j);
                    TaskHistory taskHistory = new TaskHistory();
                    taskHistory.setTitle(jsonObject1.optString("title"));
                    taskHistory.setOperatorType(jsonObject1.optString("operatorType"));
                    histories.add(taskHistory);
                }
                timeLineModel.setHistories(histories);
                mDataList.add(timeLineModel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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