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
import info.androidhive.slidingmenu.database.Controller;
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

        Controller controller = new Controller();
        rootView = inflater.inflate(R.layout.fragment_product, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) rootView.findViewById(R.id.listView1);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listView.setAdapter(productoArrayAdapter);
        productoArrayAdapter = new ProductoArrayAdapter(getActivity().getApplicationContext(), R.layout.activity_products_main, R.layout.activity_products_main, controller.consulta(codSubCat));
        listView.setAdapter(productoArrayAdapter);

        return rootView;

    }





}
