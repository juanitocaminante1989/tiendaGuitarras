package info.androidhive.slidingmenu;

import java.util.ArrayList;
import java.util.List;

import android.app.Fragment;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import info.androidhive.slidingmenu.adapter.ProductoArrayAdapter;
import info.androidhive.slidingmenu.constants.Constants;
import info.androidhive.slidingmenu.adapter.SubCategoryArrayAdapter;
import info.androidhive.slidingmenu.database.Controller;

public class ProductList extends Fragment {
    View rootView;
    public SubCategoryArrayAdapter categoryArrayAdapter;
    public ListView listView;
    int layout;
    Context context;
    String codSubCat;

    public ProductList(int layout,String codSubCat, Context context) {
        this.context = context;
        this.layout = layout;
        this.codSubCat = codSubCat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Controller controller = new Controller();
        rootView = inflater.inflate(layout, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) rootView.findViewById(R.id.listView1);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        categoryArrayAdapter = new SubCategoryArrayAdapter(getActivity().getApplicationContext(), R.layout.activity_subcategory, R.layout.activity_subcategory, controller.consultaArticulos(codSubCat));
        listView.setAdapter(categoryArrayAdapter);

        return rootView;

    }

}

