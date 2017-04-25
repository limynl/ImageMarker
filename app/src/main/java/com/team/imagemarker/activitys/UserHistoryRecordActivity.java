package com.team.imagemarker.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.ShowHistoryAdapter;
import com.team.imagemarker.bases.btnClickListener;
import com.team.imagemarker.entitys.HistoryModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/4/22.
 * email 1434117404@qq.com
 */

public class UserHistoryRecordActivity extends Activity implements View.OnClickListener, btnClickListener{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private ListView listView;
    private List<HistoryModel> list;
    private ShowHistoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history_record);
        bindView();
        setData();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        listView = (ListView) findViewById(R.id.history_record);

        titleBar.setBackgroundColor(getResources().getColor(R.color.titleBar));
        title.setText("历史记录");
        rightIcon.setVisibility(View.GONE);
        leftIcon.setOnClickListener(this);
    }

    private void setData() {
        list = new ArrayList<>();

        list.add(new HistoryModel("金毛狗", "系统推送", "2017-04-22 17:31:00", 1));
        list.add(new HistoryModel("波斯猫", "兴趣爱好", "2017-04-15 17:31:00", 1));
        list.add(new HistoryModel("水蜜桃", "系统推送", "2017-04-10 17:31:00", 1));
        list.add(new HistoryModel("紫丁香", "兴趣爱好", "2017-04-18 17:31:00", 1));
        list.add(new HistoryModel("大白菜", "兴趣爱好", "2017-04-30 17:31:00", 1));
        list.add(new HistoryModel("红树林", "系统推送", "2017-04-25 17:31:00", 1));

        adapter = new ShowHistoryAdapter(this, list);
        listView.setAdapter(adapter);
        adapter.setListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                UserHistoryRecordActivity.this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        }
    }

    /**
     * 继续操作
     * @param position 点击的位置
     */
    @Override
    public void btnEditClick(int position) {
        Toast.makeText(this, "即将跳转到打标签界面", Toast.LENGTH_SHORT).show();
    }

    /**
     * 删除记录
     * @param position 点击的位置
     */
    @Override
    public void btnDeleteClick(int position) {
        list.remove(position);
        adapter.notifyDataSetChanged();
    }
}
