package com.team.imagemarker.activitys;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.bases.BaseListAdapter;
import com.team.imagemarker.utils.CustomViewPager;
import com.team.imagemarker.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

import static com.team.imagemarker.R.id.tv_change_items;

/**
 * Created by Lmy on 2017/4/1.
 * email 1434117404@qq.com
 */

public class UpdateUserMessageActivity extends Activity implements View.OnClickListener{
    private TextView title;
    private ImageView leftIcon;
    private ImageView rightIcon;
    private LinearLayout popupWindowLayout;

    private TextView userHobby;

    private ImageView userHobbySelect;
    private View contentView;
    private PopupWindow popupWindow;

    private TextView userWaring;
    private ViewPager vp_details;
    private MyGridView grid_selected;
    private ParentIconAdapter parentIconAdapter = new ParentIconAdapter();
    private ChildrenIconAdapter adapter_one, adapter_two, adapter_three;
    private List<String> parentList = new ArrayList<String>();//选中的标签
    final int length = 3;
    private MyGridView[] mGridView = new MyGridView[length];//对应于3个ViewPager
    private MyPagerAdapter mMyadapter;
    private List<View> listViews;
    private ArrayList<String> data_one, data_two, data_three;//各个ViewPager对应的GridView中的数据
    private int mSumOne = 1;//换一批数目调整
    private TextView changeData;

    //更新父表与子表中的数据
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case 1:// 删除
                    adapter_one.notifyDataSetChanged();
                    adapter_two.notifyDataSetChanged();
                    adapter_three.notifyDataSetChanged();
                    parentIconAdapter.notifyDataSetChanged();
                    break;
                case 2:// 添加
                    parentIconAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user_message);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        userHobby = (TextView) findViewById(R.id.user_hobby);

        userHobbySelect = (ImageView) findViewById(R.id.user_hobby_select);

        title.setText("个人资料");
        title.setTextColor(Color.parseColor("#101010"));
        leftIcon.setImageResource(R.drawable.back);
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
        userHobbySelect.setOnClickListener(this);

        initData();
        //显示PopupWindow
        setPopupWindow();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                UpdateUserMessageActivity.this.finish();
            }
            break;
            case R.id.user_hobby_select:{
                parentList.clear();//清空上一次选中的数据
                showCenterPopupWindow(v);
            }
            break;
            case tv_change_items:{
                if (getDataOne().size() == 0) {
                    Toast.makeText(getApplicationContext(), "无资讯标签数据", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    if (getDataOne().size() <= 12) {
                        vp_details.setCurrentItem(0);
                        changeData.setVisibility(View.GONE);
                    } else if (getDataOne().size() > 12
                            && getDataOne().size() <= 24) {
                        if (mSumOne < 2) {
                            vp_details.setCurrentItem(mSumOne);
                            mSumOne++;
                        } else {
                            mSumOne = 0;
                            vp_details.setCurrentItem(0);
                        }
                    } else {
                        if (mSumOne < 3) {
                            vp_details.setCurrentItem(mSumOne);
                            mSumOne++;
                        } else {
                            mSumOne = 0;
                            vp_details.setCurrentItem(0);
                        }
                    }
                }
            }
            break;
        }
    }

    private void initData() {
        listViews = new ArrayList<View>();
        listViews.add(LayoutInflater.from(this).inflate(R.layout.purchase_gridview, null));
        listViews.add(LayoutInflater.from(this).inflate(R.layout.purchase_gridview, null));
        listViews.add(LayoutInflater.from(this).inflate(R.layout.purchase_gridview, null));
    }

    private void setPopupWindow() {
        contentView = LayoutInflater.from(this).inflate(R.layout.user_hobby_layout, null);
        popupWindowLayout = (LinearLayout) contentView.findViewById(R.id.popupwindow_layout);
        userWaring = (TextView) contentView.findViewById(R.id.waring_user);
        popupWindowLayout.setBackgroundColor(Color.parseColor("#e0e0e0"));
        changeData = (TextView) contentView.findViewById(tv_change_items);
        changeData.setOnClickListener(this);

        grid_selected = (MyGridView) contentView.findViewById(R.id.grid_selected);
        vp_details = (CustomViewPager) contentView.findViewById(R.id.vp_details);

        if(parentList.size() > 0){
            userWaring.setVisibility(View.GONE);
        }

        //加载ViewPager
        mMyadapter = new MyPagerAdapter(listViews);//GridView的list集合
        vp_details.setAdapter(mMyadapter);//加载三页的Viewpager
        vp_details.setCurrentItem(0);//初始化为第一页

        data_one = new ArrayList<String>();
        data_two = new ArrayList<String>();
        data_three = new ArrayList<String>();
        adapter_one = new ChildrenIconAdapter(UpdateUserMessageActivity.this, data_one);
        adapter_two = new ChildrenIconAdapter(UpdateUserMessageActivity.this, data_two);
        adapter_three = new ChildrenIconAdapter(UpdateUserMessageActivity.this, data_three);
        for (int i = 0; i < 3; i++) {
            mGridView[i] = (MyGridView) listViews.get(i).findViewById(R.id.gridview);
        }

            if (getDataOne().size() <= 12) {
                changeData.setVisibility(View.GONE);
                for (int i = 0; i < getDataOne().size(); i++) {
                    data_one.add(getDataOne().get(i).toString().trim());
                }
            } else if (getDataOne().size() > 12 && getDataOne().size() <= 24) {
                for (int i = 0; i < 12; i++) {
                    data_one.add(getDataOne().get(i).toString().trim());
                }
                for (int i = 12; i < getDataOne().size(); i++) {
                    data_two.add(getDataOne().get(i).toString().trim());
                }
            } else if (getDataOne().size() > 24) {
                for (int i = 0; i < 12; i++) {
                    data_one.add(getDataOne().get(i).toString().trim());
                }
                for (int i = 12; i < 24; i++) {
                    data_two.add(getDataOne().get(i).toString().trim());
                }
                for (int i = 24; i < 36 && i < getDataOne().size(); i++) {
                    data_three.add(getDataOne().get(i).toString().trim());
                }
            }

            for (int i = 0; i < length; i++) {
                if (i == 0) {
                    mGridView[i].setAdapter(adapter_one);
                }
                if (i == 1) {
                    mGridView[i].setAdapter(adapter_two);
                }
                if (i == 2) {
                    mGridView[i].setAdapter(adapter_three);
                }
            }
            grid_selected.setAdapter(parentIconAdapter);
        }

    public void showCenterPopupWindow(View view) {
        popupWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));
        // 设置PopupWindow以外部分的背景颜色  有一种变暗的效果
        final WindowManager.LayoutParams wlBackground = getWindow().getAttributes();
        wlBackground.alpha = 0.4f;      // 0.0 完全不透明,1.0完全透明
        getWindow().setAttributes(wlBackground);
        // 当PopupWindow消失时,恢复其为原来的颜色
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                wlBackground.alpha = 1.0f;
                getWindow().setAttributes(wlBackground);
            }
        });
        //设置PopupWindow进入和退出动画
        popupWindow.setAnimationStyle(R.style.anim_popup_centerbar);
        // 设置PopupWindow显示在中间
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    private ArrayList<String> getDataOne() {
        ArrayList<String> result = new ArrayList<String>();
        for (int i = 0; i < 50; i++) {
            result.add("爱好选择" + i);
        }
        return result;
    }

    public class MyPagerAdapter extends PagerAdapter {
        public List<View> mListViews;

        public MyPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mListViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View v = mListViews.get(position);
            ViewGroup parent = (ViewGroup) v.getParent();
            if (parent != null) {
                parent.removeAllViews();
            }
            container.addView(v);
            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view.equals(object);
        }

    }

    /**
     * childgridview图片适配
     * 待选择数据
     */
    private class ChildrenIconAdapter extends BaseListAdapter<String> {
        private List<String> mData;

        public ChildrenIconAdapter(Context context, List<String> mData) {
            super(context, mData);
            this.mData = mData;
        }

        @Override
        public String getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        private class ViewHolder {
            ImageView iv_delete;
            TextView tv_iconname;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.home_custom_grid_item, null);
                holder.tv_iconname = (TextView) convertView.findViewById(R.id.tv_intent_iconname);
                holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.iv_delete.setVisibility(View.GONE);
            if (parentList.contains(mData.get(position))) {
                holder.tv_iconname.setEnabled(false);
                holder.tv_iconname.setTextColor(Color.parseColor("#808080"));
            } else {
                holder.tv_iconname.setEnabled(true);
            }
            holder.tv_iconname.setText(mData.get(position));
            holder.tv_iconname.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (parentList.size() >= 20) {
                        Toast toast = Toast.makeText(getApplicationContext(), "最多可选择3个特色标签", Toast.LENGTH_LONG);
                        toast.show();
                        return;
                    }
                    v.setEnabled(false);//将其设置为不能操作
                    parentList.add(mData.get(position));
                    if(parentList.size() > 0){
                        userWaring.setVisibility(View.GONE);
                    }
                    mHandler.sendEmptyMessage(2);//更新父GridView的数据
                }
            });
            return convertView;
        }

        @Override
        protected View getItemView(View convertView, int position) {
            return null;
        }

        public void update(List<String> values) {
            mData = values;
            notifyDataSetInvalidated();
            notifyDataSetChanged();
        }
    }

    /**
     * parentgridview图片适配
     * 已选择的数据
     */
    private class ParentIconAdapter extends BaseAdapter {
        @Override
        public String getItem(int position) {
            return parentList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return parentList.size();
        }

        private class ViewHolder {
            ImageView iv_delete;
            TextView tv_iconname;
            RelativeLayout rl_item;
        }

        @Override
        public View getView(final int position, View convertView,
                            ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_custom_grid_item, null);
                holder.tv_iconname = (TextView) convertView.findViewById(R.id.tv_intent_iconname);
                holder.iv_delete = (ImageView) convertView.findViewById(R.id.iv_delete_item);
                holder.rl_item = (RelativeLayout) convertView.findViewById(R.id.rl_item);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv_iconname.setBackgroundResource(R.drawable.shape_brand_selected);
            holder.iv_delete.setVisibility(View.VISIBLE);

            holder.tv_iconname.setText(parentList.get(position));
            holder.rl_item.setClickable(true);
            holder.rl_item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentList.remove(position);
                    if(parentList.size() == 0){
                        userWaring.setVisibility(View.VISIBLE);
                    }
                    mHandler.sendEmptyMessage(1);
                }
            });
            return convertView;
        }
    }

}