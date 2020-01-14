package com.example.container.appcontainer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    //DECLARACION DE VARIABLES GLOBALES
    Context context;
    private static final String TAG = MapsMarkerActivity.class.getSimpleName();
    LocationManager locationManager;
    private LogicaFake laLogica;



    public MapsMarkerActivity (Context context, LocationManager locationManager){

        this.context = context;
        this.locationManager = locationManager;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        //Quito la opcion navegacion
        googleMap.getUiSettings().setMapToolbarEnabled(false);
        googleMap.getUiSettings().setZoomControlsEnabled(true);

        laLogica = new LogicaFake();


        //Cambio de estilo de maps
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            context, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }


        //Mi posición
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
             googleMap.setMyLocationEnabled(true);
        }

        // Marker pngs to small bitmaps
        int height = 100;
        int width = 100;
        final Bitmap markerPlastic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_plastic), width, height, false);
        final Bitmap markerGlass = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_glass), width, height, false);
        final Bitmap markerOrganic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_organic), width, height, false);

        // Add a marker in Sydney, Australia,
        LatLng sydney = new LatLng(-33.852, 151.211);
        googleMap.addMarker(new MarkerOptions().position(sydney)
                .title("Marker in Sydney").icon(BitmapDescriptorFactory.fromBitmap(markerPlastic)));

        // Add a marker in antartida,
        LatLng antartida = new LatLng(-79.054148, 26.783465);
        googleMap.addMarker(new MarkerOptions().position(antartida)
                .title("Marker in Antartida").icon(BitmapDescriptorFactory.fromBitmap(markerGlass)));

        // zoom camera
        //googleMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        Criteria criteria = new Criteria();

        Location location = locationManager.getLastKnownLocation(locationManager.getBestProvider(criteria, false));
        if (location != null)
        {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        }

        //Subir imagen al servidor
        laLogica.obtenerContenedoresValencia(new PeticionarioREST.Callback () {
            @Override
            public void respuestaRecibida(int codigo, String cuerpo) {
                try {
                    JSONArray jsonArrayMedidas = new JSONArray(cuerpo);
                    JSONObject object = jsonArrayMedidas.getJSONObject(1);

                    //String longitud = object.getDouble("longitud");
                    Double longitud = object.getDouble("Longitud");
                    Double latitud = object.getDouble("Latitud");

                    // Add a marker in argentina
                    // and move the map's camera to the same location.
                    LatLng argentina = new LatLng(latitud, longitud);
                    googleMap.addMarker(new MarkerOptions().position(argentina)
                            .title("Marker in Argentina").icon(BitmapDescriptorFactory.fromBitmap(markerOrganic)));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(argentina));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
