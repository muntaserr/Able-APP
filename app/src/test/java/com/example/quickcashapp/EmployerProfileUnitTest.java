package com.example.quickcashapp;

import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.*;

import com.google.firebase.auth.FirebaseAuth;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


/**
 * Unit tests for the logout portion
 */
public class EmployerProfileUnitTest {

    @Mock
    FirebaseAuth mockAuth;

    @Before
    public void setUp() {

        MockitoAnnotations.initMocks(this);
        //Mock Firebase instance
        mockAuth = Mockito.mock(FirebaseAuth.class);
    }

    @Test
    public void testLogout(){

        //Act like there is no user signed in by returning null from getCurrentUser
        when(mockAuth.getCurrentUser()).thenReturn(null);

        //Use signOut and make sure it works
        mockAuth.signOut();
        verify(mockAuth).signOut();
        //Check to make sure there is no current user
        assertNull(mockAuth.getCurrentUser());
    }
}
