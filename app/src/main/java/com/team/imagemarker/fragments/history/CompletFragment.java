package com.team.imagemarker.fragments.history;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.team.imagemarker.R;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.adapters.history.ShowHistoryAdapter;
import com.team.imagemarker.bases.btnClickListener;
import com.team.imagemarker.entitys.history.HistoryModel;
import com.team.imagemarker.entitys.marker.ItemEntity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 已完成的历史记录
 * Created by Lmy on 2017/4/29.
 * email 1434117404@qq.com
 */

public class CompletFragment extends Fragment implements btnClickListener, SwipeRefreshLayout.OnRefreshListener{
    private View view;
    private ListView listView;

    private List<HistoryModel> list = new ArrayList<>();
    private ShowHistoryAdapter adapter;

    private SwipeRefreshLayout refreshLayout;

    private View customDialog;
    private TextView showMessage;
    private Button delete, cancel;
    private Dialog dialogOne, dialogTwo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_complete_history, null);
        Log.e("已完成", "onCreateView: 已完成");
        listView = (ListView) view.findViewById(R.id.complete_record_liseview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.complete_record_fresh);
        refreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.write, R.color.yellow);
        refreshLayout.setProgressBackgroundColor(R.color.theme);
        refreshLayout.setOnRefreshListener(this);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getAllRecordData();//得到所有历史记录的数据
    }

    private void getAllRecordData() {
        for (int i = 0; i < 2; i++) {
            //未完成
            list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car2.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 0));
        }
        for (int i = 0; i < 2; i++) {
            //已完成
            list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car2.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 1));
        }
        setData();
    }

    private void setData() {
        adapter = new ShowHistoryAdapter(getActivity(), list, 1);//1表示已完成的记录
        listView.setAdapter(adapter);
        adapter.setListener(this);
    }

    /**
     * 继续操作
     * @param position 点击的位置
     */
    @Override
    public void btnEditClick(int position) {
        customDialog = LayoutInflater.from(getContext()).inflate(R.layout.delete_dialog, null);
        showMessage = (TextView) customDialog.findViewById(R.id.show_message);
        showMessage.setText("是否想要查看这次操作?");
        delete = (Button) customDialog.findViewById(R.id.record_delete);
        delete.setText("查看");
        cancel = (Button) customDialog.findViewById(R.id.record_cancel);
        dialogOne = new Dialog(getContext());
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(customDialog);
        dialogOne = builder.create();
        dialogOne.show();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOne.dismiss();
                Intent intent = new Intent(getContext(), MarkHomeActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("pageTag", "completeHistory");
                bundle.putSerializable("completeData", (Serializable) getDataFromNet());
                intent.putExtras(bundle);
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOne.dismiss();
            }
        });
    }

    /**
     * 删除记录
     * @param position 点击的位置
     */
    @Override
    public void btnDeleteClick(final int position) {
        customDialog = LayoutInflater.from(getContext()).inflate(R.layout.delete_dialog, null);
        showMessage = (TextView) customDialog.findViewById(R.id.show_message);
        showMessage.setText("是否要删除该条记录?");
        delete = (Button) customDialog.findViewById(R.id.record_delete);
        cancel = (Button) customDialog.findViewById(R.id.record_cancel);
        dialogOne = new Dialog(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(customDialog);
        dialogOne = builder.create();
        dialogOne.show();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTwo = new Dialog(getContext());
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getContext());
                builder1.setView(LayoutInflater.from(getContext()).inflate(R.layout.alter_dialog, null));
                dialogTwo = builder1.create();
                dialogTwo.show();
                Timer timer = new Timer();
                timer.schedule(new Wait(), 1500);
                list.remove(position);
                adapter.notifyDataSetChanged();

                dialogOne.dismiss();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogOne.dismiss();
            }
        });
    }

    /**
     * 上拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();//清空数据
                int size = 3;
                for (int i = 0; i < 3; i++) {//重新加载数据
                    list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car2.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 0));
                }
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);//刷新完成
            }
        }, 3000);
    }

    class Wait extends TimerTask {
        @Override
        public void run() {
            dialogTwo.dismiss();

        }
    }

    private List<ItemEntity> getDataFromNet(){
        List<ItemEntity> list = new ArrayList<>();
        try {
            InputStream in = getContext().getAssets().open("history.json");
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
                    list.add(itemEntity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}