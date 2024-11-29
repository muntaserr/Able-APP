package com.example.quickcashapp.Firebase;

import com.example.quickcashapp.Job; /**
 * A class for all of our firebase logic, throughout the app.
 */
public interface JobCallback {
    void onJobRetrieved(Job job);
}
