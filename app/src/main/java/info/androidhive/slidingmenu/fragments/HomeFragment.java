package info.androidhive.slidingmenu.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.SparseArray;
import android.view.DragEvent;
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

public class HomeFragment extends CustomFragment {
    Context context;
    ArrayList<Producto> productos;
    Controller controller;
    ViewPager similarViewPager;
    CirclePageIndicator similarViewIndicator;
    Timer swipeTimer;
    GridView marcasGridView;
    View generalView;
    int layout;


    public HomeFragment(int layout, View rootView, Context context) {
        super(layout, rootView, context, "main", "Principal");
        this.layout = layout;
        this.context = context;
    }

    @Override
    public View onCreateCustomView(View var1) {
        controller = new Controller();
        generalView = var1;
        similarViewPager = (ViewPager) var1.findViewById(R.id.pager_similar_product);
        similarViewIndicator = (CirclePageIndicator) var1.findViewById(R.id.indicator_similar_product);
        marcasGridView = (GridView) var1.findViewById(R.id.marcaGridHome);

        try {


            productos = controller.getMostViewedProducts();

            if (productos.size() != 0) {

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

//                similarViewPager.setOnDragListener(new View.OnDragListener() {
//                    @Override
//                    public boolean onDrag(View view, DragEvent dragEvent) {
//                        int pos = similarViewPager.getCurrentItem();
//
//                        if(pos == productos.size()-1){
//                            pos = -1;
//                        }
//                        pos++;
//                        similarViewPager.setCurrentItem(pos, true);
//                        return false;
//                    }
//                });
            }

            HashMap<Integer, Marca> marcas = controller.getMarcas();

            if(marcas != null){
                if(marcas.size()>0){

                    MarcaGridViewAdapter adapter = new MarcaGridViewAdapter(context, 0, marcas);
                    marcasGridView.setAdapter(adapter);

                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return var1;
    }

    @Override
    public void onDestroyView() {


        super.onDestroyView();
        if(swipeTimer!=null){
            swipeTimer.cancel();
        }

    }

//    @Override
//    public Thread.UncaughtExceptionHandler UnCaughtExceptionHandler() {
//        return super.UnCaughtExceptionHandler();
//    }
}