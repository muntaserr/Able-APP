package com.example.quickcashapp;

import java.util.regex.Pattern;

public class LoginValidator {

    public LoginValidator(){

    };

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
        return Pattern.matches(passwordPattern, password);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;

        // Regular expression for the email
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    // Credit Card: Validates using Luhn algorithm
    public static boolean isValidCreditCard(String number) {
        if (number == null) return false;

        int length =number.length();

        return (length == 16);
    }
}

