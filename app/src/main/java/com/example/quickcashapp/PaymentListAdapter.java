package com.example.quickcashapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.JobViewHolder> {

    private List<Job> jobList;
    private List<JobStatus> jobStatusList;
    private OnJobActionListener listener;

    public PaymentListAdapter(List<Job> jobList, List<JobStatus> jobStatusList, OnJobActionListener listener) {
        this.jobList = jobList;
        this.jobStatusList = jobStatusList;
        this.listener = listener;
    }

    @Override
    public JobViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.payment_item_row, parent, false);
        return new JobViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(JobViewHolder holder, int position) {
        Job job = jobList.get(position);
        JobStatus jobStatus = findJobStatusByJobId(job.getJobId());

        holder.jobTitleTV.setText(job.getTitle());
        holder.jobSalaryTV.setText("Salary: $" + job.getSalary());

        // Fetch and display employee's name
        if (jobStatus != null && jobStatus.getEmployeeID() != null) {
            DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(jobStatus.getEmployeeID());
            userRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String employeeName = snapshot.getValue(String.class);
                    holder.employeeNameTV.setText(employeeName != null ? "Employee: " + employeeName : "Employee: Unknown");
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    holder.employeeNameTV.setText("Employee: Error");
                }
            });
        } else {
            holder.employeeNameTV.setText("Employee: Not Assigned");
        }

        // Determine Pay button state based on status
        if (jobStatus != null && "paid".equalsIgnoreCase(jobStatus.getStatus())) {
            holder.payBtn.setEnabled(false);
            holder.payBtn.setText("Paid");
            holder.markCompleteBtn.setVisibility(View.GONE);
        } else if (jobStatus != null && "completed".equalsIgnoreCase(jobStatus.getStatus())) {
            holder.payBtn.setEnabled(true);
            holder.payBtn.setText("Pay Now");

            // Handle Pay button click
            holder.payBtn.setOnClickListener(v -> {
                listener.onPayClicked(job);

                // Mark as paid in Firebase
                markJobAsPaid(jobStatus);
                holder.payBtn.setEnabled(false);
                holder.payBtn.setText("Paid");
                holder.markCompleteBtn.setVisibility(View.GONE);
            });
        } else {
            holder.payBtn.setEnabled(false);
            holder.payBtn.setText("Pending Completion");
            holder.markCompleteBtn.setVisibility(View.VISIBLE);
            holder.markCompleteBtn.setOnClickListener(v -> {
                listener.onMarkCompleteClicked(job);
                holder.markCompleteBtn.setVisibility(View.GONE);
            });
        }
    }

    private void markJobAsPaid(JobStatus jobStatus) {
        DatabaseReference jobStatusRef = FirebaseDatabase.getInstance().getReference("jobStatuses").child(jobStatus.getJobId());
        jobStatusRef.child("status").setValue("paid")
                .addOnSuccessListener(aVoid -> {
                    // Toast.makeText(context, "Job marked as paid", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {

                    // Log.e("PaymentError", "Failed to mark job as paid: " + e.getMessage());
                });
    }





    @Override
    public int getItemCount() {
        return jobList.size();
    }

    // Helper method to find JobStatus by jobId
    private JobStatus findJobStatusByJobId(String jobId) {
        for (JobStatus status : jobStatusList) {
            if (status.getJobId().equals(jobId)) {
                return status;
            }
        }
        return null;
    }

    public interface OnJobActionListener {
        void onPayClicked(Job job);

        void onMarkCompleteClicked(Job job);
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitleTV, jobSalaryTV, employeeNameTV;
        Button payBtn, markCompleteBtn;
        boolean isPaid = false;

        public JobViewHolder(@NonNull View itemView) {
            super(itemView);
            jobTitleTV = itemView.findViewById(R.id.jobTitleTV);
            jobSalaryTV = itemView.findViewById(R.id.jobSalaryTV);
            employeeNameTV = itemView.findViewById(R.id.employeeNameTV);
            payBtn = itemView.findViewById(R.id.payBtn);
            markCompleteBtn = itemView.findViewById(R.id.updateStatusBtn);
        }
    }



}
