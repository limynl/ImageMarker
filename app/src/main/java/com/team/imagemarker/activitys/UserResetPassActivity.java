package com.team.imagemarker.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.EditTextWithDel;
import com.team.imagemarker.utils.PaperButton;

/**
 * 重置密码
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UserResetPassActivity extends Activity implements View.OnClickListener{
    private ImageView back;
    private EditTextWithDel userPhone, userPassword, userCode;
    private PaperButton submit, sendCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reset_pass);
        back = (ImageView) findViewById(R.id.close_reset);
        userPhone = (EditTextWithDel) findViewById(R.id.user_phone);
        userPassword = (EditTextWithDel) findViewById(R.id.user_password);
        userCode = (EditTextWithDel) findViewById(R.id.user_code);
        sendCode = (PaperButton) findViewById(R.id.send_code);
        submit = (PaperButton) findViewById(R.id.user_submit);

        back.setOnClickListener(this);
        sendCode.setOnClickListener(this);
        submit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_reset:{//返回
                UserResetPassActivity.this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            break;
            case R.id.send_code:{
                Toast.makeText(this, "获取验证码", Toast.LENGTH_SHORT).show();
            }
            break;
            case R.id.user_submit:{//确定
                Toast.makeText(this, "提交", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }
}