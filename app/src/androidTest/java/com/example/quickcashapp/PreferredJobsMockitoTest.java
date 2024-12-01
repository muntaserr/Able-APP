package com.example.quickcashapp;

import static org.junit.Assert.assertTrue;

import com.example.quickcashapp.employeeDashboard.SearchJobsActivity;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class PreferredJobsMockitoTest {
    static SearchJobsActivity searchJobsActivity;

    @BeforeClass
    public static void setup(){
        searchJobsActivity = Mockito.mock(SearchJobsActivity.class);
    }

    @Test
    public void AddPrefferedJobs() {
        SetPreferenceActivity setPreferenceActivity = new SetPreferenceActivity();
        assertTrue(searchJobsActivity.AddtoPreference());

    }


}
