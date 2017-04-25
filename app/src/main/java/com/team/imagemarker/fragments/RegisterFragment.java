package com.team.imagemarker.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.utils.EditTextWithDel;
import com.team.imagemarker.utils.PaperButton;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

import static com.mob.tools.utils.R.getStringRes;


/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class RegisterFragment extends Fragment implements View.OnClickListener{
    private View view;

    private EditTextWithDel userPhone, userPassword, userCode;
    private PaperButton sendCode, userRegister;

    private String iPhone, iCord;
    private int time = 60;//两次获取验证码的时间间隔
    private boolean flag = true;//验证码是否正确标记

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_register, null);
        initSMS();//初始化短信接口
        userPhone = (EditTextWithDel) view.findViewById(R.id.user_phone);
        userPassword = (EditTextWithDel) view.findViewById(R.id.user_password);
        userCode = (EditTextWithDel) view.findViewById(R.id.user_code);
        sendCode = (PaperButton) view.findViewById(R.id.send_code);
        userRegister = (PaperButton) view.findViewById(R.id.user_register);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sendCode.setOnClickListener(this);
        userRegister.setOnClickListener(this);
    }

    /**
     * 初始化短信接口
     */
    private void initSMS() {
        SMSSDK.initSDK(getActivity(),"1d4c456254b5c","6f5980d703c07b9ec90bc7cdd551bd8a");
        EventHandler eh=new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                Message msg = new Message();
                msg.arg1 = event;
                msg.arg2 = result;
                msg.obj = data;
                handler.sendMessage(msg);
            }
        };
        SMSSDK.registerEventHandler(eh);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.send_code:{//发送验证码
                if(!TextUtils.isEmpty(userPhone.getText().toString().trim())){
                    if(!TextUtils.isEmpty(userPassword.getText().toString().trim())){
                        if(userPhone.getText().toString().trim().length() == 11){
                            iPhone = userPhone.getText().toString().trim();
                            SMSSDK.getVerificationCode("86", iPhone);//向服务器请求发送验证码
                            userCode.requestFocus();
                        }else{
                            Toast.makeText(getActivity(), "电话号码的位数不对", Toast.LENGTH_SHORT).show();
                            userPhone.requestFocus();
                        }
                    }else{
                        Toast.makeText(getActivity(), "请输入您的密码", Toast.LENGTH_SHORT).show();
                        userPassword.requestFocus();
                    }
                }else{
                    Toast.makeText(getActivity(), "请输入您的电话号码", Toast.LENGTH_SHORT).show();
                    userPhone.requestFocus();
                }
            }
            break;
            case R.id.user_register:{//用户注册
                if(!TextUtils.isEmpty(userPhone.getText().toString().trim())){
                    if(!TextUtils.isEmpty(userPassword.getText().toString().trim())){
                        if(!TextUtils.isEmpty(userCode.getText().toString().trim())){
                            if(userPhone.getText().toString().trim().length() == 11){
                                if(userCode.getText().toString().trim().length() == 4){
                                    iCord = userCode.getText().toString().trim();
                                    SMSSDK.submitVerificationCode("86", iPhone, iCord);//向服务器提交接收到的验证码
                                    flag = false;
                                }else{
                                    Toast.makeText(getActivity(), "验证码的位数不对", Toast.LENGTH_SHORT).show();
                                    userCode.requestFocus();
                                }
                            }else{
                                Toast.makeText(getActivity(), "电话号码的位数不对", Toast.LENGTH_SHORT).show();
                                userPhone.requestFocus();
                            }
                        }else{
                            Toast.makeText(getActivity(), "请输入验证码", Toast.LENGTH_SHORT).show();
                            userCode.requestFocus();
                        }
                    }else{
                        Toast.makeText(getActivity(), "请输入您的密码", Toast.LENGTH_SHORT).show();
                        userPassword.requestFocus();
                    }
                }else{
                    Toast.makeText(getActivity(), "请输入您的电话号码", Toast.LENGTH_SHORT).show();
                    userPhone.requestFocus();
                }
            }
            break;
        }
    }

    /**
     * 验证码发送阶段文字提示
     */
    private void reminderText() {
        handlerText.sendEmptyMessageDelayed(1, 0);
    }

    /**
     * 改变获取验证码按钮的可点击属性，防止获取验证码过于频繁
     */
    Handler handlerText =new Handler(){
        public void handleMessage(Message msg) {
            if(msg.what==1){
                if(time>0){
                    sendCode.setText(time+"秒后可重新发送");
                    time--;
                    handlerText.sendEmptyMessageDelayed(1, 1000);
                }else{
                    sendCode.setText("重新发送");
                    time = 60;
                }
            }else{//重置获取验证码
                userCode.setText("");
                sendCode.setText("重新发送");
                time = 60;
            }
        }
    };

    /**
     * 获取服务器的信息，通过返回的结果判断是否验证成功
     */
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;
            if (result == SMSSDK.RESULT_COMPLETE) {//回调完成
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {//提交验证码成功,验证通过
//                    handlerText.sendEmptyMessage(2);
                    Toast.makeText(getActivity(), "注册成功", Toast.LENGTH_SHORT).show();
//                    registerUser();//将用户的注册信息传至后台进行保存
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){//服务器验证码发送成功
                    reminderText();
                    Toast.makeText(getActivity(), "验证码已经发送", Toast.LENGTH_SHORT).show();
                }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){//返回支持发送验证码的国家列表
                    Toast.makeText(getActivity(), "获取国家列表成功", Toast.LENGTH_SHORT).show();
                }
            } else {
                if(flag){
                    Toast.makeText(getActivity(), "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    userCode.requestFocus();
                }else{
                    ((Throwable) data).printStackTrace();
                    int resId = getStringRes(getActivity(), "smssdk_network_error");
                    Toast.makeText(getActivity(), "验证码错误", Toast.LENGTH_SHORT).show();
                    userCode.selectAll();
                    if (resId > 0) {
                        Toast.makeText(getActivity(), "resId", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };

    /**
     * 注销短信验证，实现反注册，防止内存泄漏
     */
    @Override
    public void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterAllEventHandler();
    }
}