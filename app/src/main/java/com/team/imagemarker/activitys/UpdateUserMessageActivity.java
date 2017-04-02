package com.team.imagemarker.activitys;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.team.imagemarker.R;

/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UpdateUserMessageActivity extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon;
    private ImageView rightIcon;

    private ImageView userHobbySelect;
    private View contentView;
    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_message);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        userHobbySelect = (ImageView) findViewById(R.id.user_hobby_select);

        title.setText("个人资料");
        title.setTextColor(Color.parseColor("#101010"));
        leftIcon.setImageResource(R.drawable.back);
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
        userHobbySelect.setOnClickListener(this);

        //显示PopupWindow
        setPopupWindow();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                UpdateUserMessageActivity.this.finish();
            }
            break;
            case R.id.user_hobby_select:{
                showCenterPopupWindow(v);
            }
            break;
        }
    }

    private void setPopupWindow() {
        contentView = LayoutInflater.from(this).inflate(R.layout.user_hobby_layout, null);
    }

    public void showCenterPopupWindow(View view) {
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.4f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}