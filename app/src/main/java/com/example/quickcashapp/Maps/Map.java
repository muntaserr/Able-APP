package com.example.quickcashapp.Maps;

import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;

import com.example.quickcashapp.Firebase.FirebaseCRUD;
import com.example.quickcashapp.Firebase.MapCRUD;
import com.example.quickcashapp.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;



public class Map extends AppCompatActivity implements OnMapReadyCallback {

    MapCRUD firebase;
    private static final int REQUEST_LOCATION_PERMISSION = 0;
    GoogleMap mMap;
    LocationManager locationManager;
    LocationListener locationListener;
    Marker marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

        firebase = new MapCRUD(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]
                            {Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }

        initializeLocationListener();

        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener); **/
    }


    /**
     * Initializes the LocationListener to track location changes.
     * When the location changes, the method gets the latitude and longitude, uses a Geocoder
     * to obtain address information from the coordinates, and updates the map with a marker at the new location.
     * If there was a previous marker, it will be removed.
     * The camera is also moved to the new location with a specified zoom level.
     */

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

    /**
     * Removes location updates when the activity is stopped to save resources and
     * avoid receiving unnecessary location updates while the activity is not in the foreground.
     */
    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(locationListener);
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

    /**
     * Called when the map is ready to be used. This is where you can set up markers, listeners,
     * or perform any other initialization needed for the Google Map.
     *
     * @param googleMap the GoogleMap object that is ready to be used.
     */
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

      //  LatLng halifax = new LatLng(44.6488, -63.5752);
      //  mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(halifax, 12f)); Uncomment this and comment the Location listener if you need to use this feature

        firebase.loadJobMarkers();
    }
}