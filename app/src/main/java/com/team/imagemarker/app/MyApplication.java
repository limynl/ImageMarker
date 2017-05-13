package com.team.imagemarker.app;

import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.team.imagemarker.utils.GlideImageLoader;

/**
 *
 * Created by Lmy on 2017/4/30.
 * email 1434117404@qq.com
 */

public class MyApplication extends Application{
    private static MyApplication myApplication;
    public static RequestQueue requestQueue;
//    public static int memoryCacheSize;

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化全局对象
         myApplication = this;

        // 在使用 SDK 各组之前初始化 context 信息，传入 ApplicationContext
//        SDKInitializer.initialize(this);

        //建立Volley请求队列
        requestQueue = Volley.newRequestQueue(getApplicationContext());

//        memoryCacheSize = getMemoryCacheSize();

//        // 九宫格图片展示及加载方式初始化
//        NineGridView.setImageLoader(new PicassoImageLoader());

        //系统图片多选ImagePicker初始化
        initImagePicker();

    }

    /**
     *
     * @return 得到需要分配的缓存大小，这里用八分之一的大小来做
     */
    public int getMemoryCacheSize() {
        int maxMemory = (int) Runtime.getRuntime().maxMemory();
        return maxMemory / 8;
    }

    /**
     * 获取Application Context
     */
    public static Context getAppContext() {
        return myApplication != null ? myApplication.getApplicationContext() : null;
    }

    /**
     * 设置Volley网络请求队列接口
     * @return
     */
    public static RequestQueue getRequestQueue(){
        return requestQueue;
    }

    /**
     * 系统图片多选ImagePicker初始化
     */
    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
}