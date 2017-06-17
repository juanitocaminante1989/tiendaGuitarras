package info.androidhive.slidingmenu.fragments;

import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import info.androidhive.slidingmenu.R;
import info.androidhive.slidingmenu.adapter.SubCategoryArrayAdapter;
import info.androidhive.slidingmenu.database.Controller;
import info.androidhive.slidingmenu.util.DebugUtilities;

/**
 * Created by Juan on 16/03/2017.
 */

public class MarcasFragment extends CustomFragment{

    View rootView;
    public SubCategoryArrayAdapter categoryArrayAdapter;
    public ListView listView;
    int layout;
    Context context;
    int codSubCat;



    public MarcasFragment(int layout,View view, Context context, int codSubCat, String title) {
        super(layout, view, context, codSubCat, String.valueOf(codSubCat),title);
        this.context = context;
        this.layout = layout;
        this.codSubCat = codSubCat;



    }

    @Override
    public View onCreateCustomView(View var1) {
        Controller controller = new Controller();
        //txtResultado = (TextView)rootView.findViewById(R.id.resultado);
        listView = (ListView) var1.findViewById(R.id.marcasList);
        listView.setTranscriptMode(AbsListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        categoryArrayAdapter = new SubCategoryArrayAdapter(context, R.layout.activity_subcategory, R.layout.activity_subcategory, controller.getProductsByMarcaId(codSubCat));
        listView.setAdapter(categoryArrayAdapter);


        return var1;
    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
        rootView = null;
    }

//    @Override
//    public Thread.UncaughtExceptionHandler UnCaughtExceptionHandler() {
//        return super.UnCaughtExceptionHandler();
//    }
}
