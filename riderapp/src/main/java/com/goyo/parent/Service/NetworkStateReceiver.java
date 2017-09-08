package com.goyo.parent.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

/**
 * Created by mis on 14-Jul-17.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    public static boolean IsMobailConnected=true;
    public static boolean IsWifiConnected=true;
    /*
     * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
     * android.content.Intent)
     */
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        int networkType = intent.getExtras().getInt(ConnectivityManager.EXTRA_NETWORK_TYPE);
        boolean isWiFi = networkType == ConnectivityManager.TYPE_WIFI;
        boolean isMobile = networkType == ConnectivityManager.TYPE_MOBILE;
        NetworkInfo networkInfo = connectivityManager.getNetworkInfo(networkType);
        boolean isConnected = networkInfo.isConnected();

        if (isWiFi) {
            if (isConnected) {
//                SQLBase db = new SQLBase(context);
//                Boolean stops=db.tableExists("tbl_offlinetask");
//                Boolean tags=db.tableExists("tbl_tags");
//                Boolean expens=db.tableExists("tbl_expense");
//                Boolean task=db.tableExists("tbl_task");
//
//                if(expens) {
//                    //sending expense update to server
//                    expense expense = new expense();
//                    expense.SendOfflineExpensstoServer();
//                }
//
//                if(task) {
//                    //sending task update to server
//                    pending_order pending_order = new pending_order();
//                    pending_order.SendOfflineTaskstoServer();
//                }
//
//                if(tags) {
//                    //sending tags to server on network changes
//                    all_order all_order = new all_order();
//                    all_order.SendOfflineTagstoServer();
//                }
//
//                if(stops) {
//                    //sending stops to server on network changes
//                    dashboard dashboard = new dashboard();
//                    dashboard.SendOfflineStopstoServer();
//                }

                Log.i("APP_TAG", "Wi-Fi - CONNECTED");
                IsWifiConnected=true;
            } else {
                Log.i("APP_TAG", "Wi-Fi - DISCONNECTED");
                IsWifiConnected=false;
            }
        } else if (isMobile) {
            if (isConnected) {

//                SQLBase db = new SQLBase(context);
//                Boolean stops=db.tableExists("tbl_offlinetask");
//                Boolean tags=db.tableExists("tbl_tags");
//                Boolean expens=db.tableExists("tbl_expense");
//                Boolean task=db.tableExists("tbl_task");
//
//                if(expens) {
//                    //sending expense update to server
//                    expense expense = new expense();
//                    expense.SendOfflineExpensstoServer();
//                }
//
//                if(task) {
//                    //sending task update to server
//                    pending_order pending_order = new pending_order();
//                    pending_order.SendOfflineTaskstoServer();
//                }
//
//                if(tags) {
//                    //sending tags to server on network changes
//                    all_order all_order = new all_order();
//                    all_order.SendOfflineTagstoServer();
//                }
//
//                if(stops) {
//                    //sending stops to server on network changes
//                    dashboard dashboard = new dashboard();
//                    dashboard.SendOfflineStopstoServer();
//                }

                Log.i("APP_TAG", "Mobile - CONNECTED");
                IsMobailConnected=true;
            } else {
                Log.i("APP_TAG", "Mobile - DISCONNECTED");
                IsMobailConnected=false;
            }
        } else {
            if (isConnected) {
                Log.i("APP_TAG", networkInfo.getTypeName() + " - CONNECTED");
//                IsMobailConnected=true;
//                IsWifiConnected=true;
            } else {
                Log.i("APP_TAG", networkInfo.getTypeName() + " - DISCONNECTED");
//                IsMobailConnected=false;
//                IsWifiConnected=false;

            }
        }
    }

}
