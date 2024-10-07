package com.example.quickcashapp;

import java.util.regex.Pattern;

public class LoginValidator {

    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        String passwordPattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}";
        return Pattern.matches(passwordPattern, password);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) return false;

        // Regular expression for the email
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    // Credit Card: Validates using Luhn algorithm
    public static boolean isValidCreditCard(String cardNumber) {
        if (cardNumber == null || !cardNumber.matches("\\d+")) return false;

        int nDigits = cardNumber.length();
        int sum = 0;
        boolean isSecond = false;

        for (int i = nDigits - 1; i >= 0; i--) {
            int d = cardNumber.charAt(i) - '0';

            if (isSecond) d *= 2;
            sum += d / 10;
            sum += d % 10;

            isSecond = !isSecond;
        }
        return (sum % 10 == 0);
    }
}

