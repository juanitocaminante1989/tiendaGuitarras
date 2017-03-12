package info.androidhive.slidingmenu.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.CategoryMessage;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.BusquedaArrayAdapter;
import info.androidhive.slidingmenu.adapter.ProductoArrayAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Producto;

public class HomeFragment extends Fragment {
    public LinearLayout leftArrow;
    public LinearLayout rightArrow;
    public LinearLayout offerLinear;
    Context context;
    ArrayList<Producto> productos;
    ArrayList<CategoryMessage> categoryMessages;
    public TextView name;
    Producto producto;
    Controller controller;
    TextView precio;
    ImageView imagen;

    public HomeFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        controller = new Controller();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        leftArrow = (LinearLayout) rootView.findViewById(R.id.left_arrow);
        rightArrow = (LinearLayout) rootView.findViewById(R.id.right_arrow);
        name = (TextView) rootView.findViewById(R.id.itemText);
        precio = (TextView) rootView.findViewById(R.id.itemPrice);
        imagen = (ImageView) rootView.findViewById(R.id.itemImage);
        offerLinear = (LinearLayout) rootView.findViewById(R.id.offerLinear);
        try {
            offerLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    goToOffer(producto.getCodArticulo());
                    Constants.currentFragment = 1;
                }
            });

            categoryMessages = controller.consultaSubCategorias("guitar100");

            if (categoryMessages.size() != 0) {
                productos = controller.consultaArticulos(categoryMessages.get(0).getTitle());

                if (productos.size() != 0) {
                    producto = productos.get(0);
                    if (producto != null)
                        fillData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view(-1);
            }
        });

        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view(+1);
            }
        });

        return rootView;
    }

    public void goToOffer(String codSubCat) {
        Fragment fragment = null;
        Controller controller = new Controller();
        fragment = new ProductoArrayAdapter(context, R.layout.activity_products_main, controller.consulta(codSubCat));
        if (Constants.manager != null)
            Constants.manager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void fillData() {
        if (producto != null) {
            name.setText(producto.getArticulo());
            precio.setText(producto.getPrecio() + "");
            File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/" + "tiendamusica");
            File[] files = filePath.listFiles();
            Uri uri = null;
            for (File file : files) {
                if (file.getName().equals(producto.directorio)) {
                    uri = Uri.fromFile(file);

                }
            }
//        holder.imagen.setBackgroundResource(-1);

            imagen.setImageURI(uri);
        }
    }

    public void view(int number) {

        int index = 0;

        if (number == +1) {

            index = productos.indexOf(producto);
            if (index == productos.size() - 1) {
                producto = productos.get(0);
            } else {
                producto = productos.get(index + number);
            }
        } else {
            index = productos.indexOf(producto);
            if (index == 0) {
                producto = productos.get(productos.size() - 1);
            } else {
                producto = productos.get(index + number);
            }
        }
        if (producto != null)
            fillData();
    }

}