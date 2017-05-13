package com.team.imagemarker.adapters.imgscan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.image.CommentInfoModel;
import com.team.imagemarker.utils.CircleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/5/13.
 * email 1434117404@qq.com
 */

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private List<CommentInfoModel> list;

    public CommentAdapter(Context context, List<CommentInfoModel> list) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_user_comment, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

//        Glide.with(context)
//                .load(list.get(position).getUserHead())
//                .diskCacheStrategy(DiskCacheStrategy.ALL)
//                .into(viewHolder.userHead);
        viewHolder.userHead.setImageResource(R.mipmap.system_push4);
        viewHolder.userNick.setText(list.get(position).getNickName());
        viewHolder.sendTime.setText(list.get(position).getSendTime());
        viewHolder.commentContent.setText(list.get(position).getSendContent());
        return convertView;
    }

    class ViewHolder{
        public CircleImageView userHead;
        public TextView userNick;
        public TextView sendTime;
        public TextView commentContent;

        public ViewHolder(View view) {
            userHead = (CircleImageView) view.findViewById(R.id.comment_image);
            userNick = (TextView) view.findViewById(R.id.comment_name);
            sendTime = (TextView) view.findViewById(R.id.comment_time);
            commentContent = (TextView) view.findViewById(R.id.comment_text);
        }
    }
}
