package com.example.quickcashapp.Maps;

import static android.app.PendingIntent.getActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.util.Log;

import com.example.quickcashapp.Firebase.MapCRUD;
import com.example.quickcashapp.R;
import com.example.quickcashapp.employeeDashboard.MainActivityEmployee;
import com.example.quickcashapp.employeeDashboard.SearchJobsActivity;
import com.example.quickcashapp.individualJob;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class Map extends AppCompatActivity implements GoogleMap.OnInfoWindowClickListener, OnMapReadyCallback {

    MapCRUD firebase;
    private static final int REQUEST_LOCATION_PERMISSION = 0;
    GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Marker marker;
    private LocationHelper locationHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        firebase = new MapCRUD(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
        //locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        this.locationHelper = new LocationHelper(this);


    }


    /**
     * Initializes the LocationListener to track location changes.
     * When the location changes, the method gets the latitude and longitude, uses a Geocoder
     * to obtain address information from the coordinates, and updates the map with a marker at the new location.
     * If there was a previous marker, it will be removed.
     * The camera is also moved to the new location with a specified zoom level.
     */
    private void initializeMap(){
        Location myLocation = locationHelper.getMyLocation();
        double latitude = myLocation.getLatitude();
        double longitude = myLocation.getLongitude();
        LatLng latLng = new LatLng(latitude,longitude);
        locationHelper.stop();
        if (marker != null) {
            marker.remove();
        }
        marker = mMap.addMarker(new MarkerOptions().position(latLng).title("You"));
        mMap.setMaxZoomPreference(15);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));// Adjust zoom level

    }


    /**
     * Removes location updates when the activity is stopped to save resources and
     * avoid receiving unnecessary location updates while the activity is not in the foreground.
     */
    @Override
    protected void onStop() {
        //locationManager.removeUpdates(locationListener);
        locationHelper.stop();
        super.onStop();

    }

    /**
     * A method to take info from the mapCRUD and actually put the markers on the map
     *
     * @param latitude Latitude of the new job that is being added
     * @param longitude Longitude of the new job that is being added
     * @param title of the new job that is being added to the map
     */
    public void addMarkerToMap(Double latitude, double longitude, String title){
        LatLng jobLocation = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(jobLocation).title(title));
    }

    public GoogleMap getMap(){
        return this.mMap;
    }
    /**
     * Called when the map is ready to be used. This is where you can set up markers, listeners,
     * or perform any other initialization needed for the Google Map.
     *
     * @param googleMap the GoogleMap object that is ready to be used.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        this.initializeMap();
        firebase.loadJobMarkers();
        googleMap.setOnInfoWindowClickListener(this);
    }

    @Override
    public void onInfoWindowClick(Marker marker){
        Log.e("Lucas test", "Marker is: "+marker.getTag().toString());
        Intent intent = new Intent(this.getApplicationContext(), individualJob.class);
        String jobID =(String) marker.getTag();
        if(jobID == null){
            jobID = "-OCnOSL0O2Nn2aK20VN6";
        }
        intent.putExtra("jobID", jobID);
        locationHelper.stop();
        startActivity(intent);
    }
}