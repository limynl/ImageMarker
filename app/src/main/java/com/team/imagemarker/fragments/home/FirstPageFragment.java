package com.team.imagemarker.fragments.home;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.home.MoreCategoryActivity;
import com.team.imagemarker.activitys.imagscan.ImgScanMainActivity;
import com.team.imagemarker.activitys.imagscan.PictureGroupScanFragment;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.activitys.saying.SayingScanActivity;
import com.team.imagemarker.adapters.CardPagerAdapter;
import com.team.imagemarker.adapters.ShadowTransformer;
import com.team.imagemarker.adapters.home.HobbyPushAdapter;
import com.team.imagemarker.adapters.home.SystemPushAdapter;
import com.team.imagemarker.entitys.CardItem;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.utils.MyGridView;
import com.team.imagemarker.utils.marker.FadeTransitionImageView;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.team.imagemarker.R.id.hobby_push;

/**
 * Created by Lmy on 2017/4/28.
 * email 1434117404@qq.com
 */

public class FirstPageFragment extends Fragment implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener, View.OnClickListener{
    private View view;

    private ViewPager viewPager;
    private CardPagerAdapter cardPagerAdapter;
    private ShadowTransformer shadowTransformer;//ViewPager切换动画
    private List<String> backgroundList = new ArrayList<>();
    private float transitionValue;

    private ObjectAnimator transitionAnimator;
    private Animator.AnimatorListener animatorListener;
    private FadeTransitionImageView viewPagerBackground;
    private int lastDisplay = -1;

    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfaHelper;

    //系统推送
    private MyGridView SystemgridView;
    private List<CategoryModel> systemPushList = new ArrayList<>();
    private static List<MarkerModel> systemPushDatas = new ArrayList<>();
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
        viewPager = (ViewPager) view.findViewById(R.id.card_viewpager);
        viewPagerBackground = (FadeTransitionImageView) view.findViewById(R.id.viewPager_background);

        rfaLayout = (RapidFloatingActionLayout) view.findViewById(R.id.rfa_layout);
        rfaButton = (RapidFloatingActionButton) view.findViewById(R.id.rfa_button);

        SystemgridView = (MyGridView) view.findViewById(R.id.system_push);
        hobbyGridView = (MyGridView) view.findViewById(hobby_push);
        systemPushMore = (Button) view.findViewById(R.id.system_push_more);
        hobbyPushMore = (Button) view.findViewById(R.id.hobby_push_more);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDataFromNet(null);//系统推送数据
        setDataToViewPager();//为ViewPager设置数据
        initAnimationListener();

        setFlaotButton();//设置悬浮按钮
        setSystemDate();//设置系统推送数据
        setHobbyDate();//设置兴趣推送数据
    }

    /**
     * 设置系统推送数据
     */
    private void setSystemDate() {

//        systemPushList.add(new CategoryModel(R.mipmap.system_push1, "这是标题", "这是简要信息"));
//        systemPushList.add(new CategoryModel(R.mipmap.system_push2, "这是标题", "这是简要信息"));
        /*systemPushList.add(new CategoryModel(R.mipmap.system_push3, "这是标题", "这是简要信息"));
        systemPushList.add(new CategoryModel(R.mipmap.system_push4, "这是标题", "这是简要信息"));*/
        adapterSystem = new SystemPushAdapter(getActivity(), systemPushDatas);
        SystemgridView.setAdapter(adapterSystem);

        SystemgridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MarkHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pageTag", "firstPage");
                bundle.putSerializable("item", systemPushDatas.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    /**
     * 设置兴趣推送数据
     */
    private void setHobbyDate(){
//        hobbyPushList.add(new CategoryModel(R.mipmap.system_push1, "这是标题", "这是简要信息"));
//        hobbyPushList.add(new CategoryModel(R.mipmap.system_push2, "这是标题", "这是简要信息"));
//        hobbyPushList.add(new CategoryModel(R.mipmap.system_push3, "这是标题", "这是简要信息"));
//        hobbyPushList.add(new CategoryModel(R.mipmap.system_push4, "这是标题", "这是简要信息"));
        hobbyAdapter = new SystemPushAdapter(getActivity(), systemPushDatas);
        hobbyGridView.setAdapter(hobbyAdapter);
        hobbyGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getContext(), MarkHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pageTag", "firstPage");
                bundle.putSerializable("item", systemPushDatas.get(position));
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    /**
     * 为ViewPager设置数据
     */
    private void setDataToViewPager() {
        cardPagerAdapter = new CardPagerAdapter(getContext());
        cardPagerAdapter.addCardItem(new CardItem("http://img.hb.aicdn.com/5342e50c6b6876b7cfde7dcdc3f89b290997624f37c56-qoOZli_fw658", "这是一句说明性文字"));
        cardPagerAdapter.addCardItem(new CardItem("http://img.hb.aicdn.com/04e84f35444738307f4acf872138daca92e4c4bb2abe0-KB7srY_fw658", "这是一句说明性文字"));
        cardPagerAdapter.addCardItem(new CardItem("http://attachments.gfan.com/forum/attachments2/day_110422/1104222243bffe391469c3bee4.jpg", "这是一句说明性文字"));
        cardPagerAdapter.addCardItem(new CardItem("http://img.hb.aicdn.com/226ce6243ae187c107e1a22cfa7cc0763fe2bda9908c4-8foxjv_fw658", "这是一句说明性文字"));
        cardPagerAdapter.addCardItem(new CardItem("http://img.hb.aicdn.com/3c515a7e4fac799d7f62bf6be045d8a287cb16f214b91-Y8N5wB_fw658", "这是一句说明性文字"));

        backgroundList.add("http://img.hb.aicdn.com/5342e50c6b6876b7cfde7dcdc3f89b290997624f37c56-qoOZli_fw658");
        backgroundList.add("http://img.hb.aicdn.com/04e84f35444738307f4acf872138daca92e4c4bb2abe0-KB7srY_fw658");
        backgroundList.add("http://attachments.gfan.com/forum/attachments2/day_110422/1104222243bffe391469c3bee4.jpg");
        backgroundList.add("http://img.hb.aicdn.com/226ce6243ae187c107e1a22cfa7cc0763fe2bda9908c4-8foxjv_fw658");
        backgroundList.add("http://img.hb.aicdn.com/3c515a7e4fac799d7f62bf6be045d8a287cb16f214b91-Y8N5wB_fw658");

        shadowTransformer = new ShadowTransformer(viewPager, cardPagerAdapter);
        viewPager.setPageMargin((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 30, getResources().getDisplayMetrics()));
        viewPager.setAdapter(cardPagerAdapter);
        viewPager.setPageTransformer(false, shadowTransformer);
        viewPager.setOffscreenPageLimit(3);
        shadowTransformer.enableScaling(true);
        viewPager.setCurrentItem(1);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (lastDisplay < 0) {
                    viewPagerBackground.firstInit(backgroundList.get(position));
                    lastDisplay = 0;
                } else if (lastDisplay != position) {
                    if (transitionAnimator != null) {
                        transitionAnimator.cancel();
                    }
                    viewPagerBackground.saveNextPosition(position, backgroundList.get(position));
                    transitionAnimator = ObjectAnimator.ofFloat(this, "transitionValue", 0.0f, 1.0f);
                    transitionAnimator.setDuration(300);
                    transitionAnimator.start();
                    transitionAnimator.addListener(animatorListener);
                    lastDisplay = position;
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
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
                viewPagerBackground.onAnimationEnd();
            }

            @Override
            public void onAnimationCancel(Animator animation) {}

            @Override
            public void onAnimationRepeat(Animator animation) {}
        };
    }

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
                startActivity(new Intent(getActivity(), ImgScanMainActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case 1:{//发表心情
                startActivity(new Intent(getActivity(), SayingScanActivity.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
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
                startActivity(new Intent(getActivity(), PictureGroupScanFragment.class));
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            break;
            case 1:{//发表心情
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
//    @Override
//    public void onRefresh() {
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//               refreshLayout.setRefreshing(false);
//            }
//        }, 3000);
//    }

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

    /**
     * 系统推送数据接收
     */
    private void getDataFromNet(String dataUrl){
        String url  = "http://obs.myhwclouds.com/look.admin.info/systemPush.txt";
        VolleyRequestUtil.RequestGet(getContext(), url, "systemPush", new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(new String(result.getBytes("ISO-8859-1"), "UTF-8"));
                    String tag = new String(object.optString("TAG").getBytes("ISO-8859-1"), "UTF-8");
                    JSONArray array = object.optJSONArray("picture");
                    Gson gson = null;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.optJSONObject(i);
                        gson = new Gson();
                        MarkerModel model = gson.fromJson(object1.toString(), MarkerModel.class);
                        systemPushDatas.add(model);
                        adapterSystem.notifyDataSetChanged();
                        hobbyAdapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }


//    public void setTransitionValue(float transitionValue) {
//        this.transitionValue = transitionValue;
//        viewPagerBackground.duringAnimation(transitionValue);
//    }
//
//    public float getTransitionValue() {
//        return transitionValue;
//    }

}