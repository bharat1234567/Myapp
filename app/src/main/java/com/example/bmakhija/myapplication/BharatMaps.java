package com.example.bmakhija.myapplication;

import android.app.Dialog;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.ErrorDialogFragment;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

public class BharatMaps extends FragmentActivity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private GoogleApiClient mGoogleApiClient;
   // private String currentLat,currentLng;    //here to store currentLatitude and currentLongitude
    private Location myLocation;             // this object will have last latitude and longitude positions
    private final static int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;
    private double latitude = 0;
    private double longitude = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bharat_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Toast.makeText(this, "Reaches onCreate function, instantiating googleapi client",
                Toast.LENGTH_LONG).show();
         mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    protected void onStart() {
        Toast.makeText(this, "Reaches onStart function",
                Toast.LENGTH_LONG).show();
        super.onStart();
          mGoogleApiClient.connect();
    }


    protected void onStop() {

        Toast.makeText(this, "Reaches onStop function",
                Toast.LENGTH_LONG).show();
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        /*
         * Google Play services can resolve some errors it detects. If the error
         * has a resolution, try sending an Intent to start a Google Play
         * services activity that can resolve error.
         */
        Toast.makeText(this, "reached onConnectionFailed",
                Toast.LENGTH_LONG).show();
        if (connectionResult.hasResolution()) {
            try {
                // Start an Activity that tries to resolve the error
                connectionResult.startResolutionForResult(this,
                        CONNECTION_FAILURE_RESOLUTION_REQUEST);
                /*
                 * Thrown if Google Play services canceled the original
                 * PendingIntent
                 */
            } catch (IntentSender.SendIntentException e) {
                // Log the error
                e.printStackTrace();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Sorry. Location services not available to you", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onConnectionSuspended(int cause) {
        // TODO Auto-generated method stub
        Toast.makeText(this, "Reached onConnectionSuspended",
                Toast.LENGTH_LONG).show();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "reached onMapReady",
                Toast.LENGTH_LONG).show();
        // Add a marker in Sydney and move the camera


        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // control comes here if permission is not already granted, so we need to ask for the permission now

            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);

            // so what changes i did to bring uses-permissions defined in manifest.xml to this java file are as follows
            // first I imported android.Manifest -- this doesn't get imported by default
            // changed Manifest.permission to android.Manifest.permission everywhere  ---> done, its resolved now!!
        }

    } // end of onmap ready

    @Override
    public void onConnected(Bundle connectionHint) {
        Toast.makeText(this, "Reached to onConnected",
                Toast.LENGTH_LONG).show();
       if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            myLocation = LocationServices.FusedLocationApi.getLastLocation(
                    mGoogleApiClient);
            if (myLocation != null) {
                locateMe();
            }
            else
            {
                Toast.makeText(this,"myLocation isnot populated",Toast.LENGTH_LONG).show();
            }

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
// If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
// permission was granted, yay! Do the task you need to do.
                    Toast.makeText(this, "Thanks for approving the request",
                            Toast.LENGTH_LONG).show(); // for anywhere where getactivity() cannot be resolved you can use this
                    mGoogleApiClient.connect();
                    locateMe();
                } else {
                 // permission denied, boo! Disable the // functionality that depends on this permission.
                    Toast.makeText(this, "GPS access is denied",
                            Toast.LENGTH_LONG).show();
                    locateDef();
                }
                return;
            }
        }}

    public void locateMe(){
        Toast.makeText(this, "Reached to locateMe",
                Toast.LENGTH_LONG).show();
    if(myLocation != null) {
        Toast.makeText(this, "myLocation object is loaded",
                Toast.LENGTH_LONG).show();
        LatLng sydney = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title("My Location"));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15),2500,null); // it will take 2500ms for camera to come to location sydney and get zoomed
        // this is the best apporoach to follow so far



        // FYI :
        // use movecamera normally when u just have to show location, animate camera can be used when you are moving or so
        //mMap.moveCamera(CameraUpdateFactory.zoomTo(15)); // this sets the default zoom level to 15
        // movecamera simply does it and shows the end results to the user like zooming in and moving camera
        // but animate camera shows user camera moving and doing things like zooming, setting new locations etc
        // 1 -- world view , 5 -- country view , 15 -- street view , 20 -- building view
        //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(sydney,15)); // it will bring ur location marker at center of mobile screen
        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,15));
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15)); zoomTo simply zooms the map it won't care weather ur current location is coming in the screen or not
        // while newLatLngZoom sets marker on ur current location and zooms in such a way that marker comes on screen

        // we can also set the minimum and maximum zoom a user can do
        mMap.setMinZoomPreference(5); // country view
        mMap.setMaxZoomPreference(20); // building view

    }
    else
    {
        Toast.makeText(this,"Problem in loading current location",Toast.LENGTH_LONG).show();
        Toast.makeText(this,"Redirecting to default location",Toast.LENGTH_LONG).show();
        locateDef();
    }
}

    public void locateDef()
    {
        Toast.makeText(this, "Reached locateDef",
                Toast.LENGTH_LONG).show();
        LatLng sydney = new LatLng(0,0);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Location:0,0"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        mMap.animateCamera(CameraUpdateFactory.newLatLng(sydney));
    }




} // final end of class