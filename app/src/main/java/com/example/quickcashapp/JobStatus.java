package com.example.quickcashapp;

public class JobStatus extends Job {
    private String status;
    private String employerID;

    // Default constructor required for Firebase
    public JobStatus() {
        super(); // Call the parent (Job) class constructor
    }

    public JobStatus(String jobId, String title, double salary, String duration, String urgency, String description, String location,
                     String status, String employerID) {
        super(jobId, title, salary, duration, urgency, description, location);
        this.status = status;
        this.employerID = employerID;
    }

    // Getters and Setters for new fields
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmployerID() {
        return employerID;
    }

    public void setEmployerID(String employerID) {
        this.employerID = employerID;
    }
}
