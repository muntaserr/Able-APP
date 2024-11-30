package com.example.quickcashapp.employeeDashboard;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcashapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

public class MatchJobs extends AppCompatActivity {
    private Button sendNotificationBtn;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_jobs);
        init();
        // Calls the init method to initialize interface components and Settings.
        setListeners();
        // Calls the setListeners method to set event listeners for an interface component
    }


    private void init() {
        sendNotificationBtn = findViewById(R.id.sendNotificationBtn);
        //requestQueue = Volley.newRequestQueue(this);
        // Initializes Volley's request queue.
        databaseReference = FirebaseDatabase.getInstance().getReference("jobs");
        // Call the Firebase Messaging API to subscribe to the topic "jobs".

        FirebaseMessaging.getInstance().subscribeToTopic("jobs")
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("FCM Subscribe", "Failed to subscribe to jobs topic");
                    } else {
                        Log.d("FCM Subscribe", "Subscribed to jobs topic successfully");
                    }
                });
    }

    private void setListeners() {
        sendNotificationBtn.setOnClickListener(view -> sendNotification());
    }

    private void checkLocationAndSendNotification(String targetLocation) {
        // Read jobs from Firebase Database
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String location = snapshot.child("location").getValue(String.class);
                    if (targetLocation.equals(location)) {
                        sendNotification();
                        break; // Send notification for the first match, then stop checking
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("DB Error", "Failed to read job locations", databaseError.toException());
            }
        });
    }


    private void sendNotification() {

        Toast.makeText(this, "There are matching jobs now!", Toast.LENGTH_SHORT).show();

    }

}
