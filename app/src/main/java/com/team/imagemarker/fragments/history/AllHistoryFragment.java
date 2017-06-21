package com.team.imagemarker.fragments.history;

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
import com.team.loading.SweetAlertDialog;

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
 * 所有历史记录
 * Created by Lmy on 2017/4/29.
 * email 1434117404@qq.com
 */

public class AllHistoryFragment extends Fragment implements btnClickListener, SwipeRefreshLayout.OnRefreshListener{
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
        Log.e("tag", "onCreateView: 所有记录");
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
        adapter = new ShowHistoryAdapter(getContext(), list, 0);
        listView.setAdapter(adapter);
        adapter.setListener(this);
    }

    /**
     * 继续操作
     * @param position 点击的位置
     */
    @Override
    public void btnEditClick(final int position) {
//        customDialog = LayoutInflater.from(getContext()).inflate(R.layout.dialog_delete, null);
//        showMessage = (TextView) customDialog.findViewById(R.id.show_message);
//        delete = (Button) customDialog.findViewById(R.id.record_delete);
//        if(list.get(position).getFlag().equals("T")){
//            showMessage.setText("是否想要查看这次操作?");
//            delete.setText("查看");
//        }else if (list.get(position).getFlag().equals("S")) {
//            showMessage.setText("是否想要继续完成此次操作?");
//            delete.setText("继续");
//        }
//        cancel = (Button) customDialog.findViewById(R.id.record_cancel);
//        dialogOne = new Dialog(getContext());
//        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
//        builder.setView(customDialog);
//        dialogOne = builder.create();
//        dialogOne.show();
//        delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogOne.dismiss();
//                Intent intent = new Intent(getContext(), MarkHomeActivity.class);
//                Bundle bundle = new Bundle();
//                bundle.putString("pageTag", "completeHistory");
//                bundle.putSerializable("completeData", list.get(position));
//                intent.putExtras(bundle);
//                startActivity(intent);
//                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
//            }
//        });
//        cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogOne.dismiss();
//            }
//        });


        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(list.get(position).getFlag().equals("T") ? "该次操作已经提交!" : "该次操作已经保存!")
                .setContentText(list.get(position).getFlag().equals("T") ? "您可以选择查看此次操作!" : "您可以继续修改此次操作并提交!")
                .setCancelText(list.get(position).getFlag().equals("T") ? "查 看" : "继 续")
                .setConfirmText("取 消")
                .showConfirmButton(true)
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                        Intent intent = new Intent(getContext(), MarkHomeActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("pageTag", "completeHistory");
                        bundle.putSerializable("completeData", list.get(position));
                        intent.putExtras(bundle);
                        startActivity(intent);
                        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                       sDialog.dismiss();
                    }
                })
                .show();
    }

    /**
     * 删除记录
     * @param position 点击的位置
     */
    @Override
    public void btnDeleteClick(final int position) {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText(list.get(position).getFlag().equals("T") ? "该次操作已经提交!" : "该次操作已经保存!")
                .setContentText("您可以删除此次操作!")
                .setCancelText("删 除")
                .setConfirmText("取 消")
                .showConfirmButton(true)
                .showCancelButton(true)
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        deleteHistory(position);
                        sDialog.setTitleText("删除成功")
                                .setContentText("")
                                .showConfirmButton(false)
                                .showCancelButton(false)
                                .setCancelClickListener(null)
                                .setConfirmClickListener(null)
                                .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        Timer timer=new Timer();
                        timer.schedule(new Wite(sDialog), 2000);
                    }
                })
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismiss();
                    }
                })
                .show();
    }

    private void deleteHistory(final int position) {
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
        Map<String, String> userHistory = new HashMap<String, String>();
        userHistory.put("userId", Constants.USER_ID + "");
        VolleyRequestUtil.RequestGet(getContext(), url, "AllcompletHistory", new VolleyListenerInterface() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(new String(result.getBytes("ISO-8859-1"), "UTF-8"));
                    String tag = new String(object.optString("TAG").getBytes("ISO-8859-1"), "UTF-8");
                    JSONArray array = object.optJSONArray("picture");
                    Gson gson = null;
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object1 = array.optJSONObject(i);
                        gson = new Gson();
                        MarkerModel model = gson.fromJson(object1.toString(), MarkerModel.class);
                        list.add(model);
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
        }
    }

}