package com.example.quickcashapp.employeeDashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashapp.JobListing;
import com.example.quickcashapp.R;

import java.util.List;

public class PreferredJobListAdapter extends RecyclerView.Adapter<PreferredJobListAdapter.ViewHolder> {
    private final List<JobListing> jobList;
    private final OnJobActionListener listener;

    public PreferredJobListAdapter(List<JobListing> jobList, OnJobActionListener listener) {
        this.jobList = jobList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_preferredjob_listing, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JobListing job = jobList.get(position);
        holder.bind(job, listener);
    }

    @Override
    public int getItemCount() {
        return jobList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView jobTitle, salary, duration, urgency, location;
        private final Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitle = itemView.findViewById(R.id.jobTitleTextView);
            salary = itemView.findViewById(R.id.salaryTextView);
            duration = itemView.findViewById(R.id.durationTextView);
            urgency = itemView.findViewById(R.id.urgencyTextView);
            location = itemView.findViewById(R.id.locationTextView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(JobListing job, OnJobActionListener listener) {
            jobTitle.setText(job.getTitle());
            salary.setText("Salary: " + job.getSalary());
            duration.setText("Duration: " + job.getDuration());
            urgency.setText("Urgency: " + job.getUrgency());
            location.setText("Location: " + job.getLocation());

            deleteButton.setOnClickListener(v -> {
                Toast.makeText(deleteButton.getContext(), "delete button pressed", Toast.LENGTH_SHORT).show();
            });
        }
    }

    public interface OnJobActionListener {
        void onDeleteButton(JobListing job);
    }
}
