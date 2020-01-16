package com.example.container.appcontainer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;

public class MarkerClusterRenderer<T extends ClusterItem> extends DefaultClusterRenderer<T> {

    Context context;
    public MarkerClusterRenderer(Context context, GoogleMap googleMap, ClusterManager<T> clusterManager){
        super(context, googleMap, clusterManager);
        this.context = context;
    }

    @Override
    protected boolean shouldRenderAsCluster(Cluster cluster) {
        return cluster.getSize() >= 3;
    }

    @Override
    public int getMinClusterSize() {
        return 500;
    }

    // Para cambiar el icono de los markers
    @Override
    protected void onBeforeClusterItemRendered(T item, MarkerOptions markerOptions) {
        int height = 150;
        int width = 150;
        final BitmapDescriptor markerPlastic = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_plastic), width, height, false));
        final BitmapDescriptor markerGlass = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_glass), width, height, false));
        final BitmapDescriptor markerOrganic = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_organic), width, height, false));
        final BitmapDescriptor markerWaste = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_restos), width, height, false));
        final BitmapDescriptor markerPaper = BitmapDescriptorFactory.fromBitmap(Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_paper), width, height, false));

        switch (markerOptions.getTitle()) {
            case "Plastic":
                markerOptions.icon(markerPlastic).snippet("meme");
                break;
            case "Glass":
                markerOptions.icon(markerGlass).snippet("meme");
                break;
            case "Organic":
                markerOptions.icon(markerOrganic).snippet("meme");
                break;
            case "Waste":
                markerOptions.icon(markerWaste).snippet("meme");
                break;
            case "Paper":
                markerOptions.icon(markerPaper).snippet("meme");
                break;
            default:
                // code block
        }
    }

    // Para cambiar el icono de los clusters
    @Override
    protected void onBeforeClusterRendered(Cluster<T> cluster, MarkerOptions markerOptions) {
        int height = 170;
        int width = 170;
        final Bitmap markerOrganic = Bitmap.createScaledBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.marker_organic), width, height, false);
        final BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(markerOrganic);
        markerOptions.icon(bitmapDescriptor).snippet("meme");
    }
}
