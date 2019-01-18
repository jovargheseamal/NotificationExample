package com.example.user.notificationexample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getStringExtra("settedTime");
        Toast.makeText(context, "onReceive=\t"+data, Toast.LENGTH_LONG).show();
    }
}
