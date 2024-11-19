package com.example.quickcashapp.employeeDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

import com.example.quickcashapp.MainActivity;
import com.example.quickcashapp.R;
import com.example.quickcashapp.Maps.employerMap;


public class MainActivityEmployee extends ComponentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_employee);


        // Initialize the buttons inside onCreate
        Button map = findViewById(R.id.C);
        Button SearchJobs = findViewById(R.id.SearchJobs);
        Button SetPreference = findViewById(R.id.SetPreference);
        Button ChooseRole = findViewById(R.id.ChooseRole);
        Button viewJobs = findViewById(R.id.viewJobs);

        SearchJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, SearchJobsActivity.class);
                startActivity(intent);
            }
        });

        SetPreference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, SetPreferenceActivity.class);
                startActivity(intent);
            }
        });


        ChooseRole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, MainActivity.class);
                startActivity(intent);
            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, employerMap.class);
                startActivity(intent);
            }
        });

        viewJobs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, ViewJobs.class);
                startActivity(intent);
            }
        });
    }


}
