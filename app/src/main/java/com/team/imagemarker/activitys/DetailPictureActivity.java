package com.team.imagemarker.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.CardItem;
import com.team.imagemarker.utils.MyGridView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class DetailPictureActivity extends Activity implements View.OnClickListener{
    private ImageView closeScan;
    private TextView indicator;//指示器
    private ViewPager pictureGroup;
    private MyGridView gridView;

    private List<CardItem> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_picture);
        bindView();
        setData();
    }

    /**
     * 初始化视图
     */
    private void bindView() {
        closeScan = (ImageView) findViewById(R.id.close_scan);
        indicator = (TextView) findViewById(R.id.indicator_tv);
        pictureGroup = (ViewPager) findViewById(R.id.detail_picture_viewpager);
        gridView = (MyGridView) findViewById(R.id.scan_marker);

        closeScan.setOnClickListener(this);
    }

    /**
     * 设置ViewPager
     */
    private void setData() {
        list = new ArrayList<>();

        list.add(new CardItem(R.mipmap.image1, "动物"));
        list.add(new CardItem(R.mipmap.image1, "金毛狗"));
        list.add(new CardItem(R.mipmap.image1, "植物"));
        list.add(new CardItem(R.mipmap.image1, "大白菜"));
        list.add(new CardItem(R.mipmap.image1, "紫丁香"));
        list.add(new CardItem(R.mipmap.image1, "红树林"));
        list.add(new CardItem(R.mipmap.image1, "车站"));

        pictureGroup.setOffscreenPageLimit(3);
        pictureGroup.setAdapter(new PictureScanAdapter());

        gridView.setAdapter(new MarkerAdapter());

        //viewPager滑动时调整指示器
        pictureGroup.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                updateIndicator();
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        updateIndicator();
    }

    /**
     * 更新指示器
     */
    private void updateIndicator(){
        int currentItem = pictureGroup.getCurrentItem() + 1;//得到当前ViewPager的位置
        int totalNum = pictureGroup.getAdapter().getCount();//得到总数
        indicator.setText(String.valueOf(currentItem) + "/" + String.valueOf(totalNum));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.close_scan:{
                DetailPictureActivity.this.finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    }

    /**
     * 图片浏览适配器
     */
    class PictureScanAdapter extends PagerAdapter{
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
            ImageView imageView = new ImageView(DetailPictureActivity.this);
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
     * 标签适配器
     */
    class MarkerAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.home_custom_grid_item, null);
                viewHolder.markerName = (TextView) convertView.findViewById(R.id.tv_intent_iconname);
                viewHolder.deleteMarker = (ImageView) convertView.findViewById(R.id.iv_delete_item);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.deleteMarker.setVisibility(View.INVISIBLE);
//            viewHolder.markerName.setBackground(getResources().getDrawable(R.drawable.radius_drawable_bg));
            viewHolder.markerName.setText(list.get(position).getImgName());
            viewHolder.markerName.setTextSize(11);

            return convertView;
        }

        private class ViewHolder {
            TextView markerName;
            ImageView deleteMarker;
        }

    }

}
