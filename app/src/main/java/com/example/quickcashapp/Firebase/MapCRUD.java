package com.example.quickcashapp.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.quickcashapp.Maps.Map;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A class for all of our firebase map logic, throughout the app.
 */
public class MapCRUD {
    private final Map map;
    private final DatabaseReference jobsRef = FirebaseDatabase.getInstance().getReference("jobs");

    /**
     * Dependency Injection
     * This firebase class is specifically for the map.
     *
     * @param map the map that will have updates performed on it from the firebase.
     */
    public MapCRUD(Map map){
        this.map = map;
    }

    /**
     * Gets all info from the firebase needed to get the markers and give the information to the map.
     * If issue with getting info from the firebase it is logged.
     */
    public void loadJobMarkers(){
        jobsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){
                    String location = dataSnapshot.child("location").getValue(String.class);
                    String title = dataSnapshot.child("title").getValue(String.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    String jobID = dataSnapshot.child("jobId").getValue(String.class);
                    Log.e("Lucas MApCrud","Job id for marker is "+jobID);
                    if(location != null && title != null){

                        String coordinates = location.substring(location.indexOf('[') + 1, location.indexOf(']'));
                        String[] parts = coordinates.split(" ");
                        String latLngPart = parts[1];
                        String[] LatLng = latLngPart.split(",");

                        double latitude = Double.parseDouble(LatLng[0].trim());
                        double longitude = Double.parseDouble(LatLng[1].trim());
                        LatLng latLng = new LatLng(latitude,longitude);
                        MarkerOptions options=  new MarkerOptions().position(latLng).title(title).snippet(description);
                        Marker newMarker = map.getMap().addMarker(options);
                        newMarker.setTag(jobID);
                        newMarker.showInfoWindow();

                        //map.addMarkerToMap(latitude, longitude, title);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to load job locations", error.toException());
            }
        });
    }
}
