package com.example.quickcashapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;

public class MainActivityEmployer extends ComponentActivity {

    TextView v1, v2, v3, v4, v5, v6;
    CardView c1, c2, c3, c4, c5, c6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_employer);


        v1 = findViewById(R.id.textView1);
        v2 = findViewById(R.id.textView2);
        v3 = findViewById(R.id.textView3);
        v4 = findViewById(R.id.textView4);
        v5 = findViewById(R.id.textView5);
        v6 = findViewById(R.id.textView6);

        c1 = findViewById(R.id.cardView1);
        c2 = findViewById(R.id.cardView2);
        c3 = findViewById(R.id.cardView3);
        c4 = findViewById(R.id.cardView4);
        c5 = findViewById(R.id.cardView5);
        c6 = findViewById(R.id.cardView6);

        // Set text for TextViews
        v1.setText("MAP");
        v2.setText("ROLE SWITCH");
        v3.setText("PREFERRED EMPLOYEE");
        v4.setText("JOB POST");
        v5.setText("PAYMENT");
        v6.setText("MY PROFILE");


        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityMap.class);
                startActivity(intent);
            }
        });

        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, MainActivity.class);
                startActivity(intent);
            }
        });

        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityPreferredEmployee.class);
                startActivity(intent);
            }
        });

        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityJobPost.class);
                startActivity(intent);
            }
        });

        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityPayment.class);
                startActivity(intent);
            }
        });

        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityProfile.class);
                startActivity(intent);
            }
        });
    }
}
