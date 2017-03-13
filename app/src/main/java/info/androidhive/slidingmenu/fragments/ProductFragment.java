package info.androidhive.slidingmenu.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.CustomPagerAdapterMainProduct;
import info.androidhive.slidingmenu.adapter.CustomPagerAdapterProduct;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Images;
import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.util.ScrollViewX;

public class ProductFragment extends Fragment {

    TextView articulo;
    TextView precio;
    TextView descripcion;
    //    ImageView imagen;
    private LinearLayout sampleLinear;
    int layout;
    HashMap<Integer, View> productViews;
    private Producto producto;
    private Button play;
    Context context;
    private ProgressBar progressBar;
    ArrayList<Producto> productos;
    Controller controller;
    View view;
    ActionBar actionBar;
    ViewPager viewPager;
    ViewPager similarViewPager;
    CirclePageIndicator similarViewIndicator;
    RelativeLayout similarProductLayout;
    CirclePageIndicator mIndicator;

    public ProductFragment(Context context, int layout, Producto producto) {

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
        actionBar = getActivity().getActionBar();
        viewPager = (ViewPager) rootView.findViewById(R.id.pager_main_product);
        similarProductLayout = (RelativeLayout) rootView.findViewById(R.id.similarProductLayout);
        similarViewPager = (ViewPager) rootView.findViewById(R.id.pager_similar_product);
        similarViewIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator_similar_product);
        mIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator_main_product);

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
                int minDist = 0, maxDist = 255;
                if (scrollY > maxDist) {
                    return 255;
                } else if (scrollY < minDist) {
                    return 0;
                } else {
                    int alpha = 0;
                    alpha = (int) ((255.0 / maxDist) * (maxDist - scrollY));
                    return alpha;
                }
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
                if (productos.size() != 0) {
                    CustomPagerAdapterProduct adapterProduct = new CustomPagerAdapterProduct(context, productos);
                    similarViewPager.setAdapter(adapterProduct);
                    similarViewIndicator.setViewPager(similarViewPager);

                } else {
                    similarProductLayout.setVisibility(View.GONE);
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

    public static void goToSimilarProduct(String codSubCat, Context context) {
        Fragment fragment = null;
        Controller controller = new Controller();
        fragment = new ProductFragment(context, R.layout.activity_products_main, controller.consulta(codSubCat));
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
            ArrayList<Uri> uris = new ArrayList<Uri>();
            if (producto.getDirectorio().size() > 0) {
                for (File file : files) {
                    for (Images images : producto.getDirectorio()) {
                        if (file.getName().equals(images.getDirectory())) {
                            uri = Uri.fromFile(file);
                            uris.add(uri);
                        }
                    }
                }

                CustomPagerAdapterMainProduct adapter = new CustomPagerAdapterMainProduct(context, uris);

                viewPager.setAdapter(adapter);
                mIndicator.setViewPager(viewPager);
            }
//            imagen.setImageURI(uri);
        }
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