package com.example.quickcashapp;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class MainActivityEmployer extends ComponentActivity {

    TextView v1 = findViewById(R.id.textView1);
    TextView v2 = findViewById(R.id.textView2);
    TextView v3 = findViewById(R.id.textView3);
    TextView v4 = findViewById(R.id.textView4);
    TextView v5 = findViewById(R.id.textView5);
    TextView v6 = findViewById(R.id.textView6);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_employer);
        v1.setText("MAP");
        v2.setText("ROLE SWITCH");
        v3.setText("PREFERRED EMPLOYEE");
        v4.setText("JOB POST");
        v5.setText("PAYMENT");
        v6.setText("MY PROFILE");


    }



}
