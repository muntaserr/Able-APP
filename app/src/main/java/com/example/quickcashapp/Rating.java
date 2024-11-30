package com.example.quickcashapp;

public class Rating {
    private String jobId;
    private String employeeID;
    private float rating;

    public Rating() {
    }

    public Rating(String jobId, String employeeID, float rating) {
        this.jobId = jobId;
        this.employeeID = employeeID;
        this.rating = rating;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String employeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }
}
