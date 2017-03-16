package info.androidhive.slidingmenu.fragments;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.SubCategoryArrayAdapter;
import info.androidhive.slidingmenu.database.Controller;

/**
 * Created by Juan on 16/03/2017.
 */

public class MarcasFragment extends Fragment{

    View rootView;
    public SubCategoryArrayAdapter categoryArrayAdapter;
    public ListView listView;
    int layout;
    Context context;
    int codSubCat;

    public  MarcasFragment(){

    }

    public MarcasFragment(int layout,int codSubCat, Context context) {
        this.context = context;
        this.layout = layout;
        this.codSubCat = codSubCat;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Controller controller = new Controller();
        rootView = inflater.inflate(layout, container, false);
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) rootView.findViewById(R.id.marcasList);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        categoryArrayAdapter = new SubCategoryArrayAdapter(context, R.layout.activity_subcategory, R.layout.activity_subcategory, controller.getProductsByMarcaId(codSubCat));
        listView.setAdapter(categoryArrayAdapter);

        return rootView;

    }

    @Override
    public void onDestroyView() {

        super.onDestroyView();
        rootView = null;
    }
}
