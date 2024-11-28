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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class JobCheckService extends IntentService{

    private static final String CHANNEL_ID = "job_alerts";
    private final DatabaseReference databaseRef;

    public JobCheckService() {
        super("JobCheckService");
        databaseRef = FirebaseDatabase.getInstance().getReference();
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        if (intent != null && intent.getExtras() != null) {
            String jobDetails = intent.getStringExtra("job_details");
            assert jobDetails != null;
            checkJobMatch(jobDetails);
        }else {
            Log.d("JobCheckService", "No job details provided");
        }
    }

    private void checkJobMatch(String jobDetails) {
        String userId = "some_user_id"; // 用正确的方式来获取用户ID
        DatabaseReference userPrefRef = databaseRef.child("userPreferences").child(userId).child("location");

        userPrefRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String preferredLocation = dataSnapshot.getValue(String.class);
                if (preferredLocation != null && jobDetails.contains(preferredLocation)) {
                    showNotification(jobDetails);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("JobCheckService", "loadPost:onCancelled", databaseError.toException());
            }
        });
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
