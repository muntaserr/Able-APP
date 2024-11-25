package com.example.quickcashapp.Firebase;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A class for all of our firebase logic, throughout the app.
 */
public class FirebaseCRUD {
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    public FirebaseCRUD(FirebaseAuth mAuth, DatabaseReference mDatabase){
    this.mAuth = mAuth;
    this.mDatabase = mDatabase;
    }
    public String getJobTitle(){
        return "Title";
    }


}
