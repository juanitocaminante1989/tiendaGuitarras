package info.androidhive.slidingmenu.adapter;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.util.ScrollViewX;

public class ProductoArrayAdapter extends Fragment {

    TextView articulo;
    TextView precio;
    TextView descripcion;
//    ImageView imagen;
    private LinearLayout sampleLinear;
    int layout;
    HashMap<Integer, View> productViews;
    private Producto currentProducto;
    private Producto producto;
    private Button play;
    Context context;
    private ProgressBar progressBar;
    private LinearLayout leftArrow;
    private LinearLayout rightArrow;
    ArrayList<Producto> productos;
    Controller controller;
    View view;
    LinearLayout similarProduct;
    ActionBar actionBar;
    ViewPager viewPager;

    public ProductoArrayAdapter(Context context, int layout, Producto producto) {

        this.context = context;
        productViews = new HashMap<Integer, View>();
        this.layout = layout;
        this.producto = producto;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_products_main, container, false);
        view = rootView;
        controller = new Controller();
        sampleLinear = (LinearLayout) rootView.findViewById(R.id.samplelinear);
        articulo = (TextView) rootView.findViewById(R.id.articulo);
        precio = (TextView) rootView.findViewById(R.id.precio);
//        imagen = (ImageView) rootView.findViewById(R.id.imagen);
        descripcion = (TextView) rootView.findViewById(R.id.descripcion);
        play = (Button) rootView.findViewById(R.id.playsample);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar4);
        leftArrow = (LinearLayout) rootView.findViewById(R.id.left_arrow);
        rightArrow = (LinearLayout) rootView.findViewById(R.id.right_arrow);
        similarProduct = (LinearLayout) rootView.findViewById(R.id.similarProduct);
        actionBar = getActivity().getActionBar();
        viewPager = (ViewPager) rootView.findViewById(R.id.pager);


        final ColorDrawable cd = new ColorDrawable(Color.rgb(0, 0, 255));
        actionBar.setBackgroundDrawable(cd);
        cd.setAlpha(255);
        ScrollViewX scrollView = (ScrollViewX) rootView.findViewById(R.id.scroll_view);

        scrollView.setOnScrollViewListener(new ScrollViewX.OnScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollViewX v, int l, int t, int oldl, int oldt) {

                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0,maxDist = 255;
                if(scrollY>maxDist){
                    return 255;
                }
                else if(scrollY<minDist){
                    return 0;
                }
                else {
                    int alpha = 0;
                    alpha = (int)  ((255.0/maxDist)*(maxDist-scrollY));
                    return alpha;
                }
            }

        });
        similarProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSimilarProduct(currentProducto.getCodArticulo());
            }
        });

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
        if (producto.codCat.equals("guitar100") || producto.codCat.equals("bass200")) {
            sampleLinear.setVisibility(View.VISIBLE);
        } else {
            sampleLinear.setVisibility(View.GONE);
        }
        productos = controller.consultaArticulos(producto.getCodSubCat());
        if (productos.size() != 0) {
            try {
                Iterator<Producto> iter = productos.iterator();
                while (iter.hasNext()) {
                    if (iter.next().getCodArticulo().equals(producto.getCodArticulo())) {
                        iter.remove();
                    }
                }
                currentProducto = productos.get(0);
                fillDataSimilar();
                if(productos.size()==1){
                    leftArrow.setVisibility(View.INVISIBLE);
                    rightArrow.setVisibility(View.INVISIBLE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        fillData();

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSample();
            }
        });
        return rootView;
    }

    public void goToSimilarProduct(String codSubCat) {
        Fragment fragment = null;
        Controller controller = new Controller();
        fragment = new ProductoArrayAdapter(context, R.layout.activity_products_main, controller.consulta(codSubCat));
        if (Constants.manager != null)
            Constants.manager.beginTransaction().replace(R.id.frame_container, fragment).commit();
    }

    public void fillData() {
        if (producto != null) {
            articulo.setText(producto.getArticulo());
            precio.setText(producto.getPrecio() + "€ (IVA incluido)");
            descripcion.setText(" " + producto.getDescripcion());
            File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/" + "tiendamusica");
            File[] files = filePath.listFiles();
            Uri uri = null;
            for (File file : files) {
                if (file.getName().equals(producto.directorio)) {
                    uri = Uri.fromFile(file);

                }
            }
            ArrayList<Uri> images = new ArrayList<Uri>();
            images.add(uri);
            ArrayList<Producto> productos = controller.consultaArticulos(producto.getCodSubCat());
            for(Producto producto: productos){
                for (File file : files) {
                    if (file.getName().equals(producto.directorio)) {
                        uri = Uri.fromFile(file);
                        images.add(uri);
                    }
                }
            }
            CustomPagerAdapter adapter = new CustomPagerAdapter(context, images);
            viewPager.setAdapter(adapter);
//            imagen.setImageURI(uri);
        }
    }

    public void fillDataSimilar() {
        if (currentProducto != null) {
            TextView itemText = (TextView) view.findViewById(R.id.itemText);
            TextView itemPrice = (TextView) view.findViewById(R.id.itemPrice);
            ImageView itemImage = (ImageView) view.findViewById(R.id.itemImage);
            itemText.setText(currentProducto.getArticulo());
            itemPrice.setText(currentProducto.getPrecio() + "€ ");
            File filePath = new File(android.os.Environment.getExternalStorageDirectory().getPath() + "/" + "tiendamusica");
            File[] files = filePath.listFiles();
            Uri uri = null;
            for (File file : files) {
                if (file.getName().equals(currentProducto.getDirectorio())) {
                    uri = Uri.fromFile(file);

                }
            }

            itemImage.setImageURI(uri);
        }
    }

    public void view(int number) {

        int index = 0;

        if (number == +1) {

            index = productos.indexOf(currentProducto);
            if (index == productos.size() - 1) {
                currentProducto = productos.get(0);
            } else {
                currentProducto = productos.get(index + number);
            }
        } else {
            index = productos.indexOf(currentProducto);
            if (index == 0) {
                currentProducto = productos.get(productos.size() - 1);
            } else {
                currentProducto = productos.get(index + number);
            }
        }
        if (currentProducto != null)
            fillDataSimilar();
    }

    private void playSample() {
        MediaPlayer mp = MediaPlayer.create(context, R.raw.sample);
        final SeekBar seekBar = new SeekBar(context);
        final int duration = mp.getDuration();
        final int amountToUpdate = duration / 100;
        Timer mTimer = new Timer();
        mTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                if (!(amountToUpdate * seekBar.getProgress() >= duration)) {
                    int p = seekBar.getProgress();
                    p += 1;
                    seekBar.setProgress(p);
                }
            }
        }, amountToUpdate);
        mp.start();
    }



}