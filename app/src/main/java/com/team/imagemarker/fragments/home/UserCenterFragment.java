package com.team.imagemarker.fragments.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.history.HistoryRecordActivity;
import com.team.imagemarker.activitys.hobby.HobbyActivity;
import com.team.imagemarker.activitys.imagscan.ImgScanMainActivity;
import com.team.imagemarker.activitys.integral.IntegralMainActivity;
import com.team.imagemarker.activitys.tasks.UserTaskActivity;
import com.team.imagemarker.activitys.user.FeedBackActivity;
import com.team.imagemarker.activitys.user.UpdateUserMessageActivity;
import com.team.imagemarker.adapters.SharePopBaseAdapter;
import com.team.imagemarker.db.UserDbHelper;
import com.team.imagemarker.entitys.UserModel;
import com.team.imagemarker.entitys.share.SharePopBean;
import com.team.imagemarker.utils.CircleImageView;
import com.team.imagemarker.utils.PaperButton;
import com.team.imagemarker.utils.SlideSwitch;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.scrollview.TranslucentScrollView;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Lmy on 2017/4/28.
 * email 1434117404@qq.com
 */

public class UserCenterFragment extends Fragment implements View.OnClickListener, AdapterView.OnItemClickListener, SlideSwitch.OnStateChangedListener{
    private View view;
    private static final int UPDATE_USER_MESSAGE = 1;
    private TextView tvName, tvIntegral;
    private CircleImageView headImg;

    private TranslucentScrollView translucentScrollView;
    private View zoomView;

    private RelativeLayout updateUserMessage, historyRecord, tasksAchieve, feedBack, shareApp;

    private SlideSwitch systemPush;

    private PaperButton test;
    private ImageView imageView;

    private LinearLayout imgScan, hobbyForum, integralMall;

    //分享App
    private PopupWindow pw;
    private View popView;
    private GridView gv;
    private TextView carName;
    private String[] names = {"QQ", "新浪", "微信"};
    private int[] iconId = {R.mipmap.ssdk_oks_classic_qq, R.mipmap.ssdk_oks_classic_sinaweibo, R.mipmap.ssdk_oks_classic_wechat};
    private List<SharePopBean> shareBeanList = new ArrayList<SharePopBean>();
    private SharePopBaseAdapter shareBaseAdapter;

    private ToastUtil toastUtil = new ToastUtil();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user_center, null);
        popView=LayoutInflater.from(getActivity()).inflate(R.layout.share_grid,null);

        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvIntegral = (TextView) view.findViewById(R.id.tv_fans);
        headImg = (CircleImageView) view.findViewById(R.id.head_img);

        translucentScrollView = (TranslucentScrollView) view.findViewById(R.id.pullzoom_scrollview);
        zoomView = view.findViewById(R.id.lay_header);

        imgScan = (LinearLayout) view.findViewById(R.id.img_scan);
        hobbyForum = (LinearLayout) view.findViewById(R.id.hobby_forum);
        integralMall = (LinearLayout) view.findViewById(R.id.integral_mall);

        updateUserMessage = (RelativeLayout) view.findViewById(R.id.to_update_user_message);
        historyRecord = (RelativeLayout) view.findViewById(R.id.to_history_record);
        tasksAchieve = (RelativeLayout) view.findViewById(R.id.to_tasks_achievement);
        systemPush = (SlideSwitch) view.findViewById(R.id.system_slide_switch);
        shareApp = (RelativeLayout) view.findViewById(R.id.share_app);
        feedBack = (RelativeLayout) view.findViewById(R.id.feed_back);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setData();
        setShareApp();
        UserDbHelper.setInstance(getContext());
        UserModel userModel = UserDbHelper.getInstance().getUserInfo();
        tvName.setText(TextUtils.isEmpty(userModel.getUserNickName()) ? "Limynl" : userModel.getUserNickName());
        tvIntegral.setText(userModel.getIntegral() <= 0 ? "当前积分为：30" : ("当前积分为：" + userModel.getIntegral() + ""));
        if(!userModel.getUserHeadImage().equals("")){
            Glide.with(getContext())
                    .load(userModel.getUserHeadImage())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(headImg);
        }else{
            headImg.setImageResource(R.mipmap.man_head);
        }

    }

    private void setData() {
        translucentScrollView.setPullZoomView(zoomView);//关联需要伸缩的视图

        imgScan.setOnClickListener(this);
        hobbyForum.setOnClickListener(this);
        integralMall.setOnClickListener(this);
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

        gv=(GridView)popView.findViewById(R.id.share_grid);
        shareBaseAdapter = new SharePopBaseAdapter(getActivity(), shareBeanList);
        gv.setAdapter(shareBaseAdapter);
        gv.setOnItemClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.to_update_user_message:{//用户信息修改
                startActivityForResult(new Intent(getActivity(), UpdateUserMessageActivity.class), UPDATE_USER_MESSAGE);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.img_scan:{//美图浏览
                startActivity(new Intent(getActivity(), ImgScanMainActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.hobby_forum:{//兴趣论坛
                startActivity(new Intent(getContext(), HobbyActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.integral_mall:{
                startActivity(new Intent(getContext(), IntegralMainActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.to_history_record:{//历史记录
                startActivity(new Intent(getActivity(), HistoryRecordActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.to_tasks_achievement:{//任务完成情况
//                startActivity(new Intent(getActivity(), UserMissionActivity.class));
//                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(new Intent(getContext(), UserTaskActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.share_app:{//分享App
                pw = getPopWindow(popView);
            }
            break;
            case R.id.feed_back:{//意见反馈
                startActivity(new Intent(getActivity(), FeedBackActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = shareBeanList.get(position).getName();
        if(TextUtils.equals(name, "QQ")){
            Platform qq = ShareSDK.getPlatform(getContext(), QQ.NAME);
            QQ.ShareParams sp = new QQ.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("快来使用图片分类App吧！");
            sp.setTitleUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
            qq.share(sp);
            pw.dismiss();
        }else if (TextUtils.equals(name, "新浪")){
            SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
            sp.setText("测试分享的文本");
            sp.setImagePath("http://139.199.23.142:8080/TestShowMessage1/logo.png");
            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
            weibo.share(sp);
            pw.dismiss();
        }else if(TextUtils.equals(name, "微信")){
            Toast.makeText(getActivity(), "微信分享", Toast.LENGTH_SHORT).show();
            pw.dismiss();
        }
    }

    @Override
    public void onStateChanged(boolean state) {
        if (state == true) {
            toastUtil.Short(getContext(), "系统推送已打开").show();
        } else {
            toastUtil.Short(getContext(), "系统推送已关闭").show();
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
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getActivity().getWindow().setAttributes(lp);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == UPDATE_USER_MESSAGE && resultCode == UPDATE_USER_MESSAGE && data != null){
            Bundle bundle = data.getExtras();
            tvName.setText(bundle.getString("userNick"));
            headImg.setImageBitmap((Bitmap) bundle.getParcelable("userHead"));
        }
    }
}