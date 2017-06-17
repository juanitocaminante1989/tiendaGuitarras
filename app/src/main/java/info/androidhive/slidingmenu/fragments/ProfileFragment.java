package info.androidhive.slidingmenu.fragments;


import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.w3c.dom.Text;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.chops.AssociatedShops;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.GPSTracker;
import info.androidhive.slidingmenu.util.DebugUtilities;

/**
 * Created by Juan on 26/01/2017.
 */


public class ProfileFragment extends CustomFragment {

    private View generalView;
    private Context context;
    private int layout;
    private TextView nombre;
    private TextView direccion;
    private TextView codpos;
    private TextView correo;
    private TextView telefono;

    public ProfileFragment(int layout, View rootView, Context context) {
        super(layout, rootView, context, "profile", "Profile");
        this.context = context;
        this.layout = layout;

    }

    @Override
    public View onCreateCustomView(View var1) {

        generalView = var1;

        nombre = (TextView) generalView.findViewById(R.id.welcome);
        direccion = (TextView) generalView.findViewById(R.id.calle);
        codpos = (TextView) generalView.findViewById(R.id.codpos);
        correo = (TextView) generalView.findViewById(R.id.correo);
        telefono = (TextView) generalView.findViewById(R.id.telefono);

        if(Constants.currentClient != null){
            nombre.setText("Bienvenido "+Constants.currentClient.getNombre() + " " + Constants.currentClient.getApellidos());
            direccion.setText("Direccion: "+Constants.currentClient.getDireccion());
            codpos.setText("Codigo postal: "+Constants.currentClient.getCodPos());
            correo.setText("Correo: "+Constants.currentClient.getCorreo());
            telefono.setText("Telefono: "+Constants.currentClient.getTelefono());
        }


        return generalView;
    }

//    @Override
//    public Thread.UncaughtExceptionHandler UnCaughtExceptionHandler() {
//        return super.UnCaughtExceptionHandler();
//    }
}