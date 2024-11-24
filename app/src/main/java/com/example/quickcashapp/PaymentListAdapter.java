package com.example.quickcashapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PaymentListAdapter extends RecyclerView.Adapter<PaymentListAdapter.JobViewHolder> {

    private List<Job> jobList;
    private OnJobActionListener listener;

    public PaymentListAdapter(List<Job> jobList, OnJobActionListener listener) {
        this.jobList = jobList;
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

        // Bind data to views
        holder.jobTitleTV.setText(job.getTitle());
        holder.jobSalaryTV.setText("Salary: $" + job.getSalary());

        // Disable Pay button if the job is not completed
        holder.payBtn.setEnabled("completed".equals(job.getStatus()));

        // Set up button actions
        holder.payBtn.setOnClickListener(v -> {
            if (holder.payBtn.isEnabled()) {
                listener.onPayClicked(job);
            }
        });

        holder.markCompleteBtn.setOnClickListener(v -> {
            listener.onMarkCompleteClicked(job);
        });
    }


    @Override
    public int getItemCount() {
        return jobList.size();
    }

    public interface OnJobActionListener {
        void onPayClicked(Job job);
        void onMarkCompleteClicked(Job job);
    }

    public static class JobViewHolder extends RecyclerView.ViewHolder {
        TextView jobTitleTV, jobSalaryTV;
        Button payBtn, markCompleteBtn;

        public JobViewHolder(View itemView) {
            super(itemView);
            jobTitleTV = itemView.findViewById(R.id.jobTitleTV);
            jobSalaryTV = itemView.findViewById(R.id.jobSalaryTV);
            payBtn = itemView.findViewById(R.id.payBtn);
            markCompleteBtn = itemView.findViewById(R.id.updateStatusBtn);
        }
    }
}

