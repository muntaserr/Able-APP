package com.example.quickcashapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcashapp.employeeDashboard.ViewPreferredJobsActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ViewPreferredJobsActivityTest {

    @Rule
    public ActivityScenarioRule<ViewPreferredJobsActivity> activityRule =
            new ActivityScenarioRule<>(ViewPreferredJobsActivity.class);

    @Test
    public void testLoadJobTitles() {
    }
}
