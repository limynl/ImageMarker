package com.team.imagemarker.activitys.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.entitys.home.CateGoryInfo;
import com.team.imagemarker.entitys.marker.ItemEntity;
import com.team.imagemarker.utils.EditTextWithDel;
import com.team.imagemarker.utils.RoundAngleImageView;
import com.team.imagemarker.utils.ToastUtil;
import com.team.imagemarker.utils.tag.TagColor;
import com.team.imagemarker.utils.tag.TagGroup;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lmy on 2017/5/4.
 * email 1434117404@qq.com
 */

public class UserSearchActivity extends Activity implements View.OnClickListener{
    private TagGroup tagGroup;
    private List<String> tagList = new ArrayList<String>();
    private int hotIndex = 0;

    private EditTextWithDel searchContent;//搜索内容
    private ListView showResult;//显示结果列表
    private ListView showHistory;//显示历史记录列表
    private RelativeLayout layoutHotWord;//热门搜索
    private RelativeLayout rlHistory;//历史记录
    private ArrayAdapter<String> historyAdapter;//历史记录
    private List<CateGoryInfo> resultList = new ArrayList<>();
    private List<ItemEntity> itemModel;
    private ToastUtil toastUtil = new ToastUtil();
    private TextView searchBack, tvClear, tvChangeWords;//清除历史记录
    private int[] imgs = {R.mipmap.search1, R.mipmap.search2, R.mipmap.search3, R.mipmap.search4, R.mipmap.search5, R.mipmap.search6};

    private static final int DO_SEARCH = 1;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            StartSearch();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_search);
        tagGroup = (TagGroup) findViewById(R.id.tag_group);
        tagList.add("好吃的");
        tagList.add("优美的风景");
        tagList.add("蓝天");
        tagList.add("高达的建筑");
        tagList.add("旅游景点");
        tagList.add("宠物");
        tagList.add("文具-书包");
        tagList.add("自由女神");
        showHotWord(tagList);
        bindView();
    }

    private void bindView() {
        searchContent = (EditTextWithDel) findViewById(R.id.user_search);
        showResult = (ListView) findViewById(R.id.lvShowResult);
        showHistory = (ListView) findViewById(R.id.lvSearchHistory);
        layoutHotWord = (RelativeLayout) findViewById(R.id.layoutHotWord);
        rlHistory = (RelativeLayout) findViewById(R.id.rlHistory);
        searchBack = (TextView) findViewById(R.id.search_back);
        tvClear = (TextView) findViewById(R.id.tvClear);
        tvChangeWords = (TextView) findViewById(R.id.tvChangeWords);

        searchContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == 0){//搜索框为空时，结果列表隐藏
                    showResult.setVisibility(View.GONE);
                }else{
                    showResult.setVisibility(View.VISIBLE);
                }
                mHandler.sendEmptyMessageDelayed(DO_SEARCH,1000);//延迟一秒进行搜索，提高性能，节约流量
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.length() == 0){
                    showResult.setVisibility(View.GONE);
                    layoutHotWord.setVisibility(View.VISIBLE);
                    rlHistory.setVisibility(View.VISIBLE);
                }
            }
        });
        initSearchHistory();

        searchBack.setOnClickListener(this);
        tvClear.setOnClickListener(this);
        tvChangeWords.setOnClickListener(this);
    }

    /**
     * 联网进行实时搜索
     */
    private void StartSearch() {
        String content = searchContent.getText().toString();
        if(TextUtils.isEmpty(content)){
            return;
        }
        layoutHotWord.setVisibility(View.GONE);
        rlHistory.setVisibility(View.GONE);
        saveHistory(content);//缓存搜索内容
        getData();
        if(resultList != null){
            if(showResult.getVisibility() == View.GONE){
                showResult.setVisibility(View.VISIBLE);
            }
            showResult.setAdapter(new ResultListAdapter());
            showResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(UserSearchActivity.this, MarkHomeActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("pageTag", "searchHobby");
                    bundle.putSerializable("item", (Serializable) itemModel);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });
            //收起软键盘
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }else{
            toastUtil.Short(this, "没有相关内容,换换其他搜索词吧").show();
        }
    }

    /**
     * 加载数据
     */
    private void getData() {
        if(resultList != null){
            resultList.clear();
        }
        resultList.add(new CateGoryInfo("上传时间: 2017-06-01 17:23:00", "天空 植物 枯树", ""));
        resultList.add(new CateGoryInfo("上传时间: 2017-06-01 17:23:00", "海滩 树木 莱利湾", ""));
        resultList.add(new CateGoryInfo("上传时间: 2017-06-01 17:23:00", "帕太神庙 建筑 天空", ""));
        resultList.add(new CateGoryInfo("上传时间: 2017-06-01 17:23:00", "喷泉 草地", ""));
        resultList.add(new CateGoryInfo("上传时间: 2017-06-01 17:23:00", "道路 树木 落叶", ""));
        resultList.add(new CateGoryInfo("上传时间: 2017-06-01 17:23:00", "高山 湖水", ""));
        try {
            itemModel = new ArrayList<>();
            InputStream in = getAssets().open("mark.json");
            int size = in.available();
            byte[] buffer = new byte[size];
            in.read(buffer);
            String jsonStr = new String(buffer, "UTF-8");
            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray jsonArray = jsonObject.optJSONArray("result");
            if (null != jsonArray) {
                int len = jsonArray.length();
                for (int i = 0; i < len; i++) {
                    JSONObject itemJsonObject = jsonArray.getJSONObject(i);
                    ItemEntity itemEntity = new ItemEntity(itemJsonObject);
                    itemModel.add(itemEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化历史记录列表
     */
    private void initSearchHistory() {
        String cache = SearchHistoryCacheUtils.getCache(this);
        if(cache != null){
            List<String> historyRecordList = new ArrayList<>();
            for (String record : cache.split(",")){
                historyRecordList.add(record);
            }
            historyAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, historyRecordList);
            if(historyRecordList.size() > 0){
                showHistory.setAdapter(historyAdapter);
            }
        }
    }

    /**
     * 保存历史记录
     */
    private void saveHistory(String history){
        String oldCache = SearchHistoryCacheUtils.getCache(this);
        StringBuilder builder = new StringBuilder(history);
        if(oldCache == null){
            SearchHistoryCacheUtils.setCache(builder.toString(), this);
            updateHistory();//更新历史记录
        }else{
            builder.append("," + oldCache);
            if(!oldCache.contains(history)){//避免缓存重复数据
                SearchHistoryCacheUtils.setCache(builder.toString(), this);
                updateHistory();
            }
        }
    }

    /**
     * 更新历史记录
     */
    private void updateHistory() {
        String cache = SearchHistoryCacheUtils.getCache(this);
        String[] recordData = new String[]{};
        if(cache != null){
            recordData = cache.split(",");
        }
        historyAdapter = new ArrayAdapter<String>(this, R.layout.item_search_history, recordData);
        showHistory.setAdapter(historyAdapter);
        historyAdapter.notifyDataSetChanged();
    }

    /**
     * 显示人们搜索
     */
    private void showHotWord(List<String> list) {
        int tagSize = 8;//每次显示8个
        String[] tags = new String[tagSize];
        for (int j = 0; j < tagSize && j < list.size(); hotIndex++, j++) {
            tags[j] = list.get(hotIndex % list.size());
        }
        List<TagColor> colors = TagColor.getRandomColors(tagSize);
        tagGroup.setTags(colors, tags);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_back:{
                onBackPressed();
            }
            break;
            case R.id.tvClear:{//删除历史记录
                String cache = SearchHistoryCacheUtils.getCache(this);
                if(cache != null){
                    SearchHistoryCacheUtils.setCache("", this);
                    updateHistory();
                }
            }
            break;
            case R.id.tvChangeWords:{
                List<String> list = new ArrayList<>();
                list.add("旅游景点");
                list.add("宠物");
                list.add("文具-书包");
                list.add("自由女神");
                list.add("好吃的");
                list.add("优美的风景");
                list.add("蓝天");
                list.add("高达的建筑");
                showHotWord(list);
            }
            break;
        }
    }

    class ResultListAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public Object getItem(int position) {
            return resultList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if(convertView == null){
                convertView = View.inflate(UserSearchActivity.this, R.layout.item_search_recult, null);
                viewHolder = new ViewHolder();
                viewHolder.itemImg = (RoundAngleImageView) convertView.findViewById(R.id.item_img);
                viewHolder.itemName = (TextView) convertView.findViewById(R.id.item_name);
                viewHolder.itemTime = (TextView) convertView.findViewById(R.id.item_time);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder) convertView.getTag();
            }
            CateGoryInfo info = resultList.get(position);
//            Glide.with(UserSearchActivity.this)
//                    .load(info.getImgUrl())
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .thumbnail(0.8f)
//                    .into(viewHolder.itemImg);
            viewHolder.itemImg.setImageResource(imgs[position]);
            viewHolder.itemName.setText(info.getName());
            viewHolder.itemTime.setText(info.getMainTitle());
            return convertView;
        }
    }

    class ViewHolder{
        public RoundAngleImageView itemImg;
        public TextView itemName;
        public TextView itemTime;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }
}