package com.example.behnam.myapplication.broadcast_reciver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.example.behnam.myapplication.connect_to_server.update_server_data;

public class NetworkChangeReceiver extends BroadcastReceiver {

    public void onReceive(Context context, Intent intent) {
        if (NetworkUtil.getConnectivityStatusString(context) != "Not connected to Internet") {
           // Toast.makeText(context, "به شبکه وصل شد", Toast.LENGTH_SHORT).show();
            update_server_data.syncData(context.getApplicationContext());

        }
    }


}
