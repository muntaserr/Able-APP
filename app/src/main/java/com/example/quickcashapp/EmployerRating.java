package com.example.quickcashapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EmployerRating  {

    public void showRatingDialog(Context context, String jobId, String employeeID){

        View dialogView = LayoutInflater.from(context).inflate(R.layout.rating_popup, null);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setView(dialogView);
        AlertDialog dialog = builder.create();

        //get the rating bar and submit button
        RatingBar ratingBar = dialogView.findViewById(R.id.rating_bar);
        Button Submit = dialogView.findViewById(R.id.submit_rating_button);

        //On submit in the alert dialog get the rating inputted by the employer
        Submit.setOnClickListener(v -> {
            float rating = ratingBar.getRating();

            saveRatingToFirebase(jobId, employeeID, rating);

            Toast.makeText(context, "Rating submitted!", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        });
        dialog.show();
    }

    /**
     * Saves the rating from the employer to the firebase
     * @param jobId ID of the job
     * @param employeeId ID of the employee who completed the job
     * @param rating the rating the employer gave to the employee
     */
    private void saveRatingToFirebase(String jobId, String employeeId, float rating) {
        DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("ratings");
        ratingRef.child(employeeId).child(jobId).setValue(rating);
    }
}
