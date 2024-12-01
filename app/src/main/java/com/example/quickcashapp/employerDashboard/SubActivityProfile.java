package com.example.quickcashapp.employerDashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;

import com.example.quickcashapp.Firebase.FirebaseCRUD;
import com.example.quickcashapp.LoginActivity;
import com.example.quickcashapp.R;

import com.google.firebase.auth.FirebaseAuth;




public class SubActivityProfile extends MainActivityEmployer {


    private FirebaseAuth mAuth;
    private FirebaseCRUD firebaseCRUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_profile);


        //Setup Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        firebaseCRUD = new FirebaseCRUD();

        //When logout button is clicked take to method to logout user
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> firebaseCRUD.logoutUser(this, mAuth));

    }
}