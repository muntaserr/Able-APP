package com.example.quickcashapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.rule.ActivityTestRule;

import org.junit.Rule;
import org.junit.Test;

public class MapsActivityEspressoTest {


    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testRequestLocationPermissionClickNo(){

        onView(withText("Location Permission")).check(matches(isDisplayed()));
        onView(withText("This app needs location permission to better help you find relevant jobs near you"))
                .check(matches(isDisplayed()));
        onView(withText("OK")).check(matches(isDisplayed()));
        onView(withText("NO")).check(matches(isDisplayed()));

        onView(withText("NO")).perform(click());

        //Implement logic for "No"
    }

    @Test
    public void testRequestLocationPermissionClickYes(){

        onView(withText("Location Permission")).check(matches(isDisplayed()));
        onView(withText("This app needs location permission to better help you find relevant jobs near you"))
                .check(matches(isDisplayed()));
        onView(withText("OK")).check(matches(isDisplayed()));
        onView(withText("NO")).check(matches(isDisplayed()));

        onView(withText("OK")).perform(click());

        //Request the user location
        isLocationPermissionGranted();



    }

    private void isLocationPermissionGranted(){
        //Coarse location access

    }
}
