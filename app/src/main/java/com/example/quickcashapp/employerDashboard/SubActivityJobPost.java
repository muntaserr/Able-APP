package com.example.quickcashapp.employerDashboard;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

import com.example.quickcashapp.R;

public class SubActivityJobPost extends MainActivityEmployer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitivty_sub_jobpost);
    }
}
