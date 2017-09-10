package com.team.imagemarker.utils.objectselect;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.team.imagemarker.R;


/**
 * Created by Lmy on 2017/8/26.
 * email 1434117404@qq.com
 */

public class MyDividerItemDecoration extends RecyclerView.ItemDecoration{
    private Drawable drawable;
    public MyDividerItemDecoration (Context context){
        drawable= ContextCompat.getDrawable(context, R.drawable.selector_object_select_one_level);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        drawVerticalLine(c,parent);
    }
    /**
     * 绘制分割线
     */
    private void drawVerticalLine(Canvas c, RecyclerView parent) {
        final int left=parent.getPaddingLeft();
        final int right=parent.getWidth()-parent.getPaddingRight();
        final int childcount=parent.getChildCount();
        for (int i=0;i<childcount;i++)
        {
            final View child=parent.getChildAt(i);//获取子view在ViewGroup的确切位置
            final RecyclerView.LayoutParams params= (RecyclerView.LayoutParams) child.getLayoutParams();
            final  int top=child.getBottom()+params.bottomMargin+Math.round(ViewCompat.getTranslationY(child));
            final int bottom=top+drawable.getIntrinsicHeight();
            drawable.setBounds(left,top,right,bottom);
            drawable.draw(c);

        }
    }
}
