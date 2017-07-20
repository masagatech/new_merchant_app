package com.goyo.traveltracker.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by mis on 14-Jul-17.
 */

public class NetworkStateReceiver extends BroadcastReceiver {

    public static boolean IsConnected=true;
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
                Log.i("APP_TAG", "Wi-Fi - CONNECTED");
                IsConnected=true;
                Toast.makeText(context, IsConnected+"", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("APP_TAG", "Wi-Fi - DISCONNECTED");
                IsConnected=false;
                Toast.makeText(context, IsConnected+"", Toast.LENGTH_SHORT).show();
            }
        } else if (isMobile) {
            if (isConnected) {
                Log.i("APP_TAG", "Mobile - CONNECTED");
                IsConnected=true;
                Toast.makeText(context, IsConnected+"", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("APP_TAG", "Mobile - DISCONNECTED");
                IsConnected=false;
                Toast.makeText(context, IsConnected+"", Toast.LENGTH_SHORT).show();
            }
        } else {
            if (isConnected) {
                Log.i("APP_TAG", networkInfo.getTypeName() + " - CONNECTED");
                IsConnected=true;
                Toast.makeText(context, IsConnected+"", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("APP_TAG", networkInfo.getTypeName() + " - DISCONNECTED");
                IsConnected=false;
                Toast.makeText(context, IsConnected+"", Toast.LENGTH_SHORT).show();

            }
        }
    }

}
