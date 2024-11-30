package com.example.quickcashapp.employeeDashboard;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;


import com.example.quickcashapp.MainActivity;
import com.example.quickcashapp.R;
import com.example.quickcashapp.Maps.Map;
import com.example.quickcashapp.employeeDashboard.SearchJobsActivity;


/**
 * This class handles all the logic for switching Activity's in the Employee dashboard.
 */
public class MainActivityEmployee extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_employee);


        // Initialize the buttons inside onCreate
        Button profile = findViewById(R.id.E);
        Button map = findViewById(R.id.C);
        Button SearchJobs = findViewById(R.id.SearchJobs);
        Button SetPreference = findViewById(R.id.SetPreference);
        Button ChooseRole = findViewById(R.id.ChooseRole);
        Button matchJobs = findViewById(R.id.MatchJobs);


        SearchJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivityEmployee.this, SearchJobsActivity.class);
                startActivity(intent2);
            }
        });

        SetPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivityEmployee.this, SetPreferenceActivity.class);
                startActivity(intent2);
            }
        });


        ChooseRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivityEmployee.this, MainActivity.class);
                startActivity(intent2);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivityEmployee.this, Map.class);
                startActivity(intent2);
            }
        });

        profile.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, EmployeeProfile.class);
                startActivity(intent);
            }

        });





    }


}
