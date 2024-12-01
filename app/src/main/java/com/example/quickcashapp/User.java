package com.example.quickcashapp;

public class User {
    public String name;
    public String email;
    public String password;
    public String creditCard;
    public String role;

    public User() {

    }
    /**
     * Constructor for user including all important info
     */
    public User(String name, String email, String password, String creditCard, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.creditCard = creditCard;
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(String creditCard) {
        this.creditCard = creditCard;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}

