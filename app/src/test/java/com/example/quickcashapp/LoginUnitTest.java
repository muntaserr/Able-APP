package com.example.quickcashapp;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.junit.Before;
import org.junit.Test;

public class LoginUnitTest {
    private FirebaseAuth mAuth;

    @Before
    public void setUp() {
        // Initialize FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
    }

    @Test
    public void testLoginSuccess() {
        // This assumes you have a test user set up in your Firebase Authentication
        String testEmail = "test@example.com";
        String testPassword = "password";

        // Attempt to log in
        mAuth.signInWithEmailAndPassword(testEmail, testPassword)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Check to make sure the user is successfully logged in
                        assertNotNull(user);
                    } else {
                        // If login fails, ensure no user is set
                        assertNull(mAuth.getCurrentUser());
                    }
                });
    }

    @Test
    public void testLoginFailure() {
        // Attempt to log in with incorrect credentials
        String wrongEmail = "wrong";
        String wrongPassword = "fy.f";

        mAuth.signInWithEmailAndPassword(wrongEmail, wrongPassword)
                .addOnCompleteListener(task -> {
                    // Check to make sure the login failed
                    assertNull(mAuth.getCurrentUser());
                });
    }
}
