package com.example.quickcashapp.employeeDashboard;

import android.content.Context;
import android.content.DialogInterface;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcashapp.Job;
import com.example.quickcashapp.LoginActivity;
import com.example.quickcashapp.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddPreferenceActivity extends AppCompatActivity {
    private Context context;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private String jobTitle;
    private String userId;
    private boolean isValid;


    public AddPreferenceActivity(Context context, String jobTitle){
        this.context = context;
        this.jobTitle = jobTitle;
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        user = mAuth.getCurrentUser();
    }

    public void showMessage() {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setMessage("then click 'Yes'");
        builder.setTitle("Add to Preference?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
            save2Database();
        });

        builder.setNegativeButton("No", (DialogInterface.OnClickListener) (dialog, which) -> {
            dialog.dismiss();
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    protected void save2Database() {
        userId = user.getUid();
        DatabaseReference firebaseRef = mDatabase.child("preference").child(userId);

        // Checking if the job already exists
        checkValid(firebaseRef, jobTitle, isValid -> {
            if (isValid) {
                getCount(firebaseRef, count -> {
                    PreferenceInfo preferenceInfo = new PreferenceInfo(jobTitle);

                    firebaseRef.child("Job" + count).setValue(preferenceInfo)
                            .addOnSuccessListener(aVoid -> Toast.makeText(context, "Preference saved successfully", Toast.LENGTH_SHORT).show())
                            .addOnFailureListener(e -> Toast.makeText(context, "Failed to save preference: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                });
            } else {
                Toast.makeText(context, "This job is already in the database!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean checkValidStoring(){
        return isValid;
    }

    protected void getCount(DatabaseReference ref, CountCallback callback) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                long count = snapshot.getChildrenCount() + 1;
                callback.onCountReceived(count); // Pass the count to the callback
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onCountReceived(0); // Handle errors gracefully
            }
        });
    }

    interface CountCallback {
        void onCountReceived(long count);
    }


    protected void checkValid(DatabaseReference ref, String jobTitle, ValidCallback callback) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isValid = true;

                // checking for dulplicated information
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    PreferenceInfo existingPreference = childSnapshot.getValue(PreferenceInfo.class);
                    if (existingPreference != null && existingPreference.getJobTitle().equals(jobTitle)) {
                        isValid = false;
                        break;
                    }
                }

                callback.onValidationChecked(isValid);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onValidationChecked(false);
            }
        });
    }

    interface ValidCallback {
        void onValidationChecked(boolean isValid);
    }

}