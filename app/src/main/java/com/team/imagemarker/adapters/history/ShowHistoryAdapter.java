package com.team.imagemarker.adapters.history;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.team.imagemarker.R;
import com.team.imagemarker.bases.btnClickListener;
import com.team.imagemarker.entitys.history.HistoryModel;
import com.team.imagemarker.utils.CircleImageView;

import java.util.List;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class ShowHistoryAdapter extends BaseAdapter {
    private final int ALL = 0;
    private final int COMPLETE = 1;
    private final int NO_COMPLETE = 2;

    private int type;

    private Context context;
    private List<HistoryModel> list;
    private btnClickListener listener;

    public void setListener(btnClickListener listener) {
        this.listener = listener;
    }

    public ShowHistoryAdapter(Context context, List<HistoryModel> list, int type) {
        this.context = context;
        this.list = list;
        this.type = type;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.show_history_item, null);
            viewHolder.firstImg = (CircleImageView) convertView.findViewById(R.id.first_img);
            viewHolder.recordName = (TextView) convertView.findViewById(R.id.record_name);
            viewHolder.operateType = (TextView) convertView.findViewById(R.id.operate_type);
            viewHolder.recordTime = (TextView) convertView.findViewById(R.id.record_time);
            viewHolder.editHitory = (Button) convertView.findViewById(R.id.edit_history);
            viewHolder.deleteHistory = (Button) convertView.findViewById(R.id.delete_history);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder) convertView.getTag();
        }

        HistoryModel model = list.get(position);
        if (model != null) {
            if (type == ALL){//全部记录
                Glide.with(context)
                        .load(model.getFirstImg())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(viewHolder.firstImg);
                viewHolder.recordName.setText(model.getRecordName());
                viewHolder.operateType.setText(model.getOperateType());
                viewHolder.recordTime.setText(model.getRecordTime());
                if(model.getRecordType() == 0){//未完成
                    viewHolder.editHitory.setText("继续");
                    viewHolder.deleteHistory.setText("删除");
                }else{//已完成
                    viewHolder.editHitory.setText("查看");
                    viewHolder.deleteHistory.setText("删除");
                }
            }else if(type == COMPLETE && model.getRecordType() == 1){//已完成
                    Glide.with(context)
                            .load(model.getFirstImg())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(viewHolder.firstImg);
                    viewHolder.recordName.setText(model.getRecordName());
                    viewHolder.operateType.setText(model.getOperateType());
                    viewHolder.recordTime.setText(model.getRecordTime());

                    viewHolder.editHitory.setText("查看");
                    viewHolder.deleteHistory.setText("删除");
            }else if(type == NO_COMPLETE && model.getRecordType() == 0){//未完成
                    Glide.with(context)
                            .load(model.getFirstImg())
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(viewHolder.firstImg);
                    viewHolder.recordName.setText(model.getRecordName());
                    viewHolder.operateType.setText(model.getOperateType());
                    viewHolder.recordTime.setText(model.getRecordTime());

                    viewHolder.editHitory.setText("继续");
                    viewHolder.deleteHistory.setText("删除");
            }
        }
        /**
         * 继续操作
         */
        viewHolder.editHitory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.btnEditClick(position);
            }
        });

        /**
         * 删除记录
         */
        viewHolder.deleteHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.btnDeleteClick(position);
            }
        });

        return convertView;
    }

    class ViewHolder{
        CircleImageView firstImg;
        TextView recordName;
        TextView operateType;
        TextView recordTime;
        Button editHitory;
        Button deleteHistory;
    }
}
