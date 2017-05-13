package com.team.imagemarker.entitys.saying;

/**
 * 说说实体类：规定用户的发送图片的组大数目为6张
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class SayingModel {
    private int userId;
    private int sayingId;
    private String userHeadImg;//用户头像
    private String nickName;//用户昵称
    private String sendTime;//发送时间
    private String sayingContent;//说说内容
    private int sayingType;//说说类型
    private String sayingImg1;//图片1
    private String sayingImg2;//图片2
    private String sayingImg3;//图片3
    private String sayingImg4;//图片4
    private String sayingImg5;//图片5
    private String sayingImg6;//图片6

    public SayingModel() {
    }

    public SayingModel(int userId, int sayingId, String userHeadImg, String nickName, String sendTime, String sayingContent, int sayingType, String sayingImg1, String sayingImg2, String sayingImg3, String sayingImg4, String sayingImg5, String sayingImg6) {
        this.userId = userId;
        this.sayingId = sayingId;
        this.userHeadImg = userHeadImg;
        this.nickName = nickName;
        this.sendTime = sendTime;
        this.sayingContent = sayingContent;
        this.sayingType = sayingType;
        this.sayingImg1 = sayingImg1;
        this.sayingImg2 = sayingImg2;
        this.sayingImg3 = sayingImg3;
        this.sayingImg4 = sayingImg4;
        this.sayingImg5 = sayingImg5;
        this.sayingImg6 = sayingImg6;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSayingId() {
        return sayingId;
    }

    public void setSayingId(int sayingId) {
        this.sayingId = sayingId;
    }

    public String getUserHeadImg() {
        return userHeadImg;
    }

    public void setUserHeadImg(String userHeadImg) {
        this.userHeadImg = userHeadImg;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getSendTime() {
        return sendTime;
    }

    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }

    public String getSayingContent() {
        return sayingContent;
    }

    public void setSayingContent(String sayingContent) {
        this.sayingContent = sayingContent;
    }

    public int getSayingType() {
        return sayingType;
    }

    public void setSayingType(int sayingType) {
        this.sayingType = sayingType;
    }

    public String getSayingImg1() {
        return sayingImg1;
    }

    public void setSayingImg1(String sayingImg1) {
        this.sayingImg1 = sayingImg1;
    }

    public String getSayingImg2() {
        return sayingImg2;
    }

    public void setSayingImg2(String sayingImg2) {
        this.sayingImg2 = sayingImg2;
    }

    public String getSayingImg3() {
        return sayingImg3;
    }

    public void setSayingImg3(String sayingImg3) {
        this.sayingImg3 = sayingImg3;
    }

    public String getSayingImg4() {
        return sayingImg4;
    }

    public void setSayingImg4(String sayingImg4) {
        this.sayingImg4 = sayingImg4;
    }

    public String getSayingImg5() {
        return sayingImg5;
    }

    public void setSayingImg5(String sayingImg5) {
        this.sayingImg5 = sayingImg5;
    }

    public String getSayingImg6() {
        return sayingImg6;
    }

    public void setSayingImg6(String sayingImg6) {
        this.sayingImg6 = sayingImg6;
    }
}
