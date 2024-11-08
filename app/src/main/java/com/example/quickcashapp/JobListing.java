package com.example.quickcashapp;

public class JobListing {
    private String jobTitle;
    private int minSalary;
    private int maxSalary;
    private String duration;
    private int vicinity;

    public JobListing(String jobTitle, int minSalary, int maxSalary, String duration, int vicinity) {
        this.jobTitle = jobTitle;
        this.minSalary = minSalary;
        this.maxSalary = maxSalary;
        this.duration = duration;
        this.vicinity = vicinity;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public int getMinSalary() {
        return minSalary;
    }

    public int getMaxSalary() {
        return maxSalary;
    }

    public String getDuration() {
        return duration;
    }

    public int getVicinity() {
        return vicinity;
    }
}
