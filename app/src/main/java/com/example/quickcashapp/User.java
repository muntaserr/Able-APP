package com.example.quickcashapp;

public class User {
    public String name;
    public String email;
    public String password;
    public String creditCard;
    public String role;
    public float rating;

    public User() {

    }
    /**
     * Constructor for user including all important info
     */
    public User(String name, String email, String password, String creditCard, String role, float rating) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.creditCard = creditCard;
        this.role = role;
        this.rating =  0;
    }
}

