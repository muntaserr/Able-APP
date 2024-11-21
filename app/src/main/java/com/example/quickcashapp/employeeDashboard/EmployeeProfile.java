package com.example.quickcashapp.employeeDashboard;

import android.os.Bundle;


import androidx.activity.EdgeToEdge;

import com.example.quickcashapp.R;

public class EmployeeProfile extends MainActivityEmployee {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_profile);

    }
}
