package com.example.quickcashapp.Firebase;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.quickcashapp.Job;
import com.example.quickcashapp.LoginActivity;
import com.example.quickcashapp.employerDashboard.SubActivityProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import android.content.Context;



import java.util.ArrayList;

public class FirebaseCRUD {
    private DatabaseReference dataRef;


    public FirebaseCRUD(){

    }
    public ArrayList<Job> getAllJobs(){

        ArrayList<Job> jobList = new ArrayList<Job>();
        dataRef = FirebaseDatabase.getInstance().getReference("jobs");
        dataRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);
                    jobList.add(job);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.err.println("Error reading jobs from Firebase: " + error.getMessage());

            }
        });
        return jobList;
    }


    public void getJob(String id, JobCallback callback) {
        dataRef = FirebaseDatabase.getInstance().getReference("jobs");
        dataRef.child(id).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting job data", task.getException());
                    callback.onJobRetrieved(null); // Pass null in case of failure
                } else {
                    Job job = task.getResult().getValue(Job.class); // Use getValue with the model class
                    callback.onJobRetrieved(job); // Pass the retrieved job
                }
            }
        });
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
    public void logoutUser(Context context, FirebaseAuth mAuth){

        new AlertDialog.Builder(context)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which){

                        //Sign out the user from firebase
                        mAuth.signOut();
                        Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show();

                        //Switch the activity to the login activity
                        Intent intent = new Intent(context, LoginActivity.class);
                        context.startActivity(intent);

                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
