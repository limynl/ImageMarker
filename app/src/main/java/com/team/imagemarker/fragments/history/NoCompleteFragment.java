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

import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.team.imagemarker.R;
import com.team.imagemarker.activitys.mark.MarkHomeActivity;
import com.team.imagemarker.adapters.history.ShowHistoryAdapter;
import com.team.imagemarker.bases.btnClickListener;
import com.team.imagemarker.constants.Constants;
import com.team.imagemarker.entitys.MarkerModel;
import com.team.imagemarker.entitys.marker.ItemEntity;
import com.team.imagemarker.utils.volley.VolleyListenerInterface;
import com.team.imagemarker.utils.volley.VolleyRequestUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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

    private static List<MarkerModel> list = new ArrayList<>();
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
        Log.e("tag", "onCreateView: 未完成");
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
        getDataFromToHistory();
        adapter = new ShowHistoryAdapter(getContext(), list, 2);
        listView.setAdapter(adapter);
        adapter.setListener(this);
    }

    /**
     * 继续操作
     * @param position 点击的位置
     */
    @Override
    public void btnEditClick(final int position) {
        customDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_delete, null);
        showMessage = (TextView) customDialog.findViewById(R.id.show_message);
        delete = (Button) customDialog.findViewById(R.id.record_delete);
        showMessage.setText("是否想要继续完成此次操作?");
        delete.setText("继续");
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
                bundle.putString("pageTag", "noCompleteHistory");
                bundle.putSerializable("noCompleteData", list.get(position));
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
        customDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_delete, null);
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
                builder1.setView(LayoutInflater.from(getContext()).inflate(R.layout.dialog_alter, null));
                dialogTwo = builder1.create();
                dialogTwo.show();
                Timer timer = new Timer();
                timer.schedule(new Wait(), 1500);
                deleteHistory(position);

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

    private void deleteHistory(final int position) {
//        list.remove(position);
//        adapter.notifyDataSetChanged();

        String url = Constants.USER_HISTORY_DELETE;
        Map<String, String> map = new HashMap<>();
        map.put("itemId", list.get(position).getId() + "");
        VolleyRequestUtil.RequestPost(getContext(), url, "deleteHistory", map, new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                list.remove(position);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onError(VolleyError error) {

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
                list.clear();
                getDataFromToHistory();
                adapter.notifyDataSetChanged();
                refreshLayout.setRefreshing(false);
            }
        }, 2000);
    }

    class Wait extends TimerTask {
        @Override
        public void run() {
            dialogTwo.dismiss();
        }
    }

    //    class wait extends TimerTask {
//        private SweetAlertDialog sDialog;
//
//        public wait(SweetAlertDialog sDialog){
//            this.sDialog = sDialog;
//        }
//
//        @Override
//        public void run() {
//            sDialog.dismiss();
//        }
//    }
//
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

    private void getDataFromToHistory(){
        String url = Constants.USER_HISTORY_DATA;
//        String url = Constants.USER_ALL_HISTORY;
        Map<String, String> userHistory = new HashMap<String, String>();
        userHistory.put("userId", Constants.USER_ID + "");
        VolleyRequestUtil.RequestGet(getContext(), url, "NocompletHistory", new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    JSONArray array = object.optJSONArray("picture");
                    Gson gson = null;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.optJSONObject(i);
                        gson = new Gson();
                        MarkerModel model = gson.fromJson(object1.toString(), MarkerModel.class);
                        if(model.getFlag().equals("S")){
                            list.add(model);
                        }
                        adapter.notifyDataSetChanged();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.e("tag", "onError: " + error.toString());
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(list != null){
            list.clear();
//            list = null;
        }
    }
}
