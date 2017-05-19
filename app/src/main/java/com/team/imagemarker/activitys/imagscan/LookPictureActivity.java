package com.team.imagemarker.activitys.imagscan;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.imgscan.CommentAdapter;
import com.team.imagemarker.entitys.image.CommentInfoModel;
import com.team.imagemarker.entitys.image.LookDetailModel;
import com.team.imagemarker.utils.SoftInputMethodUtil;
import com.team.imagemarker.utils.WavyLineView;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;
import com.yalantis.contextmenu.lib.ContextMenuDialogFragment;
import com.yalantis.contextmenu.lib.MenuObject;
import com.yalantis.contextmenu.lib.MenuParams;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class LookPictureActivity extends FragmentActivity implements View.OnClickListener, View.OnTouchListener, OnMenuItemClickListener {
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private ViewPager imgViewPager;
    private TextView imgIndicator;
    private TagGroup imgTagGroup;
    private ListView imgComment;
    private TextView sendComment;
    private EditText editTextComment;
    private int tagIndex = 0;
    private WavyLineView mWavyLine;

    private List<LookDetailModel> list = new ArrayList<LookDetailModel>();
    private String[] imgTag = {"测试标签", "图片标签描述", "图片描述", "测试美图浏览"};
    private List<CommentInfoModel> commentList = new ArrayList<CommentInfoModel>();
    private CommentAdapter commentAdapter;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");

    private FragmentManager fragmentManager;
    private ContextMenuDialogFragment menuDialogFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_picture);
        fragmentManager = getSupportFragmentManager();
        bindView();//初始化视图
        setData();//加载数据
        setFloatMenu();//设置悬浮菜单
        SoftInputMethodUtil.HideSoftInput(editTextComment.getWindowToken());//隐藏软键盘
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        imgViewPager = (ViewPager) findViewById(R.id.detail_image);
        imgIndicator = (TextView) findViewById(R.id.image_indicator);
        imgTagGroup = (TagGroup) findViewById(R.id.image_tag);
        imgComment = (ListView) findViewById(R.id.detail_listview);
        sendComment = (TextView) findViewById(R.id.detail_send);
        editTextComment = (EditText) findViewById(R.id.detail_edit);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title.setText("图组详情");
        rightIcon.setImageResource(R.mipmap.three_more);
        leftIcon.setOnClickListener(this);
        rightIcon.setOnClickListener(this);
        editTextComment.setOnTouchListener(this);
        sendComment.setOnClickListener(this);

        // 波浪线设置
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        mWavyLine.setPeriod((float) (2 * Math.PI / 60));
        mWavyLine.setAmplitude(5);
        mWavyLine.setStrokeWidth(2);

    }

    private void setData() {
        //模拟数据
        list.add(new LookDetailModel(R.mipmap.system_push1, imgTag, null));
        list.add(new LookDetailModel(R.mipmap.system_push2, imgTag, null));
        list.add(new LookDetailModel(R.mipmap.system_push3, imgTag, null));
        list.add(new LookDetailModel(R.mipmap.system_push4, imgTag, null));
        list.add(new LookDetailModel(R.mipmap.system_push5, imgTag, null));
        list.add(new LookDetailModel(R.mipmap.system_push3, imgTag, null));
        imgViewPager.setOffscreenPageLimit(3);
        imgViewPager.setAdapter(new PictureScanAdapter());
        imgViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                updateIndicator(position);//当滑动时指示器改变，并且标签变化
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        updateIndicator(imgViewPager.getCurrentItem());//为第一张图片设置标签，并添加指示器

        //设置评论数据
        commentList.add(new CommentInfoModel("http://139.199.23.142:8080/TestShowMessage1/marker/test1.jpg", "Limynl", "2017-05-01 11:31:00", "用户评论测试"));
        commentList.add(new CommentInfoModel("http://139.199.23.142:8080/TestShowMessage1/marker/test1.jpg", "Limynl", "2017-05-01 11:31:00", "用户评论测试"));
        commentList.add(new CommentInfoModel("http://139.199.23.142:8080/TestShowMessage1/marker/test1.jpg", "Limynl", "2017-05-01 11:31:00", "用户评论测试"));
        commentList.add(new CommentInfoModel("http://139.199.23.142:8080/TestShowMessage1/marker/test1.jpg", "Limynl", "2017-05-01 11:31:00", "用户评论测试"));
        commentList.add(new CommentInfoModel("http://139.199.23.142:8080/TestShowMessage1/marker/test1.jpg", "Limynl", "2017-05-01 11:31:00", "用户评论测试"));
        commentList.add(new CommentInfoModel("http://139.199.23.142:8080/TestShowMessage1/marker/test1.jpg", "Limynl", "2017-05-01 11:31:00", "用户评论测试"));

        commentAdapter = new CommentAdapter(this, commentList);
        imgComment.setAdapter(commentAdapter);

    }

    /**
     * 设置悬浮菜单
     */
    private void setFloatMenu() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(true);
        menuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        menuDialogFragment.setItemClickListener(this);
    }

    private List<MenuObject> getMenuObjects(){
        List<MenuObject> menuObjects = new ArrayList<>();
        MenuObject close = new MenuObject();
        close.setResource(R.mipmap.ic_close);
        menuObjects.add(close);

        MenuObject share = new MenuObject("一键分享");
        share.setResource(R.mipmap.ic_close);
        menuObjects.add(share);

        MenuObject change = new MenuObject("换壁纸");
        change.setResource(R.mipmap.ic_close);
        menuObjects.add(change);
        return menuObjects;
    }

    /**
     * 图片浏览的指示器
     * @param position
     */
    private void updateIndicator(int position){
        int currentItem = imgViewPager.getCurrentItem() + 1;//得到当前ViewPager的位置
        int totalNum = imgViewPager.getAdapter().getCount();//得到总数
        imgIndicator.setText(String.valueOf(currentItem) + "/" + String.valueOf(totalNum));
        setImgTag(position);//设置标签
    }

    /**
     * 为每一张图片设置标签
     * @param position
     */
    private void setImgTag(int position) {
        int tagSize = imgTag.length;
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize; tagIndex++, j++) {
            tags[j] = imgTag[tagIndex % imgTag.length];
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);//随机生成标签颜色
        imgTagGroup.setTags(colors, tags);
    }

    /**
     * 显示系统软键盘
     */
    private void showKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) editTextComment.getContext().getSystemService(INPUT_METHOD_SERVICE);
        inputManager.showSoftInput(editTextComment, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                onBackPressed();
            }
            break;
            case R.id.right_icon:{
                if(fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null){
                    menuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);
                }
            }
            break;
            case R.id.detail_send:{
                Toast.makeText(this, "发送", Toast.LENGTH_SHORT).show();
                String str=sdf.format(new Date());
                String commentContent = editTextComment.getText().toString();
            }
            break;
        }
    }

    /**
     *触摸编辑框弹出软键盘
     */
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(v.getId() == R.id.detail_edit){
            showKeyboard();//弹出软键盘
        }
        return false;
    }

    /**
     * 浮动按钮点击
     * @param clickedView
     * @param position
     */
    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Toast.makeText(this, "您点击的是第" + position + "个", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if(menuDialogFragment != null && menuDialogFragment.isAdded()){
            menuDialogFragment.dismiss();
        }else{
            this.finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
        }
    }

    /**
     * 图片浏览适配器
     */
    class PictureScanAdapter extends PagerAdapter {
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(LookPictureActivity.this);
            imageView.setBackgroundResource(list.get(position).getImgId());
            imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewPager.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
}