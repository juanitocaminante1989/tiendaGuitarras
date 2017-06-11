package info.androidhive.slidingmenu.adapter;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Fragment;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.fragments.CustomFragment;
import info.androidhive.slidingmenu.fragments.ProductFragment;

public class BusquedaArrayAdapter extends ArrayAdapter {

    Button articulo;
    int layout;
    private HashMap<Integer, Producto> productos;
    Context context;

    public BusquedaArrayAdapter(Context context, int textViewResourceId, int layout, HashMap<Integer, Producto> productos) {
        super(context, textViewResourceId);
        this.context = context;
        this.layout = layout;
        this.productos = productos;
    }

    public int getCount() {
        return this.productos.size();
    }

    public Producto getItem(int index) {
        return (Producto) this.productos.get(index);

    }

    public View getView(int position, View convertView, final ViewGroup parent) {

        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.activity_main_main, parent, false);
        }
        final Producto MessageObj = getItem(position);
        articulo = (Button) row.findViewById(R.id.singleMessage);

        articulo.setText(MessageObj.articulo);
        if(MessageObj.getCodArticulo().equals("null")){
            articulo.setEnabled(false);
        }
        articulo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                producto(MessageObj.codArticulo, R.layout.activity_main_main, parent, context);
                Constants.currentFragment = 1;

            }
        });
        return row;
    }

    public void producto(String codSubCat, int layout, ViewGroup parent,Context context) {
        View row;
        //ViewGroup parent;
        this.layout = layout;
        //layout = R.layout.activity_proaudio_main;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        row = inflater.inflate(layout, parent, false);
        CustomFragment fragment = null;
        Controller controller = new Controller();
        fragment = new ProductFragment(R.layout.activity_products_main, null, context, controller.consulta(codSubCat));
        Constants.createNewFragment(R.id.frame_container, fragment);

    }

}