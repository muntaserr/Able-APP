package com.example.quickcashapp;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;
import androidx.test.uiautomator.UiObjectNotFoundException;


import org.junit.Before;
import org.junit.Test;

public class UIAutomatorTest {

    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackageName = "com.example.quickcashapp";
    private UiDevice device;

    @Before
    public void setup() {
        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent launcherIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackageName);
        launcherIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(launcherIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackageName).depth(0)), LAUNCH_TIMEOUT);    }

    @Test
    public void checkIfLandingPageIsVisible() throws UiObjectNotFoundException {
        //enter email and password
        UiObject emailBox = device.findObject(new UiSelector().text("Email"));
        emailBox.setText("test@example.com");
        UiObject passwordBox = device.findObject(new UiSelector().text("Password"));
        passwordBox.setText("Password123");
        UiObject loginButton = device.findObject(new UiSelector().text("Login"));
        loginButton.clickAndWaitForNewWindow();


        //find employer and employee button
        UiObject EmployerButton = device.findObject(new UiSelector().text("Employer"));
        assertTrue(EmployerButton.exists());
        UiObject EmployeeButton = device.findObject(new UiSelector().text("Employee"));
        assertTrue(EmployeeButton.exists());
    }

    @Test
    public void checkIfSelectingRoleIsAvailable() throws UiObjectNotFoundException {
        //enter email and password
        UiObject emailBox = device.findObject(new UiSelector().text("Email"));
        emailBox.setText("test@example.com");
        UiObject passwordBox = device.findObject(new UiSelector().text("Password"));
        passwordBox.setText("Password123");
        UiObject loginButton = device.findObject(new UiSelector().text("Login"));
        loginButton.clickAndWaitForNewWindow();

        //Press employer button
        UiObject employerButton = device.findObject(new UiSelector().text("Employer"));
        employerButton.clickAndWaitForNewWindow();

        //check employer dashboard exists
        UiObject dashboardMain = device.findObject(new UiSelector().text("EMPLOYER DASHBOARD"));
        assertTrue(dashboardMain.exists());
    }

}
