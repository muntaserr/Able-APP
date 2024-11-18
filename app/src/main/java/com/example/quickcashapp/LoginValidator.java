package com.example.quickcashapp;

import java.util.regex.Pattern;

public class LoginValidator {

    public LoginValidator() {
    }

    /**
     *  Validates if the password meets the criteria: at least 1 digit, 1 lowercase, 1 uppercase, and minimum 6 characters
     * @param password the password param the user provides that is being checked
     * @return true or false if the password is valid
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) return false;
        String passwordPattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{6,}$";
        return Pattern.matches(passwordPattern, password);
    }

    /**
     * Validates if the email follows a proper email pattern
     * @param email the email the user provides that is being checked
     * @return ture or false if the password is valid or not
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        String emailPattern = "^[\\w!#$%&'*+/=?`{|}~^.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
        return Pattern.matches(emailPattern, email);
    }

    /**
     * Validates credit card number (length check and Luhn algorithm)
     * @param number The credit card number that is being checked
     * @return If the number is 16 digits (fits requirement of Luhn check), returns true or false
     */
    public static boolean isValidCreditCard(String number) {
        if (number == null || number.isEmpty()) return false;
        // Length check: standard length is 16
        if (number.length() != 16) return false;

        return luhnCheck(number);
    }


    /**
     *  Makes sure the credit number is luhnChecked
     * @param number The number that is being checked provided by the user
     * @return returns true or false depending on if the number passes the check
     */
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

    /**
     * Checks if input string is empty
     * @param input string from user
     * @return makes sure the input isn't empty, returns true if not.
     */
    public static boolean isEmpty(String input) {
        return input == null || input.trim().isEmpty();
    }

    /**
     * Checks if the role selected is either "employer" or "employee"
     * @param role the users selected role
     * @return returns true or false depending on what role the user is.
     */
    public static boolean isValidRole(String role) {
        return "employer".equalsIgnoreCase(role) || "employee".equalsIgnoreCase(role);
    }
}
