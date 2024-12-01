package com.example.quickcashapp.Firebase;


import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcashapp.Job;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
                //return null;
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
}
