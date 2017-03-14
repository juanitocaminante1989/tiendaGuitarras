package info.androidhive.slidingmenu.fragments;

import java.io.CharArrayReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.viewpagerindicator.CirclePageIndicator;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.CustomPagerAdapterMainProduct;
import info.androidhive.slidingmenu.adapter.CustomPagerAdapterProduct;
import info.androidhive.slidingmenu.chops.AssociatedShops;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.GPSTracker;
import info.androidhive.slidingmenu.entities.Images;
import info.androidhive.slidingmenu.entities.Producto;
import info.androidhive.slidingmenu.entities.shopStock;
import info.androidhive.slidingmenu.util.ScrollViewX;

public class ProductFragment extends Fragment {


    //    ImageView imagen;
    private LinearLayout sampleLinear;
    private Producto producto;
    private Button play;
    private ProgressBar progressBar;
    private GoogleMap googleMap;
    private int layout;
    private TextView articulo;
    private TextView precio;
    private TextView descripcion;
    private HashMap<Integer, View> productViews;
    private Context context;
    private ArrayList<Producto> productos;
    private Controller controller;
    private View view;
    private ActionBar actionBar;
    private ViewPager viewPager;
    private ViewPager similarViewPager;
    private CirclePageIndicator similarViewIndicator;
    private RelativeLayout similarProductLayout;
    private CirclePageIndicator mIndicator;
    private MapView mMapView;
    private int scrollViewHeight = 0;
    private ScrollViewX scrollView;
    private ListView availableShopsList;

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
        descripcion = (TextView) rootView.findViewById(R.id.descripcion);
        play = (Button) rootView.findViewById(R.id.playsample);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar4);
        actionBar = getActivity().getActionBar();
        viewPager = (ViewPager) rootView.findViewById(R.id.pager_main_product);
        similarProductLayout = (RelativeLayout) rootView.findViewById(R.id.similarProductLayout);
        similarViewPager = (ViewPager) rootView.findViewById(R.id.pager_similar_product);
        similarViewIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator_similar_product);
        mIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator_main_product);
        mMapView = (MapView) rootView.findViewById(R.id.mapViewProducts);
        mMapView.onCreate(savedInstanceState);
        availableShopsList = (ListView) rootView.findViewById(R.id.shopAvailList);

        mMapView.onResume(); // needed to get the map to display immediately



        final ColorDrawable cd = new ColorDrawable(Color.rgb(0, 0, 255));
        actionBar.setBackgroundDrawable(cd);
        cd.setAlpha(255);
        scrollView = (ScrollViewX) rootView.findViewById(R.id.scroll_view);
        ViewTreeObserver vto = scrollView.getViewTreeObserver();
        vto.addOnGlobalLayoutListener (new ViewTreeObserver.OnGlobalLayoutListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onGlobalLayout() {
                scrollView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                scrollViewHeight= scrollView.getMeasuredHeight();

            }
        });
        scrollView.setOnScrollViewListener(new ScrollViewX.OnScrollViewListener() {

            @Override
            public void onScrollChanged(ScrollViewX v, int l, int t, int oldl, int oldt) {

                cd.setAlpha(getAlphaforActionBar(v.getScrollY()));
            }

            private int getAlphaforActionBar(int scrollY) {
                int minDist = 0, maxDist = scrollViewHeight;
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

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                // For showing a move to my location button
                googleMap.setMyLocationEnabled(true);

                // For dropping a marker at a point on the Map
                GPSTracker gpsTracker = new GPSTracker(context);
                LatLng sydney = new LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude());
                if(Controller.getShopByProduct(producto.codArticulo).size()>0) {
                    for (AssociatedShops tienda : Controller.getShopByProduct(producto.codArticulo)) {
                        LatLng coords = new LatLng(tienda.getLatitude(), tienda.getLongitude());
                        googleMap.addMarker(addMarketOptions(coords, tienda.getName(), tienda.getStreet()));
                    }
                }
                // For zooming automatically to the location of the marker
                CameraPosition cameraPosition = new CameraPosition.Builder().target(sydney).zoom(12).build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            }
        });

        fillData();

        ArrayList<shopStock> shopStocks = Controller.getStockShopByProduct(producto.codArticulo);
        if(shopStocks != null){
            if(shopStocks.size()>0){
                listViewAdapter adapter = new listViewAdapter(context, 0, shopStocks);
                availableShopsList.setAdapter(adapter);
                setListViewHeightBasedOnChildren(availableShopsList);
            }
        }

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSample();
            }
        });
        return rootView;
    }

    public MarkerOptions addMarketOptions(LatLng place, String title, String snippet){

        MarkerOptions marker = new MarkerOptions();
        marker.position(place).title(title).snippet(snippet);

        return marker;

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

    private class listViewAdapter extends ArrayAdapter{

        private ArrayList<shopStock> shopStocks;
        private shopStock shopStock;
        private Context context;

        public listViewAdapter(Context context, int resourceLayout, ArrayList<shopStock> shopStocks) {
            super(context, resourceLayout);
            this.context= context;
            this.shopStocks = shopStocks;

        }



        @Override
        public int getCount() {
            return shopStocks.size();
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View row = convertView;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.available_shop_item, parent, false);
            }

            TextView itemName = (TextView) row.findViewById(R.id.shopAvailitemName);
            TextView itemStreet = (TextView) row.findViewById(R.id.shopAvailitemStreet);
            TextView itemCity = (TextView) row.findViewById(R.id.shopAvailitemCity);
            ImageView stockImage = (ImageView) row.findViewById(R.id.stockImage);

            shopStock = shopStocks.get(position);
            itemName.setText(shopStock.getName());
            itemCity.setText(shopStock.getCity());
            itemStreet.setText(shopStock.getStreet());

            Drawable imageId = null;

            switch (shopStock.getStock()){

                case 0:
                    imageId = context.getResources().getDrawable(R.drawable.batteryempty);
                    break;

                case 1:
                    imageId = context.getResources().getDrawable(R.drawable.batterylow);
                    break;

                case 2:
                    imageId = context.getResources().getDrawable(R.drawable.batteryfull);
                    break;

            }

            stockImage.setImageDrawable(imageId);

            notifyDataSetChanged();
            return row;
        }
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

}