package info.androidhive.slidingmenu.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.entities.Producto;

public class ProductoArrayAdapter extends ArrayAdapter {

    TextView articulo;
    TextView marca;
    TextView modelo;
    TextView precio;
    TextView descripcion;
    ImageView imagen;
    private List MessageList = new ArrayList();
    private LinearLayout singleMessageContainer;
    int layout;
    int textViewResourceId;
    private String codSubCat;
    Fragment fragment;
    HashMap<Integer, View> productViews;

    public void add(Producto object) {
        MessageList.add(object);
        super.add(object);

    }
    /*public CategoryArrayAdapter(int layout){
        this.layout = layout;
	}*/

    public ProductoArrayAdapter(Context context, int textViewResourceId, int layout) {
        super(context, textViewResourceId);
        productViews = new HashMap<Integer, View>();
        this.layout = layout;
    }

    public int getCount() {
        return this.MessageList.size();
    }

    public Producto getItem(int index) {
        return (Producto) this.MessageList.get(index);

    }

    public View getView(int position, View convertView, ViewGroup parent) {

        Holder holder = null;
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Producto MessageObj = getItem(position);
        if (!productViews.containsKey(position)) {
            convertView = inflater.inflate(layout, parent, false);
            holder = new Holder();

            singleMessageContainer = (LinearLayout) convertView.findViewById(R.id.singleMessageContainer);
            articulo = (TextView) convertView.findViewById(R.id.articulo);
            marca = (TextView) convertView.findViewById(R.id.marca);
            modelo = (TextView) convertView.findViewById(R.id.modelo);
            precio = (TextView) convertView.findViewById(R.id.precio);
            imagen = (ImageView) convertView.findViewById(R.id.imagen);
            descripcion = (TextView) convertView.findViewById(R.id.descripcion);

            holder.singleMessageContainer = singleMessageContainer;
            holder.articulo = articulo;
            holder.marca = marca;
            holder.modelo = modelo;
            holder.precio = precio;
            holder.imagen = imagen;
            holder.descripcion = descripcion;

            convertView.setTag(holder);
            productViews.put(position, convertView);

        } else {

            convertView = productViews.get(position);
            holder = (Holder) convertView.getTag();
        }


        holder.articulo.setText(" " + MessageObj.articulo);
        holder.marca.setText(" " + MessageObj.marca);
        holder.modelo.setText(" " + MessageObj.modelo);
        String price = Double.toString(MessageObj.precio);
        holder.precio.setText(" " + price + "€ (IVA incluido)");
        holder.imagen.setBackgroundResource((MessageObj.directorio));
        holder.descripcion.setText(" " + MessageObj.descripcion);
        return convertView;
    }

    private class Holder {

        LinearLayout singleMessageContainer;
        TextView articulo;
        TextView marca;
        TextView modelo;
        TextView precio;
        ImageView imagen;
        TextView descripcion;
    }

}