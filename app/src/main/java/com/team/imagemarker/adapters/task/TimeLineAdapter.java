package com.team.imagemarker.adapters.task;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.entitys.task.TimeLineModel;
import com.team.imagemarker.utils.timeline.TimelineView;

import java.util.List;

public class TimeLineAdapter extends RecyclerView.Adapter<TimeLineAdapter.TimeLineViewHolder> {
    private static final String STATUS_COMPLETED = "COMPLETED";
    private static final String STATUS_ACTIVE = "ACTIVE";
    private static final String STATUS_INACTIVE = "INACTIVE";

    private List<TimeLineModel> mFeedList;
    private Context mContext;
    private LayoutInflater mLayoutInflater;

    public TimeLineAdapter(List<TimeLineModel> mFeedList) {
        this.mFeedList = mFeedList;
    }

    @Override
    public int getItemViewType(int position) {
        return TimelineView.getTimeLineViewType(position,getItemCount());
    }

    @Override
    public TimeLineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        mLayoutInflater = LayoutInflater.from(mContext);
        View view = mLayoutInflater.inflate(R.layout.item_task_timeline, parent, false);
        return new TimeLineViewHolder(view, viewType);
    }

    @Override
    public void onBindViewHolder(TimeLineViewHolder holder, int position) {
        TimeLineModel timeLineModel = mFeedList.get(position);
        if(timeLineModel.getStatus() == STATUS_INACTIVE) {
            holder.mTimelineView.setMarker(mContext.getResources().getDrawable(R.drawable.timeline_marker_inactive));
        } else if(timeLineModel.getStatus() == STATUS_ACTIVE) {
            holder.mTimelineView.setMarker(mContext.getResources().getDrawable(R.drawable.timeline_marker_active));
        } else {
            holder.mTimelineView.setMarker(mContext.getResources().getDrawable(R.drawable.timeline_marker));
        }

        if(!timeLineModel.getDate().isEmpty()) {
            holder.mDate.setVisibility(View.VISIBLE);
            holder.mDate.setText(timeLineModel.getDate());
        }
        else{
            holder.mDate.setVisibility(View.GONE);
        }

        holder.mMessage.setText(timeLineModel.getMessage());
    }

    @Override
    public int getItemCount() {
        return (mFeedList!=null? mFeedList.size():0);
    }

    class TimeLineViewHolder extends RecyclerView.ViewHolder {

        public TextView mDate;
        public TextView mMessage;
        public TimelineView mTimelineView;

        public TimeLineViewHolder(View itemView, int viewType) {
            super(itemView);
            mDate = (TextView) itemView.findViewById(R.id.text_timeline_date);
            mMessage = (TextView) itemView.findViewById(R.id.text_timeline_title);
            mTimelineView = (TimelineView) itemView.findViewById(R.id.time_marker);
            mTimelineView.initLine(viewType);
        }
    }
}
