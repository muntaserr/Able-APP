package com.example.quickcashapp.employeeDashboard;

public class PreferenceInfo {
    private String jobTitle;

    public PreferenceInfo(){

    }


    public PreferenceInfo(String jobTitle){
        this.jobTitle = jobTitle;
    }


    public String getJobTitle(){
        return jobTitle;
    }
}
