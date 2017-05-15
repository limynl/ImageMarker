package com.team.imagemarker.activitys.mark;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.CardPagerAdapter;
import com.team.imagemarker.adapters.ShadowTransformer;
import com.team.imagemarker.entitys.CardItem;

public class MarkHomeActivity extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon, rightIcon;
    private RelativeLayout titleBar;

    private ViewPager viewPager;
    private CardPagerAdapter cardPagerAdapter;
    private ShadowTransformer shadowTransformer;//ViewPager切换动画

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_home);
        bindView();
        setData();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        viewPager = (ViewPager) findViewById(R.id.show_img_viewpager);

        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
        title.setText("图片标记");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
    }

    private void setData() {
        cardPagerAdapter = new CardPagerAdapter(this);
        cardPagerAdapter.addCardItem(new CardItem("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner1.jpg", "这是一句说明性文字"));
        cardPagerAdapter.addCardItem(new CardItem("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner2.jpg", "这是一句说明性文字"));
        cardPagerAdapter.addCardItem(new CardItem("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner3.jpg", "这是一句说明性文字"));
        cardPagerAdapter.addCardItem(new CardItem("http://139.199.23.142:8080/TestShowMessage1/marker/navbanner/banner4.jpg", "这是一句说明性文字"));

        shadowTransformer = new ShadowTransformer(viewPager, cardPagerAdapter);
        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
        viewPager.setAdapter(cardPagerAdapter);
        viewPager.setPageTransformer(false, shadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        shadowTransformer.enableScaling(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                this.finish();
            }
            break;
        }
    }
}