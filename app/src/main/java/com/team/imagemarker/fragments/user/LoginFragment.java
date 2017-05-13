package com.team.imagemarker.fragments.user;

import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.mob.tools.utils.UIHandler;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.user.HomeActivity;
import com.team.imagemarker.activitys.user.UserResetPassActivity;
import com.team.imagemarker.utils.EditTextWithDel;
import com.team.imagemarker.utils.PaperButton;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.connect.common.Constants;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;


/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class LoginFragment extends Fragment implements View.OnClickListener, PlatformActionListener, Handler.Callback{
    private View view;

    private EditTextWithDel userPhone, userPassword;
    private PaperButton userLogin;
    private LinearLayout resetPassword;
    private RadioButton qqLogin, wechatLogin, sinaLogin;

    private static final int MSG_TOAST = 1;
    private static final int MSG_ACTION_CCALLBACK = 2;
    private static final int MSG_CANCEL_NOTIFY = 3;
    final private Context context = getActivity();
    private Platform mPf;
    private Tencent mTencent;
    private BaseUiListener mIUiListener;
    private UserInfo mUserInfo;
    private  IWXAPI api;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_login, null);
        initMob();
        userPhone = (EditTextWithDel) view.findViewById(R.id.user_phone);
        userPassword = (EditTextWithDel) view.findViewById(R.id.user_password);
        userLogin = (PaperButton) view.findViewById(R.id.user_login);
        resetPassword = (LinearLayout) view.findViewById(R.id.reset_password);
        qqLogin = (RadioButton) view.findViewById(R.id.qq_login);
        wechatLogin = (RadioButton) view.findViewById(R.id.wechat_login);
        sinaLogin = (RadioButton) view.findViewById(R.id.sina_login);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userLogin.setOnClickListener(this);
        resetPassword.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        wechatLogin.setOnClickListener(this);
        sinaLogin.setOnClickListener(this);
    }

    /**
     * 第三方平台初始化
     */
    private void initMob() {
        ShareSDK.initSDK(getActivity());//mob初始化
        mTencent = Tencent.createInstance("1105819277",getActivity());//QQ登录初始化
        api = WXAPIFactory.createWXAPI(getActivity(), "wx4868b35061f87885", true);//微信登录初始化
        api.registerApp("wx4868b35061f87885");// 将应用的appid注册到微信
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_login:{//登录
                startActivity(new Intent(getActivity(), HomeActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in_up,R.anim.slide_out_down);
            }
            break;
            case R.id.reset_password:{//重置密码
                startActivity(new Intent(getActivity(), UserResetPassActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.qq_login:{//QQ登录
                thirdQQLogin();
            }
            break;
            case R.id.wechat_login:{//微信登录
                if(!api.isWXAppInstalled()){
                    Toast.makeText(context, "请安装微信客户端之后再进行登录", Toast.LENGTH_SHORT).show();
                    return;
                }
                getCode();
            }
            break;
            case R.id.sina_login:{//新浪微博登录
                thirdSinaLogin();
            }
            break;
        }
    }

    /**
     * QQ第三方登录
     */
    private void thirdQQLogin() {
        mIUiListener = new BaseUiListener();
        if(!mTencent.isSessionValid()){//判断是否登录过
            mTencent.login(getActivity(),"all", mIUiListener);//all表示获取所有权限
        }else{//登录过注销之后再登录
            mTencent.logout(getActivity());
            mTencent.login(getActivity(),"all", mIUiListener);
        }
    }

    /**
     * 新浪微博授权，获取用户注册信息
     */
    private void thirdSinaLogin() {
        Platform pf = ShareSDK.getPlatform(context, SinaWeibo.NAME);//初始化新浪平台
        pf.SSOSetting(true);
        pf.setPlatformActionListener(this);//设置监听
        pf.authorize();//出现授权界面，获取登陆用户的信息，如果没有授权，会先授权，然后获取用户信息
    }

    /**
     * 微信登录返回码
     */
    private void getCode(){
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "carjob_wx_login";
        api.sendReq(req);
    }

    /**
     * 在调用Login的Activity或者Fragment中重写onActivityResult方法
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == Constants.REQUEST_LOGIN){
            Tencent.onActivityResultData(requestCode,resultCode,data,mIUiListener);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(getActivity());
    }

    /**
     * 微博登录返回码
     * @param msg
     * @return
     */
    @Override
    public boolean handleMessage(Message msg) {
        switch(msg.what) {
            case MSG_TOAST: {
                String text = String.valueOf(msg.obj);
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
            break;
            case MSG_ACTION_CCALLBACK: {
                switch (msg.arg1) {
                    case 1: {
                        // 成功, successful notification
                        //http://open.weibo.com/wiki/2/users/show 新浪微博返回结果字段说明
                        Platform pf = ShareSDK.getPlatform(context, SinaWeibo.NAME);
                        //常用返回字段
                        Log.e("sharesdk use_id", pf.getDb().getUserId()); //获取用户id
                        Log.e("sharesdk use_name", pf.getDb().getUserName());//获取用户名称
                        Log.e("sharesdk use_icon", pf.getDb().getUserIcon());//获取用户头像
                        Log.e("sharesdk use_gender", pf.getDb().getUserGender());//获取用户性别
                        Log.e("sharesdk use_gender", pf.getDb().getPlatformNname());//获取平台名称
//                        mThirdLoginResult.setText(pf.getDb().getUserId());
                    }
                    break;
                    case 2: {
//                        mThirdLoginResult.setText("登录失败");
                    }
                    break;
                    case 3: {
                        // 取消, cancel notification
//                        mThirdLoginResult.setText("取消授权");
                    }
                    break;
                }
            }
            break;
            case MSG_CANCEL_NOTIFY: {
                NotificationManager nm = (NotificationManager) msg.obj;
                if (nm != null) {
                    nm.cancel(msg.arg1);
                }
            }
            break;
        }
        return false;
    }

    /**
     * 新浪微博授权成功回调页面
     * @param platform
     * @param action
     * @param hashMap
     */
    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> hashMap) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 1;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * 授权失败
     * @param platform
     * @param action
     * @param t
     */
    @Override
    public void onError(Platform platform, int action, Throwable t) {
        t.printStackTrace();
        t.getMessage();
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 2;
        msg.arg2 = action;
        msg.obj = t;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * 取消授权
     * @param platform
     * @param action
     */
    @Override
    public void onCancel(Platform platform, int action) {
        Message msg = new Message();
        msg.what = MSG_ACTION_CCALLBACK;
        msg.arg1 = 3;
        msg.arg2 = action;
        msg.obj = platform;
        UIHandler.sendMessage(msg, this);
    }

    /**
     * 自定义监听器实现IUiListener接口后，需要实现的3个方法
     * onComplete完成 onError错误 onCancel取消
     */
    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            Toast.makeText(context, "授权成功", Toast.LENGTH_SHORT).show();
            JSONObject obj = (JSONObject) response;
            try {
                String openID = obj.getString("openid");
                String accessToken = obj.getString("access_token");
                String expires = obj.getString("expires_in");
                mTencent.setOpenId(openID);
                mTencent.setAccessToken(accessToken,expires);
                QQToken qqToken = mTencent.getQQToken();
                mUserInfo = new UserInfo(getActivity(),qqToken);
                mUserInfo.getUserInfo(new IUiListener() {
                    @Override
                    public void onComplete(Object response) {
                        JSONObject jsonString = (JSONObject)response;
                        try {
                            //常用字段
                            String name = jsonString.getString("nickname");//获取用户名
                            String iconQQ = jsonString.getString("figureurl_qq_1");//获取QQ用户的头像
                            String iconQZ = jsonString.getString("figureurl_1");//获取QQ空间头像
                            String province = jsonString.getString("province");//获取地址
                            String gender = jsonString.getString("gender");//获取性别
//                            mThirdLoginResult.setText(name);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Toast.makeText(getActivity(), "登录成功", Toast.LENGTH_SHORT).show();
                        Log.e("Mainactivity","登录成功"+response.toString());
                        /*Intent intent = new Intent(TestLoginActivity.this, TestExampleAsyncActivity.class);
                        startActivity(intent);
                        finish();*/
                    }
                    @Override
                    public void onError(UiError uiError) {
                        Log.e("Mainactivity","登录失败"+uiError.toString());
                    }
                    @Override
                    public void onCancel() {
                        Log.e("Mainactivity","登录取消");
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError uiError) {
            Log.e("MainActivity", "onError: " + uiError.toString());
            Toast.makeText(context, "授权失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
            Toast.makeText(context, "授权取消", Toast.LENGTH_SHORT).show();
        }
    }
}
