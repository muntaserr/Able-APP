package com.example.quickcashapp.employerDashboard;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashapp.BuildConfig;
import com.example.quickcashapp.PaymentListAdapter;
import com.example.quickcashapp.R;
import com.example.quickcashapp.Job;
import com.example.quickcashapp.JobStatus;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SubActivityPayment extends AppCompatActivity {

    private static final String TAG = "SubActivityPayment";
    private ActivityResultLauncher<Intent> activityResultLauncher;
    private PayPalConfiguration payPalConfig;

    private RecyclerView paymentRecyclerView;
    private PaymentListAdapter paymentListAdapter;
    private List<Job> jobList = new ArrayList<>();
    private List<JobStatus> jobStatusList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_payment);

        // Configure PayPal settings
        configurePayPal();
        startPayPalService();
        initializeActivityLauncher();

        paymentRecyclerView = findViewById(R.id.paymentRecyclerView);
        paymentRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchJobsAndStatusesForUser();
    }

    private void configurePayPal() {
        payPalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Use Sandbox for testing
                .clientId(BuildConfig.PAYPAL_CLIENT_ID); // Client ID from BuildConfig
    }

    private void startPayPalService() {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(intent);
    }

    private void initializeActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            try {
                                String paymentDetails = confirmation.toJSONObject().toString(4);
                                Log.i(TAG, paymentDetails);

                                JSONObject response = new JSONObject(paymentDetails).getJSONObject("response");
                                String paymentState = response.getString("state");

                                if ("approved".equalsIgnoreCase(paymentState)) {
                                    Toast.makeText(this, "Payment Successful!", Toast.LENGTH_SHORT).show();

                                    // Mark the job as paid locally and refresh UI
                                    markJobAsPaid();
                                }
                            } catch (JSONException e) {
                                Log.e(TAG, "Payment Confirmation Parsing Failed", e);
                            }
                        }
                    } else if (result.getResultCode() == Activity.RESULT_CANCELED) {
                        Toast.makeText(this, "Payment Canceled", Toast.LENGTH_SHORT).show();
                    } else if (result.getResultCode() == PaymentActivity.RESULT_EXTRAS_INVALID) {
                        Toast.makeText(this, "Invalid Payment Configuration", Toast.LENGTH_SHORT).show();
                    }
                });
    }


    private void fetchJobsAndStatusesForUser() {
        String userId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
        com.google.firebase.database.DatabaseReference jobStatusesRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("jobStatuses");

        jobStatusesRef.orderByChild("employerID").equalTo(userId)
                .addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot statusSnapshot) {
                        jobStatusList.clear();
                        for (com.google.firebase.database.DataSnapshot snapshot : statusSnapshot.getChildren()) {
                            JobStatus jobStatus = snapshot.getValue(JobStatus.class);
                            if (jobStatus != null) {
                                jobStatusList.add(jobStatus);
                            }
                        }
                        fetchJobDetails();
                    }

                    @Override
                    public void onCancelled(@NonNull com.google.firebase.database.DatabaseError databaseError) {
                        Log.e(TAG, "Error fetching job statuses", databaseError.toException());
                    }
                });
    }

    private void fetchJobDetails() {
        com.google.firebase.database.DatabaseReference jobsRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("jobs");

        jobsRef.addListenerForSingleValueEvent(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot jobSnapshot) {
                jobList.clear();
                for (JobStatus jobStatus : jobStatusList) {
                    Job job = jobSnapshot.child(jobStatus.getJobId()).getValue(Job.class);
                    if (job != null) {
                        jobList.add(job);
                    }
                }
                setupRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull com.google.firebase.database.DatabaseError error) {
                Log.e(TAG, "Error fetching job details", error.toException());
            }
        });
    }

    private void setupRecyclerView() {
        paymentListAdapter = new PaymentListAdapter(this, jobList, jobStatusList, new PaymentListAdapter.OnJobActionListener() {
            @Override
            public void onPayClicked(Job job) {
                initiatePayment(job);
            }

            @Override
            public void onMarkCompleteClicked(Job job) {
            }
        });
        paymentRecyclerView.setAdapter(paymentListAdapter);
    }


    private void updateJobStatus(Job job) {
        com.google.firebase.database.DatabaseReference jobStatusRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("jobStatuses").child(job.getJobId());
        jobStatusRef.child("status").setValue("completed")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SubActivityPayment.this, "Job marked as completed!", Toast.LENGTH_SHORT).show();
                    fetchJobsAndStatusesForUser(); // Refresh the list
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SubActivityPayment.this, "Failed to update job status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void initiatePayment(Job job) {
        double amount = job.getSalary();
        PayPalPayment payment = new PayPalPayment(
                new BigDecimal(amount), "CAD", job.getTitle(), PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        activityResultLauncher.launch(intent);
    }

    private void markJobAsPaid() {
        // Update the local adapter to reflect the "Paid" state
        paymentListAdapter.notifyDataSetChanged();
        Intent intent = new Intent(this, SubActivityPayment.class);
        finish();
        startActivity(intent);
    }



    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
