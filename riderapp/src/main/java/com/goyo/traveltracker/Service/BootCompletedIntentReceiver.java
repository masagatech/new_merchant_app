package com.goyo.traveltracker.Service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by fajar on 22-Jun-17.
 */

public class BootCompletedIntentReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {

            SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
            boolean RiderOnline= pref.getBoolean("status", false);
            if(RiderOnline) {
                Intent pushIntent = new Intent(context, RiderStatus.class);
                context.startService(pushIntent);
            }
        }
    }

}

