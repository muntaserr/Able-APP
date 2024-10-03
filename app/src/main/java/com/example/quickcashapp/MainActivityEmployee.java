package com.example.quickcashapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class MainActivityEmployee extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Enable edge-to-edge mode if using EdgeToEdge utility for immersive experience
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_employee);

        // Find the buttons by their IDs from the layout
        Button btnSearchJobs = findViewById(R.id.btnSearchJobs);
        Button btnSetPreferences = findViewById(R.id.btnSetPreferences);


        btnSearchJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), SearchJobsActivity.class);
                startActivity(intent);
            }
        });
        btnSetPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), PreferencesActivity.class);
                startActivity(intent);
            }
        });
    }
}
