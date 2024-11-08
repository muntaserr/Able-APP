package com.example.quickcashapp;

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

    @NonNull
    @Override
    public JobViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_job_listing, parent, false);
        return new JobViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull JobViewHolder holder, int position) {
        JobListing job = jobList.get(position);
        holder.jobTitleTextView.setText(job.getJobTitle());
        holder.salaryTextView.setText("Salary: $" + job.getMinSalary() + " - $" + job.getMaxSalary());
        holder.durationTextView.setText("Duration: " + job.getDuration());
        holder.vicinityTextView.setText("Vicinity: " + job.getVicinity() + " km");
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitleTextView;
        TextView salaryTextView;
        TextView durationTextView;
        TextView vicinityTextView;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTextView = itemView.findViewById(R.id.jobTitleTextView);
            salaryTextView = itemView.findViewById(R.id.salaryTextView);
            durationTextView = itemView.findViewById(R.id.durationTextView);
            vicinityTextView = itemView.findViewById(R.id.vicinityTextView);
        }
    }
}
