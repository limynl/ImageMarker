package com.team.imagemarker.activitys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzy.imagepicker.bean.ImageItem;
import com.team.imagemarker.R;
import com.team.imagemarker.adapters.ImagePickerAdapter;
import com.team.imagemarker.utils.WavyLineView;

import java.util.ArrayList;

public class SendMoodActivity extends Activity implements View.OnClickListener, ImagePickerAdapter.OnRecyclerViewItemClickListener{
    private RelativeLayout titleBar;
    private TextView title;
    private ImageView leftIcon, rightIcon;

    private WavyLineView mWavyLine;
    private RecyclerView selectPicture;

    public static final int IMAGE_ITEM_ADD = -1;
    public static final int REQUEST_CODE_SELECT = 100;
    public static final int REQUEST_CODE_PREVIEW = 101;

    private ImagePickerAdapter adapter;
    private ArrayList<ImageItem> selImageList; //当前选择的所有图片
    private int maxImgCount = 9;               //允许选择图片最大数

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_mood);
        bindView();
    }

    private void bindView() {
        titleBar = (RelativeLayout) findViewById(R.id.title_bar);
        title = (TextView) findViewById(R.id.title);
        leftIcon = (ImageView) findViewById(R.id.left_icon);
        rightIcon = (ImageView) findViewById(R.id.right_icon);

        selectPicture = (RecyclerView) findViewById(R.id.select_picture);

        titleBar.setBackgroundColor(getResources().getColor(R.color.titleBar));
        title.setText("发表见解");
        leftIcon.setImageResource(R.mipmap.send_mood_back);
        rightIcon.setImageResource(R.mipmap.send_mood_finish);
        leftIcon.setOnClickListener(this);
        rightIcon.setOnClickListener(this);

        // 波浪线设置
        mWavyLine = (WavyLineView) findViewById(R.id.release_wavyLine);
        mWavyLine.setPeriod((float) (2 * Math.PI / 60));
        mWavyLine.setAmplitude(5);
        mWavyLine.setStrokeWidth(2);//ScreenUtil.dp2px(initStrokeWidth)

        //图片选择设置
        selImageList = new ArrayList<>();
        adapter = new ImagePickerAdapter(this, selImageList, maxImgCount);
        adapter.setOnItemClickListener(this);
        selectPicture.setLayoutManager(new GridLayoutManager(this, 4));
        selectPicture.setHasFixedSize(true);
        selectPicture.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.left_icon:{
                SendMoodActivity.this.finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
            break;
            case R.id.right_icon:{
                Toast.makeText(this, "完成", Toast.LENGTH_SHORT).show();
            }
            break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "选择图片", Toast.LENGTH_SHORT).show();
    }
}