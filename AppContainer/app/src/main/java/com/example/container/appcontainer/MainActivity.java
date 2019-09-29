package com.example.container.appcontainer;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
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

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {
    SpeedDialView speedDialView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // ---------- FAB SPEED DIAL ------------------------------------------------------------------------------------

        // acceder speed dial
        speedDialView = findViewById(R.id.speedDial);

        // cambiar icono del fab principal
        speedDialView.setMainFabClosedDrawable(MaterialDrawableBuilder.with(this.getBaseContext()) // provide a context
                .setIcon(MaterialDrawableBuilder.IconValue.DOTS_HORIZONTAL) // provide an icon
                .setColor(Color.WHITE) // set the icon color
                .setToActionbarSize() // set the icon size
                .build());

        // rotacion de abrir/cerrar fab a 90º para que gire de hor a vert
        speedDialView.setMainFabAnimationRotateAngle(90);

        // action item filtro, añade icono de filter
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder((R.id.filter), MaterialDrawableBuilder.with(this.getBaseContext()) // provide a context
                        .setIcon(MaterialDrawableBuilder.IconValue.FILTER_VARIANT) // provide an icon
                        .setColor(Color.WHITE) // set the icon color
                        .setToActionbarSize() // set the icon size
                        .build())
                        // texto al lado del fab
                        .setLabel(getString(R.string.filter))
                        .create()
        );
        // action item settings, añade icono de settings
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.settings, MaterialDrawableBuilder.with(this.getBaseContext()) // provide a context
                        .setIcon(MaterialDrawableBuilder.IconValue.SETTINGS_OUTLINE) // provide an icon
                        .setColor(Color.WHITE) // set the icon color
                        .setToActionbarSize() // set the icon size
                        .build())
                        // texto al lado del fab
                        .setLabel(getString(R.string.settings))
                        .create()
        );

        // action item info, añade icono de info
        speedDialView.addActionItem(
                new SpeedDialActionItem.Builder(R.id.info, MaterialDrawableBuilder.with(this.getBaseContext()) // provide a context
                        .setIcon(MaterialDrawableBuilder.IconValue.INFORMATION_OUTLINE) // provide an icon
                        .setColor(Color.WHITE) // set the icon color
                        .setToActionbarSize() // set the icon size
                        .build())
                        // texto al lado del fab
                        .setLabel(getString(R.string.info))
                        .create()
        );

        // callback listener de pulsar settings o filtro
        speedDialView.setOnActionSelectedListener(new SpeedDialView.OnActionSelectedListener() {
            @Override
            public boolean onActionSelected(SpeedDialActionItem speedDialActionItem) {
                switch (speedDialActionItem.getId()) {
                    case R.id.settings:
                        // settings action

                        // cerrar con animacion cuando pulsas
                        speedDialView.close();
                    case R.id.filter:
                        // filter action

                        // cerrar el fab con animacion cuando pulsas
                        speedDialView.close();
                        return false; // cierra el fab sin animacion
                    case R.id.info:
                        // filter action

                        // cerrar el fab con animacion cuando pulsas
                        speedDialView.close();
                        return false; // cierra el fab sin animacion
                    default:
                        return true; // true to keep the Speed Dial open
                }
            }
        });

        // ---------------------------------------------------------------------------------------------------------------

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
