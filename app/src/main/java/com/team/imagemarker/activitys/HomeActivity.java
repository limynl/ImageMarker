package com.team.imagemarker.activitys;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.wangjie.androidbucket.utils.ABTextUtil;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionButton;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionHelper;
import com.wangjie.rapidfloatingactionbutton.RapidFloatingActionLayout;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RFACLabelItem;
import com.wangjie.rapidfloatingactionbutton.contentimpl.labellist.RapidFloatingActionContentLabelList;

import java.util.ArrayList;
import java.util.List;


/**
 * 主界面
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class HomeActivity extends Activity implements RapidFloatingActionContentLabelList.OnRapidFloatingActionContentLabelListListener{
    private RapidFloatingActionLayout rfaLayout;
    private RapidFloatingActionButton rfaButton;
    private RapidFloatingActionHelper rfaHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        bindView();//初始化视图
        setFlaotButton();//设置悬浮按钮
    }

    /**
     * 初始化视图
     */
    private void bindView() {
        rfaLayout = (RapidFloatingActionLayout) findViewById(R.id.rfa_layout);
        rfaButton = (RapidFloatingActionButton) findViewById(R.id.rfa_button);
    }

    /**
     * 设置悬浮按钮
     */
    private void setFlaotButton() {
        RapidFloatingActionContentLabelList rfaContent = new RapidFloatingActionContentLabelList(this);
        rfaContent.setOnRapidFloatingActionContentLabelListListener(this);
        List<RFACLabelItem> items = new ArrayList<>();
        items.add(new RFACLabelItem<Integer>()
                .setLabel("发表心情")
                .setResId(R.mipmap.ico_test_b)
                .setIconNormalColor(0xff056f00)
                .setIconPressedColor(0xff0d5302)
                .setLabelColor(0xff056f00)
                .setWrapper(0)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("美图欣赏")
                .setResId(R.mipmap.ico_test_d)
                .setIconNormalColor(0xffd84315)
                .setIconPressedColor(0xffbf360c)
                .setLabelColor(0xffd84315)
                .setWrapper(1)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("图组选择")
                .setResId(R.mipmap.ico_test_b)
                .setIconNormalColor(0xff4e342e)
                .setIconPressedColor(0xff3e2723)
                .setLabelColor(0xff4e342e)
                .setWrapper(2)
        );

        items.add(new RFACLabelItem<Integer>()
                .setLabel("个人中心")
                .setResId(R.mipmap.ico_test_d)
                .setIconNormalColor(0xffff9100)
                .setIconPressedColor(0xffff9100)//FFB74D FFB74D
                .setLabelColor(0xffff9100)
                .setWrapper(3)
        );

        rfaContent
                .setItems(items)
                .setIconShadowRadius(ABTextUtil.dip2px(this, 5))
                .setIconShadowColor(0xff888888)
                .setIconShadowDy(ABTextUtil.dip2px(this, 5));

        rfaHelper = new RapidFloatingActionHelper(this, rfaLayout, rfaButton, rfaContent).build();
    }

    /**
     * 悬浮按钮标签点击
     * @param position
     * @param item
     */
    @Override
    public void onRFACItemLabelClick(int position, RFACLabelItem item) {
        switch (position){
            case 0:{//发表心情
                Toast.makeText(this, "发表心情", Toast.LENGTH_SHORT).show();
            }
            break;
            case 1:{//美图欣赏
                Toast.makeText(this, "美图欣赏", Toast.LENGTH_SHORT).show();
            }
            break;
            case 2:{//图组选择
                Toast.makeText(this, "图组选择", Toast.LENGTH_SHORT).show();
            }
            break;
            case 3:{//个人中心
                startActivity(new Intent(HomeActivity.this, UserCenterActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
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
            case 0:{//发表心情
                Toast.makeText(this, "发表心情", Toast.LENGTH_SHORT).show();
            }
            break;
            case 1:{//美图欣赏
                Toast.makeText(this, "美图欣赏", Toast.LENGTH_SHORT).show();
            }
            break;
            case 2:{//图组选择
                Toast.makeText(this, "图组选择", Toast.LENGTH_SHORT).show();
            }
            break;
            case 3:{//个人中心
                startActivity(new Intent(HomeActivity.this, UserCenterActivity.class));
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
        rfaHelper.toggleContent();
    }
}