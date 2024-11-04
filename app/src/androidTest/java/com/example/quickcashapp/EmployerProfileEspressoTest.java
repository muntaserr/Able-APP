package com.example.quickcashapp;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;
import androidx.test.espresso.intent.Intents;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import com.example.quickcashapp.employerDashboard.SubActivityProfile;

@RunWith(AndroidJUnit4.class)
public class EmployerProfileEspressoTest {

    @Rule
    public ActivityTestRule<SubActivityProfile> activityTestRule = new ActivityTestRule<>(SubActivityProfile.class);

    @Before
    public void setUp() {
        // Initialize Espresso Intents before the test
        Intents.init();
    }


    @Test
    public void testLogoutButtonChangesActivity(){
        //Click logout button
        onView(withId(R.id.logout_button)).perform(click());

        onView(withText("Logout")).check(matches(isDisplayed()));
        onView(withText("Are you sure you want to logout?")).check(matches(isDisplayed()));
        onView(withText("Yes")).perform(click());

        intended(hasComponent(LoginActivity.class.getName()));

    }

    @After
    public void tearDown(){
        Intents.release();
    }
}
