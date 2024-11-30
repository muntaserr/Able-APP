package com.example.quickcashapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashapp.employeeDashboard.AddPreferenceActivity;
import com.example.quickcashapp.employeeDashboard.SearchJobsActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {

    private List<JobListing> jobList;
    private OnJobActionListener listener;

    public JobListAdapter(List<JobListing> jobList, OnJobActionListener listener) {
        this.jobList = new ArrayList<>();
        this.listener = listener;

        // Filter out jobs with status "in-progress"
        for (JobListing job : jobList) {
            DatabaseReference jobStatusRef = FirebaseDatabase.getInstance().getReference("jobStatuses").child(job.getJobId());
            jobStatusRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String status = dataSnapshot.child("status").getValue(String.class);
                    if (!"in-progress".equalsIgnoreCase(status)) {
                        JobListAdapter.this.jobList.add(job);
                        notifyDataSetChanged(); // Refresh RecyclerView
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.e("FirebaseError", "Failed to fetch job status: " + databaseError.getMessage());
                }
            });
        }
    }


    /**
     * Called when a new ViewHolder is created to represent an item.
     *
     * @param parent   The ViewGroup into which the new View will be added after it is bound to
     *                 an adapter position.
     * @param viewType The view type of the new View.
     * @return A new instance of JobViewHolder that holds the created View.
     */
    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_listing, parent, false);
        return new JobViewHolder(view);
    }

    /**
     * Called to bind the data to the ViewHolder for a specific position in the list.
     *
     * @param holder   The ViewHolder to update with the job listing data.
     * @param position The position of the item in the dataset.
     */
    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobListing job = jobList.get(position);

        holder.jobTitleTextView.setText(job.getTitle());
        holder.salaryTextView.setText("Salary: " + job.getSalary());
        holder.durationTextView.setText("Duration: " + job.getDuration());
        holder.urgencyTextView.setText("Urgency: " + job.getUrgency());
        holder.locationTextView.setText("Location: " + job.getLocation());

        //add the informations to the database
        holder.add2PreferenceButton.setOnClickListener(v -> {
            AddPreferenceActivity addPreferenceActivity = new AddPreferenceActivity(holder.itemView.getContext(), job.getTitle());
            boolean isValid = addPreferenceActivity.checkValidStoring();
            if(!isValid){
                holder.add2PreferenceButton.setEnabled(false);
                holder.add2PreferenceButton.setText("Already added to the Preference");
            }else{
                holder.add2PreferenceButton.setEnabled(true);
                holder.add2PreferenceButton.setText("Add to Preference");
            }

            addPreferenceActivity.showMessage();

        });


        // Fetch the status from the JobStatus class
        DatabaseReference jobStatusRef = FirebaseDatabase.getInstance().getReference("jobStatuses").child(job.getJobId());
        jobStatusRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String status = dataSnapshot.child("status").getValue(String.class);

                // Disable the "Accept Job" button if the job is already in-progress
                if ("in-progress".equalsIgnoreCase(status)) {
                    holder.acceptJobButton.setEnabled(false);
                    holder.acceptJobButton.setText("Already Accepted");
                } else {
                    holder.acceptJobButton.setEnabled(true);
                    holder.acceptJobButton.setText("Accept Job");

                    // Handle button click
                    holder.acceptJobButton.setOnClickListener(v -> {
                        listener.onAcceptJobClicked(job);

                        // Disable the button immediately to prevent duplicate clicks
                        holder.acceptJobButton.setEnabled(false);
                        holder.acceptJobButton.setText("Already Accepted");
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.e("FirebaseError", "Failed to fetch job status: " + databaseError.getMessage());
            }
        });
    }


    /**
     * Returns the total number of items in the dataset.
     *
     * @return The total number of job listings.
     */
    @Override
    public int getItemCount() {
        return jobList.size();
    }
    public interface OnJobActionListener {
        void onAcceptJobClicked(JobListing job);
    }

    /**
     * ViewHolder class for holding and managing the views for individual job listings.
     */
    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitleTextView, salaryTextView, durationTextView, urgencyTextView, locationTextView;
        Button acceptJobButton;
        Button add2PreferenceButton;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            urgencyTextView = itemView.findViewById(R.id.urgencyTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
            acceptJobButton = itemView.findViewById(R.id.acceptJobButton);
            add2PreferenceButton = itemView.findViewById(R.id.add2PreferenceButton);
        }
    }
}