package com.team.imagemarker.entitys;


public class CardItem {

    private int imgId;
    private String imgName;//图组描述
    private String imgUrl;//图组地址

    public CardItem(int imgId, String imgName){
        this.imgId = imgId;
        this.imgName = imgName;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getImgName() {
        return imgName;
    }

    public void setImgName(String imgName) {
        this.imgName = imgName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}