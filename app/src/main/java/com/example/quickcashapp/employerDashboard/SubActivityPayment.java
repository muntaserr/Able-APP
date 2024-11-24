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

        fetchJobsForUser();
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
                                String paymentId = response.getString("id");
                                String paymentState = response.getString("state");

                                Toast.makeText(this, "Payment Successful: " + paymentState, Toast.LENGTH_LONG).show();
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

    private void fetchJobsForUser() {
        // Fetch jobs from Firebase, only "in-progress" jobs
        String userId = com.google.firebase.auth.FirebaseAuth.getInstance().getCurrentUser().getUid();
        com.google.firebase.database.DatabaseReference jobsRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("jobs");

        jobsRef.orderByChild("employerID").equalTo(userId)
                .addValueEventListener(new com.google.firebase.database.ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                        jobList.clear();
                        for (com.google.firebase.database.DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Job job = snapshot.getValue(Job.class);
                            if (job != null) {
                                jobList.add(job);
                            }
                        }
                        setupRecyclerView();
                    }

                    @Override
                    public void onCancelled(@NonNull com.google.firebase.database.DatabaseError databaseError) {
                        Log.e(TAG, "Error fetching jobs", databaseError.toException());
                    }
                });
    }

    private void setupRecyclerView() {
        paymentListAdapter = new PaymentListAdapter(jobList, new PaymentListAdapter.OnJobActionListener() {
            @Override
            public void onPayClicked(Job job) {
                initiatePayment(job); // Payment action
            }

            @Override
            public void onMarkCompleteClicked(Job job) {
                updateJobStatus(job); // Mark as completed action
            }
        });
        paymentRecyclerView.setAdapter(paymentListAdapter);

    }

    private void updateJobStatus(Job job) {
        com.google.firebase.database.DatabaseReference jobRef = com.google.firebase.database.FirebaseDatabase.getInstance().getReference("jobs").child(job.getJobId());
        jobRef.child("status").setValue("completed")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(SubActivityPayment.this, "Job marked as completed!", Toast.LENGTH_SHORT).show();
                    job.setStatus("completed");
                    paymentListAdapter.notifyDataSetChanged();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(SubActivityPayment.this, "Failed to update job status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void initiatePayment(Job job) {
        if (!"completed".equals(job.getStatus())) {
            Toast.makeText(this, "Please mark the job as completed before paying.", Toast.LENGTH_SHORT).show();
            return;
        }

        // Use job's salary for payment
        String amount = job.getSalary();
        PayPalPayment payment = new PayPalPayment(
                new BigDecimal(amount), "CAD", job.getTitle(), PayPalPayment.PAYMENT_INTENT_SALE);

        Intent intent = new Intent(this, PaymentActivity.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
        activityResultLauncher.launch(intent);
    }


    @Override
    public void onDestroy() {
        stopService(new Intent(this, PayPalService.class));
        super.onDestroy();
    }
}
