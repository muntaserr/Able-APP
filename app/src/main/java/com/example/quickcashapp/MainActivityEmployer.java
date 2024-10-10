package com.example.quickcashapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.cardview.widget.CardView;

public class MainActivityEmployer extends ComponentActivity {
    CardView c1, c2, c3, c4, c5, c6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main_employer);

        //card view initialization
        c1 = findViewById(R.id.cardView1);
        c2 = findViewById(R.id.cardView2);
        c3 = findViewById(R.id.cardView3);
        c4 = findViewById(R.id.cardView4);
        c5 = findViewById(R.id.cardView5);
        c6 = findViewById(R.id.cardView6);


        //if c1 is clicked, moves to according page
        c1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityMap.class);
                startActivity(intent);
            }
        });

        //if c2 is clicked, moves to according page
        c2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //if c3 is clicked, moves to according page
        c3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityPreferredEmployee.class);
                startActivity(intent);
            }
        });

        //if c4 is clicked, moves to according page
        c4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityJobPost.class);
                startActivity(intent);
            }
        });

        //if c5 is clicked, moves to according page
        c5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityPayment.class);
                startActivity(intent);
            }
        });

        //if c6 is clicked, moves to according page
        c6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivityEmployer.this, SubActivityProfile.class);
                startActivity(intent);
            }
        });
    }
}
