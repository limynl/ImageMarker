package com.team.imagemarker.adapters.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.CategoryModel;
import com.team.imagemarker.utils.CircleImageView;
import com.wangjie.rapidfloatingactionbutton.textlabel.LabelView;

import java.util.List;

/**
 * Created by Lmy on 2017/5/1.
 * email 1434117404@qq.com
 */

public class TaskBoxAdapter extends BaseAdapter {
    private Context context;
    private List<CategoryModel> list;
    private ViewHolder viewHolder = null;

    public TaskBoxAdapter(Context context, List<CategoryModel> list) {
        this.context = context;
        this.list = list;
    }

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
//        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_task_box, null);
            viewHolder.categoryImg = (ImageView) convertView.findViewById(R.id.category_img);
//            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
//            viewHolder.categorySimapleMessage = (TextView) convertView.findViewById(R.id.category_simple_message);
            viewHolder.userHead = (CircleImageView) convertView.findViewById(R.id.user_head);
            viewHolder.userRanking = (LabelView) convertView.findViewById(R.id.user_ranking);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryModel model = list.get(position);

//        Glide.with(context).load("http://139.199.23.142:8080/TestShowMessage1/marker/test.jpg").asBitmap().into(new SimpleTarget<Bitmap>() {
//            @Override
//            public void onResourceReady(final Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                BitmapBlurUtil.addTask(resource, new Handler(){
//                    @Override
//                    public void handleMessage(Message msg) {
//                        super.handleMessage(msg);
//                        Drawable drawable = (Drawable) msg.obj;
//                        viewHolder.categoryImg.setImageDrawable(drawable);
//                        resource.recycle();
//                    }
//                });
//            }
//        });

        viewHolder.categoryImg.setImageResource(model.getImgId());
//        viewHolder.categoryName.setText(model.getName());
//        viewHolder.categorySimapleMessage.setText("");
        viewHolder.userHead.setImageResource(model.getImgId1());
        viewHolder.userRanking.setText(model.getName());

        return convertView;
    }

    class ViewHolder{
        public ImageView categoryImg;
//        public TextView categoryName;
//        public TextView categorySimapleMessage;
        public CircleImageView userHead;
        public LabelView userRanking;
    }
}
