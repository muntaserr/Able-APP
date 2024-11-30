package com.example.quickcashapp.Firebase;


import android.provider.ContactsContract;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * A class for all of our firebase logic, throughout the app.
 */
public class FirebaseCRUD {

    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

    public void getUserID(){

        for(DataSnapshot userSnapshot : s)
    }
}
