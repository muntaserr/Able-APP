package com.example.quickcashapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.example.quickcashapp.employerDashboard.SubActivityPayment;

public class EmployerRating extends AppCompatActivity {

    private String jobId;
    private String employeeID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rating_popup);

        jobId = getIntent().getStringExtra("jobId");
        employeeID = getIntent().getStringExtra("employeeID");

        if (jobId == null || employeeID == null) {
            Toast.makeText(this, "Missing required data", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        RatingBar ratingBar = findViewById(R.id.rating_bar);
        Button submitButton = findViewById(R.id.submit_rating_button);

        submitButton.setOnClickListener(v -> {
            float rating = ratingBar.getRating();
            saveRatingToFirebase(jobId, employeeID, rating);

            Toast.makeText(this, "Rating submitted!", Toast.LENGTH_SHORT).show();

            // Redirect back to PaymentActivity
            Intent restartIntent = new Intent(this, SubActivityPayment.class);
            restartIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(restartIntent);

            // Finish the EmployerRating activity
            finish();
        });

    }

    private void saveRatingToFirebase(String jobId, String employeeID, float rating) {
        DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("ratings");
        ratingRef.child(employeeID).child(jobId).setValue(rating)
                .addOnSuccessListener(aVoid -> Log.d("EmployerRating", "Rating saved successfully"))
                .addOnFailureListener(e -> Log.e("EmployerRating", "Failed to save rating: " + e.getMessage()));
    }
}
