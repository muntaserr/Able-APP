package com.example.quickcashapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcashapp.Employee.MainActivityEmployee;
import com.example.quickcashapp.Employer.MainActivityEmployer;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        requestLocationPermissions();


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

    private void requestLocationPermissions(){

        new AlertDialog.Builder(this)
                .setTitle("Location Permission")
                .setMessage("This app needs location permission to better help you find relevant jobs near you")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id){
                        //Request permission

                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        // Add logic here for user implementing there own location
                    }
                })
                .show();
    }
}