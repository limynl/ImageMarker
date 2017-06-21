package com.team.imagemarker.activitys.saying;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImagePreviewDelActivity;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.ImagePickerAdapter;
import com.team.imagemarker.utils.WavyLineView;
import com.team.imagemarker.utils.imageloder.BitmapUtil;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SendMoodActivity extends Activity implements View.OnClickListener, ImagePickerAdapter.OnRecyclerViewItemClickListener{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private EditText editSayingContent;

    private WavyLineView mWavyLine;
    private RecyclerView selectPicture;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数
    private List<File> mFiles;
    private List<String> mSmallUrls;
    private int reqWidth = 0;
    private int reqHeight = 0;
    private Point point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mood);

        updatePixel();
        bindView();
        initDate();
    }

    private void updatePixel() {
        point = new Point();
        getWindowManager().getDefaultDisplay().getSize(point);
        Log.e("carDynamicActivity","宽:"+point.x+",高："+point.y + "hahha.............");
        reqWidth = point.x ;
        reqHeight = point.y ;
    }

    private void initDate() {
        mFiles = new ArrayList<>();
        mSmallUrls = new ArrayList<>();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);
        editSayingContent = (EditText) findViewById(R.id.edit_saying_content);

        selectPicture = (RecyclerView) findViewById(R.id.select_picture);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
        title.setText("发表动态");
        rightIcon.setImageResource(R.mipmap.send_mood_finish);
        leftIcon.setOnClickListener(this);
        rightIcon.setOnClickListener(this);

        // 波浪线设置
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        mWavyLine.setPeriod((float) (2 * Math.PI / 60));
        mWavyLine.setAmplitude(5);
        mWavyLine.setStrokeWidth(2);//ScreenUtil.dp2px(initStrokeWidth)

        //图片选择设置
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        selectPicture.setLayoutManager(new GridLayoutManager(this, 4));
        selectPicture.setHasFixedSize(true);
        selectPicture.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.right_icon:{
                if(editSayingContent.getText().toString().equals("")){
                    Toast.makeText(this, "请输入说说内容", Toast.LENGTH_SHORT).show();
                }else{
                    //显示进度条
                    tryDecodeSmallImg2();
                    //取消进度条
                }
            }
            break;
        }
    }

    /**
     * 压缩图片为小图片
     */
    private void tryDecodeSmallImg2() {
        for (int i = 0; i < selImageList.size(); i++) {
            Log.e("carDynamicActivity","第"+i+"个图片宽:"+selImageList.get(i).width);
            Log.e("carDynamicActivity","第"+i+"个图片高:"+selImageList.get(i).height);
            String filePath = selImageList.get(i).path;

            /* 这里假设只对 200k 以上 并且 宽高小的像素 > 400的图片进行裁剪*/
            reqHeight = selImageList.get(i).height;
            reqWidth = selImageList.get(i).width;
            int minSize = Math.min(reqHeight,reqWidth);
            int size = (int) (selImageList.get(i).size/1024);//当前图片的大小
            Log.e("carDynamicActivity","图片size:"+size+"KB");
            while (minSize > 350 && size >= 200){
                reqWidth /= 2;
                reqHeight /= 2;
                minSize = Math.min(reqHeight,reqWidth);
            }
            if (reqWidth == 0 || reqHeight == 0){ //拍照返回的宽高为0，这里避免异常
                reqWidth = 390;
                reqHeight = 520;
            }
            Log.e("carDynamicActivity","第"+i+"个图片压缩后宽："+reqWidth);
            Log.e("carDynamicActivity","第"+i+"个图片压缩后高："+reqHeight);
            // 对图片压缩尺寸为原来的八分之一
            Bitmap bitmap = BitmapUtil.decodeSampledBitmapFromFile(filePath,reqWidth,reqHeight);
            Log.e("carDynamicActivity","第"+i+"个图片大小:"+bitmap.getByteCount()/1024+"kb");
            saveBitmapFile(bitmap,filePath);
        }
        Toast.makeText(this, "发表成功", Toast.LENGTH_SHORT).show();
    }

    /**
     * 把bitmap转换为file
     * @param bitmap    bitmap源
     * @param filePath  文件路径
     */
    public void saveBitmapFile(Bitmap bitmap,String filePath) {
        if(bitmap==null){
            return;
        }
        Log.e("carDynamicActivity","文件路径:"+filePath);
        File file = new File(filePath);
        try {
            FileOutputStream bos = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bos);
            bos.flush();
            bos.close();
            mFiles.add(file);

        } catch (IOException e) {
            e.printStackTrace();
            Log.e("carDynamicActivity","失败："+e.getMessage());
        }
    }

    //向服务器发送图片
    private void  load(Bitmap photodata) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            photodata.compress(Bitmap.CompressFormat.PNG, 100, baos);//将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
            baos.close();
            byte[] buffer = baos.toByteArray();
            //将图片的字节流数据加密成base64字符输出
            String photo = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT);
            String url = "http://139.199.23.142:8080/TestShowMessage1/lmy/ShowPicture";
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        switch (position) {
            case IMAGE_ITEM_ADD:
                ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
                Intent intent = new Intent(this, com.lzy.imagepicker.ui.ImageGridActivity.class);
                startActivityForResult(intent, REQUEST_CODE_SELECT);
                Log.e("carDynamicActivity", "onSelection: " + "成功打开相册");
                break;
            default:
                //打开预览
                Intent intentPreview = new Intent(this, ImagePreviewDelActivity.class);
                intentPreview.putExtra(ImagePicker.EXTRA_IMAGE_ITEMS, (ArrayList<ImageItem>) adapter.getImages());
                intentPreview.putExtra(ImagePicker.EXTRA_SELECTED_IMAGE_POSITION, position);
                startActivityForResult(intentPreview, REQUEST_CODE_PREVIEW);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            //添加图片返回
            if (data != null && requestCode == REQUEST_CODE_SELECT) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                mFiles.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        } else if (resultCode == ImagePicker.RESULT_CODE_BACK) {
            //预览图片返回
            if (data != null && requestCode == REQUEST_CODE_PREVIEW) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_IMAGE_ITEMS);
                selImageList.clear();
                selImageList.addAll(images);
                adapter.setImages(selImageList);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mFiles != null){
            mFiles.clear();
            mFiles = null;
        }
        if (mSmallUrls != null){
            mSmallUrls.clear();
            mSmallUrls = null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SendMoodActivity.this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}