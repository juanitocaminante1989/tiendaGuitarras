package info.androidhive.slidingmenu.adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.entities.Marca;
import info.androidhive.slidingmenu.fragments.MarcasFragment;

/**
 * Created by Juan on 16/03/2017.
 */

public class MarcaGridViewAdapter extends ArrayAdapter{
    private ArrayList<Marca> marcas;
    private Context context;

    public MarcaGridViewAdapter(Context context, int textViewResourceId, ArrayList<Marca> marcas) {
        super(context, textViewResourceId);
        this.context = context;
        this.marcas = marcas;

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
                    Fragment fragment = new MarcasFragment(R.layout.marcas_fragment, marca.getIdMarca(), context);
                    Constants.createNewFragment(R.id.frame_container, fragment);
                    Constants.currentFragment = 2;
                    Constants.whichFragment = 2;
                }
            });

        }

        return row;
    }
}
