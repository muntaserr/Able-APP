package com.example.quickcashapp;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;

import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class espressoLoginTest {
    @Test
    public void testLoginSuccess() {
        // Launch the login activity
        ActivityScenario.launch(LoginActivity.class);

        // Enter valid email and password
        onView(withId(R.id.email)).perform(typeText("test@example.com"));
        onView(withId(R.id.password)).perform(typeText("password123"));

        // Close the soft keyboard to avoid it blocking the login button
        onView(withId(R.id.login_button)).perform(click());

        // Assuming login success redirects to a dashboard activity, check if the dashboard is displayed
        // For example, if the dashboard shows a welcome message, you can check it
        onView(withText("Welcome to your dashboard")).check(matches(isDisplayed()));
    }

}
