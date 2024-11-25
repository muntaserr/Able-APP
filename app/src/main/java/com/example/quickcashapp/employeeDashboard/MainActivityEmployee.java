package com.example.quickcashapp.employeeDashboard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;

import com.example.quickcashapp.MainActivity;
import com.example.quickcashapp.R;
import com.example.quickcashapp.Maps.Map;
import com.example.quickcashapp.employeeDashboard.EmployeeProfile;


/**
 * This class handles all the logic for switching Activity's in the Employee dashboard.
 */
public class MainActivityEmployee extends ComponentActivity {
    CardView c1, c2, c3, c4, c5, c6, c7, c8;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_employee);


        // Initialize the buttons inside onCreate
        c1 = findViewById(R.id.cardView1);
        c2 = findViewById(R.id.cardView2);
        c3 = findViewById(R.id.cardView3);
        c4 = findViewById(R.id.cardView4);
        c5 = findViewById(R.id.cardView5);
        c6 = findViewById(R.id.cardView6);
        c7 = findViewById(R.id.cardView7);
        c8 = findViewById(R.id.cardView8);


        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, SearchJobsActivity.class);
                startActivity(intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, SetPreferenceActivity.class);
                startActivity(intent);
            }
        });


        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, MainActivity.class);
                startActivity(intent);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, Map.class);
                startActivity(intent);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, ViewJobs.class);
                startActivity(intent);
            }
        });

        c5.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployee.this, EmployeeProfile.class);
                startActivity(intent);
            }

        });

    }


}
