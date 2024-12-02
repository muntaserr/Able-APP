package com.example.quickcashapp.employeeDashboard;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashapp.Job;
import com.example.quickcashapp.JobListAdapter;
import com.example.quickcashapp.JobListing;
import com.example.quickcashapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchJobsActivity extends AppCompatActivity {
    private EditText jobTitleEditText, minSalaryEditText, maxSalaryEditText;
    private Spinner durationSpinner;
    private SeekBar vicinitySeekBar;
    private TextView vicinityLabel, noResultsMessage;
    private Button searchButton;
    private Button add2PreferenceButton;
    private RecyclerView resultsRecyclerView;
    private DatabaseReference jobsDatabaseReference;
    private boolean clicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_jobs);

        initializeUI();
        setupDurationSpinner();
        setupVicinitySeekBar();
        setupRecyclerView();
        jobsDatabaseReference = FirebaseDatabase.getInstance().getReference("jobs");

        searchButton.setOnClickListener(v -> performSearch());
    }

    /**
     * This Initializes the UI for the SearchJobs in the Employee Dashboard
     */
    private void initializeUI() {
        jobTitleEditText = findViewById(R.id.jobTitleEditText);
        minSalaryEditText = findViewById(R.id.minSalaryEditText);
        maxSalaryEditText = findViewById(R.id.maxSalaryEditText);
        durationSpinner = findViewById(R.id.durationSpinner);
        vicinitySeekBar = findViewById(R.id.vicinitySeekBar);
        vicinityLabel = findViewById(R.id.vicinityLabel);
        searchButton = findViewById(R.id.searchButton);
        resultsRecyclerView = findViewById(R.id.resultsRecyclerView);
        noResultsMessage = findViewById(R.id.noResultsMessage);
        add2PreferenceButton = findViewById(R.id.add2PreferenceButton);
    }

    /**
     * Sets up the Duration Spinner for the search jobs UI. (Duration Spinner is where the employee selects how long the job takes)
     */
    private void setupDurationSpinner() {
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.job_duration_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        durationSpinner.setAdapter(adapter);
    }

    private void setupVicinitySeekBar() {
        vicinitySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                vicinityLabel.setText("Vicinity: " + progress + " km");
            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    /**
     * Sets up the results Recycler view
     */
    private void setupRecyclerView() {
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsRecyclerView.setAdapter(new JobListAdapter(new ArrayList<>(), new JobListAdapter.OnJobActionListener() {
            @Override
            public void onAcceptJobClicked(JobListing job) {
                acceptJob(job);
            }
        }));

    }

    /**
     * Logic for actually performing a search.
     * Gets the user input and checks a list where the jobs are stored to get the stored job.
     * Passes the list to the update UI with results.
     */
    private void performSearch() {
        String jobTitle = jobTitleEditText.getText().toString();
        Double minSalary,maxSalary;
        //Add default salary search options if left blank
        if(TextUtils.isEmpty(minSalaryEditText.getText().toString()) ) {
            minSalary = 0.0;
        }else {
            minSalary = Double.parseDouble(minSalaryEditText.getText().toString());
        }
        if(TextUtils.isEmpty(maxSalaryEditText.getText().toString()) ) {
            maxSalary =Double.MAX_VALUE;
        }else{
            maxSalary = Double.parseDouble(maxSalaryEditText.getText().toString());
        }

        String duration = durationSpinner.getSelectedItem().toString();
        int vicinity = vicinitySeekBar.getProgress();

        List<JobListing> results = searchJobs(jobTitle, minSalary, maxSalary, duration, vicinity);
        updateUIWithResults(results);
    }

    /**
     * Updates the UI for the user to the see the jobs that fit his description
     * @param results takes a list of jobs and then shows the list in a display that the user can see.
     */
    private void updateUIWithResults(List<JobListing> results) {
        if (results.isEmpty()) {
            resultsRecyclerView.setVisibility(View.GONE);
            noResultsMessage.setVisibility(View.VISIBLE);
        } else {
            resultsRecyclerView.setVisibility(View.VISIBLE);
            noResultsMessage.setVisibility(View.GONE);

            resultsRecyclerView.setAdapter(new JobListAdapter(results, new JobListAdapter.OnJobActionListener() {

                @Override
                public void onAcceptJobClicked(JobListing job) {
                    acceptJob(job); // Handle job acceptance here
                }
            }));


        }
    }


    private void acceptJob(JobListing job) {
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




    /**
     * Searches and filters job listings based on the given criteria.
     *
     * @param title     the job title or keywords to filter by
     * @param minSal    the minimum salary range as a string
     * @param maxSal    the maximum salary range as a string
     * @param duration  the job duration (e.g., full-time, part-time) to filter by
     * @param vicinity  the radius or vicinity to search within, in kilometers
     * @return          a list of filtered job listings matching the criteria
     */
    private List<JobListing> searchJobs(String title, Double minSal, Double maxSal, String duration, int vicinity) {

        List<JobListing> matchingJobs = new ArrayList<>();
        // Add filtering logic here if needed
        // Fetch all jobs from Firebase
        jobsDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                    Job job = jobSnapshot.getValue(Job.class);

                    // Ensure the job object is not null and matches the criteria
                    if (job != null && isMatching(job, title, minSal, maxSal, duration, vicinity)) {
                        Log.e("Lucas Test", "Found a job that matches adding it now");
                        // Convert Job to JobListing
                        JobListing jobListing = new JobListing(
                                job.getJobId(),
                                job.getTitle(),
                                job.getSalary(),
                                job.getDuration(),
                                job.getUrgency(),
                                job.getLocation(),
                                job.getDescription()
                        );
                        matchingJobs.add(jobListing);
                    }
                }

                // Handle the search results (e.g., update UI or pass to another method)
                updateUIWithResults(matchingJobs);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle Firebase read error
                System.err.println("Error reading jobs from Firebase: " + databaseError.getMessage());
            }
        });

        return matchingJobs;
    }
    private boolean isMatching(Job job, String title, Double minSal, Double maxSal, String duration, int vicinity) {
        // Title matching (case-insensitive partial match)
        if (title != null && !title.isEmpty() && !job.getTitle().toLowerCase().contains(title.toLowerCase())) {
            return false;
        }

        // Salary matching
        try {
            double jobSalary = job.getSalary();
            double minSalary = (minSal != null ) ? minSal : 0;
            double maxSalary = (maxSal != null ) ? maxSal : Double.MAX_VALUE;

            if (jobSalary < minSalary || jobSalary > maxSalary) {
                return false;
            }
        } catch (NumberFormatException e) {
            // Ignore salary filter if parsing fails
        }



        // Vicinity matching (future implementation for geolocation filtering)

        return true;
    }

}
