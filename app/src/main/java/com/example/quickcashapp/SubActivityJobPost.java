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

    // Firebase database and authentication references
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    // UI elements for job details
    private EditText jobTitleEditText;
    private EditText jobSalaryEditText;
    private EditText jobDurationEditText;
    private EditText jobUrgencyEditText;
    private EditText jobLocationEditText;
    private Button submitJobButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(layout.acitivty_sub_jobpost);  // Set the layout for this activity

        // Initialize Firebase authentication and database references
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Initialize UI elements
        jobTitleEditText = findViewById(id.jobTitle);
        jobSalaryEditText = findViewById(id.jobSalary);
        jobDurationEditText = findViewById(id.jobDuration);
        jobUrgencyEditText = findViewById(id.jobUrgency);
        jobLocationEditText = findViewById(id.jobLocation);
        submitJobButton = findViewById(id.submitJobButton);

        // Set the button click listener to submit the job
        submitJobButton.setOnClickListener(v -> submitJob());
    }

    /**
     * Validates input fields, retrieves the current user's ID, and submits job data to Firebase.
     */
    private void submitJob() {
        // Get input values from the text fields and trim whitespace
        String title = jobTitleEditText.getText().toString().trim();
        String salaryStr = jobSalaryEditText.getText().toString().trim();
        String duration = jobDurationEditText.getText().toString().trim();
        String urgency = jobUrgencyEditText.getText().toString().trim();
        String location = jobLocationEditText.getText().toString().trim();

        // Validate that all fields are filled in
        if (title.isEmpty() || salaryStr.isEmpty() || duration.isEmpty() || urgency.isEmpty() || location.isEmpty()) {
            Toast.makeText(this, "All fields are required", Toast.LENGTH_SHORT).show();
            return;
        }

        // Convert salary from String to double, with error handling for invalid input
        double salary;
        try {
            salary = Double.parseDouble(salaryStr);
        } catch (NumberFormatException e) {
            jobSalaryEditText.setError("Invalid salary format");  // Show error on salary field if format is incorrect
            Toast.makeText(this, "Please enter a valid salary", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get the current user
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String userId = currentUser.getUid();  // Get the current user's ID
        submitJobToFirebase(userId, title, salary, duration, urgency, location);  // Submit job data to Firebase
    }

    /**
     * Submits job details to Firebase under both 'jobs' and the user's node.
     *
     * @param userId    ID of the user posting the job
     * @param title     Job title
     * @param salary    Job salary
     * @param duration  Job duration
     * @param urgency   Urgency level of the job
     * @param location  Job location
     */
    private void submitJobToFirebase(String userId, String title, double salary, String duration,
                                     String urgency, String location) {
        // Generate a unique job ID
        String jobId = mDatabase.child("jobs").push().getKey();
        if (jobId == null) {
            Toast.makeText(this, "Error generating job ID", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a map to store the job details
        Map<String, Object> job = new HashMap<>();
        job.put("title", title);
        job.put("salary", salary);
        job.put("duration", duration);
        job.put("urgency", urgency);
        job.put("location", location);
        job.put("postedBy", userId);  // Link the job to the user ID

        // Save the job data under the 'jobs' node in the Firebase database
        mDatabase.child("jobs").child(jobId).setValue(job)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // If the job was saved successfully, also save it under the user's node
                        mDatabase.child("users").child(userId).child("jobs").child(jobId).setValue(job)
                                .addOnCompleteListener(userJobTask -> {
                                    if (userJobTask.isSuccessful()) {
                                        Toast.makeText(this, "Job submitted successfully!", Toast.LENGTH_SHORT).show();
                                        finish();  // Close the activity after successful submission
                                    } else {
                                        Toast.makeText(this, "Failed to save job under user", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    } else {
                        Toast.makeText(this, "Failed to submit job", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Getter and Setter methods for each field

    public String getJobTitle() {
        return jobTitleEditText.getText().toString().trim();
    }

    public void setJobTitle(String title) {
        jobTitleEditText.setText(title);
    }

    public String getJobSalary() {
        return jobSalaryEditText.getText().toString().trim();
    }

    public void setJobSalary(String salary) {
        jobSalaryEditText.setText(salary);
    }

    public String getJobDuration() {
        return jobDurationEditText.getText().toString().trim();
    }

    public void setJobDuration(String duration) {
        jobDurationEditText.setText(duration);
    }

    public String getJobUrgency() {
        return jobUrgencyEditText.getText().toString().trim();
    }

    public void setJobUrgency(String urgency) {
        jobUrgencyEditText.setText(urgency);
    }

    public String getJobLocation() {
        return jobLocationEditText.getText().toString().trim();
    }

    public void setJobLocation(String location) {
        jobLocationEditText.setText(location);
    }
}
