package info.androidhive.slidingmenu.fragments;

import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.viewpagerindicator.CirclePageIndicator;

import info.androidhive.slidingmenu.CategoryMessage;
import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.CustomPagerAdapterMainProduct;
import info.androidhive.slidingmenu.adapter.CustomPagerAdapterProduct;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.entities.Producto;

public class HomeFragment extends Fragment {
    public LinearLayout offerLinear;
    Context context;
    ArrayList<Producto> productos;
    ArrayList<CategoryMessage> categoryMessages;
    Producto producto;
    Controller controller;
    ViewPager similarViewPager;
    CirclePageIndicator similarViewIndicator;
    Timer swipeTimer;


    public HomeFragment(Context context) {
        this.context = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        controller = new Controller();
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);

        offerLinear = (LinearLayout) rootView.findViewById(R.id.offerLinear);

        similarViewPager = (ViewPager) rootView.findViewById(R.id.pager_similar_product);
        similarViewIndicator = (CirclePageIndicator) rootView.findViewById(R.id.indicator_similar_product);
        try {
            categoryMessages = controller.consultaSubCategorias("guitar100");

            if (categoryMessages.size() != 0) {
                productos = controller.consultaArticulos(categoryMessages.get(0).getTitle());

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
    }
}