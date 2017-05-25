package com.team.imagemarker.adapters.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.home.CategoryModel;

import java.util.List;

/**
 * Created by Lmy on 2017/5/1.
 * email 1434117404@qq.com
 */

public class SystemPushAdapter extends BaseAdapter {
    private Context context;
    private List<CategoryModel> list;

    public SystemPushAdapter(Context context, List<CategoryModel> list) {
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_category, null);
            viewHolder.categoryImg = (ImageView) convertView.findViewById(R.id.category_img);
            viewHolder.categoryName = (TextView) convertView.findViewById(R.id.category_name);
            viewHolder.categorySimapleMessage = (TextView) convertView.findViewById(R.id.category_simple_message);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        CategoryModel model = list.get(position);
        viewHolder.categoryImg.setImageResource(model.getImgId());
        viewHolder.categoryName.setText(model.getName());
        viewHolder.categorySimapleMessage.setText(model.getSimpleMessage());
        return convertView;
    }

    class ViewHolder{
        public ImageView categoryImg;
        public TextView categoryName;
        public TextView categorySimapleMessage;
    }
}
