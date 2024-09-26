package com.example.quickcashapp.data.model;

public class Users {
    private String name;
    private String email;
    private String password;
    private String creditCard;
    private String role;

    // Empty constructor needed for Firebase Realtime Database
    public Users() {}

    // Constructor
    public Users(String name, String email, String password, String creditCard, String role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.creditCard = creditCard;
        this.role = role;
    }

    // Getters and Setters
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

