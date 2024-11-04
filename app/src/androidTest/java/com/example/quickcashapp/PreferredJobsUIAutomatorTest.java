package com.example.quickcashapp;

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

import org.junit.Before;
import org.junit.Test;

public class PreferredJobsUIAutomatorTest {


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
    public void AddPreferredJobs() throws UiObjectNotFoundException {
        UiObject EmployeeTitle = device.findObject(new UiSelector().text("Employee"));
        assertTrue(EmployeeTitle.exists());
        UiObject JobSearchButton = device.findObject(new UiSelector().text("Job Search"));
        JobSearchButton.clickAndWaitForNewWindow();
        UiObject AddtoPreferredJob = device.findObject(new UiSelector().text("Add to Preferred Job"));
        assertTrue(AddtoPreferredJob.exists());
    }

    @Test
    public void ManagePreferredJobs() throws UiObjectNotFoundException{
        UiObject EmployeeTitle = device.findObject(new UiSelector().text("Employee"));
        assertTrue(EmployeeTitle.exists());
        UiObject PreferredJobButton = device.findObject(new UiSelector().text("Preferred Job"));
        PreferredJobButton.clickAndWaitForNewWindow();
        UiObject PreferredJobTitle = device.findObject(new UiSelector().text(preferredJob.getJobTitle()));
        assertTrue(PreferredJobTitle.exists());
        UiObject PreferredJobSalary = device.findObject(new UiSelector().text(preferredJob.getJobSalary()));
        assertTrue(PreferredJobSalary.exists());
        PreferredJobTitle.clickAndWaitForNewWindow();
        UiObject PreferredJobsDetail = device.findObject(new UiSelector().text("Details"));
        assertTrue(PreferredJobTitle.exists());
        assertTrue(PreferredJobsDetail.exists());
    }

}
