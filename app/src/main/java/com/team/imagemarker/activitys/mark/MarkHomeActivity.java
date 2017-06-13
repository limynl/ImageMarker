package com.team.imagemarker.activitys.mark;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.marker.ItemEntity;
import com.team.imagemarker.utils.marker.FadeTransitionImageView;
import com.team.imagemarker.utils.marker.HorizontalTransitionLayout;
import com.team.imagemarker.utils.marker.PileLayout;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lmy on 2017/5/18.
 * email 1434117404@qq.com
 */

public class MarkHomeActivity extends Activity implements View.OnClickListener{
    private TextView title, subTitle;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private PileLayout pileLayout;
    private List<ItemEntity> dataList = new ArrayList<>();

    private int lastDisplay = -1;

    private ObjectAnimator transitionAnimator;
    private float transitionValue;
    private HorizontalTransitionLayout countIndicator;
    private FadeTransitionImageView markImg;
    private Animator.AnimatorListener animatorListener;
    private TagGroup tag;
    private String[] tempTags;//暂存每张图片的先前的标签
    private List<TagColor> colors = TagColor.getRandomColors(7);//随机生成标签颜色

    private View customDialog, dialogMessage;
    private TextView showMessage, dialogTitle, operateMessage;
    private Button delete, cancel;
    private Dialog dialogOne, dialogTwo;

    private String pageFlag;//页面标志，用于判断是哪一个界面进入的

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_home);
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        subTitle = (TextView) findViewById(R.id.sub_title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title.setText("图片标注");
        rightIcon.setVisibility(View.GONE);
        subTitle.setVisibility(View.VISIBLE);
        leftIcon.setOnClickListener(this);
        subTitle.setOnClickListener(this);

        countIndicator = (HorizontalTransitionLayout) findViewById(R.id.countIndicator);
        pileLayout = (PileLayout) findViewById(R.id.pileLayout);
        markImg = (FadeTransitionImageView) findViewById(R.id.mark_img);
        tag = (TagGroup) findViewById(R.id.image_tag);

        initAnimationListener();//初始化动画属性

        Bundle bundle = getIntent().getExtras();
        pageFlag = bundle.getString("pageTag");//判断页面来源
        if (pageFlag.equals("completeHistory")){//历史记录已完成，查看标签
            tag.setAppendMode(false);//将标签转换成不可编辑状态
            title.setText("历史标注");
            subTitle.setVisibility(View.GONE);
            dataList = (List<ItemEntity>) bundle.getSerializable("completeData");
        }else if(pageFlag.equals("noCompleteHistory")){//历史记录未完成，继续打标签
            dataList = (List<ItemEntity>) bundle.getSerializable("noCompleteData");
        }else if(pageFlag.equals("firstPage")){
            initDataList();//接受需要打标签的图片数据
        }
        pileLayout.setAdapter(new Adapter());//设置底部图片滚动数据
    }

    /**
     * 初始化动画属性
     */
    private void initAnimationListener() {
        animatorListener = new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {}

            @Override
            public void onAnimationEnd(Animator animation) {
                countIndicator.onAnimationEnd();
                markImg.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };
    }

    /**
     * 设置需要标注图片缩略图的适配器
     */
    class Adapter extends PileLayout.Adapter {

        @Override
        public int getLayoutId() {
            return R.layout.item_layout;
        }

        @Override
        public void bindView(View view, int position) {
            ViewHolder viewHolder = (ViewHolder) view.getTag();
            if (viewHolder == null) {
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView) view.findViewById(R.id.imageView);
                view.setTag(viewHolder);
            }
            Glide.with(MarkHomeActivity.this).load(dataList.get(position).getCoverImageUrl()).into(viewHolder.imageView);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        @Override
        public void displaying(final int position) {
            if (lastDisplay < 0) {
                initSecene(position);
                lastDisplay = 0;
            } else if (lastDisplay != position) {
                transitionSecene(position);
                lastDisplay = position;
            }

            //监听标签改变事件
            tag.setOnTagChangeListener(new TagGroup.OnTagChangeListener() {
                /**
                 * 添加标签
                 */
                @Override
                public void onAppend(TagGroup tagGroup, String tag) {
                    tempTags = dataList.get(position).getTags();//保存先前的标签
                    tempTags = insertTag(tempTags, tag);//将新打的标签插入到原先的标签数组中
                    dataList.get(position).setTags(tempTags);
                    tempTags = null;//清空数组
                }

                /**
                 * 删除标签
                 */
                @Override
                public void onDelete(TagGroup tagGroup, String tag) {
                    tempTags = dataList.get(position).getTags();
                    tempTags = deleteTag(tempTags, tag);
                    dataList.get(position).setTags(tempTags);
                    tempTags = null;
                }
            });
        }

        @Override
        public void onItemClick(View view, int position) {
            super.onItemClick(view, position);
        }
    }

    /**
     * 相当于初始化数据，初始显示数据
     */
    private void initSecene(int position) {
        countIndicator.firstInit((position + 1) + "/" + dataList.size());
        markImg.firstInit(dataList.get(position).getCoverImageUrl());
        tag.setTags(colors, dataList.get(position).getTags());
    }

    /**
     * 当前图片转换到下一张图片
     */
    private void transitionSecene(int position) {
        if (transitionAnimator != null) {
            transitionAnimator.cancel();
        }
        countIndicator.saveNextPosition(position, (position + 1) + "/" + dataList.size());
        markImg.saveNextPosition(position, dataList.get(position).getCoverImageUrl());
        tag.setTags(colors, dataList.get(position).getTags());

        transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
        transitionAnimator.setDuration(300);
        transitionAnimator.start();
        transitionAnimator.addListener(animatorListener);
    }

    /**
     * 从asset中读取文件json数据，模拟网络传输数据
     */
    private void initDataList() {
        try {
            InputStream in = getAssets().open("mark.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity(itemJsonObject);
                    dataList.add(itemEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 属性动画
     */
    public void setTransitionValue(float transitionValue) {
        this.transitionValue = transitionValue;
        countIndicator.duringAnimation(transitionValue);
        markImg.duringAnimation(transitionValue);
    }

    public float getTransitionValue() {
        return transitionValue;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.sub_title:{
                customDialog = LayoutInflater.from(this).inflate(R.layout.dialog_delete, null);
                showMessage = (TextView) customDialog.findViewById(R.id.show_message);
                dialogTitle = (TextView) customDialog.findViewById(R.id.dialog_title);
                showMessage.setText("请选择操作类型");
                dialogTitle.setText("图片标注");
                delete = (Button) customDialog.findViewById(R.id.record_delete);
                cancel = (Button) customDialog.findViewById(R.id.record_cancel);
                delete.setText("提交");
                cancel.setText("保存");
                dialogOne = new Dialog(this);
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setView(customDialog);
                dialogOne = builder.create();
                dialogOne.show();
                delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogTwo = new Dialog(MarkHomeActivity.this);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MarkHomeActivity.this);
                        dialogMessage = LayoutInflater.from(MarkHomeActivity.this).inflate(R.layout.dialog_alter, null);
                        operateMessage = (TextView) dialogMessage.findViewById(R.id.operate_message);
                        operateMessage.setText("提交成功");
                        builder1.setView(dialogMessage);
                        dialogTwo = builder1.create();
                        dialogTwo.show();
                        Timer timer = new Timer();
                        timer.schedule(new Wait(), 1500);

                        submitTags();//提交操作

                        dialogOne.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialogTwo = new Dialog(MarkHomeActivity.this);
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(MarkHomeActivity.this);
                        dialogMessage = LayoutInflater.from(MarkHomeActivity.this).inflate(R.layout.dialog_alter, null);
                        operateMessage = (TextView) dialogMessage.findViewById(R.id.operate_message);
                        operateMessage.setText("保存成功");
                        builder1.setView(dialogMessage);
                        dialogTwo = builder1.create();
                        dialogTwo.show();
                        Timer timer = new Timer();
                        timer.schedule(new Wait(), 1500);

                        saveTags();//保存操作

                        dialogOne.dismiss();
                    }
                });
            }
            break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    class ViewHolder{
        public ImageView imageView;
    }

    /**
     * dialog延时
     */
    class Wait extends TimerTask {
        @Override
        public void run() {
            dialogTwo.dismiss();
        }
    }

    /**
     * 添加标签
     */
    private static String[] insertTag(String[] arr, String str) {
        int size = arr.length;
        String[] tmp = new String[size + 1];
        System.arraycopy(arr, 0, tmp, 0, size);
        tmp[size] = str;
        return tmp;
    }

    /**
     * 删除标签
     */
    private static String[] deleteTag(String[] arr, String str){
        List<String> tags = new LinkedList<String>();
        for(String tag : arr){
            tags.add(tag);
        }
        if(tags.contains(str)){
            tags.remove(str);
        }
        String[] newTags = {};
        return tags.toArray(newTags);
    }

    /**
     * 提交用户所打的标签
     */
    private void submitTags() {

    }

    /**
     * 保存用户所打的标签
     */
    private void saveTags() {

    }

}