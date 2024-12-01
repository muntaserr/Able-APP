package com.example.quickcashapp.employeeDashboard;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;


import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;

import com.example.quickcashapp.Firebase.FirebaseCRUD;
import com.example.quickcashapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class EmployeeProfile extends MainActivityEmployee {

    private FirebaseCRUD firebaseCRUD;
    private RatingBar ratingBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_sub_profile);


        ratingBar = findViewById(R.id.profile_rating);

        mAuth = FirebaseAuth.getInstance();
        firebaseCRUD = new FirebaseCRUD();

        String employeeID = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        setRating(employeeID);

        Button logoutButton = findViewById(R.id.logout_button);
        logoutButton.setOnClickListener(v -> firebaseCRUD.logoutUser(this, mAuth));
    }

    private void setRating(String employeeID){
        DatabaseReference ratingRef = FirebaseDatabase.getInstance().getReference("ratings").child(employeeID);

        ratingRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                double totalRatingChange = 0;
                int count = 0;
                Log.e("Julians Bio", "Julian is wants to ligma and lokey is always imagining dragons");
                for (DataSnapshot ratingSnapshot : snapshot.getChildren()) {
                    Integer rating = ratingSnapshot.getValue(Integer.class);
                    if (rating != null) {
                        totalRatingChange += rating;
                        count++;
                    }
                }
                if(count > 0){
                    double avgRating = totalRatingChange/count;
                    ratingBar.setRating((float)avgRating);
                } else {
                    ratingBar.setRating(0);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to get the ratings from firebase", error.toException());
            }
        });
    }
}


