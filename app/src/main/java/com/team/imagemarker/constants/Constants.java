package com.team.imagemarker.constants;

import android.graphics.Color;

/**
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class Constants {
    public static int USER_ID = 520;//用户id
//    public static final String HOST_IP = "http://139.199.23.142";//服务器地址
    public static final String HOST_IP = "http://192.168.43.204";//服务器地址
    public static final String HOST_PORT = ":8080";//端口号

    //用户信息请求URL
    public static final String USER_REGISTER = HOST_IP + HOST_PORT + "/look/app/SaveUser";//用户注册
    public static final String USER_LOGIN = HOST_IP + HOST_PORT + "/look/app/userLogin";//用户登录（手机号+密码）
    public static final String USER_LOGIN_OTHER = HOST_IP + HOST_PORT + "/look/app/otherLogin";//用户第三方登录
    public static final String USER_PASSWORD_RESEAT = HOST_IP + HOST_PORT + "/look/app/ChangeUserPassword";//用户密码重置
    public static final String USER_PHONE_JUDGE = HOST_IP + HOST_PORT + "/look/app/JudgeUser";//用户手机判断时候已经注册过
    public static final String USER_UPDATE_MESSAGE = HOST_IP + HOST_PORT + "/look/app/ChangeUserInfo";//用户信息修改
    public static final String USER_EXIT = HOST_IP + HOST_PORT + "/look/app/outLogin";//用户退出登录



    /**
     * 测试图片
     */
    public static String User_Head = "http://139.199.23.142:8080/TestShowMessage1/marker/head.jpg";
    public static String User_Head1 = "http:139.199.23.142:8080/TestShowMessage1/marker/head/user_head1.jpg";
    public static String User_Head2 = "http:139.199.23.142:8080/TestShowMessage1/marker/head/user_head2.jpg";
    public static String User_Head3 = "http:139.199.23.142:8080/TestShowMessage1/marker/head/user_head3.jpg";
    public static String User_Head4 = "http:139.199.23.142:8080/TestShowMessage1/marker/head/user_head4.jpg";
    public static String User_Head5 = "http:139.199.23.142:8080/TestShowMessage1/marker/head/user_head5.jpg";
    public static String Test_Img1 = "http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img1.jpg";
    public static String Test_Img2 = "http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img2.jpg";
    public static String Test_Img3 = "http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img3.jpg";
    public static String Test_Img4 = "http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img4.jpg";
    public static String Test_Img5 = "http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img5.jpg";
    public static String Test_Img6 = "http://139.199.23.142:8080/TestShowMessage1/marker/shopping/img6.jpg";

    /**
     * App中标签的颜色
     */
    public static final int[] tagColors = new int[]{
            Color.parseColor("#90C5F0"),
            Color.parseColor("#91CED5"),
            Color.parseColor("#F88F55"),
            Color.parseColor("#C0AFD0"),
            Color.parseColor("#E78F8F"),
            Color.parseColor("#67CCB7"),
            Color.parseColor("#F6BC7E")
    };

    public static final String[] bannerImg = {
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner1.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner2.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner3.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner4.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner5.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner6.jpg"
    };
}
