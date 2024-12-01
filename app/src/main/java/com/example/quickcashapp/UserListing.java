package com.example.quickcashapp;
public class UserListing {
    private String creditCard;
    private String email;
    private String name; // Combine minSalary and maxSalary as a single string to match the Job class
    private String role;


    public UserListing(String creditCard, String email, String name, String role) {
        this.creditCard = creditCard;
        this.email = email;
        this.name = name;
        this.role = role;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }
}
