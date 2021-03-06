package com.example.container.appcontainer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.leinardi.android.speeddial.SpeedDialActionItem;
import com.leinardi.android.speeddial.SpeedDialView;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import java.sql.Array;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //DECLARACION DE VARIABLES GLOBALES
    SpeedDialView speedDialView;
    Integer[] showOnMap = new Integer[5];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Include the OnCreate() method here too, as described above.

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
                        boolean open = showFilterMenu(findViewById(R.id.filter));
                        return true; // cierra el fab sin animacion
                    case R.id.info:
                        // info action
                        //startInfoActivity();
                        presentActivity(findViewById(R.id.info));
                        // cerrar el fab con animacion cuando pulsas
                        // speedDialView.close();
                        return true;
                    default:
                        return true; // true to keep the Speed Dial open
                }
            }
        });

        // ---------------------------------------------------------------------------------------------------------------

        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);


        //Creación del mapa
        MapsMarkerActivity map = new MapsMarkerActivity(this, locationManager);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(map);

        // ------------Array checked filtrado---------------------------------------------------

        // Hacemos checked todos los filtros al iniciar la app (aparecen todos los contenedores)
        showAllBins();

    }//OnCreate()


    public boolean showFilterMenu(View anchor) {
        PopupMenu popup = new PopupMenu(this, anchor, R.style.FilterPopup);
        popup.getMenuInflater().inflate(R.menu.filter_menu, popup.getMenu());
        // Antes de mostrar el menu del popup miramos si estaba checked o no, y lo mostramos como tal
        for (int i = 0; i < showOnMap.length; i++) {
            if (showOnMap[i] == 1) {
                // Mostramos que sea checked
                popup.getMenu().getItem(i).setChecked(true);
            }
        }
        popup.show();

        
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // cambia el checked del item cuando es pulsado
                item.setChecked(!item.isChecked());

                // Keep the popup menu open -------------------------------------------------------
                item.setShowAsAction(MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
                item.setActionView(new View(getBaseContext()));
                item.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
                    @Override
                    public boolean onMenuItemActionExpand(MenuItem item) {
                        return false;
                    }

                    @Override
                    public boolean onMenuItemActionCollapse(MenuItem item) {
                        return false;
                    }
                });
                // --------------------------------------------------------------------------------

                // El switch cambia el checked del item dependiendo del item
                // -- falta implementar el filtrado real de los contenedores
                switch(item.getItemId()){
                    case R.id.plasticFilter:
                        if (item.isChecked()) showOnMap[0] = 1;
                        else showOnMap[0] = 0;
                        return false;
                    case R.id.glassFilter:
                        if (item.isChecked()) showOnMap[1] = 1;
                        else showOnMap[1] = 0;
                        return false;
                    case R.id.organicFilter:
                        if (item.isChecked()) showOnMap[2] = 1;
                        else showOnMap[2] = 0;
                        return false;
                    case R.id.paperFilter:
                        if (item.isChecked()) showOnMap[3] = 1;
                        else showOnMap[3] = 0;
                        return false;
                    case R.id.wasteFilter:
                        if (item.isChecked()) showOnMap[4] = 1;
                        else showOnMap[4] = 0;
                        return false;
                    default:
                        return false;
                }
            }
        });

        return true;
    }

    // Hacemos checked todos los filtros al iniciar la app (aparecen todos los contenedores)
    public void showAllBins() {
        for (int i = 0; i < showOnMap.length; i++) {
            showOnMap[i] = 1;
        }
    }

    // Empezar info activity
    public void startInfoActivity() {
        Intent intent = new Intent(this, InfoActivity.class);
        startActivity(intent);
    }

    public void presentActivity(View view) {

        // https://android.jlelse.eu/a-little-thing-that-matter-how-to-reveal-an-activity-with-circular-revelation-d94f9bfcae28
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        int[] locationOnScreen = new int[2];
        view.getLocationOnScreen(locationOnScreen);

        Intent intent = new Intent(this, InfoActivity.class);
        intent.putExtra(InfoActivity.EXTRA_CIRCULAR_REVEAL_X, locationOnScreen[0]+250);
        intent.putExtra(InfoActivity.EXTRA_CIRCULAR_REVEAL_Y, locationOnScreen[1]);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
