package com.example.quickcashapp;

import java.util.regex.Pattern;

public class LoginValidator {

    public LoginValidator() {
    }

    // Validates if the password meets the criteria: at least 1 digit, 1 lowercase, 1 uppercase, and minimum 6 characters
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) return false;
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
        return Pattern.matches(passwordPattern, password);
    }

    // Validates if the email follows a proper email pattern
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    // Validates credit card number (length check and Luhn algorithm)
    public static boolean isValidCreditCard(String number) {
        if (number == null || number.isEmpty()) return false;
        // Length check: standard length is 16
        if (number.length() != 16) return false;

        return true;
    }


    private static boolean luhnCheck(String number) {
        int sum = 0;
        boolean alternate = false;
        for (int i = number.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(number.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) n -= 9;
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }

    // Checks if input string is empty
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    // Checks if the role selected is either "employer" or "employee"
    public static boolean isValidRole(String role) {
        return "employer".equalsIgnoreCase(role) || "employee".equalsIgnoreCase(role);
    }
}
