package com.example.quickcashapp;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class JobListAdapter extends RecyclerView.Adapter<JobListAdapter.JobViewHolder> {

    private List<JobListing> jobList;

    public JobListAdapter(List<JobListing> jobList) {
        this.jobList = jobList;
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
        Log.e("Lucas test", "got job listing salary is: " + job.getSalary());
        holder.jobTitleTextView.setText(job.getTitle());
        holder.salaryTextView.setText("Salary: " + job.getSalary().toString());
        holder.durationTextView.setText("Duration: " + job.getDuration());
        holder.urgencyTextView.setText("Urgency: " + job.getUrgency());
        holder.locationTextView.setText("Location: " + job.getLocation());
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

    /**
     * ViewHolder class for holding and managing the views for individual job listings.
     */
    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitleTextView;
        TextView salaryTextView;
        TextView durationTextView;
        TextView urgencyTextView;
        TextView locationTextView;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            urgencyTextView = itemView.findViewById(R.id.urgencyTextView);
            locationTextView = itemView.findViewById(R.id.locationTextView);
        }
    }
}