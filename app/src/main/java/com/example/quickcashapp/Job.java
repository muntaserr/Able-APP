package com.example.quickcashapp;

public class Job {
    private String jobId;
    private String title;
    private String salary;
    private String duration;
    private String urgency;
    private String location;
    private String status;
    private String employerID;
    private String employeeID;



    private String description;

    // Default constructor required for Firebase
    public Job() {
    }

    public Job(String jobId, String title, String salary, String duration, String urgency,String description, String location, String status, String employerID, String employeeID) {
        this.jobId = jobId;
        this.title = title;
        this.salary = salary;
        this.duration = duration;
        this.urgency = urgency;
        this.location = location;
        this.description = description;
        this.status = status;
        this.employerID = employerID;
        this.employeeID = employeeID;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
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

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public String getEmployeeID() {
        return employeeID;
    }
}
