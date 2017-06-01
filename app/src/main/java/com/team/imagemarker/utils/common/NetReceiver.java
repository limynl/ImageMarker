package com.team.imagemarker.utils.common;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.team.imagemarker.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Lmy on 2017/5/30.
 * email 1434117404@qq.com
 */

public class NetReceiver extends BroadcastReceiver {
    private   String netType=""; //网络类型
    private Dialog dialog;

    @Override
    public void onReceive(Context context, Intent intent) {
        String action= intent.getAction();
        if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
            boolean isConnected = NetUtils.isNetworkConnected(context);
            boolean isConnectedTypeMobile=NetUtils.isMobileConnected(context);
            boolean isConnectedTypeWifi=NetUtils.isWifiConnected(context);
            dialog = new Dialog(context);
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            View view = LayoutInflater.from(context).inflate(R.layout.dialog_alter, null);
            TextView textView = (TextView) view.findViewById(R.id.operate_message);
            if (isConnected) {
                if(isConnectedTypeWifi&&NetUtils.getConnectedType(context)==1){
                    this.setNetType("wifinet");
                }else if(isConnectedTypeMobile&&NetUtils.getConnectedType(context)==0){
                    this.setNetType("mobilenet");
                }
                textView.setText("网络已连接");
                builder1.setView(view);
                dialog = builder1.create();
                dialog.show();
                Timer timer = new Timer();
                timer.schedule(new Wait(), 1500);
//                Toast.makeText(context, "网络已连接", Toast.LENGTH_SHORT).show();
            } else {
                textView.setText("网络好像走丢了");
                builder1.setView(view);
                dialog = builder1.create();
                dialog.show();
                Timer timer = new Timer();
                timer.schedule(new Wait(), 1500);
                this.setNetType("nonet");
            }
        }
    }
    public String getNetType() {
        return netType;
    }
    public void setNetType(String netType) {
        this.netType = netType;
    }

    class Wait extends TimerTask {
        @Override
        public void run() {
            dialog.dismiss();
        }
    }
}
