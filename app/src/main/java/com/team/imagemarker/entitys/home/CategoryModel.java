package com.team.imagemarker.entitys.home;

/**
 * Created by Lmy on 2017/5/1.
 * email 1434117404@qq.com
 */

public class CategoryModel {
    private int imgId;
    private int imgId1;
    private String name;
    private String simpleMessage;

    public CategoryModel() {
    }

    public CategoryModel(int imgId, String name, String simpleMessage) {
        this.imgId = imgId;
        this.name = name;
        this.simpleMessage = simpleMessage;
    }

    public CategoryModel(int imgId, int imgId1, String name){
        this.imgId = imgId;
        this.imgId1 = imgId1;
        this.name = name;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getImgId1() {
        return imgId1;
    }

    public void setImgId1(int imgId1) {
        this.imgId1 = imgId1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleMessage() {
        return simpleMessage;
    }

    public void setSimpleMessage(String simpleMessage) {
        this.simpleMessage = simpleMessage;
    }
}
