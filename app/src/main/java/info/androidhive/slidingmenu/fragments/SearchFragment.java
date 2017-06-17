package info.androidhive.slidingmenu.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.BusquedaArrayAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.util.DebugUtilities;

/**
 * Created by Juan on 10/03/2017.
 */

public class SearchFragment extends CustomFragment {


    public BusquedaArrayAdapter busquedaArrayAdapter;
    public ListView listView;
    int layout;
    String busqueda;
    private SearchView cuadroBusqueda;
    private Button searchButton;
    Context context;
    Controller controller;
    private View generalView;

    public SearchFragment(int layout, View rootView, Context context) {
        super(layout, rootView, context, "search", "Search");
        this.layout = layout;
        this.context = context;

    }

    @Override
    public View onCreateCustomView(View var1) {
        controller = new Controller();
        View rootView = var1;
        generalView = rootView;
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        cuadroBusqueda = (SearchView) rootView.findViewById(R.id.search);
        listView = (ListView) rootView.findViewById(R.id.searchList);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);

        searchButton = (Button) rootView.findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Search();
            }
        });

        cuadroBusqueda.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                Search();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        return rootView;
    }

    public void Search(){
        try {
            busqueda = cuadroBusqueda.getQuery().toString();
            busquedaArrayAdapter = new BusquedaArrayAdapter(context, layout, layout, controller.busqueda(busqueda, getActivity()));
            busquedaArrayAdapter.notifyDataSetChanged();
            listView.setAdapter(busquedaArrayAdapter);
            hideKeyBoard();
            listView.setSelectionAfterHeaderView();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void hideKeyBoard(){
        View view = generalView;
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

//    @Override
//    public Thread.UncaughtExceptionHandler UnCaughtExceptionHandler() {
//        return super.UnCaughtExceptionHandler();
//    }
}