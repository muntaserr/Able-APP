package com.example.quickcashapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

import com.example.quickcashapp.Maps.LocationHelper;
import com.example.quickcashapp.employeeDashboard.MainActivityEmployee;
import com.example.quickcashapp.employerDashboard.MainActivityEmployer;

public class MainActivity extends ComponentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Button btn1 = findViewById(R.id.button); //employer
        Button btn2 = findViewById(R.id.button2); //employee

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, MainActivityEmployer.class);
                startActivity(intent1);
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(MainActivity.this, MainActivityEmployee.class);
                startActivity(intent2);
            }
        });
    }
}