package com.team.imagemarker.activitys.imagscan;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.SharePopBaseAdapter;
import com.team.imagemarker.adapters.imgscan.CommentAdapter;
import com.team.imagemarker.entitys.image.CommentInfoModel;
import com.team.imagemarker.entitys.image.LookDetailModel;
import com.team.imagemarker.entitys.share.SharePopBean;
import com.team.imagemarker.utils.SoftInputMethodUtil;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.WavyLineView;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class LookPictureActivity extends FragmentActivity implements View.OnClickListener, View.OnTouchListener, AdapterView.OnItemClickListener{
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
    private List<CommentInfoModel> commentList = new ArrayList<CommentInfoModel>();
    private CommentAdapter commentAdapter;

    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private PopupMenu popupMenu;
    private String[] tabs = {"一键分享", "设为壁纸", "关闭"};

    private PopupWindow pw;
    private View popView;
    private GridView gv;
    private TextView carName;
    private String[] names = {"QQ", "新浪", "微信"};
    private int[] iconId = {R.mipmap.ssdk_oks_classic_qq, R.mipmap.ssdk_oks_classic_sinaweibo, R.mipmap.ssdk_oks_classic_wechat};
    private List<SharePopBean> shareBeanList = new ArrayList<SharePopBean>();
    private SharePopBaseAdapter shareBaseAdapter;
    private ToastUtil toastUtil = new ToastUtil();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_picture);
        popView= LayoutInflater.from(this).inflate(R.layout.share_grid, null);
        bindView();//初始化视图
        setData();//加载数据
        SoftInputMethodUtil.HideSoftInput(editTextComment.getWindowToken());//隐藏软键盘
        setShareApp();
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

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme1));
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

        popupMenu = new PopupMenu(this, tabs);

    }

    private void setData() {
        //模拟数据
        list.add(new LookDetailModel(R.mipmap.system_push3, new String[]{"蓝蓝的湖水", "伟大的高山", "茂密的丛林"}, null));
        list.add(new LookDetailModel(R.mipmap.system_push4, new String[]{"金门大桥", "宁静的大海", "耀眼的霓虹灯", "繁华的城市"}, null));
        list.add(new LookDetailModel(R.mipmap.system_push5, new String[]{"洁白的盘子", "好吃的红烧肉", "一只手"}, null));
        list.add(new LookDetailModel(R.mipmap.hot6, new String[]{"湖水", "船只", "房子"}, null));
        list.add(new LookDetailModel(R.mipmap.system_push2, new String[]{"宫殿"}, null));
        list.add(new LookDetailModel(R.mipmap.hot1, new String[]{"橘子", "橘子汁", "托盘"}, null));
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
        commentList.add(new CommentInfoModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/手机-桌子.jpg", "Rose", "2017-05-01 11:31:00", "这个图片真好看"));
        commentList.add(new CommentInfoModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-桌子-鼠标-手机.jpg", "Jack", "2017-05-01 11:31:00", "美图浏览"));
        commentList.add(new CommentInfoModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-椅子-眼睛-桌子.jpg", "Tom", "2017-05-01 11:31:00", "用户评论测试"));
        commentList.add(new CommentInfoModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/笔记本电脑-水杯-桌子-手机.jpg", "Lili", "2017-05-01 11:31:00", "美图欣赏"));
        commentList.add(new CommentInfoModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-22/矿用挖掘机-天空-机器.jpg", "Json", "2017-05-01 11:31:00", "图片真漂亮"));
        commentList.add(new CommentInfoModel("http://obs.myhwclouds.com/look.admin.image/华为/2017-5-20/键盘-鼠标-桌子.jpg", "Gson", "2017-05-01 11:31:00", "用户评论测试"));

        commentAdapter = new CommentAdapter(this, commentList);
        imgComment.setAdapter(commentAdapter);

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
//        int tagSize = imgTag.length;
//        int tagSize = list.get(position).getImgTag().length;
//        String[] tags = new String[tagSize];
//        for (int j = 0; j < tagSize; tagIndex++, j++) {
//            tags[j] = imgTag[tagIndex % imgTag.length];
//        }
        List<TagColor> colors = TagColor.getRandomColors(7);//随机生成标签颜色
        imgTagGroup.setTags(colors, list.get(position).getImgTag());
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
                popupMenu.showLocation(R.id.right_icon);
                popupMenu.setOnItemClickListener(new PopupMenu.OnItemClickListener() {
                    @Override
                    public void onClick(PopupMenu.MENUITEM item, String str) {
                        if(str.equals("一键分享")){
                            pw = getPopWindow(popView);
                        }else if(str.equals("设为壁纸")){
                            toastUtil.Short(LookPictureActivity.this, "壁纸设置成功").show();
                        }
                    }
                });
            }
            break;
            case R.id.detail_send:{
                String str=sdf.format(new Date());
                String commentContent = editTextComment.getText().toString();
                if(!commentContent.equals("")){
                    commentList.add(0, new CommentInfoModel("http://obs.myhwclouds.com/look.admin.info/man_head.jpg", "老马识图", str, commentContent));
                    commentAdapter.notifyDataSetChanged();
                    editTextComment.setText("");
                }else{
                    toastUtil.Short(this, "请输入评论的内容!").show();
                }
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


    @Override
    public void onBackPressed() {
            this.finish();
            overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String name = shareBeanList.get(position).getName();
        if(TextUtils.equals(name, "QQ")){
            Platform qq = ShareSDK.getPlatform(this, QQ.NAME);
            QQ.ShareParams sp = new QQ.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);
            sp.setTitle("老马识图——美图分享");
            sp.setTitleUrl("http://139.199.23.142:8080/TestShowMessage1/car.apk");
            qq.share(sp);
            pw.dismiss();
        }else if (TextUtils.equals(name, "新浪")){
            SinaWeibo.ShareParams sp = new SinaWeibo.ShareParams();
            sp.setText("老马识图——美图分享");
            sp.setImagePath("http://139.199.23.142:8080/TestShowMessage1/logo.png");
            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
            weibo.share(sp);
            pw.dismiss();
        }else if(TextUtils.equals(name, "微信")){
            pw.dismiss();
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

    /**
     * 设置弹窗
     * @param view
     * @return
     */
    private PopupWindow getPopWindow(View view){
        PopupWindow popupWindow=new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        backgroundAlpha((float) 0.5);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((float) 1.0);
            }
        });
        return popupWindow;
    }

    /**
     * 背景半透明
     * @param bgAlpha
     */
    private void backgroundAlpha(float bgAlpha){
        WindowManager.LayoutParams lp = this.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        this.getWindow().setAttributes(lp);
    }

    private void setShareApp() {
        shareBeanList.add(new SharePopBean(iconId[0], names[0]));
        shareBeanList.add(new SharePopBean(iconId[1], names[1]));
        shareBeanList.add(new SharePopBean(iconId[2], names[2]));

        gv=(GridView)popView.findViewById(R.id.share_grid);
        shareBaseAdapter = new SharePopBaseAdapter(this, shareBeanList);
        gv.setAdapter(shareBaseAdapter);
        gv.setOnItemClickListener(this);
    }
}