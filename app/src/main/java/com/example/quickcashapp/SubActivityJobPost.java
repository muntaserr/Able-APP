package com.example.quickcashapp;

import static com.example.quickcashapp.R.*;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class SubActivityJobPost extends MainActivityEmployer {
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.acitivty_sub_jobpost);

        // Initialize Firebase Authentication and Database reference
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Get UI elements
        EditText jobTitleEditText = findViewById(id.jobTitle);
        EditText jobSalaryEditText = findViewById(id.jobSalary);
        EditText jobDurationEditText = findViewById(id.jobDuration);
        EditText jobUrgencyEditText = findViewById(id.jobUrgency);
        EditText jobLocationEditText = findViewById(id.jobLocation);
        Button submitJobButton = findViewById(id.submitJobButton);

        // Set the button click listener to submit the job
        submitJobButton.setOnClickListener(v -> {
            String title = jobTitleEditText.getText().toString().trim();
            String salaryStr = jobSalaryEditText.getText().toString().trim();
            String duration = jobDurationEditText.getText().toString().trim();
            String urgency = jobUrgencyEditText.getText().toString().trim();
            String location = jobLocationEditText.getText().toString().trim();

            if (title.isEmpty() || salaryStr.isEmpty() || duration.isEmpty() || urgency.isEmpty() || location.isEmpty()) {
                Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
                return;
            }

            double salary = Double.parseDouble(salaryStr);
            FirebaseUser currentUser = mAuth.getCurrentUser();

            if (currentUser != null) {
                String userId = currentUser.getUid();  // Get the current user's ID
                submitJobToFirebase(userId, title, salary, duration, urgency, location);
            } else {
                Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void submitJobToFirebase(String userId, String title, double salary, String duration,
                                     String urgency, String location) {
        // Generate a unique job ID
        String jobId = mDatabase.child("jobs").push().getKey();

        // Create the job data map
        Map<String, Object> job = new HashMap<>();
        job.put("title", title);
        job.put("salary", salary);
        job.put("duration", duration);
        job.put("urgency", urgency);
        job.put("location", location);
        job.put("postedBy", userId);

        // Save the job data under the 'jobs' node
        mDatabase.child("jobs").child(jobId).setValue(job)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Also store the job under the user's node
                        mDatabase.child("users").child(userId).child("jobs").child(jobId).setValue(job)
                                .addOnCompleteListener(userJobTask -> {
                                    if (userJobTask.isSuccessful()) {
                                        Toast.makeText(this, "Job submitted successfully!", Toast.LENGTH_SHORT).show();
                                        finish();  // Close the activity after submission
                                    } else {
                                        Toast.makeText(this, "Failed to save job under user", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Failed to submit job", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
