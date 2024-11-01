package com.example.quickcashapp;

import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcashapp.Employee.MainActivityEmployee;
import com.example.quickcashapp.Employer.MainActivityEmployer;
import com.example.quickcashapp.Maps.LocationHelper;
import java.util.Map;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        LocationHelper locationHelper = new LocationHelper(this);
        locationHelper.askForPermissions();

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