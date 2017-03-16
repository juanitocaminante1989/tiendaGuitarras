package info.androidhive.slidingmenu.fragments;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.viewpagerindicator.CirclePageIndicator;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.CustomPagerAdapterProduct;
import info.androidhive.slidingmenu.adapter.MarcaGridViewAdapter;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Marca;
import info.androidhive.slidingmenu.entities.Producto;

public class HomeFragment extends Fragment {
    Context context;
    ArrayList<Producto> productos;
    Producto producto;
    Controller controller;
    ViewPager similarViewPager;
    CirclePageIndicator similarViewIndicator;
    Timer swipeTimer;
    GridView marcasGridView;
    View generalView;

    public HomeFragment(){

    }

    public HomeFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        controller = new Controller();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        generalView = rootView;
        similarViewPager = (ViewPager) rootView.findViewById(R.id.pager_similar_product);
        similarViewIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator_similar_product);
        marcasGridView = (GridView) rootView.findViewById(R.id.marcaGridHome);

        try {


                productos = controller.getMostViewedProducts();

                if (productos.size() != 0) {
                    producto = productos.get(0);
                    if (producto != null){

                        final CustomPagerAdapterProduct adapter = new CustomPagerAdapterProduct(context, productos);
                        similarViewPager.setAdapter(adapter);
                        similarViewIndicator.setViewPager(similarViewPager);

                        swipeTimer = new Timer();
                        swipeTimer.schedule(new TimerTask() {

                            @Override
                            public void run() {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        int pos = similarViewPager.getCurrentItem();

                                        if(pos == productos.size()-1){
                                            pos = -1;
                                        }
                                        pos++;
                                        similarViewPager.setCurrentItem(pos, true);
                                    }
                                });
                            }
                        }, 500, 3000);

                    }
                }

        ArrayList<Marca> marcas = controller.getMarcas();

            if(marcas != null){
                if(marcas.size()>0){

                    MarcaGridViewAdapter adapter = new MarcaGridViewAdapter(context, 0, marcas);
                    marcasGridView.setAdapter(adapter);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }


    @Override
    public void onDestroyView() {


        super.onDestroyView();
        if(swipeTimer!=null){
            swipeTimer.cancel();
        }
        generalView = null;
    }
}