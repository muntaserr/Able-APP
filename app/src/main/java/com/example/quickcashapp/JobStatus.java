package com.example.quickcashapp;

public class JobStatus extends Job {
    private String status;
    private String employerID;
    private String employeeID; // New field

    // Default constructor required for Firebase
    public JobStatus() {
        super();
    }

    public JobStatus(String jobId, String title, double salary, String duration, String urgency, String description, String location,
                     String status, String employerID, String employeeID) {
        super(jobId, title, salary, duration, urgency, description, location);
        this.status = status;
        this.employerID = employerID;
        this.employeeID = employeeID;
    }

    // Getters and Setters
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

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
}
