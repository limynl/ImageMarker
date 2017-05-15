package com.team.imagemarker.entitys.home;

/**
 * Created by Lmy on 2017/5/13.
 * email 1434117404@qq.com
 */

public class CateGoryInfo {
    private String name;
    private String imgUrl;

    public CateGoryInfo(String name, String imgUrl) {
        this.name = name;
        this.imgUrl = imgUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}
