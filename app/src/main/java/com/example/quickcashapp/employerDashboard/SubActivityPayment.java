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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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

    private static final String TAG = PaymentActivity.class.getName();
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

    /**
     * Configure PayPal settings.
     */
    private void configurePayPal() {
        payPalConfig = new PayPalConfiguration()
                .environment(PayPalConfiguration.ENVIRONMENT_SANDBOX) // Use Sandbox for testing
                .clientId(BuildConfig.PAYPAL_CLIENT_ID); // Client ID from BuildConfig
    }

    /**
     * Start PayPal service.
     */
    private void startPayPalService() {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, payPalConfig);
        startService(intent);
    }

    /**
     * Initialize Activity Launcher for PayPal payments.
     */
    private void initializeActivityLauncher() {
        activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        PaymentConfirmation confirmation = result.getData().getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
                        if (confirmation != null) {
                            try {
                                String paymentDetails = confirmation.toJSONObject().toString(4);
                                Log.i(TAG, paymentDetails);

                                // Handle payment success
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

    /**
     * Fetch jobs for the current user.
     */
    private void fetchJobsForUser() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");

        jobsRef.orderByChild("employerId").equalTo(userId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        jobList.clear();

                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Job job = snapshot.getValue(Job.class);
                            if (job != null && "in-progress".equals(job.getStatus())) { // Show only in-progress jobs
                                jobList.add(job);
                            }
                        }

                        // Set up adapter with the ability to change status and pay
                        paymentListAdapter = new PaymentListAdapter(jobList, job -> {
                            // Change status to "completed"
                            updateJobStatus(job);
                        });
                        paymentRecyclerView.setAdapter(paymentListAdapter);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.e("Firebase", "Error fetching jobs", databaseError.toException());
                    }
                });
    }

    /**
     * Update job status to "completed".
     */
    private void updateJobStatus(Job job) {
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobs").child(job.getJobId());
        jobRef.child("status").setValue("completed")
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(this, "Job marked as completed!", Toast.LENGTH_SHORT).show();

                    // Fetch employee name to display
                    fetchEmployeeName(job.getEmployeeID());

                    // Allow payment once status is updated
                    initiatePayment(job);
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to update job status: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    /**
     * Fetch employee name from Firebase.
     */
    private void fetchEmployeeName(String employeeId) {
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users").child(employeeId);
        usersRef.child("name").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String employeeName = snapshot.getValue(String.class);
                if (employeeName != null) {
                    Toast.makeText(SubActivityPayment.this, "Employee: " + employeeName, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SubActivityPayment.this, "Employee name not found.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(SubActivityPayment.this, "Error fetching employee name: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Initiate PayPal payment for the selected job.
     */
    private void initiatePayment(Job job) {
        String amount = String.valueOf(job.getSalary());
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
