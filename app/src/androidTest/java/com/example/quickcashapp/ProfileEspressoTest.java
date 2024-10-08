package com.example.quickcashapp;


import androidx.test.ext.junit.runners.AndroidJUnit4;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;



@RunWith(AndroidJUnit4.class)
public class ProfileEspressoTest {



    @Test
    public void testLogoutButtonChangesActivity(){
        //Click logout button
        onView(withId(R.id.logout_button)).perform(click());

        //Make sure activity switches to login activity
        //Insert to make sure the activity is switched to


    }
}
