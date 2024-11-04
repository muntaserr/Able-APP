package com.example.quickcashapp;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.quickcashapp.employeeDashboard.RegisterActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class RegisterEspressoTest {

    @Before
    public void setUp() {
        ActivityScenario.launch(RegisterActivity.class);
    }

    @Test
    public void testSuccessfulRegistration() throws InterruptedException {
        onView(withId(R.id.name)).perform(typeText("iterationOne"));
        onView(withId(R.id.email)).perform(typeText("iterationOne@gmail.com"));//must create new email for the test to work.
        onView(withId(R.id.password)).perform(typeText("password1234S"));
        onView(withId(R.id.credit_card)).perform(typeText("4111111111111111"));
        onView(withId(R.id.role_radio_group)).perform(click());
        onView(withId(R.id.register_button)).perform(click());
        Thread.sleep(2000);// test closes whilst program is sending a GET request
        onView(withId(R.id.statusMessage)).check(matches(withText("Registration successful")));
    }

    @Test
    public void testEmptyFields() {
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.statusMessage)).check(matches(withText("Please fill in all fields")));
    }

    @Test
    public void testNoRoleSelected() {
        onView(withId(R.id.name)).perform(typeText("Johntest111111"));
        onView(withId(R.id.email)).perform(typeText("Johntest111111!@gmail.com"));
        onView(withId(R.id.password)).perform(typeText("password1234S"));
        onView(withId(R.id.credit_card)).perform(typeText("4111111111111111"));
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.statusMessage)).check(matches(withText("Please select a role")));
    }

    @Test
    public void testInvalidEmail() {
        onView(withId(R.id.name)).perform(typeText("Johntest111111"));
        onView(withId(R.id.email)).perform(typeText("invalidemail"));
        onView(withId(R.id.password)).perform(typeText("password1234S"));
        onView(withId(R.id.credit_card)).perform(typeText("4111111111111111"));
        onView(withId(R.id.role_radio_group)).perform(click());
        onView(withId(R.id.register_button)).perform(click());
        onView(withId(R.id.statusMessage)).check(matches(withText("Invalid email address")));
    }

}
