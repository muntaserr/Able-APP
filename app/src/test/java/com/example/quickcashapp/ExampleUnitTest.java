package com.example.quickcashapp;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    LoginValidator validator;
    @Before
    public void setup(){
        validator = new LoginValidator();
    }
    
    @Test
    public void is_valid_password() {
        // Valid
        assertTrue(validator.isValidPassword("Password123!"));
        assertTrue(validator.isValidPassword("Strong@Pass2021"));

        // Invalid
        assertFalse(validator.isValidPassword("pass"));               // Too short
        assertFalse(validator.isValidPassword("password"));           // No numbers or special chars
        assertFalse(validator.isValidPassword("Password125"));        // No special characters
        assertFalse(validator.isValidPassword("!@#$%^&*()"));         // No letters or numbers
    }

    @Test
    public void is_valid_email() {
        // Valid
        assertTrue(validator.isValidEmail("example@example.com"));
        assertTrue(validator.isValidEmail("user.name+tag+sorting@example.com"));
        assertTrue(validator.isValidEmail("user_name@example.co.uk"));

        // Invalid
        assertFalse(validator.isValidEmail("plainaddress"));                // Missing @ and domain
        assertFalse(validator.isValidEmail("missingdomain@.com"));          // Missing part of domain

    }

    @Test
    public void is_valid_credit_card() {
        // Valid
        assertTrue(validator.isValidCreditCard("1234567812345678"));

        // Invalid
        assertFalse(validator.isValidCreditCard("123456787654321"));  // Random numbers
    }



}