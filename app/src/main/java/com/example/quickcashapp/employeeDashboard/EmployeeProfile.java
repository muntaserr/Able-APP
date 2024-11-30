package com.example.quickcashapp.employeeDashboard;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


import androidx.activity.EdgeToEdge;

import com.example.quickcashapp.LoginActivity;
import com.example.quickcashapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class EmployeeProfile extends MainActivityEmployee {

    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_profile);

        //Setup Firebase Auth and Database
        mAuth = FirebaseAuth.getInstance();


        //When logout button is clicked take to method to logout user
        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> logoutUser());

    }

    /**
     * Logs out the user from the application.
     *
     * This method displays a confirmation dialog to the user.
     * If the user confirms, it performs the following actions:
     *
     * - Signs the user out of Firebase authentication.
     * - Displays a toast message indicating a successful logout.
     * - Switches the activity to the login activity.
     * - Closes the current activity (profile activity).
     *
     * If the user says No the user is not logged out
     */

    private void logoutUser(){

        new AlertDialog.Builder(EmployeeProfile.this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){

                        //Sign out the user from firebase
                        mAuth.signOut();
                        Toast.makeText(EmployeeProfile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

                        //Switch the activity to the login activity
                        Intent intent = new Intent(EmployeeProfile.this, LoginActivity.class); //Fill in null with login activity
                        startActivity(intent);
                        finish(); //Close the profile activity

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}

