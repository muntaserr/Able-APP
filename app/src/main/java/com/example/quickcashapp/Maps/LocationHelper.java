package com.example.quickcashapp.Maps;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.quickcashapp.MainActivity;

public class LocationHelper {

    final private AppCompatActivity activity;
    private static final int REQUEST_LOCATION_PERMISSION = 1;
    private final LocationManager locationManager;
    private LocationListener locationListener;


    /**
     *
     * @param activity
     */
    public LocationHelper(AppCompatActivity activity){
        this.activity = activity;
        locationManager = (LocationManager) activity.getSystemService(activity.LOCATION_SERVICE);
    }


    /**
     *
     */
    public void RequestLocationPermission() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            new AlertDialog.Builder(activity)
                    .setTitle("Location Permission")
                    .setMessage("This app needs location permission to better help you find relevant jobs near you")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            //Request permission
                            ActivityCompat.requestPermissions(activity,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_LOCATION_PERMISSION);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                            showManualLocationInputDialog();

                        }
                    })
                    .show();
        } else {
            updateLocation();
        }
    }

    /**
     *
     */
    private void updateLocation(){
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            locationListener = new LocationListener() {
                @Override
                public void onLocationChanged(@NonNull Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                }

                public void onStatusChanged(String provider, int status, Bundle extras) {
                    // Handle changes in the provider status
                }

                @Override
                public void onProviderEnabled(String provider) {
                    // Handle when the provider is enabled
                }

                @Override
                public void onProviderDisabled(String provider) {
                    // Handle when the provider is disabled
                }
            };
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, locationListener);
        }

    }

    /**
     *
     */
    private void showManualLocationInputDialog(){

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Enter Location Manually?");

        final EditText input = new EditText(activity);
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String userLocation = input.getText().toString();
                processUserLocation(userLocation);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }

    /**
     *
     * @param userLocation
     */
    private void processUserLocation(String userLocation) {

    }

}
