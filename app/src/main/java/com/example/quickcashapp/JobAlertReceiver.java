package com.example.quickcashapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class JobAlertReceiver extends BroadcastReceiver {
    // 重写BroadcastReceiver的onReceive方法，这个方法会在接收到匹配的广播时被调用
    @Override
    public void onReceive(Context context, Intent intent) {
        // 检查接收到的Intent是否有特定的动作"com.example.quickcashapp.ACTION_FIND_JOB"。
        if (intent.getAction().equals("com.example.quickcashapp.ACTION_FIND_JOB")) {
            // 创建一个指向JobCheckService的Intent。
            Intent serviceIntent = new Intent(context, JobCheckService.class);
            // 将接收到的Intent中名为"job_details"的字符串附加到新的Intent中。
            serviceIntent.putExtra("job_details", intent.getStringExtra("job_details"));
            // 启动JobCheckService服务，传递上面创建的serviceIntent。
            context.startService(serviceIntent);
        }
    }
}
