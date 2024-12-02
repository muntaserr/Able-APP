package com.example.quickcashapp.Maps;

import static android.content.Context.LOCATION_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;

import android.location.Location;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import android.Manifest;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class LocationHelper {

    private static final int REQUEST_LOCATION_PERMISSION = 0;
    final private Context context;
    LocationManager locationManager;
    LocationListener locationListener;
    Location myLocation;
    public boolean gotLocationYet = false;

    /**
     * Constructor
     * @param context gets the Activity from the calling class to display information.
     */
    public LocationHelper(Context context){
            this.context = context;

            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions((Activity) context,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        REQUEST_LOCATION_PERMISSION);
                return; // Ensure execution stops until permissions are granted
            }
            locationManager = (LocationManager) context.getSystemService(LocationManager.class);


           initializeLocationListener();

            }


    /**
     * Initializes the LocationListener to track location changes.
     * When the location changes, the method gets the latitude and longitude, uses a Geocoder
     * to obtain address information from the coordinates, and updates the map with a marker at the new location.
     * If there was a previous marker, it will be removed.
     * The camera is also moved to the new location with a specified zoom level.
     */

    public Location getMyLocation(){

        if(myLocation == null){

            Log.e("Lucas Location","No location available yet returning dummy loc");
            Location halifaxLocation = new Location("Halifax");
            halifaxLocation.setLatitude(44.6488);
            halifaxLocation.setLongitude(-63.5752);
            return halifaxLocation;

        }else {
            Log.e("Lucas Location", "Got a real lokey");
            this.gotLocationYet = true;
            this.stop();

            return myLocation;
        }


    }
    public void stop(){
        locationManager.removeUpdates(locationListener);
    }
    private void initializeLocationListener() {
        Log.e("Lucas test","Initializing location listener");
        ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) &&
                !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            Log.e("LocationHelper", "No location provider available.");
            // Optionally, prompt the user to enable location services
        }
       locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                Log.e("Lucas test", "Location changed:" + location.toString());
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                LocationHelper.this.myLocation = location;

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
        locationManager.requestLocationUpdates(locationManager.GPS_PROVIDER,500, 10, locationListener);
        locationManager.requestLocationUpdates(locationManager.NETWORK_PROVIDER,500, 10, locationListener);
    }
    public void postedJobs(){

    }

}
