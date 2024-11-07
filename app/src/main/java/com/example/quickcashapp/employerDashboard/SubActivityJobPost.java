package com.example.quickcashapp.employerDashboard;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quickcashapp.R;
import com.example.quickcashapp.Job; // Import the Job class
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SubActivityJobPost extends MainActivityEmployer {

    private EditText jobTitleEditText, salaryEditText, durationEditText, urgencyEditText, locationEditText;
    private Button submitButton;
    private DatabaseReference jobsDatabaseReference;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.acitivty_sub_jobpost);  // Ensure the correct layout file name

        // Initialize Firebase Database reference
        jobsDatabaseReference = FirebaseDatabase.getInstance().getReference("jobs");

        // Initialize UI elements
        jobTitleEditText = findViewById(R.id.jobTitle);
        salaryEditText = findViewById(R.id.salary);
        durationEditText = findViewById(R.id.duration);
        urgencyEditText = findViewById(R.id.urgency);
        locationEditText = findViewById(R.id.location);
        submitButton = findViewById(R.id.submitButton);

        // Check for location permissions
        checkLocationPermission();

        // Set up the submit button click listener
        submitButton.setOnClickListener(view -> submitJob());
    }

    /**
     * Checks if the app has permission to access fine location.
     * If not, requests permission from the user.
     */
    private void checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request location permission if not granted
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Permission already granted, fetch location
            fetchUserLocation();
        }
    }

    /**
     * Attempts to fetch the user's current location.
     * If location permission is granted, it will retrieve the last known location.
     * If location detection fails, prompts the user to enter it manually.
     */
    private void fetchUserLocation() {
        // Placeholder for location fetching logic
        locationEditText.setHint("Enter job location manually if location detection fails");
    }

    /**
     * Handles the job submission process.
     * Validates all input fields, creates a Job object, and saves it to Firebase.
     */
    private void submitJob() {
        // Retrieve input values from EditText fields
        String jobTitle = jobTitleEditText.getText().toString().trim();
        String salary = salaryEditText.getText().toString().trim();
        String duration = durationEditText.getText().toString().trim();
        String urgency = urgencyEditText.getText().toString().trim();
        String location = locationEditText.getText().toString().trim();

        // Validate input fields; exit method if validation fails
        if (!validateInput(jobTitle, salary, duration, urgency, location)) return;

        // Generate a unique ID for the job post
        String jobId = jobsDatabaseReference.push().getKey();
        if (jobId == null) {
            // Handle error if jobId could not be generated
            Toast.makeText(this, "Failed to generate job ID. Please try again.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new Job object with the input details
        Job job = new Job(jobId, jobTitle, salary, duration, urgency, location);

        // Save the Job object to Firebase
        saveJobToFirebase(jobId, job);
    }

    /**
     * Validates each input field to ensure they are not empty.
     * Sets an error message on the respective field if validation fails.
     *
     * @param jobTitle The title of the job.
     * @param salary   The salary offered for the job.
     * @param duration The expected duration of the job.
     * @param urgency  The urgency level of the job.
     * @param location The location of the job.
     * @return true if all fields are valid; false otherwise.
     */
    private boolean validateInput(String jobTitle, String salary, String duration, String urgency, String location) {
        if (TextUtils.isEmpty(jobTitle)) {
            jobTitleEditText.setError("Job title is required");
            return false;
        }
        if (TextUtils.isEmpty(salary)) {
            salaryEditText.setError("Salary is required");
            return false;
        }
        if (TextUtils.isEmpty(duration)) {
            durationEditText.setError("Expected duration is required");
            return false;
        }
        if (TextUtils.isEmpty(urgency)) {
            urgencyEditText.setError("Urgency level is required");
            return false;
        }
        if (TextUtils.isEmpty(location)) {
            locationEditText.setError("Job location is required");
            return false;
        }
        return true;
    }

    /**
     * Saves the Job object to Firebase Database.
     *
     * @param jobId The unique ID for the job post.
     * @param job   The Job object containing job details.
     */
    private void saveJobToFirebase(String jobId, Job job) {
        jobsDatabaseReference.child(jobId).setValue(job)
                .addOnSuccessListener(aVoid -> {
                    // Job saved successfully, show confirmation and close the activity
                    Toast.makeText(SubActivityJobPost.this, "Job submitted successfully!", Toast.LENGTH_SHORT).show();
                    finish(); // Close the form and return to the previous screen
                })
                .addOnFailureListener(e -> {
                    // Show error message if job save fails
                    Toast.makeText(SubActivityJobPost.this, "Failed to submit job: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Handles the result of the location permission request.
     * If permission is granted, fetches the user's location.
     * If permission is denied, prompts the user to enter the location manually.
     *
     * @param requestCode  The request code for the permission request.
     * @param permissions  The requested permissions.
     * @param grantResults The results of the permission requests.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, proceed to fetch location
                fetchUserLocation();
            } else {
                // Permission denied, prompt user to enter location manually
                Toast.makeText(this, "Location permission denied. Please enter location manually.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

