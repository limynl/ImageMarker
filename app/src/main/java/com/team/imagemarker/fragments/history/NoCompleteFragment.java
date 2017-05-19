package com.team.imagemarker.fragments.history;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.Toast;

import com.team.imagemarker.R;
import com.team.imagemarker.adapters.history.ShowHistoryAdapter;
import com.team.imagemarker.bases.btnClickListener;
import com.team.imagemarker.entitys.HistoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 未完成的历史记录
 * Created by Lmy on 2017/4/29.
 * email 1434117404@qq.com
 */

public class NoCompleteFragment extends Fragment implements btnClickListener, SwipeRefreshLayout.OnRefreshListener{
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
        view = inflater.inflate(R.layout.fragment_no_complete_history, null);
        Log.e("未完成", "onCreateView: 未完成");
        listView = (ListView) view.findViewById(R.id.no_complete_record_liseview);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.no_complete_record_fresh);
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
            list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car3.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 0));
        }
        for (int i = 0; i < 2; i++) {
            //已完成
            list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car3.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 1));
        }
        setData();
    }

    private void setData() {
        adapter = new ShowHistoryAdapter(getActivity(), list, 2);//2表示未完成的记录
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
        showMessage.setText("是否想要继续操作?");
        delete = (Button) customDialog.findViewById(R.id.record_delete);
        delete.setText("继续");
        cancel = (Button) customDialog.findViewById(R.id.record_cancel);
        dialogOne = new Dialog(getContext());
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(customDialog);
        dialogOne = builder.create();
        dialogOne.show();
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "继续操作", Toast.LENGTH_SHORT).show();
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
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                list.clear();//清空数据
                int size = 3;
                for (int i = 0; i < 3; i++) {//重新加载数据
                    list.add(new HistoryModel(2, "http://139.199.23.142:8080/TestShowMessage1/carImages/car3.jpg", "记录名称", "系统推送", "2017-04-29 19:39:00", 0));
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
}
