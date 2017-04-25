package com.team.imagemarker.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.bases.btnClickListener;
import com.team.imagemarker.entitys.HistoryModel;

import java.util.List;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class ShowHistoryAdapter extends BaseAdapter {
    private Context context;
    private List<HistoryModel> list;
    private btnClickListener listener;

    public void setListener(btnClickListener listener) {
        this.listener = listener;
    }

    public ShowHistoryAdapter(Context context, List<HistoryModel> list) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.show_history_item, null);
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
            viewHolder.recordName.setText(model.getRecordName());
            viewHolder.operateType.setText("操作类型：" + model.getOperateType());
            viewHolder.recordTime.setText("时间: " + model.getRecordTime());
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
        TextView recordName;
        TextView operateType;
        TextView recordTime;
        Button editHitory;
        Button deleteHistory;
    }
}
