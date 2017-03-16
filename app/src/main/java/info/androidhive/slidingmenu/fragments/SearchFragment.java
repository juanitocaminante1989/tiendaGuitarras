package info.androidhive.slidingmenu.fragments;

import android.app.Fragment;
import android.content.Context;
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

import java.util.ArrayList;
import java.util.List;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.BusquedaArrayAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;

/**
 * Created by Juan on 10/03/2017.
 */

public class SearchFragment extends Fragment {


    public BusquedaArrayAdapter busquedaArrayAdapter;
    public ListView listView;
    int layout;
    String busqueda;
    private EditText cuadroBusqueda;
    private Button searchButton;
    Context context;
    Controller controller;

    public SearchFragment(){

    }

    public SearchFragment(Context context) {
        this.context = context;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        controller = new Controller();
        View rootView = inflater.inflate(R.layout.search_fragment, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        cuadroBusqueda = (EditText) rootView.findViewById(R.id.search);
        listView = (ListView) rootView.findViewById(R.id.searchList);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        searchButton = (Button) rootView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    busqueda = cuadroBusqueda.getText().toString();
                    busquedaArrayAdapter = new BusquedaArrayAdapter(context, layout, layout, controller.busqueda(busqueda, getActivity()));
                    busquedaArrayAdapter.notifyDataSetChanged();
                    listView.setAdapter(busquedaArrayAdapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });


        return rootView;
    }

}