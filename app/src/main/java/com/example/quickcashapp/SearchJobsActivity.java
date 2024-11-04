package com.example.quickcashapp;

import android.os.Bundle;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class SearchJobsActivity extends ComponentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable EdgeToEdge display if required
        EdgeToEdge.enable(this);
        // Set the content view to your activity's layout
        setContentView(R.layout.activity_search_jobs);
        // You can add more logic here (e.g., setting up views, buttons, etc.)

    }
}
