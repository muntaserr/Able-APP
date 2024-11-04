package com.example.quickcashapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.rule.ActivityTestRule;

import com.example.quickcashapp.MainActivity;
import com.example.quickcashapp.Maps.LocationHelper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

public class LocationHelperEspressoTest {

    private LocationHelper locationHelper;

    @Rule
    public ActivityTestRule<MainActivity> activityTestRule = new ActivityTestRule<>(MainActivity.class);


    @Before
    public void setUp() {
        locationHelper = new LocationHelper(activityTestRule.getActivity());
    }

    @Test
    public void testRequestLocationPermissionIsDisplayed(){

        activityTestRule.getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                locationHelper.askForPermissions();
            }
        });

        onView(withText("Location Permission Needed")).check(matches(isDisplayed()));
        onView(withText("this app need location permission to work correctly")).check(matches(isDisplayed()));

        onView(withText("Ok")).check(matches(isDisplayed()));
        onView(withText("Cancel")).check(matches(isDisplayed()));

    }
}
