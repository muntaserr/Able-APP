package com.example.quickcashapp.employeeDashboard;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.quickcashapp.PreferredJob;

import org.junit.Before;
import org.junit.Test;

public class StorePreferredJobUITest {


    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackageName = "com.example.quickcashapp";
    private UiDevice device;
    private PreferredJob preferredJob;

    @Before
    public void setup() throws UiObjectNotFoundException {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackageName);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);

        UiObject emailBox = device.findObject(new UiSelector().text("Email"));
        emailBox.setText("test@example.com");
        UiObject passwordBox = device.findObject(new UiSelector().text("Password"));
        passwordBox.setText("Password123");
        UiObject loginButton = device.findObject(new UiSelector().text("Login"));
        loginButton.clickAndWaitForNewWindow();
    }

    @Test
    public void IsLandingPageValid() throws UiObjectNotFoundException {
        UiObject searchJobsButton = device.findObject(new UiSelector().text("SEARCH JOBS"));
        searchJobsButton.clickAndWaitForNewWindow();
        UiObject jobTitleBox = device.findObject(new UiSelector().text("Job Title"));
        assertTrue(jobTitleBox.exists());
        UiObject minSalaryBox = device.findObject(new UiSelector().text("Min Salary"));
        assertTrue(minSalaryBox.exists());
        UiObject maxSalaryBox = device.findObject(new UiSelector().text("Max Salary"));
        assertTrue(maxSalaryBox.exists());
        UiObject searchButton = device.findObject(new UiSelector().text("Search"));
        assertTrue(searchButton.exists());
    }

    @Test
    public void StoringJobSuccessful() throws UiObjectNotFoundException, InterruptedException {
        UiObject searchJobsButton = device.findObject(new UiSelector().text("SEARCH JOBS"));
        searchJobsButton.clickAndWaitForNewWindow();
        UiObject jobTitleBox = device.findObject(new UiSelector().text("Job Title"));
        assertTrue(jobTitleBox.exists());
        UiObject minSalaryBox = device.findObject(new UiSelector().text("Min Salary"));
        minSalaryBox.setText("0");
        UiObject maxSalaryBox = device.findObject(new UiSelector().text("Max Salary"));
        maxSalaryBox.setText("1000");
        UiObject searchButton = device.findObject(new UiSelector().text("Search"));
        searchButton.click();

        UiObject add2PreferenceButton = device.findObject(new UiSelector().text("Add to Preference"));
        assertTrue(add2PreferenceButton.exists());
        add2PreferenceButton.click();

        UiObject messageBox = device.findObject(new UiSelector().text("Add to Preference?"));
        assertTrue(messageBox.exists());
        UiObject checkMessage = device.findObject(new UiSelector().text("Yes"));
        checkMessage.click();
    }

}
