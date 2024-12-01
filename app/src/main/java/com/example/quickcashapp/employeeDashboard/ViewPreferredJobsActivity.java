package com.example.quickcashapp.employeeDashboard;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcashapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewPreferredJobsActivity extends AppCompatActivity {

    private ListView listViewJobs;
    private Button buttonLoadJobs;
    private ArrayAdapter<String> jobAdapter;
    private List<String> jobTitles;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_preferred_jobs);

        listViewJobs = findViewById(R.id.listViewJobs);
        buttonLoadJobs = findViewById(R.id.buttonLoadJobs);
        jobTitles = new ArrayList<>();
        jobAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, jobTitles);
        listViewJobs.setAdapter(jobAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("preference");

        buttonLoadJobs.setOnClickListener(v -> loadJobTitles());
    }

    private void loadJobTitles() {
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jobTitles.clear(); // Clear old data
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                        String jobTitle = jobSnapshot.child("jobTitle").getValue(String.class);
                        if (jobTitle != null) {
                            jobTitles.add(jobTitle);
                        }
                    }
                }
                jobAdapter.notifyDataSetChanged(); // Refresh the list view
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(ViewPreferredJobsActivity.this, "Failed to load job titles", Toast.LENGTH_SHORT).show();
            }
        });
    }
}