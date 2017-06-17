package info.androidhive.slidingmenu.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.entities.Marca;
import info.androidhive.slidingmenu.fragments.CustomFragment;
import info.androidhive.slidingmenu.fragments.MarcasFragment;

/**
 * Created by Juan on 16/03/2017.
 */

public class MarcaGridViewAdapter extends ArrayAdapter{
    private HashMap<Integer, Marca> marcas;
    private Context context;
    private Activity activity;

    public MarcaGridViewAdapter(Context context, int textViewResourceId, HashMap<Integer, Marca> marcas, Activity activity) {
        super(context, textViewResourceId);
        this.context = context;
        this.marcas = marcas;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return marcas.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(R.layout.gridview_item, parent, false);
        }

        TextView marcaText = (TextView) row.findViewById(R.id.marcaText);

        final Marca marca = marcas.get(position);

        if(marca != null){

            marcaText.setText(marca.getNombre());

            marcaText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Constants.idMarca = marca.getIdMarca();
                    CustomFragment fragment = new MarcasFragment(R.layout.marcas_fragment,null, context,marca.getIdMarca(),marca.getNombre());
                    Constants.createNewFragment(R.id.frame_container, fragment);
                    Constants.currentFragment = 1;
                    Constants.whichFragment = 2;

                    activity.getActionBar().setTitle(marca.getNombre());

                }
            });

        }

        return row;
    }
}
