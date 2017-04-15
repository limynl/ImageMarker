package com.team.imagemarker.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.PaperButton;


/**
 * 个人中心
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UserCenterActivity extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private RelativeLayout updateUserMessage, historyRecord, tasksAchieve;

    private PaperButton test;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        bindVeiw();//初始化视图控件
        setData();

    }

    private void bindVeiw() {
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        updateUserMessage = (RelativeLayout) findViewById(R.id.to_update_user_message);
        historyRecord = (RelativeLayout) findViewById(R.id.to_history_record);
        tasksAchieve = (RelativeLayout) findViewById(R.id.to_tasks_achievement);
    }

    private void setData() {
        title.setText("个人中心");
        title.setTextColor(Color.parseColor("#101010"));
        leftIcon.setImageResource(R.drawable.back);
        rightIcon.setVisibility(View.GONE);

        leftIcon.setOnClickListener(this);
        updateUserMessage.setOnClickListener(this);
        historyRecord.setOnClickListener(this);
        tasksAchieve.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{//返回
                UserCenterActivity.this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            break;
            case R.id.to_update_user_message:{//用户信息修改
                startActivity(new Intent(UserCenterActivity.this, UpdateUserMessageActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.to_history_record:{//历史记录
                startActivity(new Intent(UserCenterActivity.this, UserHistoryRecord.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.to_tasks_achievement:{//任务完成情况
                startActivity(new Intent(UserCenterActivity.this, UserMissionActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }
}