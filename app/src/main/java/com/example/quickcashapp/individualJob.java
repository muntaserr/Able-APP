package com.example.quickcashapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quickcashapp.Firebase.FirebaseCRUD;
import com.example.quickcashapp.Firebase.JobCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class individualJob extends AppCompatActivity {
    private String jobID;
    FirebaseCRUD database;
    TextView jobTitleTextView;
    TextView salaryTextView;
    TextView durationTextView;
    TextView urgencyTextView;
    TextView descriptionTextView;
    TextView locationTextView;
    Button acceptJobButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_individual_job);
        database = new FirebaseCRUD();
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            this.jobID = (String) extras.get("jobID");
        }else{
            try {
                throw new Exception("Job Id not passed with intent");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        initializeUI();
        getAndSetJobData();

    }

    private void initializeUI() {
        jobTitleTextView = findViewById(R.id.jobTitleTextView);
        salaryTextView = findViewById(R.id.salaryTextView);
        durationTextView = findViewById(R.id.durationTextView);
        urgencyTextView = findViewById(R.id.urgencyTextView);
        locationTextView = findViewById(R.id.locationTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        acceptJobButton = findViewById(R.id.acceptJobButton);
    }

    private void getAndSetJobData(){
        database.getJob(this.jobID, new JobCallback() {
            @Override
            public void onJobRetrieved(Job job) {
                jobTitleTextView.setText(job.getTitle());
                salaryTextView.setText("Salary: " + job.getSalary());
                durationTextView.setText("Duration: " + job.getDuration());
                urgencyTextView.setText("Urgency: " + job.getUrgency());
                locationTextView.setText("Location: " + job.getLocation());
                descriptionTextView.setText("Job Description: "+job.getDescription());
                acceptJobButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        acceptJob(job);
                    }
                });
            }
        });


    }

    private void acceptJob(Job job) {
        String employeeID = FirebaseAuth.getInstance().getCurrentUser().getUid();

        // Reference to the job in the database
        DatabaseReference jobRef = FirebaseDatabase.getInstance().getReference("jobStatuses").child(job.getJobId());

        // Update employeeID and status fields in a single operation
        jobRef.child("employeeID").setValue(employeeID)
                .addOnSuccessListener(aVoid -> {
                    // Once employeeID is successfully updated, update the job status
                    jobRef.child("status").setValue("in-progress")
                            .addOnSuccessListener(innerVoid -> {
                                Toast.makeText(this, "Job accepted successfully and status updated to in-progress!", Toast.LENGTH_SHORT).show();
                            })
                            .addOnFailureListener(innerError -> {
                                Toast.makeText(this, "Failed to update job status: " + innerError.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(this, "Failed to accept job: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }
}
