package info.androidhive.slidingmenu.fragments;


import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.chops.AssociatedShops;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.GPSTracker;

/**
 * Created by Juan on 26/01/2017.
 */


public class ProfileFragment extends CustomFragment {

    MapView mMapView;
    private View generalView;
    private GoogleMap googleMap;
    private Context context;
    private Bundle savedInstanceState;
    private int layout;

    public ProfileFragment(int layout, View rootView, Context context) {
        super(layout, rootView, context);
        this.context = context;
        this.layout = layout;
    }

    public MarkerOptions addMarketOptions(LatLng place, String title, String snippet){

        MarkerOptions marker = new MarkerOptions();
        marker.position(place).title(title).snippet(snippet);

        return marker;

    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
        generalView = null;
    }

    @Override
    public View onCreateCustomView(View var1) {

        generalView = var1;
        mMapView = (MapView) generalView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                GPSTracker gpsTracker = new GPSTracker(context);
                LatLng sydney = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());

                for (int i =0; i<Controller.getTiendas().size();i++){
                    LatLng coords = new LatLng(Controller.getTiendas().get(i).getLatitude(), Controller.getTiendas().get(i).getLongitude());
                    googleMap.addMarker(addMarketOptions(coords, Controller.getTiendas().get(i).getName(), Controller.getTiendas().get(i).getStreet()));
                }

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return generalView;
    }
}