package com.team.imagemarker.activitys.user;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.PaperButton;
import com.team.imagemarker.utils.WavyLineView;

/**
 * 用户反馈
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class FeedBackActivity extends Activity implements View.OnClickListener{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private PaperButton submit;
    private WavyLineView mWavyLine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back);
        bindView();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        submit = (PaperButton) findViewById(R.id.submit_feed_back);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title.setText("意见反馈");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
        submit.setOnClickListener(this);

        // 波浪线设置
        mWavyLine.setColor(Color.parseColor("#ef6c00"));
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        mWavyLine.setPeriod((float) (2 * Math.PI / 60));
        mWavyLine.setAmplitude(5);
        mWavyLine.setStrokeWidth(2);//ScreenUtil.dp2px(initStrokeWidth)
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.submit_feed_back:{
                Toast.makeText(this, "提交完成", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FeedBackActivity.this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
