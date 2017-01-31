package info.androidhive.slidingmenu;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import info.androidhive.slidingmenu.adapter.ProductoArrayAdapter;
import info.androidhive.slidingmenu.database.Controller;

public class ProductView extends Fragment {
    View rootView;
    public ProductoArrayAdapter productoArrayAdapter;
    public ListView listView;
    int layout;
    String codSubCat;
    Context context;

    public ProductView(String codSubCat, int layout, Context context) {
        this.context = context;
        this.codSubCat = codSubCat;
        this.layout = layout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Controller controller = new Controller();
        rootView = inflater.inflate(layout, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) rootView.findViewById(R.id.listView1);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(productoArrayAdapter);
        productoArrayAdapter = new ProductoArrayAdapter(context, R.layout.activity_products_main, R.layout.activity_products_main, controller.consulta(codSubCat));
        listView.setAdapter(productoArrayAdapter);

        return rootView;

    }





}
