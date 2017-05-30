package com.team.imagemarker.adapters.imgscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.imgscan.DayRecommendModel;

import java.util.List;

/**
 * Created by Lmy on 2017/5/29.
 * email 1434117404@qq.com
 */

public class DayRecommendAdapter extends BaseAdapter {
    private Context context;
    private List<DayRecommendModel> list;

    public DayRecommendAdapter(Context context, List<DayRecommendModel> list){
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_day_recommend, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.imgTag.setText(list.get(position).getImgTag());
        viewHolder.background.setBackgroundResource(list.get(position).getBackground());
        return convertView;
    }

    class ViewHolder{
        public TextView imgTag;
        public RelativeLayout background;

        public ViewHolder(View view) {
            imgTag = (TextView) view.findViewById(R.id.img_tag);
            background = (RelativeLayout) view.findViewById(R.id.item_background);
        }
    }
}
