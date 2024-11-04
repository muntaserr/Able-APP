package com.example.quickcashapp.Maps;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

import android.Manifest;

public class LocationHelper {

    private static final int REQUEST_LOCATION_PERMISSION = 0;
    final private Context context;

    public LocationHelper(Context context){
        this.context = context;
    }


    public void askForPermissions(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Location Permission Needed");
        builder.setMessage("this app need location permission to work correctly");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions((Activity) context, new String[]
                                    {Manifest.permission.ACCESS_FINE_LOCATION},
                            REQUEST_LOCATION_PERMISSION);
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
