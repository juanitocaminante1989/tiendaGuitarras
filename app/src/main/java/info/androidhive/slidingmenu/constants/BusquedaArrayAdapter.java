package info.androidhive.slidingmenu.constants;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.ProductView;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.entities.Producto;

public class BusquedaArrayAdapter extends ArrayAdapter {

	Button articulo;
	TextView marca;
	TextView modelo;
	TextView precio;
	ImageView imagen;
	private LinearLayout singleMessageContainer;
	int layout;
	int textViewResourceId;
	private String codSubCat;
	Fragment fragment;
	private ArrayList<Producto> productos;

	public void add(Producto object) {
		productos.add(object);
		super.add(object);

	}
	/*public CategoryArrayAdapter(int layout){
		this.layout = layout;
	}*/

	public BusquedaArrayAdapter(Context context, int textViewResourceId, int layout, ArrayList<Producto> productos) {
		super(context, textViewResourceId);
		this.layout = layout;
		this. productos = productos;
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
		singleMessageContainer = (LinearLayout) row.findViewById(R.id.searchCont);
		final Producto MessageObj = getItem(position);
		articulo = (Button) row.findViewById(R.id.singleMessage);

		articulo.setText(MessageObj.articulo);
		articulo.setOnClickListener(new View.OnClickListener() {
			@Override
            public void onClick(View arg0) {

              producto(MessageObj.codArticulo, R.layout.activity_main_main, parent);

            }
        });
		return row;
	}

	public void producto(String codSubCat, int layout, ViewGroup parent){
		View row;
		//ViewGroup parent;
		this.codSubCat = codSubCat;
		this.layout = layout;
		//layout = R.layout.activity_proaudio_main;
		LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		row = inflater.inflate(layout, parent, false);
		final Context context = parent.getContext();
		Fragment fragment = null;
		fragment = new ProductView(codSubCat);
		FragmentManager fragmentManager = ((Activity)context).getFragmentManager();
		fragmentManager.beginTransaction().replace(R.id.frame_container, fragment).commit();
	}

}