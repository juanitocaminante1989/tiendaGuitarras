package info.androidhive.slidingmenu.fragments;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.BusquedaArrayAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Producto;

public class HomeFragment extends FragmentCreator {

    private TextView txtResultado;
    private RelativeLayout relLay;
    View rootView;
    public BusquedaArrayAdapter busquedaArrayAdapter;
    public LinearLayout opciones;
    public ListView listView;
    public Animation animFadein1;
    public Animation animFadein2;

    public int numMessagesOne = 0;
    public int notificationIdOne = 111;
    List<String> mensajes = new ArrayList<String>();
    String noti1;
    String noti2;
    String noti3;
    int layout;
    String busqueda;
    private EditText cuadroBusqueda;
    private Button searchButton;

    public HomeFragment(int layout) {
        this.layout = layout;
    }

    public HomeFragment(int layout, String busqueda) {
        this.layout = layout;
        this.busqueda = busqueda;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        return null;
    }



    public void buscar() {

    }

    @Override
    public View FragmentCreator(int layout, String query) {
        View view = super.FragmentCreator(layout, query);
                Controller controller= new Controller();
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) view.findViewById(R.id.listView1);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        if (busqueda == null || busqueda == "") {
            buscar();
        } else {

            busquedaArrayAdapter = new BusquedaArrayAdapter(getActivity().getApplicationContext(), layout, layout,  controller.busqueda(busqueda, getActivity()));
            busquedaArrayAdapter.notifyDataSetChanged();
            listView.setAdapter(busquedaArrayAdapter);

        }
        return view;

    }


}