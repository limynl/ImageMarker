package com.team.imagemarker.activitys;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.gitonway.lee.niftynotification.lib.Effects;
import com.gitonway.lee.niftynotification.lib.NiftyNotificationView;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.SharePopBaseAdapter;
import com.team.imagemarker.entitys.SharePopBean;
import com.team.imagemarker.utils.PaperButton;
import com.team.imagemarker.utils.SlideSwitch;

import java.util.ArrayList;
import java.util.List;


/**
 * 个人中心
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UserCenterActivity extends Activity implements View.OnClickListener, AdapterView.OnItemClickListener, SlideSwitch.OnStateChangedListener{
    private LinearLayout rootView;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private RelativeLayout updateUserMessage, historyRecord, tasksAchieve, feedBack, shareApp;

    private SlideSwitch systemPush;
    private Effects effect;

    private PaperButton test;
    private ImageView imageView;

    //分享App
    private PopupWindow pw;
    private View popView;
    private GridView gv;
    private TextView carName;
    private String[] names = {"QQ", "新浪", "微信"};
    private int[] iconId = {R.mipmap.ssdk_oks_classic_qq, R.mipmap.ssdk_oks_classic_sinaweibo, R.mipmap.ssdk_oks_classic_wechat};
    private List<SharePopBean> shareBeanList = new ArrayList<SharePopBean>();
    private SharePopBaseAdapter shareBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_center);
        bindVeiw();//初始化视图控件
        setData();
        setShareApp();

    }

    private void bindVeiw() {
        rootView = (LinearLayout) findViewById(R.id.activity_user_message);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        updateUserMessage = (RelativeLayout) findViewById(R.id.to_update_user_message);
        historyRecord = (RelativeLayout) findViewById(R.id.to_history_record);
        tasksAchieve = (RelativeLayout) findViewById(R.id.to_tasks_achievement);
        systemPush = (SlideSwitch) findViewById(R.id.system_slide_switch);
        shareApp = (RelativeLayout) findViewById(R.id.share_app);
        feedBack = (RelativeLayout) findViewById(R.id.feed_back);
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
        systemPush.setOnStateChangedListener(this);
        shareApp.setOnClickListener(this);
        feedBack.setOnClickListener(this);
    }

    private void setShareApp() {
        shareBeanList.add(new SharePopBean(iconId[0], names[0]));
        shareBeanList.add(new SharePopBean(iconId[1], names[1]));
        shareBeanList.add(new SharePopBean(iconId[2], names[2]));
        popView=getLayoutInflater().inflate(R.layout.share_grid,null);
        gv=(GridView)popView.findViewById(R.id.share_grid);
        shareBaseAdapter = new SharePopBaseAdapter(UserCenterActivity.this, shareBeanList);
        gv.setAdapter(shareBaseAdapter);
        gv.setOnItemClickListener(this);
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
                startActivity(new Intent(UserCenterActivity.this, UserHistoryRecordActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.to_tasks_achievement:{//任务完成情况
                startActivity(new Intent(UserCenterActivity.this, UserMissionActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.share_app:{//分享App
                pw = getPopWindow(popView);
            }
            break;
            case R.id.feed_back:{//意见反馈
                startActivity(new Intent(UserCenterActivity.this, FeedBackActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }

    /**
     * 系统推送滑动开关
     * @param state
     */
    @Override
    public void onStateChanged(boolean state) {
        if (state == true) {
            effect=Effects.slideIn;
            NiftyNotificationView
                    .build(this,"系统推送已打开", effect,R.id.notification)
                    .setIcon(R.drawable.head)
                    .show();
        } else {
            Toast.makeText(this, "系统推送已关闭", Toast.LENGTH_SHORT).show();
        }
    }

    public static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();//获取Snackbar的view
        if(view!=null){
            view.setBackgroundColor(backgroundColor);//修改view的背景色
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);//获取Snackbar的message控件，修改字体颜色
            ((TextView) view.findViewById(R.id.snackbar_text)).setTextSize(20);
        }
    }

    /**
     * 弹窗中的点击事件
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = shareBeanList.get(position).getName();
        if(TextUtils.equals(name, "QQ")){
            Toast.makeText(this, "QQ分享", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }else if (TextUtils.equals(name, "新浪")){
            Toast.makeText(this, "新浪微博分享", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }else if(TextUtils.equals(name, "微信")){
            Toast.makeText(this, "微信分享", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }
    }

    /**
     * 设置弹窗
     * @param view
     * @return
     */
    private PopupWindow getPopWindow(View view){
        PopupWindow popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        backgroundAlpha((float) 0.5);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((float) 1.0);
            }
        });
        return popupWindow;
    }

    /**
     * 背景半透明
     * @param bgAlpha
     */
    private void backgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }
}