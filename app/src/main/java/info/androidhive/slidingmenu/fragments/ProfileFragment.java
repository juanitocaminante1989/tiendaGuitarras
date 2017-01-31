package info.androidhive.slidingmenu.fragments;


import android.app.Fragment;
import android.content.Context;
import android.location.Address;
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

import java.util.List;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.chops.MusicalDominguez;
import info.androidhive.slidingmenu.chops.TiendaMidi;
import info.androidhive.slidingmenu.entities.GPSTracker;
import info.androidhive.slidingmenu.chops.MadridMusical;

/**
 * Created by Juan on 26/01/2017.
 */


public class ProfileFragment extends Fragment {

    MapView mMapView;
    private GoogleMap googleMap;
    private Context context;

    public ProfileFragment(Context context){
        this.context = context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.map_fragment, container, false);

        mMapView = (MapView) rootView.findViewById(R.id.mapView);
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

                MadridMusical madridMusical = new MadridMusical();
                TiendaMidi tiendaMidi = new TiendaMidi();
                MusicalDominguez musicalDominguez = new MusicalDominguez();
                LatLng madridMusicalCoord = new LatLng(madridMusical.getLatitude(), madridMusical.getLongitude());
                LatLng tiendaMidiCoord = new LatLng(tiendaMidi.getLatitude(), tiendaMidi.getLongitude());
                LatLng musicalDomCoord = new LatLng(musicalDominguez.getLatitude(), musicalDominguez.getLongitude());

                googleMap.addMarker(addMarketOptions(madridMusicalCoord,"Madrid Musical", "Calle Málaga, 8"));
                googleMap.addMarker(addMarketOptions(tiendaMidiCoord, "Tienda Midi", "Alcalá Norte"));
                googleMap.addMarker(addMarketOptions(musicalDomCoord, "Musical Dominguez","Calle de Elfo, 102"));

                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        return rootView;
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
    }
}