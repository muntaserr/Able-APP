package com.example.quickcashapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.app.IntentService;
import android.content.SharedPreferences;
import android.os.Build;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class JobCheckService extends IntentService{


    private static final String CHANNEL_ID = "job_alerts";

    public JobCheckService() {
        super("JobCheckService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("JobCheckService", "onHandleIntent started");//


        if (intent != null && intent.getExtras() != null) {

            String jobDetails = intent.getStringExtra("job_details");

            assert jobDetails != null;
            if (jobMatchesPreferences(jobDetails)) {
                showNotification(jobDetails);
            }
        }

        if (intent.hasExtra("job_details")) {
            Log.d("JobCheckService", "Intent has job details");
            // 处理通知逻辑...
        } else {
            Log.d("JobCheckService", "No job details provided");
        }
        Log.d("JobCheckService", "onHandleIntent completed");//
    }

    private boolean jobMatchesPreferences(String jobDetails) {
        // Assuming jobDetails contain information such as job type and location in a predefined format.
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String preferredJobType = prefs.getString("preferred_job_type", "");
        String preferredLocation = prefs.getString("preferred_location", "");

        // Extracting job type and location from jobDetails (implementation depends on jobDetails format)
        // Example assumes jobDetails as "jobType;location"
        String[] details = jobDetails.split(";");
        if (details.length < 2) {

            return false;
        }
        String jobType = details[0];
        String location = details[1];

        return jobType.equals(preferredJobType) && location.equals(preferredLocation);
    }


    private void showNotification(String content) {

        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    "Job Alerts",
                    NotificationManager.IMPORTANCE_DEFAULT//这个值用于定义通知渠道的重要性级别
            );

            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Job Match Found!")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        if (notificationManager != null) {
            notificationManager.notify(1, builder.build());
        }
    }



}
