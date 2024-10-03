package com.example.quickcashapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity; // AppCompatActivity is preferred in most cases due to its extensive support for Android UI features

public class PreferencesActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences);  // Link to the layout XML file
    }
}
