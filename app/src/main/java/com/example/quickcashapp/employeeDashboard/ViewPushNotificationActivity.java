package com.example.quickcashapp.employeeDashboard;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcashapp.R;

public class ViewPushNotificationActivity extends AppCompatActivity {
    private TextView titleTV;
    private TextView bodyTV;
    private TextView jobIdTV;
    private TextView jobLocationTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_push_notification);
        init();
        setData();
    }

    private void init() {
        //binding the views with the variables
        titleTV = findViewById(R.id.titleTV);
        bodyTV = findViewById(R.id.bodyTV);
        jobIdTV = findViewById(R.id.jobIdTV);
        jobLocationTV = findViewById(R.id.jobLocationTV);
    }

    private void setData() {
        // Check if the extras Bundle is not null and contains the expected keys
        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            final String title = extras.containsKey("title") ? extras.getString("title", "No title available") : "No title available";
            final String body = extras.containsKey("body") ? extras.getString("body", "No details available") : "No details available";
            final String jobId = extras.containsKey("job_id") ? extras.getString("job_id", "Not specified") : "Not specified";
            final String jobLocation = extras.containsKey("jobLocation") ? extras.getString("jobLocation", "Location unknown") : "Location unknown";

            titleTV.setText(title);
            bodyTV.setText(body);
            jobIdTV.setText(jobId);
            jobLocationTV.setText(jobLocation);
        } else {
            // Set default values or perform some error handling if no data is available
            titleTV.setText("No title available");
            bodyTV.setText("No details available");
            jobIdTV.setText("Not specified");
            jobLocationTV.setText("Location unknown");
        }
    }


}
