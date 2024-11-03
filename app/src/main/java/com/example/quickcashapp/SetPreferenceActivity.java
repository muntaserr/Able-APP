package com.example.quickcashapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;

public class SetPreferenceActivity extends ComponentActivity {


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Enable EdgeToEdge display if required
        EdgeToEdge.enable(this);
        // Set the content view to your activity's layout
        setContentView(R.layout.activity_set_preference);
        // You can add more logic here (e.g., setting up views, buttons, etc.)

    }

    private void savePreferences() {
        Toast.makeText(this, "Preference is saved！", Toast.LENGTH_SHORT).show();
    }

}

