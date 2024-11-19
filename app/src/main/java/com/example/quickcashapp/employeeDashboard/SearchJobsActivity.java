package com.example.quickcashapp.employeeDashboard;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.quickcashapp.JobListAdapter;
import com.example.quickcashapp.JobListing;
import com.example.quickcashapp.R;

import java.util.ArrayList;
import java.util.List;

public class SearchJobsActivity extends AppCompatActivity {
    private EditText jobTitleEditText, minSalaryEditText, maxSalaryEditText;
    private Spinner durationSpinner;
    private SeekBar vicinitySeekBar;
    private TextView vicinityLabel, noResultsMessage;
    private Button searchButton;
    private RecyclerView resultsRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_jobs);

        initializeUI();
        setupDurationSpinner();
        setupVicinitySeekBar();
        setupRecyclerView();

        searchButton.setOnClickListener(v -> performSearch());
    }

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
    }

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

    private void setupRecyclerView() {
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        resultsRecyclerView.setAdapter(new JobListAdapter(new ArrayList<>()));  // Placeholder empty list
    }

    private void performSearch() {
        String jobTitle = jobTitleEditText.getText().toString();
        String minSalary = minSalaryEditText.getText().toString();
        String maxSalary = maxSalaryEditText.getText().toString();
        String duration = durationSpinner.getSelectedItem().toString();
        int vicinity = vicinitySeekBar.getProgress();

        List<JobListing> results = searchJobs(jobTitle, minSalary, maxSalary, duration, vicinity);
        updateUIWithResults(results);
    }

    private void updateUIWithResults(List<JobListing> results) {
        if (results.isEmpty()) {
            resultsRecyclerView.setVisibility(View.GONE);
            noResultsMessage.setVisibility(View.VISIBLE);
        } else {
            resultsRecyclerView.setVisibility(View.VISIBLE);
            noResultsMessage.setVisibility(View.GONE);
            resultsRecyclerView.setAdapter(new JobListAdapter(results));
        }
    }

    private List<JobListing> searchJobs(String title, String minSal, String maxSal, String duration, int vicinity) {
        List<JobListing> filteredJobs = new ArrayList<>();
        // Add filtering logic here if needed
        return filteredJobs;
    }
}
