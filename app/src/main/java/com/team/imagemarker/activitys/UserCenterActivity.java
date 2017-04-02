package com.team.imagemarker.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.PaperButton;


/**
 * 个人中心
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UserCenterActivity extends Activity {
    private TextView title;
    private ImageView leftIcon;
    private ImageView rightIcon;


    private PaperButton test;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);

        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        title.setText("个人中心");
        title.setTextColor(Color.parseColor("#101010"));
        leftIcon.setImageResource(R.drawable.back);
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserCenterActivity.this.finish();
            }
        });


        //测试部分
        test = (PaperButton) findViewById(R.id.user_center_test);
        test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserCenterActivity.this, UpdateUserMessageActivity.class));
            }
        });

        imageView = (ImageView) findViewById(R.id.tasks);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserCenterActivity.this, UserMissionActivity.class));
            }
        });
    }
}