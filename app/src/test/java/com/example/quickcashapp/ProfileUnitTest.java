package com.example.quickcashapp;

import static org.junit.Assert.assertNull;

import com.google.firebase.auth.FirebaseAuth;
import org.junit.Before;
import org.junit.Test;

/**
 * Unit tests for the logout portion
 */
public class ProfileUnitTest {

    FirebaseAuth mAuth;

    @Before
    public void setUp() {
        //Mocking a fake user login
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword("test@example.com", "password");
    }

    @Test
    public void testLogout(){
        mAuth.signOut();

        //Check to make sure there is no current user
        assertNull(mAuth.getCurrentUser());
    }
}
