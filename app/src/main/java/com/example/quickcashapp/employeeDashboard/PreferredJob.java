package com.example.quickcashapp.employeeDashboard;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashapp.JobListing;
import com.example.quickcashapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PreferredJob extends AppCompatActivity {
    private DatabaseReference preferenceRef, jobsRef;
    private RecyclerView preferredJobsRecyclerView;
    private PreferredJobListAdapter adapter;
    private List<JobListing> preferredJobList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferredjobs);

        initFirebaseReferences();
        initRecyclerView();

        fetchPreferredJobs();
    }

    private void initFirebaseReferences() {
        preferenceRef = FirebaseDatabase.getInstance().getReference("preference");
        jobsRef = FirebaseDatabase.getInstance().getReference("jobs");
    }

    private void initRecyclerView() {
        preferredJobsRecyclerView = findViewById(R.id.preferredjobsRecyclerView);
        preferredJobsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PreferredJobListAdapter(preferredJobList, job -> {
            // Optional: Implement job delete or other actions
            Log.d("JobAction", "Delete clicked for: " + job.getTitle());
        });
        preferredJobsRecyclerView.setAdapter(adapter);
    }

    private void fetchPreferredJobs() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        preferenceRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String preferredJobTitle = snapshot.child("jobTitle").getValue(String.class);

                if (preferredJobTitle != null) {
                    fetchJobsByPreference(preferredJobTitle);
                } else {
                    Log.d("PreferredJob", "No preferred job title found for user: " + userId);
                    updateUIWithResults();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching preference: " + error.getMessage());
            }
        });
    }

    private void fetchJobsByPreference(String preferredJobTitle) {
        jobsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                preferredJobList.clear();
                for (DataSnapshot jobSnapshot : snapshot.getChildren()) {
                    JobListing job = jobSnapshot.getValue(JobListing.class);
                    if (job != null && preferredJobTitle.equals(job.getTitle())) {
                        preferredJobList.add(job);
                    }
                }
                updateUIWithResults();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching jobs: " + error.getMessage());
            }
        });
    }

    private void updateUIWithResults() {
        if (preferredJobList.isEmpty()) {
            preferredJobsRecyclerView.setVisibility(View.GONE);
            Log.d("PreferredJob", "No preferred jobs found.");
        } else {
            preferredJobsRecyclerView.setVisibility(View.VISIBLE);
        }
        adapter.notifyDataSetChanged();
    }
}
