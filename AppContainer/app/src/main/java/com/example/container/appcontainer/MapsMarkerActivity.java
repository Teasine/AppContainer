package com.example.container.appcontainer;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public final class MapsMarkerActivity extends AppCompatActivity implements OnMapReadyCallback {

    //DECLARACION DE VARIABLES GLOBALES
    Context context;
    private static final String TAG = MapsMarkerActivity.class.getSimpleName();
    LocationManager locationManager;
    private LogicaFake laLogica;
    private Location location;
    public List<MarkerOptions> marcadorOptions = new ArrayList<>();
    public List<Marker> marcadoresPaper = new ArrayList<>();
    public List<Marker> marcadoresPlastic = new ArrayList<>();
    public List<Marker> marcadoresWaste = new ArrayList<>();
    public List<Marker> marcadoresOrganic = new ArrayList<>();
    public List<Marker> marcadoresGlass = new ArrayList<>();
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    public ClusterManager<MarkerClusterItem> clusterManager;
    public ClusterAlgorithm clusterAlgorithm = new ClusterAlgorithm();
    public GoogleMap googleMap;
    public Collection<MarkerClusterItem> items;

    public MapsMarkerActivity(Context context_, LocationManager locationManager_, Location location_) {

        this.context = context_;
        this.locationManager = locationManager_;
        this.location = location_;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        clusterManager = new ClusterManager<>(context, googleMap);
        clusterManager.setAlgorithm(clusterAlgorithm);
        this.googleMap = googleMap;
        /* Para mejores resultados segun SO
         Or, for better performance, wrap it with PreCachingAlgorithmDecorator, as ClusterManager does by default:
        clusterManager.setAlgorithm(new PreCachingAlgorithmDecorator<MyClusterItem>(clusterAlgorithm);
         */

        googleMap.setOnCameraIdleListener(clusterManager);
        googleMap.setOnMarkerClickListener(clusterManager);

        //Mi posición
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }

        //Quito la opcion navegacion
        googleMap.getUiSettings().setMapToolbarEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(false);


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

        // zoom camera
        //googleMap.animateCamera( CameraUpdateFactory.zoomTo( 17.0f ) );

        // Animar camara a mi localizacion
        if (location != null) {
            location.setLatitude(39.470868);
            location.setLongitude(-0.358238);
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 13));

            CameraPosition cameraPosition = new CameraPosition.Builder()
                    .target(new LatLng(location.getLatitude(), location.getLongitude()))      // Sets the center of the map to location user
                    .zoom(17)                   // Sets the zoom
                    .bearing(90)                // Sets the orientation of the camera to east
                    .tilt(40)                   // Sets the tilt of the camera to 30 degrees
                    .build();                   // Creates a CameraPosition from the builder
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

            //Obtener radio guardado en ajustes
            preferences = context.getSharedPreferences("Ajustes", MODE_PRIVATE);
            editor = preferences.edit();

            Float radio = preferences.getFloat("Radio",0.2f);

            //Obtener contenedores de Valencia del servidor
            laLogica.obtenerContenedoresValencia(radio, location.getLatitude(), location.getLongitude(), new PeticionarioREST.Callback() {
                @Override
                public void respuestaRecibida(int codigo, String cuerpo) {
                    try {
                        JSONArray jsonArrayMedidas = new JSONArray(cuerpo);

                        for (int i = 0; i < jsonArrayMedidas.length(); i++) {

                            JSONObject object = jsonArrayMedidas.getJSONObject(i);

                            Integer idTipoContenedor = object.getInt("IdTipoContenedor");
                            Double longitud = object.getDouble("Longitud");
                            Double latitud = object.getDouble("Latitud");

                            LatLng marcador = new LatLng(latitud, longitud);

                            MarkerOptions options;


                            switch (idTipoContenedor) {
                                case 1:
                                    // Add a marker
                                 /*   marcadoresPlastic.add(googleMap.addMarker(new MarkerOptions().position(marcador)
                                            .title("Plastic").icon(BitmapDescriptorFactory.fromBitmap(markerPlastic))));*/
                                    options = new MarkerOptions().position(marcador)
                                            .title("Plastic");
                                    marcadorOptions.add(options);
                                    break;
                                case 2:
                                    // Add a marker
                                 /*   marcadoresPaper.add(googleMap.addMarker(new MarkerOptions().position(marcador)
                                            .title("Paper").icon(BitmapDescriptorFactory.fromBitmap(markerPaper))));*/
                                    options = new MarkerOptions().position(marcador)
                                            .title("Paper");
                                    marcadorOptions.add(options);
                                    break;
                                case 3:
                                 /*   marcadoresOrganic.add(googleMap.addMarker(new MarkerOptions().position(marcador)
                                            .title("Organic").icon(BitmapDescriptorFactory.fromBitmap(markerOrganic))));*/
                                    options = new MarkerOptions().position(marcador)
                                            .title("Organic");
                                    marcadorOptions.add(options);
                                    break;
                                case 4:
                                 /*   marcadoresGlass.add(googleMap.addMarker(new MarkerOptions().position(marcador)
                                            .title("Glass").icon(BitmapDescriptorFactory.fromBitmap(markerGlass))));*/
                                    options = new MarkerOptions().position(marcador)
                                            .title("Glass");
                                    marcadorOptions.add(options);
                                    break;
                                case 5:
                                 /*   marcadoresWaste.add(googleMap.addMarker(new MarkerOptions().position(marcador)
                                            .title("Waste").icon(BitmapDescriptorFactory.fromBitmap(markerWaste))));*/
                                    options = new MarkerOptions().position(marcador)
                                            .title("Waste");
                                    marcadorOptions.add(options);
                                    break;
                                default:
                                    // code block
                            }
                            // Move the map's camera to the same location
                            // googleMap.moveCamera(CameraUpdateFactory.newLatLng(marcador));
                        }

                        setRenderer(googleMap);
                        addClusterItems();
                        clusterManager.cluster();

                        // Guardamos los items en una lista para acceder luego
                        items = clusterAlgorithm.getItems();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });

        }// si encuentra la localizacion

    }

    private void addClusterItems() {
        for(MarkerOptions markerOptions : marcadorOptions){
            MarkerClusterItem clusterItem = new MarkerClusterItem(markerOptions.getPosition().latitude, markerOptions.getPosition().longitude, markerOptions.getTitle(), markerOptions.getTitle());
            clusterManager.addItem(clusterItem);
        }
    }

    public void setRenderer(GoogleMap googleMap) {
        MarkerClusterRenderer<MarkerClusterItem> clusterRenderer = new MarkerClusterRenderer<>(context, googleMap, clusterManager);
        clusterManager.setRenderer(clusterRenderer);
    }
}
