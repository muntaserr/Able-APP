
package com.example.quickcashapp;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import com.example.quickcashapp.employeeDashboard.SearchJobsActivity;

@RunWith(AndroidJUnit4.class)
public class SearchJobsEspressoTest {

    @Rule
    public ActivityScenarioRule<SearchJobsActivity> activityRule =
            new ActivityScenarioRule<>(SearchJobsActivity.class);

    @Test
    public void testSearchButtonDisplayed() {
        onView(withId(R.id.searchButton)).check(matches(isDisplayed()));
    }

    @Test
    public void testSearchFunctionality() {
        onView(withId(R.id.searchButton)).perform(click());
        onView(withId(R.id.resultsRecyclerView)).check(matches(isDisplayed()));
    }
}
