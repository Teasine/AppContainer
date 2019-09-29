package com.example.container.appcontainer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public final class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    //DECLARACION DE VARIABLES GLOBALES
    Context context;
    boolean permissionGranted;


    public MapsMarkerActivity (Context context, boolean permissionGranted){

        this.context = context;
        this.permissionGranted = permissionGranted;

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        //Mi posición
        if(permissionGranted==true) {
            googleMap.setMyLocationEnabled(true);
        }

        // Marker pngs to small bitmaps
        int height = 100;
        int width = 100;
        Bitmap markerPlastic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_plastic), width, height, false);
        Bitmap markerGlass = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_glass), width, height, false);
        Bitmap markerOrganic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_organic), width, height, false);

        // Add a marker in Sydney, Australia,
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromBitmap(markerPlastic)));

        // Add a marker in antartida,
        LatLng antartida = new LatLng(-79.054148, 26.783465);
        googleMap.addMarker(new MarkerOptions().position(antartida)
                .title("Marker in Antartida").icon(BitmapDescriptorFactory.fromBitmap(markerGlass)));

        // Add a marker in argentina
        // and move the map's camera to the same location.
        LatLng argentina = new LatLng(-38.726140, -62.270526);
        googleMap.addMarker(new MarkerOptions().position(argentina)
                .title("Marker in Argentina").icon(BitmapDescriptorFactory.fromBitmap(markerOrganic)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(argentina));

        // zoom camera
        //googleMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );
    }
}
