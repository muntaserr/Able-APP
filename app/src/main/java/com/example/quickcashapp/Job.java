package com.example.quickcashapp;

public class Job {
    private String jobId;
    private String title;
    private Double salary;
    private String duration;
    private String urgency;
    private String location;
    private String description;

    // Default constructor required for Firebase
    public Job() {
    }

    public Job(String jobId, String title, Double salary, String duration, String urgency,String description, String location) {
        this.jobId = jobId;
        this.title = title;
        this.salary = salary;
        this.duration = duration;
        this.urgency = urgency;
        this.location = location;
        this.description = description;
    }

    // Getters and Setters
    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getUrgency() {
        return urgency;
    }

    public void setUrgency(String urgency) {
        this.urgency = urgency;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {return description;}

    public void setDescription(String description) {this.description = description;}
}
