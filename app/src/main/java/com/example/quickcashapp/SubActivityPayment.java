package com.example.quickcashapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;

public class SubActivityPayment extends MainActivityEmployer {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_payment);
    }
}