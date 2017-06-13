package com.team.imagemarker.entitys.hobby;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Lmy on 2017/6/12.
 * email 1434117404@qq.com
 */

public class HotPointModel implements Serializable {
    private String userHead;
    private String userName;
    private String userJob;
    private String userTime;
    private String[] userHobbyTags;
    private String userContent;
    private List<String> userImgList;

    public HotPointModel() {
    }

    public HotPointModel(String userHead, String userName, String userJob, String userTime, String[] userHobbyTags, String userContent, List<String> userImgList) {
        this.userHead = userHead;
        this.userName = userName;
        this.userJob = userJob;
        this.userTime = userTime;
        this.userHobbyTags = userHobbyTags;
        this.userContent = userContent;
        this.userImgList = userImgList;
    }

    public String getUserHead() {
        return userHead;
    }

    public void setUserHead(String userHead) {
        this.userHead = userHead;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getUserTime() {
        return userTime;
    }

    public void setUserTime(String userTime) {
        this.userTime = userTime;
    }

    public String[] getUserHobbyTags() {
        return userHobbyTags;
    }

    public void setUserHobbyTags(String[] userHobbyTags) {
        this.userHobbyTags = userHobbyTags;
    }

    public String getUserContent() {
        return userContent;
    }

    public void setUserContent(String userContent) {
        this.userContent = userContent;
    }

    public List<String> getUserImgList() {
        return userImgList;
    }

    public void setUserImgList(List<String> userImgList) {
        this.userImgList = userImgList;
    }

    @Override
    public String toString() {
        return "HotPointModel{" +
                "userHead='" + userHead + '\'' +
                ", userName='" + userName + '\'' +
                ", userJob='" + userJob + '\'' +
                ", userTime='" + userTime + '\'' +
                ", userHobbyTags=" + Arrays.toString(userHobbyTags) +
                ", userContent='" + userContent + '\'' +
                ", userImgList=" + userImgList +
                '}';
    }
}
