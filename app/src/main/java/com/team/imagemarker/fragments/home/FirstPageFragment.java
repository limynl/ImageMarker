package com.team.imagemarker.fragments.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.activitys.home.MoreCategoryActivity;
import com.team.imagemarker.activitys.imagscan.PictureGroupScanActivity;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.activitys.saying.SayingScanActivity;
import com.team.imagemarker.adapters.home.HobbyPushAdapter;
import com.team.imagemarker.adapters.home.SystemPushAdapter;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.utils.MyGridView;
import com.team.imagemarker.viewpager.firstpager.CardData;
import com.team.imagemarker.viewpager.firstpager.CommentArrayAdapter;
import com.team.imagemarker.viewpager.firstpager.ExampleDataset;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;
import com.wangjie.rapidfloatingactionbutton.expandingcollection.ECBackgroundSwitcherView;
import com.wangjie.rapidfloatingactionbutton.expandingcollection.ECCardData;
import com.wangjie.rapidfloatingactionbutton.expandingcollection.ECPagerView;
import com.wangjie.rapidfloatingactionbutton.expandingcollection.ECPagerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/4/28.
 * email 1434117404@qq.com
 */

// implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener
public class FirstPageFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, SwipeRefreshLayout.OnRefreshListener, View.OnClickListener{
    private View view;
    private SwipeRefreshLayout refreshLayout;

    private ECPagerView ecPagerView;

//    private RelativeLayout titleBar;
//    private TextView title;
//    private ImageView leftIcon, rightIcon;

//    private ViewPager viewPager;
//    private CardPagerAdapter cardPagerAdapter;
//    private ShadowTransformer shadowTransformer;//ViewPager切换动画

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfaHelper;

    //系统推送
    private MyGridView SystemgridView;
    private List<CategoryModel> systemPushList = new ArrayList<>();
    private SystemPushAdapter adapterSystem;

    //兴趣推送
    private MyGridView hobbyGridView;
    private List<CategoryModel> hobbyPushList = new ArrayList<>();
    private SystemPushAdapter hobbyAdapter;


    private HobbyPushAdapter adapterHobby;

    private Button systemPushMore, hobbyPushMore;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_first_page, null);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.first_page_refresh);

//        titleBar = (RelativeLayout) view.findViewById(R.id.title_bar);
//        title = (TextView) view.findViewById(R.id.title);
//        leftIcon = (ImageView) view.findViewById(R.id.left_icon);
//        rightIcon = (ImageView) view.findViewById(R.id.right_icon);
//        viewPager = (ViewPager) view.findViewById(R.id.card_viewpager);

        rfaLayout = (RapidFloatingActionLayout) view.findViewById(R.id.rfa_layout);
        rfaButton = (RapidFloatingActionButton) view.findViewById(R.id.rfa_button);

//        titleBar.setBackgroundColor(getResources().getColor(R.color.theme));
//        titleBar.setAlpha(0);
//        title.setText("首页");
//        leftIcon.setVisibility(View.GONE);
//        rightIcon.setVisibility(View.GONE);

        SystemgridView = (MyGridView) view.findViewById(R.id.system_push);
        hobbyGridView = (MyGridView) view.findViewById(R.id.hobby_push);
//        recyclerView = (RecyclerView) view.findViewById(R.id.hobby_push);
        systemPushMore = (Button) view.findViewById(R.id.system_push_more);
        hobbyPushMore = (Button) view.findViewById(R.id.hobby_push_more);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        setDataToViewPager();//为ViewPager设置数据

        //初始化刷新控件
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setOnRefreshListener(this);

        systemPushMore.setOnClickListener(this);
        hobbyPushMore.setOnClickListener(this);

        setTopViewPager();
        setFlaotButton();//设置悬浮按钮
        setSystemDate();//设置系统推送数据
        setHobbyDate();//设置兴趣推送数据
    }

    /**
     * 设置顶部ViewPager
     */
    private void setTopViewPager() {
        ECPagerViewAdapter adapter = new ECPagerViewAdapter(getActivity(), new ExampleDataset().getDataset()) {
            @Override
            public void instantiateCard(LayoutInflater inflaterService, ViewGroup head, ListView list, final ECCardData data) {
                final CardData cardData = (CardData) data;
                CommentArrayAdapter commentArrayAdapter = new CommentArrayAdapter(getActivity(), cardData.getListItems());
                list.setAdapter(commentArrayAdapter);
                View gradient = new View(getActivity());
                gradient.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.MATCH_PARENT));
                gradient.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_head_gradient));
                head.addView(gradient);
                inflaterService.inflate(R.layout.simple_viewpager_head, head);
//                TextView title = (TextView) head.findViewById(R.id.title);//标题
                ImageView avatar = (ImageView) head.findViewById(R.id.avatar);//用户头像
                TextView name = (TextView) head.findViewById(R.id.name);//用户名
                TextView message = (TextView) head.findViewById(R.id.message);//用户信息
//                TextView viewsCount = (TextView) head.findViewById(R.id.socialViewsCount);//浏览量
//                TextView likesCount = (TextView) head.findViewById(R.id.socialLikesCount);//喜欢数量
//                TextView commentsCount = (TextView) head.findViewById(R.id.socialCommentsCount);//评论数量
//                title.setText(cardData.getHeadTitle());
                avatar.setImageResource(cardData.getPersonPictureResource());
                name.setText(cardData.getPersonName() + ":");
                message.setText(cardData.getPersonMessage());
//                viewsCount.setText(" " + cardData.getPersonViewsCount());
//                likesCount.setText(" " + cardData.getPersonLikesCount());
//                commentsCount.setText(" " + cardData.getPersonCommentsCount());
                head.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(final View v) {
                        Toast.makeText(getActivity(), "跳转到打标签界面", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        };

        ecPagerView = (ECPagerView) view.findViewById(R.id.ec_pager_element);
        ecPagerView.setPagerViewAdapter(adapter);
        ecPagerView.setBackgroundSwitcherView((ECBackgroundSwitcherView) view.findViewById(R.id.ec_bg_switcher_element));//设置背景转换
    }

    /**
     * 设置系统推送数据
     */
    private void setSystemDate() {
//        systemPushList.add(new CategoryModel(R.mipmap.system_push1, "这是标题", "这是简要信息"));
//        systemPushList.add(new CategoryModel(R.mipmap.system_push2, "这是标题", "这是简要信息"));
        systemPushList.add(new CategoryModel(R.mipmap.system_push3, "这是标题", "这是简要信息"));
        systemPushList.add(new CategoryModel(R.mipmap.system_push4, "这是标题", "这是简要信息"));
        adapterSystem = new SystemPushAdapter(getActivity(), systemPushList);
        SystemgridView.setAdapter(adapterSystem);
        SystemgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MarkHomeActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    /**
     * 设置兴趣推送数据
     */
    private void setHobbyDate(){
        hobbyPushList.add(new CategoryModel(R.mipmap.system_push1, "这是标题", "这是简要信息"));
        hobbyPushList.add(new CategoryModel(R.mipmap.system_push2, "这是标题", "这是简要信息"));
//        hobbyPushList.add(new CategoryModel(R.mipmap.system_push3, "这是标题", "这是简要信息"));
//        hobbyPushList.add(new CategoryModel(R.mipmap.system_push4, "这是标题", "这是简要信息"));
        hobbyAdapter = new SystemPushAdapter(getActivity(), hobbyPushList);
        hobbyGridView.setAdapter(hobbyAdapter);
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());//设置水平RecycleView
//        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        recyclerView.setLayoutManager(layoutManager);
//        recyclerView.setAdapter(adapterHobby);

    }

    /**
     * 为ViewPager设置数据
     */
//    private void setDataToViewPager() {
//        cardPagerAdapter = new CardPagerAdapter();
//        cardPagerAdapter.addCardItem(new CardItem(R.mipmap.image1, "这是一句说明性文字"));
//        cardPagerAdapter.addCardItem(new CardItem(R.mipmap.image2, "这是一句说明性文字"));
//        cardPagerAdapter.addCardItem(new CardItem(R.mipmap.image4, "这是一句说明性文字"));
//        cardPagerAdapter.addCardItem(new CardItem(R.mipmap.image1, "这是一句说明性文字"));
//
//        shadowTransformer = new ShadowTransformer(viewPager, cardPagerAdapter);
//        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
//        viewPager.setAdapter(cardPagerAdapter);
//        viewPager.setPageTransformer(false, shadowTransformer);
//        viewPager.setOffscreenPageLimit(3);
//        shadowTransformer.enableScaling(true);
//    }

    /**
     * 设置悬浮按钮
     */
    private void setFlaotButton() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(getActivity());
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("美图欣赏")
                .setResId(R.mipmap.ico_test_b)
                .setIconNormalColor(0xff056f00)
                .setIconPressedColor(0xff0d5302)
                .setLabelColor(getResources().getColor(R.color.font_light))
                .setWrapper(0)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("发表心情")
                .setResId(R.mipmap.ico_test_d)
                .setIconNormalColor(0xffff9100)
                .setIconPressedColor(0xffff9100)//FFB74D FFB74D
                .setLabelColor(getResources().getColor(R.color.font_light))
                .setWrapper(1)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(getActivity(), 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(getActivity(), 5));

        rfaHelper = new RapidFloatingActionHelper(getActivity(), rfaLayout, rfaButton, rfaContent).build();
    }

    /**
     * 悬浮按钮标签点击
     * @param position
     * @param item
     */
    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (position){
            case 0:{//美图欣赏
                Toast.makeText(getActivity(), "美图欣赏", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), PictureGroupScanActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case 1:{//发表心情
                Toast.makeText(getActivity(), "发表心情", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), SayingScanActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
        rfaHelper.toggleContent();
    }

    /**
     * 悬浮按钮图标点击
     * @param position
     * @param item
     */
    @Override
    public void onRFACItemIconClick(int position, RFACLabelItem item) {
        switch (position){
            case 0:{//美图欣赏
                Toast.makeText(getActivity(), "美图欣赏", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), PictureGroupScanActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case 1:{//发表心情
                Toast.makeText(getActivity(), "发表心情", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), SayingScanActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
        rfaHelper.toggleContent();
    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               refreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.system_push_more:{
                Intent intent = new Intent(getContext(), MarkHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pageTag", "firstPage");
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case R.id.hobby_push_more:{
                Intent intent = new Intent(getContext(), MoreCategoryActivity.class);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
        }
    }
}