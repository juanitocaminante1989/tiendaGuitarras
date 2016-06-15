package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.NotificationManager;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.constants.ProductoArrayAdapter;
import info.androidhive.slidingmenu.entities.Producto;

public class ProductView extends Fragment {
    private TextView txtResultado;
    private RelativeLayout relLay;
    View rootView;
    public ProductoArrayAdapter productoArrayAdapter;
    public LinearLayout opciones;
    public ListView listView;
    public Animation animFadein1;
    public Animation animFadein2;
    public NotificationManager myNotificationManager;
    public int numMessagesOne = 0;
    public int notificationIdOne = 111;
    List<String> mensajes = new ArrayList<String>();
    String noti1;
    String noti2;
    String noti3;
    int layout;
    String codSubCat;

    public ProductView(String codSubCat) {
        this.codSubCat = codSubCat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_product, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) rootView.findViewById(R.id.listView1);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(productoArrayAdapter);
        productoArrayAdapter = new ProductoArrayAdapter(getActivity().getApplicationContext(), R.layout.activity_products_main, R.layout.activity_products_main);
        listView.setAdapter(productoArrayAdapter);

        consulta(codSubCat);
        return rootView;

    }


    public void consulta(String codArticulo) {

        if (Constants.database != null) {
            Cursor c = Constants.database.rawQuery("SELECT * FROM articulo WHERE codArticulo = '" + codArticulo + "'", null);
            //txtResultado.setText("");

            Producto producto = new Producto();

            int i = 0;
            if (c.moveToFirst()) {
                do {
                    i = i++;
                    codArticulo = c.getString(0);
                    final String codSubCat = c.getString(1);
                    final String codCat = c.getString(2);
                    final String articulo = c.getString(3);
                    final String marca = c.getString(4);
                    final String modelo = c.getString(5);
                    final String descripcion = c.getString(6);
                    final double precio = Double.parseDouble(c.getString(7));
                    final double IVA = Double.parseDouble(c.getString(8));
                    final String direc = c.getString(9);
                    int id = getResources()
                            .getIdentifier(direc, "drawable", getActivity().getApplicationContext().getPackageName());
                    int bla = getResources().getIdentifier("proaudio", "drawable", getActivity().getApplicationContext().getPackageName());

                    final int directorio = id;


                    producto.setCodArticulo(codArticulo);
                    producto.setCodSubCat(codSubCat);
                    producto.setCodCat(codCat);
                    producto.setArticulo(articulo);
                    producto.setMarca(marca);
                    producto.setModelo(modelo);
                    producto.setDescripcion(descripcion);
                    producto.setPrecio(precio);
                    producto.setIVA(IVA);
                    producto.setDirectorio(directorio);

                    productoArrayAdapter.add(producto);

                } while (c.moveToNext());
            }
        }
    }


}
