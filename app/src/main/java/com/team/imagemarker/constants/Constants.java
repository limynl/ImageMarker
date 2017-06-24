package com.team.imagemarker.constants;

import android.graphics.Color;

/**
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class Constants {
    public static int USER_ID = 17;//用户id
//    public static final String HOST_IP = "http://139.199.23.142";//服务器地址
    public static final String HOST_IP = "http://172.20.10.10";//服务器地址
    public static final String HOST_PORT = ":8080";//端口号

    /**
     * 用户信息请求URL
     */
    public static final String USER_REGISTER = HOST_IP + HOST_PORT + "/look/app/SaveUser";//用户注册
    public static final String USER_LOGIN = HOST_IP + HOST_PORT + "/look/app/userLogin";//用户登录（手机号+密码）
    public static final String USER_LOGIN_OTHER = HOST_IP + HOST_PORT + "/look/app/otherLogin";//用户第三方登录
    public static final String USER_PASSWORD_RESEAT = HOST_IP + HOST_PORT + "/look/app/ChangeUserPassword";//用户密码重置
    public static final String USER_PHONE_JUDGE = HOST_IP + HOST_PORT + "/look/app/JudgeUser";//用户手机判断时候已经注册过
    public static final String USER_UPDATE_MESSAGE = HOST_IP + HOST_PORT + "/look/app/ChangeUserInfo";//用户信息修改
    public static final String USER_EXIT = HOST_IP + HOST_PORT + "/look/app/outLogin";//用户退出登录

    public static final String USER_HISTORY_DELETE = HOST_IP + HOST_PORT + "/look/app/DeleteUserHistoryById";
    public static final String USER_ALL_HISTORY = HOST_IP + HOST_PORT + "/look/app/GetUserHistory";

    public static final String USER_SUBMIT_TAG = HOST_IP + HOST_PORT + "/look/picture/judgeUserUpInageLabelInfo";
    public static final String USER_SAVE_TAG = HOST_IP + HOST_PORT + "/look/picture/saveUserUpImageInfo";

    public static final String USER_HISTORY_DATA = "http://obs.myhwclouds.com/look.admin.info/historyRecord.txt";

    /**
     * 界面数据展示地址
     */
    public static final String SYSTEM_PUSH_DATA = "http://obs.myhwclouds.com/look.admin.info/systemPush.txt";//首页-系统推送
    public static final String HOBBY_PUSH_DATA = "http://obs.myhwclouds.com/look.admin.info/hobbyPush.txt";//首页-兴趣推送
    public static final String HOT_CATEGROY_DATA = "http://obs.myhwclouds.com/look.admin.info/hotCateGroy.txt";//兴趣导航-热门种类
    public static final String GUESS_YOU_LIKE_DATA = "http://obs.myhwclouds.com/look.admin.info/hotCateGroy.txt";//兴趣导航-猜你喜欢

    /**
     * 测试数据
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

    /**
     * banner页数据
     */
    public static final String[] bannerImg = {
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner1.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner2.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner3.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner4.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner5.jpg",
            "http://139.199.23.142:8080/TestShowMessage1/marker/banner/imgNavBanner/banner6.jpg"
    };

    public static final String[] guessYouLikeImg = {
            "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-21/白云岩-高山-水-天空-树.jpg",
            "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-22/城堡-建筑-宫殿-天空.jpg",
            "http://obs.myhwclouds.com/look.admin.image/华为/2017-5-21/天空-云朵-船-树-湖水.jpg",
            "http://obs.myhwclouds.com/look.admin.image/腾讯/2017-5-21/建筑-纪念碑-卡鲁城堡.jpg",
            "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-20/城堡-树木-草地-建筑.jpg",
            "http://obs.myhwclouds.com/look.admin.image/老马识途/2017-5-20/房屋-河-城市.jpg",
            "http://obs.myhwclouds.com/look.admin.image/华为/2017-5-23/电梯-人-店铺-大厅.jpg",
            "http://obs.myhwclouds.com/look.admin.image/腾讯/2017-5-23/山-树-建筑-水.jpg"
    };
}
