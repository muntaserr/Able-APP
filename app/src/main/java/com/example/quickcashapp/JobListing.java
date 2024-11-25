package com.example.quickcashapp;

/**
 * Job listing class for getting all the information needed in a job listing.
 * Also jobListing object.
 */
public class JobListing {
    private String jobId;
    private String title;
    private Double salary; // Combine minSalary and maxSalary as a single string to match the Job class
    private String duration;
    private String urgency;
    private String location;
    private String description;

    public JobListing(String jobId, String title, Double salary, String duration, String urgency, String location, String description) {
        this.jobId = jobId;
        this.title = title;
        this.salary = salary;
        this.duration = duration;
        this.urgency = urgency;
        this.location = location;
        this.description = description;
    }

    // Getters for JobListing
    public String getJobId() {
        return jobId;
    }

    public String getTitle() {
        return title;
    }

    public Double getSalary() {
        return salary;
    }

    public String getDuration() {
        return duration;
    }

    public String getUrgency() {
        return urgency;
    }

    public String getLocation() {
        return location;
    }

    public String getDescription() {
        return description;
    }


}
