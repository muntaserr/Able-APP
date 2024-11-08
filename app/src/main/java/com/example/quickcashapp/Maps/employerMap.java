package com.example.quickcashapp.Maps;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.util.Log;

import com.example.quickcashapp.R;
import com.example.quickcashapp.employerDashboard.MainActivityEmployer;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;


public class employerMap extends MainActivityEmployer implements OnMapReadyCallback {


    private static final int REQUEST_LOCATION_PERMISSION = 1;
    GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Marker marker;
    LatLng myLocation;
    public static final double LONGITUDE = -63.58;
    public static final double LATITUDE = 44.65;
    public boolean firstLocation = true;

    public void setLocation(LatLng location){
        this.myLocation = location;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_map);
        Log.e("Lucas test", "Employer map is being created");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        if(this.myLocation == null){
            Log.e("Lucas test", "Location is not available yet");
            this.myLocation = new LatLng(LATITUDE, LONGITUDE);
        }


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.e("Lucas test", "The app deffs doesn't have permission");
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }else{
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Log.e("Lucas test", latitude+":"+longitude);
                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = addresses.get(0).getLocality() + ": " + addresses.get(0).getCountryName();

                    Log.e("Lucas test",address);
                    LatLng latLng = new LatLng(latitude, longitude);
                    this.setLocation( latLng);
                    if(employerMap.this.firstLocation){
                        if (marker != null) {
                            marker.remove();
                        }
                        marker = mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                        mMap.setMaxZoomPreference(15);
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));// Adjust zoom level
                        employerMap.this.firstLocation=false;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            private void setLocation(LatLng latLng) {
                employerMap.this.setLocation(latLng);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }



    private void initializeLocationListener() {

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                Geocoder geocoder = new Geocoder(getApplicationContext());
                try {
                    List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
                    String address = addresses.get(0).getLocality() + ": " + addresses.get(0).getCountryName();

                    LatLng latLng = new LatLng(latitude, longitude);

                    if (marker != null) {
                        marker.remove();
                    }
                    marker = mMap.addMarker(new MarkerOptions().position(latLng).title(address));
                    mMap.setMaxZoomPreference(15);
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));// Adjust zoom level

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
    }

    protected LatLng getLatLong() {

            return this.myLocation;

    }

    protected String getMyLocationTitle() {
        Geocoder geocoder = new Geocoder(getApplicationContext());
        LatLng myLoc = this.getLatLong();
        String address;
        try {
            List<Address> addresses = geocoder.getFromLocation(myLoc.latitude, myLoc.longitude, 1);
            address = addresses.get(0).getLocality() + ": " + addresses.get(0).getCountryName();

        } catch (IOException e) {
            e.printStackTrace();
            address = null;
        }
        return address;
    }
            @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;


        LatLng myLocation = getLatLong();
        mMap.addMarker(new MarkerOptions().position(myLocation).title(getMyLocationTitle()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myLocation));
    }
}