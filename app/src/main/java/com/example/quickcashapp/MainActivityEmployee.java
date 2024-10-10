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
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_employee);


        // Initialize the buttons inside onCreate
        Button SearchJobs = findViewById(R.id.SearchJobs);
        Button SetPreference = findViewById(R.id.SetPreference);
        Button ChooseRole = findViewById(R.id.ChooseRole);

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


    }


}
