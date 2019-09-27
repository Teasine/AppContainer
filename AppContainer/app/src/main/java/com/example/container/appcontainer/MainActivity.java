package com.example.container.appcontainer;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Fab
        SpeedDialView speedDialView = findViewById(R.id.speedDial);
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder((R.id.fab), R.drawable.baseline_add_24)
                        .create()
        );

        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.fab:
                        //showToast("Link action clicked!");
                        return true; // true to keep the Speed Dial open
                    default:
                        return false;
                }
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng sydney = new LatLng(-33.852, 151.211);

        // create marker
        MarkerOptions marker = new MarkerOptions().position(sydney);

        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng antartida = new LatLng(-79.054148, 26.783465);
        googleMap.addMarker(new MarkerOptions().position(antartida)
                .title("Marker in Antartida").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(antartida));

        // Add a marker in Sydney, Australia,
        // and move the map's camera to the same location.
        LatLng argentina = new LatLng(-38.726140, -62.270526);
        googleMap.addMarker(new MarkerOptions().position(argentina)
                .title("Marker in Argentina").icon(BitmapDescriptorFactory.fromResource(R.drawable.icon_marker)));
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(argentina));


    }
}
