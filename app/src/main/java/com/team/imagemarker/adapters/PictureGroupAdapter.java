package com.team.imagemarker.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.team.imagemarker.R;
import com.team.imagemarker.entitys.PictureGroupModel;

import java.util.List;

/**
 * 图组选择界面适配器
 * Created by Lmy on 2017/4/15.
 * email 1434117404@qq.com
 */

public class PictureGroupAdapter extends RecyclerView.Adapter<PictureGroupAdapter.PictureViewHolder>{
    private LayoutInflater inflater;
    private Context context;
    private List<PictureGroupModel> list;

    private OnItemActionListener onItemActionListener;

    public void setOnItemActionListener(OnItemActionListener onItemActionListener) {
        this.onItemActionListener = onItemActionListener;
    }

    public PictureGroupAdapter(Context context, List<PictureGroupModel> list){
        this.context = context;
        this.list = list;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.picture_group_item, parent, false);
        PictureViewHolder viewHolder = new PictureViewHolder(view);
//        viewHolder.setIsRecyclable(true);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final PictureViewHolder viewHolder, int position) {
        viewHolder.imgTitle.setText(list.get(position).getImgContent());

//        int width = ((Activity)viewHolder.firstImg.getContext()).getWindowManager().getDefaultDisplay().getWidth();
//        ViewGroup.LayoutParams params = viewHolder.firstImg.getLayoutParams();
//        params.width = width / 3;
//        params.height = (int)(200 + Math.random() * 400);
//        viewHolder.firstImg.setLayoutParams(params);

        Glide.with(context)
                .load(list.get(position).getImgUrl())
                .asBitmap()
                .error(R.mipmap.ic_launcher)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new BitmapImageViewTarget(viewHolder.firstImg) {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        super.onResourceReady(resource, glideAnimation);
                        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
                            public void onGenerated(Palette p) {
                                int vibrant = p.getLightVibrantColor(0x000000);
                                viewHolder.imgTitle.setBackgroundColor(vibrant);
                            }
                        });
                    }
                });

        if (onItemActionListener != null) {
            viewHolder.firstImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //这里使用viewHolder.getPosition()是为了保证动画没有使用NotifyDatasetChanged更新位置数据
                    onItemActionListener.OnItemClickListener(v, viewHolder.getPosition());//暴露接口供外部调用
                }
            });
        }
    }

    /**
     * 设置监听事件
     */
    public interface OnItemActionListener{
        /**
         * 为图组设置监听事件
         * @param view 操作的视图
         * @param position 数据的位置
         */
        public void OnItemClickListener(View view, int position);
    }


    public class PictureViewHolder extends RecyclerView.ViewHolder {
        public TextView imgTitle;
        public ImageView firstImg;

        public PictureViewHolder(View item) {
            super(item);
            imgTitle = (TextView) item.findViewById(R.id.picture_group_title);
            firstImg = (ImageView) item.findViewById(R.id.item_image);
        }
    }
}

